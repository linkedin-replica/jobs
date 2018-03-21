package database;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;


import com.arangodb.entity.MultiDocumentEntity;
import config.Configuration;
import models.Job;
import com.arangodb.ArangoCollection;
//import com.arangodb.ArangoDB;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import models.User;
//import utils.Config;
//import com.arangodb.ArangoDB;

public class ArangoSQLJobsHandler implements DatabaseHandler{
    ArangoDB arangoDB;

    private ArangoDatabase dbInstance;
    private ArangoCollection collection;
    private String collectionName;
    Connection mysqlConnection;
    Configuration config;
    public ArangoSQLJobsHandler()throws IOException {
        Configuration config = Configuration.getInstance();
        ArangoDB arangoDriver = DatabaseConnection.getInstance().getArangoDriver();
        collectionName = config.getArangoConfig("collection.notifications.name");
        dbInstance = arangoDriver.db(config.getArangoConfig("db.name"));
        collection = dbInstance.collection(collectionName);
    }
    public void connect() {
        // TODO
        arangoDB = new ArangoDB.Builder().build();
    }

  
    public void disconnect() {
        // TODO
    }
    public ArrayList<String> getAppliedJobsIDs(String userId) throws SQLException {
        String query = "{CALL view_applied_jobs(?);}";
        CallableStatement stmt = mysqlConnection.prepareCall(query);
        stmt.setString(1, userId);
        ResultSet result = stmt.executeQuery();
        try{
            while (result.next()) {
                String coffeeName = result.getString("id");

                System.out.println(coffeeName + "\t" );
            }
        } catch (SQLException e ) {
            System.out.println("Exception ");
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return null;
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

    public ArrayList<Job> getAppliedjobs(ArrayList<String> Ids){
        Collection<String> keys = Ids;
        MultiDocumentEntity<Job> cursor= dbInstance.collection("commands").getDocuments(keys,Job.class);
       Collection<Job> jobs = cursor.getDocuments();

       return new ArrayList<Job>(jobs);

    }

    public List<Job> getSavedJobs(String userID){
        try {
            User user= dbInstance.collection("commands").getDocument(userID,User.class);

        } catch (ArangoDBException e) {
            System.err.println("Failed to retrieve document. " + e.getMessage());
        }
        return null;
    }
        public void createJobAsaCompany( Job job){

            try {
                dbInstance.collection(collectionName).insertDocument(job);
            } catch (ArangoDBException e) {
                System.err.println("Failed to update document. " + e.getMessage());
            }
    }

    public  Job getJob(String JobID){
        String JobsCollectionName = config.getAppConfig("collection.commands.name");
        Job job = dbInstance.collection(JobsCollectionName).getDocument(JobID,
                Job.class);
        return job;
    }

    public void EditJob(String JobID, LinkedHashMap<String, String > args){
        String JobsCollectionName = config.getAppConfig("collection.commands.name");
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
        dbInstance.collection(JobsCollectionName).updateDocument(job.getJobID(),job);
    }

    public void deleteJobAsaCompany(String jobID){
        String JobsCollectionName = config.getArangoConfig("collection.commands.name");

        try {
            dbInstance.collection(JobsCollectionName).deleteDocument(jobID);
        } catch (ArangoDBException e) {
            System.err.println("Failed to Delete document. " + e.getMessage());
        }
    }


}
