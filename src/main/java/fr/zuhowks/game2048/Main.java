package fr.zuhowks.game2048;

import fr.zuhowks.game2048.common.logs.Logger;
import fr.zuhowks.game2048.windows.MainWindow;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main {

    public static String sessionDir = System.getProperty("user.home") + "/.game2048/";
    public static Logger LOGGER = new Logger(Logger.DEBUG, Main.sessionDir + "/logs/" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + ".log");

    public static void main(String[] args) {
        LOGGER.log(Logger.INFO, "Démarrage de Game2048 by ZUHOWKS & Majurax !");
        LOGGER.log(Logger.DEBUG, "Debug mod active.");

        LOGGER.log(Logger.DEBUG, "Création de la fenêtre.");
        JFrame window = new MainWindow();

        LOGGER.log(Logger.DEBUG, "Affichage de la fenêtre.");
        window.setVisible(true);
    }
}