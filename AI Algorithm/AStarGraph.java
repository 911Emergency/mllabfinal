import java.util.*;

public class AStarGraph {
    static class vertex implements Comparable<vertex> {
        char id;
        double g;  // cost from the start vertex
        double h;  // heuristic value
        double f;  // f = g + h
        vertex parent;
        List<Edge> neighbors;

        vertex(char id, double h) {
            this.id = id;
            this.g = Double.MAX_VALUE;
            this.h = h;
            this.f = Double.MAX_VALUE;
            this.parent = null;
            this.neighbors = new ArrayList<>();
        }
        
        @Override
        public int compareTo(vertex other) {
            return Double.compare(this.f, other.f);
        }
    }

    static class Edge {
        double weight;
        vertex target;

        Edge(double weight, vertex target) {
            this.weight = weight;
            this.target = target;
        }
    }

    public static void addBranch(vertex vertex, double weight, vertex target) {
        Edge newEdge = new Edge(weight, target);
        vertex.neighbors.add(newEdge);
    }

    public static vertex aStar(vertex start, vertex goal) {
        PriorityQueue<vertex> q = new PriorityQueue<>();
        Set<vertex> Explored = new HashSet<>();

        start.g = 0;
        start.f = start.h;
        q.add(start);
        
        while (!q.isEmpty()) {
            vertex current = q.poll();

            if (current == goal) {
                return current;
            }

         Explored.add(current);

            for (Edge edge : current.neighbors) {
                vertex neighbor = edge.target;

                if  (Explored.contains(neighbor)) {
                    continue;  // Skip vertexs already evaluated
                }

                double totalWeight = current.g + edge.weight;

                if (!q.contains(neighbor) || totalWeight < neighbor.g) {
                    neighbor.parent = current;
                    neighbor.g = totalWeight;
                    neighbor.f = neighbor.g + neighbor.h;

                    if (!q.contains(neighbor)) {
                        q.add(neighbor);
                    }
                }
            }
        }

        return null;  // No path found
    }

    public static void printPath(vertex target) {
        vertex current = target;
        List<Character> path = new ArrayList<>();

        while (current != null) {
            path.add(current.id);
            current = current.parent;
        }

        Collections.reverse(path);

        for (char vertexId : path) {
            System.out.print(vertexId + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        // vertex S = new vertex('S', 11.5);
        // vertex A = new vertex('A', 10.1);
        // vertex B = new vertex('B', 5.8);
        // vertex C = new vertex('C', 3.4);
        // vertex D = new vertex('D', 9.2);
        // vertex E = new vertex('E', 7.1);
        // vertex F = new vertex('F', 3.5);
        // vertex G = new vertex('G', 0);

        // addBranch(S, 3, A);
        // addBranch(S, 4, D);
        // addBranch(A, 4, B);
        // addBranch(A, 5, D);
        // addBranch(B, 4, C);47654
        // addBranch(B, 5, E);
        // addBranch(D, 2, E);
        // addBranch(E, 4, F);
        // addBranch(E, 5, B);
        // addBranch(F, 3.5, G);

        
        vertex S = new vertex('S', 6);
        vertex A = new vertex('A', 4);
        vertex B = new vertex('B', 4);
        vertex C = new vertex('C', 3.5);
        vertex D = new vertex('D', 3.5);
        vertex E = new vertex('E', 1);
        vertex F = new vertex('F', 1);
        vertex G = new vertex('G', 0);

        addBranch(S, 2, A);
        addBranch(S, 3, B);
        addBranch(A, 3, C);
        addBranch(B, 1, C);
        addBranch(B, 3, D);
        addBranch(C, 3, E);
        addBranch(C, 1, D);
        addBranch(D, 2, F);
        addBranch(E, 2, G);
        addBranch(F, 1, G);
        


        vertex result = aStar(S, G);

        if (result != null) {
            System.out.println("Shortest Path:");
            printPath(result);
        } else {
            System.out.println("No path found.");
        }
    }
}
