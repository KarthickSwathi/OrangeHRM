package genericLib;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExecutionLogManager {

    private static String executionLogFile;

    public static void createExecutionLog() {
        String timestamp = new SimpleDateFormat("dd_MM_yyyy_hh_mm_a").format(new Date());
        executionLogFile = "Execution_" + timestamp + ".log";
        System.setProperty("EXECUTION_LOG_FILE", executionLogFile);
    }

    public static String getExecutionLogFile() {
        return executionLogFile;
    }
}
