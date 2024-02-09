package fr.zuhowks.game2048.game;

public abstract class Game2048 {
    protected final int[][] grid;
    protected int score;

    protected Game2048() {
        this.grid = new int[4][4];
        this.score = 0;

    }

    public int[][] getGridCopy() {
        int[][] grid = new int[4][4];

        for (int row=0; row<4; row++) {
            System.arraycopy(this.grid[row], 0, grid[row], 0, 4);
        }

        return grid;
    }

    public int[][] getGrid() {
        return grid;
    }

    public int getScore() {
        return score;
    }

    public void copyGrid(int[][] grid) {
        for (int row=0; row<4; row++) {
            System.arraycopy(grid[row], 0, this.grid[row], 0, 4);
        }
    }
    public int getBox(int row, int column) {
        return this.grid[row][column];
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int row=0; row<4; row++) {
            stringBuilder.append("[");
            for (int column=0; column<4; column++) {
                String value = String.valueOf(this.getBox(row, column));
                int space = 5 - value.length();
                while (space > 0) {
                    stringBuilder.append(" ");
                    space--;
                }
                stringBuilder.append(value).append(" ");
            }
            stringBuilder.append("]\n");
        }

        return stringBuilder.toString();
    }


}
