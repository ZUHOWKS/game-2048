package fr.zuhowks.game2048;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Fenetre1 extends JFrame {
    private Jeu2048 jeu;
    public static final int HEIGHT = 800;
    public static final int WIDTH = 800;
    Canvas2048 canvas = new Canvas2048(jeu);

    public Fenetre1() {
        this.setSize(WIDTH,HEIGHT);
        this.add(canvas);

    }
}