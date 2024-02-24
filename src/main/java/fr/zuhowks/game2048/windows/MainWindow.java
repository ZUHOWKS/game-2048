package fr.zuhowks.game2048.windows;

import fr.zuhowks.game2048.Main;
import fr.zuhowks.game2048.canvas.Canvas2048;
import fr.zuhowks.game2048.common.logs.Logger;
import fr.zuhowks.game2048.game.GameManager;
import fr.zuhowks.game2048.listeners.KeyBoardCommandListener;

import javax.swing.*;

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

        Main.LOGGER.log(Logger.ALL, "Configuration des presets claviers.");
        this.addKeyListener(new KeyBoardCommandListener(this.game));

        Main.LOGGER.log(Logger.INFO, "Démarrage de la partie !");
        this.game.startParty();

    }
}
