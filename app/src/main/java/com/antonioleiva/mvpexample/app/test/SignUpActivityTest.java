package com.antonioleiva.mvpexample.app.test;

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
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by korupolu on 30.12.2015.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class SignUpActivityTest {
    private static final String BASIC_SAMPLE_PACKAGE
            = "com.antonioleiva.mvpexample.app";
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice mDevice;
    private Context mContext;

    /**
     * Default constructor
     */
    public SignUpActivityTest()
    {
    }
    /**
     * This function will launch teh application. After  launch main UI will be displayed.
     */
    @Before
    public void setUp() throws Exception
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

        //launch Sing Up Page
        UiObject signUpButton =  mDevice.findObject(new UiSelector()
                .text("Sign Up")
                .className("android.widget.Button"));

        signUpButton.click();

    }

    @Test
    /**
     * This test case verify the alert dialog displayed when Create User button clicked with empty username
     */

    public void testEmptyUsernameAlertInSignUpPage() throws Exception
    {
        //click the Create User button
        UiObject createUserButton = mDevice.findObject(new UiSelector()
                .text("Create User")
                .resourceId("com.antonioleiva.mvpexample.app:id/create_user_button")
                .className("android.widget.Button"));

        createUserButton.click();

        UiObject alertDialog = mDevice.findObject(new UiSelector()
                .className("android.widget.TextView")
                .resourceId("android:id/message")
                .text("Username cannot be empty"));
        assertNotNull(alertDialog);
    }

    @Test
    /**
     * This test case verify the alert dialog displayed when Create User button clicked with empty password
     */
    public void testEmptyPasswordAlertInSingUpPage() throws Exception
    {
        //set username
        UiObject userNameText = mDevice.findObject(new UiSelector().className("android.widget.EditText")
                .instance(0)
                .text("username"));
        userNameText.setText("test");

        //click the Create User button
        UiObject createUserButton = mDevice.findObject(new UiSelector()
                .text("Create User")
                .className("android.widget.Button"));
        createUserButton.click();

        UiObject alertDialog = mDevice.findObject(new UiSelector()
                .className("android.widget.TextView")
                .resourceId("android:id/message")
                .text("Password cannot be empty"));
        assertNotNull(alertDialog);
    }


    @Test
    /**
     * This test case verify the alert dialog displayed when Create User button clicked with empty re-type password
     */
    public void testEmptyRetypePasswordAlertInSingUpPage() throws Exception
    {
        //set username
        UiObject userNameText = mDevice.findObject(new UiSelector().className("android.widget.EditText")
                .instance(0)
                .text("username"));
        userNameText.setText("test");

        //set password
        UiObject passwordText = mDevice.findObject(new UiSelector()
                .resourceId("com.antonioleiva.mvpexample.app:id/sign_in_password"));
        passwordText.setText("test");

        //click the Create User button
        UiObject createUserButton = mDevice.findObject(new UiSelector()
                .text("Create User")
                .className("android.widget.Button"));
        createUserButton.click();

        UiObject alertDialog = mDevice.findObject(new UiSelector()
                .className("android.widget.TextView")
                .resourceId("android:id/message")
                .text("New password cannot be empty"));
        assertNotNull(alertDialog);
    }

    @Test
    /**
     * This test case verify the alert dialog displayed when Create User button clicked with not same password and re-type password
     */
    public void testNotSamePasswordAlertInSingUpPage() throws Exception
    {
        //set username
        UiObject userNameText = mDevice.findObject(new UiSelector().className("android.widget.EditText")
                .instance(0)
                .text("username"));
        userNameText.setText("test");

        //set password
        UiObject passwordText = mDevice.findObject(new UiSelector()
                .resourceId("com.antonioleiva.mvpexample.app:id/sign_in_password"));
        passwordText.setText("test");

        //set retype password
        UiObject retypePasswordText = mDevice.findObject(new UiSelector()
                .resourceId("com.antonioleiva.mvpexample.app:id/retype_password"));
        retypePasswordText.setText("test111");

        //click the Create User button
        UiObject createUserButton = mDevice.findObject(new UiSelector()
                .text("Create User")
                .resourceId("com.antonioleiva.mvpexample.app:id/create_user_button")
                .className("android.widget.Button"));

        createUserButton.click();

        UiObject alertDialog = mDevice.findObject(new UiSelector()
                .className("android.widget.TextView")
                .resourceId("android:id/message")
                .text("Password and re-type password not same"));
        assertNotNull(alertDialog);
    }

    @Test
    /**
     * This test case verify the alert message generated when user created successfully.
     */

    public void testCreateUser() throws Exception
    {
        //set username
        UiObject userNameText = mDevice.findObject(new UiSelector().className("android.widget.EditText")
                .instance(0)
                .text("username"));
        userNameText.setText("test");

        //set password
        UiObject passwordText = mDevice.findObject(new UiSelector()
                .resourceId("com.antonioleiva.mvpexample.app:id/sign_in_password"));
        passwordText.setText("test");

        //set retype password
        UiObject retypePasswordText = mDevice.findObject(new UiSelector()
                .resourceId("com.antonioleiva.mvpexample.app:id/retype_password"));
        retypePasswordText.setText("test");

        //tap on Create User button
        UiObject createUserButton = mDevice.findObject(new UiSelector()
                .text("Create User")
                .resourceId("com.antonioleiva.mvpexample.app:id/create_user_button")
                .className("android.widget.Button"));
        createUserButton.click();

        UiObject alertDialog = mDevice.findObject(new UiSelector()
                .className("android.widget.TextView")
                .resourceId("android:id/message")
                .text("User created successfully."));
        assertNotNull(alertDialog);
    }

    @Test
    /**
     * This test case verify the alert message generated when user try to  create another user with same name
     */

    public void testCreateSameUserAgain() throws Exception
    {
        //set username
        UiObject userNameText = mDevice.findObject(new UiSelector().className("android.widget.EditText")
                .instance(0)
                .text("username"));
        userNameText.setText("test");

        //set password
        UiObject passwordText = mDevice.findObject(new UiSelector()
                .resourceId("com.antonioleiva.mvpexample.app:id/sign_in_password"));
        passwordText.setText("test");

        //set retype password
        UiObject retypePasswordText = mDevice.findObject(new UiSelector()
                .resourceId("com.antonioleiva.mvpexample.app:id/retype_password"));
        retypePasswordText.setText("test");

        //tap on Create User button

        UiObject createUserButton = mDevice.findObject(new UiSelector()
                .text("Create User")
                .resourceId("com.antonioleiva.mvpexample.app:id/create_user_button")
                .className("android.widget.Button"));
        createUserButton.click();

        UiObject alertDialog = mDevice.findObject(new UiSelector()
                .className("android.widget.TextView")
                .resourceId("android:id/message")
                .text("User already exists. Use other user id."));
        assertNotNull(alertDialog);
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
