package com.linkedin.replica.jobs.database.handlers.impl;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

import com.arangodb.*;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.MultiDocumentEntity;
import com.arangodb.velocypack.VPackSlice;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.linkedin.replica.jobs.database.handlers.JobsHandler;
import com.linkedin.replica.jobs.config.Configuration;
import com.linkedin.replica.jobs.database.DatabaseConnection;
import com.linkedin.replica.jobs.exceptions.BadRequestException;
import com.linkedin.replica.jobs.models.Company;
import com.linkedin.replica.jobs.models.Job;
import com.linkedin.replica.jobs.models.ReturnedJob;
import org.json.simple.JSONObject;

public class ArangoSQLJobsHandler implements JobsHandler {
    ArangoDB arangoDB;
    private ArangoDatabase dbInstance;
    private ArangoCollection jobsCollection;
    private ArangoCollection companyCollection;
    private String jobsCollectionName;
    private String companyCollectionName;
    Connection mysqlConnection;
    Configuration config;
    public ArangoSQLJobsHandler() throws IOException, SQLException, ClassNotFoundException {
        mysqlConnection = DatabaseConnection.getInstance().getMysqlDriver();
        config = Configuration.getInstance();
        dbInstance = DatabaseConnection.getInstance().getArangoDriver().
                db(config.getArangoConfigProp("db.name"));
        jobsCollection = dbInstance.collection(config.getArangoConfigProp("collection.jobs.name"));
        companyCollection = dbInstance.collection(config.getArangoConfigProp("collection.companies.name"));
        arangoDB = new ArangoDB.Builder().build();
        jobsCollectionName = config.getArangoConfigProp("collection.jobs.name");
        companyCollectionName = config.getArangoConfigProp("collection.companies.name");
    }

    public boolean respondToJobsAsCompany(String userId,String jobId,String applicantId, int status) throws SQLException {
         if(validateJob(userId,jobId)){
            String query = "{CALL respond_to_applicant(?,?,?)}";
            CallableStatement stmt = mysqlConnection.prepareCall(query);
            stmt.setString(1, applicantId);
            stmt.setString(2, jobId);
            stmt.setInt(3, status);
            stmt.executeQuery();
            return true;
        }else
            return false;
    }
    public boolean validateJob(String userId, String jobId){
        String query = "For t in " + jobsCollectionName + " FILTER " +
                "t._key == @jobId" +
                " RETURN t.companyId";
        Map<String, Object> bindVars = new HashMap<>();
        bindVars.put("jobId", jobId);
        ArangoCursor<String> cursor = dbInstance.query(query, bindVars, null, String.class);
        if(cursor.hasNext()) {
            String CompanyId = cursor.next();
            String query1 = "For t in " + companyCollectionName + " FILTER " +
                    "t._key == @CompanyId" +
                    " RETURN t.userId";
            bindVars = new HashMap<>();
            bindVars.put("CompanyId", CompanyId);
                cursor = dbInstance.query(query1, bindVars, null, String.class);
                String user = cursor.next();
                if (user.equals(userId))
                    return true;
                return false;

        }
        throw new BadRequestException("Invalid User");
    }


    public boolean validateCompany(String userId, String companyId){
        System.out.println(userId);
        System.out.println(companyId);
        Map<String, Object> bindVars = new HashMap<>();
        String query = "For t in " + companyCollectionName + " FILTER " +
                "t._key == @CompanyId" +
                " RETURN t.userId";
        bindVars = new HashMap<>();
        bindVars.put("CompanyId", companyId);
        ArangoCursor<String> cursor = dbInstance.query(query, bindVars, null, String.class);
        if(cursor.hasNext()){
            String user = cursor.next();
            System.out.println(user);
            if(user.equals(userId))
            return true;
        return false;
        }
        return false;
    }

    public boolean userApplyForJob(String userId, String jobId) throws SQLException {
        String query = "{CALL user_apply_to_job(?,?)}";
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
            while (result.next()) {
                String jobName = result.getString("job_id");
                System.out.println(jobName + "\t" );
                Ids.add(jobName);
            }
        return Ids;
    }
    public ArrayList<ReturnedJob> getAppliedJobs(String userId) throws SQLException {
        ArrayList<String> keys = this.getAppliedJobsIDs(userId);
        return (getReturnedJobs(keys));
    }


    public ArrayList<ReturnedJob> getReturnedJobs( ArrayList<String> ids){
        String Query = "FOR job in jobs\n" +
                "filter job._key in @jobIds\n" +
                "    for company in companies\n" +
                "    filter company._key == job.companyId\n" +
                "   return MERGE_RECURSIVE(\n" +
                "                job,\n" +
                "                {\"companyName\": company.companyName, \"companyPicture\": company.companyPicture}\n" +
                "                    \n" +
                "                )";
        Map<String, Object>  bindVars = new HashMap<>();
        bindVars.put("jobIds",ids);
        ArangoCursor<ReturnedJob> cursor = dbInstance.query(Query, bindVars, null, ReturnedJob.class);
        ArrayList<ReturnedJob>jobs = new ArrayList<>();
          while (cursor.hasNext())
            jobs.add(cursor.next());
        return jobs;
    }

    public void userSaveJob(String userId,String jobId) throws SQLException {
        String query = "{CALL Save_Job(?,?)}";
        CallableStatement stmt = mysqlConnection.prepareCall(query);
        stmt.setString(1, userId);
        stmt.setString(2, jobId);
        stmt.executeQuery();
    }


    public  ArrayList<ReturnedJob> getSavedJobs(String userId) throws SQLException {
        String query = "{CALL Get_Saved_Job(?)}";
        CallableStatement stmt = mysqlConnection.prepareCall(query);
        stmt.setString(1, userId);
        ResultSet result = stmt.executeQuery();
        ArrayList<String> Ids = new ArrayList<String>();
        while (result.next()) {
            String jobId = result.getString("job_id");
            Ids.add(jobId);
        }
        return (getReturnedJobs(Ids));
    }

        public void createJobAsaCompany(JsonObject args) throws SQLException {
            String userId =  args.get("userId").getAsString();
            String companyId =  args.get("companyId").getAsString();
            if(validateCompany(userId,companyId)) {
                String query = "{CALL Insert_Job(?,?,?)}";
                CallableStatement stmt = mysqlConnection.prepareCall(query);
                String jobId = UUID.randomUUID().toString();
                stmt.setString(1, jobId);
                String jobTitle =  args.get("jobTitle").getAsString();
                stmt.setString(2, jobTitle);
                stmt.setString(3, companyId);
                stmt.executeQuery();
                Job job = new Job();
                job.setJobId(jobId);
                job.setCompanyId(companyId);
                job.setJobTitle(jobTitle);
                if(args.get("industryType") != null) {
                    String industryType =  args.get("industryType").getAsString();
                    job.setJobTitle(industryType);
                }
                if(args.get("jobBrief") != null) {
                    String jobBrief = (args.get("jobBrief")).getAsString();
                    job.setJobBrief(jobBrief);
                }
                if(args.get("requiredSkills") != null) {
                    JsonArray requiredSkills =  args.get("requiredSkills").getAsJsonArray();
                    String[] skills = new String[requiredSkills.size()];
                    for (int i = 0; i < skills.length; i++){
                        skills[i] = requiredSkills.get(i).toString();
                    }
                    job.setRequiredSkills(skills);
                }
                jobsCollection.insertDocument(job);
            }

    }

        public  ReturnedJob getJob(String jobId){
        ArrayList<String> ids = new ArrayList<>();
        ids.add(jobId);
        ArrayList<ReturnedJob> jobs = getReturnedJobs(ids);
        if(jobs.size() >= 0)
            return jobs.get(0);
        else
            return null;
        }

    public void editJob(JsonObject args){
        String userId = (args.get("userId")).getAsString();
        String jobId = (args.get("jobId")).getAsString();
        if(validateJob(userId,jobId)){
            String query = "For t in " + jobsCollectionName + " FILTER " +
                    "t._key == @jobId" + " UPDATE t " + "WITH{";
            String fields;
            Map<String, Object> bindVars = new HashMap<>();
            bindVars.put("jobId",jobId);
            int counter = 0;
            for (String key : args.keySet()) {
                if (!key.equals("jobId") && !key.equals("userId") && !key.equals("commandName")) {
                    if(key.equals("requiredSkills")){
                        JsonArray requiredSkills =  args.get("requiredSkills").getAsJsonArray();
                        String[] skills = new String[requiredSkills.size()];
                        for (int i = 0; i < skills.length; i++){
                            skills[i] = requiredSkills.get(i).toString();
                        }
                        query += key + ":@field" + counter + " ,";
                        bindVars.put("field" + counter, skills);
                        counter++;
                    }else {
                        query += key + ":@field" + counter + " ,";
                        bindVars.put("field" + counter, args.get(key).getAsString());
                        counter++;
                    }
                }
            }
            query = query.substring(0, query.length() - 1);
            query += "} IN " + jobsCollectionName;
            System.out.println("Query opa " + query);
            dbInstance.query(query, bindVars, null, Job.class);
        }else
            throw new BadRequestException("Invalid user");
    }

    public void deleteJobAsaCompany(String userId,String jobId) throws SQLException {
        if(validateJob(userId,jobId)) {
            jobsCollection.deleteDocument(jobId);
            String query = "{CALL delete_job(?)}";
            CallableStatement stmt = mysqlConnection.prepareCall(query);
            stmt.setString(1, jobId);
            stmt.executeQuery();
        }else
            throw new BadRequestException("Invalid User");
    }
}
