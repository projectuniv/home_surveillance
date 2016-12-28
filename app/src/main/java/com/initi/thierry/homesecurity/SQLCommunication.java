package com.initi.thierry.homesecurity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thierry on 18/11/2016.
 */
public class SQLCommunication {
    private String loginURL = "http://homesurveillance.esy.es/php/login_user.php";
    private String signupURL = "http://homesurveillance.esy.es/php/signup_user.php";
    private String fetchDataURL = "http://homesurveillance.esy.es/php/fetch_home_surv.php";
    private String fetchImageURL = "http://homesurveillance.esy.es/php/fetch_image.php";
    private ArrayList<Bitmap>bitmapArray = new ArrayList<Bitmap>();
    private ArrayList <UserData> userDataArrayList = new ArrayList<UserData>();
    RequestQueue reqQueue ;
    String reqLastResponse = "";


    public SQLCommunication(Context context){
        reqQueue = Volley.newRequestQueue(context);
    }
    public String login(String username, String password){
        connect(username, password, loginURL);
        String repTmp = reqLastResponse;
        reqLastResponse = "";
        return repTmp;
    }
    public String signup(String username, String password){
        connect(username, password, signupURL);
        String repTmp = reqLastResponse;
        reqLastResponse = "";
        return repTmp;
    }

    public void connect(final String username, final String password, String url){
        System.out.println("************* LOGIN IN **************");
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                System.out.println("RESPONSE value "+ response);
                try {
                    JSONObject obj  = new JSONObject(response);
                    String finalResp = obj.getString("result");
                    reqLastResponse = response;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("********* Error de connection ("+ error.getMessage()+"). Vérifier que vous etes connecte a l'internet ********* " );}
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

    public void imageRequest(String userName){
        ImageRequest ir = new ImageRequest(fetchImageURL, new Response.Listener<Bitmap>() {
            Bitmap img;

            @Override
            public void onResponse(Bitmap response) {
                System.out.println("*************ON RESPONSE IMAGE **************");
                img = response;
                bitmapArray.add(response);
                //iv.setImageBitmap(response);
            }
        }, 0, 0, null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("*************ERROR FETCHING IMAGE**************");

            }
        });

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
                        System.out.println("*************FECHING EVENT **************");
                        JSONObject event = dbEvents.getJSONObject(i);

                        String id = event.getString("id");
                        String userId = event.getString("user_id");
                        String idCapteur = event.getString("id_capteur");
                        String date = event.getString("date");
                        String imageName = event.getString("image_name");
                        Log.d("FETCHED","id : " + id + " capteur : " + idCapteur + " date :" + date +" userid : "+ userId+ " imgName: "+imageName);
                        //byte[] imgByte  = new byte[1500];//= Base64.decode(event.getString("image"), Base64.DEFAULT) ;
                        //Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);

                        //insertion dans l'arraylist
                        userDataArrayList.add(new UserData(id, userId, idCapteur, null, imageName, date ));
                    }
                    if(userDataArrayList.isEmpty()) Log.e("Array data", "Empty "+userDataArrayList.size());
                    else{
                        Log.d("Array data", "Not empty "+userDataArrayList.size());
                        printUserDataArrayList();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Fetching Error : ", e.getMessage());
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error :" + error.getMessage() + " => "+error.toString());

            }
        });
        reqQueue.add(jsonObject);

    }

    public ArrayList <UserData> getUserDataArrayList(){
        return this.userDataArrayList;
    }
    public void printUserDataArrayList(){
        for(int i=0; i< userDataArrayList.size(); i++){
            UserData u = userDataArrayList.get(i);
            System.out.println(u.getId() + " - " + u.getUserId() + " - " + u.getIdCapteur() + " - "+u.getImageName());
        }
    }
}
