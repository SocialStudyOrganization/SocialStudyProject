package com.ndlp.socialstudy.LoginSystem;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
        Typeface quicksand_light = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Light.otf");

        //create BG gradient
        RelativeLayout rl_background = (RelativeLayout) findViewById(R.id.rl_background);
        ShapeDrawable.ShaderFactory shaderFactory = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                LinearGradient linearGradient = new LinearGradient(width, 0, width, height,
                        new int[] {
                                ContextCompat.getColor(RegisterActivity.this, R.color.bgg_hellblau),
                                ContextCompat.getColor(RegisterActivity.this, R.color.bgg_hellblau_alt),
                                ContextCompat.getColor(RegisterActivity.this, R.color.bgg_dunkelblau),
                                ContextCompat.getColor(RegisterActivity.this, R.color.bgg_dunkelgrau)
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
                                Toast.makeText(RegisterActivity.this, jsonResponse.getString("error_msg"), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }

                            //  if false build an AlertDialog
                            else {
                                Toast.makeText(RegisterActivity.this, jsonResponse.getString("error_msg"), Toast.LENGTH_LONG).show();
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
