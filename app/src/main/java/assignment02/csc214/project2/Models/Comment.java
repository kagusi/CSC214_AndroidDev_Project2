package assignment02.csc214.project2.Models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kennedy on 3/30/2017.
 */

public class Comment implements Serializable {

    private String mCommentID;
    private String mFeedID;
    private String mCommenterUsername;
    private String mCommenterProfilePicLocation;
    private String mComment;
    private String mDate;
    private String mFullName;
    private String mNumberOfComments;

    public Comment(String feedID, String commenterUsername, String comment) {
        mFeedID = feedID;
        mCommenterUsername = commenterUsername;
        mComment = comment;

        DateFormat dateformat = new SimpleDateFormat("dd/MM/yy 'at' hh:mm:ss a zzz");
        Date currentDate = new Date();
        mDate = dateformat.format(currentDate);
    }

    public String getFeedID() {
        return mFeedID;
    }

    public void setFeedID(String feedID) {
        mFeedID = feedID;
    }

    public String getCommenterUsername() {
        return mCommenterUsername;
    }

    public void setCommenterUsername(String commenterUsername) {
        mCommenterUsername = commenterUsername;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getCommentID() {
        return mCommentID;
    }

    public void setCommentID(String commentID) {
        mCommentID = commentID;
    }

    public String getCommenterProfilePicLocation() {
        return mCommenterProfilePicLocation;
    }

    public void setCommenterProfilePicLocation(String commenterProfilePicLocation) {
        mCommenterProfilePicLocation = commenterProfilePicLocation;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getNumberOfComments() {
        return mNumberOfComments;
    }

    public void setNumberOfComments(String numberOfComments) {
        mNumberOfComments = numberOfComments;
    }
}
