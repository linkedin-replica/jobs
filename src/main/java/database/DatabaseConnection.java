package database;

import com.arangodb.ArangoDB;
import config.Configuration;
import config.DatabaseConnections;
import utils.ConfigReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * A singleton class carrying a database instance
 */
public class DatabaseConnection {
    private ArangoDB arangoDriver;
    private ConfigReader config;
    private Connection mysqlConn;
    private static DatabaseConnection instance;
    private Properties properties;


    private volatile static DatabaseConnection dbConnection;

    private DatabaseConnection() throws IOException, SQLException, ClassNotFoundException {
        config = new ConfigReader("database_auth");
        properties = new Properties();
        properties.load(new FileInputStream(Configuration.getInstance().getDatabaseConfigPath()));
        mysqlConn = getNewMysqlDB();
        initializeArangoDB();
    }

    private void initializeArangoDB() {
        arangoDriver = new ArangoDB.Builder()
                .user(config.getConfig("arangodb.user"))
                .password(config.getConfig("arangodb.password"))
                .build();
    }

    /**
     * Get a singleton DB instance
     * @return The DB instance
     */
    public static DatabaseConnection getDBConnection() throws IOException, SQLException, ClassNotFoundException {
        if(dbConnection == null) {
            synchronized (DatabaseConnection.class) {
                if (dbConnection == null)
                    dbConnection = new DatabaseConnection();
            }
        }
        return dbConnection;
    }

    public static DatabaseConnection getInstance() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
        if(instance == null){
            synchronized (DatabaseConnections.class) {
                if(instance == null){
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    /**
     * Implement the clone() method and throw an exception so that the singleton cannot be cloned.
     */
    public Object clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException("DatabaseConnection singleton, cannot be clonned");
    }


    private Connection getNewMysqlDB() throws SQLException, ClassNotFoundException{
        // This will load the MySQL driver, each DB has its own driver
        Class.forName(properties.getProperty("mysql.database-driver"));
        // create new connection and return it
        return DriverManager.getConnection(properties.getProperty("mysql.url"),
                properties.getProperty("mysql.userName"),
                properties.getProperty("mysql.password"));
    }
    public void closeConnections() throws SQLException {
        arangoDriver.shutdown();
        if(mysqlConn != null)
            mysqlConn.close();
    }

    public Connection getMysqlConn() {
        return mysqlConn;
    }


    public ArangoDB getArangoDriver() {
        return arangoDriver;
    }
}