package assignment02.csc214.project2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import assignment02.csc214.project2.Database.NetworkDBSchema.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import assignment02.csc214.project2.Models.*;

/**
 * Created by Kennedy on 3/31/2017.
 */

public class ModelQueryManager implements Serializable {
    private static ModelQueryManager MODELMANAGER;

    private final Context mContext;
    private final SQLiteDatabase mDatabase;

    private ModelQueryManager(Context context){

        mContext = context.getApplicationContext();
        mDatabase = new NetworkDBHelper(mContext).getWritableDatabase();

    }

    public static ModelQueryManager get(Context context) {
        MODELMANAGER = new ModelQueryManager(context);
        return MODELMANAGER;
    }

    //Queries database for an account with the supplied username
    public Account login(String[] username){
        Account account;
        Cursor cursor = mDatabase.query(
                AccountTable.NAME, // table name
                null,           // which columns; null for all
                "username = ?",          // where clause, e.g. id=?
                username,       // where arguments
                null,       // group by
                null,       // having
                null        // order by
        );
        if(cursor == null)
            return null;
        else
        {
            NetworkCursorWrapper wrapper = new NetworkCursorWrapper(cursor);

            try {
                wrapper.moveToFirst();
                if(wrapper.getCount() == 0)
                    return null;
                else
                    account = wrapper.getAccount();
            }
            finally {
                wrapper.close();
            }
            return account;
        }

    }

    //Insert account into Database
    public void createAccount(Account account)
    {
        ContentValues values = getAccountContentvalues(account);
        mDatabase.insert(AccountTable.NAME, null, values);
    }


    //insert post/feed into database
    public void createFeed(Feed feed)
    {
        ContentValues values = getFeedContentvalues(feed);
        mDatabase.insert(FeedTable.NAME, null, values);
    }

    //insert "comment" into database
    public void createComment(Comment comment)
    {
        ContentValues values = getCommentContentvalues(comment);
        mDatabase.insert(CommentTable.NAME, null, values);
    }

    //insert like into database
    public void createLike(Like like)
    {
        ContentValues values = getLikeContentvalues(like);
        mDatabase.insert(LikeTable.NAME, null, values);
    }

    //Remove Like
    public void removeLike(String feedID, String likerUsername)
    {
        String[] uname = new String[2];
        uname[0] = feedID;
        uname[1] = likerUsername;

        mDatabase.delete(LikeTable.NAME,
                LikeTable.Cols.FEEDID +"=? AND " +LikeTable.Cols.LIKERUSERNAME +"=?",
                uname);
    }

    //insert favorite into Database
    public void createFavorite(Favorite favorite)
    {
        ContentValues values = getFavoriteContentvalues(favorite);
        mDatabase.insert(FavoriteTable.NAME, null, values);
    }

    //Remove Favorite
    public void removeFavorite(String username, String favorite)
    {
        String[] uname = new String[2];
        uname[0] = username;
        uname[1] = favorite;

        mDatabase.delete(FavoriteTable.NAME,
                FavoriteTable.Cols.USERNAME +"=? AND " +FavoriteTable.Cols.FAVORITEUSERNAME +"=?",
                uname);
    }

    //Update Password
    public void updatePassword(Account account)
    {
        String[] uname = new String[1];
        uname[0] = account.getUsername();
        ContentValues values = getAccountContentvalues(account);

        mDatabase.update(AccountTable.NAME,
                values,
                AccountTable.Cols.USERNAME + "=?",
                uname);
    }

    //Create profile
    public void createProfile(Profile profile)
    {
        ContentValues values = getProfileContentvalues(profile);
        mDatabase.insert(ProfileTable.NAME, null, values);
    }


    //Queries database for a profile with the supplied username
    public Profile getProfile(String[] username){
        Profile profile;
        Cursor cursor = mDatabase.query(
                ProfileTable.NAME, // table name
                null,           // which columns; null for all
                ProfileTable.Cols.USERNAME + "=?",          // where clause, e.g. id=?
                username,       // where arguments
                null,       // group by
                null,       // having
                null        // order by

        );

        NetworkCursorWrapper wrapper = new NetworkCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            if(wrapper.getCount() == 0)
                return null;
            else
                profile = wrapper.getProfile();
        }
        finally {
            wrapper.close();
        }

        return profile;

    }

    //Update Profile
    public void updateProfile(Profile profile)
    {
        String[] uname = new String[1];
        uname[0] = profile.getUsername();
        ContentValues values = getProfileContentvalues(profile);

        mDatabase.update(ProfileTable.NAME,
                values,
                ProfileTable.Cols.USERNAME + "=?",
                uname);
    }

    //Queries database for list of all profiles
    public List<Profile> getListOfProfile(){

        List<Profile> profileList = new ArrayList<>();

        Cursor cursor = mDatabase.query(
                ProfileTable.NAME, // table name
                null,           // which columns; null for all
                null,          // where clause, e.g. id=?
                null,       // where arguments
                null,       // group by
                null,       // having
                null        // order by
        );

        NetworkCursorWrapper wrapper = new NetworkCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            while(wrapper.isAfterLast() == false) {
                Profile profile = wrapper.getProfile();
                profileList.add(profile);
                wrapper.moveToNext();
            }
        }
        finally {
            cursor.close();
            wrapper.close();
        }

        return profileList;

    }

    //Query Datase for all post from the user's favorite list including the user's post
    public List<Feed> getFeed(String[] usernames ){

        List<Feed> posts = new ArrayList<>();

        String MY_QUERY = "SELECT " +" a."+FavoriteTable.Cols.USERNAME+ " AS favUsername "+", "
                +" i."+ProfileTable.Cols.USERNAME+ " AS profUsername" +", "+ " i."+ProfileTable.Cols.PROFILEPICLOCATION+ " AS profPicLoc " + " , " +" i."+ProfileTable.Cols.FULLNAME+ " AS profFullName "+
                " , "+ " s."+FeedTable.Cols.POSTIMAGE+" AS postImage " +" , "+" s."+FeedTable.Cols.POST+" AS post "+" , "+" s."+FeedTable.Cols.DATEPOSTED+" AS datePosted "+" , "+ " s.feedid AS feedid"+
                " , "+" s."+FeedTable.Cols.NUMBEROFCOMMENTS+" AS numComments "+" , "+" s."+FeedTable.Cols.NUMBEROFLIKES+" AS numLikes "+" , "+" s."+FeedTable.Cols.USERNAME+ " AS feedUsername " +
                " FROM " +ProfileTable.NAME+ " AS i "   + " INNER JOIN " + FavoriteTable.NAME+ " AS a " + " ON " + " i."+ProfileTable.Cols.USERNAME +
                " = " +" a."+FavoriteTable.Cols.FAVORITEUSERNAME+ " INNER JOIN "+ FeedTable.NAME+ " AS s " + " ON " + " s."+FeedTable.Cols.USERNAME +" = " +" a."+FavoriteTable.Cols.FAVORITEUSERNAME+
                " WHERE " +" a."+FavoriteTable.Cols.USERNAME +
                " =? " +" ORDER BY "+ FeedTable.Cols.DATEPOSTED + " DESC";

        Cursor cursor = mDatabase.rawQuery(MY_QUERY, usernames);

        NetworkCursorWrapper wrapper = new NetworkCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            while(wrapper.isAfterLast() == false) {
                Feed post = wrapper.getFeed();
                posts.add(post);
                wrapper.moveToNext();
            }
        }
        finally {
            cursor.close();
            wrapper.close();
        }

        return posts;

    }

    //Query Database for all comments related to a particular post/feed
    public List<Comment> getComment(String[] feedID ) {

        List<Comment> comments = new ArrayList<>();


        String MY_QUERY = "SELECT " +" a.feedid AS postID "+", "+ " a."+FeedTable.Cols.NUMBEROFCOMMENTS+ " AS numComments "+", "
                +" i."+ProfileTable.Cols.USERNAME+ " AS profUsername " +", "+ " i."+ProfileTable.Cols.PROFILEPICLOCATION+ " AS profPicLoc " + " , " +" i."+ProfileTable.Cols.FULLNAME+ " AS profFullName "+
                " , "+ " s."+CommentTable.Cols.COMMENT+" AS postedComment " +" , "+" s."+CommentTable.Cols.DATE+" AS date "+" , "+" s."+CommentTable.Cols.COMMENTERUSERNAME+" AS commenterUname " +
                " FROM " +ProfileTable.NAME+ " AS i "   + " INNER JOIN " + FeedTable.NAME+ " AS a " + " ON " + " i."+ProfileTable.Cols.USERNAME +
                " = " +" s."+CommentTable.Cols.COMMENTERUSERNAME+ " INNER JOIN "+ CommentTable.NAME+ " AS s " + " ON " + " s."+CommentTable.Cols.FEEDID +" = " +" a.feedid"+
                " WHERE " +" a.feedid = ? " +" ORDER BY "+ CommentTable.Cols.DATE + " ASC";

        Cursor cursor = mDatabase.rawQuery(MY_QUERY, feedID);

        NetworkCursorWrapper wrapper = new NetworkCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            while(wrapper.isAfterLast() == false) {
                Comment comment = wrapper.getComment();
                comments.add(comment);
                wrapper.moveToNext();
            }
        }
        finally {
            cursor.close();
            wrapper.close();
        }

        return comments;

    }

    //Update Post
    public void updateFeed(Feed feed)
    {
        String MY_QUERY = "UPDATE "+FeedTable.NAME +" SET " + FeedTable.Cols.NUMBEROFLIKES+ " = ? "+ " WHERE feedid = ?";

        String[] uname = new String[2];
        uname[0] = Integer.toString(feed.getNumberOfLikes());
        uname[1] = feed.getFeedID();

        mDatabase.execSQL(MY_QUERY, uname);

    }

    //Update Post
    public void updateFeed2(Feed feed)
    {
        String MY_QUERY = "UPDATE "+FeedTable.NAME +" SET " + FeedTable.Cols.NUMBEROFCOMMENTS+ " = ? "+ " WHERE feedid = ?";

        String[] uname = new String[2];
        uname[0] = Integer.toString(feed.getNumberOfComments());
        uname[1] = feed.getFeedID();

        mDatabase.execSQL(MY_QUERY, uname);

    }


    //Query Database for list of favorites
    public String[] getListOfFavorite(String[] username){

        int index = 0;
        String[] listOfFavorite;

        Cursor cursor = mDatabase.query(
                FavoriteTable.NAME, // table name
                null,           // which columns; null for all
                FavoriteTable.Cols.USERNAME + "=?",          // where clause, e.g. id=?
                username,       // where arguments
                null,       // group by
                null,       // having
                null        // order by
        );

        NetworkCursorWrapper wrapper = new NetworkCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            //Initialize array
            listOfFavorite = new String[wrapper.getCount()];
            while(wrapper.isAfterLast() == false) {
                Favorite fav = wrapper.getFavorite();
                listOfFavorite[index] = fav.getFavoriteUsername();
                wrapper.moveToNext();
                index++;
            }
        }
        finally {
            wrapper.close();
        }

        return listOfFavorite;

    }

    //Query Database for list of liked feed
    public String[] getLikedFeeds(String[] username){

        int index = 0;
        String[] listOfLikedFeed;

        Cursor cursor = mDatabase.query(
                LikeTable.NAME, // table name
                null,           // which columns; null for all
                LikeTable.Cols.LIKERUSERNAME + "=?",          // where clause, e.g. id=?
                username,       // where arguments
                null,       // group by
                null,       // having
                null        // order by
        );

        NetworkCursorWrapper wrapper = new NetworkCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            //Initialize array
            listOfLikedFeed = new String[wrapper.getCount()];
            while(wrapper.isAfterLast() == false) {
                Like like = wrapper.getLike();
                listOfLikedFeed[index] = like.getFeedID();
                wrapper.moveToNext();
                index++;
            }
        }
        finally {
            wrapper.close();
        }

        return listOfLikedFeed;

    }

    //Create an profle Contentvalues
    private static ContentValues getProfileContentvalues(Profile profile) {

        ContentValues values = new ContentValues();
        values.put(ProfileTable.Cols.USERNAME, profile.getUsername());
        values.put(ProfileTable.Cols.FULLNAME, profile.getFullName());
        values.put(ProfileTable.Cols.PROFILEPICLOCATION, profile.getProfilePicLocation());
        values.put(ProfileTable.Cols.HOMETOWN, profile.getHomeTown());
        values.put(ProfileTable.Cols.DATEOFBIRTH, profile.getDateofBith());
        values.put(ProfileTable.Cols.BIOGRAPHY, profile.getBiography());
        values.put(ProfileTable.Cols.DATEJOINED, profile.getDateJoined());
        values.put(ProfileTable.Cols.LASTACTIVITY, profile.getLastActivity());


        return values;
    }

    //Create an account Contentvalues
    private static ContentValues getAccountContentvalues(Account account) {

        ContentValues values = new ContentValues();
        values.put(AccountTable.Cols.USERNAME, account.getUsername());
        values.put(AccountTable.Cols.PASSWORD, account.getPassword());
        values.put(AccountTable.Cols.SECURITYQUESTION, account.getSecurityQuestion());

        return values;
    }


    //Create a "Like" Contentvalues
    private static ContentValues getLikeContentvalues(Like like) {

        ContentValues values = new ContentValues();
        values.put(LikeTable.Cols.FEEDID, like.getFeedID());
        values.put(LikeTable.Cols.LIKERUSERNAME, like.getLikerUsername());

        return values;
    }

    //Create a "comment" Contentvalues
    private static ContentValues getCommentContentvalues(Comment comment) {

        ContentValues values = new ContentValues();
        values.put(CommentTable.Cols.FEEDID, comment.getFeedID());
        values.put(CommentTable.Cols.COMMENTERUSERNAME, comment.getCommenterUsername());
        values.put(CommentTable.Cols.COMMENT, comment.getComment());

        return values;
    }

    //create Favorite Contentvalues
    private static ContentValues getFavoriteContentvalues(Favorite favorite)
    {
        ContentValues values = new ContentValues();
        values.put(FavoriteTable.Cols.USERNAME, favorite.getUsername());
        values.put(FavoriteTable.Cols.FAVORITEUSERNAME, favorite.getFavoriteUsername());

        return values;
    }


    //create Feed Contentvalues
    private static ContentValues getFeedContentvalues(Feed feed)
    {
        ContentValues values = new ContentValues();
        values.put(FeedTable.Cols.USERNAME, feed.getUsername());
        values.put(FeedTable.Cols.POST, feed.getPost());
        values.put(FeedTable.Cols.NUMBEROFLIKES, feed.getNumberOfLikes());
        values.put(FeedTable.Cols.NUMBEROFCOMMENTS, feed.getNumberOfComments());
        values.put(FeedTable.Cols.POSTIMAGE, feed.getPostImageLocation());
        values.put(FeedTable.Cols.DATEPOSTED, feed.getDatePosted());

        return values;
    }

}
