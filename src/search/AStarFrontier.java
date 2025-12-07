package search;

import java.util.*;
import grid.GridState;
import grid.GridProblem;

public class AStarFrontier implements Frontier {

    private PriorityQueue<Node> pq;
    private Map<GridState, GridState> tunnels;
    private GridProblem problem;

    public AStarFrontier( GridProblem problem) {
        this.tunnels = (problem.tunnels == null ? new HashMap<>() : problem.tunnels);
        this.problem = problem;
        this.pq = new PriorityQueue<>(Comparator.comparingInt(this::f));
    }

    /** A* heuristic: admissible and consistent */
    public int heuristic(GridState current, GridState goal) {

        // 1) Standard Manhattan heuristic
        int manhattan = manhattanDistance(current, goal);

        // 2) Tunnel shortcut heuristic (admissible version)
        int bestTunnelHeuristic = Integer.MAX_VALUE;

        for (Map.Entry<GridState, GridState> tunnel : tunnels.entrySet()) {
            GridState a = tunnel.getKey();
            GridState b = tunnel.getValue();

            // Using a → b
            int h1 =
                manhattanDistance(current, a) +
                // tunnel cost = manhattan(a, b)
                manhattanDistance(a, b) +
                manhattanDistance(b, goal);

            // Using b → a (treat tunnel as bidirectional)
            int h2 =
                manhattanDistance(current, b) +
                manhattanDistance(b, a) +
                manhattanDistance(a, goal);

            bestTunnelHeuristic = Math.min(bestTunnelHeuristic, Math.min(h1, h2));
        }

        // If no tunnels, bestTunnelHeuristic = INF
        return Math.min(manhattan, bestTunnelHeuristic);
    }

    private int manhattanDistance(GridState a, GridState b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private int f(Node node) {
        GridState s = (GridState) node.state;
        GridState g = problem.goal;
        return node.pathCost + heuristic(s, g);
    }

    @Override
    public void add(Node node) {
        pq.add(node);
    }

    @Override
    public Node remove() {
        return pq.poll();
    }

    @Override
    public boolean isEmpty() {
        return pq.isEmpty();
    }

    @Override
    public boolean containsState(State s) {
        return pq.stream().anyMatch(n -> n.state.equals(s));
    }
}
