package database;
import java.util.List;
//import com.arangodb.ArangoDB;
import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import models.Company;
import models.Job;
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

    public List<Job> getAppliedjobs(String userId){
return null;
    }
public List<Job> getAppliedJobs(String userID){
    try {
       User user= dbInstance.collection("jobs").getDocument(userID,User.class);

    } catch (ArangoDBException e) {
        System.err.println("Failed to retrive  document. " + e.getMessage());
    }
    return null;
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
        BaseDocument myObject = new BaseDocument();
        myObject.setKey(job.getJobID());
        myObject.addAttribute("companyName", job.getCompanyName());
        myObject.addAttribute("companyID", job.getCompanyID());
        myObject.addAttribute("companyProfilePicture", job.getCompnayPicture());
        myObject.addAttribute("companyLocation", job.getCompanyLocation());
        myObject.addAttribute("jobID", job.getJobID());
        myObject.addAttribute("industryType", job.getIndustryType());
        myObject.addAttribute("employmentType", job.getEmploymentType());
        myObject.addAttribute("jobFunctions", job.getJobFunctions());
        myObject.addAttribute("positionName",job.getPositionName());
        myObject.addAttribute("professionLevel",job.getProfessionLevel());
    try {
        dbInstance.collection("jobs").insertDocument(myObject);
    } catch (ArangoDBException e) {
        System.err.println("Failed to update document. " + e.getMessage());
    }


    }
    public void deleteJobAsaCompany(String jobID){
        try {
            dbInstance.collection("jobs").deleteDocument(jobID);
        } catch (ArangoDBException e) {
            System.err.println("Failed to Delete document. " + e.getMessage());
        }
    }
}
