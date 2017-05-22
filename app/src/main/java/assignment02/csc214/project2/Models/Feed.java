package assignment02.csc214.project2.Models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kennedy on 3/30/2017.
 */

public class Feed implements Serializable {

    private String mFeedID;
    private String mUsername;
    private String mPost;
    private int mNumberOfLikes;
    private int mNumberOfComments;
    private String mDatePosted;
    private boolean isLiked;
    private String mProfilePicLocation;
    private String mPostImageLocation;
    private String mFullName;

    public Feed(String username, String post, String postImage) {
        mUsername = username;
        mPost = post;
        mPostImageLocation = postImage;
        mNumberOfLikes = 0;
        mNumberOfComments = 0;

        DateFormat dateformat = new SimpleDateFormat("dd/MM/yy 'at' hh:mm:ss a zzz");
        Date currentDate = new Date();
        mDatePosted = dateformat.format(currentDate);
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPost() {
        return mPost;
    }

    public void setPost(String post) {
        mPost = post;
    }

    public int getNumberOfLikes() {
        return mNumberOfLikes;
    }

    public void setNumberOfLikes(int numberOfLikes) {
        mNumberOfLikes = numberOfLikes;
    }

    public int getNumberOfComments() {
        return mNumberOfComments;
    }

    public void setNumberOfComments(int numberOfComments) {
        mNumberOfComments = numberOfComments;
    }

    public String getDatePosted() {
        return mDatePosted;
    }

    public void setDatePosted(String datePosted) {
        mDatePosted = datePosted;
    }

    public String getFeedID() {
        return mFeedID;
    }

    public void setFeedID(String feedID) {
        mFeedID = feedID;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getProfilePicLocation() {
        return mProfilePicLocation;
    }

    public void setProfilePicLocation(String profilePicLocation) {
        mProfilePicLocation = profilePicLocation;
    }

    public String getPostImageLocation() {
        return mPostImageLocation;
    }

    public void setPostImageLocation(String postImageLocation) {
        mPostImageLocation = postImageLocation;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }
}
