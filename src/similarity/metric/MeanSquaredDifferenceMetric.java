/**
 * Compute the MeanSquaredDifference similarity between profiles
 * 
 * Haolin Zhang
 * 24/02/2019
 */

package similarity.metric;

import java.util.Set;
import profile.Profile;

public class MeanSquaredDifferenceMetric implements SimilarityMetric
{
	public MeanSquaredDifferenceMetric()
	{
	}

	public double getSimilarity(Profile p1, Profile p2)
	{
		double numerator = 0, denominator = 0, r_max = 5.0, r_min = 0.5;
		Set<Integer> common = p1.getCommonIds(p2);
		for (Integer id: common)
		{
			double r1 = p1.getValue(id).doubleValue();
			double r2 = p2.getValue(id).doubleValue();
			
			numerator += Math.pow(r1 - r2, 2);
		}
		
		if (common.size() == 0)
		{
			numerator = 0;
		}
		else numerator = numerator / common.size();
		denominator = Math.pow(r_max - r_min, 2);
		
		return denominator > 0 ? 1 - (numerator / denominator) : 0;
	}
}
