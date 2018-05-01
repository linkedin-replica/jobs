package com.linkedin.replica.jobs.commands.impl;
import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;
import com.linkedin.replica.jobs.models.Job;
import com.linkedin.replica.jobs.models.ReturnedJob;

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
        JobsHandler jobsHandler = (JobsHandler) this.dbHandler;;
        ArrayList<ReturnedJob> jobs = jobsHandler.getAppliedJobs((String)args.get("userId"));
        return jobs;
    }

}
