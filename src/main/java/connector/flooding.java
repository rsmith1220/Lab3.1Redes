package connector;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Nodo {
    private String nombre;
    private List<Nodo> vecinos;

    public Nodo(String nombre) {
        this.nombre = nombre;
        this.vecinos = new ArrayList<>();
    }

    public void agregarVecino(Nodo vecino) {
        vecinos.add(vecino);
    }

    public void mostrarVecinos() {
        System.out.print("Nodo " + nombre + " tiene vecinos: ");
        for (Nodo vecino : vecinos) {
            System.out.print(vecino.nombre + " ");
        }
        System.out.println();
    }

    public void enviarMensaje(String mensaje, Nodo destino, Nodo remitente) {
    System.out.println("Nodo " + nombre + " enviando mensaje a Nodo " + destino.nombre + ": " + mensaje);

    if (this == destino) {
        System.out.println("Mensaje entregado a Nodo " + nombre);
        System.out.print("\n");
        return;
    }

    // Reenviar el mensaje a todos los vecinos
    for (Nodo vecino : vecinos) {
        if (!vecino.nombre.equals(this.nombre)) {
            System.out.println("Nodo " + nombre + " enviando mensaje a Nodo " + vecino.nombre);
            vecino.recibirMensaje(mensaje, this, destino);
            System.out.print("\n");
        }
    }
}

    public void recibirMensaje(String mensaje, Nodo remitente, Nodo destino) {
    System.out.println("Nodo " + nombre + " recibió el mensaje de Nodo " + remitente.nombre + ": " + mensaje);

    if (this == destino) {
        System.out.println("Mensaje entregado a Nodo " + nombre);
        return;
    }

    // Reenviar el mensaje a todos los vecinos, excepto al remitente original
    for (Nodo vecino : vecinos) {
        if (!vecino.nombre.equals(this.nombre) && vecino == destino) {
            System.out.println("Nodo " + nombre + " reenviando mensaje a Nodo " + vecino.nombre);
            vecino.recibirMensaje(mensaje, this, destino);
        }
    }
}
}

public class flooding {
    public static void main(String[] args) {
        Nodo nodoA = new Nodo("A");
        Nodo nodoB = new Nodo("B");
        Nodo nodoC = new Nodo("C");
        Nodo nodoD = new Nodo("D");

        nodoA.agregarVecino(nodoB);
        nodoA.agregarVecino(nodoD);
        nodoB.agregarVecino(nodoA);
        nodoB.agregarVecino(nodoC);
        nodoC.agregarVecino(nodoB);
        nodoC.agregarVecino(nodoD);
        nodoD.agregarVecino(nodoC);
        nodoD.agregarVecino(nodoA);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Nodo de origen (A, B, C, D): ");
        String origen = scanner.nextLine();
        System.out.print("Nodo de destino (A, B, C, D): ");
        String destino = scanner.nextLine();
        System.out.print("Mensaje a enviar: ");
        String mensaje = scanner.nextLine();
        System.out.print("\n");

        Nodo nodoOrigen = null;
        Nodo nodoDestino = null;

        switch (origen) {
            case "A":
                nodoOrigen = nodoA;
                break;
            case "B":
                nodoOrigen = nodoB;
                break;
            case "C":
                nodoOrigen = nodoC;
                break;
            case "D":
                nodoOrigen = nodoD;
                break;
            default:
                System.out.println("Nodo de origen no válido.");
                return;
        }

        switch (destino) {
            case "A":
                nodoDestino = nodoA;
                break;
            case "B":
                nodoDestino = nodoB;
                break;
            case "C":
                nodoDestino = nodoC;
                break;
            case "D":
                nodoDestino = nodoD;
                break;
            default:
                System.out.println("Nodo de destino no válido.");
                return;
        }

        nodoOrigen.enviarMensaje(mensaje, nodoDestino, null);
        scanner.close();
    }
}