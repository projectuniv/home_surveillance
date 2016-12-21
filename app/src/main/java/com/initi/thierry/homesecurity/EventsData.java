package com.initi.thierry.homesecurity;

import android.provider.BaseColumns;

/**
 * Created by Thierry on 29/11/2016.
 */
public class EventsData {
    public EventsData(){}

    public static abstract class EventTable implements BaseColumns {
        public final static String DATABASE_NAME = "homesurv_db";
        public final static String TABLE_NAME = "events_table";
        public final static String ID = "_id";
        public final static String ID_CAPTEUR = "id_capteur";
        public final static String DATE = "date";
        public final static String IMAGE = "image";
        public final static String IMAGE_NAME = "image_name";
        public final static String USER_ID = "user_id";
    }
}
