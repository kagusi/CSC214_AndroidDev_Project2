package assignment02.csc214.project2.Models;

import java.io.Serializable;

/**
 * Created by Kennedy on 3/30/2017.
 */

public class Favorite implements Serializable {

    private String mFaoriteID;
    private String mUsername;
    private String mFavoriteUsername;

    public Favorite(String username, String favoriteUsername) {
        mUsername = username;
        mFavoriteUsername = favoriteUsername;
    }

    public String getFaoriteID() {
        return mFaoriteID;
    }

    public void setFaoriteID(String faoriteID) {
        mFaoriteID = faoriteID;
    }




    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getFavoriteUsername() {
        return mFavoriteUsername;
    }

    public void setFavoriteUsername(String favoriteUsername) {
        mFavoriteUsername = favoriteUsername;
    }
}
