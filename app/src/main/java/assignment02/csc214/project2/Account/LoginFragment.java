package assignment02.csc214.project2.Account;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import assignment02.csc214.project2.Database.ModelQueryManager;
import assignment02.csc214.project2.Feed.FeedActivity;
import assignment02.csc214.project2.Models.Account;
import assignment02.csc214.project2.Models.Profile;
import assignment02.csc214.project2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    public interface LoginFragUpdate{
        public void loginSuccess(Account account);
        public void addCreateAccountFrag();
        public void addForgotPassFrag();
        public void showProgressBar(boolean show);
    }

    private LoginFragUpdate mLoginFragUpdate;
    private EditText mUsernameEdiText;
    private EditText mPasswordEdiText;
    private TextView mForgotPassTextView;
    private TextView mCreateAccountTextView;
    private Button mLoginButton;

    private String mUsername;
    private String mPassword;
    private Account account;

    public static final String ACCOUNTKEY = "AccountKey";



    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mLoginFragUpdate = (LoginFragUpdate)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mLoginFragUpdate = (LoginFragUpdate)activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mUsernameEdiText = (EditText)view.findViewById(R.id.Login_usernameEditText);
        mPasswordEdiText = (EditText)view.findViewById(R.id.Login_passwordEditText);
        mForgotPassTextView = (TextView)view.findViewById(R.id.Login_forgotPasswordTextView);
        mCreateAccountTextView = (TextView)view.findViewById(R.id.Login_createAccountTextView);
        mLoginButton = (Button)view.findViewById(R.id.Login_button);


        mForgotPassTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLoginFragUpdate.addForgotPassFrag();
            }
        });

        mCreateAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginFragUpdate.addCreateAccountFrag();
            }
        });

       mLoginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(TextUtils.isEmpty(mUsernameEdiText.getText().toString()))
               {
                   Toast.makeText(getActivity(), "PLEASE ENTER USERNAME", Toast.LENGTH_LONG).show();

               }
               else if(TextUtils.isEmpty(mPasswordEdiText.getText().toString()))
               {
                   Toast.makeText(getActivity(), "PLEASE ENTER PASSWORD", Toast.LENGTH_LONG).show();
               }
               else
               {
                   //Show progress bar
                   mLoginFragUpdate.showProgressBar(true);


                   mUsername = mUsernameEdiText.getText().toString();
                   mPassword = mPasswordEdiText.getText().toString();
                   String[] uname = new String[1];
                   uname[0] = mUsername;
                   //uname[1] = mUsername;
                   account = ModelQueryManager.get(getContext().getApplicationContext()).login(uname);

                   if(account == null)
                   {
                       Toast.makeText(getActivity(), "INVALID USERNAME", Toast.LENGTH_LONG).show();
                       //Show progress bar
                       mLoginFragUpdate.showProgressBar(false);
                   }

                   else
                   {
                       String verify = account.verifyPass(mPassword);
                       if(verify.equals("success"))
                       {

                           //ModelQueryManager.get(getContext().getApplicationContext()).createFeedTable();
                           mLoginFragUpdate.loginSuccess(account);

                       }
                       else
                       {
                           Toast.makeText(getActivity(), "INVALID PASSWORD", Toast.LENGTH_LONG).show();
                           //Show progress bar
                           mLoginFragUpdate.showProgressBar(false);
                       }


                   }

               }
           }
       });

        return view;
    }




}
