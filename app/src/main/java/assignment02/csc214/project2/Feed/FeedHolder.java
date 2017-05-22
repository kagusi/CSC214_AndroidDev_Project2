package assignment02.csc214.project2.Feed;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import assignment02.csc214.project2.Comment.CommentDialog;
import assignment02.csc214.project2.Database.ModelQueryManager;
import assignment02.csc214.project2.Models.Feed;
import assignment02.csc214.project2.Models.Like;
import assignment02.csc214.project2.R;

/**
 * Created by Kennedy on 4/7/2017.
 */

public class FeedHolder extends RecyclerView.ViewHolder  {

    private Feed mFeed;
    private String mUsername;
    private Context mContext;
    private ImageView mProfileImageView;
    private TextView mFullNameTextView;
    private TextView mDatePostedTextView;
    private TextView mPostTextView;
    private ImageView mPostedImageView;
    private TextView mNumLikeTextView;
    private TextView mNumCommentTextView;
    private ImageView mLikeImageview;
    private TextView mLikeTextView;
    private ImageView mCommentImageView;
    private TextView mCommentTextView;

    private Fragment mFragment;

    int imgCounter = 0;

    public FeedHolder(View itemView) {
        super(itemView);

        mProfileImageView = (ImageView)itemView.findViewById(R.id.feed_posterImageImageView);
        mFullNameTextView = (TextView)itemView.findViewById(R.id.feed_poster_name_TextView);
        mDatePostedTextView = (TextView)itemView.findViewById(R.id.feed_post_time_TextView);
        mPostTextView = (TextView)itemView.findViewById(R.id.feed_postTextView);
        mPostedImageView = (ImageView)itemView.findViewById(R.id.feed_postedImage_ImageView);
        mNumLikeTextView = (TextView)itemView.findViewById(R.id.feed_numberOfLikesTextView);
        mNumCommentTextView = (TextView)itemView.findViewById(R.id.feed_numberOfCommentsTextView);
        mLikeImageview = (ImageView)itemView.findViewById(R.id.feed_LikeIconImageView);
        mLikeTextView = (TextView)itemView.findViewById(R.id.feed_LikeTextView);
        mCommentImageView = (ImageView)itemView.findViewById(R.id.feed_CommentIconImageView);
        mCommentTextView = (TextView)itemView.findViewById(R.id.feed_commentTextView);

        mLikeImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClickLikeImg();
            }
        });
        mLikeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClickLikeImg();
            }
        });
        mCommentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewComments(v);
            }
        });
        mCommentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewComments(v);
            }
        });
        mNumCommentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewComments(v);
            }
        });



    }


    public void bind(Feed feed) {
        mFeed = feed;
        displayPic();
        mFullNameTextView.setText(mFeed.getFullName());
        mDatePostedTextView.setText(mFeed.getDatePosted());
        mPostTextView.setText(mFeed.getPost());
        if(!mFeed.getPostImageLocation().equals("empty"))
        {
            String mImageUrl = mFeed.getPostImageLocation();
            Bitmap bitmap = getScaledBitmap(mImageUrl,
                    mPostedImageView.getWidth(), mPostedImageView.getMaxHeight());
            mPostedImageView.setImageBitmap(bitmap);
            mPostedImageView.setVisibility(View.VISIBLE);
        }
        mNumLikeTextView.setText(Integer.toString(mFeed.getNumberOfLikes()));
        String numComments = Integer.toString(mFeed.getNumberOfComments())+" Comments";
        mNumCommentTextView.setText(numComments);
        setLikeImg();

    }

    public void setLikeImg()
    {
        if(mFeed.isLiked())
        {
            imgCounter = 0;
            mLikeImageview.setImageResource(R.mipmap.icon_like);
        }

        else
        {
            imgCounter = 1;
            mLikeImageview.setImageResource(R.mipmap.like_black);
        }

    }

    public void setClickLikeImg()
    {
        if(imgCounter == 0)
        {
            ModelQueryManager.get(mContext.getApplicationContext()).removeLike(mFeed.getFeedID(), mUsername);
            if(mFeed.isLiked())
                mLikeImageview.setImageResource(R.mipmap.like_black);
            imgCounter = 1;
            int numLike1 = mFeed.getNumberOfLikes()-1;
            String numLike = Integer.toString(numLike1);
            mNumLikeTextView.setText(numLike);
            mFeed.setNumberOfLikes(numLike1);
            mFeed.setLiked(false);
            ModelQueryManager.get(mContext.getApplicationContext()).updateFeed(mFeed);
        }

        else
        {
            Like like = new Like(mFeed.getFeedID(), mUsername);
            ModelQueryManager.get(mContext.getApplicationContext()).createLike(like);
            if(mFeed.isLiked() == false)
                mLikeImageview.setImageResource(R.mipmap.icon_like);
            imgCounter = 0;
            int numLike1 = mFeed.getNumberOfLikes()+1;
            String numLike = Integer.toString(numLike1);
            mNumLikeTextView.setText(numLike);
            mFeed.setNumberOfLikes(numLike1);
            mFeed.setLiked(true);
            ModelQueryManager.get(mContext.getApplicationContext()).updateFeed(mFeed);
        }

    }

    public void displayPic()
    {

        String mImageUrl = mFeed.getProfilePicLocation();
        if(!mImageUrl.isEmpty())
        {
            Bitmap bitmap = getScaledBitmap(mImageUrl,
                    mProfileImageView.getWidth(), mProfileImageView.getMaxHeight());
            mProfileImageView.setImageBitmap(bitmap);
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

    public void setUserAccount(String username, Context context, Fragment fragment)
    {
        mUsername = username;
        mContext = context;
        mFragment = fragment;
    }

    public void viewComments(View v)
    {
        FragmentActivity c = (FragmentActivity)v.getContext();
        FragmentManager manager = c.getSupportFragmentManager();
        CommentDialog dialog = CommentDialog.newInstance(mUsername, mFeed.getFeedID(), mFeed.getNumberOfComments());
        dialog.setTargetFragment(mFragment, 3);
        dialog.show(manager, "");
    }



}
