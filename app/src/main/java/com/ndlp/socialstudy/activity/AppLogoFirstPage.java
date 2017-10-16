package com.ndlp.socialstudy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ndlp.socialstudy.NavigationDrawer_BottomNavigation.MainActivity;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.tutorialsheets.TutorialsheetsSlider;

/***
 * Activity to display an intro before starting the actual App
 * Handle the SharedPreferences (remember me) to navigate
 */

public class AppLogoFirstPage extends AppCompatActivity {

    public String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_logo_first_page);

        //  Show this Activity for x ms
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                LoginData();
            }
        }, 3000);


    }

    public void LoginData() {

        //  post SharedPreferences
        SharedPreferences sharedPrefLoginData = getSharedPreferences("rememberMe", Context.MODE_PRIVATE);
        user = sharedPrefLoginData.getString("username", "");

        //  navigate to TutorialsheetsSlider if nothing is existing in SharedPrefs
        if (user == ""){
            Intent intent = new Intent(AppLogoFirstPage.this, TutorialsheetsSlider.class);
            AppLogoFirstPage.this.startActivity(intent);
            finish();


        }
        //  navigate to MainActivity if a username is existing in SharedPrefs
        else {
            Intent intent = new Intent(AppLogoFirstPage.this, MainActivity.class);
            AppLogoFirstPage.this.startActivity(intent);
            finish();
        }

    }

}
