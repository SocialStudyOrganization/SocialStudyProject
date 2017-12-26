package com.ndlp.socialstudy.LoginSystem;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ndlp.socialstudy.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Organizes the Registration of a new User
 * uses RegisterRequest
 */


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Regular.otf");
        Typeface quicksand_bold = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Bold.otf");
        Typeface quicksand_bolditalic = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-BoldItalic.otf");
        Typeface quicksand_italic = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Italic.otf");
        Typeface quicksand_light = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Light.otf");
        Typeface quicksand_lightitalic = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-LightItalic.otf");

        //connect to xml widgets
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etMatrikelnummer = (EditText) findViewById(R.id.etMatrikelnummer);
        final EditText etFirstName = (EditText) findViewById(R.id.etFirstName);
        final EditText etSurname = (EditText) findViewById(R.id.etSurname);

        final Button bRegiser = (Button) findViewById(R.id.bRegister);

        //assign typefaces
        etEmail.setTypeface(quicksand_regular);
        etPassword.setTypeface(quicksand_regular);
        etMatrikelnummer.setTypeface(quicksand_regular);
        etFirstName.setTypeface(quicksand_regular);
        etSurname.setTypeface(quicksand_regular);
        bRegiser.setTypeface(quicksand_bold);

        //  transfer username and password toString
        bRegiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();
                final int matrikelnummer = Integer.parseInt(etMatrikelnummer.getText().toString());
                final String firstName = etFirstName.getText().toString();
                final String surname = etSurname.getText().toString();


                //listens for response from volley happening through RegisterRequest
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    //  this gets called on response
                    @Override
                    public void onResponse(String response) {

                        //  check for boolean success from php
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            //  if true from php start LoginActivity
                            if (success){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }

                            //  if false build an AlertDialog
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //  call register request and transfer string username and password
                RegisterRequest registerRequest = new RegisterRequest(email, password, matrikelnummer, firstName, surname, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
