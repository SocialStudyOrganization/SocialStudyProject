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
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
    TextView tv_desc;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vergessen_screen);

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
                                ContextCompat.getColor(VergessenScreen.this, R.color.bgg_hellblau),
                                ContextCompat.getColor(VergessenScreen.this, R.color.bgg_hellblau_alt),
                                ContextCompat.getColor(VergessenScreen.this, R.color.bgg_dunkelblau),
                                ContextCompat.getColor(VergessenScreen.this, R.color.bgg_dunkelgrau)
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

        b_abbrechen = (Button) findViewById(R.id.b_passwortabbrechen);
        b_absenden = (Button) findViewById(R.id.b_passwortabsenden);
        et_email = (EditText) findViewById(R.id.et_emailvergessen);
        tv_desc = (TextView) findViewById(R.id.tv_pwvergessendesc);

        b_abbrechen.setTypeface(quicksand_light);
        b_absenden.setTypeface(quicksand_bold);
        et_email.setTypeface(quicksand_regular);
        tv_desc.setTypeface(quicksand_regular);


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
