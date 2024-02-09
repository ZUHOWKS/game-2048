package fr.zuhowks.game2048.game.server;

import fr.zuhowks.game2048.game.Game2048;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ServerGame extends Game2048 {


    public ServerGame() {
        super();
        resetGrid();
    }


    public void resetScore() {
        this.score = 0;
    }

    public void addScore(int toAdd) {
        this.score+=toAdd;
    }

    public void resetGrid() {
        for (int row=0; row<4; row++) {
            for (int column=0; column<4; column++) {
                this.grid[row][column] = 0;
            }
        }
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

                        int newValue = boxValue * 2;
                        this.setBox(row_ - 1, column, newValue);

                        this.addScore(newValue);
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

                        int newValue = boxValue * 2;
                        this.setBox(row_ + 1, column, newValue);

                        this.addScore(newValue);
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

                        int newValue = boxValue * 2;
                        this.setBox(row, column_ + 1, newValue);

                        this.addScore(newValue);
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

                        int newValue = boxValue * 2;
                        this.setBox(row, column_ - 1, newValue);

                        this.addScore(newValue);

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

        ArrayList<int[]> box = new ArrayList<>();
        for (int row=0; row<4; row++) {
            for (int column=0; column<4; column++) {
                if (this.grid[row][column] == 0) {
                    int[] pos = new int[2];
                    pos[0] = row;
                    pos[1] = column;
                    box.add(pos);
                }
            }
        }
        if (!box.isEmpty()) {
            int[] boxChooses = box.get((int) (Math.random() * (box.size() - 1)));
            this.setBox(boxChooses[0], boxChooses[1], 2);
        }


        //TODO: Return with AnimationUtils a VectorMovement
    }

    public boolean canPlay() {

        if (!canGenerateRandomBox()) {
            // Create game 2048 and copy the grid
            ServerGame gamePredicated = new ServerGame();
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


}
