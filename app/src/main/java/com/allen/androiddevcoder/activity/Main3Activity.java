package com.allen.androiddevcoder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.allen.androiddevcoder.R;

import util.MLog;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        findViewById(R.id.ly_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MLog.d("R.id.ly_1");
            }
        });
        findViewById(R.id.ly_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MLog.d("R.id.ly_2");
            }
        });
        findViewById(R.id.ly_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MLog.d("R.id.ly_top");
            }
        });
        findViewById(R.id.tv11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MLog.d("R.id.tv11");
            }
        });
        findViewById(R.id.tv12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MLog.d("R.id.tv12");
            }
        });
        findViewById(R.id.tv13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MLog.d("R.id.tv13");
            }
        });
        findViewById(R.id.tv14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MLog.d("R.id.tv14");
            }
        });
        findViewById(R.id.tv21).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MLog.d("R.id.tv21");
            }
        });
        findViewById(R.id.tv22).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MLog.d("R.id.tv22");
            }
        });
        findViewById(R.id.tv23).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MLog.d("R.id.tv23");
            }
        });
        findViewById(R.id.tv24).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MLog.d("R.id.tv24");
            }
        });
    }
}
