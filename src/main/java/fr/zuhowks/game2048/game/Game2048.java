package fr.zuhowks.game2048.game;

import java.util.Arrays;
import java.util.Random;

public class Game2048 {

    private final int[][] grid;  // grid[row][column]

    public Game2048() {
        this.grid = new int[4][4];
        resetGrid();
    }

    private int[][] getGrid() {
        return grid;
    }

    public void resetGrid() {
        for (int row=0; row<4; row++) {
            for (int column=0; column<4; column++) {
                this.grid[row][column] = 0;
            }
        }
    }

    public int getBox(int row, int column) {
        return this.grid[row][column];
    }

    public void setBox(int row, int column, int value) {
        this.grid[row][column] = value;
    }

    public boolean isGridFinished() {

        for (int row=0; row<4; row++) {
            for (int column=0; column<4; column++) {
                 if (this.grid[row][column] == 2048) {
                     return true;
                 }
            }
        }

        return false;
    }

    public void moveUp() {

        //TODO: Create AnimationUtils for listed VectorMovement

        for (int column=0; column<4; column++) {
            for (int row=1; row<4; row++) {
                final int boxValue = this.getBox(row, column);

                if (boxValue > 0) {
                    int row_ = row;

                    // check not isCancelFusionBox || isTheLimit || isBoxDefined
                    while (!(this.getBox(row_, column) == -1 || (row_ == 1) || (this.getBox(row_ - 1, column) > 0))) {
                        row_--;
                    }

                    if (this.getBox(row_, column) == -1 || ((this.getBox(row_, column) == 0 || row == row_) && this.getBox(row_ - 1, column) != boxValue)) {
                        this.setBox(row, column, 0);
                        this.setBox(row_ - (this.getBox(row_ - 1, column) == 0 ? 1 : 0), column, boxValue);

                    } else {
                        this.setBox(row, column, 0);
                        this.setBox(row_, column, -1);
                        this.setBox(row_ - 1, column, boxValue * 2);
                    }

                    //TODO: Add to AnimationUtils a VectorMovement
                }


            }
        }

        resetCancelFusion();
    }

    public void moveDown() {

        //TODO: Create AnimationUtils for listed VectorMovement

        for (int column=0; column<4; column++) {
            for (int row=2; row>=0; row--) {
                final int boxValue = this.getBox(row, column);

                if (boxValue > 0) {
                    int row_ = row;
                    // check not isCancelFusionBox || isTheLimit || isBoxDefined
                    while (!(this.getBox(row_, column) == -1 || (row_ == 2) || (this.getBox(row_ + 1, column) > 0))) {
                        row_++;
                    }

                    if (this.getBox(row_, column) == -1 || ((this.getBox(row_, column) == 0 || row == row_) && this.getBox(row_ + 1, column) != boxValue)) {

                        this.setBox(row, column, 0);
                        this.setBox(row_ + (this.getBox(row_ + 1, column) == 0 ? 1 : 0), column, boxValue);

                    } else {
                        this.setBox(row, column, 0);
                        this.setBox(row_, column, -1);
                        this.setBox(row_ + 1, column, boxValue*2);
                    }

                    //TODO: Add to AnimationUtils a VectorMovement
                }

            }
        }

        resetCancelFusion();
    }

    public void moveRight() {

        //TODO: Create AnimationUtils for listed VectorMovement
        for (int row=0; row<4; row++) {
            for (int column=2; column>=0; column--) {
                final int boxValue = this.getBox(row, column);

                if (boxValue > 0) {
                    int column_ = column;

                    // check not isCancelFusionBox || isTheLimit || isBoxDefined
                    while (!(this.getBox(row, column_) == -1 || (column_ == 2) || (this.getBox(row, column_ + 1) > 0))) {
                        column_++;
                    }

                    if (this.getBox(row, column_) == -1 || ((this.getBox(row, column_) == 0 || column == column_) && this.getBox(row, column_ + 1) != boxValue)) {
                        this.setBox(row, column, 0);
                        this.setBox(row, column_ + (this.getBox(row, column_ + 1) == 0 ? 1 : 0), boxValue);

                    } else {
                        this.setBox(row, column, 0);
                        this.setBox(row, column_, -1);
                        this.setBox(row, column_ + 1, boxValue * 2);
                    }

                    //TODO: Add to AnimationUtils a VectorMovement
                }
            }
        }

        resetCancelFusion();
    }

    public void moveLeft() {

        //TODO: Create AnimationUtils for listed VectorMovement
        for (int row=0; row<4; row++) {
            for (int column=1; column<4; column++) {
                final int boxValue = this.getBox(row, column);

                if (boxValue > 0) {
                    int column_ = column;

                    // check not isCancelFusionBox || isTheLimit || isBoxDefined
                    while (!(this.getBox(row, column_) == -1 || (column_ == 1) || (this.getBox(row, column_ - 1) > 0))) {
                        column_--;
                    }

                    if (this.getBox(row, column_) == -1 || ((this.getBox(row, column_) == 0 || column == column_) && this.getBox(row, column_ - 1) != boxValue)) {
                        this.setBox(row, column, 0);
                        this.setBox(row, column_ - (this.getBox(row, column_ - 1) == 0 ? 1 : 0), boxValue);

                    } else {
                        this.setBox(row, column, 0);
                        this.setBox(row, column_, -1);
                        this.setBox(row, column_ - 1, boxValue * 2);
                    }

                    //TODO: Add to AnimationUtils a VectorMovement
                }
            }
        }

        resetCancelFusion();
    }

    private void resetCancelFusion() {
        for (int row=0; row<4; row++) {
            for (int column=0; column<4; column++) {
                if (this.getBox(row, column) == -1) {
                    this.setBox(row, column, 0);
                }
            }
        }
    }

    public boolean canGenerateRandomBox() {
        for (int row=0; row<4; row++) {
            for (int column=0; column<4; column++) {
                if (this.grid[row][column] == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public void generateRandomBox() {
        int value = 1;
        int row = 0;
        int column = 0;
        while (value != 0) {
            row = (int) (Math.random() * 4);
            column = (int) (Math.random() * 4);
            value = this.getBox(row, column);
        }

        this.setBox(row, column, 2);

        //TODO: Return with AnimationUtils a VectorMovement
    }

    public boolean canPlay() {

        if (!canGenerateRandomBox()) {
            // Create game 2048 and copy the grid
            Game2048 gamePredicated = new Game2048();
            gamePredicated.copyGrid(this.grid);

            // Try all movement action
            gamePredicated.moveUp();
            gamePredicated.moveRight();
            gamePredicated.moveDown();
            gamePredicated.moveLeft();

            return !(Arrays.deepEquals(this.getGrid(), gamePredicated.getGrid()));

        } else {
            return true;
        }
    }

    private void copyGrid(int[][] grid) {
        for (int row=0; row<4; row++) {
            System.arraycopy(grid[row], 0, this.grid[row], 0, 4);
        }
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
