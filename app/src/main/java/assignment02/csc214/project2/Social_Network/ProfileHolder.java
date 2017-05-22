package assignment02.csc214.project2.Social_Network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import assignment02.csc214.project2.Database.ModelQueryManager;
import assignment02.csc214.project2.Models.Account;
import assignment02.csc214.project2.Models.Favorite;
import assignment02.csc214.project2.Models.Profile;
import assignment02.csc214.project2.PictureManager.PictureManager;
import assignment02.csc214.project2.R;

/**
 * Created by Kennedy on 4/5/2017.
 */

public class ProfileHolder extends RecyclerView.ViewHolder  {

    private TextView mNameTextView;
    private ImageView mProfileImageView;
    private TextView mDateJoinedTextView;
    private TextView mLastSeenTextView;
    private ImageView mFavoriteImageView;
    private Profile mProfile;
    private String mUsername;
    private Context mContext;
    private LinearLayout mLinearLayout1;
    private LinearLayout mLinearLayout2;


    int imgCounter = 0;

    public ProfileHolder(View itemView) {
        super(itemView);

        mNameTextView = (TextView)itemView.findViewById(R.id.users_name_display);
        mProfileImageView = (ImageView)itemView.findViewById(R.id.users_pix_displayImageView);
        mDateJoinedTextView = (TextView)itemView.findViewById(R.id.users_DateJoined_display2);
        mLastSeenTextView = (TextView)itemView.findViewById(R.id.users_LastSeen_display2);
        mFavoriteImageView = (ImageView)itemView.findViewById(R.id.users_FavoriteStarImage);
        mLinearLayout1 = (LinearLayout)itemView.findViewById(R.id.users_layout1);
        mLinearLayout2 = (LinearLayout)itemView.findViewById(R.id.users_layout2);

        mNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewUser(v);

            }
        });

        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewUser(v);
            }
        });

        mLinearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewUser(v);

            }
        });

        mLinearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewUser(v);
            }
        });

        mFavoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imgCounter ^= 1;
                setFavoriteImg2();
            }
        });

    }

    public void bind(Profile profile) {
        mProfile = profile;
        mNameTextView.setText(profile.getFullName());
        displayPic();
        mDateJoinedTextView.setText(profile.getDateJoined());
        mLastSeenTextView.setText(profile.getLastActivity());
        setFavoriteImg();

    }

    public void displayPic()
    {

        String mImageUrl = mProfile.getProfilePicLocation();
        if(!mImageUrl.isEmpty())
        {
            Bitmap bitmap = getScaledBitmap(mImageUrl,
                    mProfileImageView.getWidth(), mProfileImageView.getMaxHeight());
            mProfileImageView.setImageBitmap(bitmap);
        }

    }

    public void setFavoriteImg()
    {
        if(mProfile.getIsFavorite())
        {
            imgCounter = 0;
            mFavoriteImageView.setImageResource(R.mipmap.favorite_yellow);
        }

        else
        {
            imgCounter = 1;
            mFavoriteImageView.setImageResource(R.mipmap.favorite_white);
        }

    }

    public void setFavoriteImg2()
    {
        if(imgCounter == 0)
        {
            ModelQueryManager.get(mContext.getApplicationContext()).removeFavorite(mUsername, mProfile.getUsername());
            if(mProfile.getIsFavorite())
                mFavoriteImageView.setImageResource(R.mipmap.favorite_white);
            imgCounter = 1;
            mProfile.setmIsFavorite(false);
        }

        else
        {
            Favorite fav = new Favorite(mUsername, mProfile.getUsername());
            ModelQueryManager.get(mContext.getApplicationContext()).createFavorite(fav);
            if(mProfile.getIsFavorite() == false)
                mFavoriteImageView.setImageResource(R.mipmap.favorite_yellow);
            imgCounter = 0;
            mProfile.setmIsFavorite(true);
        }


    }

    public Bitmap getScaledBitmap(String path, int width, int height) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int sampleSize = 1;
        if(srcHeight > height || srcWidth > width ) {
            if(srcWidth > srcHeight) {
                sampleSize = Math.round(srcHeight / height);
            }
            else {
                sampleSize = Math.round(srcWidth / width);
            }
        }
        BitmapFactory.Options scaledOptions =
                new BitmapFactory.Options();
        scaledOptions.inSampleSize = sampleSize;
        return BitmapFactory.decodeFile(path, scaledOptions);
    }

    public void setUserAccount(String username, Context context)
    {
        mUsername = username;
        mContext = context;
    }

    public void viewUser(View v)
    {
        FragmentActivity c = (FragmentActivity)v.getContext();
        FragmentManager manager = c.getSupportFragmentManager();
        UserDisplayDialog dialog = UserDisplayDialog.newInstance(mProfile);
        dialog.show(manager, "");
    }

}
