package com.t.serialserver;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.t.serialserver.ISerialService;
import com.t.serialserver.ISerialCallback;

public class SerialService extends Service {
    private static final String TAG = "SerialService";

    private ISerialCallback mCallback = null;

    private static final int REPORT_MSG = 1;
    int mValue = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        HandlerThread handler_thread = new HandlerThread("handler_thread");
        handler_thread.start();
        MyHandler my_handler = new MyHandler(handler_thread.getLooper());
        my_handler.sendEmptyMessage(REPORT_MSG);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "SerialService starting", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStartCommand");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.d(TAG, "onBind");
        return binder;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
    }

    private final ISerialService.Stub binder = new ISerialService.Stub() {
        public int getPid() {
            return Process.myPid();
        }
        public int openSerial(ISerialCallback cb) {
            mCallback = cb;
            return 0;
        }
    };

    class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REPORT_MSG:
                    int value = mValue ++;
                    try {
                        if (mCallback != null)
                            mCallback.RespDataFromSerial(value);
                    } catch (RemoteException e) {
                        Log.d(TAG, "RespDataFromSerial failed: " + e);
                    }
                    sendMessageDelayed(obtainMessage(REPORT_MSG), 1*1000);
                    break;
            }
        }
    }

}
