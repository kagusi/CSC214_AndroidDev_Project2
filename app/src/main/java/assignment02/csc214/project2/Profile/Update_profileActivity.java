package assignment02.csc214.project2.Profile;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import assignment02.csc214.project2.Account.LoginFragment;
import assignment02.csc214.project2.Models.Account;
import assignment02.csc214.project2.R;

public class Update_profileActivity extends AppCompatActivity {

    private Account account;
    private UpdateProfileFragment updateFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);


        ActionBar aBar = getSupportActionBar();
        aBar.setTitle(R.string.update_profile_menu_title);
        //aBar.setHomeAsUpIndicator(R.drawable.home_action_icon);
        Intent intent = getIntent();
        account = (Account)intent.getSerializableExtra(LoginFragment.ACCOUNTKEY);

        Bundle bundle = new Bundle();
        bundle.putSerializable(LoginFragment.ACCOUNTKEY, account);
        updateFrag = new UpdateProfileFragment();
        updateFrag.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.update_profile_fragInsert, updateFrag)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        updateFrag.changeImage();

    }
}
