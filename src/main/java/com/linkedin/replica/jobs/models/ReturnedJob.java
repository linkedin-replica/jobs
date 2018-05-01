package com.linkedin.replica.jobs.models;

import com.arangodb.entity.DocumentField;

public class ReturnedJob {
    private  String jobId;
    @DocumentField(DocumentField.Type.KEY)
    private  String jobID;
    private  String jobTitle;
    private  String industryType;
    private  String companyId;
    private  String jobBrief;
    private  String[] requiredSkills;
    private String companyName;
    private String companyPicture;

    public ReturnedJob() {
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
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

    public void setJobBrief(String jobBrief) {
        this.jobBrief = jobBrief;
    }

    public String[] getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String[] requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPicture() {
        return companyPicture;
    }

    public void setCompanyPicture(String companyPicture) {
        this.companyPicture = companyPicture;
    }
}
