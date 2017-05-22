package assignment02.csc214.project2.Social_Network;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import assignment02.csc214.project2.Account.LoginFragment;
import assignment02.csc214.project2.Database.ModelQueryManager;
import assignment02.csc214.project2.Models.Account;
import assignment02.csc214.project2.Models.Profile;
import assignment02.csc214.project2.PictureManager.PictureManager;
import assignment02.csc214.project2.Profile.UpdateProfileFragment;
import assignment02.csc214.project2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListOfUsersFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private Profile mProfile;

    public ListOfUsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_of_users, container, false);

        mProfile = (Profile)getArguments().getSerializable(UpdateProfileFragment.PROFILE_KEY);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.ListOfUsersRecycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Profile> profileList = ModelQueryManager.get(getContext().getApplicationContext()).getListOfProfile();

        String[] username = new String[1];
        username[0] = mProfile.getUsername();
        String[] favoriteList = ModelQueryManager.get(getContext().getApplicationContext()).getListOfFavorite(username);

        mRecyclerView.setAdapter(new ListOfUserRecyclerAdapter(profileList, favoriteList, mProfile.getUsername()));

        return view;
    }



}
