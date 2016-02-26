package com.allen.andfixsample;

import android.app.Application;

import com.alipay.euler.andfix.patch.PatchManager;

/**
 * 作者: allen on 16/2/25.
 */
public class MainApplication extends Application {
    private static final String TAG = "euler";


    private static final String APATCH_PATH = "/out.apatch";
    /**
     * patch manager
     */
    private PatchManager mPatchManager;


    @Override
    public void onCreate() {
        super.onCreate();
        // initialize
//        mPatchManager = new PatchManager(this);
//        mPatchManager.init("1.0");
//        Log.d(TAG, "inited.");
//
//
//        // load patch
//        mPatchManager.loadPatch();
//        Log.d(TAG, "apatch loaded.");


        // add patch at runtime
//        try {
//            // .apatch file path
//            String patchFileString = Environment.getExternalStorageDirectory()
//                    .getAbsolutePath() + APATCH_PATH;
//            mPatchManager.addPatch(patchFileString);
//            Log.d(TAG, "apatch:" + patchFileString + " added.");
//        } catch (IOException e) {
//            Log.e(TAG, "", e);
//        }


    }

}
