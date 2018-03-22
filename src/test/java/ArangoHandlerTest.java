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


import static org.junit.Assert.assertEquals;

public class ArangoHandlerTest {
    private static ArangoSQLJobsHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    static Configuration config;
    static DatabaseSeed databaseSeed;

    @BeforeClass
    public static void init() throws IOException, SQLException, ClassNotFoundException {
        Configuration.init("src/main/resources/config/app.config", "src/main/resources/config/arango.config",
                "src/main/resources/config/database.config","src/main/resources/config/commands.config",
                "src/main/resources/config/controller.config");
        config = Configuration.getInstance();
        DatabaseConnection.init();
        arangoHandler = new ArangoSQLJobsHandler();
        databaseSeed = new DatabaseSeed();
        arangoHandler.connect();
        arangoDb =DatabaseConnection.getInstance().getArangoDriver().
                db(config.getAppConfigProp("db.name"));
    }

    @Before
    public void initBeforeTest() throws IOException, ParseException, SQLException, ClassNotFoundException {
        databaseSeed.insertJobs();
    }



    @Test
    public void JobListingTest() throws IOException, SQLException, ClassNotFoundException {
        Job results = arangoHandler.getJob("1067639");
        assertEquals("matching position name" , "Data Wrangling Engineer" ,results.getPositionName());
        assertEquals("matching company Name" , "DFKI" ,results.getCompanyName());
        assertEquals("matching industryType" , "Software" ,results.getIndustryType());
    }

//    @Test
//    public void EditJobTest() throws IOException{
//        String collectionName = config.getArangoConfig("collection.jobs.name");
//        ArangoHandler arangoHandler = new ArangoHandler();
//        LinkedHashMap<String, String> args = new LinkedHashMap<>();
//        args.put("professionLevel", "Junior");
//        arangoHandler.EditJob("414592", args);
//        Job newJob = arangoHandler.getJob("414592");
//        assertEquals("matching new professional level" , "Junior" , newJob.getProfessionLevel());
//    }
//
//    @Test
//    public void DeletejobTest() throws IOException, SQLException, ClassNotFoundException {
//        String collectionName = config.getArangoConfig("collection.jobs.name");
//        ArangoHandler arangoHandler = new ArangoHandler();
//        arangoHandler.deleteJobAsaCompany("414595");
//        Job newJob = arangoHandler.getJob("414595");
//        assertEquals("Job has been deleted" , null , newJob);
//
//    }
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




//    @AfterClass
//    public static void cleanAfterTest() throws IOException {
//        arangoDb.collection(
//                config.getArangoConfig("collection.users.name")
//        ).drop();
//    }
//
//    @AfterClass
//    public static void clean() throws IOException, SQLException, ClassNotFoundException {
//
//        DatabaseConnection.getDBConnection().closeConnections();
//    }

}