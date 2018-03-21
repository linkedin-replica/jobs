package commands.impl;

import commands.Command;
import database.DatabaseHandler;
import models.Job;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class JobListingCommand extends Command {

    public JobListingCommand(HashMap<String, String> args) {
        super(args);
    }

    public LinkedHashMap<String, Object> execute() throws IOException {
        validateArgs(new String[]{"jobId"});
        // get notifications from db
        DatabaseHandler dbHandler = (DatabaseHandler) this.dbHandler;

        Job job =  dbHandler.getJob(args.get("jobId"));
        LinkedHashMap<String, Object> resutls = new LinkedHashMap<String, Object>();
        resutls.put("response", job);
        return resutls;
    }
}
