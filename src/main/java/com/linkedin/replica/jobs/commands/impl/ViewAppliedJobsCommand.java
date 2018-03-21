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

    public ViewAppliedJobsCommand(HashMap<String, String> args) {
        super(args);
    }

    public LinkedHashMap<String, Object> execute() throws IOException {

        validateArgs(new String[]{"userId"});
        // get notifications from db
        JobsHandler jobsHandler = (JobsHandler) this.jobsHandler;
        LinkedHashMap<String, Object> resutls = new LinkedHashMap<String, Object>();
        try {


            ArrayList<Job> jobs = jobsHandler.getAppliedJobs(args.get("userId"));


            resutls.put("response", jobs);

        }
        catch (Exception e){
            System.out.println(e);
        }
    return resutls;
    }


}
