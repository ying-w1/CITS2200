import java.io.*;
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
            graph.put(idURLFrom, temp);
        }
    }

    private void addToTransGraph(String from, String to) {
        int idURLFrom = urlKey.get(from);
        int idURLTo = urlKey.get(to);

        if (!transGraph.containsKey(idURLFrom)) {
            List<Integer> temp2 = new LinkedList<>();
            temp2.add(idURLTo);
            transGraph.put(idURLFrom, temp2);
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
        // Set up necesary variables
        boolean[] visited = new boolean[urlArray.size()];
        Stack<Integer> stack = new Stack<Integer>();
        String[][] result;
        Arrays.fill(visited, Boolean.FALSE);

        // Implement the Kosaraju-Shamir algorithm
        // Perform the first depth-first search (DFS)
        for (int urlID : graph.keySet()) {
            if(!visited[urlID]){
                firstDFS(urlID,stack,visited);
            }
        }

        // Reset the visited array in preparation for the second DFS
        Arrays.fill(visited, Boolean.FALSE);

        // Perform the second depth first search on the transposed graph
        // Get strongly connected components
        List<List<Integer>> sccList = new ArrayList<>();
        while (!stack.isEmpty()){
            int current = stack.pop();
            if (!visited[current]) {
                List<Integer> scc = new ArrayList<>();
                secondDFS(current, visited, scc);
                sccList.add(scc);
            }
        }

        // Convert List of strongly connected components to String[][]
        result = new String[sccList.size()][];
        for (int i = 0; i < sccList.size(); i++) {
            List<Integer> scc = sccList.get(i);
            result[i] = new String[scc.size()];
            for (int j = 0; j < scc.size(); j++) {
                int urlID = scc.get(j);
                result[i][j] = urlArray.get(urlID);
                }
            }
        return result;
    }

    private void firstDFS(int vertex, Stack<Integer> stack, boolean[] visited) {
        visited[vertex] = true;
        List<Integer> neighbours = graph.get(vertex);

        if (neighbours != null){
            for (int i:neighbours){
                if(!visited[i]){
                    firstDFS(i,stack,visited);
                }
            }
        }
        stack.push(vertex);
    }

    private void secondDFS(int vertex, boolean[] visited, List<Integer> scc) {
        visited[vertex] = true;
        scc.add(vertex);
        List<Integer> neighbours = transGraph.get(vertex);
    
        if (neighbours != null) {
            for (int i : neighbours) {
                if (!visited[i]) {
                    secondDFS(i, visited, scc);
                }
            }
        }
    }
}
