package ix.lab08;

import static ix.lab08.pagerank.Manipulation.EDGE_BUDGET;
import static ix.lab08.pagerank.Manipulation.addEdges;
import static ix.lab08.pagerank.Manipulation.addIncomingEdges;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import utils.Graph;

public class ManipulationTest {

    public static int TARGET = 5;

    @Test
    public void testAddIncomingEdges() throws IOException {
        Graph original = Graph.cycle(EDGE_BUDGET + 10);

        Graph graph = Graph.cycle(EDGE_BUDGET + 10);
        addIncomingEdges(graph, TARGET);

        int total = 0;
        for (int i = 0; i < graph.size(); ++i) {
            List<Integer> neighbs = graph.neighbors(i);
            int nb = original.neighbors(i).size();
            int diff = neighbs.size() - nb;
            assertTrue(diff >= 0 && diff <= 1);
            if (diff == 1) {
                assertEquals("only links towards target node is allowed",
                        TARGET, neighbs.get(nb).intValue());
                ++total;
            }
        }
        assertTrue("edges cannot exceed budget", total <= EDGE_BUDGET);
    }


    @Test
    public void testAddEdges() throws IOException {
        Graph original = Graph.cycle(EDGE_BUDGET + 10);

        Graph graph = Graph.cycle(EDGE_BUDGET + 10);
        addEdges(graph, TARGET);

        int total = 0;
        for (int i = 0; i < graph.size(); ++i) {
            int diff = graph.neighbors(i).size() - original.neighbors(i).size();
            assertTrue("cannot remove edges", diff >= 0);
            total += diff;
        }
        assertTrue("edges cannot exceed budget", total <= EDGE_BUDGET);
    }

}
