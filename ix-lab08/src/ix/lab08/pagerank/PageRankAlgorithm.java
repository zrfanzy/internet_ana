package ix.lab08.pagerank;

import utils.Graph;
import utils.PageRank;


/** Interface for all PageRank algorithms. */
public interface PageRankAlgorithm {

    /** Controls the frequency of random restarts. */
    public static final double DAMPING_FACTOR = 0.15;


    /**
     * Computes the PageRank for a given directed graph. The results of the
     * computation are wrapped in a helper object.
     *
     * @param graph the graph for which the PageRank is requested
     * @return a PageRank instance containing the result of the computation
     */
    public PageRank compute(Graph graph);

}
