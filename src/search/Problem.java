package search;

import java.util.List;

public interface Problem {
    State initialState();
    boolean isGoal(State state);
    List<Node> expand(Node node);
}
