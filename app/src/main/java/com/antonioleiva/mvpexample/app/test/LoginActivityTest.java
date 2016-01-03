package com.antonioleiva.mvpexample.app.test;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.antonioleiva.mvpexample.app.Login.LoginActivity;
import com.antonioleiva.mvpexample.app.R;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
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
public class LoginActivityTest  {
    private static final String BASIC_SAMPLE_PACKAGE
            = "com.antonioleiva.mvpexample.app";
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice mDevice;
    private Context mContext;

    /**
     * Default constructor
     */
    public LoginActivityTest()
    {
    }
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
     * This test case verify the alert dialog displayed when Log In button clicked with empty username
     */

    public void testEmptyUsernameAlert() throws Exception
    {
        //click the Log In button
        UiObject logInButton = mDevice.findObject(new UiSelector()
                .text("Log In")
                .className("android.widget.Button"));
        logInButton.click();

        UiObject alertDialog = mDevice.findObject(new UiSelector()
                .className("android.widget.TextView")
                .resourceId("android:id/message")
                .text("Username cannot be empty"));
        assertNotNull(alertDialog);
    }

    @Test
    /**
     * This test case verify the alert dialog displayed when Log In button clicked with empty password
     */
    public void testEmptyPasswordAlert() throws Exception
    {
        //set username
        UiObject userNameText = mDevice.findObject(new UiSelector().className("android.widget.EditText")
                .instance(0)
                .text("username"));
        userNameText.setText("test");

        //click the Log In button
        UiObject logInButton = mDevice.findObject(new UiSelector()
                .text("Log In")
                .className("android.widget.Button"));
        logInButton.click();

        UiObject alertDialog = mDevice.findObject(new UiSelector()
                .className("android.widget.TextView")
                .resourceId("android:id/message")
                .text("Password cannot be empty"));
        assertNotNull(alertDialog);
    }

    @Test
    /**
     * This test case verify the alert message generated when user try to login with invalid user name
     */

    public void testInvalidUser() throws Exception
    {
        //set username
        UiObject userNameText = mDevice.findObject(new UiSelector().className("android.widget.EditText")
                .instance(0)
                .text("username"));
        userNameText.setText("test");

        //set password
        UiObject passwordText = mDevice.findObject(new UiSelector()
                .resourceId("com.antonioleiva.mvpexample.app:id/password"));
        passwordText.setText("test111111");

        //click the Log In button
        UiObject logInButton = mDevice.findObject(new UiSelector()
                .text("Log In")
                .className("android.widget.Button"));
        logInButton.click();

        UiObject alertDialog = mDevice.findObject(new UiSelector()
                        .className("android.widget.TextView")
                        .resourceId("android:id/message")
                        .text("User does not exist. Please create user first!"));
        assertNotNull(alertDialog);
    }

    @Test
    /**
     * This test case verify the alert message generated when user try to login with incorrect password
     */

    public void testInvalidPassword() throws Exception
    {
        //create test user in shared preference
        //createUser();

        //set username
        UiObject userNameText = mDevice.findObject(new UiSelector().className("android.widget.EditText")
                .instance(0)
                .text("username"));
        userNameText.setText("test");

        //set password
        UiObject passwordText = mDevice.findObject(new UiSelector()
                .resourceId("com.antonioleiva.mvpexample.app:id/password"));
        passwordText.setText("test1111");

        //click the Log In button
        UiObject logInButton = mDevice.findObject(new UiSelector()
                .text("Log In")
                .className("android.widget.Button"));
        logInButton.click();

        UiObject alertDialog = mDevice.findObject(new UiSelector()
                .className("android.widget.TextView")
                .resourceId("android:id/message")
                .text("Incorrect password!. Try again.!"));
        assertNotNull(alertDialog);
    }


    @Test
    /**
     * This test case verify that valid user can login and main view is displayed
     */
    public void testValidCredentials() throws Exception
    {

        //set username
        UiObject userNameText = mDevice.findObject(new UiSelector().className("android.widget.EditText")
                .instance(0)
                .text("username"));
        userNameText.setText("test");

        //set password
        UiObject passwordText = mDevice.findObject(new UiSelector()
                .resourceId("com.antonioleiva.mvpexample.app:id/password"));
        passwordText.setText("test");

        //click the Log In button
        UiObject logInButton = mDevice.findObject(new UiSelector()
                .text("Log In")
                .className("android.widget.Button"));
        logInButton.click();

        //verify that item1 exists
        UiObject item1 = mDevice.findObject(new UiSelector()
                .className("android.widget.TextView")
                .index(0)
                .text("Item 1"));
        assertNotNull(item1);

        //verify that item5 exists
        UiObject item5 = mDevice.findObject(new UiSelector()
                .className("android.widget.TextView")
                .index(4)
                .text("Item 5"));
        assertNotNull(item5);
    }

    /**
     * teardown method to clean up created users.
     */
    @After
    public void tearDown() throws Exception
    {
        Thread.currentThread().sleep(10000);
    }
}
