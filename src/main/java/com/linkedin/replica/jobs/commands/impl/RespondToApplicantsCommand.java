package com.linkedin.replica.jobs.commands.impl;

import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RespondToApplicantsCommand extends Command {
    public RespondToApplicantsCommand(HashMap<String, Object> args) {
        super(args);
    }

    public Object execute() throws SQLException {
        validateArgs (new String[]{"userId", "jobId", "applicantId", "response"});
           JobsHandler jobsHandler = (JobsHandler) this.dbHandler;;
           jobsHandler.respondToJobsAsCompany((String)args.get("userId"), (String)args.get("jobId"),(String) args.get("applicantId"), (int) args.get("response"));
           return null;
    }
}

