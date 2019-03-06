package alg.ib;

import java.io.File;

import alg.ib.neighbourhood.NearestNeighbourhood;
import alg.ib.neighbourhood.Neighbourhood;
import alg.ib.neighbourhood.ThresholdNeighbourhood;
import alg.ib.predictor.DeviationFromItemMeanPredictor;
import alg.ib.predictor.Predictor;
import alg.ib.predictor.SimpleAveragePredictor;
import alg.ib.predictor.WeightedAveragePredictor;
import similarity.metric.CosineMetric;
import similarity.metric.PearsonMetric;
import similarity.metric.SimilarityMetric;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;

public class ExecuteIB_ML20M_2 {
	public static void main(String[] args)
	{
		for (float t = 0; t < 0.75; t += 0.05)
		{
			// configure the item-based CF algorithm - set the predictor, neighbourhood and similarity metric ...
			Predictor predictor = new DeviationFromItemMeanPredictor();
			Neighbourhood neighbourhood = new ThresholdNeighbourhood(t);
			SimilarityMetric metric = new CosineMetric();
			
			// set the paths and filenames of the item file, genome scores file, train file and test file ...
			String folder = "ml-20m-2018-2019";
			String itemFile = folder + File.separator + "movies-sample.txt";
			String itemGenomeScoresFile = folder + File.separator + "genome-scores-sample.txt";
			String trainFile = folder + File.separator + "train.txt";
			String testFile = folder + File.separator + "test.txt";	
			
			// set the path and filename of the output file ...
			String outputFile = "results" + File.separator + "predictions.txt";
			
			////////////////////////////////////////////////
			// Evaluates the CF algorithm (do not change!!):
			// - the RMSE (if actual ratings are available) and coverage are output to screen
			// - output file is created
			DatasetReader reader = new DatasetReader(itemFile, itemGenomeScoresFile, trainFile, testFile);
			ItemBasedCF ibcf = new ItemBasedCF(predictor, neighbourhood, metric, reader);
			Evaluator eval = new Evaluator(ibcf, reader.getTestData());
			
			// Write to output file
			eval.writeResults(outputFile);
			
			// Display which k what KNN used
			System.out.printf("Threshold = %.2f\n", t);
			
			// Display RMSE and coverage
			Double RMSE = eval.getRMSE();
			if(RMSE != null) System.out.printf("RMSE: %.6f\n", RMSE);
			
			double coverage = eval.getCoverage();
			System.out.printf("coverage: %.2f%%\n", coverage);	
			
			System.out.println();
		}
	}
}
