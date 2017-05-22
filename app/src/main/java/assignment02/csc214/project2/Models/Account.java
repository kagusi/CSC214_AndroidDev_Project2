package assignment02.csc214.project2.Models;

import java.io.Serializable;

/**
 * Created by Kennedy on 3/30/2017.
 */

public class Account implements Serializable {

    private String mUsername;
    private String mPassword;
    private String mSecurityQuestion;

    public Account(String username, String password, String securityQuestion) {
        mUsername = username;
        mPassword = password;
        mSecurityQuestion = securityQuestion;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getSecurityQuestion() {
        return mSecurityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        mSecurityQuestion = securityQuestion;
    }

    public String verifyPass(String password)
    {
        if(password.equals(mPassword))
            return "success";
        else
            return "failed";
    }

    public String verifySecureQuestion(String secureQuestion)
    {
        if(secureQuestion.equals(mSecurityQuestion))
            return "success";
        else
            return "failed";
    }






}
