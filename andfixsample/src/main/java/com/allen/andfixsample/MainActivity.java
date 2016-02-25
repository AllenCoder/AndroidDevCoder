package com.allen.andfixsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private android.support.v7.widget.Toolbar toolbar;
    private EditText textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (EditText) findViewById(R.id.textView);
        Log.e(TAG, "savedInstanceState");
        Log.e(TAG, "" + "savedInstanceState");
        Log.e(TAG, "" + "savedInstanceState");
//        textView.setText("TestTest---------------------");
    }
}
