package com.linkedin.replica.jobs.database;

public class Main {
    public static void main(String [] args)throws Exception{
        new DatabaseSeed();
        DatabaseSeed.insertJobs();
    }
}
