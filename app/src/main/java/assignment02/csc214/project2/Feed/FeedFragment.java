package assignment02.csc214.project2.Feed;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import assignment02.csc214.project2.Account.LoginFragment;
import assignment02.csc214.project2.Database.ModelQueryManager;
import assignment02.csc214.project2.Models.Feed;
import assignment02.csc214.project2.Models.Profile;
import assignment02.csc214.project2.PictureManager.PictureManager;
import assignment02.csc214.project2.Profile.UpdateProfileFragment;
import assignment02.csc214.project2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {

    public interface FeedFragUpdate{
        public void commentDone();
    }

    private FeedFragUpdate mFeedFragUpdate;
    private RecyclerView mRecyclerView;
    private ImageView mProfilePicImageView;
    private Profile profile;
    private PictureManager mPictureManager;
    private TextView mPostTextView;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mFeedFragUpdate = (FeedFragUpdate)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mFeedFragUpdate = (FeedFragUpdate)activity;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        profile = (Profile)getArguments().getSerializable(UpdateProfileFragment.PROFILE_KEY);
        mProfilePicImageView = (ImageView)view.findViewById(R.id.feed_fragment_profilePic);
        mPostTextView = (TextView)view.findViewById(R.id.feed_fragment_WriteSomethingEditText);
        mPictureManager = new PictureManager(getActivity());
        displayPic();

        mRecyclerView = (RecyclerView)view.findViewById(R.id.FeedRecycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String[] username = new String[1];
        username[0] = profile.getUsername();

        List<Feed> feedList = ModelQueryManager.get(getContext().getApplicationContext()).getFeed(username);
        String[] likedList = ModelQueryManager.get(getContext().getApplicationContext()).getLikedFeeds(username);

        mRecyclerView.setAdapter(new FeedRecyclerAdapter(feedList, likedList, profile.getUsername(), this));


        mPostTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), PostActivity.class);
                intent.putExtra(LoginFragment.ACCOUNTKEY, profile);
                getActivity().startActivityForResult(intent, FeedActivity.FEED_POST_KEY);
            }
        });

        return view;
    }

    public void displayPic()
    {
        String mImageUrl = profile.getProfilePicLocation();
        if(!mImageUrl.isEmpty())
        {
            Bitmap bitmap = mPictureManager.getScaledBitmap(mImageUrl,
                    mProfilePicImageView.getWidth(), mProfilePicImageView.getMaxHeight());
            mProfilePicImageView.setImageBitmap(bitmap);
        }

    }

    public String[] appendFav(String[] fav1, String[] fav2)
    {
        int index = 1;
        for(int i = 0; i<fav1.length; i++){
            fav2[index] = fav1[i];
            index++;
        }
        return fav2;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        mFeedFragUpdate.commentDone();
    }

}
