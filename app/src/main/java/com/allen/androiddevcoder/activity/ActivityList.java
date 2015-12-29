package com.allen.androiddevcoder.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.allen.androiddevcoder.R;

public class ActivityList extends AppCompatActivity {

    private Toolbar mToolbarToolbar;
    private FloatingActionButton mFabFloatingActionButton;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);
        mToolbarToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFabFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new BaseAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
                TextView textView = (TextView) view.findViewById(R.id.textView);
                if (checkBox.isChecked()){
                    checkBox.setChecked(false);
                    textView.setText("我被取消了");
                }else {
                    checkBox.setChecked(true);
                    textView.setText("我被选中了");
                }

            }
        });

    }
    class  BaseAdapter extends android.widget.BaseAdapter{

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(ActivityList.this).inflate(R.layout.activity_list_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            return view;
        }


        class ViewHolder {
            private CheckBox checkBox;


            ViewHolder(View view) {
                initViews(view);
            }

            private void initViews(View root) {
               checkBox = (CheckBox) root.findViewById(R.id.checkBox);
            }
        }
    }
}
