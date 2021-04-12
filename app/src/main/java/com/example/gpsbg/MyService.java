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

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.util.TimeUtils;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyService extends Service implements LOcListenerIteerface{
    private LocationManager locationManager;
    private MyLocListener myLocListener;
    //private DatabaseReference mDataBase;
    private FirebaseFirestore db;


    public MyService() {
    }

    @Override
    public void onCreate() {
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        myLocListener = new MyLocListener();
        myLocListener.setlOcListenerIteerface(this);
        db = FirebaseFirestore.getInstance();
//        mDataBase = FirebaseDatabase.getInstance().getReference("coordinates");
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
        Log.i("Location Services", "Stop");
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

//        Loc nevLocation = new Loc(loc.getLatitude(), loc.getLongitude());
//        mDataBase.push().setValue(nevLocation);

        // Create a new user with a first and last name
        Map<String, Object> coord = new HashMap<>();
        coord.put("latitude", loc.getLatitude());
        coord.put("longitude", loc.getLongitude());

        // Add a new document with a generated ID
        db.collection("users")
                .add(coord)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Firedatabase", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firedatabase", "Error adding document", e);
                    }
                });
    }

    private void someTask() {
        Log.i("Services", "SomeTaskRun");
        for (int i = 1; i<=15; i++){
            Log.d("Services", "i = " + i);
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }        }
    }
}