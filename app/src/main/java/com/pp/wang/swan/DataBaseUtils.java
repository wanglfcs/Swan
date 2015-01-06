package com.pp.wang.swan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;

/**
 * Created by wang on 1/6/15.
 */
public class DataBaseUtils {
    static final String TAG = "DataBaseUtils";
    static final String DB_NAME = "swan.db";
    static final String DB_TABLE = "swan";
    static final String DB_ID = "_id";
    static final String DB_USER = "user";
    static final String DB_TIME = "time";
    static final String DB_CONTENT = "content";
    static final int DB_VERSION = 1;

    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public DataBaseUtils(Context context)
    {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public synchronized Cursor getHistoryContent()
    {
        db = dbHelper.getReadableDatabase();
        Cursor res = db.query(DB_TABLE, null, null, null, null, null, DB_TIME + " DESC");
        return res;
    }

    public synchronized long getLastMicroBlogTime()
    {
        db = dbHelper.getReadableDatabase();
        String[] column = {"max(" + DB_TIME + ")"};
        Cursor res = db.query(DB_TABLE, column, null, null, null, null, null);
        db.close();
        return res.moveToNext()? res.getLong(0): Long.MIN_VALUE;
    }

    public synchronized void updateDataBase()
    {
        TwitterUtils twitterUtils = new TwitterUtils();
        Twitter twitter = twitterUtils.getTwitter();
        List<Twitter.Status> timeline;
        db = dbHelper.getWritableDatabase();
        try{
            timeline = twitter.getFriendsTimeline();
            ContentValues values = new ContentValues();
            for (Twitter.Status status: timeline)
            {
                Log.d(TAG, "content " + status.getText());
                values.put(DB_ID, status.getId());
                values.put(DB_USER, status.getUser().getName());
                values.put(DB_TIME, status.getCreatedAt().getTime());
                values.put(DB_CONTENT, status.getText());
                db.insertWithOnConflict(DB_TABLE, null, values,SQLiteDatabase.CONFLICT_IGNORE);
            }

        }catch (TwitterException e)
        {
            Log.e(TAG, "Twitter error " + e.toString());
        }

    }

    class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + DB_TABLE + " (" + DB_ID + " int primary key," + DB_USER + " text, " + DB_TIME + " text," + DB_CONTENT + " text);");
            Log.d(TAG, "create databse");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
