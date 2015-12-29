package com.allen.androiddevcoder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.androiddevcoder.R;

import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Activity_JINI extends AppCompatActivity  {

    private LuaState mLuaState;//Lua解析和执行由此对象完成


    private TextView displayResult1;//用于演示，显示数据
    private TextView displayResult2;
    private LinearLayout mLayout;

    private static int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jini);
        displayResult1 = (TextView)findViewById(R.id.displayResult1);
        displayResult2 = (TextView)findViewById(R.id.displayResult2);
        mLayout = (LinearLayout) findViewById(R.id.layout);


        findViewById(R.id.executeLuaStatemanet).setOnClickListener(listener);
        findViewById(R.id.executeLuaFile).setOnClickListener(listener);
        findViewById(R.id.callAndroidApi).setOnClickListener(listener);
        findViewById(R.id.clearBtn).setOnClickListener(listener);

        mLuaState = LuaStateFactory.newLuaState();
        mLuaState.openLibs();
    }

    private View.OnClickListener listener=new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.executeLuaStatemanet:
                    executeLuaStatemanet();
                    break;

                case R.id.executeLuaFile:
                    executeLuaFile();
                    break;

                case R.id.callAndroidApi:
                    callAndroidAPI();
                    break;

                case R.id.clearBtn:
                    displayResult1.setText("");
                    displayResult2.setText("");
                    mLayout.removeAllViews();
                    break;
            }
        }
    };

    private void executeLuaStatemanet()
    {
        mLuaState.LdoString(" varSay = 'call from android : This is string in lua script statement.'");// 定义一个Lua变量
        mLuaState.getGlobal("varSay");// 获取
        displayResult1.setText(mLuaState.toString(-1));// 输出
    }

    private void executeLuaFile()
    {
        mLuaState.LdoString(readStream(getResources().openRawResource(R.raw.luafile)));
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "functionInLuaFile");// 找到functionInLuaFile函数
        mLuaState.pushString("从Java中传递的参数");// 将参数压入栈

        // functionInLuaFile函数有一个参数，一个返回结果
        int paramCount = 1;
        int resultCount = 1;
        mLuaState.call(paramCount, resultCount);
        mLuaState.setField(LuaState.LUA_GLOBALSINDEX, "resultKey");// 将结果保存到resultKey中
        mLuaState.getGlobal("resultKey");// 获取
        displayResult2.setText(mLuaState.toString(-1));// 输出
    }

    private void callAndroidAPI()
    {
        mLuaState.LdoString(readStream(getResources().openRawResource(R.raw.luafile)));
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "callAndroidApi");// 找到functionInLuaFile函数
        mLuaState.pushJavaObject(getApplicationContext());
        mLuaState.pushJavaObject(mLayout);
        mLuaState.pushString("lua call android Textview的setText()方法, 内容是:"+(++count));
        mLuaState.call(3, 0);
    }


    private String readStream(InputStream is)
    {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1)
            {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            Log.e("ReadStream", "读取文件流失败");
            return "";
        }
    }
}
