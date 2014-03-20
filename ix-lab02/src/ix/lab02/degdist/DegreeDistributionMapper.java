package ix.lab02.degdist;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * This mapper receives, for each article, its set of linked articles. As the
 * final goal of the job is to get, for each degree, the number of articles that
 * have this number of neighbors, we output the degree as key, and 1 as value.
 *
 * Example:
 *
 * Input:
 * Article1		Article2, Article3, Article8, Article12
 *
 * Output:
 * 4	1
 *
 */
@SuppressWarnings("unused")
public class DegreeDistributionMapper extends Mapper<Text, Text, IntWritable, IntWritable> {

    private IntWritable outputKey = new IntWritable();  // Degree of the article / node.
    private static final IntWritable ONE = new IntWritable(1);

    /**
     * This map operation simply counts the number of neighbors of the given article,
     * and outputs (articleDegree, 1).
     *
     * @param inputKey
     *            Name of the article
     * @param inputValues
     *            Concatenated list of linked articles, separated with ", "
     */
    public void map(Text inputKey, Text inputValues, Context context)
            throws IOException, InterruptedException {

        // TODO Write the mapper.
    	String line = inputValues.toString();
    	//System.out.println(inputKey.toString());
    	int i = 0, ans = 1;
    	while (line.indexOf(",", i) >= 0) {
    		i = line.indexOf(",", i) + 1;
    		//System.out.println(i);
    		ans ++;
    	}
    	outputKey.set(ans);
    	context.write(outputKey, ONE);
    }

}
