package com.initi.thierry.homesecurity;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Thierry on 21/12/2016.
 */
public class HttpCommunication {
    private String loginURL = "http://homesurveillance.esy.es/php/login_user.php";
    private String signupURL = "http://homesurveillance.esy.es/php/signup_user.php";
    private String fetchDataURL = "http://homesurveillance.esy.es/php/fetch_home_surv.php";

    public HttpCommunication(){

    }

    public void login(String uName, String uPwd){
        new ThreadTask().execute(loginURL);
    }

    private class ThreadTask extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                /*connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                OutputStream os = connection.getOutputStream();*/

                connection.connect();

                InputStream input = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(input));

                StringBuffer buffer = new StringBuffer();
                String line ="";
                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                String jsonString = buffer.toString();
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray result = jsonObject.getJSONArray("result");
                
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) connection.disconnect();
                try {
                    if(reader != null) reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
