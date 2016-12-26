package com.techacademy.demomaps.City;

/**
 * Created by samy on 06/06/16.
 */

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.techacademy.demomaps.Defi.MarseilleDefi;
import com.techacademy.demomaps.MyListAdapter;
import com.techacademy.demomaps.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by samy on 05/06/16.
 */
public class Marseille extends ListActivity {
    private ListView list;
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
        map.put("nom", "Visite la canebiere");
        map.put("prenom", "Unknown");
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
            startActivity(new Intent(getApplicationContext(), MarseilleDefi.class));
            o.setBackgroundResource(R.color.green);

        } else {
            o.setBackgroundResource(R.color.blue);
        }
    }
}




