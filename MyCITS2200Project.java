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
        // Set up necesary variables
        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<Integer>();
        String[][] result = new String[numVertices][numVertices];

        // Insert root to stack & visited set
        stack.push(0);
        visited.add(0);

        // Implement the Kosaraju-Shamir algorithm
        // Perform the first depth-first search (DFS)
        while (!stack.isEmpty()){
            int s = stack.peek();
            if (!visited.contains(s)){
                visited.add(s);
            }

            // Iterate through the graph add not vidited 
            for (Map.Entry<Integer, List<Integer>> set : graph.entrySet()) {
                int currentKey = set.getKey();
                List<Integer> currentValues = set.getValue();

                if(!visited.contains(currentKey)){
                    visited.add(currentKey);

                    //if the adjacent vertices are all in 'visited', push to stack
                    visited.contains(currentValues);

                    

                    Iterator<Integer> iter = graph.get(currentKey).iterator();
                    while(iter.hasNext()) {


                    }
                    {
                        System.out.println(city);
                        // compare the 
                            stack.push(current);
                }
        }
    }

    public static void main(String args[]) {
        // test adjacency list

    }
}
