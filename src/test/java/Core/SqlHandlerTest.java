package Core;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDatabase;
import com.linkedin.replica.jobs.config.Configuration;
import com.linkedin.replica.jobs.database.DatabaseSeed;
import com.linkedin.replica.jobs.database.handlers.impl.ArangoSQLJobsHandler;
import com.linkedin.replica.jobs.database.DatabaseConnection;
//import models.testUser;
import com.linkedin.replica.jobs.models.Company;
import com.linkedin.replica.jobs.models.Job;
import org.json.simple.parser.ParseException;
import org.junit.*;
//import utils.ConfigReader;
//
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


import static org.junit.Assert.assertEquals;

public class SqlHandlerTest {
    private static ArangoSQLJobsHandler mysqlHandler;
    static DatabaseSeed databaseSeed;
    private static ArangoDatabase arangoDb;
    private static ArangoCollection jobsCollection;
    private static ArangoCollection companyCollection;
    static Configuration config;
    private static  ArangoDatabase dbInstance;


    @BeforeClass
    public static void init() throws IOException, SQLException, ClassNotFoundException, ParseException {
        Configuration.init("src/main/resources/config/app.config", "src/main/resources/config/arango.config",
                "src/main/resources/config/database.config", "src/main/resources/config/commands.config",
                "src/main/resources/config/controller.config");
        DatabaseConnection.init();
        config = Configuration.getInstance();
        arangoDb = DatabaseConnection.getInstance().getArangoDriver().db(
                Configuration.getInstance().getArangoConfigProp("db.name")
        );
        databaseSeed = new DatabaseSeed();

        databaseSeed.insertJobs();
        databaseSeed.insertCompanies();
        mysqlHandler = new ArangoSQLJobsHandler();
        dbInstance = DatabaseConnection.getInstance().getArangoDriver().
                db(config.getArangoConfigProp("db.name"));
        jobsCollection = dbInstance.collection(config.getArangoConfigProp("collection.jobs.name"));
        companyCollection = dbInstance.collection(config.getArangoConfigProp("collection.companies.name"));
    }


    @Test
    public void getAppliedjobsTest() throws IOException, SQLException {
        ArrayList<String> jobs = mysqlHandler.getAppliedJobsIDs("1");
        assertEquals("Jobs must be of size 1" ,jobs.size() ,2);
    }
//
    @Test
    public void respondToApplicantTest() throws SQLException {
        Company comp = new Company();
        comp.setAdminUserID("100");
        comp.setCompanyId("103");
        companyCollection.insertDocument(comp);
        Job job = new Job();
        job.setJobId("102");
        job.setCompanyId("103");
        jobsCollection.insertDocument(job);
        mysqlHandler.userApplyForJob("5","102");
        mysqlHandler.respondToJobsAsCompany("100","102","5",1);
    }

    @Test
    public void userAppliedForJobTest() throws SQLException {
        mysqlHandler.userApplyForJob("4","20");
    }

    @Test
    public void deletejob() throws SQLException {
        Company comp = new Company();
        comp.setAdminUserID("100");
        comp.setCompanyId("102");
        companyCollection.insertDocument(comp);
        Job job = new Job();
        job.setJobId("101");
        job.setCompanyId("102");
        jobsCollection.insertDocument(job);
        Company compTest = companyCollection.getDocument("102",Company.class);
        Job jobTest = jobsCollection.getDocument("101",Job.class);
        assertEquals("company id = 102" ,jobTest.getCompanyId() ,"102");
        assertEquals("user id = 100 " ,compTest.getAdminUserID() ,"100");
        mysqlHandler.deleteJobAsaCompany("100","101");
        Job jobTest1 = jobsCollection.getDocument("101",Job.class);
        assertEquals("job is deleted " , jobTest1 ,null);
    }

    @AfterClass
    public static void clean() throws IOException, SQLException, ClassNotFoundException {
        databaseSeed.deleteAllJobs();
        databaseSeed.deleteAllCompanies();
        DatabaseConnection.getInstance().closeConnections();
    }



}