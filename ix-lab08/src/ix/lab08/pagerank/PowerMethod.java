package ix.lab08.pagerank;

import static org.apache.hadoop.thirdparty.guava.common.base.Preconditions.checkArgument;
import java.util.List;
import utils.Graph;
import utils.PageRank;

public class PowerMethod implements PageRankAlgorithm {

    private static final double TOLERANCE = 10e-9;


    /**
     * Returns the Google matrix, a column-stochastic matrix that represents the
     * transition probabilities between nodes in the graph. Entry (i,j) represents
     * the probability of going from i to j.
     *
     * Important note: when a node has no outgoing link (dangling node), the probability
     * is uniformly distributed over all the nodes.
     *
     * @param graph
     * @return the Google matrix associated to the graph
     */
    public static double[][] googleMatrix(Graph graph) {
        int n = graph.size();
        double[][] probs = new double[n][n];
        double delta = DAMPING_FACTOR / n;
        double theta = 1 - DAMPING_FACTOR;

        // TODO
        return probs;
    }


    /**
     * Returns a vector that can be used to initialize the power iterations
     * method. It can be seen as an initial probability distribution over N
     * objects. It should satisfy two constraints:
     *
     * - no entry should be zero
     * - the entries should sum up to 1.
     *
     * @param n
     * @return
     */
    public static double[] initVector(int n) {
        double[] vec = new double[n];
        double val = 1.0 / vec.length;
        for (int i = 0; i < vec.length; ++i) {
            vec[i] = val;
        }
        return vec;
    }


    /**
     * Performs the multiplication of a square matrix with a column vector.
     * @param vector V
     * @param matrix M
     * @return V * M
     */
    public static double[] multiply( double[] vector,double[][] matrix) {
        double[] result = new double[matrix.length];
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix.length; ++j) {
                result[i] += matrix[j][i] * vector[j];
            }
        }
        return result;
    }


    /**
     * Computes the mean square difference between two vectors of same size. Useful
     * to check whether a sequence of vectors is converging.
     *
     * @param vec1 the first vector
     * @param vec2 the second vector
     * @return the mean square difference between the two vectors.
     */
    public static double mse(double[] vec1, double[] vec2) {
        checkArgument(vec1.length == vec2.length, "size mismatch");
        double ss = 0;
        for (int i = 0; i < vec1.length; ++i) {
            double diff = (vec1[i] - vec2[i]);
            ss += diff * diff;
        }
        return ss / vec1.length;
    }


    /**
     * Compute the PageRank score of nodes for a given graph.
     * Uses the power iteration method explained in the equation (2) of Lab assignment.
     */
    @Override
    public PageRank compute(Graph graph) {
        double[][] probs = googleMatrix(graph);
        double[] vec = initVector(graph.size());
        // TODO

        return new PageRank(graph, vec);
    }


    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println(String.format(
                    "Usage: %s <graph>", PowerMethod.class.getName()));
            return;
        }
        Graph graph = Graph.fromFile(args[0]);
        PageRank pr = new PowerMethod().compute(graph);
        pr.printTop(); // Note: you can also use pr.printAll().
    }

}
