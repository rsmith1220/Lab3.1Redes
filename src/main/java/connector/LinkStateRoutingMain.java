package connector;
import java.util.*;

class Node {
    private String name;
    private Map<String, Integer> neighbors = new HashMap<>();
    
    public Node(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void addNeighbor(String neighborName, int distance) {
        neighbors.put(neighborName, distance);
    }
    
    public Map<String, Integer> getNeighbors() {
        return neighbors;
    }
}

class LinkStateRouting {
    private Map<String, Node> network = new HashMap<>();
    private Map<String, Map<String, Integer>> shortestPaths = new HashMap<>();
    
    public void addNode(Node node) {
        network.put(node.getName(), node);
    }
    
    public void computeShortestPaths() {
        for (Node node : network.values()) {
            Map<String, Integer> shortestPath = computeShortestPath(node);
            shortestPaths.put(node.getName(), shortestPath);
        }
    }
    
    private Map<String, Integer> computeShortestPath(Node sourceNode) {
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>();
        pq.offer(new NodeDistance(sourceNode, 0));
        
        Map<String, Integer> distanceMap = new HashMap<>();
        distanceMap.put(sourceNode.getName(), 0);
        
        while (!pq.isEmpty()) {
            NodeDistance nd = pq.poll();
            Node currentNode = nd.getNode();
            int currentDistance = nd.getDistance();
            
            for (Map.Entry<String, Integer> neighborEntry : currentNode.getNeighbors().entrySet()) {
                String neighborName = neighborEntry.getKey();
                int neighborDistance = neighborEntry.getValue();
                int newDistance = currentDistance + neighborDistance;
                
                if (!distanceMap.containsKey(neighborName) || newDistance < distanceMap.get(neighborName)) {
                    distanceMap.put(neighborName, newDistance);
                    pq.offer(new NodeDistance(network.get(neighborName), newDistance));
                }
            }
        }
        
        return distanceMap;
    }
    
    public void printRoutingTable() {
        for (Node node : network.values()) {
            System.out.println("Routing table for Node " + node.getName() + ":");
            for (String neighborName : node.getNeighbors().keySet()) {
                System.out.println("To Node " + neighborName + " via " + neighborName +
                                   ", Distance: " + shortestPaths.get(node.getName()).get(neighborName));
            }
            System.out.println();
        }
    }
    
    public String findShortestPath(String fromNode, String toNode) {
        if (!shortestPaths.containsKey(fromNode) || !shortestPaths.get(fromNode).containsKey(toNode)) {
            return "No path found.";
        }
        
        List<String> path = new ArrayList<>();
        String currentNode = toNode;
        
        while (!currentNode.equals(fromNode)) {
            path.add(currentNode);
            currentNode = getPreviousNodeInPath(fromNode, currentNode);
        }
        
        path.add(fromNode);
        Collections.reverse(path);
        
        return String.join(" -> ", path);
    }
    
    private String getPreviousNodeInPath(String sourceNode, String currentNode) {
    int shortestDistance = shortestPaths.get(sourceNode).get(currentNode);
    String previousNode = null;

    for (Map.Entry<String, Integer> entry : shortestPaths.get(sourceNode).entrySet()) {
        if (network.get(currentNode).getNeighbors().containsKey(entry.getKey()) && entry.getValue() + network.get(currentNode).getNeighbors().get(entry.getKey()) == shortestDistance) {
            previousNode = entry.getKey();
            break;
        }
    }
    return previousNode;
    }



    
    private static class NodeDistance implements Comparable<NodeDistance> {
        private Node node;
        private int distance;
        
        public NodeDistance(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
        
        public Node getNode() {
            return node;
        }
        
        public int getDistance() {
            return distance;
        }
        
        @Override
        public int compareTo(NodeDistance other) {
            return Integer.compare(this.distance, other.distance);
        }
    }
}

public class LinkStateRoutingMain {
    public static void main(String[] args) {
        LinkStateRouting router = new LinkStateRouting();
        
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");
        
        nodeA.addNeighbor("B", 1);
        nodeA.addNeighbor("C", 2);
        
        nodeB.addNeighbor("A", 1);
        nodeB.addNeighbor("C", 3);
        nodeB.addNeighbor("D", 4);
        
        nodeC.addNeighbor("A", 2);
        nodeC.addNeighbor("B", 3);
        nodeC.addNeighbor("D", 2);
        nodeC.addNeighbor("E", 5);
        
        nodeD.addNeighbor("B", 4);
        nodeD.addNeighbor("C", 2);
        nodeD.addNeighbor("E", 1);
        
        nodeE.addNeighbor("C", 5);
        nodeE.addNeighbor("D", 1);
        nodeE.addNeighbor("F", 3);
        
        nodeF.addNeighbor("E", 3);
        
        router.addNode(nodeA);
        router.addNode(nodeB);
        router.addNode(nodeC);
        router.addNode(nodeD);
        router.addNode(nodeE);
        router.addNode(nodeF);
        
        router.computeShortestPaths();
        router.printRoutingTable();
        
        String shortestPath = router.findShortestPath("A", "F");
        System.out.println("Shortest path from Node A to Node F: " + shortestPath);
    }
}

