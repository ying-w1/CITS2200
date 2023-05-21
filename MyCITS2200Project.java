import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Iterator;

public class MyCITS2200Project implements CITS2200Project {

    // Setting up some variables as per the assignment editorial 

    private int numVertices; // number of vertices
    private Map<Integer, List<Integer>> graph; // adjacency list rep of graph
    private Map<Integer, List<Integer>> transGraph; // Reverse direction of graph for Kosaraju's algorithm in stronglyConnectedComponent()

    // The following has been set up to access the graph both via the index & via the URL 
    private ArrayList<String> urlArray; // Array of URL (vertices)
    
    /* index : vertex
     * 1 : /wiki.com/page1
     * 2 : /wiki.com/page2
     * 3 : /wiki.com/page3
     * etc..
     */

    private HashMap<String, Integer> urlKey; // Key = URL, value = index in urlArray

    /* key (URL) : values (index)
     * /wiki.com/page : 1
     * /wiki.com/page : 2
     * /wiki.com/page : 3
     * etc..
     */


    public MyCITS2200Project() {
        graph = new HashMap<Integer, List<Integer>>();
        transGraph = new HashMap<Integer, List<Integer>>();
        urlArray = new ArrayList<String>();
        urlKey = new HashMap<String, Integer>();
    }

    public void addEdge(String urlFrom, String urlTo) {
        // If the vertex does not exist in the graph, add it
            addVertex(urlFrom);
            addVertex(urlTo);

        // Create the edge in normal graph
            addToGraph(urlFrom,urlTo);

         // Create the edge in transposed graph
            addToTransGraph(urlTo,urlFrom);
    }

    private void addVertex(String v){
        /** Check if vertix exist in the hashmap 
         * Add to map
        */
        if (!urlKey.containsKey(v)){
           urlArray.add(v);
           int index = urlArray.indexOf(v);
           urlKey.put(v,index);
        }
   }

   private void addToGraph(String from, String to) {
       int idURLFrom = urlKey.get(from);
       int idURLTo = urlKey.get(to);

       if (!graph.containsKey(idURLFrom)) {
           List<Integer> temp = new LinkedList<>();
           graph.put(idURLFrom, temp);
       }

       List<Integer> adjacencyList = graph.get(idURLFrom);
       adjacencyList.add(idURLTo);
   }

   private void addToTransGraph(String from, String to) {
       int idURLFrom = urlKey.get(from);
       int idURLTo = urlKey.get(to);

       if (!transGraph.containsKey(idURLFrom)) {
           List<Integer> temp2 = new LinkedList<>();
           transGraph.put(idURLFrom, temp2);
       }

       List<Integer> transAdjacencyList = transGraph.get(idURLFrom);
       transAdjacencyList.add(idURLTo);
    }

    /**
	 * Finds the shorest path in number of links between two pages.
	 * If there is no path, returns -1.
	 * 
	 * @param urlFrom the URL where the path should start.
	 * @param urlTo the URL where the path should end.
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
    public String[] getHamiltonianPath(){

    }

    /**
	 * Finds all the centers of the page graph. The order of pages
	 * in the output does not matter. Any order is correct as long as
	 * all the centers are in the array, and no pages that aren't centers
	 * are in the array.
	 * 
	 * @return an array containing all the URLs that correspond to pages that are centers.
	 */
    public String[] getCenters() {
        // Implement the algorithm to find the centers
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

        // Perform the second pass of BFS in the transposed order
        // Get strongle connected vertices

        return result;
    }


    public static void main(String args[]){
            // test adjacency list
            
    }

    }
