package datasource.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileLogger {
    private static FileLogger fileLogger = null;

    private static final String LOGFILE_NAME = "error.log";
    private Handler handler;

    public FileLogger() {
        initializeLogger();
    }

    public static FileLogger getInstance(){
      if(fileLogger == null){
          fileLogger = new FileLogger();
      }
      return fileLogger;
    }

    private void initializeLogger() {
        try {
                handler = new FileHandler(LOGFILE_NAME, true);
        } catch (IOException e) {
            Logger.getLogger("logger").severe("something went wrong with the logfile: " + e.getMessage());
        }
    }

    public void log(String className, Level level, String message) {
        Logger.getLogger(className).addHandler(handler);
        Logger.getLogger(className).log(level,message);
    }

}
