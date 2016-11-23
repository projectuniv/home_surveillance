package com.initi.thierry.homesecurity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener{
    private Button signupBt, loginBt;
    private EditText unameField, passField;
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
        if(uname.isEmpty() ||pass.isEmpty())
            Log.e("LOGIN_ERROR", "Les champs sont vides");
        else{
            communication.login(uname, pass);
        }

    }
    public void signupUser(){
        String uname = unameField.getText().toString();
        String pass = passField.getText().toString();
        if(uname.isEmpty() ||pass.isEmpty())
            Log.e("SIGNUP_ERROR", "Les champs sont vides");
        else{
            communication.signup(uname, pass);
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
