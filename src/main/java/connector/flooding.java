package connector;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Node {
    public int nodeId;
    private List<Node> neighbors;
    private boolean receivedMessage;

    public Node(int nodeId) {
        this.nodeId = nodeId;
        this.neighbors = new ArrayList<>();
        this.receivedMessage = false;
    }

    public void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
    }

    public void flood(String message, int hopCount, int destinationNode) {
        if (hopCount <= 10) {
            if (!receivedMessage) {
                System.out.println(generateInfoJSON(nodeId, nodeId, hopCount, message));
                receivedMessage = true;
            }
            if (nodeId != destinationNode) {
                for (Node neighbor : neighbors) {
                    if (!neighbor.receivedMessage) {
                        System.out.println(generateInfoJSON(nodeId, neighbor.nodeId, hopCount + 1, message));
                        neighbor.forwardMessage(message, hopCount + 1, destinationNode);
                    }
                }
            }
        }
    }

    public void forwardMessage(String message, int hopCount, int destinationNode) {
        if (hopCount <= 10) {
            if (!receivedMessage) {
                System.out.println(generateInfoJSON(nodeId, nodeId, hopCount, message));
                receivedMessage = true;
            }
            if (nodeId != destinationNode) {
                for (Node neighbor : neighbors) {
                    if (!neighbor.receivedMessage) {
                        System.out.println(generateInfoJSON(nodeId, neighbor.nodeId, hopCount + 1, message));
                        neighbor.forwardMessage(message, hopCount + 1, destinationNode);
                    }
                }
            }
        }
    }

    private String generateInfoJSON(int fromNode, int toNode, int hopCount, String message) {
        String headers = "\"headers\" : [{\"from\" : \"" + fromNode + "\", \"to\" : \"" + toNode + "\", \"hop_count\" : " + hopCount + "}],";
        String payload = "\"payload\" : \"" + message + "\"";
        return "{" + "\"type\" : \"info\"," + headers + payload + "}";
    }
}

public class flooding {
    public static void main(String[] args) {
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            nodes.add(new Node(i));
        }
      // Configuración de conexiones
        nodes.get(0).addNeighbor(nodes.get(1));
        nodes.get(0).addNeighbor(nodes.get(2));

        nodes.get(1).addNeighbor(nodes.get(0));
        nodes.get(1).addNeighbor(nodes.get(3));

        nodes.get(2).addNeighbor(nodes.get(0));
        nodes.get(2).addNeighbor(nodes.get(3));
        nodes.get(2).addNeighbor(nodes.get(4));

        nodes.get(3).addNeighbor(nodes.get(1));
        nodes.get(3).addNeighbor(nodes.get(2));
        nodes.get(3).addNeighbor(nodes.get(5));

        nodes.get(4).addNeighbor(nodes.get(2));
        nodes.get(4).addNeighbor(nodes.get(5));
        nodes.get(4).addNeighbor(nodes.get(6));

        nodes.get(5).addNeighbor(nodes.get(3));
        nodes.get(5).addNeighbor(nodes.get(4));
        nodes.get(5).addNeighbor(nodes.get(7));

        nodes.get(6).addNeighbor(nodes.get(4));
        nodes.get(6).addNeighbor(nodes.get(7));

        nodes.get(7).addNeighbor(nodes.get(5));
        nodes.get(7).addNeighbor(nodes.get(6));
        nodes.get(7).addNeighbor(nodes.get(8));

        nodes.get(8).addNeighbor(nodes.get(7));
        nodes.get(8).addNeighbor(nodes.get(9));

        nodes.get(9).addNeighbor(nodes.get(8));
        nodes.get(9).addNeighbor(nodes.get(10));

        nodes.get(10).addNeighbor(nodes.get(9));


        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter source node (0-10): ");
        int sourceNode = scanner.nextInt();

        System.out.print("Enter destination node (0-10): ");
        int destinationNode = scanner.nextInt();

        scanner.nextLine(); // Consume newline

        System.out.print("Enter message: ");
        String message = scanner.nextLine();

        if (sourceNode >= 0 && sourceNode <= 10 && destinationNode >= 0 && destinationNode <= 10) {
            if (sourceNode != destinationNode) {
                Node source = getNodeById(sourceNode, nodes.toArray(new Node[0]));
                Node destination = getNodeById(destinationNode, nodes.toArray(new Node[0]));

                if (source != null && destination != null) {
                    source.flood(message, 0, destinationNode);
                } else {
                    System.out.println("Invalid source or destination node.");
                }
            } else {
                System.out.println("Source and destination nodes cannot be the same.");
            }
        } else {
            System.out.println("Invalid node number.");
        }

        scanner.close();
    }

    private static Node getNodeById(int nodeId, Node... nodes) {
        for (Node node : nodes) {
            if (node.nodeId == nodeId) {
                return node;
            }
        }
        return null;
    }
}
