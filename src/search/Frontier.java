package search;

public interface Frontier {
    void add(Node node);
    Node remove();
    boolean isEmpty();
    boolean containsState(State state);
}

