package com.allen.androiddevcoder;

import com.facebook.react.ReactActivity;

/**
 * 作者: allen on 16/7/31.
 */

public class LiveActivity extends ReactActivity {
    @Override
    protected String getMainComponentName() {
        return "react-native-module";
    }
    @Override
    protected boolean getUseDeveloperSupport() {
        return BuildConfig.DEBUG;
    }



}
