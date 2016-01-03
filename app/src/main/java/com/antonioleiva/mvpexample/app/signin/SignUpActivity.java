package com.antonioleiva.mvpexample.app.signin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.antonioleiva.mvpexample.app.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import android.content.Context;
import android.content.SharedPreferences;

public class SignUpActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    /*
       Method for creating username
     */
    public void createLogin(View view)
    {
        //validate entered data
        EditText username = (EditText) findViewById(R.id.sign_in_username);
        EditText password = (EditText) findViewById(R.id.sign_in_password);
        EditText retyprpassword = (EditText) findViewById(R.id.retype_password);
        if (username.length() == 0) {
            showMessage(R.string.username_error);
        } else if (password.length() == 0) {
            showMessage(R.string.password_error);
        } else if (retyprpassword.length() == 0) {
            showMessage(R.string.retyprepassword_error);
        } else if (!password.getText().toString().equals(retyprpassword.getText().toString())) {
            showMessage(R.string.both_passwords_not_same);
        } else {
            saveUsernameAndPassword(username.getText().toString().trim(), password.getText().toString().trim());
        }
    }

    //method for storing the data
    public void saveUsernameAndPassword(String username, String password) {
        SharedPreferences sharedpreferences = this.getSharedPreferences("myPrefs",MODE_APPEND);
        //check if user already exists
        String pwd = sharedpreferences.getString(username.trim(), null);
        if (pwd != null)
        {
            showMessage(R.string.user_alredy_exists);
        }
        else
        {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(username, password);
            editor.commit();
            showMessage(R.string.user_created_successfully);
        }
    }

    // method for showing the alert message
    public void showMessage(int errorMessage) {
        //username.setError(getString(R.string.username_error));
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(errorMessage);
        alertDialogBuilder.setNegativeButton(R.string.ok_button_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(true);
    }
}
