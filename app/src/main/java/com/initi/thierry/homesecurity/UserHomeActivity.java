package com.initi.thierry.homesecurity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thierry et Idriss on 18/11/2016.
 */

public class UserHomeActivity extends Activity implements View.OnClickListener{
    private String userID;
    private TextView unameField;
    private Button updateBt, deleteAllBt, graphBt;
    private ListView eventListView;
    private List<UserData> userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        Intent intent = getIntent();
        String uname = intent.getStringExtra("uname");
        setUserID(uname);

        //init
        unameField = (TextView) findViewById(R.id.unameField);
        unameField.setText(uname);
        //init components
        updateBt = (Button) findViewById(R.id.updateBt);
        deleteAllBt = (Button) findViewById(R.id.deleteAllBt);
        graphBt = (Button) findViewById(R.id.graphBt);
        eventListView = (ListView) findViewById(R.id.eventsListView);
        //Listeners
        updateBt.setOnClickListener(this);
        deleteAllBt.setOnClickListener(this);
        graphBt.setOnClickListener(this);
    }
    public void setUserID(String userID){this.userID = userID;}
    public String getUserID(){return userID;}

    public void updateUserData(){
        /*SQLCommunication com = new SQLCommunication(this);
        com.fetchSiteDatabaseEvents();
        userData= com.getUserDataArrayList();
        byte [] imgByte = userData.get(0).getImageBytes();
        Bitmap bmp1 = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);*/

        //eventListView.setAdapter(new Lis);
       // eventListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, imgByte));
    }
    public void poulateListView(){
        SQLCommunication com = new SQLCommunication(this);
        com.fetchSiteDatabaseEvents();
        userData = new ArrayList<UserData>();
        userData = com.getUserDataArrayList();
        if(userData.isEmpty()) Log.e("User Data", "empty array list");

        ArrayAdapter <UserData> adapter = new EventListAdapter();
        eventListView.setAdapter(adapter);
    }

    public void getUserEvents(){}
    public void deleteAllUserData(){}
    public void updateUserInfo(){};
    public void deleteDataByID(){}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.graphBt:
                break;
            case R.id.deleteAllBt:
                break;
            case R.id.updateBt:
                updateUserData();
                poulateListView();
                break;
        }
    }

    private class EventListAdapter extends ArrayAdapter <UserData> {
        public EventListAdapter(){
            super(UserHomeActivity.this, R.layout.events_item, userData);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View addView = convertView;
            if(addView == null)
                addView = getLayoutInflater().inflate(R.layout.events_item, parent, false);

            UserData uData = userData.get(position);

            ImageView imageView = (ImageView)addView.findViewById(R.id.photo_view);
            byte [] imgByte = uData.getImageBytes();
            Bitmap bmp1 = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            imageView.setImageBitmap(bmp1);

            TextView dateView = (TextView)addView.findViewById(R.id.dateView);
            dateView.setText(uData.getDate());

            return super.getView(position, convertView, parent);
        }
    }
}
