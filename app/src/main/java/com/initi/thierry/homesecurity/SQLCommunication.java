package com.initi.thierry.homesecurity;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thierry on 18/11/2016.
 */
public class SQLCommunication {
    private String loginURL = "http://initiapps.esy.es/php/login_user.php";
    private String signupURL = "http://initiapps.esy.es/php/signup_user.php";
    private String fetchDataURL = "http://initiapps.esy.es/php/fetch_home_surv.php";
    RequestQueue reqQueue ;

    public SQLCommunication(Context context){
        reqQueue = Volley.newRequestQueue(context);
    }

    public void login(String username, String password){
        connect(username, password, loginURL);
    }
    public void signup(String username, String password){
        connect(username, password, signupURL);
    }

    public void connect(final String username, final String password, String url){
        System.out.println("************* LOGIN IN **************");
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {}
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) { System.out.println("********* Error **** "+ error.getMessage() );}
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        reqQueue.add(req);
    }

    public void fetchSiteDatabaseEvents(){
        System.out.println("*************FECHING DATA **************");
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, fetchDataURL, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("*************ON RESPONSE**************");
                try {
                    JSONArray dbEvents = response.getJSONArray("result");
                    for(int i = 0; i < dbEvents.length(); i++){
                        System.out.println("*************FECHING CONTACTS **************");
                        JSONObject event = dbEvents.getJSONObject(i);
                        String id = event.getString("id");
                        String idCapteur = event.getString("id_capteur");
                        String date = event.getString("date");
                        String userId = event.getString("user_id");
                        //image
                        System.out.println("id : " + id + " capteur : " + idCapteur + " date :" + date);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error :" + error.getMessage());

            }
        });
        reqQueue.add(jsonObject);
    }
}
