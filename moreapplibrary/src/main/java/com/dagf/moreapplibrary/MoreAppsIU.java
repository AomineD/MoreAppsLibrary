package com.dagf.moreapplibrary;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.dagf.moreapplibrary.adapter.AppAdapt;
import com.dagf.moreapplibrary.adapter.AppAdapterS;
import com.dagf.moreapplibrary.dataserve.GetDataFromServer;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.Random;

public class MoreAppsIU extends AppCompatActivity {

    public static void openIU(Context m, String slug)
    {
        MoreAppsIU.slug = slug;
        m.startActivity(new Intent(m, MoreAppsIU.class));
    }

public static void loadApps(Context m){
   GetDataFromServer dataFromServer = new GetDataFromServer(m, urlServer+"api.php?videos",new GetDataFromServer.OnDataReceive() {
        @Override
        public void Correct(ArrayList<AppModel> apps) {
            aps.addAll(apps);
            for(int i=0; i < aps.size(); i++){
                if(aps.get(i).getPackagen().equals(packagenameApp) || aps.get(i).slug.equals(slug)){
                    aps.remove(i);
                    break;
                }
            }
          //  Log.e("MAIN", "Correct: "+apps.size());
            //adapterS.notifyDataSetChanged();
        }

        @Override
        public void Fail(String erno) {
            Log.e("MAIN", "Fail: "+erno);
        }
    });
}

private static AppModel promotionalExt = null;
public static void externalAds(Context m){
    GetDataFromServer dataFromServer = new GetDataFromServer(m, "https://wineberryhalley.com/secure/mrapps/cpanel/api.php?external",new GetDataFromServer.OnDataReceive() {
        @Override
        public void Correct(ArrayList<AppModel> apps) {
            if(apps.size() > 0) {
             //   apps.add(apps.get(0));
                promotionalExt = apps.get(0);
            }
            //  Log.e("MAIN", "Correct: "+apps.size());
            //adapterS.notifyDataSetChanged();
        }

        @Override
        public void Fail(String erno) {
            Log.e("MAIN", "Fail: "+erno);
        }
    });
}



    FoldingCell cell;

    private static String spinoff = "";
    public static void applySpin(){
        spinoff = "spin";
    }

    public static void applyShrink(){
        spinoff = "shrink";
    }

    public static void applySplit(){
spinoff = "split";
    }



    public static String urlServer = "https://wineberryhalley.com/secure/mrapps/cpanel/";
    public static final String packagenameApp = "com.nothing.app";
    public static String slug;
    private RecyclerView recyclerView;
    private AppAdapt adapterS;
    private GetDataFromServer dataFromServer;

    private View toolbar;
    private static ArrayList<AppModel> aps = new ArrayList<>();
    private static ArrayList<AppModel> promotionals = new ArrayList<>();

    public static AppModel getPromotion(){
        AppModel thisare = null;

        ArrayList<Integer> s = new ArrayList<>();
        if(promotionals.size() < 1) {
            for (int i = 0; i < aps.size(); i++) {
                if (aps.get(i).status > 1) {
                    int index = (aps.get(i).status * randInt(0, 6)) + randInt(55, 80);
                    if (index > 60) {
                        s.add(index);
                        promotionals.add(aps.get(i));
                    }
                }
            }

            for (int i = 0; i < aps.size(); i++) {
                if (aps.get(i).status > 0) {
                    int index = (aps.get(i).status * randInt(1, 6)) + randInt(55, 80);
                    if (index > 60 && !promotionals.contains(aps.get(i))) {
                        s.add(index);
                        promotionals.add(aps.get(i));
                    }
                }
            }
        }

        if(promotionals.size() > 0) {
            for (int i = 0; i < s.size(); i++) {
          //     Log.e("MAIN", "getPromotion: "+s.get(i) );
                if (s.get(i) > randInt(60, 85) && !promotionals.get(i).yaSalio) {

                    thisare = promotionals.get(i);
                    promotionals.get(i).yaSalio = true;
                    break;
                }else{
                    promotionals.get(i).yaSalio = false;
                }
            }
        }

        return thisare;
    }


    public static AppModel getExternalPromotion(){



        return promotionalExt;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_apps_iu);

        recyclerView = findViewById(R.id.list_app);

        toolbar = findViewById(R.id.tolb);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*if(getSupportActionBar() == null){
            setSupportActionBar(toolbar);


          //  getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }*/

       GridLayoutManager li = new GridLayoutManager(this, 3);
       adapterS = new AppAdapt(this, aps, new AppAdapt.ClickApp() {
           @Override
           public void OnClickApp(AppModel app, ImageView v) {
               //Toast.makeText(MoreAppsIU.this, "SI", Toast.LENGTH_SHORT).show();

               if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                   ViewAppActivity.appn = app;

                  // ActivityOptions o = ActivityOptions.makeSceneTransitionAnimation(MoreAppsIU.this, v, "img");

                   startActivity(new Intent(MoreAppsIU.this, ViewAppActivity.class));
               }else{
                   ViewAppActivity.appn = app;
                   startActivity(new Intent(MoreAppsIU.this, ViewAppActivity.class));
               }
               switch (spinoff) {
                   case "spin":
                   Animatoo.animateSpin(MoreAppsIU.this);
               break;
                   case "shrink":
                       Animatoo.animateShrink(MoreAppsIU.this);
                       break;
                   case "split":
                       Animatoo.animateSplit(MoreAppsIU.this);
                       break;
                       default:
                           Animatoo.animateInAndOut(MoreAppsIU.this);
               }
               }
       });

      //  recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //recyclerView.setLayoutManager(new CardSliderLayoutManager(this));

     //   new CardSnapHelper().attachToRecyclerView(recyclerView);

       recyclerView.setLayoutManager(li);
        recyclerView.setAdapter(adapterS);

     //   dataFromServer.setUrlM(urlServer);

        if(aps.size() < 1) {
            dataFromServer = new GetDataFromServer(this, urlServer + "api.php?videos", new GetDataFromServer.OnDataReceive() {
                @Override
                public void Correct(ArrayList<AppModel> apps) {
                    aps.addAll(apps);
                    for (int i = 0; i < aps.size(); i++) {
                        if (aps.get(i).getPackagen().equals(packagenameApp) || aps.get(i).slug.equals(slug)) {
                            aps.remove(i);
                            break;
                        }
                    }
                    Log.e("MAIN", "Correct: " + apps.size());
                    adapterS.notifyDataSetChanged();
                }

                @Override
                public void Fail(String erno) {
                    Log.e("MAIN", "Fail: " + erno);
                }
            });

        }
//
   //     fr.setCustomAnimations

    }










    public static int randInt(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        //
        // In particular, do NOT do 'Random rand = new Random()' here or you
        // will get not very good / not very random results.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
