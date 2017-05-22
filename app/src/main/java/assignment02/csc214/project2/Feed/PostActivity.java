package assignment02.csc214.project2.Feed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import assignment02.csc214.project2.Account.LoginFragment;
import assignment02.csc214.project2.Models.Profile;
import assignment02.csc214.project2.R;

public class PostActivity extends AppCompatActivity implements PostFragment.PostFeedDone {

    private PostFragment mPostFragmentfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Profile profile = (Profile)getIntent().getSerializableExtra(LoginFragment.ACCOUNTKEY);
        Bundle bundle = new Bundle();
        bundle.putSerializable(LoginFragment.ACCOUNTKEY, profile);
        mPostFragmentfragment = new PostFragment();
        mPostFragmentfragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.Post_Activity_postLayout, mPostFragmentfragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK)
            mPostFragmentfragment.displayPostPic();
        else
            mPostFragmentfragment.removePostPic();

    }

    @Override
    public void postFeedDone() {
        setResult(RESULT_OK);
        finish();
    }
}
