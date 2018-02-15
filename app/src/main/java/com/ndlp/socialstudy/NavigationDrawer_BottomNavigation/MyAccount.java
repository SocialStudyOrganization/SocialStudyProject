package com.ndlp.socialstudy.NavigationDrawer_BottomNavigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ndlp.socialstudy.LoginSystem.LoginActivity;
import com.ndlp.socialstudy.R;

import org.w3c.dom.Text;

public class MyAccount extends AppCompatActivity {

    Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        //  set toolbar and adjust title
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("");

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Regular.otf");
        Typeface quicksand_bold = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Bold.otf");

        Button sendAccoundData = (Button) findViewById(R.id.b_buttonsendaccountdata);
        Button logoutbutton = (Button) findViewById(R.id.b_buttonLogout);
        Button changepassword = (Button) findViewById(R.id.b_changepassword);
        TextView emailview = (TextView) findViewById(R.id.tv_accountemail);
        TextView matrikelnummerview = (TextView) findViewById(R.id.tv_accountmatrikelnummer);
        TextView nameview = (TextView) findViewById(R.id.tv_accountname);
        TextView textView = (TextView) findViewById(R.id.tv_toolbar_title_workaround_account);

        sendAccoundData.setTypeface(quicksand_regular);
        logoutbutton.setTypeface(quicksand_regular);
        changepassword.setTypeface(quicksand_regular);
        emailview.setTypeface(quicksand_regular);
        matrikelnummerview.setTypeface(quicksand_regular);
        nameview.setTypeface(quicksand_regular);
        textView.setTypeface(quicksand_bold);

        final String email, matrikelnummer, firstname, surname;

        SharedPreferences sharedPrefLoginData = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        email = sharedPrefLoginData.getString("username", "Error");
        matrikelnummer = sharedPrefLoginData.getInt("matrikelnummer", 0) + "";
        firstname = sharedPrefLoginData.getString("firstname","Error");
        surname = sharedPrefLoginData.getString("surname","Error");

        emailview.setText(email);
        matrikelnummerview.setText(matrikelnummer);
        nameview.setText(firstname + " " + surname);



        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("rememberMe", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();

                MainActivity.fa.finish();

                Intent intent = new Intent(MyAccount.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        sendAccoundData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password;

                SharedPreferences sharedPrefLoginData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                password = sharedPrefLoginData.getString("password","");

                Toast.makeText(MyAccount.this, "Your password is: " + password, Toast.LENGTH_LONG).show();
            }
        });

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccount.this, ChangePasswordActivity.class);
                intent.putExtra("Matrikelnummer", matrikelnummer + "");
                startActivity(intent);
                finish();
            }
        });


    }
}
