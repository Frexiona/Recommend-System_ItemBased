/**
 * A class that implements the "Threshold Neighbourhood" technique (item-based CF)
 * 
 * Haolin Zhang
 * 24/02/2019
 */

package alg.ib.neighbourhood;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import similarity.SimilarityMap;
import profile.Profile;
import util.ScoredThingDsc;

public class ThresholdNeighbourhood extends Neighbourhood
{
	private final float threshold;
	
	public ThresholdNeighbourhood(float threshold)
	{
		super();
		this.threshold = threshold;
	}
	
	public void computeNeighbourhoods(SimilarityMap simMap)
	{
		for (Integer itemId: simMap.getIds())
		{
			SortedSet<ScoredThingDsc> ss = new TreeSet<ScoredThingDsc>();
			
			Profile profile = simMap.getSimilarities(itemId);
			if(profile != null)
			{
				for(Integer id: profile.getIds())
				{
					double sim = profile.getValue(id);
					if(sim > 0)
						ss.add(new ScoredThingDsc(sim, id));
				}
			}
			
			for(Iterator<ScoredThingDsc> iter = ss.iterator(); iter.hasNext();)
			{
				ScoredThingDsc st = iter.next();
				Integer id = (Integer)st.thing;
				if (st.score >= threshold)
				{
					this.add(itemId, id);
				}
				else break;
			}
		}
	}

}
