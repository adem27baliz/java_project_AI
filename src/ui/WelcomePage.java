package ui;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class WelcomePage extends JPanel {

    private JTextField rowsField;
    private JTextField colsField;
    private JTextField storesField;
    private JTextField customersField;
    private JTextField tunnelsField;
    private JTextField obstaclesField;

    private MainApp mainApp;

    public WelcomePage(MainApp mainApp) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));

        initComponents();
    }

    private void initComponents() {
        // Panel principal avec effet de gradient
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(70, 130, 180),
                        0, getHeight(), new Color(135, 206, 250));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout());

        // Panneau central
        JPanel centerPanel = createCenterPanel();

        mainPanel.add(centerPanel);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 3),
                BorderFactory.createEmptyBorder(40, 60, 40, 60)));

        // Titre avec effet
        JLabel titleLabel = new JLabel("🚚 SYSTÈME DE LIVRAISON");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(70, 130, 180));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Configuration de la Grille");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(subtitleLabel);
        panel.add(Box.createVerticalStrut(30));

        // Séparateur décoratif
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(400, 2));
        separator.setForeground(new Color(70, 130, 180));
        panel.add(separator);
        panel.add(Box.createVerticalStrut(30));

        // Formulaire
        JPanel formPanel = createFormPanel();
        panel.add(formPanel);

        panel.add(Box.createVerticalStrut(30));

        // Boutons
        JPanel buttonPanel = createButtonPanel();
        panel.add(buttonPanel);

        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Configuration des champs
        String[][] fields = {
                { "📏 Nombre de Lignes (Rows):", "8", "5-20" },
                { "📐 Nombre de Colonnes (Columns):", "8", "5-20" },
                { "🏪 Nombre de Magasins:", "2", "1-10" },
                { "👥 Nombre de Clients:", "2", "1-10" },
                { "🚇 Nombre de Tunnels:", "1", "0-5" },
                { "🚧 Nombre d'Obstacles:", "5", "0-50" }
        };

        JTextField[] textFields = new JTextField[6];

        for (int i = 0; i < fields.length; i++) {
            // Label
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0.4;
            JLabel label = new JLabel(fields[i][0]);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setForeground(new Color(70, 130, 180));
            panel.add(label, gbc);

            // TextField
            gbc.gridx = 1;
            gbc.weightx = 0.4;
            JTextField textField = createStyledTextField(fields[i][1]);
            textFields[i] = textField;
            panel.add(textField, gbc);

            // Info label
            gbc.gridx = 2;
            gbc.weightx = 0.2;
            JLabel infoLabel = new JLabel(fields[i][2]);
            infoLabel.setFont(new Font("Arial", Font.ITALIC, 11));
            infoLabel.setForeground(Color.GRAY);
            panel.add(infoLabel, gbc);
        }

        // Assigner les champs
        rowsField = textFields[0];
        colsField = textFields[1];
        storesField = textFields[2];
        customersField = textFields[3];
        tunnelsField = textFields[4];
        obstaclesField = textFields[5];

        return panel;
    }

    private JTextField createStyledTextField(String defaultValue) {
        JTextField field = new JTextField(defaultValue, 10);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        // Effet focus
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)));
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)));
            }
        });

        return field;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panel.setBackground(Color.WHITE);

        JButton generateButton = createModernButton("✨ Générer la Grille",
                new Color(70, 130, 180), Color.WHITE);
        generateButton.addActionListener(e -> generateGrid());

        JButton resetButton = createModernButton("🔄 Réinitialiser",
                new Color(220, 100, 100), Color.WHITE);
        resetButton.addActionListener(e -> resetFields());

        panel.add(generateButton);
        panel.add(resetButton);

        return panel;
    }

    private JButton createModernButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(fgColor);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 45));

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

    private void generateGrid() {
        try {
            int rows = Integer.parseInt(rowsField.getText().trim());
            int cols = Integer.parseInt(colsField.getText().trim());
            int stores = Integer.parseInt(storesField.getText().trim());
            int customers = Integer.parseInt(customersField.getText().trim());
            int tunnels = Integer.parseInt(tunnelsField.getText().trim());
            int obstacles = Integer.parseInt(obstaclesField.getText().trim());

            GridConfiguration config = new GridConfiguration(
                    rows, cols, stores, customers, tunnels, obstacles);

            if (!config.isValid()) {
                JOptionPane.showMessageDialog(this,
                        "⚠️ Configuration invalide!\n\n" +
                                "Vérifiez les contraintes:\n" +
                                "- Lignes/Colonnes: 5-20\n" +
                                "- Magasins/Clients: 1-" + (rows * cols / 4) + "\n" +
                                "- Tunnels: 0-5\n" +
                                "- Obstacles: 0-" + (rows * cols / 2),
                        "Erreur de Configuration",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Envoyer la configuration au backend (à implémenter)
            System.out.println("📤 Configuration envoyée au backend: " + config);

            // Naviguer vers l'application principale
            mainApp.showDeliveryApp(config);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "❌ Veuillez entrer des nombres valides dans tous les champs!",
                    "Erreur de Saisie",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        rowsField.setText("8");
        colsField.setText("8");
        storesField.setText("2");
        customersField.setText("2");
        tunnelsField.setText("1");
        obstaclesField.setText("5");
    }
}