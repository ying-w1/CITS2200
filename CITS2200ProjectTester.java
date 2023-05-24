import java.io.*;
import java.util.*;

public class CITS2200ProjectTester {
	public static void loadGraph(MyCITS2200Project project, String path) {
		// The graph is in the following format:
		// Every pair of consecutive lines represent a directed edge.
		// The edge goes from the URL in the first line to the URL in the second line.
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			while (reader.ready()) {
				String from = reader.readLine();
				String to = reader.readLine();
				System.out.println("Adding edge from " + from + " to " + to);
				project.addEdge(from, to);
			}
		} catch (Exception e) {
			System.out.println("There was a problem:");
			System.out.println(e.toString());
		}
	}

	public static void main(String[] args) {
		// Change this to be the path to the graph file.
		String pathToGraphFile = "testURLs.txt";
		// Create an instance of your implementation.
		MyCITS2200Project project = new MyCITS2200Project();
		// Load the graph into the project.
		loadGraph(project, pathToGraphFile);

		// Write your own tests!
        

        //project.addEdge("URLA", "URLB");
        //project.addEdge("URLA", "URLC");
        //project.addEdge("URLA", "URLD");

        // Testing centers
        String[] centers = project.getCenters();
        System.out.println("Centres:");
        for (String center : centers) {
            System.out.println(center);
        }
    
        // Testing shortest path
    int shortestPathLength = project.getShortestPath("/wiki/Flow_network to /wiki/Braess%27_paradox", "/wiki/Flow_network to /wiki/Circulation_problem");
    System.out.println("Shortest Path Length: " + shortestPathLength);


    // Testing strongly connected components
    // Get strongly connected components
    String[][] components = project.getStronglyConnectedComponents();

        // Print the components
        for (String[] component : components) {
            System.out.println(Arrays.toString(component));
        }
    
        //Testing Hamiltonian path
        String[] hamiltonianPath = project.getHamiltonianPath();
        System.out.println("Hamiltonian Path:");
        for (String url : hamiltonianPath) {
            System.out.println(url);
            }
	}
}
