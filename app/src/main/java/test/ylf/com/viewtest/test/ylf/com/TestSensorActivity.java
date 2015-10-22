package test.ylf.com.viewtest.test.ylf.com;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import test.ylf.com.viewtest.R;

/**
 * Created by Administrator on 2015/10/22.
 *
 *  摇一摇
 */
public class TestSensorActivity extends Activity {

    private SensorManager sensorManager;
    private Vibrator vibrator;
    @ViewInject(R.id.result)
    private TextView result;

    private static final String TAG = TestSensorActivity.class.getSimpleName();
    private static final int SENSOR_SHAKE = 10;
    private Handler mHandle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==SENSOR_SHAKE){
                result.setTextColor(Color.GREEN);
                result.setTextSize(25);
                result.setText("恭喜您获得一张优惠券");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_sensor);
        ViewUtils.inject(this);
        sensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        vibrator= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensorManager!=null){
            sensorManager.registerListener(registerListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        }
    }


    SensorEventListener registerListener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // 传感器信息改变时执行该方法
            float values []=event.values;
            float x=values[0]; // x轴方向的重力加速度，向右为正
            float y=values[1]; // y轴方向的重力加速度，向前为正
            float z=values[2]; //z轴方向的重力加速度，向上为正
            Log.i(TAG, "x轴方向的重力加速度" + x + "；y轴方向的重力加速度" + y + "；z轴方向的重力加速度" + z);
            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
            int medumValue = 19;// 如果不敏感请自行调低该数值,低于10的话就不行了,因为z轴上的加速度本身就已经达到10了
            if(Math.abs(x)>medumValue||Math.abs(y)>medumValue||Math.abs(z)>medumValue){
                vibrator.vibrate(2000);
                mHandle.obtainMessage(SENSOR_SHAKE).sendToTarget();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if(sensorManager!=null){
            sensorManager.unregisterListener(registerListener);
        }
    }
}
