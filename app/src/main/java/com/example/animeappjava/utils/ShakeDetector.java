package com.example.animeappjava.utils;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class ShakeDetector implements SensorEventListener {

    private static final int SHAKE_THRESHOLD = 800;

    private final OnShakeListener onShakeListener;

    private long lastUpdate = 0;
    private float last_x = 0f;
    private float last_y = 0f;
    private float last_z = 0f;

    public ShakeDetector(OnShakeListener onShakeListener) {
        this.onShakeListener = onShakeListener;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long curTime = System.currentTimeMillis();
        if (curTime - lastUpdate > 100) {
            long diffTime = curTime - lastUpdate;
            lastUpdate = curTime;
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
            if (speed > SHAKE_THRESHOLD) {
                onShakeListener.onShake();
            }
            last_x = x;
            last_y = y;
            last_z = z;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }

    public interface OnShakeListener {
        void onShake();
    }
}