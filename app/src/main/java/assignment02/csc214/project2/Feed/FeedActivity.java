package assignment02.csc214.project2.Feed;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import assignment02.csc214.project2.Account.AccountActivity;
import assignment02.csc214.project2.Account.LoginFragment;
import assignment02.csc214.project2.Database.ModelQueryManager;
import assignment02.csc214.project2.Models.Account;
import assignment02.csc214.project2.Models.Profile;
import assignment02.csc214.project2.PictureManager.PictureManager;
import assignment02.csc214.project2.Profile.UpdateProfileFragment;
import assignment02.csc214.project2.Profile.Update_profileActivity;
import assignment02.csc214.project2.R;
import assignment02.csc214.project2.Social_Network.ListOfUsersFragment;
import assignment02.csc214.project2.Social_Network.Social_NetworkActivity;

public class FeedActivity extends AppCompatActivity implements FeedFragment.FeedFragUpdate {

    private NavigationView mNavigationView;
    private DrawerLayout mNavigationLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    ActionBar aBar;
    private Account account;
    private Profile profile;
    private ImageView mProfilePicImageView;
    private TextView mProfileNameTextview;
    private PictureManager mPictureManager;
    private static final String TAG = "FeedActivity";
    public static final int FEED_POST_KEY = 3;

    private FeedFragment mFeedFragment;
    private ListOfUsersFragment mListOfUsersFragment;

    private String mAddedFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        aBar = getSupportActionBar();
        aBar.setTitle(R.string.app_titlename);
        //aBar.setHomeAsUpIndicator(R.drawable.drawer_icon2);
        //aBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        account = (Account)intent.getSerializableExtra(LoginFragment.ACCOUNTKEY);
        setProfile();
        mPictureManager = new PictureManager(FeedActivity.this);



        //Set Navigation Drawer
        mNavigationView = (NavigationView)findViewById(R.id.drawer);
        mNavigationLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mNavigationLayout,
                R.string.app_name,
                R.string.app_name
        ) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                aBar.setTitle(R.string.app_name);
                //aBar.setHomeAsUpIndicator(R.drawable.drawer_icon2);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                aBar.setTitle("Menu");
                //aBar.setHomeAsUpIndicator(R.drawable.home_action_icon);
            }
        };

        mNavigationLayout.setDrawerListener(mDrawerToggle);

        aBar.setDisplayHomeAsUpEnabled(true);
        aBar.setHomeButtonEnabled(true);


        //NavigationView Click Listener
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                boolean handled;
                switch(item.getItemId()) {
                    case R.id.menu_feed:
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(UpdateProfileFragment.PROFILE_KEY, profile);
                        mFeedFragment = new FeedFragment();
                        mFeedFragment.setArguments(bundle);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.feed_activity_framelayout, mFeedFragment)
                                .commit();
                        handled = true;
                        break;
                    case R.id.menu_users:
                        Intent intent = new Intent(FeedActivity.this, Social_NetworkActivity.class);
                        intent.putExtra(UpdateProfileFragment.PROFILE_KEY, profile);
                        intent.putExtra(LoginFragment.ACCOUNTKEY, account);
                        startActivity(intent);
                        /*
                        Bundle bundle2 = new Bundle();
                        bundle2.putSerializable(UpdateProfileFragment.PROFILE_KEY, profile);
                        mListOfUsersFragment = new ListOfUsersFragment();
                        mListOfUsersFragment.setArguments(bundle2);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.feed_activity_framelayout, mListOfUsersFragment)
                                .commit();
                        aBar.setTitle(R.string.social_menu_title);
                        */
                        //restartActivity(Social_NetworkActivity.class);
                        handled = true;
                        break;
                    case R.id.menu_update_profile:
                        restartActivity(Update_profileActivity.class);
                        handled = true;
                        break;
                    case R.id.menu_logout:
                        restartActivity(AccountActivity.class);
                        handled = true;
                        break;
                    default:
                        handled = true;
                        break;
                }

                mNavigationLayout.closeDrawers();

                return handled;
            }
        });

        View header = mNavigationView.getHeaderView(0);
        mProfilePicImageView = (ImageView)header.findViewById(R.id.drawer_profilePic);
        mProfileNameTextview = (TextView)header.findViewById(R.id.drawer_profilename);
        updateDrawerHeader();

        //Add Feed Fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable(UpdateProfileFragment.PROFILE_KEY, profile);
        mFeedFragment = new FeedFragment();
        mFeedFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.feed_activity_framelayout, mFeedFragment)
                .commit();
    }

    private void restartActivity(Class activityClass) {
        Intent intent = new Intent(FeedActivity.this, activityClass);
        intent.putExtra(LoginFragment.ACCOUNTKEY, account);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateDrawerHeader()
    {
        String[] uname = new String[1];
        uname[0] = account.getUsername();
        profile = ModelQueryManager.get(getApplicationContext()).getProfile(uname);
        String mImageUrl = profile.getProfilePicLocation();

        if(!mImageUrl.isEmpty())
        {
            Bitmap bitmap = mPictureManager.getScaledBitmap(mImageUrl,
                    mProfilePicImageView.getWidth(), mProfilePicImageView.getMaxHeight());
            mProfilePicImageView.setImageBitmap(bitmap);
            mProfileNameTextview.setText(profile.getFullName());
        }
        if(!profile.getFullName().isEmpty())
            mProfileNameTextview.setText(profile.getFullName());

    }

    public void setProfile()
    {
        String[] uname = new String[1];
        uname[0] = account.getUsername();
        profile = ModelQueryManager.get(getApplicationContext()).getProfile(uname);
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(TAG, "onResume() called");
        updateDrawerHeader();
        aBar.setTitle(R.string.app_titlename);


        Bundle bundle = new Bundle();
        bundle.putSerializable(UpdateProfileFragment.PROFILE_KEY, profile);
        mFeedFragment = new FeedFragment();
        mFeedFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.feed_activity_framelayout, mFeedFragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState() called");

    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);


    }


    @Override
    public void commentDone() {

        Bundle bundle = new Bundle();
        bundle.putSerializable(UpdateProfileFragment.PROFILE_KEY, profile);
        mFeedFragment = new FeedFragment();
        mFeedFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.feed_activity_framelayout, mFeedFragment)
                .commit();

    }
}
