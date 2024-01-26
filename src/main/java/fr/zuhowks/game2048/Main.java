package fr.zuhowks.game2048;

import fr.zuhowks.game2048.game.Game2048;

public class Main {

    public static void main(String[] args) {

        devGame2048();
    }

    private static void devGame2048() {
        Game2048 game2048 = new Game2048();

        System.out.println();

        System.out.println("Test 1");
        System.out.println("------");

        game2048.setBox(0,0, 2);
        game2048.setBox(1,0, 2);
        game2048.setBox(3,0, 4);

        game2048.setBox(0,1, 2);
        game2048.setBox(1,1, 2);
        game2048.setBox(2,1, 2);
        game2048.setBox(3,1, 2);

        game2048.setBox(1,2, 2);
        game2048.setBox(2,2, 2);
        game2048.setBox(3,2, 2);

        game2048.setBox(0,3, 4);
        game2048.setBox(1,3, 2);

        System.out.println("Can play ? " + game2048.canPlay());

        System.out.println(game2048);

        game2048.generateRandomBox();

        System.out.println(game2048);

        game2048.moveUp();
        System.out.println(game2048);
        game2048.moveDown();
        System.out.println(game2048);
        game2048.moveLeft();
        System.out.println(game2048);
        game2048.moveRight();
        System.out.println(game2048);

        System.out.println("Score: " + game2048.getScore());


        game2048.resetGrid();
        game2048.resetScore();

        System.out.println("Test 2");
        System.out.println("------");


        game2048.setBox(0,0, 2);
        game2048.setBox(0,1, 2);
        game2048.setBox(0, 3, 4);

        game2048.setBox(1,0, 2);
        game2048.setBox(1,1, 2);
        game2048.setBox(1,2, 2);
        game2048.setBox(1,3, 2);

        game2048.setBox(2,1, 2);
        game2048.setBox(2,2, 2);
        game2048.setBox(2,3, 2);

        game2048.setBox(3,0, 4);
        game2048.setBox(3,1, 2);

        System.out.println(game2048);
        game2048.moveLeft();
        System.out.println(game2048);
        game2048.moveRight();
        System.out.println(game2048);
        game2048.moveUp();
        System.out.println(game2048);
        game2048.moveDown();
        System.out.println(game2048);

        System.out.println("Score: " + game2048.getScore());

    }
}