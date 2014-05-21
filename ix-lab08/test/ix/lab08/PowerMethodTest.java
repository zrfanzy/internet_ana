package ix.lab08;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import ix.lab08.pagerank.PageRankAlgorithm;
import ix.lab08.pagerank.PowerMethod;

import org.junit.Test;

public class PowerMethodTest extends PageRankAlgorithmTest {

    private static final double DELTA = 10e-8;

    @Test
    public void testInitVector() {
        // The important thing is that entries are non-null and sum up to 1.
        int size = 10;
        double[] vec = PowerMethod.initVector(size);
        assertEquals("vector needs to be of length 'size'", size, vec.length);
        for (double val : vec) {
            assertTrue("entries need to be non-null", Math.abs(val) > DELTA);
        }
        double sum = 0.0;
        for (double val : vec) {
            sum += val;
        }
        assertEquals("vector has to sum up to 1", 1, sum, DELTA);
    }


    @Test
    public void testMultiply() {
        double[][] matrix = new double[][] {
            new double[] {1, -0.2, -0.38},
            new double[] {3.41, 5.3, -2.3},
            new double[] {-3.1, 0.64, 0.1}
        };
        double[] vec = new double[] {0.5, 0.2, -0.37};
        double[] expected = new double[] {2.329, 0.7232, -0.687};
        double[] actual = PowerMethod.multiply(vec,matrix);
        assertArrayEquals(expected, actual, DELTA);
    }


    @Test
    public void testSimpleGraphTransitions() {
        double[][] expected = new double[][] {
            new double[] {0.05, 0.475, 0.475},
            new double[] {0.05, 0.05, 0.9},
            new double[] {0.9, 0.05, 0.05}
        };
        double[][] actual = PowerMethod.googleMatrix(SIMPLE_GRAPH);
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; ++i) {
            assertArrayEquals(expected[i], actual[i], DELTA);
        }
    }


    @Test
    public void testAbsorbingGraphTransitions() {
        double[][] expected = new double[][] {
            new double[] {0.05, 0.05, 0.9},
            new double[] {0.05, 0.05, 0.9},
            new double[] {0.3333333333333333, 0.3333333333333333, 0.3333333333333333}
        };
        double[][] actual = PowerMethod.googleMatrix(ABSORBING_GRAPH);
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; ++i) {
            assertArrayEquals(expected[i], actual[i], DELTA);
        }
        
    }


    @Override
    public PageRankAlgorithm getInstance() {
        return new PowerMethod();
    }

}
