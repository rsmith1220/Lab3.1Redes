package connector;

import java.io.IOException;
import java.util.Scanner;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.PresenceBuilder;
import org.jivesoftware.smack.packet.StanzaFactory;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.parts.Localpart;

public class Initiator {

    // iniciar sesion
    public static AbstractXMPPConnection Coneccion(String user, String pass)
            throws SmackException, IOException, XMPPException, InterruptedException {

        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setHost("alumchat.xyz")
                .setXmppDomain("alumchat.xyz")
                .setPort(5222)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setUsernameAndPassword(user, pass)
                .build();

        AbstractXMPPConnection connection = new XMPPTCPConnection(config);
        connection.connect(); // Establishes a connection to the server
        connection.login(); // Logs in

        return connection;
    }

    // cerrar sesion
    public static String Deconeccion(AbstractXMPPConnection connection)
            throws SmackException, IOException, XMPPException, InterruptedException {

        connection.disconnect();

        return "Desconectado";

    }

    // crear un usuario nuevo
    public static void Creation()
            throws SmackException, IOException, XMPPException, InterruptedException {
        System.out.println("Ingrese su nombre");
        Scanner sc = new Scanner(System.in);
        String usuario = sc.nextLine();

        System.out.println("Ingrese su password");
        String password = sc.nextLine();

        try {
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setHost("alumchat.xyz")
                    .setXmppDomain("alumchat.xyz")
                    .setPort(5222)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .build();
            AbstractXMPPConnection connection = new XMPPTCPConnection(config);
            connection.connect();

            AccountManager accountManager = AccountManager.getInstance(connection);

            // se hace la prueba
            try {
                if (accountManager.supportsAccountCreation()) {
                    accountManager.sensitiveOperationOverInsecureConnection(true);
                    accountManager.createAccount(Localpart.from(usuario), password);
                    System.out.println("\nSe ha creado su cuenta, ya puede iniciar sesion\n");
                } else {
                    System.out.println("Account creation is not supported by this server");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al crear la cuenta");
            }
            connection.disconnect();

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("\n Su cuenta no fue creada");

        }

    }

    // eliminar cuenta
    public static void eliminar() {

        System.out.println("Ingrese su usuario para confirmar la eliminacion");
        Scanner sc = new Scanner(System.in);
        String user = sc.nextLine();

        System.out.println("Ingrese su password");
        String pass = sc.nextLine();

        // verifica la cuenta que se eliminara
        try {
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setHost("alumchat.xyz")
                    .setXmppDomain("alumchat.xyz")
                    .setPort(5222)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .setUsernameAndPassword(user, pass)
                    .build();
            AbstractXMPPConnection connection = new org.jivesoftware.smack.tcp.XMPPTCPConnection(config);
            connection.connect();
            connection.login();

            AccountManager accountManager = AccountManager.getInstance(connection); // se conecta y asi sabe que cuenta
                                                                                    // eliminar
            accountManager.deleteAccount();
            System.out.println("Cuenta eliminada");

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("\n Su cuenta no fue eliminada");
        }
    }

    // define mensaajes de presencia y status
    public static void Presencia(AbstractXMPPConnection connection) throws NotConnectedException, InterruptedException {
        StanzaFactory stanzaFactory = connection.getStanzaFactory();
        PresenceBuilder presenceBuilder = stanzaFactory.buildPresenceStanza();

        System.out.println("Presencia\n 1.Available\n 2.Do not disturb\n 3.Away");
        Scanner sc = new Scanner(System.in);
        int coose = sc.nextInt();
        switch (coose) {
            case 1:// available
                Presence presence = presenceBuilder.setMode(Presence.Mode.available).build();
                presence = stanzaFactory.buildPresenceStanza()
                        .setPriority(10)
                        .build();
                connection.sendStanza(presence);
                break;
            case 2:// do not disturb
                presence = presenceBuilder.setMode(Presence.Mode.dnd).build();
                presence = stanzaFactory.buildPresenceStanza()
                        .setPriority(10)
                        .build();
                connection.sendStanza(presence);
                break;
            case 3:// away
                presence = presenceBuilder.setMode(Presence.Mode.away).build();
                connection.sendStanza(presence);
                break;
            default:
                System.out.println("Elija una opcion correcta");
        }
        System.out.println("Desea agregar un status?");
        System.out.println("Status\n 1.si\n 2.no\n ");
        coose = sc.nextInt();
        switch (coose) {
            case 1:
                System.out.println("Escriba su status");
                String estatus = sc.nextLine();
                Presence status = presenceBuilder.setStatus(estatus).build();
                connection.sendStanza(status);
                sc.nextLine();
                break;
            case 2:
                break;
            default:
                System.out.println("Elija una opcion correcta");
        }

    }
}
