package Core;

import com.arangodb.ArangoDatabase;
import com.linkedin.replica.jobs.config.Configuration;
import com.linkedin.replica.jobs.database.handlers.impl.ArangoSQLJobsHandler;
import com.linkedin.replica.jobs.database.DatabaseConnection;
import com.linkedin.replica.jobs.database.DatabaseSeed;
import com.linkedin.replica.jobs.models.Job;
import org.json.simple.parser.ParseException;
import org.junit.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;


import static org.junit.Assert.assertEquals;

public class ArangoHandlerTest {
    private static ArangoSQLJobsHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    static Configuration config;
   static DatabaseSeed databaseSeed;

    @BeforeClass
    public static void init() throws IOException, SQLException, ClassNotFoundException, ParseException {
        Configuration.init("src/main/resources/config/app.config", "src/main/resources/config/arango.config",
                "src/main/resources/config/database.config","src/main/resources/config/commands.config",
                "src/main/resources/config/controller.config","src/main/resources/config/cache.config");
         config = Configuration.getInstance();
         DatabaseConnection.init();
        arangoHandler = new ArangoSQLJobsHandler();
        databaseSeed = new DatabaseSeed();
        arangoHandler.connect();
        arangoDb =DatabaseConnection.getInstance().getArangoDriver().
                db(config.getAppConfigProp("db.name"));
        databaseSeed.insertJobs();
    }



    @Test
    public void jobListingTest() throws IOException, SQLException, ClassNotFoundException {
        Job results = arangoHandler.getJob("1");
        assertEquals("matching position name" , "Data Wrangling Engineer" ,results.getPositionName());
        assertEquals("matching company Name" , "DFKI" ,results.getCompanyName());
        assertEquals("matching industryType" , "Software" ,results.getIndustryType());
    }

    @Test
    public void editJobTest() throws IOException{
        LinkedHashMap<String, String> args = new LinkedHashMap<>();
        args.put("professionLevel", "Junior");
        arangoHandler.EditJob("1", args);
        Job newJob = arangoHandler.getJob("1");
        assertEquals("matching new professional level" , "Junior" , newJob.getProfessionLevel());
    }
//
    @Test
    public void deleteJobTest() throws IOException, SQLException, ClassNotFoundException {
        arangoHandler.deleteJobAsaCompany("2");
        Job newJob = arangoHandler.getJob("2");
        assertEquals("Job has been deleted" , null , newJob);

    }
//    @Test
//    public void getAppliedjobsTest() throws IOException{
//       ArrayList<String> ids = new ArrayList<String>();
//       ids.add("144616");
//       ids.add("156540");
//       ArrayList<Job> jobs =arangoHandler.getAppliedjobs(ids);
//       System.out.println(jobs.get(0).getCompanyName());
//        System.out.println(jobs.get(1).getCompanyName());
//        assertEquals("Jobs must be of size 2" ,jobs.size() ,2);
//
//    }




    @AfterClass
    public static void cleanAfterTest() throws IOException, SQLException {
        databaseSeed.deleteAllJobs();
    }



}