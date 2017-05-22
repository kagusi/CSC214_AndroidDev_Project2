package assignment02.csc214.project2.Profile;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import assignment02.csc214.project2.Account.AccountActivity;
import assignment02.csc214.project2.Account.LoginFragment;
import assignment02.csc214.project2.Database.ModelQueryManager;
import assignment02.csc214.project2.Feed.FeedActivity;
import assignment02.csc214.project2.Models.Account;
import assignment02.csc214.project2.Models.Favorite;
import assignment02.csc214.project2.Models.Profile;
import assignment02.csc214.project2.PictureManager.PictureManager;
import assignment02.csc214.project2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateProfileFragment extends Fragment {

    public interface CreateProfileUpdate{
        public void createProfileDone(Account account);
    }

    private CreateProfileUpdate mCreateProfileUpdate;
    private String mImageUrl;
    private TextView mTitleTextView;
    private ImageView mProfileImageView;
    private EditText mFullnameEdiText;
    private EditText mDateofBirthEdiText;
    private EditText mHomeTownEdiText;
    private EditText mBiographyEdiText;
    private Button mSubmitButton;
    private Button mChangePicButton;
    private Profile profile;
    private Account account;
    private PictureManager mPictureManager;

    private static final String TAG = "CreateProfileFrag";
    public static final String PROFILE_KEY = "profilekey";

    public CreateProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mCreateProfileUpdate = (CreateProfileUpdate)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mCreateProfileUpdate = (CreateProfileUpdate)activity;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Oncreate() called");
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_update_profile, container, false);

        account = (Account)getArguments().getSerializable(LoginFragment.ACCOUNTKEY);
        mPictureManager = new PictureManager(getActivity());


        mTitleTextView = (TextView)view.findViewById(R.id.UpdateProfile_TitletextView);
        mProfileImageView = (ImageView)view.findViewById(R.id.UpdateProfile_ProfilePixImageView);
        mChangePicButton = (Button)view.findViewById(R.id.UpdateProfile_UploadImageButton);
        mFullnameEdiText = (EditText)view.findViewById(R.id.UpdateProfile_FullNameEditText);
        mDateofBirthEdiText = (EditText)view.findViewById(R.id.UpdateProfile_DateOfBirthEditText);
        mHomeTownEdiText = (EditText)view.findViewById(R.id.UpdateProfile_HomeTownEditText);
        mBiographyEdiText = (EditText)view.findViewById(R.id.UpdateProfile_BioEditText);
        mSubmitButton = (Button) view.findViewById(R.id.UpdateProfile_UpdateButton);

        mTitleTextView.setText(R.string.Create_Profile_Title);

        String[] uname = new String[1];
        uname[0] = account.getUsername();
        profile = ModelQueryManager.get(getContext().getApplicationContext()).getProfile(uname);

        mImageUrl = profile.getProfilePicLocation();

        if(!mImageUrl.isEmpty())
        {
            Bitmap bitmap = mPictureManager.getScaledBitmap(mImageUrl,
                    mProfileImageView.getWidth(), mProfileImageView.getMaxHeight());
            mProfileImageView.setImageBitmap(bitmap);
        }

        mFullnameEdiText.setText(profile.getFullName());
        mDateofBirthEdiText.setText(profile.getDateofBith());
        mHomeTownEdiText.setText(profile.getHomeTown());
        mBiographyEdiText.setText(profile.getBiography());

        mChangePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPictureManager.takeAPhoto(profile.getUsername());
                mImageUrl = mPictureManager.getFilePath();

            }
        });


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullName = mFullnameEdiText.getText().toString();
                String dateOfBirth = mDateofBirthEdiText.getText().toString();
                String homeTown = mHomeTownEdiText.getText().toString();
                String bio = mBiographyEdiText.getText().toString();

                profile.setProfilePicLocation(mImageUrl);
                profile.setFullName(fullName);
                profile.setDateofBith(dateOfBirth);
                profile.setHomeTown(homeTown);
                profile.setBiography(bio);

                //Log.d(TAG, "ImageURL2: "+mImageUrl);

                ModelQueryManager.get(getContext().getApplicationContext()).updateProfile(profile);
                //Add the users as its own favorite so that the user can see his own posts
                Favorite fav = new Favorite(profile.getUsername(), profile.getUsername());
                ModelQueryManager.get(getContext().getApplicationContext()).createFavorite(fav);

                Toast.makeText(getActivity(), "PROFILE SUCCESSFULY CREATED", Toast.LENGTH_LONG).show();

                mCreateProfileUpdate.createProfileDone(account);


            }
        });


        return view;
    }

    public void changeImage()
    {
        Bitmap bitmap = mPictureManager.getScaledBitmap(mImageUrl,
                mProfileImageView.getWidth(), mProfileImageView.getMaxHeight());
        mProfileImageView.setImageBitmap(bitmap);
    }

}
