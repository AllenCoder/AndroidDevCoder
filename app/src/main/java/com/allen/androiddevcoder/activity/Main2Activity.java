package com.allen.androiddevcoder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alipay.euler.andfix.patch.PatchManager;
import com.allen.androiddevcoder.R;

public class Main2Activity extends AppCompatActivity {
    PatchManager patchManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        patchManager = new PatchManager(this);

    }
}
