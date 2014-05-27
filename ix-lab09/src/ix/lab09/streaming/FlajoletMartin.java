package ix.lab09.streaming;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.hadoop.thirdparty.guava.common.base.Preconditions;

import ix.lab09.utils.*;


/**
 * Implementation of the Flajolet-Martin algorithm for approximately counting
 * the number of distinct words in a stream.
 */
public class FlajoletMartin implements UniqueWordsCounter {

    private final int nbBits;
    private final HashFunction[] hashFunctions;
    public final int[] maxZeros;  // Public for testing purposes.


    /**
     * Constructs an instance of the Flajolet-Martin algorithm.
     *
     * @param nbBits  the number of bits per hash
     * @param nbHashes  the number of hashes
     */
    public FlajoletMartin(int nbBits, int nbHashes) {
        this.nbBits = nbBits;
        this.hashFunctions = new HashFunction[nbHashes];
        this.maxZeros = new int[nbHashes];

        for (int i = 0; i < nbHashes; ++i) {
            hashFunctions[i] = HashFunction.randomInstance(this.nbBits);
        }
    }


    /** Approximate count of the number of of words. */
    public long countUniqueWords(Iterator<String> iter) {
        while (iter.hasNext()) {
            this.processWord(iter.next());
        }
        // TODO Complete.
        return 0L;
    }


    /** Computes the median of an array. */
    public static double median(double[] arr) {
        Preconditions.checkArgument(arr.length > 0);
        // TODO Complete.
        // Note: if the array has even length, we define the median as the
        // average of the two middle values.
        return 0.0;
    }


    /**
     * Computes the hashes for a word, and updates entries of the maxZeros array
     * if needed.
     *
     * @param word  the word to process
     */
    public void processWord(String word) {
        // TODO Complete.
    }


    /**
     * Computes the number of leading zeros in a hash. Hashes are encoded
     * as long (64 bits) but we are only interested in the nbBits last bits.
     *
     * @param hash  the hash, encoded in a long
     * @param nbBits  the number of bits to consider
     * @return the number of leading zeros in the hash
     */
    public static int getLeadingZeros(long hash, int nbBits) {
        return Long.numberOfLeadingZeros(hash) - (64 - nbBits);
    }


    public static void main(String[] args) throws Exception {
        InputStream input = WordStream.getInputFromArgs(args);

        int nbBits = 24;  // The number of bits for each hash.
        int nbHashes = 10;  // The number of hash functions.

        UniqueWordsCounter counter = new FlajoletMartin(nbBits, nbHashes);
        Iterator<String> stream = new WordStream(input);

        long count = counter.countUniqueWords(stream);
        System.out.println(String.format("Estimated number of distinct words: %d", count));
    }
}