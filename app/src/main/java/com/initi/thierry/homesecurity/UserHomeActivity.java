package com.initi.thierry.homesecurity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserHomeActivity extends Activity implements View.OnClickListener{
    private String userID;
    private TextView unameField;
    private Button updateBt, deleteAllBt, graphBt;
    private ListView eventListView;
    private List<UserData> userData = new ArrayList<UserData>();

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

        //Listeners
        updateBt.setOnClickListener(this);
        deleteAllBt.setOnClickListener(this);
        graphBt.setOnClickListener(this);

        onItemClickedAction();
    }
    private void onItemClickedAction(){
        ListView listView = (ListView) findViewById(R.id.eventsListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserData udata = userData.get(position);
                String message = "Position : " + position + " idCapteur : " + udata.getIdCapteur() + " Date : " + udata.getDate();
                Toast.makeText(UserHomeActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
    public void setUserID(String userID){this.userID = userID;}
    public String getUserID(){return userID;}

    public void poulateListView(){
        SQLCommunication com = new SQLCommunication(this);
        com.fetchSiteDatabaseEvents();
        userData.clear();
        userData = com.getUserDataArrayList();
        if(userData.isEmpty()) Log.e("User Data", "empty array list");

        ArrayAdapter <UserData> adapter = new EventListAdapter();
        eventListView = (ListView) findViewById(R.id.eventsListView);
        eventListView.setAdapter(adapter);
    }

    public void initView(){
        byte []byt = new byte[100];
        userData.add(new UserData("1","2", "capt1", byt, "imagename", "2015--01-12",  "image0"));
        userData.add(new UserData("1","2", "capt2", byt, "new", "2015--01-12",  "image0"));
        userData.add(new UserData("1", "2", "capt3", byt, "test", "2015--01-12", "image0"));

        ///populate
        ArrayAdapter <UserData> adapter = new EventListAdapter();
        eventListView = (ListView) findViewById(R.id.eventsListView);
        eventListView.setAdapter(adapter);
    }

    public void updateUserData(){

    }


    public void getUserEvents(){}
    public void deleteAllUserData(){}
    public void updateUserInfo(){};
    public void deleteDataByID(){}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.graphBt:
                SQLCommunication com = new SQLCommunication(this);
                com.imageRequest(null);
                break;
            case R.id.deleteAllBt:
                poulateListView();
                break;
            case R.id.updateBt:
                //updateUserData();
                initView();
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
            //byte [] imgByte = uData.getImageBytes();
            //Bitmap bmp1 = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            //imageView.setImageBitmap(bmp1);
            imageView.setImageResource(R.drawable.user_icon);

            TextView dateView = (TextView)addView.findViewById(R.id.dateView);
            dateView.setText(uData.getDate());

            TextView capteurView = (TextView)addView.findViewById(R.id.idcpteur_view);
            capteurView.setText(uData.getIdCapteur());
            System.out.println("*** Added to view ***");

            return addView;
        }

    }



}
