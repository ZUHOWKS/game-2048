package fr.zuhowks.game2048.windows;

import fr.zuhowks.game2048.Main;
import fr.zuhowks.game2048.canvas.Canvas2048;
import fr.zuhowks.game2048.common.logs.Logger;
import fr.zuhowks.game2048.game.GameManager;
import fr.zuhowks.game2048.listeners.KeyBoardCommandListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow extends JFrame {

    private final GameManager game;
    public static final int HEIGHT = 800;
    public static final int WIDTH = 800;
    private final Canvas2048 canvas;

    // Fenêtre principale
    public MainWindow() {
        Main.LOGGER.log(Logger.ALL, "Initialisation du contrôleur.");
        this.game = new GameManager();

        Main.LOGGER.log(Logger.ALL, "Initialisation de la vue.");
        this.canvas = new Canvas2048(this.game);

        Main.LOGGER.log(Logger.DEBUG, "Configuration de la fenêtre.");
        this.setSize(WIDTH,HEIGHT);
        this.add(canvas);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,1));
        buttonPanel.setPreferredSize(new java.awt.Dimension(buttonPanel.getPreferredSize().width, 80));

        JButton resetButton = new JButton("Recommencer");
        JButton saveButton = new JButton("Sauver la partie");
        JButton loadButton = new JButton("Charger une partie");
        JButton[] buttons = {resetButton, saveButton, loadButton};

        for (JButton button : buttons){
            button.setBackground(new Color(186, 172, 159));
            buttonPanel.add(button);
            button.setFocusable(false);
        }

        // Restart a party
        resetButton.addActionListener(e -> {
            Main.LOGGER.log(Logger.INFO, "Démarrage d'une nouvelle partie");
            this.game.startParty();
        });

        // Save the current party
        saveButton.addActionListener(e -> {
            try {
                Main.LOGGER.log(Logger.INFO, "Sauvegarde de la partie...");
                this.game.saveParty();
            } catch (IOException ex) {
                Main.LOGGER.log(Logger.INFO, "Erreur => Enregistrement de la partie impossible: " + ex);
                throw new RuntimeException(ex);
            }
        });

        // Open file explorer to select a party
        loadButton.addActionListener(e -> {
            Main.LOGGER.log(Logger.INFO, "Ouverture de l'explorateur des fichiers.");
            JFileChooser choice = new JFileChooser();

            int option = choice.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                try {
                    Main.LOGGER.log(Logger.INFO, "Chargement de la partie...");
                    this.game.loadParty(choice.getSelectedFile().getPath());
                } catch (IOException | ClassNotFoundException ex) {
                    Main.LOGGER.log(Logger.INFO, "Erreur => Chargement de la partie impossible: " + ex);
                    throw new RuntimeException(ex);
                }
            }

        });


        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        Main.LOGGER.log(Logger.ALL, "Configuration des presets claviers.");
        this.addKeyListener(new KeyBoardCommandListener(this.game));

        Main.LOGGER.log(Logger.INFO, "Démarrage de la partie !");
        this.game.startParty();

    }
}
