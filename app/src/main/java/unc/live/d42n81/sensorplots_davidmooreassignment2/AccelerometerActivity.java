package unc.live.d42n81.sensorplots_davidmooreassignment2;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sm;
    Sensor accelerometer;
    GraphView g;
    long timesSensorHasChanged = 0;
    TextView zerothXLabel;
    long zerothXValue = 0;
    TextView firstXLabel;
    long firstXValue = 1;
    TextView secondXLabel;
    long secondXValue = 2;
    TextView thirdXLabel;
    long thirdXValue = 3;
    TextView fourthXLabel;
    long fourthXValue = 4;
    TextView fifthXLabel;
    long fifthXValue = 5;
    TextView sixthXLabel;
    long sixthXValue = 6;
    TextView legend;
    ImageView rocketShip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accelerometer_intent);
        // Get x labels:
        this.zerothXLabel = findViewById(R.id.zerothXLabel);
        this.firstXLabel = findViewById(R.id.firstXLabel);
        this.secondXLabel = findViewById(R.id.secondXLabel);
        this.thirdXLabel = findViewById(R.id.thirdXLabel);
        this.fourthXLabel = findViewById(R.id.fourthXLabel);
        this.fifthXLabel = findViewById(R.id.fifthXLabel);
        this.sixthXLabel = findViewById(R.id.sixthXLabel);

        g = findViewById(R.id.graph);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE); //Maybe just SENSOR_SERVICE here
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        int time = 100000;
        // time = 100000000;
        sm.registerListener(this, accelerometer, time);
        // update legend text
//        this.legend = findViewById(R.id.legend);
//        String legend = "Legend: ";
//        String data = "Data, ";
//        String mean = "Mean, ";
//        String std = "Standard Deviation";
//        this.legend.setText(legend+data+mean+std, TextView.BufferType.SPANNABLE);
//        Spannable s = (Spannable)this.legend.getText();
//        s.setSpan(new ForegroundColorSpan(0x660066), data.length(), data.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        this.rocketShip = findViewById(R.id.rocketShip);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(this.timesSensorHasChanged > 6) {
            // update labels
            this.zerothXValue++;
            this.zerothXLabel.setText("" + this.zerothXValue);
            this.firstXValue++;
            this.firstXLabel.setText(""+this.firstXValue);
            this.secondXValue++;
            this.secondXLabel.setText("" + this.secondXValue);
            this.thirdXValue++;
            this.thirdXLabel.setText("" + this.thirdXValue);
            this.fourthXValue++;
            this.fourthXLabel.setText("" + this.fourthXValue);
            this.fifthXValue++;
            this.fifthXLabel.setText("" + this.fifthXValue);
            this.sixthXValue++;
            this.sixthXLabel.setText("" + this.sixthXValue);
        }
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        double value = Math.sqrt(x*x+y*y+z*z);
        Log.v("MYTAG", "Value of data = " + value);
        g.addPointToData((float)value);
        float mean = g.getMean();
        // Handle changing of BackGround rocketship:
//        if(value <= 10) {
//            rocketShip.setImageResource(R.drawable.rocketwithoutflames);
//        } else if (value <= 20) {
//            rocketShip.setImageResource(R.drawable.rocketwithlowflamesa);
//        } else if (value <= 25) {
//
//            rocketShip.setImageResource(R.drawable.rocketwithlowflamesb);
//        } else if (value <=30) {
//
//            rocketShip.setImageResource(R.drawable.rocketwithlowflamesc);
//        } else if(value <= 40) {
//
//            rocketShip.setImageResource(R.drawable.rocketwithhighflamesa);
//        } else if (value <= 50) {
//
//            rocketShip.setImageResource(R.drawable.rocketwithhighflamesb);
//        } else if (value > 50) {
//
//            rocketShip.setImageResource(R.drawable.rocketwithhighflamesc);
//        }
        if (mean <= 10) {
            rocketShip.setImageResource(R.drawable.rocketwithoutflames);
        } else if (mean <= 12) {
            rocketShip.setImageResource(R.drawable.rocketwithlowflamesa);
        } else if (mean <=15) {
            rocketShip.setImageResource(R.drawable.rocketwithlowflamesb);
        } else if (mean <=18) {
            rocketShip.setImageResource(R.drawable.rocketwithlowflamesc);
        } else if (mean <=21) {
            rocketShip.setImageResource(R.drawable.rocketwithhighflamesa);
        } else if (mean <=25) {
            rocketShip.setImageResource(R.drawable.rocketwithhighflamesb);
        } else if (mean > 25) {
            rocketShip.setImageResource(R.drawable.rocketwithhighflamesc);
        }

        g.invalidate();
        this.timesSensorHasChanged++;

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
        sm.registerListener(this, accelerometer, 100000);
    }

    public void backOnClick(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.pianod);
        mp.start();
        // go back to main activity intent:
        Intent myIntent = new Intent(AccelerometerActivity.this, MainActivity.class);
        // myIntent.putExtra("key", value); //Optional parameters
        AccelerometerActivity.this.startActivity(myIntent);
    }
}
