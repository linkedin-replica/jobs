package com.linkedin.replica.jobs.cache.handlers.impl;
import com.linkedin.replica.jobs.cache.handlers.CacheHandler;
import com.linkedin.replica.jobs.models.Job;

import java.io.IOException;
import java.util.List;

public interface JobsHandler extends CacheHandler{
    public void saveJobListings(String userId, Object jobs) throws IOException;
    List<Job> getJobListingsFromCache(String userId) throws IOException;

}
