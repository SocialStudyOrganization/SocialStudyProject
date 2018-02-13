package com.ndlp.socialstudy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ndlp.socialstudy.LoginSystem.LoginActivity;
import com.ndlp.socialstudy.NavigationDrawer_BottomNavigation.MainActivity;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.tutorialsheets.TutorialsheetsSlider;
import com.testfairy.TestFairy;

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

        TestFairy.begin(this, "ab6ad7265adebec9d50f5a02ce3ca3806289d3fe");


        //create BG gradient
        RelativeLayout rl_background = (RelativeLayout) findViewById(R.id.rl_background);
        ShapeDrawable.ShaderFactory shaderFactory = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                LinearGradient linearGradient = new LinearGradient(width, 0, width, height,
                        new int[] {
                                ContextCompat.getColor(AppLogoFirstPage.this, R.color.bgg_hellblau),
                                ContextCompat.getColor(AppLogoFirstPage.this, R.color.bgg_hellblau_alt),
                                ContextCompat.getColor(AppLogoFirstPage.this, R.color.bgg_dunkelblau),
                                ContextCompat.getColor(AppLogoFirstPage.this, R.color.bgg_dunkelgrau)
                        },
                        new float[] {
                                0, 0.4f, 0.9f, 1 },
                        Shader.TileMode.REPEAT);
                return linearGradient;
            }
        };
        PaintDrawable paint = new PaintDrawable();
        paint.setShape(new RectShape());
        paint.setShaderFactory(shaderFactory);

        rl_background.setBackground(paint);

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Regular.otf");
        Typeface quicksand_bold = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Bold.otf");

        TextView et_appname = (TextView) findViewById(R.id.et_appname);

        et_appname.setTypeface(quicksand_bold);

        CleanUmfragenNews cleanUmfragenNews = new CleanUmfragenNews(AppLogoFirstPage.this);
        cleanUmfragenNews.deleteoldDataonServer();

        //  Show this Activity for x ms
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                LoginData();
            }
        }, 1000);
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
