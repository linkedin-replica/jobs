package database;

import java.io.IOException;

public class Main {
    public static void main(String [] args)throws Exception{
        new DatabaseSeed();
        DatabaseSeed.insertJobs();
    }
}
