package assignment02.csc214.project2.Feed;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import assignment02.csc214.project2.Account.AccountActivity;
import assignment02.csc214.project2.Account.LoginFragment;
import assignment02.csc214.project2.Database.ModelQueryManager;
import assignment02.csc214.project2.Models.Feed;
import assignment02.csc214.project2.Models.Profile;
import assignment02.csc214.project2.PictureManager.PictureManager;
import assignment02.csc214.project2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {

    public interface PostFeedDone{
        public void postFeedDone();
    }

    private PostFeedDone mPostFeedDone;
    private Profile mProfile;
    private ImageView mProfilePixImageView;
    private EditText mPostEdiText;
    private ImageView mPostImageView;
    private Button mAddPicButton;
    private Button mPostButton;
    private PictureManager mPictureManager;
    private String mImageUrl;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mPostFeedDone = (PostFeedDone)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mPostFeedDone = (PostFeedDone) activity;

    }



        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        mProfile = (Profile)getArguments().getSerializable(LoginFragment.ACCOUNTKEY);
        mProfilePixImageView = (ImageView)view.findViewById(R.id.Post_UserImage);
        mPostEdiText = (EditText)view.findViewById(R.id.Post_EnterPostEdiTextView);
        mPostImageView = (ImageView)view.findViewById(R.id.Post_PostImageView);
        mAddPicButton = (Button)view.findViewById(R.id.Post_AddPicButton);
        mPostButton = (Button)view.findViewById(R.id.Post_PostButton);
        mPictureManager = new PictureManager(getActivity());
        mImageUrl = "empty";

        displayPic(mProfilePixImageView);

        mAddPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPictureManager.takeAPhoto(mProfile.getUsername());
                mImageUrl = mPictureManager.getFilePath();

            }
        });

        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(mPostEdiText.getText().toString()) && mImageUrl.equals("empty"))
                {
                    Toast.makeText(getActivity(), "POST CAN'T BE EMPTY", Toast.LENGTH_LONG).show();
                }
                else
                {
                    String post = mPostEdiText.getText().toString();
                    Feed feed = new Feed(mProfile.getUsername(), post, mImageUrl);
                    ModelQueryManager.get(getContext().getApplicationContext()).createFeed(feed);

                    mPostFeedDone.postFeedDone();
                }

            }
        });


        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.update_profile_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled;
        switch(item.getItemId()) {
            case R.id.menu_logout:
                restartActivity(AccountActivity.class);
                handled = true;
                break;
            default:
                handled = super.onOptionsItemSelected(item);
                break;
        }

        return handled;
    }

    private void restartActivity(Class activityClass) {
        Intent intent = new Intent(getActivity(), activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void displayPic(ImageView view)
    {
        String mImageUrl = mProfile.getProfilePicLocation();
        if(!mImageUrl.isEmpty())
        {
            Bitmap bitmap = mPictureManager.getScaledBitmap(mImageUrl,
                    view.getWidth(), view.getMaxHeight());
            view.setImageBitmap(bitmap);
        }

    }

    public void displayPostPic()
    {
        if(!mImageUrl.equals("") || mImageUrl != null)
        {
            Bitmap bitmap = mPictureManager.getScaledBitmap(mImageUrl,
                    mPostImageView.getWidth(), mPostImageView.getMaxHeight());
            mPostImageView.setImageBitmap(bitmap);
            mPostImageView.setVisibility(View.VISIBLE);
        }

    }

    public void removePostPic()
    {
        mPostImageView.setImageResource(android.R.color.transparent);
        mPostImageView.setVisibility(View.GONE);
        mImageUrl = "empty";
    }

}
