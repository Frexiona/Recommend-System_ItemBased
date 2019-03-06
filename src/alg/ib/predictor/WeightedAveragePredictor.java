/**
 * A class to compute a weighted average predictor prediction for the target item.
 * 
 * Haolin Zhang
 * 25/02/2019
 */

package alg.ib.predictor;

import java.util.Map;

import alg.ib.neighbourhood.Neighbourhood;
import profile.Profile;
import similarity.SimilarityMap;

public class WeightedAveragePredictor implements Predictor
{
	public WeightedAveragePredictor()
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
				numerator += rating.doubleValue() * similarity.doubleValue();
				denominator += Math.abs(similarity.doubleValue());
			}
		}
		
		// if (denominator == 0) return (double)0;
		
		return numerator > 0 ? numerator / denominator : null;
	}
	
}
