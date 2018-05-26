package com.easontesting.myfirstapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class OdometerService extends Service {
    private final IBinder b = new OdometerBinder();
    private static double distanceInMeters;
    private static Location lastLocation = null;

    public OdometerService() {
        Log.w("OdometerService", "easontesting OdometerService constructor");
    }
    public class OdometerBinder extends Binder {
        OdometerService getOdometer() {
            Log.w("OdometerService", "easontesting OdometerService getOdometer");
            return OdometerService.this;
        }
    }
    @Override
    public void onCreate() {
        Log.w("OdometerService", "easontesting OdometerService onCreate");
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.w("OdometerService", "easontesting OdometerService LocationListener onLocationChanged");
                if(lastLocation == null){
                    lastLocation = location;
                }
                distanceInMeters += location.distanceTo(lastLocation);
                lastLocation = location;
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.w("OdometerService", "easontesting OdometerService LocationListener onProviderEnabled");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.w("OdometerService", "easontesting OdometerService LocationListener onProviderDisabled");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.w("OdometerService", "easontesting OdometerService LocationListener onStatusChanged");
            }
        };
        LocationManager lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try{
            Log.w("OdometerService", "easontesting OdometerService LocationManager requestLocationUpdates");
            //requestLocationUpdates(provider, timeInterval, minDistance, listener)
            lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, listener);
        }catch(SecurityException e){
            Log.w("OdometerService", "easontesting OdometerService LocationManager SecurityException");
        }

    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.w("OdometerService", "easontesting OdometerService onBind");
        return b;
    }
    //public void onStartCommand() {}  // for started service only
    //public void onDestory() {}
    //public double getMiles(){return this.distanceInMeters / 1609.344;}
    public double getMeters(){
        return this.distanceInMeters ;
    }
    public double resetMeters(){
        distanceInMeters = 0;
        return this.distanceInMeters ;
    }


}
