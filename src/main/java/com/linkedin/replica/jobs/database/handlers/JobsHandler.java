package com.linkedin.replica.jobs.database.handlers;

import com.linkedin.replica.jobs.models.Job;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public interface JobsHandler extends DatabaseHandler{
    /**
     * Initiate a connection with the database
     */
    void connect() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;
    public ArrayList<Job> getAppliedJobs(String userID) throws SQLException;
    public List<Job> getSavedJobs(String userId);
    public void createJobAsaCompany( Job job);
    public void deleteJobAsaCompany(String jobID);
    public Job getJob(String JobID);
    public void EditJob(String JobID, LinkedHashMap<String, String > args);
    public boolean RespondToJobsAsCompany(String userId,String jobId) throws SQLException;    /**
     * Send a new notification to the user
     */
    void disconnect();
}
