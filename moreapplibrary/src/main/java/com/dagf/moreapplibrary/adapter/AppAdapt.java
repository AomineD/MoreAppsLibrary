package com.dagf.moreapplibrary.adapter;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dagf.moreapplibrary.AppModel;
import com.dagf.moreapplibrary.MoreAppsIU;
import com.dagf.moreapplibrary.R;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AppAdapt extends RecyclerView.Adapter<AppAdapt.AppHolder> {

    private ArrayList<AppModel> appl = new ArrayList<>();

    private Context m;

    public interface ClickApp{
        void OnClickApp(AppModel app, ImageView v);
    }



    private ClickApp onClickapp;

    public AppAdapt(Context context, ArrayList<AppModel> appModels, ClickApp clickApp){
        this.appl = appModels;
        this.m = context;
        this.onClickapp = clickApp;
    }

    @NonNull
    @Override
    public AppHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(m).inflate(R.layout.item_app_3, viewGroup, false);
        return new AppHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AppHolder appHolder, int i) {

        final AppModel model = appl.get(i);
if(MoreAppsIU.easyNativeLoader == null && MoreAppsIU.easyFANMoreApps == null){
    appHolder.nativ.setVisibility(View.GONE);
    appHolder.nativ2.setVisibility(View.GONE);
}else {
    if (posnativ >= MoreAppsIU.maxIdsNatives) {
        posnativ = 0;
    }


    if ((i + 1) % 3 != 0) {
        appHolder.nativ.setVisibility(View.GONE);
        appHolder.nativ2.setVisibility(View.GONE);
    } else {
        //  appHolder.nativ.setVisibility(View.VISIBLE);
        //   appHolder.nativ2.setVisibility(View.VISIBLE);

        makeNativeGreat(appHolder);
    }
}
            Picasso.get().load(Uri.parse(model.getImgBig())).fit().into(appHolder.Icon);


            appHolder.titl.setText(model.getAppName());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                appHolder.desc.setText(Html.fromHtml(model.getAppDesc(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                appHolder.desc.setText(Html.fromHtml(model.getAppDesc()));
            }

            appHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickapp.OnClickApp(model, appHolder.Icon);
                }
            });
    }

    @Override
    public int getItemCount() {
        return appl.size();
    }

    class AppHolder extends RecyclerView.ViewHolder{


        private ImageView Icon;
        private TextView titl;
        private TextView desc;
        private View normal_v;
        private View nativ, nativ2, natadmob;

        public AppHolder(@NonNull View itemView) {
            super(itemView);

            nativ = itemView.findViewById(R.id.native1);
            nativ2 = itemView.findViewById(R.id.native2);
            natadmob = itemView.findViewById(R.id.natvadmob);
            titl = itemView.findViewById(R.id.firstTitle);
            Icon = itemView.findViewById(R.id.imagebig);
desc = itemView.findViewById(R.id.firstDesc);
        }
    }

    
    private int posnativ = 0;
    
    private void makeNativeGreat(AppHolder holder) {

        if (MoreAppsIU.easyFANMoreApps != null) {
            holder.nativ2.setVisibility(View.GONE);
            if (MoreAppsIU.easyFANMoreApps.isLoadedBanner(posnativ)) {
                holder.nativ.setVisibility(View.VISIBLE);
                MoreAppsIU.easyFANMoreApps.setupNativeView(holder.nativ, posnativ, m.getResources().getColor(R.color.white), m.getResources().getColor(R.color.black));
                posnativ++;
            } else {

                if (posnativ < 4 && posnativ > 0) {
                    posnativ++;
                } else {
                    posnativ = 0;
                }


                if (MoreAppsIU.easyFANMoreApps.isLoadedBanner(posnativ)) {
                    holder.nativ.setVisibility(View.VISIBLE);
                    MoreAppsIU.easyFANMoreApps.setupNativeView(holder.nativ, posnativ, m.getResources().getColor(R.color.white), m.getResources().getColor(R.color.black));
                    posnativ++;
                } else {
                    Log.e("MAIN", "makeNativeGreat: ni este  a cargado " + posnativ);
                    holder.nativ.setVisibility(View.GONE);
                }
            }
        } else if (MoreAppsIU.easyNativeLoader != null) {

            holder.nativ.setVisibility(View.GONE);

if(!MoreAppsIU.easyNativeLoader.isLoading) {
    holder.nativ2.setVisibility(View.VISIBLE);
    if(posnativ >= MoreAppsIU.maxIdsNatives){
        posnativ = 0;
    }

  UnifiedNativeAd un = MoreAppsIU.easyNativeLoader.getNat(posnativ);

    MoreAppsIU.easyNativeLoader.populateUnifiedNativeAdView(un, (UnifiedNativeAdView) holder.natadmob);

    if (posnativ < MoreAppsIU.maxIdsNatives && posnativ >= 0) {
        posnativ++;
    }
}



            }

        }


}
