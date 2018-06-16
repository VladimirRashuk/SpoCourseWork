package ServerLogger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;


public class ServerLog {

    static public FileHandler fileTxt;

    static public FileHandler fileHTML;

    static public void setup(File dirName, String pattern, String outputFormat) throws IOException {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }
        logger.setLevel(Level.ALL);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        System.out.println("Current/Startdate: " + date);

        if (outputFormat.equals("txt")) {
            System.out.println("Log-File format: .txt/.log");
            fileTxt = new FileHandler(dirName.getPath() + File.separator + "log_" + date + ".log", true);
            SimpleFormatter formatterTxt = new SimpleFormatter();
            fileTxt.setFormatter(formatterTxt);
            logger.addHandler(fileTxt);
        }
        if (outputFormat.equals("html")) {
            System.out.println("Log-File format: HTML");
            fileHTML = new FileHandler(dirName.getPath() + File.separator + "log_" + date + ".html", true);
            Formatter formatterHTML = new ServerLogFormatter();
            fileHTML.setFormatter(formatterHTML);
            logger.addHandler(fileHTML);
        }
    }
}


