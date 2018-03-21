package com.linkedin.replica.jobs.commands.impl;

import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RespondToApplicantsCommand extends Command {
    public RespondToApplicantsCommand(HashMap<String, String> args) {
        super(args);
    }

    public LinkedHashMap<String, Object> execute(){
        validateArgs(new String[]{"companyId"});
        // get notifications from db
        JobsHandler jobsHandler = (JobsHandler) this.jobsHandler;

        boolean job = false;
        try {
            job = jobsHandler.RespondToJobsAsCompany(args.get("companyId"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LinkedHashMap<String, Object> resutls = new LinkedHashMap<String, Object>();
        resutls.put("response", true);
        return resutls;
    }
}

