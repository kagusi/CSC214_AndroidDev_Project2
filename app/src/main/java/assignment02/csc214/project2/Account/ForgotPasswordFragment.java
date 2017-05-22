package assignment02.csc214.project2.Account;


import android.app.Activity;
import android.content.Context;
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
import assignment02.csc214.project2.Models.Account;
import assignment02.csc214.project2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends Fragment {

    public interface ForgotPassUpdate{
        public void doneForgotPass();
    }

    public ForgotPassUpdate mForgotPassUpdate;
    private EditText mUsernameEdiText;
    private EditText mSecAnsEdiText;
    private EditText mPasswordEdiText;
    private EditText mComfirmPassEdiText;
    private TextView mCancelTextView;
    private Button mSubmitButton;
    private Account account;



    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mForgotPassUpdate = (ForgotPassUpdate)context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mForgotPassUpdate = (ForgotPassUpdate)activity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        mUsernameEdiText = (EditText)view.findViewById(R.id.RecoverPassword_usernameEditText);
        mSecAnsEdiText = (EditText)view.findViewById(R.id.RecoverPassword_securityQuestionEditText);
        mPasswordEdiText = (EditText)view.findViewById(R.id.RecoverPassword_passwordEditText);
        mComfirmPassEdiText = (EditText)view.findViewById(R.id.RecoverPassword_renterpasswordEditText);
        mCancelTextView = (TextView)view.findViewById(R.id.RecoverPassword_cacel);
        mSubmitButton = (Button)view.findViewById(R.id.RecoverPassword_button);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(mUsernameEdiText.getText().toString()))
                {
                    Toast.makeText(getActivity(), R.string.e1, Toast.LENGTH_LONG).show();

                }
                else if(TextUtils.isEmpty(mSecAnsEdiText.getText().toString()))
                {
                    Toast.makeText(getActivity(), R.string.e2, Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(mPasswordEdiText.getText().toString()))
                {
                    Toast.makeText(getActivity(), R.string.chose1, Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(mComfirmPassEdiText.getText().toString()))
                {
                    Toast.makeText(getActivity(), R.string.choose2, Toast.LENGTH_LONG).show();
                }
                else
                {
                    String username = mUsernameEdiText.getText().toString();
                    String secAns = mSecAnsEdiText.getText().toString();
                    String pass1 = mPasswordEdiText.getText().toString();
                    String pass2 = mComfirmPassEdiText.getText().toString();

                    if(pass1.equals(pass2))
                    {
                        String[] uname = new String[1];
                        uname[0] = username;
                        account = ModelQueryManager.get(getContext().getApplicationContext()).login(uname);
                        if(account == null)
                            Toast.makeText(getActivity(), R.string.user_n, Toast.LENGTH_LONG).show();
                        else
                        {
                            String verifySecAns = account.verifySecureQuestion(secAns);
                            if(verifySecAns.equals("success"))
                            {
                                account.setPassword(pass1);
                                ModelQueryManager.get(getContext().getApplicationContext()).updatePassword(account);
                                mForgotPassUpdate.doneForgotPass();
                                Toast.makeText(getActivity(), R.string.e4, Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(getActivity(), R.string.e5, Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), R.string.e6, Toast.LENGTH_LONG).show();

                    }

                }
            }
        });


        mCancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mForgotPassUpdate.doneForgotPass();
            }
        });

        return view;
    }

}
