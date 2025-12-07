package delivery;

import grid.*;
import search.*;
import java.util.*;
import model.*;
import data.*;

import search.GenericSearch.SearchResult;



public class DeliverySearch  {

    public static Map<GridState, GridState> convertTunnelsToMap(List<Tunnel> tunnels) {
        Map<GridState, GridState> tunnelMap = new HashMap<>();
    

    for (Tunnel tunnel : tunnels) {
        
        GridState entry = new GridState(tunnel.getEntrance().x, tunnel.getEntrance().y);
        GridState exit = new GridState(tunnel.getExit().x, tunnel.getExit().y);
        tunnelMap.put(entry, exit);
      
    }

    return tunnelMap;
}
    public static String convertactionstoString(List<Action> actions) {
        StringBuilder sb = new StringBuilder();
        for (Action action : actions) {
            sb.append(action.name).append(",");
        }
        return sb.toString().trim();
    }
    
    public static void findDeliveryRoute() {
        // ---- copy of customers list to track remaining ----
        System.out.println("hcost " ) ;
        printMatrix(GridData.H_COSTS);
            System.out.println("vcost " ) ;
        printMatrix(GridData.V_COSTS);
        List<Customer> remainingCustomers = new ArrayList<>(GridData.getCustomers());

        // ---- for each store, find nearest customer ----
     Map<GridState, GridState> tunnelsMap = convertTunnelsToMap(GridData.getTunnels());
    


    for (Store store : GridData.getStores()) {
        


        int minCost = Integer.MAX_VALUE;
        SearchResult bestRoute = null;
        Customer bestCustomer = null;

        // ---- find nearest customer for THIS store ----
        for (Customer customer : remainingCustomers) {

            GridProblem problem = new GridProblem(
                GridData.ROWS, GridData.COLS,
                new GridState(store.getPosition().x, store.getPosition().y),
                new GridState(customer.getPosition().x, customer.getPosition().y),
                GridData.H_COSTS,
                GridData.V_COSTS,
                tunnelsMap
            );

            // Try all strategies
            SearchResult ucs  = GenericSearch.search(problem, new UCSFrontier());
            System.out.println("UCS route from Store " + ucs.totalCost) ;
            SearchResult dfs  = GenericSearch.search(problem, new DFSFrontier());
            System.out.println("DFS route from Store " + dfs.totalCost) ;
            SearchResult bfs  = GenericSearch.search(problem, new BFSFrontier());
            System.out.println("BFS route from Store " + bfs.totalCost) ;
            SearchResult ast  = GenericSearch.search(problem, new AStarFrontier(problem));
            System.out.println("A* route from Store " + ast.totalCost) ;

            // Choose cheapest among them
            for (SearchResult sol : new SearchResult[]{ ucs, dfs, bfs, ast }) {
                if (sol.totalCost < minCost) {
                    minCost = sol.totalCost;
                    bestRoute = sol;
                    bestCustomer = customer;     // save which customer is matched
                }
            }
        }
        System.out.println("Best route from Store " + store.getId() +
            " to Customer " + bestCustomer.getId() +
            " with cost " + minCost + "with actions " + convertactionstoString(bestRoute.actions));

        DeliveryInfo delivery = new DeliveryInfo(store.getPosition(), bestCustomer.getPosition(),convertactionstoString(bestRoute.actions));
        GridData.addDelivery(delivery);

        // ---- remove the matched customer ----
        remainingCustomers.remove(bestCustomer);
    }

    
}
public static void printMatrix(int[][] matrix) {
    for (int[] row : matrix) {
        for (int value : row) {
            System.out.print(value + "\t"); // Print each value with a tab space
        }
        System.out.println(); // Move to the next line after each row
    }
}

   
    
}

    

