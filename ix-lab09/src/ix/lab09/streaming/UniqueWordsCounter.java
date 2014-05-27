package ix.lab09.streaming;

import java.util.Iterator;

/** Interface for algorithms that count the number of unique words in a stream. */
public interface UniqueWordsCounter {

    /**
     * Computes the estimated number of unique words encountered in a stream. The result is
     * not guaranteed to be exact, depending on the implementation.
     *
     * @param iter  an iterator of the stream of words
     * @return the (potentially approximate) number of unique words encountered.
     */
    public long countUniqueWords(Iterator<String> iter);

}
