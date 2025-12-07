package ui;

import javax.swing.*;
import java.awt.*;

import data.*;
import model.*;

public class MainApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    private WelcomePage welcomePage;
    private DeliveryApp deliveryApp;

    private static final String WELCOME_PAGE = "WELCOME";
    private static final String DELIVERY_PAGE = "DELIVERY";

    public MainApp() {
        setTitle("Système de Livraison - Application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Créer la page d'accueil
        welcomePage = new WelcomePage(this);
        contentPanel.add(welcomePage, WELCOME_PAGE);

        add(contentPanel);

        // Afficher la page d'accueil
        showWelcomePage();
    }

    public void showWelcomePage() {
        cardLayout.show(contentPanel, WELCOME_PAGE);
    }

    public void showDeliveryApp(GridConfiguration config) {
        // Configurer GridData avec la nouvelle configuration
        GridData.configure(config);

        // Créer ou recréer DeliveryApp
        if (deliveryApp != null) {
            contentPanel.remove(deliveryApp);
        }

        deliveryApp = new DeliveryApp(this);
        contentPanel.add(deliveryApp, DELIVERY_PAGE);

        // Afficher la page de livraison
        cardLayout.show(contentPanel, DELIVERY_PAGE);

        // Maximiser la fenêtre
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public static void runmain() {
        // Utiliser le Look and Feel du système
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Lancer l'application
        SwingUtilities.invokeLater(() -> {
            MainApp app = new MainApp();
            app.setVisible(true);
        });
    }
}
