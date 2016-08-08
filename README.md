###
#License
```
Copyright 2016 AllenCoder

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

#开源库汇总
1. linechart 为指数基金走势图表可根据当前走势绘制出想要的图表。
2. 支持手势显示当前值
3. 支持当前的区域颜色渐变

4. 演示效果图

<img src="https://github.com/AllenCoder/AndroidDevCoder/blob/master/gif/linechart.gif" width=300 />



	Add it in your root build.gradle at the end of repositories:
```groovy
```
Add the dependency
```groovy
dependencies {
        compile 'compile 'com.allen.linechart:linechart:0.0.1'
}
```
或者  or
```
<dependency>
  <groupId>com.allen.linechart</groupId>
  <artifactId>linechart</artifactId>
  <version>0.0.1</version>
  <type>pom</type>
</dependency>
```

5.如果ListView中的单个Item的view中存在checkbox，button等view，会导致ListView.setOnItemClickListener无效，
  事件会被子View捕获到，ListView无法捕获处理该事件.
  解决方法：
  在checkbox、button对应的view处加
  [html] 
  ```xml
  android:focusable="false"  
  android:clickable="false"  
  android:focusableInTouchMode="false"  
  ```
6.反射获取状态栏高度
```
 /**
     * 反射获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight(Context mContext) {
        Class<?> c;
        Object obj;
        Field field;
        int x, sbar;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = mContext.getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {
            sbar = 0;
            Log.d("PopupListView", "getStatusBarHeight error =" + e1);
            e1.printStackTrace();
        }
        return sbar;
    }
```
7.MotionEvent的getX()，getY()与getRawX(),getRawY()区别
```
如果某个Activity中实现OnTouchListener接口，需要重写onTouch(View view，MotionEvent event)这个方法，getRawX()和getRawY()获得的是相对屏幕的位置，getX()和getY()获得的永远是相对view的触摸位置坐标
（这两个值不会超过view的长度和宽度）。
```

#Use it item  click
```java
mRecyclerView.addOnItemTouchListener(new OnItemClickListener(mRecyclerView,mQuickAdapter){

            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(RecyclerClickItemActivity.this, "" + Integer.toString(position), Toast.LENGTH_SHORT).show();
                
            }
        });
        
```

#Use it item chlid click
