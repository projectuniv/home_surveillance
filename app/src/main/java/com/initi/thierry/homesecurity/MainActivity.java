package com.initi.thierry.homesecurity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Thierry et Idriss on 18/11/2016.
 */

public class MainActivity extends Activity implements View.OnClickListener{
    private Button signupBt, loginBt;
    private EditText unameField, passField;
    private TextView messageText;
    private SQLCommunication communication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init components
        signupBt = (Button)findViewById(R.id.signUpButton);
        loginBt = (Button) findViewById(R.id.loginButton);
        unameField = (EditText) findViewById(R.id.uname_text);
        passField = (EditText) findViewById(R.id.pass_text);
        messageText = (TextView) findViewById(R.id.messageText);

        //init listeners
        signupBt.setOnClickListener(this);
        loginBt.setOnClickListener(this);

        //init SQL communication
        communication = new SQLCommunication(this);
    }
    @Override
    protected void onResume() {super.onResume();}
    @Override
    protected void onPause() {super.onPause();}
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void loginUser(){
        String uname = unameField.getText().toString();
        String pass = passField.getText().toString();
        String resp =  communication.login(uname, pass);
        if(resp.equals("{\"result\":[1]}")){
            System.out.println("Successful login");
            Intent userHomeIntent = new Intent(MainActivity.this, UserHomeActivity.class);
            userHomeIntent.putExtra("uname", uname);
            MainActivity.this.startActivity(userHomeIntent);
        }
        else{
            System.out.println("Login fail = " +resp);
            messageText.setText("Username ou password incorrect. Veuillez réessayer ou créer un compte");
        }

    }
    public void signupUser(){
        String uname = unameField.getText().toString();
        String pass = passField.getText().toString();
        if(uname.isEmpty() ||pass.isEmpty())
            Log.e("SIGNUP_ERROR", "Les champs sont vides");
        else{
            communication.signup(uname, pass);
            messageText.setText("Username ou password incorrect. Veuillez réessayer ou créer un compte");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signUpButton:
                signupUser();
                break;
            case R.id.loginButton:
                loginUser();
                break;
            default:
                break;
        }

    }
}
