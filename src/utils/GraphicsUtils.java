package utils;

import javax.swing.*;
import java.awt.*;

public class GraphicsUtils {

    /**
     * Dessine une flèche entre deux points
     */
    public static void drawArrow(Graphics2D g2d, Point start, Point end, Color color) {
        // Sauvegarder les paramètres originaux
        Color originalColor = g2d.getColor();
        Stroke originalStroke = g2d.getStroke();

        // Dessiner la ligne principale
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(start.x, start.y, end.x, end.y);

        // Calculer l'angle de la flèche
        double angle = Math.atan2(end.y - start.y, end.x - start.x);
        int arrowLength = 10;

        // Dessiner la tête de flèche
        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(end.x, end.y);
        arrowHead.addPoint(
                (int) (end.x - arrowLength * Math.cos(angle - Math.PI / 6)),
                (int) (end.y - arrowLength * Math.sin(angle - Math.PI / 6)));
        arrowHead.addPoint(
                (int) (end.x - arrowLength * Math.cos(angle + Math.PI / 6)),
                (int) (end.y - arrowLength * Math.sin(angle + Math.PI / 6)));

        g2d.setColor(color);
        g2d.fill(arrowHead);
        g2d.setColor(color.darker());
        g2d.draw(arrowHead);

        // Restaurer les paramètres originaux
        g2d.setColor(originalColor);
        g2d.setStroke(originalStroke);
    }

    /**
     * Dessine un signe de route bloquée
     */
    public static void drawBlockSign(Graphics2D g2d, int x, int y) {
        int size = 14;

        Color originalColor = g2d.getColor();
        Stroke originalStroke = g2d.getStroke();

        // Cercle rouge
        g2d.setColor(Color.RED);
        g2d.fillOval(x - size / 2, y - size / 2, size, size);

        // Bordure blanche
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawOval(x - size / 2, y - size / 2, size, size);

        // Ligne diagonale blanche
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(x - size / 3, y - size / 3, x + size / 3, y + size / 3);

        g2d.setColor(originalColor);
        g2d.setStroke(originalStroke);
    }

    /**
     * Obtient la couleur selon le coût
     */
    public static Color getCostColor(int cost) {
        switch (cost) {
            case 0:
                return Color.GREEN.darker();
            case 1:
                return Color.BLUE;
            case 2:
                return Color.ORANGE;
            case 3:
                return Color.RED;
            case 4:
                return Color.MAGENTA.darker();
            default:
                return Color.BLACK;
        }
    }

    /**
     * Crée un bouton stylisé (version compacte)
     */
    public static JButton createStyledButton(String text, Color bgColor, Color fgColor, Font font) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 2),
                BorderFactory.createEmptyBorder(6, 15, 6, 15)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Effet hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }
}