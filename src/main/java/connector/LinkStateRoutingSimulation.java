package connector;

import java.util.*;

public class LinkStateRoutingSimulation {
    public static void main(String[] args) {
        // Create nodes
        LinkStateRoutingNode nodeA = new LinkStateRoutingNode("A");
        LinkStateRoutingNode nodeB = new LinkStateRoutingNode("B");
        LinkStateRoutingNode nodeC = new LinkStateRoutingNode("C");

        // Add neighbors and initial distances
        nodeA.addNeighbor("B", 1);
        nodeA.addNeighbor("C", 1);
        nodeB.addNeighbor("A", 1);
        nodeB.addNeighbor("C", 2);
        nodeC.addNeighbor("A", 1);
        nodeC.addNeighbor("B", 2);

        // Initial distance vectors
        Map<String, Map<String, Integer>> initialDistanceVectors = new HashMap<>();
        initialDistanceVectors.put("A", new HashMap<>(Map.of("B", 1, "C", 1)));
        initialDistanceVectors.put("B", new HashMap<>(Map.of("A", 1, "C", 2)));
        initialDistanceVectors.put("C", new HashMap<>(Map.of("A", 1, "B", 2)));

        // Update distances based on initial distance vectors
        nodeA.updateDistances(initialDistanceVectors);
        nodeB.updateDistances(initialDistanceVectors);
        nodeC.updateDistances(initialDistanceVectors);

        // Print routing tables
        nodeA.printRoutingTable();
        nodeB.printRoutingTable();
        nodeC.printRoutingTable();
    }
}
