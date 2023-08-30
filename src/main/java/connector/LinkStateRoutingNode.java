package connector;

import java.util.*;

class LinkStateRoutingNode {
    private String nodeName;
    private Map<String, Integer> neighbors; // Neighbor node name and cost
    private Map<String, Integer> distances; // Distance to other nodes

    public LinkStateRoutingNode(String nodeName) {
        this.nodeName = nodeName;
        this.neighbors = new HashMap<>();
        this.distances = new HashMap<>();
    }

    public void addNeighbor(String neighborName, int cost) {
        neighbors.put(neighborName, cost);
        distances.put(neighborName, cost);
    }

    public void updateDistances(Map<String, Map<String, Integer>> neighborDistances) {
        for (String neighbor : neighbors.keySet()) {
            Map<String, Integer> neighborDistanceVector = neighborDistances.get(neighbor);
            if (neighborDistanceVector != null) {
                for (String destination : neighborDistanceVector.keySet()) {
                    int newDistance = distances.get(neighbor) + neighborDistanceVector.get(destination);
                    if (!distances.containsKey(destination) || newDistance < distances.get(destination)) {
                        distances.put(destination, newDistance);
                    }
                }
            }
        }
    }

    public void printRoutingTable() {
        System.out.println("Routing table for node " + nodeName);
        for (String destination : distances.keySet()) {
            System.out.println("To " + destination + " : " + distances.get(destination));
        }
    }

    // Other methods for sending and receiving packets, updating neighbors, etc.
}

