package assignment02.csc214.project2.Account;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import assignment02.csc214.project2.Feed.FeedActivity;
import assignment02.csc214.project2.Models.Account;
import assignment02.csc214.project2.Profile.CreateProfileFragment;
import assignment02.csc214.project2.Profile.Update_profileActivity;
import assignment02.csc214.project2.R;

public class AccountActivity extends FragmentActivity implements LoginFragment.LoginFragUpdate,
        CreateAccountFragment.CreateAccountFragUpdate, ForgotPasswordFragment.ForgotPassUpdate, CreateProfileFragment.CreateProfileUpdate {

    LoginFragment mLoginFrag;
    CreateAccountFragment mCreateAccFrag;
    ForgotPasswordFragment mForgotPasswordFrag;
    CreateProfileFragment mCreateProfileFragment;

    private RecyclerView mRecyclerView;

    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);


        mLoginFrag = new LoginFragment();

        getFragmentManager()
                .beginTransaction()
                .add(R.id.account_activity_layout, mLoginFrag)
                .commit();


    }

    @Override
    public void loginSuccess(Account account) {

        Intent intent = new Intent(AccountActivity.this, FeedActivity.class);
        intent.putExtra(LoginFragment.ACCOUNTKEY, account);
        startActivity(intent);

        showProgressBar(true);


    }

    @Override
    public void addCreateAccountFrag() {

        mCreateAccFrag = new CreateAccountFragment();
        getFragmentManager()
                .beginTransaction()
                //.remove(mLoginFrag)
                .replace(R.id.account_activity_layout, mCreateAccFrag)
                .commit();
    }

    @Override
    public void addForgotPassFrag() {

        mForgotPasswordFrag = new ForgotPasswordFragment();
        getFragmentManager()
                .beginTransaction()
                .remove(mLoginFrag)
                .add(R.id.account_activity_layout, mForgotPasswordFrag)
                .commit();

    }


    @Override
    public void createAccountDone(Account account) {

        //Intent intent = new Intent(AccountActivity.this, Update_profileActivity.class);
        //intent.putExtra(LoginFragment.ACCOUNTKEY, account);
        //startActivity(intent);

        Bundle bundle = new Bundle();
        bundle.putSerializable(LoginFragment.ACCOUNTKEY, account);
        mCreateProfileFragment = new CreateProfileFragment();
        mCreateProfileFragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.account_activity_layout, mCreateProfileFragment)
                .commit();

    }

    @Override
    public void createAccountCancelled() {

        getFragmentManager()
                .beginTransaction()
                .remove(mCreateAccFrag)
                .add(R.id.account_activity_layout, mLoginFrag)
                .commit();

    }

    @Override
    public void doneForgotPass() {

        getFragmentManager()
                .beginTransaction()
                .remove(mForgotPasswordFrag)
                .add(R.id.account_activity_layout, mLoginFrag)
                .commit();

    }

    @Override
    public void createProfileDone(Account account) {

        Intent intent = new Intent(AccountActivity.this, FeedActivity.class);
        intent.putExtra(LoginFragment.ACCOUNTKEY, account);
        startActivity(intent);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.account_activity_layout, mLoginFrag)
                .commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        mCreateProfileFragment.changeImage();

    }

    @Override
    public void showProgressBar(boolean show) {
        showProgress(show);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            int longAnimTime = getResources().getInteger(android.R.integer.config_longAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(longAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        //Hide progressbar
        showProgress(false);

    }
}
