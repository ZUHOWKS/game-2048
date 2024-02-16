package fr.zuhowks.game2048.game;

import fr.zuhowks.game2048.game.client.ClientGame;
import fr.zuhowks.game2048.game.server.ServerGame;
import fr.zuhowks.game2048.game.server.stats.GameStatus;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;

public class GameManager {

    private GameStatus gameStatus;
    private ServerGame serverGame;
    private ClientGame clientGame;

    private PropertyChangeSupport pcs;

    public GameManager() {
        this.gameStatus = GameStatus.NOT_IN_GAME;
        this.serverGame = new ServerGame();
        this.clientGame = new ClientGame();
        this.pcs = new PropertyChangeSupport(this);
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
                    case "moveUp":
                        this.serverGame.moveUp();
                        break;

                    case "moveDown":
                        this.serverGame.moveDown();
                        break;

                    case "moveRight":
                        this.serverGame.moveRight();
                        break;

                    case "moveLeft":
                        this.serverGame.moveLeft();
                        break;
                }

                this.serverGame.generateRandomBox();
                if (!Arrays.deepEquals(clientGame.getGrid(), serverGame.getGrid())) {
                    updateGameStatus();
                    this.clientGame.update(this.serverGame.getGrid(), this.serverGame.getScore());
                }

            } else {
                this.gameStatus = GameStatus.LOOSE;
            }


        }



        //TODO: Action possible in the another game status.

    }

    public void startParty() {
        this.serverGame.resetGrid();
        this.serverGame.resetScore();

        GameStatus oldStatus = this.gameStatus;
        this.gameStatus = GameStatus.IN_GAME;
        pcs.firePropertyChange("gameStatus", oldStatus, this.gameStatus);
    }

    public boolean nextPlay() {
        if (this.serverGame.isGridFinished()) {
            this.gameStatus = GameStatus.WIN;
        } else if (this.serverGame.canPlay()) {
            return true;
        } else {
            this.gameStatus = GameStatus.LOOSE;
        }

        return false;
    }

    public void updateGameStatus() {
        if (this.serverGame.isGridFinished()) {
            this.gameStatus = GameStatus.WIN;
            pcs.firePropertyChange("gameStatus", GameStatus.IN_GAME, this.gameStatus);
        } else if (!nextPlay()) {
            this.gameStatus = GameStatus.LOOSE;
            pcs.firePropertyChange("gameStatus", GameStatus.IN_GAME, this.gameStatus);
        }
    }

    public void leaveParty() {
        GameStatus oldStatus = this.gameStatus;
        this.gameStatus = GameStatus.NOT_IN_GAME;
        pcs.firePropertyChange("gameStatus", oldStatus, this.gameStatus);
    }

    public ClientGame getClientGame() {
        return clientGame;
    }

    public ServerGame getServerGame() {
        return serverGame;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

}
