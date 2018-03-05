package database;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import org.javalite.activejdbc.Base;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MysqlHandler implements DatabaseHandler {


    /**
     *
     */
    private Properties properties;

    /**
     * @throws IOException
     */
    public MysqlHandler() throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream("config"));
    }

    public void connect() {
        Base.open(properties.getProperty("development.driver"), properties.getProperty("development.url"), properties.getProperty("development.username"), properties.getProperty("development.password"));
    }

    public void disconnect() {
        Base.close();
    }

    /**
     * Get the user of an email or null if it's not found
     * @param email of the user
     * @return User
     */


}