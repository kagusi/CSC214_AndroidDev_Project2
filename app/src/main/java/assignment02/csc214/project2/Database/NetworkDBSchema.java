package assignment02.csc214.project2.Database;

/**
 * Created by Kennedy on 3/30/2017.
 */

public class NetworkDBSchema {

    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "project2_database.db";

    public static final class AccountTable {
        public static final String NAME = "account";
        public static final class Cols {
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
            public static final String SECURITYQUESTION = "securityquestion";
        }

    }

    public static final class ProfileTable {
        public static final String NAME = "profile";
        public static final class Cols {
            public static final String USERNAME = "username";
            public static final String FULLNAME = "fullname";
            public static final String PROFILEPICLOCATION = "profilepiclocation";
            public static final String HOMETOWN = "hometown";
            public static final String DATEOFBIRTH = "dateofbirth";
            public static final String BIOGRAPHY = "biography";
            public static final String DATEJOINED = "datejoined";
            public static final String LASTACTIVITY = "lastactivity";
        }

    }


    public static final class FavoriteTable {
        public static final String NAME = "favorite";
        public static final class Cols {
            public static final String USERNAME = "username";
            public static final String FAVORITEUSERNAME = "favoriteusername";
        }

    }

    public static final class FeedTable {
        public static final String NAME = "feed";
        public static final class Cols {
            public static final String USERNAME = "username";
            public static final String POST = "post";
            public static final String NUMBEROFLIKES = "numberoflikes";
            public static final String NUMBEROFCOMMENTS = "numberofcomments";
            public static final String POSTIMAGE = "postimage";
            public static final String DATEPOSTED = "dateposted";
        }

    }

    public static final class CommentTable {
        public static final String NAME = "comment";
        public static final class Cols {
            public static final String FEEDID = "feeid";
            public static final String COMMENTERUSERNAME = "commenterusername";
            public static final String COMMENT = "comment";
            public static final String DATE = "date";
        }

    }

    public static final class LikeTable {
        public static final String NAME = "like";
        public static final class Cols {
            public static final String FEEDID = "feeid";
            public static final String LIKERUSERNAME = "likerusername";
        }

    }


}
