package ix.lab08.pagerank;

import static org.junit.Assert.assertEquals;

import java.awt.List;
import java.util.ArrayList;
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
        double maxans = -1;
        for (int loops = 0; loops < 300; ++loops) {
        	max = -1;
        	for (int i = 0; i < graph.size(); ++i) {
    			if (i == node) continue;
            	if (!graph.containsEdge(i, node)) {
            		double now = pr.get(i) / (double)graph.neighbors(i).size();
            		if (max == -1) {max = i; maxans = now;}
            		else if (maxans - now < 1e-9) {
            			max = i;
            			maxans = now;
            		}
            	}
        	}
        	if (max < 0) return;
    		graph.addEdge(max, node);
        }
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
    	PageRankAlgorithm algo = new PowerMethod();
        PageRank pr = algo.compute(graph);
        int max = -1;
        int loops = 0;
        /*for (loops = 0; loops < 300; ++loops) {
        	max = node;
        	for (int i = 0; i < graph.size(); ++i) {
    			if ((node != i) && (!graph.containsEdge(i, node))) {
            		if (pr.get(max) - pr.get(i) < 1e-9) {
            			max = i;
            		}
            	}
    			else if (node == i && pr.get(max) - pr.get(i) < 1e-9)
    				max = i;
        	}
        	if (max == node) break;
    		graph.addEdge(max, node);
        }*/
        ArrayList<Integer> addlists = new ArrayList<Integer>();
        addlists.clear();
        double maxans;
        for (; loops < 300; ++loops) {
        	max = -1;
        	
        	for (int i = 0; i < graph.size(); ++i) {
    			if (i == node) continue;
            	if ((!graph.containsEdge(i, node))) { // || (!graph.containsEdge(node, i)
            		if (max == -1) max = i;
            		
            		else if (pr.get(max) - pr.get(i) < 1e-9) {
            			max = i;
            		}
            	}
        	}
        	if (!graph.containsEdge(max, node)) {
        		graph.addEdge(max, node);
        		loops ++;
        	}
        	/*if (!graph.containsEdge(node, max)) {
        		graph.addEdge(node, max);
        		loops ++;
        	}*/
        	/*for (int i = 0; i < addlists.size(); ++i) {
        		if (!graph.containsEdge(addlists.get(i), max)) {
        			graph.addEdge(addlists.get(i), max);
        			loops ++;
        		}
        		/*if (!graph.containsEdge(max, addlists.get(i))) {
        			graph.addEdge(max, addlists.get(i));
        			loops ++;
        		}*/
        	//}
        	addlists.add(max);
        	loops --;
        }
    }


    public static void main(String[] args) throws Exception {
        Graph graph = Graph.fromFile(Graph.WIKIPEDIA_PATH);
        addIncomingEdges(graph, HISTORY_OF_MATHS);
        //addEdges(graph, HISTORY_OF_MATHS);

        PageRank pr = new PowerMethod().compute(graph);
        System.out.println(String.format("You're at %.2f%% of our best pagerank!",
                100 * pr.get(HISTORY_OF_MATHS) / GREEDY_PR));
    }

}
