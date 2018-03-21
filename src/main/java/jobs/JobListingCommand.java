package jobs;

import database.ArangoHandler;
import database.DatabaseHandler;
import models.Command;
import models.Job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class JobListingCommand extends Command {

    public JobListingCommand(HashMap<String, String> args) {
        super(args);
    }

    public LinkedHashMap<String, Object> execute() throws IOException {
        validateArgs(new String[]{"jobId"});
        // get notifications from db
        DatabaseHandler noSqlHandler = (DatabaseHandler) new ArangoHandler();
        this.setDbHandler(noSqlHandler);

        Job job =  dbHandler.getJob(args.get("jobId"));
        LinkedHashMap<String, Object> resutls = new LinkedHashMap<String, Object>();
        resutls.put("response", job);
        return resutls;
    }
}
