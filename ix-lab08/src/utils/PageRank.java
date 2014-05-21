package utils;

import static org.apache.hadoop.thirdparty.guava.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.thirdparty.guava.common.collect.TreeMultimap;

/**
 * Helper class that manipulates PageRank results. Useful to sort the nodes by
 * descending PR values and display the results.
 */
public class PageRank {

    private static final int DEFAULT_NB_ELEMENTS = 50;
    private static final int ALL_ELEMENTS = -1;

    private final Graph graph;
    private final double[] pr;
    private final List<Integer> sortedNodes;


    /**
     * Initializes an instance with a graph and the PageRank values associated to each
     * of its node (need to be computed beforehand!).
     *
     * @param graph the Graph instance on which the PageRank was computed
     * @param pr the PageRank of each of the graph's nodes
     */
    public PageRank(Graph graph, double[] pr) {
        checkArgument(graph.size() == pr.length, "size mismatch");
        this.graph = graph;
        this.pr = pr;
        this.sortedNodes = new ArrayList<Integer>();

        // Use a TreeMultimap to iterate over sorted keys.
        TreeMultimap<Double, Integer> map = TreeMultimap.create();
        for (int i = 0; i < pr.length; ++i) {
            // Small hack: take the negative PR (reverses the natural order).
            map.put(-pr[i], i);
        }
        for (Map.Entry<Double, Integer> entry : map.entries()) {
            this.sortedNodes.add(entry.getValue());
        }
    }


    /** Returns the graph for which the PageRank was computed. */
    public Graph getGraph() {
        return this.graph;
    }


    /** Returns a particular node's page rank */
    public double get(int idx) {
        return this.pr[idx];
    }


    /** Formats and displays the nodes with the highest page rank. */
    public void printTop() {
        this.print(DEFAULT_NB_ELEMENTS);
    }


    /** Formats and displays all the nodes by decreasing order of page rank. */
    public void printAll() {
        this.print(ALL_ELEMENTS);
    }


    private void print(int number) {
        int counter = 1;
        for (int idx : this.sortedNodes) {
            if (number != ALL_ELEMENTS && counter > number) { break; }
            System.out.println(String.format("%-4d %s (PR: %.5f%%)",
                    counter, this.graph.getName(idx), this.pr[idx] * 100));
            ++counter;
        }
    }
}
