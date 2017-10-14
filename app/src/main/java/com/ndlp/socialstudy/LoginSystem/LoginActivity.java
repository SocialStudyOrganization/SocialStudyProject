package com.ndlp.socialstudy.LoginSystem;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

        //  connect to the xml widgets
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
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
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);

                //  when sth is not filled show toast
                if (username.matches("") || password.matches("")) {
                    Toast.makeText(LoginActivity.this, "Fill in your Username and Password", Toast.LENGTH_LONG).show();

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
                                    editorLoginData.putString("username", username);
                                    editorLoginData.putString("password", password);
                                    editorLoginData.apply();

                                    //  check if rememberMe is checked and put it in sharedPrefrememberMe
                                    if (rememberMe.isChecked()) {
                                        SharedPreferences sharedPrefrememberMe = getSharedPreferences("rememberMe", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editorrememberMe = sharedPrefrememberMe.edit();
                                        editorrememberMe.putString("username", username);
                                        editorrememberMe.apply();
                                    }

                                    //  start MainActivity
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    LoginActivity.this.startActivity(intent);
                                    finish();

                                //  if false give toast
                                } else {

                                    findViewById(R.id.etUsername).startAnimation(shake);
                                    findViewById(R.id.etPassword).startAnimation(shake);
                                    Toast.makeText(LoginActivity.this, "Wrong Username or Password", Toast.LENGTH_LONG).show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    //  call class LoginRequest
                    LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }


            }
        });
    }


}
