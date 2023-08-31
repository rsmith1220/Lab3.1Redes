package connector;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Dijkstra {

    static class Graph {
        Map<String, Map<String, Integer>> vertices = new HashMap<>();

        void addVertex(String name, Map<String, Integer> neighbors) {
            vertices.put(name, neighbors);
        }

        // retorna el shortest path
        public Result shortestPath(String start, String end) {
            Map<String, Integer> distances = new HashMap<>();
            Map<String, String> previous = new HashMap<>();
            PriorityQueue<VertexDistance> pq = new PriorityQueue<>();

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
                    break; // se acaba el algoritmo
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

            return new Result(distances.get(end), path);
        }

        static class VertexDistance implements Comparable<VertexDistance> {
            String vertex;
            Integer distance;

            VertexDistance(String vertex, Integer distance) {
                this.vertex = vertex;
                this.distance = distance;
            }

            @Override
            public int compareTo(VertexDistance other) {
                return this.distance.compareTo(other.distance);
            }
        }
    }

    static class Result {
        Integer distance;
        List<String> path;

        Result(Integer distance, List<String> path) {
            this.distance = distance;
            this.path = path;
        }

        @Override
        public String toString() {

            return "Shortest Path: " + String.join(" -> ", path) + "\nTotal Distance: " + distance;
        }
    }

}
