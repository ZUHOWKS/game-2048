package fr.zuhowks.game2048.game;

import fr.zuhowks.game2048.game.client.ClientGame;
import fr.zuhowks.game2048.game.server.ServerGame;
import fr.zuhowks.game2048.game.server.stats.GameStatus;

public class GameManager {

    private GameStatus gameStatus;
    private ServerGame serverGame;
    private ClientGame clientGame;

    public GameManager() {
        this.gameStatus = GameStatus.NOT_IN_GAME;
        this.serverGame = new ServerGame();
        this.clientGame = new ClientGame();
    }

    public GameManager(ServerGame serverGame) {
        this.gameStatus = GameStatus.NOT_IN_GAME;
        this.serverGame = serverGame;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void performAction(String cmd) {
        if (this.getGameStatus() == GameStatus.IN_GAME) {
            if (nextPlay()) {
                switch (cmd) {
                    case "moveUp": {
                        this.serverGame.moveUp();
                    }

                    case "moveDown": {
                        this.serverGame.moveDown();
                    }

                    case "moveRight": {
                        this.serverGame.moveRight();
                    }

                    case "moveLeft": {
                        this.serverGame.moveLeft();
                    }
                }

                this.clientGame.update(this.serverGame.getGridCopy(), this.serverGame.getScore());
            }
        }

        //TODO: Action possible in the another game status.

    }

    public void startParty() {
        this.serverGame.resetGrid();
        this.serverGame.resetScore();
        this.serverGame.generateRandomBox();

        this.gameStatus = GameStatus.IN_GAME;
    }

    public boolean nextPlay() {
        if (this.serverGame.isGridFinished()) {
            this.gameStatus = GameStatus.WIN;
        } else if (this.serverGame.canPlay()) {
            if (this.serverGame.canGenerateRandomBox()) {
                this.serverGame.generateRandomBox();
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

    public ClientGame getClientGame() {
        return clientGame;
    }
}
