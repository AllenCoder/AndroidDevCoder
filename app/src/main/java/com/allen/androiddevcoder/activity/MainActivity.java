package com.allen.androiddevcoder.activity;

import android.animation.Animator;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.allen.androiddevcoder.R;
import com.allen.androiddevcoder.util.WeakRefHander;
import com.allen.linechart.LineChart;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WeakRefHander.Callback{
    private List<Animator> mAnimators;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private LineChart lineChart;
    private WeakRefHander weakRefHander;
    private static final int HANDLER_MESSAGE_START = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        lineChart = (LineChart) findViewById(R.id.linechart);
        mAnimators = lineChart.createAnimation();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        lineChart.setAnimationStatus(mAnimators, LineChart.AnimStatus.START);
        weakRefHander = new WeakRefHander(this);
        weakRefHander.start(HANDLER_MESSAGE_START, 1000 * 10);
    }
    /**
     * 找到数值里面的最大值确定绘图区间
     *
     * @param value 指数大师数组
     * @return
     */
    private float getMaxValue(List<Float> value) {
        float temp = 0.0f;
        for (int i = 0; i < value.size(); i++) {
            if (temp < Math.abs(value.get(i) - 3873.43)) {
                temp = Math.abs(value.get(i) - 3873.43f);
            }
        }
        return temp;
    }
    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == HANDLER_MESSAGE_START) {
            lineChart.setLoading(false);
            final List<Float> lineData = ParseDate(getData);
            lineChart.setNowTime(nowtime);
            /**
             * 昨日收盘大于现在值
             */
            if (true) {
                lineChart.setIsUp(false);
            } else {
                lineChart.setIsUp(true);
            }
            lineChart.setLineData(lineData);
            lineChart.setStartValue(3873.43f);
            lineChart.setNowValue(lineData.get(lineData.size() - 1));
            lineChart.setMaxValue(getMaxValue(lineData));
            lineChart.invalidate();
        }
        return true;
    }

    private List<Float> ParseDate(String data) {
        String[] index = data.split(";");
        List<Float> linedata = new ArrayList<>();
        for (int i = 0; i < index.length; i++) {
            linedata.add(Float.parseFloat(index[i].split(",")[1]));
        }
        nowtime = index[index.length - 1].split(",")[0].toString();
        return linedata;
    }
    String nowtime;
    /**
     * 模拟数据
     */
    String getData = "0930,3873.43,6488345000,11.930,543886100;0931,3874.33,3368922900,12.214,263142000;0932,3873.20,2960691200,12.340,231699200;0933,3871.35,2486288900,12.367,198823900;0934,3875.45,2396155000,12.364,194075900;0935,3879.58,2448239000,12.476,183396600;0936,3879.27,2472663000,12.496,195210300;0937,3876.52,2470259000,12.465,202767200;0938,3873.68,2272074000,12.471,181183500;0939,3870.08,1961041000,12.547,143049900;0940,3866.73,1840553000,12.575,141041700;0941,3864.54,2041130000,12.612,154586500;0942,3863.41,1909899000,12.581,158457500;0943,3865.25,1946988000,12.591,152430400;0944,3868.08,1967558000,12.643,143371200;0945,3868.49,1911872000,12.675,143107600;0946,3868.16,1828412000,12.717,132959100;0947,3870.43,1694958000,12.735,128538900;0948,3872.43,1652068000,12.747,126139100;0949,3874.86,1714885000,12.750,133751200;0950,3878.79,1682670000,12.769,126190200;0951,3879.69,1526995000,12.757,123432800;0952,3880.09,1572000000,12.763,121086900;0953,3876.48,1654360000,12.747,135129700;0954,3873.49,1637796000,12.737,131836000;0955,3873.19,1482875000,12.718,123091200;0956,3873.42,1314297000,12.711,106143200;0957,3873.98,1208534000,12.734,86286000;0958,3872.40,1355968000,12.747,101768000;0959,3869.05,1468588000,12.756,111785000;1000,3864.11,1682832000,12.759,130755200;1001,3860.58,1343079000,12.767,101915500;1002,3859.41,1383675000,12.780,102998800;1003,3861.26,1245416000,12.801,88790400;1004,3863.26,1383565000,12.818,100860300;1005,3865.55,1245017000,12.840,87488200;1006,3866.03,1284885000,12.863,90158700;1007,3866.25,1112821000,12.879,79522900;1008,3865.84,1234695000,12.910,81869400;1009,3864.39,1094960000,12.918,80953900;1010,3863.52,1110674000,12.935,78256200;1011,3863.23,964950000,12.933,75316900;1012,3863.73,1089310000,12.934,84179300;1013,3864.70,1006463000,12.942,73575300;1014,3865.60,925861000,12.949,68214700;1015,3864.72,1255754000,12.967,88518500;1016,3862.80,1242367000,12.979,89469500;1017,3860.09,1360194000,12.984,102449100;1018,3858.07,1278887000,12.985,98143500;1019,3856.17,1275016000,12.972,104853100;1020,3853.70,1276601000,12.964,102857300;1021,3854.75,1262667000,12.963,97494900;1022,3856.43,1098659000,12.967,82649400;1023,3858.67,1059837000,12.974,78083700;1024,3859.59,871714000,12.985,60907200;1025,3859.75,787094000,12.996,54850900;1026,3859.63,826397000,13.002,60110900;1027,3858.08,815272000,13.005,60694500;1028,3858.19,1011009000,13.014,72905500;1029,3861.24,1089225000,13.033,72751600;1030,3864.09,1102317000,13.047,76282600;1031,3866.47,1265752000,13.058,90987500;1032,3869.07,1353233000,13.071,95797600;1033,3870.05,1245605000,13.080,90084300;1034,3870.02,1090772000,13.083,81513400;1035,3869.97,1151635000,13.085,86491800;1036,3868.65,1057778000,13.096,73913800;1037,3867.28,842686000,13.106,58285700;1038,3866.82,706996000,13.110,51460800;1039,3866.66,704910000,13.118,48799100;1040,3866.02,693637000,13.122,50422500;1041,3863.84,702183000,13.127,50422400;1042,3862.08,811702000,13.132,58737200;1043,3856.36,1165086000,13.126,92143200;1044,3854.12,1076478000,13.124,83523600;1045,3854.38,1040515000,13.127,77213900;1046,3855.84,1147609000,13.121,91825200;1047,3856.90,887382000,13.123,65743500;1048,3857.30,677269000,13.125,50899100;1049,3857.60,722260000,13.121,57453400;1050,3858.58,851237000,13.130,58981900;1051,3858.45,789284000,13.131,58909400;1052,3858.71,854901000,13.135,62898700;1053,3861.62,1151602000,13.142,82662000;1054,3863.36,1166114000,13.153,80900100;1055,3863.98,961731000,13.165,64445500;1056,3863.45,837921000,13.175,56553800;1057,3861.80,799683000,13.182,55685800;1058,3860.91,833346000,13.188,59083500;1059,3860.61,655588000,13.194,45176200;1100,3860.00,732254000,13.200,51310400;1101,3860.78,754822000,13.209,50510300;1102,3861.23,750106000,13.215,52077300;1103,3859.83,828550000,13.218,60959400;1104,3856.80,913712000,13.217,69916300;1105,3851.62,1143933000,13.211,91009700;1106,3848.68,1428267000,13.191,123107300;1107,3845.91,1349864000,13.180,110482100;1108,3846.68,1156169000,13.175,92108600;1109,3848.56,865980000,13.180,61498000;1110,3850.20,670150000,13.179,51800700;1111,3849.84,589820000,13.177,45968000;1112,3848.84,662660000,13.174,52673400;1113,3847.23,700560000,13.178,50717700;1114,3846.24,684730000,13.176,52886200;1115,3844.52,790020000,13.175,61321300;1116,3842.68,872820000,13.170,70062000;1117,3843.08,884180000,13.165,71383700;1118,3842.15,793530000,13.164,61304800;1119,3844.99,680680000,13.160,55004300;1120,3846.54,647100000,13.159,49411500;1121,3848.80,539740000,13.163,38176700;1122,3849.33,553760000,13.164,40724300;1123,3849.99,557900000,13.168,39151600;1124,3849.44,511280000,13.167,39487100;1125,3848.73,487670000,13.168,36298000;1126,3848.86,463770000,13.171,32667800;1127,3848.12,470830000,13.172,35225900;1128,3848.23,407550000,13.175,28486400;1129,3848.71,415850000,13.179,28058600;1130,3848.71,68450000,13.180,4309000";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
