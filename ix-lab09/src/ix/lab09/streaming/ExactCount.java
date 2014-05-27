package ix.lab09.streaming;

import ix.lab09.utils.WordStream;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Counts the exact number of distinct words in a stream of words.
 * Space complexity: O(nlogn).
 */
public class ExactCount implements UniqueWordsCounter {

    @Override
    public long countUniqueWords(Iterator<String> iter) {
        /*
         * Idea: put all the words in a set (gets rid of the duplicates)
         * and return the final size of the set.
         */
        Set<String> distinctWords = new HashSet<String>();
        while (iter.hasNext()) {
            distinctWords.add(iter.next());
        }
        return distinctWords.size();
    }

    public static void main(String[] args) throws Exception {
        InputStream input = WordStream.getInputFromArgs(args);

        UniqueWordsCounter counter = new ExactCount();
        Iterator<String> stream = new WordStream(input);

        long count = counter.countUniqueWords(stream);
        System.out.println(String.format("Exact number of distinct words: %d", count));
    }

}
