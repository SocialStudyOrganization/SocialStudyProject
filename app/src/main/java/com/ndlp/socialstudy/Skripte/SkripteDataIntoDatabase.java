package com.ndlp.socialstudy.Skripte;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 *Class to handle the request to the server to upload detailled info of data to server
 */

public class SkripteDataIntoDatabase extends StringRequest {

    //  declare php location
    private static final String Register_Request_URL = "http://h2774251.stratoserver.net/PHP-Dateien/Skripteverwaltung/fileintodatabase.php";
    private Map<String, String> params;


    //  Constructor
    //  if volley is finished it calls the listener in SkripteActivity
    public SkripteDataIntoDatabase(String skriptname, String format, String category, String user, String kursid, String subfolder, Response.Listener<String> listener){
        super(Method.POST, Register_Request_URL, listener, null);
        params = new HashMap<>();
        params.put("filename", skriptname);
        params.put("format", format);
        params.put("category", category);
        params.put("user", user);
        params.put("kursid", kursid);
        params.put("subfolder", subfolder);
    }

    //  volley needs to access this data
    //  when the access is executed volley will call getparams()
    //  and it will return params above
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
