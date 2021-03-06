package com.dagf.moreapplibrary;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.dagf.admobnativeloader.EasyFAN;
import com.dagf.admobnativeloader.EasyNativeLoader;
import com.dagf.moreapplibrary.adapter.AppAdapt;
import com.dagf.moreapplibrary.dataserve.GetDataFromServer;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.material.tabs.TabLayout;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MoreAppsIU extends AppCompatActivity {

    public static int maxIdsNatives = 0;
    public static void openIU(Context m, EasyFAN easyFAN, int maxIds,String slug)
    {
        easyFANMoreApps = easyFAN;
        maxIdsNatives = maxIds;
        MoreAppsIU.slug = slug;
        m.startActivity(new Intent(m, MoreAppsIU.class));
    }

    public static void openIU(Context m, EasyNativeLoader easyFAN, int maxIds, String slug)
    {
        easyNativeLoader = easyFAN;
        maxIdsNatives = maxIds;
        MoreAppsIU.slug = slug;
        m.startActivity(new Intent(m, MoreAppsIU.class));
    }

    public static void openIU(Context m, String slug, String banner_id_ad, TypeAd t)
    {
        banner_id = banner_id_ad;
        typeAd = t;
        MoreAppsIU.slug = slug;
        m.startActivity(new Intent(m, MoreAppsIU.class));
    }

    private static String banner_id;
    private static TypeAd typeAd;

    public static EasyNativeLoader easyNativeLoader;

    public static EasyFAN easyFANMoreApps;

public static void loadApps(Context m){
        aps.clear();
    //Log.e("MAIN", "loadApps: "+urlActual );

    urlActual = urlServer + "api.php?videos";

   GetDataFromServer dataFromServer = new GetDataFromServer(m, urlActual,new GetDataFromServer.OnDataReceive() {
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


private View ic_apps_icon;
    public void loadApplications(Context m){
        aps.clear();
        lottieLoading.setVisibility(View.VISIBLE);
        ic_apps_icon.setVisibility(View.GONE);
        Log.e("MAIN", "loadAppls: "+urlActual );
        GetDataFromServer dataFromServer = new GetDataFromServer(m, urlActual,new GetDataFromServer.OnDataReceive() {
            @Override
            public void Correct(ArrayList<AppModel> apps) {
                aps.addAll(apps);
                for(int i=0; i < aps.size(); i++){
                    if(aps.get(i).getPackagen().equals(packagenameApp) || aps.get(i).slug.equals(slug)){
                        aps.remove(i);
                       // Log.e("MAIN", "Correct: s" );
                        break;
                    }
                }
               // Log.e("MAIN", "Correct: "+aps.size() );
                adapterS.notifyDataSetChanged();
               final Timer tm = new Timer();
                if(easyNativeLoader != null) {
                    tm.schedule(new TimerTask() {
                        @Override
                        public void run() {
                 if(!easyNativeLoader.isLoading){
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             Log.e("MAIN", "run: LISTOOOX" );
                             adapterS.notifyDataSetChanged();
                         }
                     });
                     tm.cancel();
                 }
                        }
                    }, 2000, 1500);
                }
lottieLoading.setVisibility(View.GONE);
                ic_apps_icon.setVisibility(View.VISIBLE);

                //  Log.e("MAIN", "Correct: "+apps.size());
                //adapterS.notifyDataSetChanged();
            }

            @Override
            public void Fail(String erno) {
                Log.e("MAIN", "Fail: "+erno);
                lottieLoading.setVisibility(View.GONE);
                ic_apps_icon.setVisibility(View.VISIBLE);
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
    private static String urlActual = urlServer + "api.php?videos";
    public static final String packagenameApp = "com.nothing.app";
    public static String slug;
    private RecyclerView recyclerView;
    private TabLayout tabLayout;
    private LottieAnimationView lottieLoading;
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

    public enum TypeAd{
        Facebook,
        Google
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_apps_iu);

        recyclerView = findViewById(R.id.list_app);
        //AdSettings.setDebugBuild(true);

        LinearLayout l = findViewById(R.id.banner);

        if(banner_id != null && !banner_id.isEmpty()){
            if(typeAd == TypeAd.Facebook){
                AdView adView = new AdView(this, banner_id, AdSize.BANNER_HEIGHT_50);
          //      Log.e("MAIN", "onCreate: "+banner_id );
                adView.loadAd(adView.buildLoadAdConfig().withAdListener(new AdListener() {
                    @Override
                    public void onError(Ad ad, AdError adError) {
                        Log.e("MAIN", "onError: "+adError.getErrorMessage() );
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        Log.e("MAIN", "onAdLoaded: loaded" );
                    }

                    @Override
                    public void onAdClicked(Ad ad) {

                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }
                }).build());
                l.addView(adView);
            }else{
                com.google.android.gms.ads.AdView adView = new com.google.android.gms.ads.AdView(this);
                adView.setAdUnitId(banner_id);
                adView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
                adView.loadAd(new AdRequest.Builder().build());
                Log.e("MAIN", "onCreate: "+banner_id );
                l.addView(adView);
            }
        }

        toolbar = findViewById(R.id.tolb);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ic_apps_icon = findViewById(R.id.ic_apps);
        lottieLoading = findViewById(R.id.loading_lott);

        tabLayout = findViewById(R.id.tablay);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.featured)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.more_rated)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.more_downloads)));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               loadNow(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        urlActual = urlServer + "api.php?get_premier";

        /*if(getSupportActionBar() == null){
            setSupportActionBar(toolbar);


          //  getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }*/

       LinearLayoutManager li = new LinearLayoutManager(this);
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
   loadApplications(this);

        }
//
   //     fr.setCustomAnimations

    }

    private void loadNow(int position) {

        switch (position){
            case 0:
urlActual = urlServer + "api.php?get_premier";
                loadApplications(this);
                break;
            case 1:
                urlActual = urlServer + "api.php?get_rated";
                loadApplications(this);
                break;
            case 2:
                urlActual = urlServer + "api.php?get_downloaded";
                loadApplications(this);
                break;
            default:
                urlActual = urlServer + "api.php?get_premier";
                loadApplications(this);
        }

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
