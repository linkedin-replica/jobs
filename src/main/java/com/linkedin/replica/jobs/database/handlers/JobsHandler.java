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

    ArrayList<Job> getAppliedJobs(String userID) throws SQLException;

    List<Job> getSavedJobs(String userId);

    void createJobAsaCompany( Job job);

    void deleteJobAsaCompany(String jobID);

    Job getJob(String JobID);

    void EditJob(String JobID, LinkedHashMap<String, String > args);

    boolean RespondToJobsAsCompany(String userId,String jobId) throws SQLException;

    /**
     * Send a new notification to the user
     */
    void disconnect();
}
