package com.initi.thierry.homesecurity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.initi.thierry.homesecurity.EventsData.EventTable;

/**
 * Created by Thierry on 29/11/2016.
 */
public class DatabaseOperations extends SQLiteOpenHelper {
    public final static int databaseVersion = 1;
    public String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS" + EventTable.TABLE_NAME +
            "("+ EventTable.ID + "TEXT, "+ EventTable.ID_CAPTEUR+ "TEXT, "+ EventTable.IMAGE_NAME + " TEXT, " +
            EventTable.IMAGE + " BLOB, "+ EventTable.DATE + " TEXT, "+ EventTable.USER_ID+ " TEXT, "+ "PRIMARY KEY ("+EventTable.ID+") );";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d("Database Operation ", " Table created");
    }

    public DatabaseOperations(Context context) {
        super(context, EventTable.DATABASE_NAME, null, databaseVersion);
        Log.d("Database Operation ", " Database created");
    }

    public void insertEvent(DatabaseOperations dop, String id, String idCapteur, byte [] image ,String imageName, String date, String userId){
        SQLiteDatabase sqlDB = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EventTable.ID, id);
        cv.put(EventTable.ID_CAPTEUR, idCapteur);
        cv.put(EventTable.IMAGE, image);
        cv.put(EventTable.IMAGE_NAME, imageName);
        cv.put(EventTable.DATE, date);
        cv.put(EventTable.USER_ID, userId);

        long k = sqlDB.insert(EventTable.TABLE_NAME, null,cv);
        Log.d("Database Operation "," One row inserted");
        //Toast.makeText(this, "One row inserted", Toast.).show();
    }

    public Cursor fecthEvent(DatabaseOperations dop, String userId){
        SQLiteDatabase sqlDB = dop.getReadableDatabase();
        String []columns = {EventTable.ID, EventTable.ID_CAPTEUR, EventTable.IMAGE, EventTable.IMAGE_NAME, EventTable.DATE, EventTable.USER_ID};
        Cursor c = sqlDB.query(EventTable.TABLE_NAME, columns, null, null, null, null, null);
        /*String query = "SELECT * FROM "+EventTable.TABLE_NAME+" where "+EventTable.USER_ID+ " = "+ userId;
        sqlDB.execSQL(query);*/
        Log.d("Database Operation "," Data has been fetched");
        return c;
    }
    public void deleteAllEvent(DatabaseOperations dop, String userId){
        SQLiteDatabase sqlDB = dop.getWritableDatabase();
        String query = "DELETE FROM "+EventTable.TABLE_NAME+" WHERE "+EventTable.USER_ID+ " = "+userId+" ;";
        sqlDB.execSQL(query);
        Log.d("Database Operation "," All data has been deleted");
    }

    public void deleteEventById(DatabaseOperations dop, String eventId){
        SQLiteDatabase sqlDB = dop.getWritableDatabase();
        String query =  "DELETE FROM "+EventTable.TABLE_NAME+" WHERE "+EventTable.ID + " = "+eventId+" ;";
        sqlDB.execSQL(query);
        Log.d("Database Operation ","Event with id "+ eventId+ " has been deleted");
    }

    public void updateDataEvent(){}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
