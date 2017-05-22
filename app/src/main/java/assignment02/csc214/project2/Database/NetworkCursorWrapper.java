package assignment02.csc214.project2.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import assignment02.csc214.project2.Models.*;
import assignment02.csc214.project2.Database.NetworkDBSchema.*;

/**
 * Created by Kennedy on 3/30/2017.
 */

public class NetworkCursorWrapper extends CursorWrapper {

    Cursor cursor;
    public NetworkCursorWrapper(Cursor cursor) {
        super(cursor);
        this.cursor = cursor;
    }

    public Account getAccount() {
        String username =
                cursor.getString(cursor.getColumnIndex(AccountTable.Cols.USERNAME));
        String password =
                cursor.getString(cursor.getColumnIndex(AccountTable.Cols.PASSWORD));
        String securityquestion =
                cursor.getString(cursor.getColumnIndex(AccountTable.Cols.SECURITYQUESTION));

        Account account = new Account(username,password,securityquestion);

        return account;
    }

    public Profile getProfile() {
        String profileid =
                cursor.getString(cursor.getColumnIndex("profileid"));
        String username =
                cursor.getString(cursor.getColumnIndex(ProfileTable.Cols.USERNAME));
        String fullname =
                cursor.getString(cursor.getColumnIndex(ProfileTable.Cols.FULLNAME));
        String profilepiclocation =
                cursor.getString(cursor.getColumnIndex(ProfileTable.Cols.PROFILEPICLOCATION));
        String hometown =
                cursor.getString(cursor.getColumnIndex(ProfileTable.Cols.HOMETOWN));
        String dateofbirth =
                cursor.getString(cursor.getColumnIndex(ProfileTable.Cols.DATEOFBIRTH));
        String biography =
                cursor.getString(cursor.getColumnIndex(ProfileTable.Cols.BIOGRAPHY));
        String datejoined =
                cursor.getString(cursor.getColumnIndex(ProfileTable.Cols.DATEJOINED));
        String lastactivity =
                cursor.getString(cursor.getColumnIndex(ProfileTable.Cols.LASTACTIVITY));

        Profile profile = new Profile(username,fullname,profilepiclocation,hometown,dateofbirth,biography);
        profile.setDateJoined(datejoined);
        profile.setLastActivity(lastactivity);
        profile.setProfileID(profileid);

        return profile;
    }

    public Favorite getFavorite() {
        String favoriteid =
                cursor.getString(cursor.getColumnIndex("favoriteid"));
        String username =
                cursor.getString(cursor.getColumnIndex(FavoriteTable.Cols.USERNAME));
        String favoriteusername =
                cursor.getString(cursor.getColumnIndex(FavoriteTable.Cols.FAVORITEUSERNAME));

        Favorite favorite = new Favorite(username,favoriteusername);
        favorite.setFaoriteID(favoriteid);

        return favorite;
    }

    public Feed getFeed() {
        String feedid =
                cursor.getString(cursor.getColumnIndex("feedid"));
        String username =
                cursor.getString(cursor.getColumnIndex("feedUsername"));
        String post =
                cursor.getString(cursor.getColumnIndex("post"));
        String numberOfLikes =
                cursor.getString(cursor.getColumnIndex("numLikes"));
        String numberOfComments =
                cursor.getString(cursor.getColumnIndex("numComments"));
        String postImage =
                cursor.getString(cursor.getColumnIndex("postImage"));
        String date =
                cursor.getString(cursor.getColumnIndex("datePosted"));

        String profileImageLocation =
                cursor.getString(cursor.getColumnIndex("profPicLoc"));

        String fullName =
                cursor.getString(cursor.getColumnIndex("profFullName"));

        Feed feed = new Feed(username, post, postImage);
        feed.setFeedID(feedid);
        feed.setNumberOfLikes(Integer.parseInt(numberOfLikes));
        feed.setNumberOfComments(Integer.parseInt(numberOfComments));

        feed.setProfilePicLocation(profileImageLocation);
        feed.setFullName(fullName);

        feed.setDatePosted(date);

        return feed;
    }


    public Comment getComment() {


        String feedid =
                cursor.getString(cursor.getColumnIndex("postID"));
        String commenterUsername =
                cursor.getString(cursor.getColumnIndex("profUsername"));
        String fullName =
                cursor.getString(cursor.getColumnIndex("profFullName"));
        String comments =
                cursor.getString(cursor.getColumnIndex("postedComment"));
        String profPicLoc =
                cursor.getString(cursor.getColumnIndex("profPicLoc"));
        String date =
                cursor.getString(cursor.getColumnIndex("date"));
        String numberOfComments =
                cursor.getString(cursor.getColumnIndex("numComments"));

        /*
        String feedid =
                cursor.getString(cursor.getColumnIndex(CommentTable.Cols.FEEDID));
        String commenterUsername =
                cursor.getString(cursor.getColumnIndex(CommentTable.Cols.COMMENTERUSERNAME));
        String comments =
                cursor.getString(cursor.getColumnIndex(CommentTable.Cols.COMMENT));
        String date =
                cursor.getString(cursor.getColumnIndex(CommentTable.Cols.DATE));
        * */

        Comment comment = new Comment(feedid,commenterUsername,comments);
        comment.setDate(date);
        comment.setFullName(fullName);
        comment.setCommenterProfilePicLocation(profPicLoc);
        comment.setNumberOfComments(numberOfComments);

        return comment;

    }


    public Like getLike() {
        String liketid =
                cursor.getString(cursor.getColumnIndex("likeid"));
        String feedid =
                cursor.getString(cursor.getColumnIndex(LikeTable.Cols.FEEDID));
        String likerUsername =
                cursor.getString(cursor.getColumnIndex(LikeTable.Cols.LIKERUSERNAME));

        Like like = new Like(feedid,likerUsername);
        like.setLikeID(liketid);
        return like;
    }




}
