#开源库汇总
1. linechart 为指数基金走势图表可根据当前走势绘制出想要的图表。
2. 支持手势显示当前值
3. 支持当前的区域颜色渐变

4.演示效果图
<img src="http://img.blog.csdn.net/20151222175518831" width=300 >


	> # Get it
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
