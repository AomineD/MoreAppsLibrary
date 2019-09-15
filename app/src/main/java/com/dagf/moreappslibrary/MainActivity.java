package com.dagf.moreappslibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dagf.moreapplibrary.AppModel;
import com.dagf.moreapplibrary.MoreAppsIU;
import com.dagf.moreapplibrary.PromotionView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MoreAppsIU.applyShrink();
       // MoreAppsIU.openIU(this, "fivmovies");
        MoreAppsIU.externalAds(this);


    new Timer().schedule(new TimerTask() {
        @Override
        public void run() {
            final AppModel mm = MoreAppsIU.getExternalPromotion();
            if(mm != null) {
                Log.e("MAIN", "run: " +mm.getAppName());

                final PromotionView p = findViewById(R.id.promotionv);

runOnUiThread(new Runnable() {
    @Override
    public void run() {
        p.startExternal(mm);
    }
});


            }else{
                Log.e("MAIN", "run: null" );
            }
        }
    }, 5500);
    }
}
