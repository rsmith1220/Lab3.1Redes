package connector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Dijkstra {

    static class Graph { // creador de grafos
        Map<String, Map<String, Integer>> vertices = new HashMap<>();

        void addVertex(String name, Map<String, Integer> neighbors) {
            vertices.put(name, neighbors);
        }

        public Message shortestPath(String start, String end) { // se empieza a implementar el algoritmo Dijkstra
            Map<String, Integer> distances = new HashMap<>();
            Map<String, String> previous = new HashMap<>();
            PriorityQueue<VertexDistance> pq = new PriorityQueue<>();

            // empieza a buscar el camino mas corto
            for (String vertex : vertices.keySet()) {
                if (vertex.equals(start)) {
                    distances.put(vertex, 0);
                    pq.add(new VertexDistance(vertex, 0));
                } else {
                    distances.put(vertex, Integer.MAX_VALUE);
                    pq.add(new VertexDistance(vertex, Integer.MAX_VALUE));
                }
                previous.put(vertex, null);
            }

            while (!pq.isEmpty()) {
                VertexDistance current = pq.poll();
                String currentVertex = current.vertex;

                if (currentVertex.equals(end)) {
                    break; // encuentra el nodo final
                }

                for (Map.Entry<String, Integer> neighbor : vertices.get(currentVertex).entrySet()) {
                    Integer alt = distances.get(currentVertex) + neighbor.getValue();
                    if (alt < distances.get(neighbor.getKey())) {
                        distances.put(neighbor.getKey(), alt);
                        previous.put(neighbor.getKey(), currentVertex);
                        pq.add(new VertexDistance(neighbor.getKey(), alt));
                    }
                }
            }

            List<String> path = new LinkedList<>();
            for (String at = end; at != null; at = previous.get(at)) {
                path.add(0, at);
            }

            int hopCount = path.size() - 1;

            return new Message("info", new Headers(start, end, hopCount), "Mensaje Hola mundo");// retorna el camino
                                                                                                // corto
        }

        static class VertexDistance implements Comparable<VertexDistance> {
            String vertex;
            Integer distance;

            VertexDistance(String vertex, Integer distance) {
                this.vertex = vertex;
                this.distance = distance; // la distancia asociada con el vertice
            }

            @Override
            public int compareTo(VertexDistance other) {
                return this.distance.compareTo(other.distance);
            }
        }
    }

    static class Message { // retorna el output con el camino corto y el mensaje
        String type;
        Headers headers;
        String payload;

        Message(String type, Headers headers, String payload) {
            this.type = type;
            this.headers = headers;
            this.payload = payload;
        }

        @Override
        public String toString() {
            return "{\n\"type\" : \"" + type + "\",\n" +
                    "\"headers\" : " + headers + ",\n" +
                    "\"payload\" : \"" + payload + "\"\n}";
        }
    }

    static class Headers {
        String from;
        String to;
        int hop_count;

        Headers(String from, String to, int hop_count) {
            this.from = from;
            this.to = to;
            this.hop_count = hop_count;
        }

        // retorna el resultado
        @Override
        public String toString() {
            return "[\"from\" : \"" + from + "\", \"to\" : \"" + to + "\", \"hop_count\" : " + hop_count + "]";
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph();

        // se agregan los nodos al grafo
        g.addVertex("A", Map.of("B", 1, "C", 4, "D", 7));
        g.addVertex("B", Map.of("A", 1, "C", 2, "E", 5));
        g.addVertex("C", Map.of("A", 4, "B", 2, "D", 1, "F", 6));
        g.addVertex("D", Map.of("A", 7, "C", 1, "G", 3));
        g.addVertex("E", Map.of("B", 5, "F", 3, "H", 1));
        g.addVertex("F", Map.of("C", 6, "E", 3, "G", 2, "I", 4));
        g.addVertex("G", Map.of("D", 3, "F", 2, "I", 1));
        g.addVertex("H", Map.of("E", 1, "I", 5));
        g.addVertex("I", Map.of("F", 4, "G", 1, "H", 5));

        // se busca el camino corto para llegar a cada nodo
        System.out.println(g.shortestPath("A", "B"));
        System.out.println(g.shortestPath("A", "C"));
        System.out.println(g.shortestPath("A", "I"));
    }

}
