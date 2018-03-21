package com.linkedin.replica.jobs.commands.impl;

import com.linkedin.replica.jobs.commands.Command;
import com.linkedin.replica.jobs.database.handlers.DatabaseHandler;

import com.linkedin.replica.jobs.database.handlers.JobsHandler;
import com.linkedin.replica.jobs.models.Job;
import com.linkedin.replica.jobs.models.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class JobAsCompanyCommand extends Command {

        public JobAsCompanyCommand(HashMap<String, String> args) {
            super(args);
        }

        public LinkedHashMap<String, Object> execute() throws IOException {

            validateArgs(new String[]{"userId"});
            // get notifications from db
            JobsHandler jobsHandler = (JobsHandler) this.jobsHandler;

            Job job = new Job(args.get("jobId"),args.get("industryType"),args.get("employementType"), args.get("jobFunctions"),
                args.get("positionName"), args.get("professionLevel"),args.get("companyID"),args.get("companyName"),
                            args.get("companyLocation"),args.get("companyProfilePicture"),args.get("jobBrief"));
            jobsHandler.createJobAsaCompany(job);
            LinkedHashMap<String, Object>resutls = new LinkedHashMap<String, Object>();
            resutls.put("response",true);
            return resutls;

        }


    }


