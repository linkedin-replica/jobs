package database;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

import com.arangodb.entity.MultiDocumentEntity;
import models.Job;
import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
//import com.arangodb.ArangoDB;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import models.User;
import utils.ConfigReader;

public class ArangoHandler implements DatabaseHandler{
    ArangoDB arangoDB;
    private ConfigReader config;
    private ArangoDatabase dbInstance;
    private ArangoCollection collection;
    private String collectionName;
    Connection mySqlConnection;

    public void connect()  throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
        // TODO
        arangoDB = new ArangoDB.Builder().build();
        mySqlConnection = DatabaseConnection.getInstance().getMysqlConn();
    }

  
    public void disconnect() {
        // TODO
    }

    public ArrayList<Job> getAppliedjobs(ArrayList<String> Ids){
        Collection<String> keys = Ids;
        MultiDocumentEntity<Job> cursor= dbInstance.collection("jobs").getDocuments(keys,Job.class);
       Collection<Job> jobs = cursor.getDocuments();

       return new ArrayList<Job>(jobs);

    }

    public List<Job> getSavedJobs(String userID){
        try {
            User user= dbInstance.collection("jobs").getDocument(userID,User.class);

        } catch (ArangoDBException e) {
            System.err.println("Failed to retrieve document. " + e.getMessage());
        }
        return null;
    }
        public void createJobAsaCompany( Job job){
            String JobsCollectionName = config.getConfig("collection.jobs.name");
            try {
                dbInstance.collection(JobsCollectionName).insertDocument(job);
            } catch (ArangoDBException e) {
                System.err.println("Failed to update document. " + e.getMessage());
            }
    }
    public  Job getJob(String JobID){
        String JobsCollectionName = config.getConfig("collection.jobs.name");
        Job job = dbInstance.collection(JobsCollectionName).getDocument(JobID,
                Job.class);
        return job;
    }

    public void EditJob(String JobID, LinkedHashMap<String, String > args){
        String JobsCollectionName = config.getConfig("collection.jobs.name");
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
        dbInstance.collection(JobsCollectionName).insertDocument(job);
    }

    public void deleteJobAsaCompany(String jobID){
        String JobsCollectionName = config.getConfig("collection.jobs.name");

        try {
            dbInstance.collection(JobsCollectionName).deleteDocument(jobID);
        } catch (ArangoDBException e) {
            System.err.println("Failed to Delete document. " + e.getMessage());
        }
    }
    public ArangoHandler() throws IOException, SQLException, ClassNotFoundException {
        config = new ConfigReader("arango_names");
        // init db
        ArangoDB arangoDriver = DatabaseConnection.getDBConnection().getArangoDriver();
        collectionName = config.getConfig("collection.users.name");
        dbInstance = arangoDriver.db(config.getConfig("db.name"));
        collection = dbInstance.collection(collectionName);
    }
}
