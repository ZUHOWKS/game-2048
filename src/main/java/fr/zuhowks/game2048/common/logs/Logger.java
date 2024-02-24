package fr.zuhowks.game2048.common.logs;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {

    public static final int ALL = 0;
    public static final int DEBUG = 100;
    public static final int INFO = 500;
    public static final int IMPORTANT = 900;
    public static final int OFF = Integer.MAX_VALUE;
    private int level;
    private PrintWriter printWriter;

    private Logger() {
        this.printWriter = new PrintWriter(System.err);
        this.level = Logger.DEBUG;
    }

    public Logger(int level, OutputStream outputStream) {
        this.printWriter = new PrintWriter(System.err);
        this.level = level;
    }

    public Logger(int level, String filePath) {
        try {
            File log = new File(filePath);

            if (!log.getParentFile().exists()) {
                log.getParentFile().mkdirs();
            }

            OutputStream out = Files.newOutputStream(Paths.get(filePath));
            this.printWriter = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                out
                            )
                    )
            );
        } catch (IOException e) {
            this.printWriter = new PrintWriter(System.out);
            throw new RuntimeException(e);
        }

        this.level = level;
    }

    public void log(int level, String message) {
        if (level >= this.level) {
            this.printWriter.println( new SimpleDateFormat("[dd/MM/yy HH:mm:ss]").format(Calendar.getInstance().getTime()) + "[Level=" + level +"]:" + message);
            this.printWriter.flush();
        }
    }
}
