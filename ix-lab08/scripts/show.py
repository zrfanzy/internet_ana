#!/usr/bin/env python
import matplotlib.pyplot as plt
import networkx as nx
import sys


def parse_file(path):
    graph = nx.DiGraph()
    names = list()
    with open(path) as f:
        for i, line in enumerate(f):
            name, neighbors = line.strip('\n').split('\t', 1)
            names.append(name)
            for n in neighbors.split():
                graph.add_edge(i, int(n))
    return graph, names


def main(path):
    graph, names = parse_file(path)
    labels = dict(enumerate(names))
    nx.draw(graph, labels=labels, alpha=0.9, node_color='w', node_size=800)
    plt.show()


if __name__ == '__main__':
    if len(sys.argv) != 2:
        print "Usage: %s path/to/graph" % sys.argv[0]
    main(sys.argv[1])
