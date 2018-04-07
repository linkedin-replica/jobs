package com.linkedin.replica.jobs.commands.impl;
import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;
import com.linkedin.replica.jobs.models.Job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ViewAppliedJobsCommand extends Command {

    public ViewAppliedJobsCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute() throws IOException {

        validateArgs(new String[]{"userId"});
        // get notifications from db
        JobsHandler jobsHandler = (JobsHandler) this.dbHandler;;
        try {
            return jobsHandler.getAppliedJobs((String)args.get("userId"));
        }
        catch (Exception e){
            System.out.println(e);
        }
       return null;
    }


}
