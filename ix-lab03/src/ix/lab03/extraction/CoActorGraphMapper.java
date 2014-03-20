package ix.lab03.extraction;

import ix.utils.TextArrayWritable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

@SuppressWarnings("unused")
public class CoActorGraphMapper extends Mapper<Text, // input key: movie title
                                          Text, // input value: actors list
                                          Text, // output key: actor name
                                          TextArrayWritable> { // output value: list of his co-actors in this movie

    private static final Pattern YEAR_PATTERN = Pattern.compile(".+ \\((\\d{4})\\)");

    private Text outputKey = new Text();
    private TextArrayWritable outputValue = new TextArrayWritable();

    private int startingYear;
    private int endingYear;

    public void map(Text inputKey, Text inputValue, Context context)
            throws IOException, InterruptedException {

        // Starting year of the period of interest.
        this.startingYear = context.getConfiguration().getInt("startingYear", -1);
        // Ending year of the period of interest
        this.endingYear = context.getConfiguration().getInt("endingYear", -1);

        Matcher m = YEAR_PATTERN.matcher(inputKey.toString());
        if (!m.matches()) {
            throw new RuntimeException(String.format("Failed to process key ''", inputKey.toString()));
        }

        int movieYear = Integer.parseInt(m.group(1));
        
        String line = inputValue.toString();
        if (movieYear < this.startingYear || movieYear > this.endingYear) return;
        String[] names = line.split(",");
        Collection<String> texts = new ArrayList<String>();
        for (int i = 0; i < names.length; ++i) {
        	Pattern pt=Pattern.compile("^\\s*|\\s*$");
        	Matcher mt=pt.matcher(names[i]);
        	names[i]=mt.replaceAll("");
        	texts.add(names[i]);
        }
        for (int i = 0 ; i < names.length ; i++ ) { 
        	 
        	outputKey.set(names[i]);
        	outputValue.setStringCollection(texts);
        	context.write(outputKey, outputValue);
   
        } 
        //TODO Extract all pairs of actors that played in the movie, if the movie was in the years of interest
        // HINT: Use the method setStringCollection to set the output value.
    }

}
