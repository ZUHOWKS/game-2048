package fr.zuhowks.game2048.windows;

import fr.zuhowks.game2048.canvas.Canvas2048;
import fr.zuhowks.game2048.game.GameManager;

import javax.swing.*;

public class MainWindow extends JFrame {
    private GameManager game;
    public static final int HEIGHT = 800;
    public static final int WIDTH = 800;
    Canvas2048 canvas;
    public MainWindow() {
        this.game = new GameManager();
        this.canvas = new Canvas2048(this.game);

        this.setSize(WIDTH,HEIGHT);
        this.add(canvas);

    }
}
