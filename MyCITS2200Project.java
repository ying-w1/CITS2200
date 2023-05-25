import java.util.*;

/**
 * A class ... subset of a graph of wikipedia pages.
 * Vertices are represented as the URL of the page.
 * Edges are represented as teh hyperlinks between pages.
 * The graph is directed and unweighted.
 * 
 * The Class implements the CITS2200Project interface.
 * Authors: Ying Wang (22863462) and Scott Castledine (23083278)
 */
public class MyCITS2200Project {

    // Declare class field variables
    private Map<String, List<String>> graph;
    private Map<String, List<String>> transGraph;
    private ArrayList<String> urlArray;
    private HashMap<String, Integer> urlKey;

    /**
     * Constructor for MyCITS2200Project, Initializes class field variables
     * Graph is an adjacency list represented as a HashMap
     * TransGraph is the transpose of the graph
     * urlArray is an array of URL vertices
     * urlKey is a HashMap of URL (key) to index (value) in urlArray
     */
    public MyCITS2200Project() {
        graph = new HashMap<String, List<String>>();
        transGraph = new HashMap<String, List<String>>();
        urlArray = new ArrayList<String>();
        urlKey = new HashMap<String, Integer>();
    }

    /**
     * A method to add a vertex to the graph
     * Adds a directed edge between two vertices from urlFrom to urlTo
     * calls addVert method to add vertices to the graph
     * 
     * @param urlFrom
     * @param urlTo
     */
    public void addEdge(String urlFrom, String urlTo) {
        // Add the edge to our map & array
        addVert(urlFrom);
        addVert(urlTo);

        // Create the edge in normal graph
        addToGraph(urlFrom, urlTo);

        // Create the edge in transposed graph
        addToTransGraph(urlTo, urlFrom);
    }

    /**
     * A method to add a vertex to the graph
     * Checks if vertex exists in the map of URL to ID
     * If not already in array, adds vertex to array & map
     */
    public void addVert(String v) {
        // Check if the edge exists in the map from URL to ID
        if (!urlKey.containsKey(v)) {
            urlArray.add(v);
            int index = urlArray.indexOf(v);
            urlKey.put(v, index);
        }

    }

    /**
     * A method to add a vertex to the graph
     * Adds a directed edge between two vertices from urlFrom to urlTo
     * checks if both vertices are already in the graph
     * creates a new LinkedList for any vertex not already in the graph
     * 
     * @param from
     * @param to
     */
    public void addToGraph(String from, String to) {
        if (!graph.containsKey(from)) {
            graph.put(from, new LinkedList<>());
        }
        if (!graph.containsKey(to)) {
            graph.put(to, new LinkedList<>());
        }
        graph.get(from).add(to);
    }

    /**
     * A method to add a vertex to the transposed graph
     * Adds a directed edge between two vertices from urlFrom to urlTo
     * checks if both vertices are already in the graph
     * creates a new LinkedList for any vertex not already in the graph
     * 
     * @param from
     * @param to
     */
    public void addToTransGraph(String from, String to) {

        if (!transGraph.containsKey(from)) {
            transGraph.put(from, new LinkedList<>());
        }
        if (!transGraph.containsKey(to)) {
            transGraph.put(to, new LinkedList<>());
        }
        transGraph.get(from).add(to);
    }

    /**
     * A method to find the shortest path between two vertices
     * Finds the shortest path in number of links between two pages.
     * Returns the length of the shortest path in number of links followed.
     * If there is no path, returns -1.
     * 
     * @param urlFrom the URL where the path should start.
     * @param urlTo   the URL where the path should end.
     * @return the length of the shortest path in number of links followed.
     */
    public int getShortestPath(String urlFrom, String urlTo) {
        // Create a queue for BFS traversal
        Queue<String> queue = new LinkedList<>();
        // Create a visited array to keep track of visited vertices
        boolean[] visited = new boolean[urlArray.size()];
        // Create a distance array to store the shortest distance from the starting
        // vertex
        int[] distance = new int[urlArray.size()];
        // Initialize distance array with -1 (indicating unreachable vertices)
        Arrays.fill(distance, -1);
        // Get the starting vertex index
        int fromIndex = urlKey.get(urlFrom);
        // Get the destination vertex index
        int toIndex = urlKey.get(urlTo);

        // Enqueue the starting vertex
        queue.add(urlFrom);
        // Mark the starting vertex as visited
        visited[fromIndex] = true;
        // Set the distance of the starting vertex to 0
        distance[fromIndex] = 0;

        // Perform BFS traversal
        while (!queue.isEmpty()) {
            // Dequeue a vertex
            String currentUrl = queue.poll();
            int currentIndex = urlKey.get(currentUrl);

            // If the destination vertex is reached, return the shortest distance
            if (currentIndex == toIndex) {
                return distance[currentIndex];
            }

            // Iterate over the neighbours of the current vertex
            List<String> neighbours = graph.get(currentUrl);
            for (String neighbourUrl : neighbours) {
                int neighbourIndex = urlKey.get(neighbourUrl);

                // If the neighbour vertex is not visited
                if (!visited[neighbourIndex]) {
                    // Mark the neighbour vertex as visited
                    visited[neighbourIndex] = true;
                    // Update the distance of the neighbour vertex
                    distance[neighbourIndex] = distance[currentIndex] + 1;
                    // Enqueue the neighbour vertex
                    queue.add(neighbourUrl);
                }
            }
        }

        // If the destination vertex is not reachable, return -1
        return -1;
    }

    /**
     * A method to find any Hamiltonian path in the graph
     * There may be many possible Hamiltonian paths.
     * This method should never be called on a graph with more than 20 vertices.
     * If there is no Hamiltonian path, this method will return an empty array.
     * If a path is found, the method returns an array containing the URLs of pages
     * The URL in the Hamiltonian path found are in sequential order from beginning
     * 
     * @return an array of a Hamiltonian path of the page graph, or empty.
     */
    public String[] getHamiltonianPath() {

        // Number of vertices
        int numURLs = urlArray.size();

        String dp[][] = new String[numURLs][(1 << numURLs)];
        // Set all dp[i][(1 << i)] to corresponding vertices
        for (int i = 0; i < numURLs; i++) {
            dp[i][(1 << i)] = String.valueOf(i);
        }

        // Iterate subset of vertices
        for (int i = 0; i < (1 << numURLs); i++) {
            for (int j = 0; j < numURLs; j++) {

                // If j-th vertex is in current subset
                if ((i & (1 << j)) != 0) {

                    // neighbor k present in current subset
                    for (int k = 0; k < numURLs; k++) {
                        if ((i & (1 << k)) != 0 &&
                                graph.get(urlArray.get(k)).contains(urlArray.get(j)) && j != k &&
                                dp[k][i ^ (1 << j)] != null) {
                            // include the current vertex in dp[j][i]
                            dp[j][i] = dp[k][i ^ (1 << j)] + " " + j;
                            break;
                        }
                    }
                }
            }
        }

        // iterate vertices
        for (int i = 0; i < numURLs; i++) {

            // found Hamiltonian path
            if (dp[i][(1 << numURLs) - 1] != null) {
                // Split the string to get an array of vertices
                String[] path = dp[i][(1 << numURLs) - 1].split(" ");
                for (int j = 0; j < path.length; j++) {
                    // Convert the vertices to URLs
                    path[j] = urlArray.get(Integer.parseInt(path[j]));
                }
                return path;
            }
        }

        // no path found, empty array
        String[] path = new String[0];
        return path;
    }

    /**
     * A method to find all the centers of the page graph.
     * The output is not ordered.
     * 
     * @return an array containing all the URLs that correspond to pages that are
     *         centers.
     */
    public String[] getCenters() {
        List<String> centers = new ArrayList<>();
        Set<String> allVertices = graph.keySet();
        int minEccentricity = Integer.MAX_VALUE;

        // Compute eccentricity of each vertex (shortest path from one vertex to every
        // other)
        // by calling the getShortestPath() method
        for (String vertex : allVertices) {
            int eccentricity = vertexEccentricity(vertex, allVertices);
            if (eccentricity < minEccentricity) {
                minEccentricity = eccentricity;
            }
        }

        // Compute the eccentricity for each of the vertices
        // If the eccentricity for the vertex = minEccentricity (the radius)
        // That vertex is a central vertex and is to be added to centers
        for (String vertex : allVertices) {
            int eccentricity = vertexEccentricity(vertex, allVertices);
            if (eccentricity == minEccentricity) {
                centers.add(vertex);
            }
        }

        // Add all centers into the result array and return the array
        String[] result = new String[centers.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = centers.get(i);
        }
        // Return an empty array if no centers are found
        if (centers.isEmpty()) {
            return new String[0];
        }
        return result;
    }

    /**
     * A helper method to compute the eccentricity of a vertex
     * called in getCenters() method
     * 
     * @param vertex
     * @param allVertices
     * @return shortest path with the greatest length
     */
    public int vertexEccentricity(String vertex, Set<String> allVertices) {
        int maxShortestPath = 0;

        for (String v : allVertices) {
            int shortestPath = getShortestPath(vertex, v);
            if (shortestPath > maxShortestPath) {
                maxShortestPath = shortestPath;
            }
        }

        return maxShortestPath;
    }

    /**
     * A method to find the strongly connected components of the page graph.
     * The output is a list of arrays of URLs.
     * 
     * @return
     */
    public String[][] getStronglyConnectedComponents() {
        // Set up necessary variables
        boolean[] visited = new boolean[urlArray.size()];
        Stack<String> stack = new Stack<>();
        String[][] result;
        Arrays.fill(visited, false);

        // Perform the first depth-first search (DFS)
        for (String url : graph.keySet()) {
            int urlID = urlKey.get(url);
            if (!visited[urlID]) { // Check if visited
                firstDFS(url, stack, visited);
            }
        }

        // Reset the visited array in preparation for the second DFS
        Arrays.fill(visited, false);

        // Perform the second depth-first search on the transposed graph
        // Get strongly connected components
        List<List<Integer>> sccList = new ArrayList<>();
        while (!stack.isEmpty()) {
            String current = stack.pop();
            int currentID = urlKey.get(current);
            if (!visited[currentID]) {
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

    /**
     * A helper method to perform the first depth-first search (DFS)
     * called in getStronglyConnectedComponents() method
     * 
     * @param vertex
     * @param stack
     * @param visited
     */
    public void firstDFS(String vertex, Stack<String> stack, boolean[] visited) {
        visited[urlKey.get(vertex)] = true;
        List<String> neighbours = graph.get(vertex);

        if (neighbours != null) {
            for (String neighbour : neighbours) {
                int neighbourID = urlKey.get(neighbour);
                if (!visited[neighbourID]) {
                    firstDFS(neighbour, stack, visited);
                }
            }
        }
        stack.push(vertex);
    }

    /**
     * A helper method to perform the second depth-first search (DFS)
     * called in getStronglyConnectedComponents() method
     * 
     * @param vertex
     * @param visited
     * @param scc
     */
    public void secondDFS(String vertex, boolean[] visited, List<Integer> scc) {
        visited[urlKey.get(vertex)] = true;
        scc.add(urlKey.get(vertex));
        List<String> neighbours = transGraph.get(vertex);

        if (neighbours != null) {
            for (String neighbour : neighbours) {
                int neighbourID = urlKey.get(neighbour);
                if (!visited[neighbourID]) {
                    secondDFS(neighbour, visited, scc);
                }
            }
        }
    }

    public static void main(String[] args) {

    }
}
