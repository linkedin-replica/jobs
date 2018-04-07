package com.linkedin.replica.jobs.cache.handlers;
import com.linkedin.replica.jobs.cache.handlers.CacheHandler;
import com.linkedin.replica.jobs.models.Job;

import java.io.IOException;
import java.util.List;

public interface JobsCacheHandler extends CacheHandler{

    void saveJobListing(String [] jobIds, Object jobs) throws IOException;

    Object getJobListingFromCache(String key);

    void deleteJobListing(String key);

}
