import java.util.*;

public class dynamicHamiltonian {
    // fields
    private Map<Integer, List<Integer>> graph;

    // constructor
    public dynamicHamiltonian() {
        graph = new HashMap<Integer, List<Integer>>();
    }

    public List<Integer> findHamiltonianPath() {
        int n = graph.size(); // Number of vertices
        boolean[][] prev = new boolean[n][n]; // Previous vertex in the path

        // Iterate over all subsets of vertices
        for (int subset = 1; subset < (int) Math.pow(2, n); subset++) {
            for (int i = 0; i < n; i++) {
                if (((subset / (int) Math.pow(2, i)) % 2) == 1) {
                    for (int j : graph.get(i)) {
                        if ((((subset / (int) Math.pow(2, j)) % 2) == 1) && !prev[i][j]) {
                            prev[i][j] = true;
                        }
                    }
                }
            }
        }

        // Find the starting vertex for the Hamiltonian path
        int start = 0;
        int subset = (int) Math.pow(2, n) - 1;
        for (int i = 0; i < n; i++) {
            if (prev[i][subset]) {
                start = i;
                break;
            }
        }

        // Reconstruct the Hamiltonian path
        List<Integer> path = new ArrayList<>();
        subset = (int) Math.pow(2, n) - 1;
        int current = start;
        while (subset != 0) {
            path.add(current);
            for (int i = 0; i < n; i++) {
                if (prev[current][i] && (((subset / (int) Math.pow(2, i)) % 2) == 1)) {
                    subset = subset - (int) Math.pow(2, current);
                    current = i;
                    break;
                }
            }
        }
        path.add(start);

        return path;
    }

    public static void main(String[] args) {
        // Create an adjacency list representing the graph
        List<List<Integer>> graph = new ArrayList<>();
        graph.add(Arrays.asList(1, 2)); // Vertex 0 is connected to vertices 1 and 2
        graph.add(Arrays.asList(0, 2)); // Vertex 1 is connected to vertices 0 and 2
        graph.add(Arrays.asList(0, 1)); // Vertex 2 is connected to vertices 0 and 1

        dynamicHamiltonian dynamicHamiltonian = new dynamicHamiltonian();
        dynamicHamiltonian.graph = graph;

        List<Integer> path = dynamicHamiltonian.findHamiltonianPath();
        if (path.size() == graph.size()) {
            System.out.println("Hamiltonian Path: " + path);
        } else {
            System.out.println("No Hamiltonian Path exists.");
        }
    }
}
