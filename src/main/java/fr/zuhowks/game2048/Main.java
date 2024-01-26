package fr.zuhowks.game2048;

import fr.zuhowks.game2048.game.Game2048;

public class Main {

    public static void main(String[] args) {

        devGame2048();
    }

    private static void devGame2048() {
        Game2048 game2048 = new Game2048();

        System.out.println(game2048);

        game2048.setCase(0,0, 2);
        game2048.setCase(2,0, 2);
        game2048.setCase(3,0, 2);

        System.out.println(game2048);

        game2048.moveDown();

        System.out.println(game2048);

    }
}