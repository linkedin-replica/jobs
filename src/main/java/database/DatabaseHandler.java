package database;

import models.Company;
import models.Job;

import java.util.List;

public interface DatabaseHandler {
    /**
     * Initiate a connection with the database
     */
    void connect();
    public List<Job> getAppliedJobs(String userId);
    public List<Job> getSavedJobs(String userId);
   public void createJobAsaCompany( Job job);
    public void deleteJobAsaCompany(String jobID);
    /**
     * Send a new notification to the user
     */

    void disconnect();
}
