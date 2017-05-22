package assignment02.csc214.project2.Social_Network;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import assignment02.csc214.project2.Models.Profile;
import assignment02.csc214.project2.PictureManager.PictureManager;
import assignment02.csc214.project2.R;

/**
 * Created by Kennedy on 4/5/2017.
 */

public class UserDisplayDialog extends DialogFragment {

    private static final String KEY_FULLNAME = "NAME";
    private static final String KEY_PIC = "PROFILEPIC";
    private static final String KEY_DOB = "DATEOFBIRTH";
    private static final String KEY_HOMETOWN = "HOMETOWN";
    private static final String KEY_BIO = "BIOGRAPHY";

    private TextView title;
    private ImageView profileImg;
    private TextView name;
    private TextView dob;
    private TextView homeTown;
    private TextView bio;

    private PictureManager mPictureManager;
    Bundle args;

    public UserDisplayDialog()
    {

    }

    public static UserDisplayDialog newInstance(Profile profile)
    {
        Bundle args = new Bundle();
        args.putString(KEY_FULLNAME, profile.getFullName());
        args.putString(KEY_PIC, profile.getProfilePicLocation());
        args.putString(KEY_DOB, profile.getDateofBith());
        args.putString(KEY_HOMETOWN, profile.getHomeTown());
        args.putString(KEY_BIO, profile.getBiography());

        UserDisplayDialog dialog = new UserDisplayDialog();
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.user_display_dialog, null);
        mPictureManager = new PictureManager(getActivity());
        args = getArguments();

        title = (TextView) view.findViewById(R.id.ViewUserProfile_TitletextView);
        profileImg = (ImageView) view.findViewById(R.id.ViewUserProfile_ProfilePixImageView);
        name = (TextView) view.findViewById(R.id.ViewUserProfile_FullName2);
        dob = (TextView) view.findViewById(R.id.ViewUserProfile_DateOfBirthEditText);
        homeTown = (TextView) view.findViewById(R.id.ViewUserProfile_HomeTownEditText);
        bio = (TextView) view.findViewById(R.id.ViewUserProfile_BioEditText);

        if (args != null) {
            title.setText(args.getString(KEY_FULLNAME));
            displayPic();
            name.setText(args.getString(KEY_FULLNAME));
            dob.setText(args.getString(KEY_DOB));
            homeTown.setText(args.getString(KEY_HOMETOWN));
            bio.setText(args.getString(KEY_BIO));

        }

        return new AlertDialog.Builder(getContext())
                //.setTitle(getString(R.string.title_album_dialog))
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }

    public void displayPic()
    {
        String mImageUrl = args.get(KEY_PIC).toString();
        if(!mImageUrl.isEmpty())
        {
            Bitmap bitmap = mPictureManager.getScaledBitmap(mImageUrl,
                    profileImg.getWidth(), profileImg.getMaxHeight());
            profileImg.setImageBitmap(bitmap);
        }

    }




}
