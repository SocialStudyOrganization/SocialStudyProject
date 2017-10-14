package com.ndlp.socialstudy.LoginSystem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to commit the RegisterRequest on the server
 */

public class LoginRequest extends StringRequest{

    private static final String LOGIN_Request_URL = "http://hellownero.de/Loginskripte/Login.php";
    private Map<String, String> params;

    //  Constructor
    //  if volley is finished with the request it calls the listener in LoginActivity
    public LoginRequest(String username, String password, Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_Request_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }

    //volley needs to access this data
    //when the access is executed volley will call getparams()
    //and it will return params above
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
