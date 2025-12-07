package search;
import java.util.*;

public class BFSFrontier implements Frontier {
    private Queue<Node> queue = new LinkedList<>();

    @Override
    public void add(Node node) { queue.add(node); }

    @Override
    public Node remove() { return queue.poll(); }

    @Override
    public boolean isEmpty() { return queue.isEmpty(); }

    @Override
    public boolean containsState(State s) {
        return queue.stream().anyMatch(n -> n.state.equals(s));
    }
}
