package com.pp.wang.swan;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdateServer extends Service {
    private static final String TAG = "UpDateServer";
    private static final int DELAY = 10000;
    private boolean running = false;
    private DataBaseUtils dataBaseUtils;
    private UpdateThread updateThread;
    public UpdateServer() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "on start service");
        dataBaseUtils = new DataBaseUtils(getApplicationContext());
        UpdateThread updateThread = new UpdateThread();
        running = true;
        updateThread.start();
        return 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "service on destroy");
        running = false;
    }

    class UpdateThread extends Thread{
        @Override
        public void run() {
            while (running)
            {
                Log.v(TAG, "service is running");
                try {
                    dataBaseUtils.updateDataBase();
                    Thread.sleep(DELAY);

                }catch (Exception e){
                    Log.e(TAG, e.toString());
                }
            }
        }
    }
}
