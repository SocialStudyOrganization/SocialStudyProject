package com.ndlp.socialstudy.LoginSystem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;



public class VergessenRequest extends StringRequest {

    private static final String LOGIN_Request_URL = "http://hellownero.de/SocialStudy/PHP-Dateien/PasswortVergessen.php";
    private Map<String, String> params;

    //  Constructor
    //  if volley is finished with the request it calls the listener in LoginActivity
    public VergessenRequest(String email,  Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_Request_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);

    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
