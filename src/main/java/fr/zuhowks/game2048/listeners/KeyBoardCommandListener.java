package fr.zuhowks.game2048.listeners;

import fr.zuhowks.game2048.game.GameManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardCommandListener implements KeyListener {

    GameManager game;

    public KeyBoardCommandListener(GameManager game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch( keyCode ) {
            case KeyEvent.VK_UP:
                game.performAction("moveUp");
                break;
            case KeyEvent.VK_DOWN:
                game.performAction("moveDown");
                break;
            case KeyEvent.VK_LEFT:
                game.performAction("moveLeft");
                break;
            case KeyEvent.VK_RIGHT :
                game.performAction("moveRight");
                break;
        }
    }
}
