package com.linkedin.replica.jobs.models;

public class Job {



    private  String jobID ;
    private  String industryType;
    private  String employmentType;
    private  String jobFunctions;
    private  String positionName;
    private  String professionLevel;
    private  String companyID;
    private  String companyName;
    private  String companyLocation;
    private   String compnayPicture;

    public Job(){
    }

    public Job(String jobID, String industryType, String employmentType, String jobFunctions, String positionName, String professionLevel, String companyID, String companyName, String companyLocation, String compnayPicture, String jobBrief) {
        this.jobID = jobID;
        this.industryType = industryType;
        this.employmentType = employmentType;
        this.jobFunctions = jobFunctions;
        this.positionName = positionName;
        this.professionLevel = professionLevel;
        this.companyID = companyID;
        this.companyName = companyName;
        this.companyLocation = companyLocation;
        this.compnayPicture = compnayPicture;
        this.jobBrief = jobBrief;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public void setJobFunctions(String jobFunctions) {
        this.jobFunctions = jobFunctions;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public void setProfessionLevel(String professionLevel) {
        this.professionLevel = professionLevel;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public void setCompnayPicture(String compnayPicture) {
        this.compnayPicture = compnayPicture;
    }

    public void setJobBrief(String jobBrief) {
        this.jobBrief = jobBrief;
    }

    public String getJobID() {

        return jobID;
    }

    public String getIndustryType() {
        return industryType;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public String getJobFunctions() {
        return jobFunctions;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getProfessionLevel() {
        return professionLevel;
    }

    public String getCompanyID() {
        return companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public String getCompnayPicture() {
        return compnayPicture;
    }

    public String getJobBrief() {
        return jobBrief;
    }

    String jobBrief;

}
