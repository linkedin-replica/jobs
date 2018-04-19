package com.linkedin.replica.jobs.models;

import com.arangodb.entity.DocumentField;

public class Job {
    private  String jobId;
    @DocumentField(DocumentField.Type.KEY)
    private  String jobID;
    private  String jobTitle;
    private  String industryType;
    private  String companyId;
    private  String jobBrief;
    private  String[] requiredSkills;


    public Job(){
    }

    public String[] getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String[] requiredSkills) {
        this.requiredSkills = requiredSkills;
    }


    public void setJobID(String jobID) {
        this.jobId = jobID;
        this.jobID = jobID;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public void setJobBrief(String jobBrief) {
        this.jobBrief = jobBrief;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobID() {
        return jobId;
    }

    public String getIndustryType() {
        return industryType;
    }


    public String getCompanyID() {
        return companyId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getJobBrief() {
        return jobBrief;
    }


}
