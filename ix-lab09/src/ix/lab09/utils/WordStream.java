package ix.lab09.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.hadoop.thirdparty.guava.common.collect.Lists;


/**
 * This class acts as an iterator over words, given an InputStream representing
 * a source. The words are preprocessed and cleaned.
 */
public class WordStream implements Iterator<String> {

    private final Scanner scanner;
    private List<String> words;


    public WordStream(InputStream input) {
        this.scanner = new Scanner(input);
        this.words = Lists.newArrayList();
    }


    /** Returns true if there remains elements in the stream. */
    @Override
    public boolean hasNext() {
        while (this.words.size() == 0) {
            if (this.scanner.hasNext()) {
                this.words = Tokenization.tokens(this.scanner.nextLine());
            } else {
                return false;
            }
        }
        return true;
    }


    /** Returns the next element in the stream. */
    @Override
    public String next() {
        if (this.hasNext()) {
            return this.words.remove(0);
        } else {
            throw new NoSuchElementException();
        }
    }


    /** Not implemented. */
    @Override
    public void remove() {
        // Doesn't make sense here.
        throw new UnsupportedOperationException();
    }


    /** Small helper function that parses the command line arguments. */
    public static InputStream getInputFromArgs(String[] args)
            throws FileNotFoundException {
        if (args.length == 0) {
            return System.in;
        } else if (args.length == 1) {
            return new FileInputStream(args[0]);
        }
        // Else.
        System.err.println("Usage: CMD [file]");
        System.exit(0);
        return null;
    }


    /** Simple main for testing purposes. */
    public static void main(String[] args) throws Exception {
        InputStream input = getInputFromArgs(args);

        WordStream stream = new WordStream(input);
        while (stream.hasNext()) {
            System.out.println(stream.next());
        }
    }
}
