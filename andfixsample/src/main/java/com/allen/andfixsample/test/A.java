package com.allen.andfixsample.test;

import android.util.Log;

/**
 * 作者: allen on 16/2/25.
 */
public class A {

    String s = "s";
    private static O o = new O("a");
    static int i = 10;

    public static String a(String str) {
        Log.i("euler", "fix error");
        return "a";
    }

    public int b(String s1, String s2) {
        Log.i("euler", "fix error");
        Log.i("euler", o.s);
        return 0;
    }

    public int getI() {
        return i;
    }
}
