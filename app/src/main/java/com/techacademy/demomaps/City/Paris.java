package com.techacademy.demomaps.City;

/**
 * Created by samy on 06/06/16.
 */

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.techacademy.demomaps.AndroidGPSTrackingActivity;
import com.techacademy.demomaps.Defi.MapsActivity;
import com.techacademy.demomaps.MyListAdapter;
import com.techacademy.demomaps.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by samy on 05/06/16.
 */

public class Paris extends ListActivity {
    public static final int NOTIFICATION_ID = 1;
    private ListView list;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_dialog:

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        Paris.this);
                builder.setTitle("Bravo");
                builder.setMessage("Vous avez creer une boite de dialogue");
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
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
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Récupération automatique de la liste (l'id de cette liste est nommé obligatoirement @android:id/list afin d'être détecté)
        list = getListView();

        // Création de la ArrayList qui nous permettra de remplir la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        // On déclare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;


        map = new HashMap<String, String>();
        map.put("nom", "Monter au 1er etage de la Tour eiffel");
        map.put("prenom", "En moins d'une heure");
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("nom", "Monter au 1er etage de la Tour eiffel");
        map.put("prenom", "En moins d'une heure");
        listItem.add(map);


        //Utilisation de notre adaptateur qui se chargera de placer les valeurs de notre liste automatiquement et d'affecter un tag à nos checkbox

        MyListAdapter mSchedule = new MyListAdapter(this.getBaseContext(), listItem,
                R.layout.paris, new String[] { "nom", "prenom" }, new int[] {
                R.id.nom, R.id.prenom });

        // On attribue à notre listView l'adaptateur que l'on vient de créer
        list.setAdapter(mSchedule);
    }

    //Fonction appelée au clic d'une des checkbox
    public void MyHandler(View v) {
        CheckBox cb = (CheckBox) v;
        //on récupère la position à l'aide du tag défini dans la classe MyListAdapter
        int position = Integer.parseInt(cb.getTag().toString());

        // On récupère l'élément sur lequel on va changer la couleur
        View o = list.getChildAt(position).findViewById(
                R.id.blocCheck);

        //On change la couleur
        if (cb.isChecked()) {
            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            o.setBackgroundResource(R.color.green);

        } else {
            o.setBackgroundResource(R.color.blue);
        }
    }
}




