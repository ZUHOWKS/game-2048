package fr.zuhowks.game2048.game;

import java.util.Arrays;

public class Game2048 {

    private final int[][] grid;  // grid[row][column]

    public Game2048() {
        this.grid = new int[4][4];
        resetGrid();

    }

    public void resetGrid() {
        for (int row=0; row<4; row++) {
            for (int column=0; column<4; column++) {
                this.grid[row][column] = 0;
            }
        }
    }

    public int getCase(int row, int column) {
        return this.grid[row][column];
    }

    public void setCase(int row, int column, int value) {
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
                final int caseValue = this.getCase(row, column);
                int row_ = row;
                if (caseValue > 0) {
                    while (!(this.getCase(row_, column) == -1 || (row_ == 1) || (this.getCase(row_, column) == 0 && this.getCase(row_ - 1, column) >= 0))) {
                        row_--;
                    }

                    if (this.getCase(row_, column) == -1 || ((this.getCase(row_, column) == 0 || row == row_) && this.getCase(row_ - 1, column) != caseValue)) {
                        this.setCase(row, column, 0);
                        this.setCase(row_ - (this.getCase(row_ - 1, column) == 0 ? 1 : 0), column, caseValue);

                    } else {
                        this.setCase(row, column, 0);
                        this.setCase(row_, column, -1);
                        this.setCase(row_ - 1, column, caseValue * 2);
                    }
                }

                //TODO: Add to AnimationUtils a VectorMovement
            }
        }
    }

    public void moveDown() {

        //TODO: Create AnimationUtils for listed VectorMovement

        for (int column=0; column<4; column++) {
            for (int row=2; row>=0; row--) {
                final int caseValue = this.getCase(row, column);
                int row_ = row;

                while (!(this.getCase(row_, column) == -1 || (row_ == 2) || (this.getCase(row_, column) == 3 && this.getCase(row_ + 1, column) > 0))) {
                    row_++;
                }

                if (this.getCase(row_, column) == -1 || (this.getCase(row_, column) == 0 && this.getCase(row_ + 1, column) != caseValue)) {

                    this.setCase(row, column, 0);
                    this.setCase(row_ + (this.getCase(row_ + 1, column) == 0 ? 1 : 0), column, caseValue);

                } else {
                    this.setCase(row_, column, -1);
                    this.setCase(row_+1, column, caseValue*2);
                }

                //TODO: Add to AnimationUtils a VectorMovement
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int row=0; row<4; row++) {
            stringBuilder.append("[");
            for (int column=0; column<4; column++) {
                String value = String.valueOf(this.getCase(row, column));
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
