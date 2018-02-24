package models;

public class lightUser {
    private int UserID;
    private String firstName;
    private String lastName;
    private String currentPosition;
    private String profilePictureURL;

    public void setUserID(int userID) {
        UserID = userID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public int getUserID() {

        return UserID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }
}
