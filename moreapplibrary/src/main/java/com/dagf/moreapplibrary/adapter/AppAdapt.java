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
import com.dagf.moreapplibrary.R;
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

      //  Log.e("MAIN", "onBindViewHolder: "+model.getImgBig() );
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

        public AppHolder(@NonNull View itemView) {
            super(itemView);

            titl = itemView.findViewById(R.id.firstTitle);
            Icon = itemView.findViewById(R.id.imagebig);
desc = itemView.findViewById(R.id.firstDesc);
        }
    }

}
