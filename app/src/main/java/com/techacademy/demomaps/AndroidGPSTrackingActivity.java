package com.techacademy.demomaps;

/**
 * Created by samy on 06/06/16.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.techacademy.demomaps.City.Marseille;
import com.techacademy.demomaps.City.Paris;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class AndroidGPSTrackingActivity extends Activity {


    public static String toureiffel;
    public static final int NOTIFICATION_ID = 1;
    Button btnShowLocation;

    // GPSTracker class
    GPSTracker gps;
    ToggleButton gpstoggle,wifitoggle;
    public static WifiManager wifiManager;
    LocationManager lm;
    WebView web;
    // BITE

    private final void createNotification(){
        final NotificationManager mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        final Intent launchNotifiactionIntent = new Intent(this, AndroidGPSTrackingActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchNotifiactionIntent, PendingIntent.FLAG_ONE_SHOT);

        String notificationTitle="Hello";
        Notification.Builder builder = new Notification.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setTicker(notificationTitle)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(getResources().getString(R.string.notification_title))
                .setContentText(getResources().getString(R.string.notification_desc))
                .setContentIntent(pendingIntent);

        mNotification.notify(NOTIFICATION_ID, builder.build());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_dialog:

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AndroidGPSTrackingActivity.this);
                builder.setTitle("Bravo");
                builder.setMessage("Vous avez creer une boite de dialogue");
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(getApplicationContext(),"Bravo! Le toast",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.show();

            case R.id.action_save:
                // Comportement du bouton "A Propos"
                createNotification();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);



        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);

        gpstoggle = (ToggleButton) findViewById(R.id.gps);
        wifitoggle = (ToggleButton) findViewById(R.id.wifi);

        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        //gps toggle
        gpstoggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    final Intent poke = new Intent();
                    poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                    poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                    poke.setData(Uri.parse("3"));
                    getApplicationContext().sendBroadcast(poke);
                }
                else{
                    final Intent poke = new Intent();
                    poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                    poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                    poke.setData(Uri.parse("3"));
                    getApplicationContext().sendBroadcast(poke);
                }
            }
        });
        //wifi toggle
        wifitoggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    wifiManager.setWifiEnabled(true);
                }
                else{
                    wifiManager.setWifiEnabled(false);
                }
            }
        });


        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object

                MediaPlayer mp = MediaPlayer.create(AndroidGPSTrackingActivity.this, R.raw.slayer);
                mp.start();
                gps = new GPSTracker(AndroidGPSTrackingActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = gcd.getFromLocation(latitude, longitude, 1);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if(!(addresses == null)){
                        if (!addresses.isEmpty() ){
                            System.out.println(addresses.get(0).getLocality());

                            Address address = addresses.get(0);

                            String addressText = String.format("%s, %s, %s, %s, %s, %s",
                                    address.getAddressLine(0),
                                    address.getAddressLine(1),
                                    address.getAddressLine(2),
                                    address.getAddressLine(3),
                                    address.getPhone(),
                                    address.getPremises());

                            System.out.println(addressText);

                            // \n is for new line
                            Toast.makeText(getApplicationContext(), "Vous visitez " + addresses.get(0).getLocality() + "\nVous etes en " + addresses.get(0).getCountryName(), Toast.LENGTH_LONG).show();

                                createNotification();

                            if(addresses.get(0).getLocality().equals("Paris-5E-Arrondissement")) {


                                MyASyncTask task =  new MyASyncTask();

                                task.execute();



                                startActivity(new Intent(getApplicationContext(), Paris.class));
                            }
                            if(addresses.get(0).getLocality().equals("Marseille")) {
                                startActivity(new Intent(getApplicationContext(), Marseille.class));
                            }
                        }else{
                            // can't get location
                            // GPS or Network is not enabled
                            // Ask user to enable GPS/network in settings
                            gps.showSettingsAlert();
                        }
                    }else{
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gps.showSettingsAlert();
                    }

                }

            }
        });
    }

    private JSONObject readJson(){
        try{
            InputStream is = new FileInputStream(getCacheDir() + "/" + "tour.json");
            JSONObject jsonObject = new JSONObject(inputStreamToString(is));
            JSONObject result = new JSONObject(jsonObject.get("result").toString());
            return jsonObject;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();

        }
        return null;
    }

    public static String inputStreamToString(InputStream is){

        Log.e("inputStreamToString", "start");

        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            is.close();
            return sBuilder.toString();
        } catch (Exception e) {
            Log.e("Error", "Error converting result " + e.toString());
        }
        return null;
    }



    private class MyASyncTask extends AsyncTask<Void,Void,Boolean>{


        @Override
        protected Boolean doInBackground(Void... params) {
            URL url = null;
            try{
                url = new URL("https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJ64R9a-Jv5kcR-BW0JxItLhI&key=AIzaSyAMHmh-S8a6-Z56aExeLzgVXzMJOgTC0E0");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                if(HttpURLConnection.HTTP_OK == conn.getResponseCode()){
                    copyInputStreamToFile(conn.getInputStream(),
                            new File(getCacheDir(), "tour.json"));
                    return Boolean.TRUE;

                }
            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Boolean.FALSE;
        }

        @Override
        protected void onPostExecute(Boolean result){

            JSONObject json = readJson();
                try {
                    toureiffel=json.getJSONObject("result").getString("name");

                } catch (JSONException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }

        }

    }



    private void copyInputStreamToFile(InputStream in, File file) {
        try{
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len = in.read(buf))>0){
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




