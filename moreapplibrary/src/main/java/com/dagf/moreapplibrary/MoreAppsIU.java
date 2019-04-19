package com.dagf.moreapplibrary;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.dagf.moreapplibrary.adapter.AppAdapterS;
import com.dagf.moreapplibrary.dataserve.GetDataFromServer;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

public class MoreAppsIU extends AppCompatActivity {

    public static void openIU(Context m)
    {
        m.startActivity(new Intent(m, MoreAppsIU.class));
    }

    FoldingCell cell;

    public static String urlServer = "http://wineberryhalley.com/secure/mrapps/cpanel/api.php?videos";
    private RecyclerView recyclerView;
    private AppAdapterS adapterS;
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

        LinearLayoutManager li = new LinearLayoutManager(this);

        adapterS = new AppAdapterS(this, aps);

        recyclerView.setLayoutManager(li);
        recyclerView.setAdapter(adapterS);

     //   dataFromServer.setUrlM(urlServer);

        dataFromServer = new GetDataFromServer(this, urlServer,new GetDataFromServer.OnDataReceive() {
            @Override
            public void Correct(ArrayList<AppModel> apps) {
                aps.addAll(apps);
                adapterS.notifyDataSetChanged();
            }

            @Override
            public void Fail(String erno) {
                Log.e("MAIN", "Fail: "+erno);
            }
        });

    }
}
