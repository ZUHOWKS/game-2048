package fr.zuhowks.game2048.game;

import fr.zuhowks.game2048.game.stats.GameStatus;

public class GameManager {

    private GameStatus isInGame;
    private Game2048 game2048;

    public GameManager() {
        this.isInGame = GameStatus.NOT_IN_GAME;
        this.game2048 = new Game2048();
    }

    public GameManager(Game2048 game2048) {
        this.isInGame = GameStatus.NOT_IN_GAME;
        this.game2048 = game2048;
    }

    public GameStatus getIsInGame() {
        return isInGame;
    }

    public void startParty() {
        //TODO: Setup party
        this.isInGame = GameStatus.IN_GAME;
    }
}
