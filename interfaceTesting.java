import java.util.*;
import java.io.*;

/*--------------SHOW GRAPH----------------*/
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.Node;
import guru.nidi.graphviz.model.Factory;
/*----------------------------------------*/

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
     * @return the legnth of the shorest path in number of links followed.
     */
    public int getShortestPath(String urlFrom, String urlTo) {
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
        return null;
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
        return null;
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

    /* ---------------------TEST----------------------------- */
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

        // Generate random edges
        for (int i = 0; i < numEdges; i++) {
            int fromIndex = random.nextInt(numNodes);
            int toIndex = random.nextInt(numNodes);
            String from = nodes.get(fromIndex);
            String to = nodes.get(toIndex);
            addEdge(from, to);
        }
    }

    public void visualizeGraph(String filename) {
        MutableGraph g = Factory.mutGraph("graph");

        // Add nodes to the graph
        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            String from = entry.getKey();
            g.add(Factory.node(from));
            for (String to : entry.getValue()) {
                g.add(Factory.mutNode(to));
            }
        }

        // Add edges to the graph
        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            String from = entry.getKey();
            for (String to : entry.getValue()) {
                g.add(Factory.mutNode(from).addLink(Factory.mutNode(to)));
            }
        }

        File file = new File('testGraph.PNG');
        try {
            Graphviz.fromGraph(g).render(Format.PNG).toFile(file);
            System.out.println("Graph visualization saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ----------------------TEST----------------------------- */

    public static void main(String[] args) {
        // class instance
        interfaceTesting tester = new interfaceTesting();
        tester.createTestGraph(5, 5);
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
    }
}
