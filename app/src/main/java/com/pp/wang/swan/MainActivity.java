package com.pp.wang.swan;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";
    String[] FROM = {DataBaseUtils.DB_USER, DataBaseUtils.DB_TIME, DataBaseUtils.DB_CONTENT};
    int [] TO = {R.id.itemUser, R.id.itemTime, R.id.itemContent};
    DataBaseUtils dataBaseUtils;
    SimpleCursorAdapter adapter;
    ListView listTimeline;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listTimeline = (ListView)findViewById(R.id.listTimeline);
        dataBaseUtils = new DataBaseUtils(getApplicationContext());
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        cursor = dataBaseUtils.getHistoryContent();
        adapter = new SimpleCursorAdapter(this, R.layout.microblogitem, cursor, FROM, TO);
        adapter.setViewBinder(VIEW_BINDER);
        listTimeline.setAdapter(adapter);
    }

    static final SimpleCursorAdapter.ViewBinder VIEW_BINDER = new SimpleCursorAdapter.ViewBinder() {
        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            if (view.getId() != R.id.itemTime)
                return false;
            else{
                long timeNum = cursor.getLong(cursor.getColumnIndex(DataBaseUtils.DB_TIME));
                CharSequence time = DateUtils.getRelativeTimeSpanString(timeNum);
                ((TextView) view).setText(time);
                return true;
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.setting_startService:
                startService(new Intent(this, UpdateServer.class));
                break;
            case R.id.setting_stopService:
                stopService(new Intent(this, UpdateServer.class));
                break;
            case R.id.setting_postStatus:
                startActivity(new Intent(this, PostStatusActivity.class));
                break;
        }
        return true;
    }
}
