package ui;

import javax.swing.*;
import java.awt.*;

public class DeliveryApp extends JPanel {

    private GridPanel gridPanel;
    private ControlPanel controlPanel;
    private MainApp mainApp;

    public DeliveryApp(MainApp mainApp) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        // Créer le panneau de la grille
        gridPanel = new GridPanel();

        // Créer le panneau de contrôle
        controlPanel = new ControlPanel(gridPanel, mainApp);

        // Panneau principal
        setBackground(new Color(245, 245, 245));

        // Ajouter un ScrollPane pour la grille avec plus d'espace
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setPreferredSize(new Dimension(800, 600));

        // Wrapper pour le control panel avec scrolling vertical
        JScrollPane controlScrollPane = new JScrollPane(controlPanel);
        controlScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        controlScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        controlScrollPane.setBorder(null);
        // Ne pas limiter la hauteur - laisser le contenu se développer
        controlScrollPane.setPreferredSize(new Dimension(Integer.MAX_VALUE, 300));

        // Assembler les composants
        add(controlScrollPane, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}