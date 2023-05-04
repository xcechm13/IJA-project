package Game;

import Game.Records.LogName;
import Game.Records.LoggerResult;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    private List<LogName> GetLogs()
    {
        List<LogName> Logs = new ArrayList<>();
        String[] logFileName = {"Log1", "Log2", "Log3", "Log4", "Log5"};
        String[] logDateTime = {"23.12.2023 22:55", "20.11.2023 17:05", "18.7.2023 22:55", "5.6.2023 1:00", "25.2.2023 17:22"};

        for(int i=0; i<logDateTime.length; i++)
        {
            Logs.add(new LogName(logDateTime[i], logFileName[i]));
        }

        return Logs;
    }

    private LoggerResult LoadLog(String logFileName, boolean fromStart)
    {
        System.out.println(logFileName + (fromStart ? "  From start" : "  From end"));
        // TODO
        return null;
    }

    private int GetCols()
    {
        // TODO
        return 0;
    }

    private int GetRows()
    {
        // TODO
        return 0;
    }
    private String[][] GetFields()
    {
        // TODO
        return null;
    }

    // Vracíš null pokud již další nejsou
    private LoggerResult NextStep()
    {
        // TODO
        return null;
    }

    // Vracíš null pokud již další nejsou
    private LoggerResult BackStep()
    {
        // TODO
        return null;
    }
}
