package ui;

import model.*;
import data.GridData;
import utils.GraphicsUtils;
import delivery.DeliverySearch;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ControlPanel extends JPanel {

    private GridPanel gridPanel;
    private MainApp mainApp;
    private JPanel directionsPanel; // Panel pour afficher les directions
    private boolean directionsVisible = false;

    public ControlPanel(GridPanel gridPanel, MainApp mainApp) {
        this.gridPanel = gridPanel;
        this.mainApp = mainApp;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 248, 255));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        add(createTitleSection());
        add(Box.createVerticalStrut(10));
        add(createDataSection());
        add(Box.createVerticalStrut(10));
        add(createButtonSection());

        // Créer le panel des directions (initialement invisible)
        directionsPanel = createDirectionsPanel();
        directionsPanel.setVisible(false);
        add(Box.createVerticalStrut(10));
        add(directionsPanel);

        // Ajouter du glue à la fin pour éviter que le contenu ne se compresse
        add(Box.createVerticalGlue());
    }

    private JPanel createTitleSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 248, 255));

        JLabel title = new JLabel("SYSTÈME DE LIVRAISON - TABLEAU DE BORD");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setForeground(new Color(0, 70, 140));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Visualisation des livraisons en temps réel ");
        subtitle.setFont(new Font("Arial", Font.ITALIC, 10));
        subtitle.setForeground(new Color(100, 100, 100));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(subtitle);

        return panel;
    }

    private JPanel createDataSection() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 0));
        panel.setBackground(new Color(240, 248, 255));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120)); // Limiter la hauteur des entités

        panel.add(createEntityPanel("MAGASINS", GridData.getStores(),
                new Color(220, 240, 255), new Color(70, 130, 180)));

        panel.add(createEntityPanel("CLIENTS", GridData.getCustomers(),
                new Color(220, 255, 220), new Color(60, 140, 60)));

        panel.add(createTunnelPanel());

        return panel;
    }

    private JPanel createEntityPanel(String title, List<?> entities, Color bgColor, Color borderColor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 2),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 11));
        titleLabel.setForeground(borderColor.darker());
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));

        // Utiliser un JScrollPane pour afficher les entités avec scrolling si
        // nécessaire
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(bgColor);

        for (Object entity : entities) {
            String text = "";
            if (entity instanceof Store) {
                Store s = (Store) entity;
                text = String.format("• %s → Position: %s", s.getId(), s.getPosition());
            } else if (entity instanceof Customer) {
                Customer c = (Customer) entity;
                text = String.format("• %s → Position: %s", c.getId(), c.getPosition());
            }

            JLabel item = new JLabel(text);
            item.setFont(new Font("Arial", Font.PLAIN, 9));
            item.setAlignmentX(Component.LEFT_ALIGNMENT);
            itemsPanel.add(item);
            itemsPanel.add(Box.createVerticalStrut(1));
        }

        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        scrollPane.setBackground(bgColor);

        panel.add(scrollPane);

        return panel;
    }

    private JPanel createTunnelPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 240, 220));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 140, 70), 2),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));

        JLabel titleLabel = new JLabel("TUNNELS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 11));
        titleLabel.setForeground(new Color(160, 100, 40));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));

        // Utiliser un JScrollPane pour les tunnels
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(new Color(255, 240, 220));

        for (Tunnel tunnel : GridData.getTunnels()) {
            String text = String.format("• %s: %s → %s",
                    tunnel.getId(), tunnel.getEntrance(), tunnel.getExit());
            JLabel item = new JLabel(text);
            item.setFont(new Font("Arial", Font.PLAIN, 9));
            item.setAlignmentX(Component.LEFT_ALIGNMENT);
            itemsPanel.add(item);
            itemsPanel.add(Box.createVerticalStrut(1));
        }

        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        scrollPane.setBackground(new Color(255, 240, 220));

        panel.add(scrollPane);

        return panel;
    }

    private JPanel createDirectionsPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(new Color(240, 220, 255)); // Couleur violette
    panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 100, 220), 2),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)));

    JLabel titleLabel = new JLabel("DIRECTIONS SUIVIES");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
    titleLabel.setForeground(new Color(120, 60, 180));
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    panel.add(titleLabel);
    panel.add(Box.createVerticalStrut(5));

    // Récupérer les livraisons du tableau
    List<DeliveryInfo> deliveries = GridData.getDeliveries();
    
    if (deliveries.isEmpty()) {
        JLabel emptyLabel = new JLabel("Aucune livraison");
        emptyLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        emptyLabel.setForeground(Color.GRAY);
        emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(emptyLabel);
    } else {
        // Créer un panel avec GridLayout dynamique (nombre de lignes, 2 colonnes)
        int numRows = (int) Math.ceil(deliveries.size() / 2.0);
        JPanel deliveriesGridPanel = new JPanel(new GridLayout(numRows, 2, 15, 8));
        deliveriesGridPanel.setBackground(new Color(240, 220, 255));

        // Ajouter chaque livraison dans le GridLayout
        for (DeliveryInfo delivery : deliveries) {
            String text = String.format("Store @ (%d,%d) --> Customer @ (%d,%d) : %s",
                    delivery.getStartPosition().x,
                    delivery.getStartPosition().y,
                    delivery.getEndPosition().x,
                    delivery.getEndPosition().y,
                    delivery.getDirections());

            JLabel item = new JLabel(text);
            item.setFont(new Font("Arial", Font.PLAIN, 11));
            item.setAlignmentX(Component.LEFT_ALIGNMENT);
            deliveriesGridPanel.add(item);
        }

        panel.add(deliveriesGridPanel);
    }

    return panel;
}
    private JPanel createButtonSection() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(new Color(240, 248, 255));

        // Bouton Retour
        JButton backBtn = GraphicsUtils.createStyledButton(
                "← Retour",
                new Color(100, 100, 100),
                Color.WHITE,
                new Font("Arial", Font.BOLD, 12));
        backBtn.addActionListener(e -> mainApp.showWelcomePage());

        // Bouton Visualiser
        JButton visualizeBtn = GraphicsUtils.createStyledButton(
                "Visualiser",
                new Color(70, 130, 180),
                Color.WHITE,
                new Font("Arial", Font.BOLD, 12));
        visualizeBtn.addActionListener(e -> visualizePaths());

        // Bouton Réinitialiser
        JButton resetBtn = GraphicsUtils.createStyledButton(
                "Réinitialiser",
                new Color(220, 100, 100),
                Color.WHITE,
                new Font("Arial", Font.BOLD, 12));
        resetBtn.addActionListener(e -> resetVisualization());

        panel.add(backBtn);
        panel.add(visualizeBtn);
        panel.add(resetBtn);

        return panel;
    }

    private void visualizePaths() {
        DeliverySearch.findDeliveryRoute();
        gridPanel.reset();

        // Note: Ces chemins sont des exemples
        // Dans une vraie application, ils viendraient du backend
       

            // Chemins de démonstration simples (à remplacer par l'algorithme réel)
            List<DeliveryInfo> deliveries = GridData.getDeliveries();

            for (DeliveryInfo delivery : deliveries) {
                // Ajouter la livraison à GridData

                GridData.addDelivery(delivery);

                // Calculer et afficher le chemin sur la grille
                List<Position> path = gridPanel.computePath(
                        delivery.getStartPosition(),
                        delivery.getDirections());
                gridPanel.addPath(path);
            }
        

        // Afficher le panel des directions
        updateDirectionsPanel();
        directionsPanel.setVisible(true);
        directionsVisible = true;
    }

    private void resetVisualization() {
        gridPanel.reset();
        GridData.clearDeliveries();
        directionsPanel.setVisible(false);
        directionsVisible = false;
    }

    private void updateDirectionsPanel() {
        // Recréer le panel des directions avec les données mises à jour
        Container parent = directionsPanel.getParent();
        int index = -1;

        if (parent instanceof JPanel) {
            JPanel parentPanel = (JPanel) parent;
            Component[] components = parentPanel.getComponents();
            for (int i = 0; i < components.length; i++) {
                if (components[i] == directionsPanel) {
                    index = i;
                    break;
                }
            }

            parentPanel.remove(directionsPanel);
            directionsPanel = createDirectionsPanel();
            if (index != -1) {
                parentPanel.add(directionsPanel, index);
            } else {
                parentPanel.add(directionsPanel);
            }
        }

        revalidate();
        repaint();
    }
}