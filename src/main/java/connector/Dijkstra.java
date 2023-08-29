package connector;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Dijkstra {

    static class Graph {
        Map<String, Map<String, Integer>> vertices = new HashMap<>();

        void addVertex(String name, Map<String, Integer> neighbors) {
            vertices.put(name, neighbors);
        }

        // Returns the shortest path from source to target
        public Map<String, Integer> shortestPath(String start, String end) {
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
                    break; // Shortest path to target found
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

            return distances;
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

}
