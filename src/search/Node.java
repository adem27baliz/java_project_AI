package search;

public class Node {
    public State state;
    public Node parent;
    public Action action;
    public int pathCost;

    public Node(State state) {
        this.state = state;
        this.parent = null;
        this.action = null;
        this.pathCost = 0;
    }

    public Node(State state, Node parent, Action action, int pathCost) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.pathCost = pathCost;
    }
}
