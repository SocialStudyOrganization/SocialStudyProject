package com.ndlp.socialstudy.LoginSystem;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ndlp.socialstudy.NavigationDrawer_BottomNavigation.MainActivity;
import com.ndlp.socialstudy.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity to compare inputs with server data and grant access
 * handels also SharedPref rememberMe
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //declaring typefonts
        Typeface quicksand_regular = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Regular.otf");
        Typeface quicksand_bold = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Bold.otf");
        Typeface quicksand_bolditalic = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-BoldItalic.otf");
        Typeface quicksand_italic = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Italic.otf");
        Typeface quicksand_light = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Light.otf");
        Typeface quicksand_lightitalic = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-LightItalic.otf");

        RelativeLayout rl_background = (RelativeLayout) findViewById(R.id.rl_background);

        //create BG gradient
        ShapeDrawable.ShaderFactory shaderFactory = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                LinearGradient linearGradient = new LinearGradient(width, 0, width, height,
                        new int[] {
                                ContextCompat.getColor(LoginActivity.this, R.color.bgg_hellblau),
                                ContextCompat.getColor(LoginActivity.this, R.color.bgg_hellblau_alt),
                                ContextCompat.getColor(LoginActivity.this, R.color.bgg_dunkelblau),
                                ContextCompat.getColor(LoginActivity.this, R.color.bgg_dunkelgrau)
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

        //  connect to the xml widgets
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final TextView tvRegisterLink = (TextView) findViewById(R.id.tvRegisterHere);

        final CheckBox rememberMe = (CheckBox) findViewById(R.id.cb_rememberMe);

        Button bLogin = (Button) findViewById(R.id.bLogin);


        //  navigate to register a new user
        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        //  compare input data with server data
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  transfer inputs to strings
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();
                final Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);

                //  when sth is not filled show toast
                if (email.matches("") || password.matches("")) {
                    Toast.makeText(LoginActivity.this, "Fill in your Email and Password", Toast.LENGTH_LONG).show();

                }
                //  set listener for a server response
                else {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //  gets called if a response is transmitted
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                //  if true put username and password in sharedPrefLoginData
                                if (success) {
                                    SharedPreferences sharedPrefLoginData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editorLoginData = sharedPrefLoginData.edit();
                                    editorLoginData.putString("username", email);
                                    editorLoginData.putString("password", password);
                                    editorLoginData.apply();

                                    //  check if rememberMe is checked and put it in sharedPrefrememberMe
                                    if (rememberMe.isChecked()) {
                                        SharedPreferences sharedPrefrememberMe = getSharedPreferences("rememberMe", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editorrememberMe = sharedPrefrememberMe.edit();
                                        editorrememberMe.putString("username", email);
                                        editorrememberMe.apply();
                                    }

                                    //  start MainActivity
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    LoginActivity.this.startActivity(intent);
                                    finish();

                                //  if false give toast
                                } else {

                                    findViewById(R.id.etEmail).startAnimation(shake);
                                    findViewById(R.id.etPassword).startAnimation(shake);
                                    Toast.makeText(LoginActivity.this, "Wrong Username or Password", Toast.LENGTH_LONG).show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    //  call class LoginRequest
                    LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }


            }
        });
    }


}
