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

public class MoreAppsIU extends AppCompatActivity {

    public static void openIU(Context m, String slug)
    {
        MoreAppsIU.slug = slug;
        m.startActivity(new Intent(m, MoreAppsIU.class));
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



    public static String urlServer = "http://wineberryhalley.com/secure/mrapps/cpanel/";
    public static final String packagenameApp = "com.nothing.app";
    public static String slug;
    private RecyclerView recyclerView;
    private AppAdapt adapterS;
    private GetDataFromServer dataFromServer;

    private View toolbar;
    private ArrayList<AppModel> aps = new ArrayList<>();

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

        dataFromServer = new GetDataFromServer(this, urlServer+"api.php?videos",new GetDataFromServer.OnDataReceive() {
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
                adapterS.notifyDataSetChanged();
            }

            @Override
            public void Fail(String erno) {
                Log.e("MAIN", "Fail: "+erno);
            }
        });


//
   //     fr.setCustomAnimations

    }
}
