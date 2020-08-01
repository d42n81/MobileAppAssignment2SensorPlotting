package unc.live.d42n81.sensorplots_davidmooreassignment2;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Button accelerometerButton;
    TextView accelerometerTextView;
    Button lightSensorButton;
    TextView lightSensorTextView;
    Button temperatureSensorButton;
    TextView temperatureSensorTextView;
    SensorManager sm;
    Sensor accelerometer;
    Sensor lightSensor;
    Sensor temperatureSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // assign UI elements to variables:
        this.accelerometerButton = findViewById(R.id.accelerometerButton);
        this.accelerometerTextView = findViewById(R.id.accelerometerTextView);
        this.lightSensorButton = findViewById(R.id.lightSensorButton);
        this.lightSensorTextView = findViewById(R.id.lightSensorTextView);
        this.temperatureSensorButton = findViewById(R.id.temperatureSensorButton);
        this.temperatureSensorTextView = findViewById(R.id.temperatureSensorTextView);

        // Now check if the sensors exist on this phone. If they do, update the
        // TextViews of each sensor
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        int time = 100000;
        this.accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.lightSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        this.temperatureSensor = sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        // register listeners:
        sm.registerListener(this, this.accelerometer, time);
        sm.registerListener(this, this.lightSensor, time);
        sm.registerListener(this, this.temperatureSensor, time);

        String isSensorPresentAccelerometer = "Accelerometer is not Present";
        String isSensorPresentLightSensor = "Light Sensor is not Present";
        String isSensorPresentTemperatureSensor = "Temperature Sensor is not Present";

        if(this.accelerometer != null) {
            isSensorPresentAccelerometer = "Accelerometer is Present";
            String range = "Range: " + this.accelerometer.getMaximumRange();
            String resolution = "Resolution: " + this.accelerometer.getResolution();
            String delay = "Delay: " + this.accelerometer.getMinDelay();
            isSensorPresentAccelerometer += "\nInfo: " + range + " " + resolution + " " + delay;
        }
        if(this.lightSensor != null) {
            isSensorPresentLightSensor = "Light Sensor is Present";
            String range = "Range: " + this.lightSensor.getMaximumRange();
            String resolution = "Resolution: " + this.lightSensor.getResolution();
            String delay = "Delay: " + this.lightSensor.getMinDelay();
            isSensorPresentLightSensor += "\nInfo: " + range + " " + resolution + " " + delay;
        }
        if(this.temperatureSensor != null) {
            isSensorPresentTemperatureSensor = "Temperature Sensor is Present";
            String range = "Range: " + this.temperatureSensor.getMaximumRange();
            String resolution = "Resolution: " + this.temperatureSensor.getResolution();
            String delay = "Delay: " + this.temperatureSensor.getMinDelay();
            isSensorPresentTemperatureSensor += "\nInfo: " + range + " " + resolution + " " + delay;
        }

        // set text of respective sensor TextViews:
        this.accelerometerTextView.setText(isSensorPresentAccelerometer);
        this.lightSensorTextView.setText(isSensorPresentLightSensor);
        this.temperatureSensorTextView.setText(isSensorPresentTemperatureSensor);
    }

    public void accelerometerOnClick(View v) {
        // do something

        // Play sound on click:
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.pianolow);
        mp.start();
        // Open new intent:
        Intent myIntent = new Intent(MainActivity.this, AccelerometerActivity.class);
        // myIntent.putExtra("key", value); //Optional parameters
        MainActivity.this.startActivity(myIntent);
    }

    public void lightSensorOnClick(View v) {
        // do something
        // Play sound on click:
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.pianolow);
        mp.start();
        Intent myIntent = new Intent(MainActivity.this, LightSensorActivity.class);
        // myIntent.putExtra("key", value); //Optional parameters
        MainActivity.this.startActivity(myIntent);
    }

    public void temperatureSensorOnClick(View v) {
        // Play sound on click:
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.pianolow);
        mp.start();
        Toast t = Toast.makeText(this, "You do not have a Temperature Sensor on this device. " +
                "No graph will be displayed", Toast.LENGTH_LONG);
        t.show();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
// List what the project will be about for our powerpoint.