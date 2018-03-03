package  models;

public class Company {
    private String companyName;
    private String companyProfilePicture;
    private String companyID;

    public String getCompanyID() {
        return companyID;
    }

    public String getCompanytype() {
        return companytype;
    }

    private String adminUserName;
    private String adminUserID;
    private String industryType;
    private String companyLocation;
    private String companytype;
    private String[] posts;
    private String[] jobListings;
    public Company(String companyName,String companyID,String companyProfilePicture,String adminUserName,String adminUserID,
                   String industryType,String companyLocation,String companytype,String[] posts,String [] lightJobListings) {
        this.companyName = companyName;
        this.adminUserName=adminUserName;
        this.companyID = companyID;
        this.companyProfilePicture = companyProfilePicture;
        this.adminUserID = adminUserID;
        this.industryType = industryType;
        this.companyLocation = companyLocation;
        this.companytype = companytype;
        this.posts = posts;
        this.jobListings = lightJobListings;

    }
    public Company(){

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


    public String[] getPosts() {
        return posts;
    }

    public String[] getJobListings() {
        return jobListings;
    }
}