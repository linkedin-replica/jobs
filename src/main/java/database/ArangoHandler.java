package database;
import java.awt.*;
import java.io.IOException;
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

    public void connect() {
        // TODO
        arangoDB = new ArangoDB.Builder().build();
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


        public ArrayList<Job>  JobListing(LinkedHashMap<String, String> args){
            String JobsCollectionName = config.getConfig("collection.jobs.name");

            Map<String, Object> bindVars = new HashMap<String, Object>();
            String values = "";
                if(args.containsKey("companyLocation")){
                    values = "t.companyLocation == @location";
                    bindVars.put("location",args.get("companyLocation"));
                }
            if(args.containsKey("companyName")){
                    if(values.equals(""))
                        values = "t.companyName == @companyName";
                        else
                        values += " AND t.companyName == @companyName";
                bindVars.put("companyName",args.get("companyName"));
            }
            if(args.containsKey("industryType")){
                if(values.equals(""))
                    values = "t.industryType == @industryType";
                else
                    values += " AND t.industryType == @industryType";
                bindVars.put("industryType",args.get("industryType"));
            }
            if(args.containsKey("employmentType")){
                if(values.equals(""))
                    values = "t.employmentType == @employmentType";
                else
                    values += " AND t.employmentType == @employmentType";
                bindVars.put("employmentType",args.get("employmentType"));
            }
            if(args.containsKey("professionLevel")){
                if(values.equals(""))
                    values = "t.professionLevel == @professionLevel";
                else
                    values += " AND t.professionLevel == @professionLevel";
                bindVars.put("professionLevel",args.get("professionLevel"));
            }
            if(args.containsKey("positionName")){
                if(values.equals(""))
                    values = "t.positionName == @positionName";
                else
                    values += " AND t.positionName == @positionName";
                bindVars.put("positionName",args.get("positionName"));
            }
            ArrayList<String> skills = new ArrayList<>();
            if(args.containsKey("skill1")){
                skills.add(args.get("skill1"));
            }
            if(args.containsKey("skill2")){
                skills.add(args.get("skill2"));
            }
            if(args.containsKey("skill3")){
                skills.add(args.get("skill3"));
            }
            System.out.println(skills.size());
            if(skills.size() >= 1){
                if(values.equals(""))
                    values = "COUNT(INTERSECTION(@skills, t.requiredSkills)) != 0  ";
                else
                    values += " AND COUNT(INTERSECTION(@skills, t.requiredSkills)) != 0 ";
                bindVars.put("skills",skills);
            }
            String query = "For t in " + JobsCollectionName + " FILTER " + values +
                            " return t";
            System.out.println(query);
            ArangoCursor<Job> cursor = dbInstance.query(query, bindVars, null, Job.class);
            final ArrayList<Job> returnedResults = new ArrayList<Job>();
            cursor.forEachRemaining(returnedResults::add);
            return returnedResults;
        }

    public void deleteJobAsaCompany(String jobID){
        String JobsCollectionName = config.getConfig("collection.jobs.name");

        try {
            dbInstance.collection(JobsCollectionName).deleteDocument(jobID);
        } catch (ArangoDBException e) {
            System.err.println("Failed to Delete document. " + e.getMessage());
        }
    }

    public ArangoHandler()throws IOException {
        config = new ConfigReader("arango_names");
        // init db
        ArangoDB arangoDriver = DatabaseConnection.getDBConnection().getArangoDriver();
        collectionName = config.getConfig("collection.users.name");
        dbInstance = arangoDriver.db(config.getConfig("db.name"));
        collection = dbInstance.collection(collectionName);
    }
}
