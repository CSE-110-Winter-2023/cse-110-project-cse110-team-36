package edu.ucsd.cse110.lab4;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class OrientationService implements SensorEventListener {
    private static OrientationService instance;

    private final SensorManager sensorManager;
    private float[] accelerometerReading;
    private float[] magnetometerReading;
    private MutableLiveData<Float> azimuth;

    /**
     * Constructor for OrientationService
     *
     * @param activity Context needed to initiate SensorManager
     */
    public OrientationService(Activity activity) {
        this.sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        this.azimuth = new MutableLiveData<>();

        this.registerSensorListeners();
    }

    public OrientationService singleton(Activity activity) {
        if (instance == null) {
            instance = new OrientationService(activity);
        }
        return instance;
    }

    /*
     * Register listener of accelerometer and magnetometer
     */
    private void registerSensorListeners () {
        // Register our listener to the accelerometer and magnetometer.
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                sensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * This method is called when the sensor detects a change in value.
     *
     * @param event the event containing the values we need.
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //can't calculate orientation with only accelerometer, but can use data later
            accelerometerReading = sensorEvent.values;
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            //can't calculate orientation with only magnetometer, but can use data later
            magnetometerReading = sensorEvent.values;
        }
        if (accelerometerReading != null && magnetometerReading != null) {
            //can calculate orientation with both
            onBothSensorDataAvailable();
        }
    }

    /**
     * Called when we have readings for both sensors.
     */
    private void onBothSensorDataAvailable() {
        //if invalid reading, error
        if (accelerometerReading == null || magnetometerReading == null) {
            throw new IllegalStateException("Both sensors must be available to compute orientation.");
        }

        float[] r = new float[9];
        float[] i = new float[9];

        //check if orientation calculates properly
        boolean success = SensorManager.getRotationMatrix(r, i, accelerometerReading, magnetometerReading);

        if (success) {
            //get and set orientation of device
            float[] orientation = new float[3];
            SensorManager.getOrientation(r, orientation);

            this.azimuth.postValue(orientation[0]);
        }
    }

    /*
     * Retrieve most recent orientation
     */
    public LiveData<Float> getOrientation() {return this.azimuth;}

    /*
     * Unregister listeners (if app is paused/stopped)
     */
    public void unregisterSensorListeners() {sensorManager.unregisterListener(this);}

    /*
     * Set mock orientation, remove listeners for real orientation
     */
    public void setMockOrientationSource(MutableLiveData<Float> mockDataSource) {
        unregisterSensorListeners();
        this.azimuth = mockDataSource;
    }

    /*
     * Required by interface, but not needed for functionality.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
