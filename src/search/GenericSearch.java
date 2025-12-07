package search;

import java.util.*;

public class GenericSearch {

    public static class SearchResult {
        public List<Action> actions;
        public int nodesExpanded;
        public int totalCost;

        public SearchResult(List<Action> actions, int nodesExpanded, int totalCost) {
            this.actions = actions;
            this.nodesExpanded = nodesExpanded;
            this.totalCost = totalCost;
        }

    @Override
    public String toString() {
        StringBuilder o = new StringBuilder();
        o.append("\n");
        o.append("Total Cost: ").append(totalCost).append("\n");
       
            for (Action child : actions) {
                o.append(child.name).append(" ");
                    }
            o.append("\n");
            
            o.append("Nodes Expanded: ").append(nodesExpanded).append("\n");
        return o.toString();
    }
        }

    public static SearchResult search(Problem problem, Frontier frontier) {

        // Initialize frontier with initial node
        Node initial = new Node(problem.initialState());
        frontier.add(initial);

        // For repeated-state checking
        Set<State> explored = new HashSet<>();
        int nodesExpanded = 0;

        while (!frontier.isEmpty()) {

            Node node = frontier.remove();

            // Goal test
            if (problem.isGoal(node.state)) {
                return new SearchResult(extractSolution(node), nodesExpanded, node.pathCost);
            }

            explored.add(node.state);
            nodesExpanded++;

            // Expand node
            for (Node child : problem.expand(node)) {

                // graph-search: avoid revisiting explored states
                if (!explored.contains(child.state) && !frontier.containsState(child.state)) {
                    frontier.add(child);
                }
            }
        }

        return new SearchResult(Collections.emptyList(), nodesExpanded, 0); // failure
    }

    private static List<Action> extractSolution(Node node) {
        List<Action> actions = new ArrayList<>();
        while (node.parent != null) {
            actions.add(node.action);
            node = node.parent;
        }
        Collections.reverse(actions);
        return actions;
    }
}


