package assignment02.csc214.project2.Account;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import assignment02.csc214.project2.Database.ModelQueryManager;
import assignment02.csc214.project2.Models.Account;
import assignment02.csc214.project2.Models.Profile;
import assignment02.csc214.project2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAccountFragment extends Fragment {

    public interface CreateAccountFragUpdate{
        public void createAccountDone(Account account);
        public void createAccountCancelled();
    }

    private CreateAccountFragUpdate mCreateAccountFragUpdate;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText mComfirmPassEditText;
    private EditText mSecureQuestionEditText;
    private Button mCreateAccountButton;
    private TextView mCancelTextView;


    private String mUsername;
    private String mPassword;
    private String mComfirmpass;
    private String mSecureQuestion;

    public CreateAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mCreateAccountFragUpdate = (CreateAccountFragUpdate)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mCreateAccountFragUpdate = (CreateAccountFragUpdate)activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);

        mUsernameEditText = (EditText)view.findViewById(R.id.createAccount_usernameEditText);
        mPasswordEditText = (EditText)view.findViewById(R.id.CreateAccount_passwordEditText);
        mSecureQuestionEditText = (EditText)view.findViewById(R.id.createAccount_securityQuestionEditText);
        mComfirmPassEditText = (EditText)view.findViewById(R.id.CreateAccount_renterpasswordEditText);
        mCreateAccountButton = (Button)view.findViewById(R.id.createAccount_button);
        mCancelTextView = (TextView)view.findViewById(R.id.createAccount_cacel);

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(mUsernameEditText.getText().toString()))
                {
                    Toast.makeText(getActivity(), R.string.enter_username2, Toast.LENGTH_LONG).show();

                }
                else if(TextUtils.isEmpty(mPasswordEditText.getText().toString()))
                {
                    Toast.makeText(getActivity(), R.string.enter_pas, Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(mComfirmPassEditText.getText().toString()))
                {
                    Toast.makeText(getActivity(), R.string.enterpass3, Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(mSecureQuestionEditText.getText().toString()))
                {
                    Toast.makeText(getActivity(), R.string.entersec, Toast.LENGTH_LONG).show();
                }
                else
                {
                    mUsername = mUsernameEditText.getText().toString();
                    mPassword = mPasswordEditText.getText().toString();
                    mComfirmpass = mComfirmPassEditText.getText().toString();
                    mSecureQuestion = mSecureQuestionEditText.getText().toString();

                    if(mPassword.equals(mComfirmpass))
                    {
                        Account account;
                        String[] uname = new String[1];
                        uname[0] = mUsername;
                        //Check whether username already exist
                        account = ModelQueryManager.get(getContext().getApplicationContext()).login(uname);
                        if(account != null)
                            Toast.makeText(getActivity(), R.string.user_exist, Toast.LENGTH_LONG).show();
                        else
                        {
                            account = new Account(mUsername, mPassword, mSecureQuestion);
                            ModelQueryManager.get(getContext().getApplicationContext()).createAccount(account);
                            Profile profile = new Profile(mUsername,"","","","","");
                            ModelQueryManager.get(getContext().getApplicationContext()).createProfile(profile);

                            Toast.makeText(getActivity(), R.string.accout_success, Toast.LENGTH_LONG).show();
                            mCreateAccountFragUpdate.createAccountDone(account);

                        }

                    }
                    else
                    {
                        Toast.makeText(getActivity(), R.string.pass_not_match, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        mCancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCreateAccountFragUpdate.createAccountCancelled();
            }
        });


        return view;
    }



}
