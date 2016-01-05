package com.allen.androiddevcoder.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
                final TextView textView = (TextView) view.findViewById(R.id.textView);
                final CheckBox checkBox1 = (CheckBox) view.findViewById(R.id.checkBox1);
                final TextView textView1 = (TextView) view.findViewById(R.id.textView1);
               checkBox.setOnCheckedChangeListener(parent.getOnItemSelectedListener());
                view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getX()<view.getWidth()/2){
                            if (checkBox.isChecked()) {
                                checkBox.setChecked(false);
                                textView.setText("我被第一个取消了");
                            } else {
                                checkBox.setChecked(true);
                                textView.setText("我被第一个选中了");
                            }
                        }else {
                            if (checkBox1.isChecked()) {
                                checkBox.setChecked(false);
                                textView1.setText("我被第二个取消了");
                            } else {
                                checkBox1.setChecked(true);
                                textView1.setText("我被第二个选中了");
                            }
                        }
                        return false;
                    }
                });
            }
        });

    }

    class BaseAdapter extends android.widget.BaseAdapter {

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
            }//            holder.checkBox.setOnCheckedChangeListener(new CheckedListener());

//            holder.checkBox1.setOnCheckedChangeListener(new CheckedListener());
            return view;
        }


        class ViewHolder {
            private CheckBox checkBox,checkBox1;


            ViewHolder(View view) {
                initViews(view);
            }

            private void initViews(View root) {
                checkBox = (CheckBox) root.findViewById(R.id.checkBox);
                checkBox1 = (CheckBox) root.findViewById(R.id.checkBox1);
            }
        }
    }
//    class CheckedListener implements CompoundButton.OnCheckedChangeListener {
//        @Override
//        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//            System.out.println("compoundButton.getTag()" + compoundButton.getTag() + "b" + b);
//        }
//    }
}
