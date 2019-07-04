package com.dagf.moreapplibrary.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dagf.moreapplibrary.AppModel;
import com.dagf.moreapplibrary.R;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

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
        View view = LayoutInflater.from(m).inflate(R.layout.item_app_2, viewGroup, false);
        return new AppHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AppHolder appHolder, int i) {

        final AppModel model = appl.get(i);


        Picasso.get().load(Uri.parse(model.getImgBig())).fit().into(appHolder.Icon);


        appHolder.titl.setText(model.getAppName());


        appHolder.Icon.setOnClickListener(new View.OnClickListener() {
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

        public AppHolder(@NonNull View itemView) {
            super(itemView);

            titl = itemView.findViewById(R.id.firstTitle);
            Icon = itemView.findViewById(R.id.imagebig);

        }
    }

}
