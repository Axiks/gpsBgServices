package com.example.gpsbg;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.ActivityCompat;

public class MyService extends Service implements LOcListenerIteerface{
    private LocationManager locationManager;
    private MyLocListener myLocListener;

    public MyService() {
    }

    @Override
    public void onCreate() {
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        myLocListener = new MyLocListener();
        myLocListener.setlOcListenerIteerface(this);
        checkPermission();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Services", "Start");
        someTask();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void checkPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2, 1, myLocListener);
        }
    }

    @Override
    public void OnLocationChanged(Location loc) {
        String text = "";
        text += loc.getLatitude();
        text += " ";
        text += loc.getLongitude();
        Log.i("Location Services", text);
    }

    private void someTask() {
        Log.i("Services", "SomeTaskRun");
    }
}