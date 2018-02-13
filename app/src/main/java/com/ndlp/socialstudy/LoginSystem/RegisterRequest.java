package com.ndlp.socialstudy.LoginSystem;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to call the PHP on the server
 */

public class RegisterRequest extends StringRequest {

    private static final String Register_Request_URL = "http://hellownero.de/SocialStudy/PHP-Dateien/Loginsystem/Register.php";
    private Map<String, String> params;

    //  Constructor
    //  if volley is finished with the request it calls the listener in RegisterActivity
    public RegisterRequest(String email, String password, int matrikelnummer, String firstName, String surname,Response.Listener<String> listener){
        super(Method.POST, Register_Request_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("matrikelnummer", matrikelnummer + "");
        params.put("firstName", firstName);
        params.put("surname", surname);

    }


    //  when the access is executed volley will call getparams()
    //  and it will return params above
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
