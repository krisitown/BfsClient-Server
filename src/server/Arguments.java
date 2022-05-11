package server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Arguments {
    private int numberOfThreads;
    private char startingNode;
    private Map<Character, List<Character>> graph;

    public Arguments() {
        graph = new HashMap<>();
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public char getStartingNode() {
        return startingNode;
    }

    public void setStartingNode(char startingNode) {
        this.startingNode = startingNode;
    }

    public Map<Character, List<Character>> getGraph() {
        return graph;
    }

    public void setGraph(Map<Character, List<Character>> graph) {
        this.graph = graph;
    }
}
