/**
 * Compute the CosineMetric similarity between profiles
 * 
 * Haolin Zhang
 * 24/02/2019
 */

package similarity.metric;

import java.util.Set;
import profile.Profile;

public class CosineMetric implements SimilarityMetric
{
	public CosineMetric()
	{
	}

	public double getSimilarity(Profile p1, Profile p2)
	{
		double numerator = 0, denominator = 0;
		
		Set<Integer> common = p1.getCommonIds(p2);
		for(Integer id: common)
		{
			double r1 = p1.getValue(id).doubleValue();
			double r2 = p2.getValue(id).doubleValue();
			
			numerator += r1 * r2;
		}
		
		denominator = p1.getNorm() * p2.getNorm();
		
		return denominator > 0 ? numerator / denominator : 0;
	}
	
}
