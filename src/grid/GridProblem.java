package grid;

import java.util.*;
import search.Action;
import search.Node;
import search.Problem;
import search.State;

public class GridProblem implements Problem {

    private int rows, cols;
    private GridState initial;
    public GridState goal;

    // Horizontal road costs: rows × (cols-1)
    private int[][] hCost;

    // Vertical road costs: (rows-1) × cols
    private int[][] vCost;

    // tunnels: entrance → exit
    public Map<GridState, GridState> tunnels = new HashMap<>();

    public GridProblem(
            int rows,
            int cols,
            GridState initial,
            GridState goal,
            int[][] hCost,
            int[][] vCost,
            Map<GridState, GridState> tunnels
    ) {
        this.rows = rows;
        this.cols = cols;
        this.initial = initial;
        this.goal = goal;
        this.hCost = hCost;
        this.vCost = vCost;
        this.tunnels = tunnels;
    }

    @Override
    public State initialState() {
        return initial;
    }

    @Override
    public boolean isGoal(State state) {
        return state.equals(goal);
    }

    private boolean inBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < rows && y < cols;
    }

    private String actionName(int dx, int dy) {
        if (dx == 1 && dy == 0) return "down";
        if (dx == -1 && dy == 0) return "up";
        if (dx == 0 && dy == 1) return "right";
        if (dx == 0 && dy == -1) return "left";
        return "";
    }
    private int getMoveCost(int x, int y, int nx, int ny) {
    int dx = nx - x;
    int dy = ny - y;
    System.out.println("Move from (" + x + "," + y + ") to (" + nx + "," + ny + ")") ;

    // ✅ RIGHT: (x, y) → (x, y+1) → use vCost[x][y]
    if (dx == 0 && dy == 1) {
        System.out.println("Cost v: " + vCost[y][x]) ;
        return vCost[y][x];
    }

    // ✅ LEFT: (x, y) → (x, y-1) → use vCost[x][y-1]
    if (dx == 0 && dy == -1) {
        System.out.println("Cost v: " + vCost[ny][x]) ;
        return vCost[ny][x];
    }

    // ✅ DOWN: (x, y) → (x+1, y) → use hCost[x][y]
    if (dx == 1 && dy == 0) {
        System.out.println("Cost h : " + hCost[y][x]) ;
        return hCost[y][x];
    }

    // ✅ UP: (x, y) → (x-1, y) → use hCost[x-1][y]
    if (dx == -1 && dy == 0) {
        System.out.println("Cost h : " + hCost[y ][nx]) ;
        return hCost[y][nx];
    }

    // ❌ Non-adjacent move
    return 0;
}


    @Override
    public List<Node> expand(Node node) {
        List<Node> children = new ArrayList<>();
        GridState s = (GridState) node.state;

        // 4 directions: down, up, right, left
        int[][] moves = {
            {1, 0},     // down
            {-1, 0},    // up
            {0, 1},     // right
            {0, -1}     // left
        };
        

        for (int[] mv : moves) {
            int dx = mv[0];
            int dy = mv[1];

            int nx = s.x + dx;
            int ny = s.y + dy;

            if (!inBounds(nx, ny)) continue;

            int cost = 0;

           cost =getMoveCost(s.x, s.y, nx, ny);

            // Check blocked
            if (cost == 0) continue;

            GridState ns = new GridState(nx, ny);
            String act = actionName(dx, dy);

            children.add(
                new Node(
                    ns,
                    node,
                    new Action(act, cost),
                    node.pathCost + cost
                )
            );
        }

        // ------------ TUNNELS ------------
        if (tunnels.containsKey(s)) {
            GridState exit = tunnels.get(s);

            int tunnelCost =
                    Math.abs(s.x - exit.x) + Math.abs(s.y - exit.y);

            children.add(
                new Node(
                    exit,
                    node,
                    new Action("tunnel", tunnelCost),
                    node.pathCost + tunnelCost
                )
            );
        }

        return children;
    }
}
