/**
 * A class to compute a deviation from item mean predictor prediction for the target item.
 * 
 * Haolin Zhang
 * 24/02/2019
 */

package alg.ib.predictor;

import java.util.Map;

import alg.ib.neighbourhood.Neighbourhood;
import profile.Profile;
import similarity.SimilarityMap;

public class DeviationFromItemMeanPredictor implements Predictor
{
	public DeviationFromItemMeanPredictor()
	{
	}
	
	public Double getPrediction(Integer userId, Integer itemId, Map<Integer, Profile> userProfileMap,
			Map<Integer, Profile> itemProfileMap, Neighbourhood neighbourhood, SimilarityMap simMap)
	{
		double numerator = 0, denominator = 0;
		
		for (Integer id: userProfileMap.get(userId).getIds())
		{
			if(neighbourhood.isNeighbour(itemId, id))
			{
				Double rating = userProfileMap.get(userId).getValue(id);
				Double similarity = simMap.getSimilarity(itemId, id);
				Double ratingAvg_id = itemProfileMap.get(id).getMeanValue();
				numerator += similarity.doubleValue() * (rating.doubleValue() - ratingAvg_id.doubleValue());
				denominator += similarity.doubleValue();
			}
		}
		double ratingAvg_item = itemProfileMap.get(itemId).getMeanValue();
		
		return denominator > 0 ? ratingAvg_item + (numerator / denominator) : null;
	}

}
