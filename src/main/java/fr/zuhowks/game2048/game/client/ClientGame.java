package fr.zuhowks.game2048.game.client;

import fr.zuhowks.game2048.Main;
import fr.zuhowks.game2048.common.game.Game2048;
import fr.zuhowks.game2048.common.logs.Logger;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ClientGame extends Game2048 {
    private final PropertyChangeSupport pcs;

    public ClientGame() {
        super();
        this.pcs = new PropertyChangeSupport(this);
    }


    public void update(int[][] newGrid, int newScore) {

        Main.LOGGER.log(Logger.ALL, "Vue => Copie de la grille serveur.");
        int[][] oldGrid = new int[4][4];

        for (int row=0; row<4; row++) {
            System.arraycopy(this.grid[row], 0, oldGrid[row], 0, 4);
        }

        this.copyGrid(newGrid);

        Main.LOGGER.log(Logger.ALL, "Vue => Copie du score serveur.");
        int oldScore = this.score;
        this.score = newScore;

        Main.LOGGER.log(Logger.ALL, "Vue => Réactualisation des informations.");
        this.pcs.firePropertyChange("grid", oldGrid, this.grid);
        this.pcs.firePropertyChange("score", oldScore, this.score);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

}
