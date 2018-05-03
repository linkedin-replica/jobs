package Core;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDatabase;
import com.linkedin.replica.jobs.config.Configuration;
import com.linkedin.replica.jobs.database.DatabaseConnection;
import com.linkedin.replica.jobs.database.DatabaseSeed;
import com.linkedin.replica.jobs.models.Company;
import com.linkedin.replica.jobs.models.Job;
import com.linkedin.replica.jobs.models.ReturnedJob;
import com.linkedin.replica.jobs.services.JobService;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class JobsServiceTest {
    private static JobService JobService;
    private static ArangoDatabase arangoDb;
    static Configuration config;
    private static Connection mysqlDBInstance;
    static DatabaseSeed databaseSeed;
    private static ArangoCollection jobsCollection;
    private static ArangoCollection companyCollection;
    private static  ArangoDatabase dbInstance;


    @BeforeClass
    public static void init() throws IOException, SQLException, ParseException, ClassNotFoundException {
        String rootFolder = "src/main/resources/config/";
        Configuration.init(rootFolder + "app.config", rootFolder +"arango.test.config",
                rootFolder +"database.config",rootFolder + "commands.config",
                rootFolder +"controller.config");
        DatabaseConnection.init();
        mysqlDBInstance = DatabaseConnection.getInstance().getMysqlDriver();
        config = Configuration.getInstance();
        JobService = new JobService();
        arangoDb = DatabaseConnection.getInstance().getArangoDriver().db(
                Configuration.getInstance().getArangoConfigProp("db.name")
        );
        databaseSeed = new DatabaseSeed();
        databaseSeed.insertJobs();
        databaseSeed.insertCompanies();
        dbInstance = DatabaseConnection.getInstance().getArangoDriver().
                db(config.getArangoConfigProp("db.name"));
        jobsCollection = dbInstance.collection(config.getArangoConfigProp("collection.jobs.name"));
        companyCollection = dbInstance.collection(config.getArangoConfigProp("collection.companies.name"));
    }



    @Test
    public void testCreateJobAsCompany() throws Exception {
        Company comp = new Company();
        comp.setAdminUserID("1");
        comp.setCompanyId("1");
        companyCollection.insertDocument(comp);
        Job job  = new Job();
        job.setJobTitle("Test");
        HashMap<String, Object> args = new HashMap<>();
        args.put("job", job);
        args.put("companyId", "1");
        String Query = "FOR job in jobs\n" +
                "   return job";
        Map<String, Object> bindVars = new HashMap<>();
        ArangoCursor<Job> cursor = dbInstance.query(Query, bindVars, null, Job.class);
        ArrayList<Job>jobs = new ArrayList<>();
        while (cursor.hasNext())
            jobs.add(cursor.next());
        int size = jobs.size();
        JobService.serve("post.job.company",args);
        cursor = dbInstance.query(Query, bindVars, null, Job.class);
       jobs = new ArrayList<>();
        while (cursor.hasNext())
            jobs.add(cursor.next());
        assertEquals("Jobs increased by 1 " , size + 1 ,jobs.size());
    }

    @Test
    public void testJobListingService() throws Exception {
        String query = "{CALL Insert_Job(?,?,?)}";
        CallableStatement stmt = mysqlDBInstance.prepareCall(query);
        stmt.setString(1, "302");
        stmt.setString(2,"Test");
        stmt.setString(3, "203");
        stmt.executeQuery();
        Job job = new Job();
        job.setJobId("302");
        job.setCompanyId("203");
        job.setJobTitle("Test");
        jobsCollection.insertDocument(job);
        HashMap<String, Object> args = new HashMap<>();
        args.put("jobId", "302");
        Job results = (Job) JobService.serve("job.listing",args);
        assertEquals("matching Title" , "Test" ,results.getJobTitle());

    }

    @Test
    public void testDeleteJobAsCompanyService() throws Exception {
        HashMap<String, Object> args = new HashMap<>();
        args.put("jobId", "302");
        args.put("userId","1");
        JobService.serve("delete.job.company",args);
        args = new HashMap<>();
        args.put("jobId", "302");
        Job job  = (Job) JobService.serve("job.listing",args);
        assertEquals("Job is deleted" , null ,job);
    }
//

//
//    @Test
//    public void TestRespondToApplicandService(){
//
//    }
//
//
//    @Test
//    public void TestCreateJob() throws Exception {
//        Company comp = new Company();
//        comp.setAdminUserID("1");
//        comp.setCompanyId("203");
//        companyCollection.insertDocument(comp);
//        HashMap<String, Object> args = new HashMap<>();
//        args.put("userId", "1");
//        args.put("jobTitle","EMC Employer");
//        args.put("companyId","203");
//            JobService.serve("company.create.job", args);
//    }
//
//    @Test
//    public void TestGetAppliedJobs() throws Exception {
//        Job job = new Job();
//        Company comp = new Company();
//        comp.setAdminUserID("1");
//        comp.setCompanyId("203");
//        comp.setCompanyName("face");
//        job.setJobId("52");
//        job.setCompanyId("203");
//        job.setJobTitle("ACMER1");
//        jobsCollection.insertDocument(job);
//        HashMap<String, Object> args = new HashMap<>();
//        args.put("userId", "1");
//        args.put("jobId","52");
//        String query = "{CALL Insert_Job(?,?,?)}";
//        Connection mysqlConnection = DatabaseConnection.getInstance().getMysqlDriver();
//        CallableStatement stmt = mysqlConnection.prepareCall(query);
//        stmt.setString(1, "52");
//        stmt.setString(2, (String)args.get("jobTitle"));
//        stmt.setString(3, "203");
//        stmt.executeQuery();
//        JobService.serve("user.apply.job",args);
//        ArrayList<ReturnedJob> appliedJobs = (ArrayList<ReturnedJob>) JobService.serve("view.applied.jobs", args);
//        assertEquals("CompanyName is face" , "face" ,appliedJobs.get(0).getCompanyName());
//    }
//
//    @Test
//    public void TestEditJob() throws Exception{
//        Job job = new Job();
//        Company comp = new Company();
//        comp.setAdminUserID("1");
//        comp.setCompanyId("214");
//        companyCollection.insertDocument(comp);
//        job.setJobId("308");
//        job.setCompanyId("213");
//        job.setJobTitle("ACMER");
//        jobsCollection.insertDocument(job);
//        Job jobTest = jobsCollection.getDocument("308",Job.class);
//        assertEquals("job Title : ACMER " , "ACMER" , jobTest.getJobTitle());
//        HashMap<String, Object> args = new HashMap<>();
//        args.put("userId","1");
//        args.put("jobId", "308");
//        args.put("jobTitle", "Developer");
//        JobService.serve("edit.job", args);
//         jobTest = jobsCollection.getDocument("308",Job.class);
//        assertEquals("job Title : Developer " , "Developer" , jobTest.getJobTitle());
//    }

    @AfterClass
    public static void cleanAfterTest() throws IOException, SQLException {
        databaseSeed.deleteAllJobs();
        databaseSeed.deleteAllCompanies();
    }

}
