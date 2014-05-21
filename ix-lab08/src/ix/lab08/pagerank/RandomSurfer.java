package ix.lab08.pagerank;

import java.util.List;
import java.util.Random;

import utils.Graph;
import utils.PageRank;


/**
 * Intuitive implementation of PageRank. The analogy is that of a surfer that wanders
 * through the Web by clicking on random links.
 */
public class RandomSurfer implements PageRankAlgorithm {

    private static final int NB_ITERATIONS = 1000000;

    @Override
    public PageRank compute(Graph graph) {
        int nbNodes = graph.size();
        double[] probabilities = new double[nbNodes];

        // TODO Implement the random surfer model for PageRank.
        // Take care of the issues we had in the naive implementation.

        return new PageRank(graph, probabilities);
    }


    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println(String.format(
                    "Usage: %s <graph>", RandomSurfer.class.getName()));
            return;
        }
        Graph graph = Graph.fromFile(args[0]);
        PageRank pr = new RandomSurfer().compute(graph);
        pr.printAll();
    }

}
