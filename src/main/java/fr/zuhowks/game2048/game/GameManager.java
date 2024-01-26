package fr.zuhowks.game2048.game;

import fr.zuhowks.game2048.game.stats.GameStatus;

public class GameManager {

    private GameStatus gameStatus;
    private Game2048 game2048;

    public GameManager() {
        this.gameStatus = GameStatus.NOT_IN_GAME;
        this.game2048 = new Game2048();
    }

    public GameManager(Game2048 game2048) {
        this.gameStatus = GameStatus.NOT_IN_GAME;
        this.game2048 = game2048;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void performAction(String cmd) {
        if (this.getGameStatus() == GameStatus.IN_GAME) {
            if (nextPlay()) {
                switch (cmd) {
                    case "moveUp": {
                        this.game2048.moveUp();
                    }

                    case "moveDown": {
                        this.game2048.moveDown();
                    }

                    case "moveRight": {
                        this.game2048.moveRight();
                    }

                    case "moveLeft": {
                        this.game2048.moveLeft();
                    }
                }
            }
        }

        //TODO: Action possible in the another game status.

    }

    public void startParty() {
        this.game2048.resetGrid();
        this.game2048.resetScore();
        this.game2048.generateRandomBox();

        this.gameStatus = GameStatus.IN_GAME;
    }

    public boolean nextPlay() {
        if (this.game2048.isGridFinished()) {
            this.gameStatus = GameStatus.WIN;
        } else if (this.game2048.canPlay()) {
            if (this.game2048.canGenerateRandomBox()) {
                this.game2048.generateRandomBox();
            }

            return true;
        } else {
            this.gameStatus = GameStatus.LOOSE;
        }

        return false;
    }

    public void leaveParty() {
        this.gameStatus = GameStatus.NOT_IN_GAME;
    }
}
