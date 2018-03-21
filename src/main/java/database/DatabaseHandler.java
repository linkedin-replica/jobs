package database;

import models.Company;
import models.Job;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface DatabaseHandler {
    /**
     * Initiate a connection with the database
     */
    void connect() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;
//    public List<Job> getAppliedJobs(String userId);
    public List<Job> getSavedJobs(String userId);
    public void createJobAsaCompany( Job job);
    public void deleteJobAsaCompany(String jobID);
    public Job getJob(String JobID);
    /**
     * Send a new notification to the user
     */

    void disconnect();
}
