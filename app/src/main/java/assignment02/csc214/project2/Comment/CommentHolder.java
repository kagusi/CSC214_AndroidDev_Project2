package assignment02.csc214.project2.Comment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import assignment02.csc214.project2.Models.Comment;
import assignment02.csc214.project2.R;

/**
 * Created by Kennedy on 4/8/2017.
 */

public class CommentHolder extends RecyclerView.ViewHolder   {

    private ImageView mProfileImage;
    private TextView mFullNameTextView;
    private TextView mCommentTextView;
    private TextView mDateTextView;

    private Comment mComment;

    public CommentHolder(View itemView) {
        super(itemView);

        mProfileImage = (ImageView)itemView.findViewById(R.id.Comment_PosterImage);
        mFullNameTextView = (TextView)itemView.findViewById(R.id.Comment_PosterName);
        mCommentTextView = (TextView)itemView.findViewById(R.id.Comment_DisplayCommentTextView);
        mDateTextView = (TextView)itemView.findViewById(R.id.Comment_DateCommentedTextView);

    }

    public void bind(Comment comment) {
        mComment = comment;

        displayPic();
        mFullNameTextView.setText(comment.getFullName());
        mCommentTextView.setText(comment.getComment());
        mDateTextView.setText(comment.getDate());

    }

    public void displayPic()
    {

        String mImageUrl = mComment.getCommenterProfilePicLocation();
        if(!mImageUrl.isEmpty())
        {
            Bitmap bitmap = getScaledBitmap(mImageUrl,
                    mProfileImage.getWidth(), mProfileImage.getMaxHeight());
            mProfileImage.setImageBitmap(bitmap);
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
}
