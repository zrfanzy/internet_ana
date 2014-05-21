package ix.lab08;

import static ix.lab08.PageRankAlgorithmTest.SIMPLE_GRAPH;
import static ix.lab08.PageRankAlgorithmTest.STAR_GRAPH;
import static org.junit.Assert.assertEquals;
import ix.lab08.pagerank.NaiveRandomSurfer;

import org.junit.Test;

import utils.Graph;
import utils.PageRank;

/**
 * Warning: these tests work only for the very first part (random surfer without
 * random restarts). They will not work anymore as soon as you integrate random
 * restarts.
 */
public class NaiveRandomSurferTest {

    private static final double DELTA = 0.01;

    private static final double[] SIMPLE_GRAPH_PR = new double[] {
        0.4, 0.2, 0.4
    };

    private static final double[] STAR_GRAPH_PR = new double[] {
        0.5, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05
    };

    @Test
    public void testSimpleGraph() {
        this.testGraph(SIMPLE_GRAPH, SIMPLE_GRAPH_PR);
    }

    @Test
    public void testStarGraph() {
        this.testGraph(STAR_GRAPH, STAR_GRAPH_PR);
    }

    private void testGraph(Graph graph, double[] truth) {
        PageRank pr = new NaiveRandomSurfer().compute(graph);
        for (int i = 0; i < graph.size(); ++i) {
            assertEquals(truth[i], pr.get(i), DELTA);
        }
    }

}
