package assignment02.csc214.project2.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import assignment02.csc214.project2.Database.NetworkDBSchema.*;

/**
 * Created by Kennedy on 3/30/2017.
 */

public class NetworkDBHelper extends SQLiteOpenHelper {

    public NetworkDBHelper(Context context) {
        super(context, NetworkDBSchema.DATABASE_NAME, null, NetworkDBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + AccountTable.NAME
                + "(accountid integer primary key autoincrement, "
                + AccountTable.Cols.USERNAME + ", "
                + AccountTable.Cols.PASSWORD + ", "
                + AccountTable.Cols.SECURITYQUESTION + ")");

        db.execSQL("create table " + ProfileTable.NAME
                + "(profileid integer primary key autoincrement, "
                + ProfileTable.Cols.USERNAME+ ", "
                + ProfileTable.Cols.FULLNAME+ ", "
                + ProfileTable.Cols.PROFILEPICLOCATION+ ", "
                + ProfileTable.Cols.HOMETOWN+ ", "
                + ProfileTable.Cols.DATEOFBIRTH+ ", "
                + ProfileTable.Cols.BIOGRAPHY+ ", "
                + ProfileTable.Cols.DATEJOINED+ ", "
                + ProfileTable.Cols.LASTACTIVITY+ ")");

        db.execSQL("create table " + FavoriteTable.NAME
                + "(favoriteid integer primary key autoincrement, "
                + FavoriteTable.Cols.USERNAME + ", "
                + FavoriteTable.Cols.FAVORITEUSERNAME + ")");

        db.execSQL("create table " + FeedTable.NAME
                + "(feedid integer primary key autoincrement, "
                + FeedTable.Cols.USERNAME+ ", "
                + FeedTable.Cols.POST+ ", "
                + FeedTable.Cols.NUMBEROFLIKES+ ", "
                + FeedTable.Cols.NUMBEROFCOMMENTS+ ", "
                + FeedTable.Cols.POSTIMAGE+ ", "
                + FeedTable.Cols.DATEPOSTED+ ")");

        db.execSQL("create table " + CommentTable.NAME
                + "(commentid integer primary key autoincrement, "
                + CommentTable.Cols.FEEDID+ ", "
                + CommentTable.Cols.COMMENTERUSERNAME+ ", "
                + CommentTable.Cols.COMMENT+ ", "
                + CommentTable.Cols.DATE+ ")");

        db.execSQL("create table " + LikeTable.NAME
                + "(likeid integer primary key autoincrement, "
                + LikeTable.Cols.FEEDID+ ", "
                + LikeTable.Cols.LIKERUSERNAME+ ")");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
