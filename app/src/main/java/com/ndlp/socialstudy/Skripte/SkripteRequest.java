package com.ndlp.socialstudy.Skripte;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 *Class to handle the request to the server to upload detailled info of data to server
 */

public class SkripteRequest extends StringRequest {

    //  declare php location
    private static final String Register_Request_URL = "http://hellownero.de/SocialStudy/PHP-Dateien/InformatikskripteRequest.php";
    private Map<String, String> params;

    //  Constructor
    //  if volley is finished it calls the listener in SkripteActivity
    public SkripteRequest(String skriptname, String category, String date, String time, String user, Response.Listener<String> listener){
        super(Method.POST, Register_Request_URL, listener, null);
        params = new HashMap<>();
        params.put("skriptname", skriptname);
        params.put("category", category);
        params.put("date", date);
        params.put("time", time);
        params.put("user", user);

    }

    //  volley needs to access this data
    //  when the access is executed volley will call getparams()
    //  and it will return params above
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
