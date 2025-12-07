package search;

import java.util.*;

public class DFSFrontier implements Frontier {
    private Stack<Node> stack = new Stack<>();

    @Override
    public void add(Node node) { stack.push(node); }

    @Override
    public Node remove() { return stack.pop(); }

    @Override
    public boolean isEmpty() { return stack.isEmpty(); }

    @Override
    public boolean containsState(State s) {
        return stack.stream().anyMatch(n -> n.state.equals(s));
    }
}

