import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class DistanceCalculator_DFS {
    public static Map<String, List<String>> constructGraph(String filename) throws IOException {
        Map<String, List<String>> graph = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("->");
                String node = parts[0].trim();
                String[] connections = parts[1].split(",");
                for (int i = 0; i < connections.length; i++) {
                    connections[i] = connections[i].trim(); // to trim leading and trailing spaces between nodes
                }
                graph.put(node, Arrays.asList(connections));
            }
        }
        return graph;
    }

    public static int calculateDistance(Map<String, List<String>> graph, String start, String end, HashSet<String> visited) {

        if (start.equals(end))
            return 0;

        if (!graph.containsKey(start))
            return -1;

        visited.add(start);
        for (int i = 0; i < graph.get(start).size(); i++) {
            String neighbor = graph.get(start).get(i);
            if (!visited.contains(neighbor)) {
                int distance = calculateDistance(graph, neighbor, end, visited);
                if (distance != -1) {

                    return distance + 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {

        if (args.length != 3) {
            System.out.println("Invalid Input");
            return;
        }

        String filename = args[0];
        String startNode = args[1];
        String endNode = args[2];

        try {
            Map<String, List<String>> graph = constructGraph(filename);
            int distance = -1;
            HashSet<String> visited = new HashSet<>();
            if (graph.containsKey(startNode)) {
                distance = calculateDistance(graph, startNode, endNode, visited);
            }
            else {
                distance = calculateDistance(graph, endNode, startNode, visited);
            }
            if (distance == -1) {
                System.out.println("Nodes are not connected.");
            } else {
                System.out.println(distance);
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}

