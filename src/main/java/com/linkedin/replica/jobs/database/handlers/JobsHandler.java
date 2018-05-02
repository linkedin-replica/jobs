package com.linkedin.replica.jobs.database.handlers;

import com.linkedin.replica.jobs.models.Job;
import com.linkedin.replica.jobs.models.ReturnedJob;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface JobsHandler extends DatabaseHandler{

    public ArrayList<ReturnedJob> getAppliedJobs(String userID) throws SQLException;
    public  ArrayList<ReturnedJob> getSavedJobs(String userId) throws SQLException;
    public void createJobAsaCompany(HashMap<String, Object > args) throws SQLException;
    public void deleteJobAsaCompany(String userId,String jobId) throws SQLException;
    public Job getJob(String JobID);
    public void userSaveJob(String userId,String jobId) throws SQLException;
    public void editJob(HashMap<String, Object > args);
    public boolean respondToJobsAsCompany(String userId,String jobId,String applicantId, int status) throws SQLException;
    public boolean userApplyForJob(String userId, String jobId) throws SQLException;
}
