package com.ndlp.socialstudy.NavigationDrawer_BottomNavigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ndlp.socialstudy.LoginSystem.LoginActivity;
import com.ndlp.socialstudy.R;

import org.w3c.dom.Text;

public class MyAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        Button sendAccoundData = (Button) findViewById(R.id.b_buttonsendaccoundata);
        Button logoutbutton = (Button) findViewById(R.id.b_buttonLogout);
        TextView emailview = (TextView) findViewById(R.id.tv_accountemail);
        TextView matrikelnummerview = (TextView) findViewById(R.id.tv_accountmatrikelnummer);
        TextView nameview = (TextView) findViewById(R.id.tv_accountname);

        final String email, matrikelnummer, firstname, surname, password;

        SharedPreferences sharedPrefLoginData = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        email = sharedPrefLoginData.getString("username", "Error");
        matrikelnummer = sharedPrefLoginData.getInt("matrikelnummer", 0) + "";
        firstname = sharedPrefLoginData.getString("firstname","Error");
        surname = sharedPrefLoginData.getString("surname","Error");
        password = sharedPrefLoginData.getString("password","Error");

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

                Intent intent = new Intent(MyAccount.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        sendAccoundData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyAccount.this, "Your password is: " + password, Toast.LENGTH_LONG).show();
            }
        });


    }
}
