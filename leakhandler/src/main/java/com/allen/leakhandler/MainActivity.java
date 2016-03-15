package com.allen.leakhandler;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements WeakRefHander.Callback {
    private WeakRefHander weakRefHander;
    private static final int HANDLER_MESSAGE_START = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weakRefHander = new WeakRefHander(this, 1);
        weakRefHander.start(HANDLER_MESSAGE_START, 1000 * 30);

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HANDLER_MESSAGE_START:
                //Todo 处理业务逻辑
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public void onResume() {
        super.onResume();
        weakRefHander.start();
    }

    @Override
    public void onPause() {
        weakRefHander.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        weakRefHander.clear();
        super.onDestroy();
    }
}
