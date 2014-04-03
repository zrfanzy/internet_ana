package ix.lab05.processing;

import static ix.lab05.processing.NetflixMatrix.TAB;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.mahout.math.SequentialAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

/** Collects all the ratings for a user and stores them in a sparse vector. */
@SuppressWarnings("unused")
public class NetflixMatrixReducer extends
        Reducer<IntWritable, Text, IntWritable, VectorWritable> {

    private VectorWritable outputValue = new VectorWritable();

    @Override
    public void reduce(IntWritable inputKey, Iterable<Text> inputValues,
            Context context) throws IOException, InterruptedException {

        // do not worry about the strange type, use it as a regular Vector
        Vector ratings = new SequentialAccessSparseVector(Integer.MAX_VALUE, 1);

        // TODO
        for (Text t : inputValues) {
        	String s = t.toString();
        	int i = s.indexOf(TAB);
        	String movieID = s.substring(0, i);
        	int ID = Integer.parseInt(movieID);
        	s = s.substring(i + 1);
        	double rate = Double.parseDouble(s);
        	ratings.set(ID, rate);
        }
        this.outputValue.set(ratings);
        context.write(inputKey, this.outputValue);

    }

}
