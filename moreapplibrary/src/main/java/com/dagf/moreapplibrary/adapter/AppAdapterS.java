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

public class AppAdapterS extends RecyclerView.Adapter<AppAdapterS.AppHolder> {

    private ArrayList<AppModel> appl = new ArrayList<>();

    private Context m;

    public AppAdapterS(Context context, ArrayList<AppModel> appModels){
        this.appl = appModels;
        this.m = context;
    }

    @NonNull
    @Override
    public AppHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(m).inflate(R.layout.item_app, viewGroup, false);
        return new AppHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppHolder appHolder, int i) {

        final AppModel model = appl.get(i);

        appHolder.title_1.setText(appl.get(i).getAppName());
        appHolder.desc_1.setText(model.getAppDescShort());
        Picasso.get().load(Uri.parse(model.getImgSmall())).fit().into(appHolder.Thumb);

        appHolder.title_2.setText(appl.get(i).getAppName());
        appHolder.desc_2.loadData(model.getAppDesc(), "text/html; charset=UTF-8", null);
        Picasso.get().load(Uri.parse(model.getImgBig())).fit().into(appHolder.Icon);


        appHolder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+model.getPackagen())));
            }
        });

    }

    @Override
    public int getItemCount() {
        return appl.size();
    }

    class AppHolder extends RecyclerView.ViewHolder{

        private FoldingCell cell;

        // CLOSE

        private TextView title_1;
        private TextView desc_1;
        private ImageView Thumb;


        //OPEN

        private TextView title_2;
        private WebView desc_2;
        private ImageView Icon;
        private TextView action;

        public AppHolder(@NonNull View itemView) {
            super(itemView);

            title_2 = itemView.findViewById(R.id.secondTitle);
            desc_2 = itemView.findViewById(R.id.secondDesc);
            Icon = itemView.findViewById(R.id.imagebig);
            action = itemView.findViewById(R.id.action);

            title_1 = itemView.findViewById(R.id.firstTitle);
            desc_1 = itemView.findViewById(R.id.firstDesc);
            Thumb = itemView.findViewById(R.id.thumb);

            desc_2.getSettings();
            desc_2.setBackgroundColor(m.getResources().getColor(R.color.colorPrimaryDarkw));

            cell = (FoldingCell) itemView;
            cell.initialize(30, 1000, m.getResources().getColor(R.color.colorAccentw), 2);
            cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cell.toggle(false);
                }
            });
        }
    }

}
