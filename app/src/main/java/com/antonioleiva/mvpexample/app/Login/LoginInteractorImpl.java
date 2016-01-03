package com.antonioleiva.mvpexample.app.Login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.antonioleiva.mvpexample.app.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class LoginInteractorImpl implements LoginInteractor {

    @Override
    public void login(final String username, final String password, final OnLoginFinishedListener listener, final Context context) {
        // Mock login. I'm creating a handler to delay the answer a couple of seconds
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                boolean error = false;
                if (TextUtils.isEmpty(username)){
                    //listener.onUsernameError();
                    listener.showMessage(R.string.username_error);
                    error = true;
                }
                else if (TextUtils.isEmpty(password)){
                    //listener.onPasswordError();
                    listener.showMessage(R.string.password_error);
                    error = true;
                }
                else if (!error) {
                    checkCreadentials(username, password, listener, context);
                }
            }
        }, 2000);
    }

    //method to simulate username and password persitence
    public void checkCreadentials(final String username, final String password,final OnLoginFinishedListener listener, final Context context)
    {
        boolean result = false;
        SharedPreferences  settings = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String pwd = settings.getString(username.trim(), null);
       if (pwd == null)
       {
           listener.showMessage(R.string.user_does_not_exist);
       }
        else if (pwd.equals(password.trim()))
       {
           listener.onSuccess();
       }
        else
       {
           listener.showMessage(R.string.incorrect_password);
       }
    }
}
