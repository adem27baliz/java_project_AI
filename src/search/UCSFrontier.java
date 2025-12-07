package search;
import java.util.*;

public class UCSFrontier implements Frontier {

    private PriorityQueue<Node> pq = new PriorityQueue<>(
        Comparator.comparingInt(n -> n.pathCost)
    );

    @Override
    public void add(Node node) { pq.add(node); }

    @Override
    public Node remove() { return pq.poll(); }

    @Override
    public boolean isEmpty() { return pq.isEmpty(); }

    @Override
    public boolean containsState(State s) {
        return pq.stream().anyMatch(n -> n.state.equals(s));
    }
}
