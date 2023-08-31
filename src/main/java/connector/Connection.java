package connector;

import org.jivesoftware.smack.*;

import java.io.IOException;

public class Connection {
    public static void main(String[] args) throws SmackException, IOException, XMPPException, InterruptedException {

        // connection
        String user = "omen";
        String pass = "1234";

        Initiator.Coneccion(user, pass);

    }

}
