package utils;

import static org.apache.hadoop.thirdparty.guava.common.base.Preconditions.checkArgument;
import static org.apache.hadoop.thirdparty.guava.common.base.Preconditions.checkElementIndex;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.thirdparty.guava.common.base.Charsets;
import org.apache.hadoop.thirdparty.guava.common.collect.Lists;
import org.apache.hadoop.thirdparty.guava.common.io.Files;


/**
 * Simple data structure for directed graphs. The N nodes are numbered
 * from 0 to N-1. Node IDs are also mapped to a (human-readable) string.
 */
public class Graph {

    /** Path to Wikipedia for Schools hyperlink graph. */
    public static final String WIKIPEDIA_PATH = "data/wikipedia.graph";

    private final List<String> names;
    private final List<List<Integer>> adjacencies;


    /** Constructor is private - use the static helpers to get an instance. */
    private Graph(List<String> names, List<List<Integer>> adjacencies) {
        checkArgument(names.size() == adjacencies.size(),
                "dimension mismatch between nodes and adjacencies");
        this.names = names;
        this.adjacencies = adjacencies;
    }


    /**
     * Returns the list of neighbors (outgoing links) of a given node. The list
     * is read-only, use addEdge() if you need to add an edge.
     *
     * @param node the node for which we want the neighbors
     * @return an unmodifiable list with the node's neighbor
     */
    public List<Integer> neighbors(int node) {
        checkElementIndex(node, names.size(), "node is out of bounds: " + node);
        return Collections.unmodifiableList(this.adjacencies.get(node));
    }


    /**
     * Adds a directed edge between a source and a destination. Fails if the edge
     * already exists.
     *
     * @param src source of the edge
     * @param dst target of the edge
     */
    public void addEdge(int src, int dst) {
        checkElementIndex(src, names.size(), "src is out of bounds: " + src);
        checkElementIndex(dst, names.size(), "dst is out of bounds: " + dst);
        checkArgument(src != dst, "self-loops not allowed (src == dst)");
        if (this.adjacencies.get(src).contains(dst)) {
            throw new IllegalArgumentException("edge already exists");
        }
        this.adjacencies.get(src).add(dst);
    }


    /**
     * Checks whether a (directed) edge already exists, given a (source,
     * destination) pair.
     *
     * @param src source of the edge
     * @param dst destination of the edge
     * @return true if the edge exists in the graph, false otherwise
     */
    public boolean containsEdge(int src, int dst) {
        checkElementIndex(src, names.size(), "src is out of bounds: " + src);
        checkElementIndex(dst, names.size(), "dst is out of bounds: " + dst);
        checkArgument(src != dst, "self-loops not allowed (src == dst)");
        return this.adjacencies.get(src).contains(dst);
    }


    /**
     * Returns the (human-readable) name associated with the given node.
     *
     * @param node the node ID (between 0 and N-1)
     * @return the name of the node, as a string.
     */
    public String getName(int node) {
        checkElementIndex(node, names.size(), "node is out of bounds: " + node);
        return this.names.get(node);
    }


    /** Returns the number of nodes in the graph. */
    public int size() {
        return this.names.size();
    }


    /**
     * Generates a Graph instance from a sequence of lines (typically read from a
     * file). The format is as follows:
     *
     * - zero-indexed line number indicates node ID (first line corresponds to
     *   ID = 0, etc.).
     * - on each line, a tab ("\t") delimits the human-readable name of the node
     *   and the list of neighbors.
     * - neighbors (outgoing-edges) are represented as IDs separated by spaces.
     *
     *
     * Here's an example for a graph with 3 nodes and the following set of edges:
     * {(0,1), (1,2), (2,0), (0,2)}
     *
     *     first node   1 2
     *     second node  2
     *     third node   0
     *
     * @param lines the sequence of lines to parse to get the graph
     * @return a Graph instance
     */
    public static Graph fromLines(List<String> lines) {
        List<String> names = new ArrayList<String>();
        List<List<Integer>> adjacencies = new ArrayList<List<Integer>>();

        String[] tokens;
        for (String line : lines) {
            tokens = line.split("\t", 2);
            names.add(tokens[0]);
            if (tokens[1].length() > 0) {
                tokens = tokens[1].split(" ");
                List<Integer> neighbors = new ArrayList<Integer>();
                for (String token : tokens) {
                    neighbors.add(Integer.parseInt(token));
                }
                adjacencies.add(neighbors);
            } else {
                adjacencies.add(new ArrayList<Integer>());
            }
        }
        return new Graph(names, adjacencies);
    }


    /**
     * Reads a graph from a file. See fromLines() for more information about the
     * foramt.
     *
     * @param path path to the file containing the graph description
     * @return a Graph instance
     */
    public static Graph fromFile(String path) throws IOException {
        List<String> lines = Files.readLines(new File(path), Charsets.UTF_8);
        return fromLines(lines);
    }


    /** Simple graph generator for directed cycles. */
    public static Graph cycle(int n) {
        List<String> names = new ArrayList<String>();
        List<List<Integer>> adjacencies = new ArrayList<List<Integer>>();
        for (int i = 0; i < n; ++i) {
            names.add(String.format("%d", i));
            adjacencies.add(Lists.newArrayList(new Integer((i + 1) % n)));
        }
        return new Graph(names, adjacencies);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.size(); ++i) {
            builder.append(i + "\t");
            for (int n : this.neighbors(i)) {
                builder.append(n + " ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

}
