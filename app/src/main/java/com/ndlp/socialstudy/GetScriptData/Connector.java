package com.ndlp.socialstudy.GetScriptData;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class to check the connection to the server
 */

public class Connector {

    public static Object connect(String urlAddress)
    {
        try
        {

            URL url= new URL(urlAddress);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            //SET CON PROPERTIES
            con.setRequestMethod("GET");
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            //Set the DoInput flag to true if you intend to use the
            //URL connection for input, false if not. The default is true.
            con.setDoInput(true);

            return con;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "Error "+e.getMessage();

        } catch (IOException e) {
            e.printStackTrace();
            return "Error "+e.getMessage();

        }
    }
}