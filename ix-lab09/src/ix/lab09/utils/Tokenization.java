package ix.lab09.utils;

import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.hadoop.thirdparty.guava.common.collect.Lists;

import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.LowerCaseTokenizerFactory;
import com.aliasi.tokenizer.ModifyTokenTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;


/**
 * Tokenization of text documents. This class provides utilities to transform
 * the raw character string representing a document into a stream of words.
 *
 * More concretely, the following processing steps are applied:
 * - tokens are split around whitespace, hyphens and punctuation
 * - diacritics and non-alphanumeric symbols are removed
 * - tokens are converted to lowercase
 */
public class Tokenization {

    private static final TokenizerFactory TOKENIZER_FACTORY = getFactory();

    /**
     * TokenizerFactory that removes diacritics (accents) and all non-alphanumeric
     * characters.
     */
    @SuppressWarnings("serial")
    private static class NormalizationTokenizerFactory extends ModifyTokenTokenizerFactory {

        private static final Pattern DISALLOWED = Pattern.compile("[^a-zA-Z0-9]");

        public NormalizationTokenizerFactory(TokenizerFactory factory) {
            super(factory);
        }

        @Override
        public String modifyToken(String token) {
            // Unicode canonical decomposition separates diacritics from base char.
            String normalized =  Normalizer.normalize(token, Normalizer.Form.NFD);
            // Replace non-alphanumeric chars (also strips out diacritics).
            String modified = DISALLOWED.matcher(normalized).replaceAll("");
            // If resulting string is empty, return null.
            return modified.length() > 0 ? modified : null;
        }
    }


    /** Builds an appropriate TokenizerFactory. */
    private static TokenizerFactory getFactory() {
        TokenizerFactory factory = IndoEuropeanTokenizerFactory.INSTANCE;
        // Convert everything to ASCII and remove symbols we're not interested in.
        factory = new NormalizationTokenizerFactory(factory);
        // Lower case everything.
        factory = new LowerCaseTokenizerFactory(factory);

        return factory;
    }


    /**
     * Given a string, e.g. a line, returns a list of (processed) words.
     *
     * @param data the original character string from which to extract the terms
     * @return an array containing the terms resulting from the processing steps
     */
    public static List<String> tokens(String data) {
        final char[] chars = data.toCharArray();
        return Lists.newArrayList(
                TOKENIZER_FACTORY.tokenizer(chars, 0, chars.length).tokenize());
    }

}
