package ix.lab08.pagerank;

import java.util.List;
import java.util.Random;

import utils.Graph;
import utils.PageRank;


/**
 * Intuitive implementation of PageRank. The analogy is that of a surfer that wanders
 * through the Web by clicking on random links.
 */
public class NaiveRandomSurfer implements PageRankAlgorithm {

    private static final int NB_ITERATIONS = 1000000;

    @Override
    public PageRank compute(Graph graph) {
        int nbNodes = graph.size();
        double[] probabilities = new double[nbNodes];
        
        // TODO Implement the naive random surfer model for PageRank.
        Random rand = new Random();
        int nownode = rand.nextInt(nbNodes);
        for (int loops = 0; loops < NB_ITERATIONS; ++loops) {
        	List<Integer> nei = graph.neighbors(nownode);
        	int mount = nei.size();
        	//if (mount >= 1) {
        		int next = rand.nextInt(mount);
        		next = (Integer) nei.get(next);
        		probabilities[next] += 1;
        		nownode = next;
        	/*} else {
        		nownode = rand.nextInt(nbNodes);
        		probabilities[nownode] += 1;
        	}*/
        }
        for (int i = 0; i < nbNodes; ++i)
        	probabilities[i] = (double)probabilities[i] / NB_ITERATIONS;
        return new PageRank(graph, probabilities);
    }


    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println(String.format(
                    "Usage: %s <graph>", RandomSurfer.class.getName()));
            return;
        }
        Graph graph = Graph.fromFile(args[0]);
        PageRank pr = new NaiveRandomSurfer().compute(graph);
        pr.printAll();
    }

}
