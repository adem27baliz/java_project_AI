package ui;

import model.*;
import data.GridData;
import utils.GraphicsUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GridPanel extends JPanel {
    private static final int CELL = 50;
    private static final int OFFSET = 50;

    private List<List<Position>> allPaths = new ArrayList<>();
    private List<Position> truckPositions = new ArrayList<>();

    public GridPanel() {
        setBackground(new Color(240, 240, 240));
        updatePreferredSize();
    }

    private void updatePreferredSize() {
        int gridWidth = OFFSET * 2 + (GridData.COLS - 1) * CELL;
        int gridHeight = OFFSET * 2 + (GridData.ROWS - 1) * CELL;
        setPreferredSize(new Dimension(gridWidth, gridHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculer le décalage pour centrer la grille
        int gridWidth = (GridData.COLS - 1) * CELL;
        int gridHeight = (GridData.ROWS - 1) * CELL;

        int xOffset = (getWidth() - gridWidth) / 2;
        int yOffset = (getHeight() - gridHeight) / 2;

        // S'assurer que les offsets ne sont pas négatifs
        xOffset = Math.max(xOffset, OFFSET);
        yOffset = Math.max(yOffset, OFFSET);

        drawCosts(g2d, xOffset, yOffset);
        drawGrid(g2d, xOffset, yOffset);
        drawIntersections(g2d, xOffset, yOffset);
        drawEntities(g2d, xOffset, yOffset);
        drawPaths(g2d, xOffset, yOffset);
        drawTrucks(g2d, xOffset, yOffset);
    }

    private void drawGrid(Graphics2D g2d, int xOffset, int yOffset) {
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(1));

        int gridWidth = (GridData.COLS - 1) * CELL;
        int gridHeight = (GridData.ROWS - 1) * CELL;

        // Lignes horizontales
        for (int i = 0; i < GridData.ROWS; i++) {
            int y = yOffset + i * CELL;
            g2d.drawLine(xOffset, y, xOffset + gridWidth, y);
        }

        // Lignes verticales
        for (int i = 0; i < GridData.COLS; i++) {
            int x = xOffset + i * CELL;
            g2d.drawLine(x, yOffset, x, yOffset + gridHeight);
        }
    }

    private void drawIntersections(Graphics2D g2d, int xOffset, int yOffset) {
        g2d.setColor(Color.BLACK);
        for (int y = 0; y < GridData.ROWS; y++) {
            for (int x = 0; x < GridData.COLS; x++) {
                int px = xOffset + x * CELL;
                int py = yOffset + y * CELL;
                g2d.fillOval(px - 4, py - 4, 8, 8);

                // Coordonnées
                g2d.setColor(Color.GRAY);
                g2d.setFont(new Font("Arial", Font.PLAIN, 9));
                g2d.drawString(x + "," + y, px + 6, py - 6);
                g2d.setColor(Color.BLACK);
            }
        }
    }

    private void drawCosts(Graphics2D g2d, int xOffset, int yOffset) {
        g2d.setFont(new Font("Arial", Font.BOLD, 12));

        // Coûts horizontaux
        for (int y = 0; y < GridData.ROWS; y++) {
            for (int x = 0; x < GridData.COLS - 1; x++) {
                int cost = GridData.H_COSTS[y][x];
                int x1 = xOffset + x * CELL;
                int x2 = xOffset + (x + 1) * CELL;
                int yPos = yOffset + y * CELL;

                int centerX = (x1 + x2) / 2;
                drawCostOnHorizontalLine(g2d, String.valueOf(cost), centerX, yPos);
            }
        }

        // Coûts verticaux
        for (int y = 0; y < GridData.ROWS - 1; y++) {
            for (int x = 0; x < GridData.COLS; x++) {
                int cost = GridData.V_COSTS[y][x];
                int xPos = xOffset + x * CELL;
                int y1 = yOffset + y * CELL;
                int y2 = yOffset + (y + 1) * CELL;

                int centerY = (y1 + y2) / 2;
                drawCostOnVerticalLine(g2d, String.valueOf(cost), xPos, centerY);
            }
        }
    }

    private void drawCostOnHorizontalLine(Graphics2D g2d, String text, int x, int y) {
        if (text.equals("0")) {
            GraphicsUtils.drawBlockSign(g2d, x, y);
        } else {
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(text);

            g2d.setColor(getBackground());
            g2d.fillRect(x - textWidth / 2 - 2, y - 13, textWidth + 4, 16);

            g2d.setColor(GraphicsUtils.getCostColor(Integer.parseInt(text)));
            g2d.drawString(text, x - textWidth / 2, y - 4);
        }
    }

    private void drawCostOnVerticalLine(Graphics2D g2d, String text, int x, int y) {
        if (text.equals("0")) {
            GraphicsUtils.drawBlockSign(g2d, x, y);
        } else {
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(text);

            g2d.setColor(getBackground());
            g2d.fillRect(x + 1, y - 7, textWidth + 4, 16);

            g2d.setColor(GraphicsUtils.getCostColor(Integer.parseInt(text)));
            g2d.drawString(text, x + 3, y + 4);
        }
    }

    private void drawEntities(Graphics2D g2d, int xOffset, int yOffset) {
        drawStores(g2d, xOffset, yOffset);
        drawCustomers(g2d, xOffset, yOffset);
        drawTunnels(g2d, xOffset, yOffset);
    }

    private void drawStores(Graphics2D g2d, int xOffset, int yOffset) {
        g2d.setStroke(new BasicStroke(2));
        for (Store store : GridData.getStores()) {
            Point p = toPoint(store.getPosition(), xOffset, yOffset);
            g2d.setColor(store.getColor());
            g2d.fillRect(p.x - 10, p.y - 10, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(p.x - 10, p.y - 10, 20, 20);
            g2d.setColor(Color.WHITE);
            g2d.drawString(store.getId(), p.x - 5, p.y + 4);
        }
    }

    private void drawCustomers(Graphics2D g2d, int xOffset, int yOffset) {
        for (Customer customer : GridData.getCustomers()) {
            Point p = toPoint(customer.getPosition(), xOffset, yOffset);
            int[] xs = { p.x, p.x - 10, p.x + 10 };
            int[] ys = { p.y - 10, p.y + 10, p.y + 10 };
            g2d.setColor(customer.getColor());
            g2d.fillPolygon(xs, ys, 3);
            g2d.setColor(Color.BLACK);
            g2d.drawPolygon(xs, ys, 3);
            g2d.setColor(Color.WHITE);
            g2d.drawString(customer.getId(), p.x - 5, p.y + 4);
        }
    }

    private void drawTunnels(Graphics2D g2d, int xOffset, int yOffset) {
        for (Tunnel tunnel : GridData.getTunnels()) {
            Point p1 = toPoint(tunnel.getEntrance(), xOffset, yOffset);
            Point p2 = toPoint(tunnel.getExit(), xOffset, yOffset);

            g2d.setColor(tunnel.getColor());
            Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[] { 9 }, 0);
            g2d.setStroke(dashed);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);

            g2d.setStroke(new BasicStroke(2));
            g2d.fillOval(p1.x - 8, p1.y - 8, 16, 16);
            g2d.fillOval(p2.x - 8, p2.y - 8, 16, 16);

            g2d.setColor(Color.WHITE);
            g2d.drawString("E", p1.x - 3, p1.y + 4);
            g2d.drawString("S", p2.x - 3, p2.y + 4);
        }
    }

    private void drawPaths(Graphics2D g2d, int xOffset, int yOffset) {
        if (allPaths.isEmpty())
            return;

        Color[] palette = { Color.BLUE, Color.MAGENTA, Color.RED, Color.CYAN, Color.ORANGE };
        truckPositions.clear();

        for (int idx = 0; idx < allPaths.size(); idx++) {
            List<Position> path = allPaths.get(idx);
            if (path.size() > 1) {
                Color color = palette[idx % palette.length];

                for (int i = 0; i < path.size() - 1; i++) {
                    Point p1 = toPoint(path.get(i), xOffset, yOffset);
                    Point p2 = toPoint(path.get(i + 1), xOffset, yOffset);
                    GraphicsUtils.drawArrow(g2d, p1, p2, color);
                }

                truckPositions.add(path.get(path.size() - 1));
            }
        }
    }

    private void drawTrucks(Graphics2D g2d, int xOffset, int yOffset) {
        for (Position truckPos : truckPositions) {
            Point p = toPoint(truckPos, xOffset, yOffset);
            g2d.setColor(Color.YELLOW);
            g2d.fillOval(p.x - 10, p.y - 10, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(p.x - 10, p.y - 10, 20, 20);
            g2d.drawString("T", p.x - 3, p.y + 4);
        }
    }

    private Point toPoint(Position pos, int xOffset, int yOffset) {
        return new Point(xOffset + pos.x * CELL, yOffset + pos.y * CELL);
    }

    public List<Position> computePath(Position startPos, String directions) {
        List<Position> path = new ArrayList<>();
        Position current = startPos;
        path.add(current);

        String[] moves = directions.split(",");
        for (String move : moves) {
            move = move.trim().toLowerCase();

            if (move.equals("tunnel")) {
                for (Tunnel tunnel : GridData.getTunnels()) {
                    Position newPos = tunnel.getOtherEnd(current);
                    if (!newPos.equals(current)) {
                        current = newPos;
                        break;
                    }
                }
            } else {
                current = current.move(move);
            }
            path.add(current);
        }

        return path;
    }

    public void reset() {
        allPaths.clear();
        truckPositions.clear();
        updatePreferredSize();
        repaint();
    }

    public void addPath(List<Position> path) {
        allPaths.add(path);
        repaint();
    }
}