package data;

import model.*;
import java.awt.Color;
import java.util.*;

public class GridData {

    public static int ROWS = 8;
    public static int COLS = 8;

    // Coûts des segments
    public static int[][] H_COSTS;
    public static int[][] V_COSTS;

    // Entités
    private static List<Store> stores = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Tunnel> tunnels = new ArrayList<>();

    // Livraisons (DeliveryInfo)
    private static List<DeliveryInfo> deliveries = new ArrayList<>();

    static {
        initArrays();
        initTestData();
    }

    private static void initArrays() {
        H_COSTS = new int[ROWS][COLS - 1];
        V_COSTS = new int[ROWS - 1][COLS];
    }

    public static void configure(GridConfiguration config) {
        ROWS = config.getRows();
        COLS = config.getColumns();

        // Réinitialiser les tableaux avec les nouvelles dimensions
        initArrays();

        // Générer les données
        generateData(config);
    }

    private static void generateData(GridConfiguration config) {
        stores.clear();
        customers.clear();
        tunnels.clear();
        deliveries.clear();

        Random rand = new Random();

        // Initialiser les coûts horizontaux avec valeurs 1-4
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS - 1; c++) {
                H_COSTS[r][c] = rand.nextInt(4) + 1; // 1-4 par défaut
            }
        }

        // Initialiser les coûts verticaux avec valeurs 1-4
        for (int r = 0; r < ROWS - 1; r++) {
            for (int c = 0; c < COLS; c++) {
                V_COSTS[r][c] = rand.nextInt(4) + 1; // 1-4 par défaut
            }
        }

        // Placer exactement numObstacles obstacles (cost = 0)
        int obstaclesPlaced = 0;
        int maxObstacles = config.getNumObstacles();

        while (obstaclesPlaced < maxObstacles) {
            // Placer obstacle horizontal
            if (obstaclesPlaced < maxObstacles) {
                int row = rand.nextInt(ROWS);
                int col = rand.nextInt(COLS - 1);
                if (H_COSTS[row][col] != 0) {
                    H_COSTS[row][col] = 0;
                    obstaclesPlaced++;
                }
            }

            // Placer obstacle vertical
            if (obstaclesPlaced < maxObstacles) {
                int row = rand.nextInt(ROWS - 1);
                int col = rand.nextInt(COLS);
                if (V_COSTS[row][col] != 0) {
                    V_COSTS[row][col] = 0;
                    obstaclesPlaced++;
                }
            }
        }

        // Générer des positions aléatoires uniques
        Set<Position> usedPositions = new HashSet<>();

        // Ajouter les magasins
        for (int i = 0; i < config.getNumStores(); i++) {
            Position pos = getRandomPosition(rand, usedPositions);
            stores.add(new Store(pos, "S" + (i + 1), Color.GRAY));
        }

        // Ajouter les clients
        for (int i = 0; i < config.getNumCustomers(); i++) {
            Position pos = getRandomPosition(rand, usedPositions);
            customers.add(new Customer(pos, "C" + (i + 1), Color.GREEN));
        }

        // Ajouter les tunnels
        for (int i = 0; i < config.getNumTunnels(); i++) {
            Position entrance = getRandomPosition(rand, usedPositions);
            Position exit = getRandomPosition(rand, usedPositions);
            tunnels.add(new Tunnel(entrance, exit, "T" + (i + 1), Color.ORANGE));
        }
    }

    private static Position getRandomPosition(Random rand, Set<Position> usedPositions) {
        Position pos;
        do {
            int x = rand.nextInt(COLS);
            int y = rand.nextInt(ROWS);
            pos = new Position(x, y);
        } while (usedPositions.contains(pos));

        usedPositions.add(pos);
        return pos;
    }

    private static void initTestData() {
        stores.clear();
        customers.clear();
        tunnels.clear();
        deliveries.clear();

        Random rand = new Random();

        // Initialiser les coûts horizontaux
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS - 1; c++) {
                H_COSTS[r][c] = rand.nextInt(5);
            }
        }

        // Initialiser les coûts verticaux
        for (int r = 0; r < ROWS - 1; r++) {
            for (int c = 0; c < COLS; c++) {
                V_COSTS[r][c] = rand.nextInt(5);
            }
        }

        // Ajouter les entités par défaut
        stores.add(new Store(new Position(1, 1), "S1", Color.GRAY));
        stores.add(new Store(new Position(6, 5), "S2", Color.GRAY));

        customers.add(new Customer(new Position(4, 3), "C1", Color.GREEN));
        customers.add(new Customer(new Position(2, 6), "C2", Color.GREEN));

        tunnels.add(new Tunnel(
                new Position(3, 2),
                new Position(5, 4),
                "T1",
                Color.ORANGE));
    }

    // Getters
    public static List<Store> getStores() {
        return new ArrayList<>(stores);
    }

    public static List<Customer> getCustomers() {
        return new ArrayList<>(customers);
    }

    public static List<Tunnel> getTunnels() {
        return new ArrayList<>(tunnels);
    }

    public static List<DeliveryInfo> getDeliveries() {
        return new ArrayList<>(deliveries);
    }

    public static int getHorizontalCost(int row, int col) {
        if (row >= 0 && row < ROWS && col >= 0 && col < COLS - 1) {
            return H_COSTS[row][col];
        }
        return -1;
    }

    public static int getVerticalCost(int row, int col) {
        if (row >= 0 && row < ROWS - 1 && col >= 0 && col < COLS) {
            return V_COSTS[row][col];
        }
        return -1;
    }

    // Ajouter une livraison
    public static void addDelivery(DeliveryInfo delivery) {
        deliveries.add(delivery);
    }

    // Réinitialiser les livraisons
    public static void clearDeliveries() {
        deliveries.clear();
    }

    // Méthode pour réinitialiser les données
    public static void reset() {
        initTestData();
    }
}