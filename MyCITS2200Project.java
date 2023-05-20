import java.util.*;

public class MyCITS2200Project {

    // field variable instantiation
    private int numVertices; // number of vertices
    private Map<Integer, List<Integer>> graph;
    private Map<Integer, List<Integer>> transGraph; // Reverse direction of adj for Kosaraju's algorithm in
                                                    // stronglyConnectedComponent()
    private ArrayList<String> urlArray; // Array of URL (vertices)
    private HashMap<String, Integer> urlKey; // Key = URL, value = index in urlArray
    /*
     * key (URL) : values (index)
     * /wiki.com/page : 1
     * /wiki.com/page : 2
     * /wiki.com/page : 3
     * etc..
     */

    // stuff that needed instantiating for use in multiple methods
    private ArrayList<Integer> visited;
    private int[][] adj;

    /**
     * Constructor for MyCITS2200Project
     */
    public MyCITS2200Project() {
        numVertices = 4; // temporary as an example, may not need
        graph = new HashMap<Integer, List<Integer>>();
        transGraph = new HashMap<Integer, List<Integer>>();
        urlArray = new ArrayList<String>();
        urlKey = new HashMap<String, Integer>();
    }

    /* ToDo javadocs */
    public void addEdge(String urlFrom, String urlTo) {
        // Add the edge to the graph assuming both vertices are not in the graph
        addVertex(urlFrom);
        addVertex(urlTo);

        // Create the edge in normal graph
        addToGraph(urlFrom, urlTo);

        // Create the edge in transposed graph
        addToTransGraph(urlTo, urlFrom);
    }

    private void addVertex(String v) {
        /**
         * Check if vertix exist in the graph
         * If not in graph, add to graph
         */

        // Check if the edge exists in the map from URL to ID
        if (!urlKey.containsKey(v)) {
            urlArray.add(v);
            int index = urlArray.indexOf(v);
            urlKey.put(v, index);
        }

    }

    private void addToGraph(String from, String to) {
        int idURLFrom = urlKey.get(from);
        int idURLTo = urlKey.get(to);

        if (!graph.containsKey(idURLFrom)) {
            List<Integer> temp = new LinkedList<>();
            temp.add(idURLTo);
            graph.put(idURLTo, temp);
        }
    }

    private void addToTransGraph(String from, String to) {
        int idURLFrom = urlKey.get(from);
        int idURLTo = urlKey.get(to);

        if (!transGraph.containsKey(idURLFrom)) {
            List<Integer> temp2 = new LinkedList<>();
            temp2.add(idURLTo);
            graph.put(idURLTo, temp2);
        }
    }

    /**
     * Finds the shorest path in number of links between two pages.
     * If there is no path, returns -1.
     * 
     * @param urlFrom the URL where the path should start.
     * @param urlTo   the URL where the path should end.
     * @return the legnth of the shorest path in number of links followed.
     */
    public int getShortestPath(String urlFrom, String urlTo) {
        // Implement the shortest path algorithm
        return -1;
    }

    /**
     * Finds a Hamiltonian path in the page graph. There may be many
     * possible Hamiltonian paths. Any of these paths is a correct output.
     * This method should never be called on a graph with more than 20
     * vertices. If there is no Hamiltonian path, this method will
     * return an empty array. The output array should contain the URLs of pages
     * in a Hamiltonian path. The order matters, as the elements of the
     * array represent this path in sequence. So the element [0] is the start
     * of the path, and [1] is the next page, and so on.
     * 
     * @return a Hamiltonian path of the page graph.
     */
    public String[] getHamiltonianPath() {
        // placeholder so theres no error
        String[] result = new String[numVertices];
        return result;
    }

    /**
     * Finds all the centers of the page graph. The order of pages
     * in the output does not matter. Any order is correct as long as
     * all the centers are in the array, and no pages that aren't centers
     * are in the array.
     * 
     * @return an array containing all the URLs that correspond to pages that are
     *         centers.
     */
    public String[] getCenters() {
        // Implement the algorithm to find the centers

        // 4 is a placeholder TODO: fix
        String[] result = new String[numVertices];

        return result;
    }

    public String[][] getStronglyConnectedComponents() {
        // Set up necessary variables
        ArrayList<Integer> visited = new ArrayList<>();
        Stack<Integer> stack = new Stack<Integer>();
        String[][] result = new String[adj.length][adj.length];
        int[][] transposedAdj = new int[adj.length][adj.length];

        // Implement the Kosaraju-Shamir algorithm
        // Perform the first depth-first search (DFS)
        for (int i = 0; i < numVertices; i++) {
            // if (!visited.contains(i)) { // cant use contains(), might have to loop array
            // manually
            // dfsFirst(i, stack);
            // }
        }
        // Reverse the direction of the graph
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < adj[i].length; j++) {
                // transposedAdj = addEdge(adj[i].get(j), i); //cant use get()
            }
        }

        // Perform the second pass of BFS in the reversed order
        // Get strongly connected vertices

        return result;
    }

    // TO DO: ask if it matters if we use recursion/loop ??? in terms of efficiency
    private void dfsFirst(int vertex, Stack<Integer> stack) {
        visited.add(vertex); // boolean type so you cant add int to array
        stack.push(vertex);
        while (!stack.isEmpty()) {
            int currentVertex = stack.pop();
            System.out.println(currentVertex); // Process or visit the current vertex

            // LinkedList<Integer> neighbors = adj[currentVertex]; // needs to be adj[][]
            // for (int neighbor : neighbors) {
            // if (!visited.contains(neighbor)) {
            // visited.add(neighbor);
            // stack.push(neighbor);
            // }
            // }
        }
    }

    private void dfsSecond(String vertex, ArrayList<Integer> visited, Stack<Integer> stack) {
        // TO DO: method for a depth first search

    }

    public static void main(String args[]) {
        // test adjacency list

    }
}