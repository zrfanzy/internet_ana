package ix.lab09;

import static org.junit.Assert.*;
import ix.lab09.streaming.FlajoletMartin;
import ix.lab09.utils.HashFunction;

import org.junit.Test;

public class FlajoletMartinTest {

    private static final double EPSILON = 1e-9;

    @Test
    public void testGetLeadinggZeros() {
        int nbBits = 40;
        int shift = 64 - nbBits;
        assertEquals(0, FlajoletMartin.getLeadingZeros(-1L >>> shift, nbBits));
        assertEquals(15, FlajoletMartin.getLeadingZeros(-1L >>> shift + 15, nbBits));
    }

    @Test
    public void testMedian() {
        double[] arr1 = new double[] {1.0, 2.0, 5.0};
        double[] arr2 = new double[] {1.0, 2.0, 3.2, 10.0};
        assertEquals(2.0, FlajoletMartin.median(arr1), EPSILON);
        assertEquals(2.6, FlajoletMartin.median(arr2), EPSILON);
    }

    @Test
    public void testProcessWord() {
        int nbBits = 10;
        int nbHashes = 5;

        HashFunction.PRNG.setSeed(42);
        FlajoletMartin fm = new FlajoletMartin(nbBits, nbHashes);

        String[] words = new String[]{"once", "upon", "a", "time", "there", "was"};
        int[][] maxZeros = new int[][]{
                new int[]{0, 1, 0, 0, 2},
                new int[]{7, 1, 0, 0, 2},
                new int[]{7, 1, 0, 0, 2},
                new int[]{7, 1, 0, 4, 5},
                new int[]{7, 1, 0, 4, 5},
                new int[]{7, 1, 0, 4, 5}};

        for (int i = 0; i < words.length; ++i) {
            fm.processWord(words[i]);
            assertArrayEquals(maxZeros[i], fm.maxZeros);
        }
    }
}