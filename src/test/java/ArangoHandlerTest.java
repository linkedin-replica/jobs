import com.arangodb.ArangoDatabase;
import database.ArangoHandler;
import database.DatabaseConnection;
import models.Job;
import org.junit.*;
import utils.ConfigReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


import static org.junit.Assert.assertEquals;

public class ArangoHandlerTest {
    private static ArangoHandler arangoHandler;
    private static ArangoDatabase arangoDb;
    static ConfigReader config;

    @BeforeClass
    public static void init() throws IOException, SQLException, ClassNotFoundException {
        ConfigReader.isTesting = true;
        config = ConfigReader.getInstance();
        arangoHandler = new ArangoHandler();
        arangoDb = DatabaseConnection.getDBConnection().getArangoDriver().db(
                ConfigReader.getInstance().getArangoConfig("db.name")
        );
    }

//    @Before
//    public void initBeforeTest() throws IOException {
//        arangoDb.createCollection(
//                config.getArangoConfig("collection.users.name")
//        );
//    }



    @Test
    public void JobListingTest() throws IOException, SQLException, ClassNotFoundException {
        String collectionName = config.getArangoConfig("collection.jobs.name");
        ArangoHandler arangoHandler = new ArangoHandler();
        Job results = arangoHandler.getJob("1");
        assertEquals("matching position name" , "Software Developer" ,results.getPositionName());
        assertEquals("matching company Name" , "Ergasti" ,results.getCompanyName());
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

    @Test
    public void DeletejobTest() throws IOException, SQLException, ClassNotFoundException {
        String collectionName = config.getArangoConfig("collection.jobs.name");
        ArangoHandler arangoHandler = new ArangoHandler();
        arangoHandler.deleteJobAsaCompany("414595");
        Job newJob = arangoHandler.getJob("414595");
        assertEquals("Job has been deleted" , null , newJob);

    }
    @Test
    public void getAppliedjobsTest() throws IOException{
       ArrayList<String> ids = new ArrayList<String>();
       ids.add("144616");
       ids.add("156540");
       ArrayList<Job> jobs =arangoHandler.getAppliedjobs(ids);
       System.out.println(jobs.get(0).getCompanyName());
        System.out.println(jobs.get(1).getCompanyName());
        assertEquals("Jobs must be of size 2" ,jobs.size() ,2);

    }


    @AfterClass
    public static void clean() throws IOException, SQLException, ClassNotFoundException {
        ConfigReader.isTesting = false;
        DatabaseConnection.getDBConnection().closeConnections();
    }

//    @AfterClass
//    public static void cleanAfterTest() throws IOException {
//        arangoDb.collection(
//                config.getArangoConfig("collection.users.name")
//        ).drop();
//    }
//
//    @AfterClass
//    public static void clean() throws IOException {
//        ConfigReader.isTesting = false;
//        DatabaseConnection.getDBConnection().closeConnections();
//    }

}