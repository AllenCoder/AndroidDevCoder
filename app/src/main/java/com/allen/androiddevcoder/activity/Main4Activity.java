package com.allen.androiddevcoder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.allen.androiddevcoder.R;

import util.MLog;

public class Main4Activity extends AppCompatActivity {
    private String TAG ="lifeCycle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        MLog.d(TAG, "onCreate: ");
    }


    @Override
    protected void onRestart() {
        MLog.d(TAG, "onRestart: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        MLog.d(TAG, "onStart: ");

    }

    @Override
    protected void onResume() {
        super.onResume();
        MLog.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MLog.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        MLog.d(TAG, "onStop: ");

    }

}
