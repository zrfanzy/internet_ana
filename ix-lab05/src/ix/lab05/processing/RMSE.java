package ix.lab05.processing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


/**
 * Utility class to compute the root mean squared error of some predictions for one key user.
 */
public final class RMSE {

    /** User for which we compare the predicted ratings with the true ones. */
    public static final int USER_ID = 16355;
	
	private RMSE() {}
    
    /**
     * Computes the RMSE between the two sets of ratings.
     * 
     * @param groundTruth The true ratings
     * @param recommendations The estimated ratings
     * @return
     */
    public static double evaluate(Map<Integer, Double> groundTruth, Map<Integer, Double> recommendations) {
        //TODO
    	int N = 0;
    	double ans = 0.0;
    	for (Map.Entry<Integer, Double> entry : groundTruth.entrySet())
    	{
    		N ++;
    		ans = ans + Math.pow(entry.getValue() - recommendations.get(entry.getKey()), 2.0);
    	}
        return Math.sqrt(ans / N);
    }
    
    /**
     * Computes the RMSE by comparing the true ratings to the ones estimated by
     * the recommender system.
     * 
     * @param groundTruthPath Path to the CSV file containing the true ratings for the user 
     * @param recommendations List of estimated ratings for the user.
     * @return RMSE of the given recommendations.
     * @throws FileNotFoundException
     */
    public static double evaluate(String groundTruthPath, Map<Integer, Double> recommendations) throws FileNotFoundException {
        Map<Integer, Double> groundTruth = parseGroundTruth(groundTruthPath);
    	
        return RMSE.evaluate(groundTruth, recommendations);
    }
	
    /**
     * Parses the ground truth from a CSV file.
     *
     * @param path path to the file containing the ground truth
     * @return a mapping between movie IDs and (true) ratings
     * @throws FileNotFoundException
     */
    private static Map<Integer, Double> parseGroundTruth(String path)
            throws FileNotFoundException {
        Map<Integer, Double> ratings = new HashMap<Integer, Double>();

        Scanner scanner = new Scanner(new FileInputStream(path));
        while (scanner.hasNextLine()) {
            String[] values = scanner.nextLine().split(",");
            int movieID = Integer.parseInt(values[0]);
            double rating = Double.parseDouble(values[1]);
            ratings.put(movieID, rating);
        }
        return ratings;
    }

}
