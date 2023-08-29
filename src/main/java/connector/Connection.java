package connector;

import org.jivesoftware.smack.*;

import connector.Dijkstra.Graph;

import java.io.IOException;

import java.util.*;

public class Connection {
    public static void main(String[] args) throws SmackException, IOException, XMPPException, InterruptedException {

        Scanner sc = new Scanner(System.in);

        String user = "omen";
        String pass = "1234";

        Initiator.Coneccion(user, pass);

        Graph g = new Graph();
        g.addVertex("A", Map.of("B", 1, "C", 4));
        g.addVertex("B", Map.of("A", 1, "C", 2, "D", 5));
        g.addVertex("C", Map.of("A", 4, "B", 2, "D", 1));
        g.addVertex("D", Map.of("B", 5, "C", 1));

        System.out.println(g.shortestPath("A", "D")); // Should print the shortest distances from A to all other
                                                      // vertices

    }

}
