package fr.zuhowks.game2048.game.client;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ClientGame {

    private int[][] grid;
    private PropertyChangeSupport pcs;

    public ClientGame() {
        super();
        this.grid = new int[4][4];
        this.pcs = new PropertyChangeSupport(this);
    }

    public void update(int[][] grid) {
        int[][] oldGrid = new int[4][4];

        for (int row=0; row<4; row++) {
            System.arraycopy(this.grid[row], 0, oldGrid[row], 0, 4);
        }

        this.copyGrid(grid);

        this.pcs.firePropertyChange("grid", oldGrid, this.grid);
    }

    private void copyGrid(int[][] grid) {
        for (int row=0; row<4; row++) {
            System.arraycopy(grid[row], 0, this.grid[row], 0, 4);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

}
