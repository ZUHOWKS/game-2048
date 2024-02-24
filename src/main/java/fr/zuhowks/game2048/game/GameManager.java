package fr.zuhowks.game2048.game;

import fr.zuhowks.game2048.Main;
import fr.zuhowks.game2048.common.logs.Logger;
import fr.zuhowks.game2048.game.client.ClientGame;
import fr.zuhowks.game2048.game.server.ServerGame;
import fr.zuhowks.game2048.game.server.stats.GameStatus;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GameManager {

    private GameStatus gameStatus;
    private final ServerGame serverGame;
    private final ClientGame clientGame;
    private final String sessionPartyDir;

    //private PropertyChangeSupport pcs;

    public GameManager() {
        this.gameStatus = GameStatus.NOT_IN_GAME;
        this.serverGame = new ServerGame();
        this.clientGame = new ClientGame();
        //this.pcs = new PropertyChangeSupport(this);
        this.sessionPartyDir = Main.sessionDir + "/parties/";
    }

    /**
     * Use a specific server model in the game controller.
     *
     * @param serverGame Specify the server
     */
    public GameManager(ServerGame serverGame) {
        this.gameStatus = GameStatus.NOT_IN_GAME;
        this.serverGame = serverGame;
        this.clientGame = new ClientGame();
        this.sessionPartyDir = Main.sessionDir + "/parties/";
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }


    /**
     * The player is able to perform the following actions:
     *      - moveUp: which executes the action to move up on the server model.
     *      - moveDown: which executes the action to move down on the server model.
     *      - moveLeft: which executes the action to move left on the server model.
     *      - moveRight: which executes the action to move right on the server model.
     *
     * @param cmd command to perform among the list of possible commands
     */
    public void performAction(String cmd) {
        Main.LOGGER.log(Logger.INFO, "Contrôlleur => Exécution de la commande '" + cmd + "'.");
        if (this.getGameStatus() == GameStatus.IN_GAME) {
            if (nextPlay()) {
                switch (cmd) {
                    case "moveUp":
                        Main.LOGGER.log(Logger.INFO, "Déplacement vertical vers le haut.");
                        this.serverGame.moveUp();
                        break;

                    case "moveDown":
                        Main.LOGGER.log(Logger.INFO, "Déplacement vertical vers le bas.");
                        this.serverGame.moveDown();
                        break;

                    case "moveRight":
                        Main.LOGGER.log(Logger.INFO, "Déplacement horizontal vers la droite.");
                        this.serverGame.moveRight();
                        break;

                    case "moveLeft":
                        Main.LOGGER.log(Logger.INFO, "Déplacement horizontal vers la gauche.");
                        this.serverGame.moveLeft();
                        break;
                }


                if (!serverGame.isGridEqual(clientGame.getGrid())) {
                    Main.LOGGER.log(Logger.DEBUG, "Controlleur => Mise à jour de la vue cliente.");
                    this.serverGame.generateRandomBox();
                    updateGameStatus();
                    this.clientGame.update(this.serverGame.getGrid(), this.serverGame.getScore());
                }

            } else {
                this.gameStatus = GameStatus.LOOSE;
            }


        }
    }


    /**
     *  To start or restart a party. On call this method, the grid and the score
     *  are reset and game status is updated.
     */
    public void startParty() {
        Main.LOGGER.log(Logger.DEBUG, "Controlleur => Réinitialisation du jeu.");
        this.serverGame.resetGrid();
        this.serverGame.resetScore();

        Main.LOGGER.log(Logger.DEBUG, "Controlleur => Mise à jour de la vue cliente.");
        this.serverGame.generateRandomBox();
        this.clientGame.update(this.serverGame.getGrid(), this.serverGame.getScore());


        //GameStatus oldStatus = this.gameStatus;
        this.gameStatus = GameStatus.IN_GAME;
        Main.LOGGER.log(Logger.ALL, "Controlleur => Mise à jour du status du jeu.");

        Main.LOGGER.log(Logger.INFO, "La partie est lancée !");
        //pcs.firePropertyChange("gameStatus", oldStatus, this.gameStatus);
    }

    /**
     * Able to check if the player can play in next. If the player cannot play, the
     * game status changes to win or lose depending on whether he/she completes
     * the grid.
     *
     * @return if the player can play after.
     */
    public boolean nextPlay() {
        Main.LOGGER.log(Logger.DEBUG, "Controlleur => Vérification de la grille.");
        if (this.serverGame.isGridFinished()) {
            Main.LOGGER.log(Logger.ALL, "Controlleur => La grille a été finie.");
            this.gameStatus = GameStatus.WIN;
        } else if (this.serverGame.canPlay()) {
            return true;
        } else {
            this.gameStatus = GameStatus.LOOSE;
        }

        return false;
    }


    /**
     * Update the game status.
     */
    public void updateGameStatus() {
        if (this.serverGame.isGridFinished()) {
            this.gameStatus = GameStatus.WIN;
            //pcs.firePropertyChange("gameStatus", GameStatus.IN_GAME, this.gameStatus);
        } else if (!nextPlay()) {
            this.gameStatus = GameStatus.LOOSE;
            //pcs.firePropertyChange("gameStatus", GameStatus.IN_GAME, this.gameStatus);
        }
    }

    /**
     * Leave the party.
     */
    public void leaveParty() {
        GameStatus oldStatus = this.gameStatus;
        this.gameStatus = GameStatus.NOT_IN_GAME;
        //pcs.firePropertyChange("gameStatus", oldStatus, this.gameStatus);
    }

    /**
     * Get client view model.
     */
    public ClientGame getClientGame() {
        return clientGame;
    }


    /**
     * Save a party into the party directory (/path/to/user/home/.game2048/parties/).
     *
     * @throws IOException if it's impossible to create the party file.
     */
    public void saveParty() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        File file = new File(this.sessionPartyDir, timeStamp + ".party");

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();

        }

        file.createNewFile();

        try {
            FileOutputStream fOut = new FileOutputStream(file);
            ObjectOutputStream oOut = new ObjectOutputStream(fOut);

            oOut.writeObject(this.serverGame);

            oOut.close();
            fOut.close();

        } catch (Exception e) {
            Main.LOGGER.log(Logger.DEBUG, "Error => Impossible d'enregistrer la partie: " + e);
        }
    }


    /**
     * Load a party from a party file.
     *
     * @param partyPath path to the party file.
     * @throws IOException if it's impossible to read the party file.
     * @throws ClassNotFoundException if it's impossible to convert the object into fr.zuhowks.game2048.game.server.ServerGame class.
     */
    public void loadParty(String partyPath) throws IOException, ClassNotFoundException {

        File file = new File(partyPath);
        Main.LOGGER.log(Logger.DEBUG, "");
        FileInputStream fIn = new FileInputStream(file);
        ObjectInputStream oIn = new ObjectInputStream(fIn);

        Object obj = oIn.readObject();

        ServerGame gameToLoad = (ServerGame) obj;
        this.serverGame.copyGrid(gameToLoad.getGrid());
        this.serverGame.setScore(gameToLoad.getScore());

        oIn.close();
        fIn.close();

        this.clientGame.update(this.serverGame.getGrid(), this.serverGame.getScore());

    }

    /**
     * get the party directory path (/path/to/user/home/.game2048/parties/).
     *
     * @return return the party directory path
     */
    public String getSessionPartyDir() {
        return sessionPartyDir;
    }


}
