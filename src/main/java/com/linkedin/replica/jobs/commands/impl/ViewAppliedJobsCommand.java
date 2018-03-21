package com.linkedin.replica.jobs.commands.impl;
import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ViewAppliedJobsCommand extends Command {

    public ViewAppliedJobsCommand(HashMap<String, String> args) {
        super(args);
    }

    public LinkedHashMap<String, Object> execute() throws IOException {
        validateArgs(new String[]{"userId"});
        // get notifications from db
        DatabaseHandler dbHandler = (DatabaseHandler) this.dbHandler;
//       List<Job> commands = dbHandler.getAppliedJobs(args.get("userId"));
        LinkedHashMap<String, Object>resutls = new LinkedHashMap<String, Object>();
        resutls.put("response","hello");
        return resutls;
    }


}
