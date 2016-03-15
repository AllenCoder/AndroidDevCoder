# Android 防内存泄露handler 


#     1.使用弱引用 WeakRefHander 
```java
	/**
	 * 作者: allen on 15/11/24.感谢开源作者https://coding.net/u/coding/p/Coding-Android/git
	 */
	
	/**
	 * 弱引用 handler 防止内存泄露
	 */
	public class WeakRefHander extends Handler {
	
	    private final WeakReference<Handler.Callback> mRef;
	    private final int mLoopTime;
	    private int NO_LOOP = -1;
	    private int what =0;
	
	    /**
	     * 循环
	     *
	     * @param loopAction
	     * @param loopTime
	     */
	    public WeakRefHander(Handler.Callback loopAction, int loopTime) {
	        super();
	        this.mRef = new WeakReference<>(loopAction);
	        this.mLoopTime = loopTime;
	
	    }
	
	    /**
	     * 不循环
	     *
	     * @param loopAction
	     */
	    public WeakRefHander(Handler.Callback loopAction) {
	        super();
	        mRef = new WeakReference<>(loopAction);
	        mLoopTime = NO_LOOP;
	    }
	
	    @Override
	    public void handleMessage(Message msg) {
	        Handler.Callback action = mRef.get();
	        if (action != null) {
	            action.handleMessage(msg);
	            if (mLoopTime != NO_LOOP) {
	                sendEmptyMessageDelayed(what, mLoopTime);
	            }
	        }
	    }
	
	    public void start() {
	        removeMessages(0);
	        sendEmptyMessageDelayed(0, 0);
	    }
	
	    public void start(int what, long delay) {
	        this.what = what;
	        removeMessages(what);
	        sendEmptyMessageDelayed(what, delay);
	    }
	
	    public void stop() {
	        removeMessages(what);
	    }
	
	    public void clear() {
	        removeMessages(what);
	        mRef.clear();
		    }
		}
```

## 2. 实现 Activity implements WeakRefHander.Callback 

## 3. 在handleMessage处理业务逻辑
  
#   示例代码:

```java
    public class MainActivity extends AppCompatActivity implements WeakRefHander.Callback {
        private WeakRefHander weakRefHander;
        private static final int HANDLER_MESSAGE_START = 1001;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            weakRefHander = new WeakRefHander(this, 1);
            weakRefHander.start(HANDLER_MESSAGE_START, 1000 * 30);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HANDLER_MESSAGE_START:
                //Todo 处理业务逻辑
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public void onResume() {
        super.onResume();
        weakRefHander.start();
    }

    @Override
    public void onPause() {
        weakRefHander.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        weakRefHander.clear();
        super.onDestroy();
    }
    }
```

# -[Github示例参考代码](https://github.com/AllenCoder/AndroidDevCoder/tree/master/leakhandler)

# 参考作者：-[Coding-Android作者](https://coding.net/u/coding/p/Coding-Android/git)
