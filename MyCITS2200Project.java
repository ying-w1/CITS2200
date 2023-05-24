import java.util.*;

public class MyCITS2200Project {

    // field variable instantiation
    private Map<String, List<String>> graph;
    private Map<String, List<String>> transGraph; // Reverse direction of adj for Kosaraju's algorithm in
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
        graph = new HashMap<String, List<String>>();
        transGraph = new HashMap<String, List<String>>();
        urlArray = new ArrayList<String>();
        urlKey = new HashMap<String, Integer>();
    }

    /**
     * Check if vertex exist in the graph
     * If not in graph, add to graph
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

    private void addVert(String v) {
        // Check if the edge exists in the map from URL to ID
        if (!urlKey.containsKey(v)) {
            urlArray.add(v);
            int index = urlArray.indexOf(v);
            urlKey.put(v, index);
        }

    }

    private void addToGraph(String from, String to) {
        if (!graph.containsKey(from)) {
            graph.put(from, new LinkedList<>());
        }
        if (!graph.containsKey(to)) {
            graph.put(to, new LinkedList<>());
        }
        graph.get(from).add(to);
    }

    private void addToTransGraph(String from, String to) {

        if (!transGraph.containsKey(from)) {
            transGraph.put(from, new LinkedList<>());
        }
        if (!transGraph.containsKey(to)) {
            transGraph.put(to, new LinkedList<>());
        }
        transGraph.get(from).add(to);
    }

    /**
     * Finds the shortest path in number of links between two pages.
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
     * Finds all the centers of the page graph. The order of pages
     * in the output does not matter. Any order is correct as long as
     * all the centers are in the array, and no pages that aren't centers
     * are in the array.
     * 
     * @return an array containing all the URLs that correspond to pages that are
     *         centers.
     */
    public String[] getCenters() {
        List<String> centers = new ArrayList<>();
        Set<String> vertices = graph.keySet();
        int minimumEccentricity = Integer.MAX_VALUE;

        for (String vertex : vertices) {
            int eccentricity = 0;
            Set<String> visited = new HashSet<>();
            Queue<String> queue = new LinkedList<>();
            queue.offer(vertex);
            visited.add(vertex);

            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    String current = queue.poll();
                    for (String neighbour : graph.get(current)) {
                        if (!visited.contains(neighbour)) {
                            queue.offer(neighbour);
                            visited.add(neighbour);
                        }
                    }
                }
                eccentricity++;
            }

            if (eccentricity < minimumEccentricity) {
                minimumEccentricity = eccentricity;
                centers.clear();
                centers.add(vertex);
            } else if (eccentricity == minimumEccentricity) {
                centers.add(vertex);
            }
        }

        return centers.toArray(new String[0]);
    }

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

    private void firstDFS(String vertex, Stack<String> stack, boolean[] visited) {
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

    private void secondDFS(String vertex, boolean[] visited, List<Integer> scc) {
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
        MyCITS2200Project project = new MyCITS2200Project();

        project.addEdge("urlA", "urlE");
        project.addEdge("urlE", "urlC");
        project.addEdge("urlE", "urlB");
        project.addEdge("urlC", "urlD");
        project.addEdge("urlD", "urlB");
        // project.addEdge("urlB", "urlD");

        String[] s = project.getHamiltonianPath();
        for (String url : s) {
            System.out.println(url);
        }

    }
}
