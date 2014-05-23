package ix.lab08.pagerank;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.apache.hadoop.thirdparty.guava.common.collect.TreeMultimap;

import utils.Graph;
import utils.PageRank;

public class Manipulation {

    public static final int HISTORY_OF_MATHS = 2463;
    public static final int EDGE_BUDGET = 300;

    /** Our best PageRank improvement when adding 300 incoming edges. */
    public static final double GREEDY_PR = 0.0066537147911695845;


    /**
     * Improve the PageRank of node HISTORY_OF_MATHS at index 2463.
     * We are only allowed to add incoming edges to this node.
     * You have a fixed budget of adding edges.
     * @param graph
     * @param node
     */
    public static void addIncomingEdges(Graph graph, int node) {
        // TODO
    	PageRankAlgorithm algo = new PowerMethod();
        PageRank pr = algo.compute(graph);
        int max = -1;
    	for (int i = 0; i < graph.size(); ++i) {
    		if (i == node) continue;
            if (!graph.containsEdge(i, node)) {
            	if (max == -1) max = i;
            	else if (pr.get(max) - pr.get(i) < 1e-9) {
            		max = i;
            	}
            }
        }
    	graph.addEdge(max, node);
    }


    /**
     * Improve the PageRank of node HISTORY_OF_MATHS at index 2463.
     * We are allowed to add edges everywhere in the graph.
     * You have a fixed budget of adding edges.
     * @param graph
     * @param node
     */
    public static void addEdges(Graph graph, int node) {
        // TODO
    }


    public static void main(String[] args) throws Exception {
        Graph graph = Graph.fromFile(Graph.WIKIPEDIA_PATH);
        addIncomingEdges(graph, HISTORY_OF_MATHS);

        PageRank pr = new PowerMethod().compute(graph);
        System.out.println(String.format("You're at %.2f%% of our best pagerank!",
                100 * pr.get(HISTORY_OF_MATHS) / GREEDY_PR));
    }

}
