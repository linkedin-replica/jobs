package com.linkedin.replica.jobs.models;

import com.arangodb.entity.DocumentField;

public class Company {
    @DocumentField(DocumentField.Type.KEY)
    private String companyID;
    private String companyId;
    private String companyName;
    private String companyProfileUrl;
    private String UserId;
    private String industryType;

    public Company(){

    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public void setCompanyProfilePicture(String companyProfileUrl) {
        this.companyProfileUrl = companyProfileUrl;
    }


    public void setAdminUserID(String adminUserID) {
        this.UserId = adminUserID;
    }


    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }


    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyProfilePicture() {
        return companyProfileUrl;
    }


    public String getAdminUserID() {
        return UserId;
    }


    public String getIndustryType() {
        return industryType;
    }



    public void setCompanyId(String companyId) {
        this.companyId = companyId;
        this.companyID = companyId;
    }
    public String getCompanyId() {
        return companyId;
    }

}