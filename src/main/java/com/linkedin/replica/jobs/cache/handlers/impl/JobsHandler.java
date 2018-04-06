package com.linkedin.replica.jobs.cache.handlers.impl;
import com.linkedin.replica.jobs.cache.handlers.CacheHandler;
import com.linkedin.replica.jobs.models.Job;

import java.io.IOException;
import java.util.List;

public interface JobsHandler extends CacheHandler{

    void saveJobListing(String [] jobIds, Object jobs) throws IOException;

    Object getJobListingFromCache(String key, Class<?> tClass) throws IOException;

    void deleteJobListing(String key);

}
