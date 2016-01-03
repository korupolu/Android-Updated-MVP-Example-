package com.antonioleiva.mvpexample.app.androidTest;


import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;


/**
 * Created by korupolu on 29.12.2015.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class LoginActivityUITest {
    private static final String BASIC_SAMPLE_PACKAGE
            = "com.antonioleiva.mvpexample.app";
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice mDevice;
    private Context mContext;

    /**
     * This function will launch teh application. After  launch main UI will be displayed.
     */
    @Before
    public void setUp()
    {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        mContext = InstrumentationRegistry.getContext();
        final Intent intent = mContext.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
                LAUNCH_TIMEOUT);
    }

    @Test
    /**
     * This test case verify that main page contains username element and its default text
     */
    public void testUserNameFieldPresenceInMainPage() throws Exception
    {
        //verify that username field exists
        UiObject userNameText = mDevice.findObject(new UiSelector().className("android.widget.EditText")
                                                                    .instance(0)
                                                                    .childSelector(new UiSelector()
                                                                            .text("username")));
        assertNotNull(userNameText);
    }


    /**
     * This test case verify that main page contains password element
     */
    @Test
    public void testPasswordFieldPresenceInMainPage() throws Exception
    {
        //verify that password field exists
        UiObject passwordText = mDevice.findObject(new UiSelector()
                .resourceId("com.antonioleiva.mvpexample.app:id/sign_in_password"));
        assertNotNull(passwordText);
    }

    /**
     * This test case verify that main page contains Log-In button and its text
     */
    @Test
    public void testLoginInButtonresenceInMainPage()throws Exception
    {
        //verify that Sign-In button exists
        UiObject logInButton = mDevice.findObject(new UiSelector()
                .text("Log In")
                .className("android.widget.Button"));
        assertNotNull(logInButton);
    }

    /**
     * This test case verify that main page contains  Sign Up button and its text
     */
    @Test
    public void testSignUpButtonresenceInMainPage() throws Exception
    {
        //verify that Sign Up button exists
        UiObject signUpButton =  mDevice.findObject(new UiSelector()
                                .text("Sign Up")
                                 .className("android.widget.Button"));
        assertNotNull(signUpButton);
    }

}
