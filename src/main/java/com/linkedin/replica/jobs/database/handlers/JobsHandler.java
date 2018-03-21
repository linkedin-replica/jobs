package com.linkedin.replica.jobs.database.handlers;

import com.linkedin.replica.jobs.models.Job;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface JobsHandler {
    /**
     * Initiate a connection with the database
     */
    void connect() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;
    public ArrayList<Job> getAppliedJobs(ArrayList<String> Ids);
    public List<Job> getSavedJobs(String userId);
    public void createJobAsaCompany( Job job);
    public void deleteJobAsaCompany(String jobID);
    public Job getJob(String JobID);
    /**
     * Send a new notification to the user
     */
    void disconnect();
}
