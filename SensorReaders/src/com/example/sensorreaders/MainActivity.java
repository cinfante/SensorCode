package com.example.sensorreaders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity implements SensorEventListener {
	
	private SensorManager mSensorManager;
	private Sensor accel, gyro, mag;
	private float xAccel = 0f, yAccel = 0f, zAccel = 0f;
	private float xGyro = 0f, yGyro = 0f, zGyro = 0f;
	private float xMag = 0f, yMag = 0f, zMag = 0f;
	private TextView tv1, tv2, tv3;
	
	public enum SensorType {
    	ACCELERATION,
    	GYROSCOPE,
    	MAGNETIC_FIELD
    }
	
    @SuppressLint("ServiceCast") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accel = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        gyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mag = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        setNewText(SensorType.ACCELERATION, 0f, 0f, 0f);
        setNewText(SensorType.GYROSCOPE, 0f, 0f, 0f);
        setNewText(SensorType.MAGNETIC_FIELD, 0f, 0f, 0f);
    }
    
    /**
     * 
     * Helper function which changes the displayed values of the input sensor type to the input x,y,z values
     * 
     * @param sensorType enum denoting the type of sensor whose text we are changing
     * @param x the new value of that sensor's value in the x-coordinate
     * @param y the new value of that sensor's value in the x-coordinate
     * @param z the new value of that sensor's value in the x-coordinate
     */
    public void setNewText(SensorType sensorType, float x, float y, float z){
    	switch(sensorType){
    	case ACCELERATION:
    		tv1.setText("ACCELERATION\n" + "x-coordinate: " + xAccel + "\n" + "y-ccordinate: " + yAccel + "\n" + "z-coordinate: " + zAccel);
    		break;
    	case GYROSCOPE:
    		tv2.setText("GYROSCOPE\n" + "x-coordinate: " + xGyro + "\n" + "y-ccordinate: " + yGyro + "\n" + "z-coordinate: " + zGyro);
    		break;
    	case MAGNETIC_FIELD:
    		tv3.setText("MAGNETIC FIELD\n" + "x-coordinate: " + xMag + "\n" + "y-ccordinate: " + yMag + "\n" + "z-coordinate: " + zMag);
    		break;
    	}
    }
    
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
			xAccel = event.values[0]; //x-direction: left is positive
			yAccel = event.values[1]; //y-direction: down is positive
			zAccel = event.values[2]; //z-direction: forward is positive
	        setNewText(SensorType.ACCELERATION, xAccel, yAccel, zAccel);
		}
		if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
		    xGyro = event.values[0]; //up-down
		    yGyro = event.values[1]; //left-right
		    zGyro = event.values[2]; //tilting
		    setNewText(SensorType.GYROSCOPE, xGyro, yGyro, zGyro);
		}
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
			xMag = event.values[0];
			yMag = event.values[1];
			zMag = event.values[2];
			setNewText(SensorType.MAGNETIC_FIELD, xMag, yMag, zMag);
		}
	}
    
	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
		mSensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_UI);
		mSensorManager.registerListener(this, mag, SensorManager.SENSOR_DELAY_UI);
	}
    
    @Override
    protected void onPause() {
    	super.onPause();
    	mSensorManager.unregisterListener(this);
    }
    
}
