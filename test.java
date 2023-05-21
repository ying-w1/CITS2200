import java.util.*;

public class test{

    private int numVertices; // number of vertices
    private Map<Integer, List<Integer>> graph;
    private Map<Integer, List<Integer>> transGraph; // Reverse direction of adj for Kosaraju's algorithm in
                                                    // stronglyConnectedComponent()
    private ArrayList<String> urlArray; // Array of URL (vertices)
    private HashMap<String, Integer> urlKey; // Key = URL, value = index in urlArray


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
            graph.put(idURLFrom, temp2);
        }
    }

    public String[][] getStronglyConnectedComponents() {
        // Step 1: Perform Depth First Search (DFS) on the original graph
        boolean[] visited = new boolean[numVertices];
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                dfs(i, visited, stack, graph);
            }
        }


        // Step 3: Perform DFS on the transposed graph in the order of the stack
        visited = new boolean[numVertices];
        List<List<Integer>> components = new ArrayList<List<Integer>>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited[v]) {
                List<Integer> component = new ArrayList<Integer>();
                dfs(v, visited, component, transGraph);
                components.add(component);
            }
        }

        // Step 4: Convert the components to a string array
        String[][] result = new String[components.size()][];
        for (int i = 0; i < components.size(); i++) {
            List<Integer> component = components.get(i);
            result[i] = new String[component.size()];
            for (int j = 0; j < component.size(); j++) {
                int index = component.get(j);
                result[i][j] = urlArray.get(index);
            }
        }

        return result;
    }

    // Depth First Search (DFS) method
    private void dfs(int v, boolean[] visited, Stack<Integer> stack, Map<Integer, List<Integer>> graph) {
        visited[v] = true;
        List<Integer> adjList = graph.get(v);
        if (adjList != null) {
            for (int neighbor : adjList) {
                if (!visited[neighbor]) {
                    dfs(neighbor, visited, stack, graph);
                }
            }
        }
        stack.push(v);
    }
    }

    public static void main(String[] args) {
        
        test testInstance = new test();
        
        testInstance.addEdge("/wiki/Dinic%27s_algorithm", "/wiki/Flow_network");
        testInstance.addEdge("/wiki/Flow_network", "/wiki/Circulation_problem");
        testInstance.addEdge("/wiki/Circulation_problem", "/wiki/Braess%27_paradox");
        testInstance.addEdge("/wiki/Flow_network", "/wiki/Braess%27_paradox");
        testInstance.addEdge("/wiki/Braess%27_paradox", "/wiki/Approximate_max-flow_min-cut_theorem");

        String[][] components = testInstance.getStronglyConnectedComponents();

        for (String[] component : components) {
            System.out.println(Arrays.toString(component));
        }
    }
}



/** 
    0 /wiki/Flow_network
    1 /wiki/Dinic%27s_algorithm
    2 /wiki/Circulation_problem
    3 /wiki/Braess%27_paradox
    4 /wiki/Approximate_max-flow_min-cut_theorem
    */