package com.ndlp.socialstudy.Answers;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class AnswersRequest extends StringRequest {

    //  declare php location
    private static final String Register_Request_URL = "http://hellownero.de/SocialStudy/PHP-Dateien/AnswerRequest.php";
    private Map<String, String> params;

    //  Constructor
    //  if volley is finished it calls the listener in Activity
    public AnswersRequest(String answername, String format, String category, String date, String time, String user, Response.Listener<String> listener){
        super(Request.Method.POST, Register_Request_URL, listener, null);
        params = new HashMap<>();
        params.put("answername", answername);
        params.put("format", format);
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
