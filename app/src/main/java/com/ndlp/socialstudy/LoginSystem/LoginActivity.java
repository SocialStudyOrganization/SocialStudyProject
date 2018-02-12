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
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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




public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Regular.otf");
        Typeface quicksand_bold = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Bold.otf");
        Typeface quicksand_light = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Light.otf");

        //create BG gradient
        RelativeLayout rl_background = (RelativeLayout) findViewById(R.id.rl_background);
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
        final TextView tv_RememberMe = (TextView) findViewById(R.id.tv_RememberMe);
        final CheckBox rememberMe = (CheckBox) findViewById(R.id.cb_rememberMe);
        final TextView tv_passwordForgotten = (TextView) findViewById(R.id.tv_passwordForgotten);


        Button bLogin = (Button) findViewById(R.id.bLogin);

        //assign typefaces
        etEmail.setTypeface(quicksand_regular);
        etPassword.setTypeface((quicksand_regular));
        tv_RememberMe.setTypeface(quicksand_light);
        bLogin.setTypeface(quicksand_bold);
        tv_passwordForgotten.setTypeface(quicksand_light);
        tvRegisterLink.setTypeface(quicksand_light);


        //  navigate to register a new user
        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        tv_passwordForgotten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, VergessenScreen.class);
                startActivity(intent);
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
                                Log.i("tagconvertstr", response);

                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {

                                    Toast.makeText(LoginActivity.this, jsonResponse.getString("error_msg"), Toast.LENGTH_LONG).show();


                                    SharedPreferences sharedPrefLoginData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editorLoginData = sharedPrefLoginData.edit();
                                    editorLoginData.putString("username", email);
                                    editorLoginData.putString("password", password);
                                    editorLoginData.putInt("matrikelnummer", jsonResponse.getInt("matrikelnummer"));
                                    editorLoginData.putString("surname", jsonResponse.getString("surname"));
                                    editorLoginData.putString("firstname", jsonResponse.getString("firstname"));
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


                                } else {

                                    findViewById(R.id.etEmail).startAnimation(shake);
                                    findViewById(R.id.etPassword).startAnimation(shake);
                                    Toast.makeText(LoginActivity.this, jsonResponse.getString("error_msg"), Toast.LENGTH_LONG).show();

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
