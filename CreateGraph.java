import java.util.*;
import java.io.*;

public class CreateGraph {
    // field variable
    private HashMap<String, List<String>> graph;

    /**
     * Class constructor for createGraph
     */
    public CreateGraph() {
        graph = new HashMap<String, List<String>>();
    }

    /**
     * copy of addEdge
     */
    public void addEdge(String urlFrom, String urlTo) {
        if (!graph.containsKey(urlFrom)) {
            graph.put(urlFrom, new ArrayList<>());
        }
        // get element with associated key, add urlTo to the list
        graph.get(urlFrom).add(urlTo);
    }

    /**
     * Method to create test graphs with random edges
     * 
     * @param numNodes number of nodes in the graph
     * @param numEdges number of edges in the graph
     */
    public void createTestGraph(int numNodes, int numEdges) {
        Random random = new Random();
        List<String> nodes = new ArrayList<>();

        // Generate nodes
        for (int i = 0; i < numNodes; i++) {
            String node = String.valueOf((char) ('A' + i));
            nodes.add(node);
        }

        // clear graph before adding edges
        graph.clear();

        // Generate random edges
        for (int i = 0; i < numEdges; i++) {
            int fromIndex = random.nextInt(numNodes);
            int toIndex = random.nextInt(numNodes);
            String from = nodes.get(fromIndex);
            String to = nodes.get(toIndex);
            addEdge(from, to);
        }
    }

    /**
     * Prints the adjacency matrix representation of the graph.
     * Not part of the assignment, but useful to see the adjacency matrix
     * 
     */
    public void printAdjacencyMatrix() {
        int numNodes = graph.size();
        int[][] adjacencyMatrix = new int[numNodes][numNodes];
        HashMap<String, Integer> vertexIndices = new HashMap<>();
        List<String> nodes = new ArrayList<>(graph.keySet());

        // Populate vertex indices
        for (int i = 0; i < numNodes; i++) {
            vertexIndices.put(nodes.get(i), i);
        }

        // Populate adjacency matrix
        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            String from = entry.getKey();
            Integer fromIndex = vertexIndices.get(from);
            if (fromIndex == null) {
                continue;
            }
            List<String> edges = entry.getValue();
            for (String edge : edges) {
                Integer toIndex = vertexIndices.get(edge);
                if (toIndex != null) {
                    adjacencyMatrix[fromIndex][toIndex] = 1;
                }
            }
        }

        // Print adjacency matrix
        System.out.println("Adjacency Matrix:");
        for (int[] row : adjacencyMatrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // class instance
        CreateGraph tester = new CreateGraph();
        // create test graph
        tester.createTestGraph(10, 10);
        System.out.println(tester.graph);

        // Print the generated graph
        for (Map.Entry<String, List<String>> entry : tester.graph.entrySet()) {
            String from = entry.getKey();
            List<String> toList = entry.getValue();
            System.out.print(from + " -> ");
            for (String to : toList) {
                System.out.print(to + " ");
            }
            System.out.println();
        }
        // print adjacency matrix
        tester.printAdjacencyMatrix();
    }
}
