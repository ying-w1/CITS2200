import java.io.*;
import java.util.*;

public class MyCITS2200Project {

    // field variable instantiation
    private int numVertices; // number of vertices
    private Map<Integer, List<Integer>> graph;
    private Map<Integer, List<Integer>> transGraph; // Reverse direction of adj for Kosaraju's algorithm in
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
        int idURLFrom = urlKey.get(from);
        int idURLTo = urlKey.get(to);

        if (!graph.containsKey(idURLFrom)) {
            graph.put(idURLFrom, new LinkedList<>());
        }
        if (!graph.containsKey(idURLTo)) {
            graph.put(idURLTo, new LinkedList<>());
        }
        graph.get(idURLFrom).add(idURLTo);
    }

    private void addToTransGraph(String from, String to) {
        int idURLFrom = urlKey.get(from);
        int idURLTo = urlKey.get(to);

        if (!transGraph.containsKey(idURLFrom)) {
            transGraph.put(idURLFrom, new LinkedList<>());
        }
        if (!transGraph.containsKey(idURLTo)) {
            transGraph.put(idURLTo, new LinkedList<>());
        }
        transGraph.get(idURLFrom).add(idURLTo);
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
        // Implement the shortest path algorithm

        // create queue
        Queue<Integer> queue = new LinkedList<Integer>();
        // create array for distance
        int[] distance = new int[urlArray.size()];
        // set all distances to -1
        Arrays.fill(distance, -1);

        // enqueue the starting vertex
        queue.add(urlKey.get(urlFrom));
        // mark the starting vertex as visited
        distance[urlKey.get(urlFrom)] = 0;

        // while the queue is not empty
        while (!queue.isEmpty()) {
            // dequeue a vertex
            int current = queue.poll();
            if (current == urlKey.get(urlTo)) {
                // found the destination
                return distance[current];
            }
            // for each neighbour in current vertex key:value set
            for (int neighbour : graph.get(current)) {
                // if unvisited neighbour
                if (distance[neighbour] == -1) {
                    distance[neighbour] = distance[current] + 1;
                    // add to queue
                    queue.add(neighbour);
                }
            }
        }
        return distance[urlKey.get(urlTo)];
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
        // number vertices
        int numVertices = urlArray.size();
        // hamiltonianPath
        ArrayList<Integer> hamiltonianPath = new ArrayList<Integer>();
        // visited
        boolean[] visited = new boolean[numVertices];

        // set all vertices as not visited
        Arrays.fill(visited, false);
        // add start to path
        hamiltonianPath.add(0);
        // mark start as visited
        visited[0] = true;

        // while loop
        int position = 1;
        while (position >= 1) {
            int current = hamiltonianPath.get(position - 1);
            boolean found = false;

            // for each neighbour in current vertex key:value set
            for (int next : graph.get(current)) {
                // if unvisited neighbour
                if (!visited[next]) {
                    // add to path
                    hamiltonianPath.add(next);
                    // mark as visited
                    visited[next] = true;
                    found = true;
                    position++;
                    break;
                }
            }
            if (!found) {
                // no neighbour found
                if (position == numVertices) {
                    // if all vertices visited
                    int last = hamiltonianPath.get(hamiltonianPath.size() - 1);
                    if (graph.get(last).contains(hamiltonianPath.get(0))) {
                        return hamiltonianPath.toArray(new String[0]);
                    }
                }
                // remove last vertex from path
                visited[hamiltonianPath.remove(position - 1)] = false;
                position--;

            }
        }
        return Collections.emptyList().toArray(new String[0]);
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
        // initialise variables
        List<Integer> centers = new ArrayList<Integer>();
        Set<Integer> vertices = graph.keySet();
        int minimumEccentricity = Integer.MAX_VALUE;

        // BFS for ALL vertices
        for (int vertex : vertices) {
            // initialise variables
            int eccentricity = 0;
            Set<Integer> visited = new HashSet<Integer>();
            Queue<Integer> queue = new LinkedList<Integer>();
            // add vertex to queue and visited sets
            queue.offer(vertex);
            visited.add(vertex);

            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    int current = queue.poll();
                    for (int neighbour : graph.get(current)) {
                        if (!visited.contains(neighbour)) {
                            queue.offer(neighbour);
                            visited.add(neighbour);
                        }
                    }
                }
                eccentricity++;
            }

            if (eccentricity < minimumEccentricity) {
                // found new minimum eccentricity
                minimumEccentricity = eccentricity;
                // clear previous
                centers.clear();
                // add new center
                centers.add(vertex);
            }
            // same eccentricity as minimum, add to list
            else if (eccentricity == minimumEccentricity) {
                centers.add(vertex);
            }
        }
        // List<Integer> to String[]
        String[] centerArray = new String[centers.size()];
        for (int i = 0; i < centers.size(); i++) {
            centerArray[i] = String.valueOf(centers.get(i));
        }

        return centerArray;
    }

    public String[][] getStronglyConnectedComponents() {
        // Set up necessary variables
        boolean[] visited = new boolean[urlArray.size()];
        Stack<Integer> stack = new Stack<Integer>();
        String[][] result;
        Arrays.fill(visited, Boolean.FALSE);

        // Implement the Kosaraju-Shamir algorithm
        // Perform the first depth-first search (DFS)
        for (int urlID : graph.keySet()) {
            if (!visited[urlID]) {
                firstDFS(urlID, stack, visited);
            }
        }

        // Reset the visited array in preparation for the second DFS
        Arrays.fill(visited, Boolean.FALSE);

        // Perform the second depth first search on the transposed graph
        // Get strongly connected components
        List<List<Integer>> sccList = new ArrayList<>();
        while (!stack.isEmpty()) {
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

        if (neighbours != null) {
            for (int i : neighbours) {
                if (!visited[i]) {
                    firstDFS(i, stack, visited);
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

    public static void main(String[] args) {
        // project instance
        MyCITS2200Project project = new MyCITS2200Project();

        // MULTIPLE CENTERS EXAMPLE WORKS
        // only getting "6", not getting "5"

        project.addEdge("1", "2");
        project.addEdge("1", "3");
        project.addEdge("2", "4");
        project.addEdge("3", "4");
        project.addEdge("4", "5");
        project.addEdge("4", "6");
        project.addEdge("5", "7");
        project.addVert("7");

        // print functions array
        System.out.println("Center Vertices:");
        String[] s = project.getCenters();
        for (String i : s) {
            System.out.println(i);
        }
    }
}
