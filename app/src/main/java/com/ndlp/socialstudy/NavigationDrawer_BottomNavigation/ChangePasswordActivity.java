package com.ndlp.socialstudy.NavigationDrawer_BottomNavigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ndlp.socialstudy.GeneralFileFolder.RefreshfromDatabase;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.Umfragen.UmfrageErstellen.NewUmfrageActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

public class ChangePasswordActivity extends AppCompatActivity {

    String oldpassword, newpassword1, newpassword2, matrikelnummer;

    String urladdress = "http://hellownero.de/SocialStudy/PHP-Dateien/Loginsystem/ChangePassword.php";

    Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        final Intent intent = getIntent();
        matrikelnummer = intent.getStringExtra("Matrikelnummer");

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Regular.otf");
        Typeface quicksand_bold = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Bold.otf");

        //  set toolbar and adjust title
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("");


        Button b_changepassword = (Button) findViewById(R.id.b_sendnewpassword);
        final EditText et_oldpassword = (EditText) findViewById(R.id.et_oldpassword);
        final EditText et_newpassword1 = (EditText) findViewById(R.id.et_newpassword1);
        final EditText et_newpassword2 = (EditText) findViewById(R.id.et_newpassword2);
        TextView textView = (TextView) findViewById(R.id.tv_toolbar_title_workaround_changepw);
        textView.setTypeface(quicksand_bold);

        b_changepassword.setTypeface(quicksand_regular);
        et_oldpassword.setTypeface(quicksand_regular);
        et_newpassword1.setTypeface(quicksand_regular);
        et_newpassword2.setTypeface(quicksand_regular);



        b_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_oldpassword.equals("") || et_newpassword1.equals("") || et_newpassword2.equals("")){
                    Toast.makeText(ChangePasswordActivity.this, "Make your Inputs complete", Toast.LENGTH_LONG).show();
                } else {

                    oldpassword = et_oldpassword.getText().toString();
                    newpassword1 = et_newpassword1.getText().toString();
                    newpassword2 = et_newpassword2.getText().toString();

                    if (!newpassword1.equals(newpassword2)){
                        Toast.makeText(ChangePasswordActivity.this, "Die neuen Passwörter stimmen nicht überein", Toast.LENGTH_LONG).show();
                    }
                    else{

                        StringRequest request = new StringRequest(Request.Method.POST, urladdress,

                                new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String response) {

                                        try {
                                            Log.i("tagconvertstr", response);
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");


                                            if (success) {
                                                Toast.makeText(ChangePasswordActivity.this, jsonResponse.getString("error_msg"), LENGTH_LONG).show();

                                                SharedPreferences sharedPrefLoginData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editorLoginData = sharedPrefLoginData.edit();
                                                editorLoginData.putString("password", newpassword2);
                                                editorLoginData.apply();

                                                Intent intent1 = new Intent(ChangePasswordActivity.this, MyAccount.class);
                                                ChangePasswordActivity.this.startActivity(intent1);
                                                finish();

                                            } else {

                                                Toast.makeText(ChangePasswordActivity.this, jsonResponse.getString("error_msg"), LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            Log.e(RefreshfromDatabase.class.getSimpleName(), e.getMessage());
                                        }
                                    }
                                },

                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Toast.makeText(ChangePasswordActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                                    }
                                }){

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String, String> params = new HashMap<>();
                                params.put("matrikelnummer", matrikelnummer);
                                params.put("newpassword", newpassword1);
                                params.put("oldpassword", oldpassword);

                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(ChangePasswordActivity.this);
                        requestQueue.add(request);

                    }
                }

            }
        });
    }
}
