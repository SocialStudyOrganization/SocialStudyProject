package com.ndlp.socialstudy.LoginSystem;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


import java.util.HashMap;
import java.util.Map;

/**
 * Class to call the PHP on the server
 */

public class RegisterRequest extends StringRequest {

    private static final String Register_Request_URL = "http://hellownero.de/SocialStudy/PHP-Dateien/Register.php";
    private Map<String, String> params;

    //  Constructor
    //  if volley is finished with the request it calls the listener in RegisterActivity
    public RegisterRequest(String username, String password, Response.Listener<String> listener){
        super(Method.POST, Register_Request_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }


    //  when the access is executed volley will call getparams()
    //  and it will return params above
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
