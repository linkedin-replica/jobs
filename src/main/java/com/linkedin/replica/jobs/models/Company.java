package com.linkedin.replica.jobs.models;

import com.arangodb.entity.DocumentField;

public class Company {
    @DocumentField(DocumentField.Type.KEY)
    private String companyID;
    private String companyId;
    private String companyName;
    private String companyProfilePicture;
    private String adminUserName;
    private String adminUserID;
    private String industryType;
    private String companyLocation;
    private String companytype;
    private String[] posts;
    private String[] jobListings;


    public Company(){

    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public void setCompanyProfilePicture(String companyProfilePicture) {
        this.companyProfilePicture = companyProfilePicture;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public void setAdminUserID(String adminUserID) {
        this.adminUserID = adminUserID;
    }


    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }
    public void setCompanytype(String companytype) {
        this.companytype = companytype;
    }
    public void setPosts(String[] posts) {
        this.posts = posts;
    }
    public void setJobListings(String[] jobListings) {
        this.jobListings = jobListings;
    }
    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyProfilePicture() {
        return companyProfilePicture;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public String getAdminUserID() {
        return adminUserID;
    }


    public String getIndustryType() {
        return industryType;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public String getCompanyID() {
        return companyID;
    }

    public String getCompanytype() {
        return companytype;
    }

    public String[] getPosts() {
        return posts;
    }

    public String[] getJobListings() {
        return jobListings;
    }
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
        this.companyID = companyId;
    }
}