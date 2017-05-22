package assignment02.csc214.project2.Models;

import java.io.Serializable;

/**
 * Created by Kennedy on 3/30/2017.
 */

public class Like implements Serializable {

    private String mLikeID;
    private String mFeedID;
    private String mLikerUsername;

    public Like(String feedID, String likerUsername) {
        mFeedID = feedID;
        mLikerUsername = likerUsername;
    }

    public String getFeedID() {
        return mFeedID;
    }

    public void setFeedID(String feedID) {
        mFeedID = feedID;
    }

    public String getLikerUsername() {
        return mLikerUsername;
    }

    public void setLikerUsername(String likerUsername) {
        mLikerUsername = likerUsername;
    }

    public String getLikeID() {
        return mLikeID;
    }

    public void setLikeID(String likeID) {
        mLikeID = likeID;
    }
}
