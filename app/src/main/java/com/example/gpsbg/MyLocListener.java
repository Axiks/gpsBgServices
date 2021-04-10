package com.example.gpsbg;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;

public class MyLocListener implements LocationListener {
    private LOcListenerIteerface lOcListenerIteerface;
    @Override
    public void onLocationChanged(@NonNull Location location) {
        lOcListenerIteerface.OnLocationChanged(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    public void setlOcListenerIteerface(LOcListenerIteerface lOcListenerIteerface) {
        this.lOcListenerIteerface = lOcListenerIteerface;
    }
}
