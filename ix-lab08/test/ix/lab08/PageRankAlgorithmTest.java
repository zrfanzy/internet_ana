package ix.lab08;

import static org.junit.Assert.assertEquals;
import ix.lab08.pagerank.PageRankAlgorithm;

import java.util.Arrays;

import org.junit.Test;

import utils.Graph;
import utils.PageRank;

public abstract class PageRankAlgorithmTest {

    /*
     * A directed cycle of 3 nodes with one additional dge in the opposite
     * direction.
     */
    public static final Graph SIMPLE_GRAPH = Graph.fromLines(Arrays.asList(
            "0\t1 2", "1\t2", "2\t0"));
    public static final double[] SIMPLE_GRAPH_PR = new double[] {
        0.3877739, 0.2148450, 0.3973811
    };

    /*
     * Graph with one central "hub" that links to 10 "satellite" nodes. Those
     * satellites simply link back to the hub.
     */
    public static final Graph STAR_GRAPH = Graph.fromLines(Arrays.asList(
            "0\t1 2 3 4 5 6 7 8 9 10", "1\t0", "2\t0", "3\t0", "4\t0",
            "5\t0", "6\t0", "7\t0", "8\t0", "9\t0", "10\t0"));
    public static final double[] STAR_GRAPH_PR = new double[] {
        0.4669613, 0.0533039, 0.0533039, 0.0533039, 0.0533039, 0.0533039,
        0.0533039, 0.0533039, 0.0533039, 0.0533039, 0.0533039
    };

    /*
     * Two disconnected components containing, each one containing a cycle with
     * two nodes.
     */
    public static final Graph TWO_COMPONENTS = Graph.fromLines(Arrays.asList(
            "0\t1", "1\t0", "2\t3", "3\t2"));
    public static final double[] TWO_COMPONENTS_PR = new double[] {
        0.25, 0.25, 0.25, 0.25
    };

    /*
     * Graph with one absorbing state, i.e. no outgoing link. Two nodes link to
     * a third one (the absorbing one).
     */
    public static final Graph ABSORBING_GRAPH = Graph.fromLines(Arrays.asList(
            "0\t2", "1\t2", "2\t"));
    public static final double[] ABSORBING_GRAPH_PR = new double[] {
    	0.2126970, 0.2125820, 0.5747210
    };


    private double delta = 0.001;


    public abstract PageRankAlgorithm getInstance();


    @Test
    public void testSumsUpToOne() {
        PageRankAlgorithm algo = this.getInstance();
        PageRank pr = algo.compute(STAR_GRAPH);
        double sum = 0;
        for (int i = 0; i < STAR_GRAPH.size(); ++i) {
            sum += pr.get(i);
        }
        assertEquals("page rank values have to sum up to one",
                1, sum, this.delta);
    }


    @Test
    public void testGraphIsSame() {
        PageRankAlgorithm algo = this.getInstance();
        PageRank pr = algo.compute(STAR_GRAPH);
        assertEquals("PageRank instance has to be constructed with graph passed to compute()",
                STAR_GRAPH, pr.getGraph());
    }


    @Test
    public void testStarGraph() {
        this.testGraph(STAR_GRAPH, STAR_GRAPH_PR);
    }


    @Test
    public void testSimpleGraph() {
        this.testGraph(SIMPLE_GRAPH, SIMPLE_GRAPH_PR);
    }


    @Test
    public void testTwoComponents() {
        this.testGraph(TWO_COMPONENTS, TWO_COMPONENTS_PR);
    }


    @Test
    public void testAbsorbingGraph() {
        this.testGraph(ABSORBING_GRAPH, ABSORBING_GRAPH_PR);
    }


    private void testGraph(Graph graph, double[] truth) {
        PageRankAlgorithm algo = this.getInstance();
        PageRank pr = algo.compute(graph);
        for (int i = 0; i < graph.size(); ++i) {
            assertEquals(truth[i], pr.get(i), this.delta);
        }
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

}
