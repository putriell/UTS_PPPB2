package com.example.uts_papb2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;



public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {

    private SensorManager mSensorManager;
    private Sensor mSensorLight;
    private Sensor mSensorTemperature;
    private Sensor mSensorGrafity;


    private TextView mTextSensorLight;
    private TextView mTextSensorTemperature;
    private TextView mIndikatorTemperature;
    private TextView mTextSensorGrafity;

    CardView cardtemp;

    CardView to_maps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mTextSensorLight = findViewById(R.id.label_light);

        mTextSensorGrafity = findViewById(R.id.label_gravity);
        cardtemp = findViewById(R.id.card_temperature);

        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mSensorGrafity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        findViewById(R.id.btn_tomaps).setOnClickListener(this);

        String sensor_error = "No sensor";
        if (mSensorLight == null){
            mTextSensorLight.setText(sensor_error);
        }

        if (mSensorGrafity == null){
            mTextSensorGrafity.setText(sensor_error);
        }
        cardtemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity_fragment.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onClick(View view){
        if (view.getId() == R.id.btn_tomaps){
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorGrafity!= null){
            mSensorManager.registerListener(this, mSensorGrafity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }



    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];
        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(
                        String.format("%1$.2f",currentValue)
                );
                changeCardColor(currentValue);
                break;


            case Sensor.TYPE_GRAVITY:
                mTextSensorGrafity.setText(
                        String.format("%1$.2f", currentValue)
                );
                break;
        }

    }

    private void changeCardColor (float currentValue){
        CardView layout = findViewById(R.id.card_light);
        if (currentValue >= 100 && currentValue <= 4000){
            layout.setCardBackgroundColor(Color.BLUE);
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}