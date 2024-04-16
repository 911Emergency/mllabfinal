import java.util.*;

class intermediatepuzzle {
    int[][] layout;
    int pathCost;
    int estimatedCost;
    intermediatepuzzle ancestor;

    intermediatepuzzle(int[][] layout, int pathCost, int estimatedCost, intermediatepuzzle ancestor) {
        this.layout = layout;
        this.pathCost = pathCost;
        this.estimatedCost = estimatedCost;
        this.ancestor = ancestor;
    }

    int totalCost() {
        return this.pathCost + this.estimatedCost;
    }
}

class AStar8Puzzle {
    public static void main(String[] args) {
        

        int[][] startState = {
            {2, 8, 3},
            {1, 6, 4},
            {7, 0, 5}
        };

        // int[][] startState = {
        //     {1, 2, 3},
        //     {4, 5, 0},
        //     {7, 8, 6}
        // };

        // int[][] endState = {
        //     {1, 2, 3},
        //     {4, 5, 6},
        //     {0, 8, 7}
        // };

        int[][] endState = {
            {1, 2, 3},
            {8, 0, 4},
            {7, 6, 5}
        };

        primary(startState, endState);
    }

    private static void primary(int[][] start, int[][] end) {
        PriorityQueue<intermediatepuzzle> q = new PriorityQueue<>(Comparator.comparingInt(intermediatepuzzle::totalCost));
        Set<String> explored = new HashSet<>();

        intermediatepuzzle startintermediatepuzzle = new intermediatepuzzle(start, 0, heuristic(start, end), null);
        q.add(startintermediatepuzzle);

        while (!q.isEmpty()) {
            intermediatepuzzle current = q.poll();

            if (Arrays.deepEquals(current.layout, end)) {
                displaySolution(current);
                return;
            }

            explored.add(Arrays.deepToString(current.layout));

            for (intermediatepuzzle next : getAdjacent(current, end)) {
                if (!explored.contains(Arrays.deepToString(next.layout))) {
                    q.add(next);
                }
            }
        }

        System.out.println("Solution not found.");
    }

    
    private static List<intermediatepuzzle> getAdjacent(intermediatepuzzle current, int[][] target) {
        List<intermediatepuzzle> adjacent = new ArrayList<>();
        int[] moveX = {0, 1, 0, -1};
        int[] moveY = {-1, 0, 1, 0};

        Point zero = findZero(current.layout);

        for (int k = 0; k < 4; k++) {
            int newX = zero.x + moveX[k];
            int newY = zero.y + moveY[k];

            if (isValid(newX, newY)) {
                int[][] newState = copyState(current.layout);

                swap(newState, zero.x, zero.y, newX, newY);

                intermediatepuzzle adjacentintermediatepuzzle = new intermediatepuzzle(newState, current.pathCost + 1, heuristic(newState, target), current);
                adjacent.add(adjacentintermediatepuzzle);
            }
        }

        return adjacent;
    }

    private static Point findZero(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if (state[i][j] == 0) {
                    return new Point(i, j);
                }
            }
        }
        return new Point(-1, -1);
    }

    private static boolean isValid(int x, int y) {
        return x >= 0 && x < 3 && y >= 0 && y < 3;
    }

    private static int[][] copyState(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }

    private static void swap(int[][] state, int x1, int y1, int x2, int y2) {
        int temp = state[x1][y1];
        state[x1][y1] = state[x2][y2];
        state[x2][y2] = temp;
    }

    private static int heuristic(int[][] state, int[][] goal) {
        int h = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] != goal[i][j]) {
                    h++;
                }
            }
        }
        return h;
    }

    private static void displaySolution(intermediatepuzzle intermediatepuzzle) {
        List<intermediatepuzzle> steps = new ArrayList<>();
        while (intermediatepuzzle != null) {
            steps.add(intermediatepuzzle);
            intermediatepuzzle = intermediatepuzzle.ancestor;
        }

        Collections.reverse(steps);

        for (intermediatepuzzle step : steps) {
            printLayout(step.layout);
            System.out.println();
        }
    }

    private static void printLayout(int[][] layout) {
        for (int[] row : layout) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    private static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
