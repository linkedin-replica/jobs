package com.linkedin.replica.jobs.commands.impl;
import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;
import com.linkedin.replica.jobs.models.Job;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ViewAppliedJobsCommand extends Command {

    public ViewAppliedJobsCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute() throws IOException, SQLException {

        validateArgs(new String[]{"userId"});
        // get notifications from db
        JobsHandler jobsHandler = (JobsHandler) this.dbHandler;;
        LinkedHashMap<String, Object> resutls = new LinkedHashMap<String, Object>();
            ArrayList<Job> jobs = jobsHandler.getAppliedJobs((String)args.get("userId"));
            resutls.put("response", jobs);
            return jobs;
    }


}
