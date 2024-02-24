package fr.zuhowks.game2048.game.server;

import fr.zuhowks.game2048.Main;
import fr.zuhowks.game2048.common.game.Game2048;
import fr.zuhowks.game2048.common.logs.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerGame extends Game2048 implements Serializable {


    public ServerGame() {
        super();
        resetGrid();
    }


    /**
     * Reset the score
     */
    public void resetScore() {
        this.score = 0;
        Main.LOGGER.log(Logger.ALL, "Model => Score modifié à " + this.score + ".");
    }


    /**
     * Add points to the game score.
     *
     * @param toAdd number of points to adds to the game score.
     */
    public void addScore(int toAdd) {
        this.score+=toAdd;
        Main.LOGGER.log(Logger.ALL, "Model => Score modifié à " + this.score + ".");
    }


    /**
     * Set the score of the game.
     *
     * @param score score number to set.
     */
    public void setScore(int score) {
        this.score = score;
        Main.LOGGER.log(Logger.ALL, "Model => Score modifié à " + this.score + ".");
    }


    /**
     * Reset the grid.
     */
    public void resetGrid() {
        for (int row=0; row<4; row++) {
            for (int column=0; column<4; column++) {
                this.grid[row][column] = 0;
            }
        }
    }


    /**
     * Set the box value in the grid.
     *
     * @param row the row musts be between 0-3.
     * @param column the column musts be between 0-3.
     * @param value the value to set in the box.
     */
    public void setBox(int row, int column, int value) {
        this.grid[row][column] = value;
        Main.LOGGER.log(Logger.ALL, "Model => La box(" + row + ", " + column + ") a été modifiée à " + value + ".");
    }


    /**
     * Check if the grid is finished.
     *
     * @return if the grid is finished.
     */
    public boolean isGridFinished() {
        Main.LOGGER.log(Logger.DEBUG, "Model => Vérification si la grille est finie...");
        for (int row=0; row<4; row++) {
            for (int column=0; column<4; column++) {
                 if (this.grid[row][column] >= 2048) {
                     return true;
                 }
            }
        }

        return false;
    }


    /**
     * Method to make move up action
     */
    public void moveUp() {
        Main.LOGGER.log(Logger.DEBUG, "Model => Exécution de la commande 'move up'...");
        for (int column=0; column<4; column++) {
            for (int row=1; row<4; row++) {
                final int boxValue = this.getBox(row, column);

                if (boxValue > 0) {
                    int row_ = row;

                    Main.LOGGER.log(Logger.ALL, "Model => Vérification de la condition: not isCancelFusionBox || isTheLimit || isBoxDefined.");
                    // check not isCancelFusionBox || isTheLimit || isBoxDefined
                    while (!(this.getBox(row_, column) == -1 || (row_ == 1) || (this.getBox(row_ - 1, column) > 0))) {
                        row_--;
                    }

                    // condition no fusion happened
                    if (this.getBox(row_, column) == -1 || ((this.getBox(row_, column) == 0 || row == row_) && this.getBox(row_ - 1, column) != boxValue)) {
                        Main.LOGGER.log(Logger.ALL, "Model => Pas de fusion pour la box(" + row + ", " + column + ").");
                        this.setBox(row, column, 0);
                        this.setBox(row_ - (this.getBox(row_ - 1, column) == 0 ? 1 : 0), column, boxValue);

                    } else {
                        Main.LOGGER.log(Logger.ALL, "Model => Fusion pour la box(" + row + ", " + column + ").");
                        this.setBox(row, column, 0);
                        this.setBox(row_, column, -1);

                        int newValue = boxValue * 2;
                        this.setBox(row_ - 1, column, newValue);

                        this.addScore(newValue);
                    }
                }
            }
        }

        resetCancelFusion(); // reset -1 box
    }

    /**
     * Method to make move up action
     */
    public void moveDown() {
        Main.LOGGER.log(Logger.DEBUG, "Model => Exécution de la commande 'move down'...");
        for (int column=0; column<4; column++) {
            for (int row=2; row>=0; row--) {
                final int boxValue = this.getBox(row, column);

                if (boxValue > 0) {
                    int row_ = row;

                    Main.LOGGER.log(Logger.ALL, "Model => Vérification de la condition: not isCancelFusionBox || isTheLimit || isBoxDefined.");
                    // check not isCancelFusionBox || isTheLimit || isBoxDefined
                    while (!(this.getBox(row_, column) == -1 || (row_ == 2) || (this.getBox(row_ + 1, column) > 0))) {
                        row_++;
                    }

                    // condition no fusion happened
                    if (this.getBox(row_, column) == -1 || ((this.getBox(row_, column) == 0 || row == row_) && this.getBox(row_ + 1, column) != boxValue)) {
                        Main.LOGGER.log(Logger.ALL, "Model => Pas de fusion pour la box(" + row + ", " + column + ").");
                        this.setBox(row, column, 0);
                        this.setBox(row_ + (this.getBox(row_ + 1, column) == 0 ? 1 : 0), column, boxValue);

                    } else {
                        Main.LOGGER.log(Logger.ALL, "Model => Fusion pour la box(" + row + ", " + column + ").");
                        this.setBox(row, column, 0);
                        this.setBox(row_, column, -1);

                        int newValue = boxValue * 2;
                        this.setBox(row_ + 1, column, newValue);

                        this.addScore(newValue);
                    }
                }

            }
        }

        resetCancelFusion(); // reset -1 box
    }

    /**
     * Method to make move up action
     */
    public void moveRight() {
        Main.LOGGER.log(Logger.DEBUG, "Model => Exécution de la commande 'move right'...");
        for (int row=0; row<4; row++) {
            for (int column=2; column>=0; column--) {
                final int boxValue = this.getBox(row, column);

                if (boxValue > 0) {
                    int column_ = column;

                    Main.LOGGER.log(Logger.ALL, "Model => Vérification de la condition: not isCancelFusionBox || isTheLimit || isBoxDefined.");
                    // check not isCancelFusionBox || isTheLimit || isBoxDefined
                    while (!(this.getBox(row, column_) == -1 || (column_ == 2) || (this.getBox(row, column_ + 1) > 0))) {
                        column_++;
                    }


                    // condition no fusion happened
                    if (this.getBox(row, column_) == -1 || ((this.getBox(row, column_) == 0 || column == column_) && this.getBox(row, column_ + 1) != boxValue)) {
                        Main.LOGGER.log(Logger.ALL, "Model => Pas de fusion pour la box(" + row + ", " + column + ").");
                        this.setBox(row, column, 0);
                        this.setBox(row, column_ + (this.getBox(row, column_ + 1) == 0 ? 1 : 0), boxValue);

                    } else {
                        Main.LOGGER.log(Logger.ALL, "Model => Fusion pour la box(" + row + ", " + column + ").");
                        this.setBox(row, column, 0);
                        this.setBox(row, column_, -1);

                        int newValue = boxValue * 2;
                        this.setBox(row, column_ + 1, newValue);

                        this.addScore(newValue);
                    }

                }
            }
        }

        resetCancelFusion(); // reset -1 box
    }

    /**
     * Method to make move up action
     */
    public void moveLeft() {
        Main.LOGGER.log(Logger.DEBUG, "Model => Exécution de la commande 'move left'...");

        for (int row=0; row<4; row++) {
            for (int column=1; column<4; column++) {
                final int boxValue = this.getBox(row, column);

                if (boxValue > 0) {
                    int column_ = column;

                    Main.LOGGER.log(Logger.ALL, "Model => Vérification de la condition: not isCancelFusionBox || isTheLimit || isBoxDefined.");
                    // check not isCancelFusionBox || isTheLimit || isBoxDefined
                    while (!(this.getBox(row, column_) == -1 || (column_ == 1) || (this.getBox(row, column_ - 1) > 0))) {
                        column_--;
                    }

                    // condition no fusion happened
                    if (this.getBox(row, column_) == -1 || ((this.getBox(row, column_) == 0 || column == column_) && this.getBox(row, column_ - 1) != boxValue)) {
                        Main.LOGGER.log(Logger.ALL, "Model => Pas de fusion pour la box(" + row + ", " + column + ").");
                        this.setBox(row, column, 0);
                        this.setBox(row, column_ - (this.getBox(row, column_ - 1) == 0 ? 1 : 0), boxValue);

                    } else {
                        Main.LOGGER.log(Logger.ALL, "Model => Fusion pour la box(" + row + ", " + column + ").");
                        this.setBox(row, column, 0);
                        this.setBox(row, column_, -1);

                        int newValue = boxValue * 2;
                        this.setBox(row, column_ - 1, newValue);

                        this.addScore(newValue);

                    }
                }
            }
        }

        resetCancelFusion(); // reset -1 box
    }


    /**
     * Reset -1 box
     */
    private void resetCancelFusion() {

        Main.LOGGER.log(Logger.DEBUG, "Model => Réinitialisation des 'cancel' de fusions...");
        for (int row=0; row<4; row++) {
            for (int column=0; column<4; column++) {
                if (this.getBox(row, column) == -1) {
                    this.setBox(row, column, 0);
                }
            }
        }
    }


    /**
     * Check if a box can be generated.
     *
     * @return if a box can be generated.
     */
    public boolean canGenerateRandomBox() {

        Main.LOGGER.log(Logger.DEBUG, "Model => Vérification si une boxe peut être générée...");
        for (int row=0; row<4; row++) {
            for (int column=0; column<4; column++) {
                if (this.grid[row][column] == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Generate a random box.
     */
    public void generateRandomBox() {

        Main.LOGGER.log(Logger.DEBUG, "Model => Génération d'une boxe...");
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


    /**
     * Check if the party can continue.
     *
     * @return if the party can continue.
     */
    public boolean canPlay() {

        Main.LOGGER.log(Logger.DEBUG, "Model => Vérification si la partie peut continué...");
        if (!canGenerateRandomBox()) {

            Main.LOGGER.log(Logger.ALL, "Model => Prédictions du jeu.");
            // Create game 2048 and copy the grid
            ServerGame gamePredicated = new ServerGame();
            gamePredicated.copyGrid(this.grid);

            Main.LOGGER.log(Logger.ALL, "Model => Exécution de tout les mouvements.");
            // Try all movement action
            gamePredicated.moveUp();
            gamePredicated.moveRight();
            gamePredicated.moveDown();
            gamePredicated.moveLeft();

            Main.LOGGER.log(Logger.ALL, "Model => Comparaison de la grille avec les prédictions du jeu...");
            return !(Arrays.deepEquals(this.getGrid(), gamePredicated.getGrid()));

        } else {
            return true;
        }
    }


}
