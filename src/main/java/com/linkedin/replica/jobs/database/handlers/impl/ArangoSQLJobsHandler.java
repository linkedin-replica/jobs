package com.linkedin.replica.jobs.database.handlers.impl;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import com.arangodb.entity.MultiDocumentEntity;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;
import com.linkedin.replica.jobs.config.Configuration;
import com.linkedin.replica.jobs.database.DatabaseConnection;
import com.linkedin.replica.jobs.models.Job;
import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import com.linkedin.replica.jobs.models.User;

public class ArangoSQLJobsHandler implements JobsHandler {
    ArangoDB arangoDB;

    private ArangoDatabase dbInstance;
    private ArangoCollection collection;
    private String collectionName;
    Connection mysqlConnection;
    Configuration config;
    public ArangoSQLJobsHandler() throws IOException, SQLException, ClassNotFoundException {
        mysqlConnection = DatabaseConnection.getInstance().getMysqlDriver();
        config = Configuration.getInstance();
        dbInstance = DatabaseConnection.getInstance().getArangoDriver().
                db(config.getArangoConfigProp("db.name"));
        collection = dbInstance.collection(config.getArangoConfigProp("collection.jobs.name"));
    }
    public void connect() throws SQLException, IOException, ClassNotFoundException {
        // TODO
        arangoDB = new ArangoDB.Builder().build();

    }

  
    public void disconnect() {
        // TODO
    }
    public boolean RespondToJobsAsCompany(String userId,String jobId) throws SQLException {
        String query = "{respond_to_applicant(?,?)}";
        CallableStatement stmt = mysqlConnection.prepareCall(query);
        stmt.setString(1, userId);
        stmt.setString(2, jobId);
        stmt.executeQuery();
        return true;
    }
    public ArrayList<String> getAppliedJobsIDs(String userId) throws SQLException {
        String query = "{CALL view_applied_jobs(?)}";
        CallableStatement stmt = mysqlConnection.prepareCall(query);
        stmt.setString(1, userId);
        ArrayList<String> Ids = new ArrayList<String>();
        ResultSet result = stmt.executeQuery();
        try{
            while (result.next()) {
                String jobName = result.getString("job_id");
                System.out.println(jobName + "\t" );
                Ids.add(jobName);
            }
        } catch (SQLException e ) {
            System.out.println(e.toString());
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return Ids;
    }
    public void deleteAll(){

        String query = "{CALL Delete_Users()}";
        CallableStatement statement = null;
        try {

            statement = mysqlConnection.prepareCall(query);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Job>  getAppliedJobs( String userID) throws SQLException {
        Collection<String> keys = this.getAppliedJobsIDs(userID);

        collectionName = config.getArangoConfigProp("collection.jobs.name");
        MultiDocumentEntity<Job> cursor= dbInstance.collection(collectionName).getDocuments(keys,Job.class);

       Collection<Job> jobs = cursor.getDocuments();

       return new ArrayList<Job>(jobs);

    }

    public List<Job> getSavedJobs(String userID){
        try {
            User user = dbInstance.collection("jobs").getDocument(userID,User.class);

        } catch (ArangoDBException e) {
            System.err.println("Failed to retrieve document. " + e.getMessage());
        }
        return null;
    }
        public void createJobAsaCompany( Job job){

            try {
                collection.insertDocument(job);
            } catch (ArangoDBException e) {
                System.err.println("Failed to update document. " + e.getMessage());
            }
    }

    public  Job getJob(String JobID){
        Job job = collection.getDocument(JobID,
                Job.class);
        return job;
    }

    public void EditJob(String JobID, LinkedHashMap<String, String > args){

        String JobsCollectionName = config.getAppConfigProp("collection.jobs.name");

         Job job = getJob(JobID);
        if(args.containsKey("industryType"))
            job.setIndustryType(args.get("industryType"));
        if(args.containsKey("employmentType"))
            job.setIndustryType(args.get("employmentType"));
        if(args.containsKey("jobFunctions"))
            job.setIndustryType(args.get("jobFunctions"));
        if(args.containsKey("positionName"))
            job.setIndustryType(args.get("positionName"));
        if(args.containsKey("professionLevel"))
            job.setIndustryType(args.get("professionLevel"));
        collection.updateDocument(JobID ,job);
    }

    public void deleteJobAsaCompany(String jobID){

        String JobsCollectionName = config.getArangoConfigProp("collection.jobs.name");


        try {
            collection.deleteDocument(jobID);
        } catch (ArangoDBException e) {
            System.err.println("Failed to Delete document. " + e.getMessage());
        }
    }


}
