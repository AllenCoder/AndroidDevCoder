package com.allen.andfixsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;

import com.allen.andfixsample.test.A;
import com.allen.andfixsample.test.Fix;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private android.support.v7.widget.Toolbar toolbar;
    private EditText textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, Fix.a("good"));
        Log.e(TAG, "" + new Fix().b("s1", "s2"));
        Log.e(TAG, "" + new A().getI());
//        textView.setText("TestTest---------------------");
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_POWER || keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_SEARCH  )
        {

            Toast.makeText(getApplicationContext(),"You pressed a control button with keyCode : " + keyCode, Toast.LENGTH_SHORT).show();
            return false;

        }
        else
        {
            Toast.makeText(getApplicationContext(),"You pressed" + keyCode, Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_POWER || keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_SEARCH  )
        {

            return false;

        }
        else
        {
            // Do nothing
        }

        return true;
    }

}
