package com.ndlp.socialstudy.LoginSystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.ndlp.socialstudy.NavigationDrawer_BottomNavigation.MainActivity;
import com.ndlp.socialstudy.R;

import org.json.JSONException;
import org.json.JSONObject;

public class VergessenScreen extends AppCompatActivity {

    Button b_absenden, b_abbrechen;
    EditText et_email;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vergessen_screen);

        b_abbrechen = (Button) findViewById(R.id.b_passwortabbrechen);
        b_absenden = (Button) findViewById(R.id.b_passwortabsenden);
        et_email = (EditText) findViewById(R.id.et_emailvergessen);

        b_absenden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Animation shake = AnimationUtils.loadAnimation(VergessenScreen.this, R.anim.shake);


                if (et_email.getText().toString().equals("")){
                    Toast.makeText(VergessenScreen.this, "Please enter your Email", Toast.LENGTH_LONG).show();
                }else {
                    email = et_email.getText().toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //  gets called if a response is transmitted
                            try {
                                Log.i("tagconvertstr", response);

                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {

                                    BackgroundMail.newBuilder(VergessenScreen.this)
                                            .withUsername("hellownero@gmail.com")
                                            .withPassword("Nadipat1")
                                            .withMailTo(email)
                                            .withType(BackgroundMail.TYPE_PLAIN)
                                            .withSubject("Your Social Study Password")
                                            .withBody(jsonResponse.getString("passwort"))
                                            .withProcessVisibility(false)
                                            .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                                                @Override
                                                public void onSuccess() {
                                                    Toast.makeText(VergessenScreen.this, "Look up your Email", Toast.LENGTH_LONG).show();
                                                    finish();
                                                }
                                            })
                                            .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                                                @Override
                                                public void onFail() {
                                                    Toast.makeText(VergessenScreen.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                                                }
                                            })
                                            .send();


                                } else {

                                    findViewById(R.id.et_emailvergessen).startAnimation(shake);
                                    Toast.makeText(VergessenScreen.this, jsonResponse.getString("error_msg"), Toast.LENGTH_LONG).show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    //  call class LoginRequest
                    VergessenRequest vergessenRequest = new VergessenRequest(email, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(VergessenScreen.this);
                    queue.add(vergessenRequest);

                }

            }
        });

        b_abbrechen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
