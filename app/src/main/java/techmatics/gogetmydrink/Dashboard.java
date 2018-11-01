package techmatics.gogetmydrink;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

public class Dashboard extends FragmentActivity implements WebApiResponseCallback, LocationListener, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {


    ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9, image10, image11, image12;

    View view1, view2, view3, view4, view5, view6, view7, view8, view9, view10, view11, view12;
    @BindView(R.id.listView)
    ListView list;
    AppController controller;
    Dialog dialog;
    ArrayList<Profile> listData = new ArrayList<>();
    String address = "";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    LocationManager mlocManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
        mGoogleApiClient = new GoogleApiClient.Builder(Dashboard.this)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API)
                .enableAutoManage(Dashboard.this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
         mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
         createLocationRequest();
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 dialog=Common.showPogress(Dashboard.this);
                 listData.clear();
                 getLocation();
             }
         },500);



    }

    public void getLocation()
    {
        if (!mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            if(mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) Dashboard.this);
                Log.d(TAG, "Location update started ..............: ");
            }
        }

    }
    private  void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Yout GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),254);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void getData(LatLng latLng) {
        if (Common.isNetworkAvailable(Dashboard.this)) {
            dialog = Common.showPogress(Dashboard.this);
            controller.getWebApiCall().getData(Common.getUserList(Double.toString(latLng.latitude), Double.toString(latLng.longitude)), this);
        }
    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {
                    Toast.makeText(getApplicationContext(), "You haven't given permission to make call.", Toast.LENGTH_SHORT).show();
                }
                return;
            }


        }
    }

    @Override
    public void onSucess(String value) {

       if(!value.contains("No User Available For This Location")) {
           try {
               listData.clear();
               JSONArray jsonArray = new JSONArray(value);
               for (int i = 0; i < jsonArray.length(); i++) {
                   listData.add(new Profile(jsonArray.getJSONObject(i)));
               }
               if (listData.size() > 0) {
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           list.setAdapter(new DashboardListAdapter(listData, Dashboard.this));
                       }
                   });
               }
           } catch (Exception ex) {
               ex.fillInStackTrace();
           }
           if (dialog != null) {
               dialog.cancel();
           }
       }else {
           Common.showToast(Dashboard.this,"Sorry! No gogetter are available for your current location.  ");
       }
    }

    @Override
    public void onError(String value) {
        if(dialog!=null)
        {
            dialog.cancel();
        }
      Common.showToast(Dashboard.this,value);
    }


    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {



           if(mGoogleApiClient.isConnected()) {
                    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,  (com.google.android.gms.location.LocationListener) Dashboard.this);
    }               Log.d(TAG, "Location update stopped .......................");
                }
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }
    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {

            String addresss = Common.getCompleteAddressString(Dashboard.this, location.getLatitude(), location.getLongitude());
            address=addresss;
            LatLng loc=new LatLng(location.getLatitude(),location.getLongitude());
            if(dialog!=null)
            {
                dialog.cancel();
            }
            if(listData.size()==0) {
                getData(loc);
                stopLocationUpdates();
            }

        }
    }




    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode==254)&&(resultCode==RESULT_OK))
        {
            getLocation();
        }
    }
}
