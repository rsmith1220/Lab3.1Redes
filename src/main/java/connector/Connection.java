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

    }

}
