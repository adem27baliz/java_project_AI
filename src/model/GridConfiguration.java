package model;

public class GridConfiguration {
    private int rows;
    private int columns;
    private int numStores;
    private int numCustomers;
    private int numTunnels;
    private int numObstacles;

    public GridConfiguration(int rows, int columns, int numStores,
            int numCustomers, int numTunnels, int numObstacles) {
        this.rows = rows;
        this.columns = columns;
        this.numStores = numStores;
        this.numCustomers = numCustomers;
        this.numTunnels = numTunnels;
        this.numObstacles = numObstacles;
    }

    // Getters
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getNumStores() {
        return numStores;
    }

    public int getNumCustomers() {
        return numCustomers;
    }

    public int getNumTunnels() {
        return numTunnels;
    }

    public int getNumObstacles() {
        return numObstacles;
    }

    // Setters
    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setNumStores(int numStores) {
        this.numStores = numStores;
    }

    public void setNumCustomers(int numCustomers) {
        this.numCustomers = numCustomers;
    }

    public void setNumTunnels(int numTunnels) {
        this.numTunnels = numTunnels;
    }

    public void setNumObstacles(int numObstacles) {
        this.numObstacles = numObstacles;
    }

    @Override
    public String toString() {
        return "GridConfiguration{" +
                "rows=" + rows +
                ", columns=" + columns +
                ", stores=" + numStores +
                ", customers=" + numCustomers +
                ", tunnels=" + numTunnels +
                ", obstacles=" + numObstacles +
                '}';
    }

    // Validation
    public boolean isValid() {
        return rows >= 5 && rows <= 20 &&
                columns >= 5 && columns <= 20 &&
                numStores >= 1 && numStores <= (rows * columns / 4) &&
                numCustomers >= 1 && numCustomers <= (rows * columns / 4) &&
                numTunnels >= 0 && numTunnels <= 5 &&
                numObstacles >= 0 && numObstacles <= (rows * columns / 2);
    }
}