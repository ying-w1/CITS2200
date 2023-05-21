import java.util.*;
import java.io.*;

public class interfaceTesting {
    private HashMap<String, List<String>> graph;

    /**
     * Class constructor for interfaceTesting
     */
    public interfaceTesting() {
        graph = new HashMap<String, List<String>>();
    }

    /**
     * Adds an edge to the Wikipedia page graph. If the pages do not
     * already exist in the graph, they will be added to the graph.
     * 
     * Adds the urlTo as an edge for the urlFrom
     * HashMap key = URL
     * HashMap value = list of URLs representing edges
     * 
     * @param urlFrom the URL which has a link to urlTo.
     * @param urlTo   the URL which urlFrom has a link to.
     */
    public void addEdge(String urlFrom, String urlTo) {
        if (!graph.containsKey(urlFrom)) {
            graph.put(urlFrom, new ArrayList<>());
        }
        // get element with associated key, add urlTo to the list
        graph.get(urlFrom).add(urlTo);
    }

    /**
     * Finds the shorest path in number of links between two pages.
     * If there is no path, returns -1.
     * 
     * @param urlFrom the URL where the path should start.
     * @param urlTo   the URL where the path should end.
     * @return the length of the shortest path in number of links followed.
     */
    public int getShortestPath(String urlFrom, String urlTo) {
        // check URLs are in graph
        if (!graph.containsKey(urlFrom) || !graph.containsKey(urlTo)) {
            return -1;
        }
        // enqueue URLs to visit
        Queue<String> queue = new LinkedList<>();
        // map of shortest distances from source
        HashMap<String, Integer> distances = new HashMap<>();

        // initialise queue and distances with source
        queue.add(urlFrom);
        distances.put(urlFrom, 0);

        while (!queue.isEmpty()) {
            String current = queue.remove();
            if (current.equals(urlTo)) {
                return distances.get(current);
            }

            List<String> neighbours = graph.get(current);
            for (String neighbour : neighbours) {
                if (!distances.containsKey(neighbour)) {
                    queue.add(neighbour);
                    distances.put(neighbour, distances.get(current) + 1);
                }
            }
        }
        // path not found
        return -1;
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
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (String node : graph.keySet()) {

            for (String start : graph.keySet()) {
                if (!start.equals(node)) {
                    int distance = getShortestPath(start, node);
                    // assign largest distance to max
                    if (distance > max) {
                        max = distance;
                    }
                }

            }
            if (max < min) {
                min = max;
                centers.clear();
                centers.add(node);
            } else if (max == min) {
                centers.add(node);
            }
        }

        String[] centerArray = new String[centers.size()];
        centers.toArray(centerArray);

        return centerArray;
    }

    /**
     * Finds all the strongly connected components of the page graph.
     * Every strongly connected component can be represented as an array
     * containing the page URLs in the component. The return value is thus an array
     * of strongly connected components. The order of elements in these arrays
     * does not matter. Any output that contains all the strongly connected
     * components is considered correct.
     * 
     * @return an array containing every strongly connected component.
     */
    public String[][] getStronglyConnectedComponents() {
        // Tarjan's algorithm for DFS

        // initialise variables
        Queue<String> queue = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        List<List<String>> components = new ArrayList<>();

        for (String url : graph.keySet()) {
            if (!visited.contains(url)) {
                List<String> component = new ArrayList<>();
                queue.offer(url);
                visited.add(url);

                while (!queue.isEmpty()) {
                    String current = queue.poll();
                    component.add(current);

                    List<String> neighbours = graph.get(current);
                    if (neighbours != null) {
                        for (String neighbour : neighbours) {
                            if (!visited.contains(neighbour)) {
                                queue.offer(neighbour);
                                visited.add(neighbour);
                            }
                        }
                    }
                }
                components.add(component);
            }
        }
        String[][] componentArray = new String[components.size()][];
        for (int i = 0; i < components.size(); i++) {
            List<String> component = components.get(i);
            componentArray[i] = component.toArray(new String[component.size()]);
        }
        return componentArray;
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
        return null;
    }

    public static void main(String[] args) {

    }
}
