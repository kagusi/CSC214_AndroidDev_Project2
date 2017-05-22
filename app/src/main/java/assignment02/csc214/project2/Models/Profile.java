package assignment02.csc214.project2.Models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kennedy on 3/30/2017.
 */

public class Profile implements Serializable {

    private String mProfileID;
    private String mUsername;
    private String mFullName;
    private String mProfilePicLocation;
    private String mHomeTown;
    private String mDateofBith;
    private String mBiography;
    private String mDateJoined;
    private String mLastActivity;
    private boolean mIsFavorite;

    public Profile(String username, String fullName, String profilePicLocation, String homeTown, String dateofBith, String biography) {
        mUsername = username;
        mFullName = fullName;
        mProfilePicLocation = profilePicLocation;
        mHomeTown = homeTown;
        mDateofBith = dateofBith;
        mBiography = biography;

        DateFormat dateformat = new SimpleDateFormat("dd/MM/yy");
        Date currentDate = new Date();
        mDateJoined = dateformat.format(currentDate);
        mLastActivity = dateformat.format(currentDate);
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getProfilePicLocation() {
        return mProfilePicLocation;
    }

    public void setProfilePicLocation(String profilePicLocation) {
        mProfilePicLocation = profilePicLocation;
    }

    public String getHomeTown() {
        return mHomeTown;
    }

    public void setHomeTown(String homeTown) {
        mHomeTown = homeTown;
    }

    public String getDateofBith() {
        return mDateofBith;
    }

    public void setDateofBith(String dateofBith) {
        mDateofBith = dateofBith;
    }

    public String getBiography() {
        return mBiography;
    }

    public void setBiography(String biography) {
        mBiography = biography;
    }

    public String getDateJoined() {
        return mDateJoined;
    }

    public void setDateJoined(String dateJoined) {
        mDateJoined = dateJoined;
    }

    public String getLastActivity() {
        return mLastActivity;
    }

    public void setLastActivity(String lastActivity) {
        mLastActivity = lastActivity;
    }

    public String getProfileID() {
        return mProfileID;
    }

    public void setProfileID(String profileID) {
        mProfileID = profileID;
    }

    public void setmIsFavorite(boolean isfav){
	    mIsFavorite = isfav;
    }

    public boolean getIsFavorite(){
	return mIsFavorite;
    }

}
