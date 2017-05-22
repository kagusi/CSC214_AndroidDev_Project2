package assignment02.csc214.project2.Feed;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import assignment02.csc214.project2.Models.Feed;
import assignment02.csc214.project2.Models.Profile;
import assignment02.csc214.project2.R;

/**
 * Created by Kennedy on 4/7/2017.
 */

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedHolder>  {

    private List<Feed> mFeeds;
    private Context mContext;
    private String mUsername;
    private String[] mLikedFeeds;
    private Fragment mFragment;

    public FeedRecyclerAdapter(List<Feed> feed, String[] likedFeeds, String username, Fragment fragment) {
        mFeeds = feed;
        mUsername = username;
        mLikedFeeds = likedFeeds;
        mFragment = fragment;
    }

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.feed_display_layout, parent, false);
        mContext = parent.getContext();

        FeedHolder holder = new FeedHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FeedHolder holder, int position) {
        Feed feed = mFeeds.get(position);
        String feedId = feed.getFeedID();
        //Check whether the user marked this profile as favorite
        feed.setLiked(searchLiked(feedId));
        holder.setUserAccount(mUsername, mContext, mFragment);
        //Bind profile to view
        holder.bind(feed);

    }

    @Override
    public int getItemCount() {
        return mFeeds.size();
    }

    public boolean searchLiked(String feedID)
    {
        for(int i = 0; i< mLikedFeeds.length; i++)
        {
            if(mLikedFeeds[i].equals(feedID))
                return true;
        }

        return false;
    }
}
