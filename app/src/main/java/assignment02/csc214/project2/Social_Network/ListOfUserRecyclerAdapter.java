package assignment02.csc214.project2.Social_Network;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import assignment02.csc214.project2.Models.Profile;
import assignment02.csc214.project2.R;

/**
 * Created by Kennedy on 4/5/2017.
 */

public class ListOfUserRecyclerAdapter extends RecyclerView.Adapter<ProfileHolder>  {

    private List<Profile> mProfiles;
    private String[] mFavorites;
    private Context mContext;
    private String mUsername;

    public ListOfUserRecyclerAdapter(List<Profile> profiles, String[] favorites, String username) {
        mProfiles = profiles;
        mFavorites = favorites;
        mUsername = username;
    }

    @Override
    public ProfileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.users_display_layout, parent, false);
        mContext = parent.getContext();

        ProfileHolder holder = new ProfileHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProfileHolder holder, int position) {

        Profile profile = mProfiles.get(position);
        String faveUsername = profile.getUsername();
        //Check whether the user marked this profile as favorite
        profile.setmIsFavorite(searchFavorite(faveUsername));
        holder.setUserAccount(mUsername, mContext);
        //Bind profile to view
        holder.bind(profile);
    }

    @Override
    public int getItemCount() {
        return mProfiles.size();
    }

    public boolean searchFavorite(String favoriteUsername)
    {
        for(int i = 0; i< mFavorites.length; i++)
        {
            if(mFavorites[i].equals(favoriteUsername))
                return true;
        }

        return false;
    }
}
