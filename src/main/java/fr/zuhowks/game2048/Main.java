package fr.zuhowks.game2048;

import fr.zuhowks.game2048.game.server.ServerGame;
import fr.zuhowks.game2048.windows.MainWindow;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame window = new MainWindow();
        window.setFocusable(true);
        window.setVisible(true);
        //devGame2048();
    }

    private static void devGame2048() {
        ServerGame serverGame = new ServerGame();

        System.out.println();

        System.out.println("Test 1");
        System.out.println("------");

        serverGame.setBox(0,0, 2);
        serverGame.setBox(1,0, 2);
        serverGame.setBox(3,0, 4);

        serverGame.setBox(0,1, 2);
        serverGame.setBox(1,1, 2);
        serverGame.setBox(2,1, 2);
        serverGame.setBox(3,1, 2);

        serverGame.setBox(1,2, 2);
        serverGame.setBox(2,2, 2);
        serverGame.setBox(3,2, 2);

        serverGame.setBox(0,3, 4);
        serverGame.setBox(1,3, 2);

        System.out.println("Can play ? " + serverGame.canPlay());

        System.out.println(serverGame);

        serverGame.generateRandomBox();

        System.out.println(serverGame);

        serverGame.moveUp();
        System.out.println(serverGame);
        serverGame.moveDown();
        System.out.println(serverGame);
        serverGame.moveLeft();
        System.out.println(serverGame);
        serverGame.moveRight();
        System.out.println(serverGame);

        System.out.println("Score: " + serverGame.getScore());


        serverGame.resetGrid();
        serverGame.resetScore();

        System.out.println("Test 2");
        System.out.println("------");


        serverGame.setBox(0,0, 2);
        serverGame.setBox(0,1, 2);
        serverGame.setBox(0, 3, 4);

        serverGame.setBox(1,0, 2);
        serverGame.setBox(1,1, 2);
        serverGame.setBox(1,2, 2);
        serverGame.setBox(1,3, 2);

        serverGame.setBox(2,1, 2);
        serverGame.setBox(2,2, 2);
        serverGame.setBox(2,3, 2);

        serverGame.setBox(3,0, 4);
        serverGame.setBox(3,1, 2);

        System.out.println(serverGame);
        serverGame.moveLeft();
        System.out.println(serverGame);
        serverGame.moveRight();
        System.out.println(serverGame);
        serverGame.moveUp();
        System.out.println(serverGame);
        serverGame.moveDown();
        System.out.println(serverGame);

        System.out.println("Score: " + serverGame.getScore());

    }
}