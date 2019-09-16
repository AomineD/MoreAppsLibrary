package com.dagf.moreapplibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Random;

public class PromotionView extends RelativeLayout {
    public PromotionView(Context context) {
        super(context);
    }

    public PromotionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void startLoad(AppModel model){
        View d = LayoutInflater.from(getContext()).inflate(R.layout.promotion_lay, (ViewGroup) getRootView(), false);
      SetupPromotion(model, d);
        addView(d);
    }

    public void startExternal(AppModel model, View native_normal, int randu){
        randomness(native_normal, randu);
        View d = LayoutInflater.from(getContext()).inflate(R.layout.native_ad_promotion, (ViewGroup) getRootView(), false);
        SetupExternalPromtoion(model, d);
        addView(d);
    }

    private void SetupPromotion(final AppModel model, View d) {

        TextView title = d.findViewById(R.id.title_app);

        WebView desc = d.findViewById(R.id.desc_app);

        final ImageView icon = d.findViewById(R.id.app_icon);

        final ImageView content = d.findViewById(R.id.contentImg);

        ((Activity)getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Picasso.get().load(Uri.parse(model.getImgBig())).fit().into(icon);

                Picasso.get().load(Uri.parse(model.getPromotional())).fit().into(content);
            }
        });


        title.setText(model.getAppName());
        desc.loadData(
                "<body style='text-align:justify;color:black;background-color:white;'>"+
                        model.getAppDesc()
                        +"</body>"
                , "text/html; charset=UTF-8", null);

        d.findViewById(R.id.install).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.getPackagen().startsWith("com")){
                    getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+model.getPackagen())));

                }else if(model.getPackagen().startsWith("http")){
                    getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(model.getPackagen())));
                }
            }
        });

    }

    private ImageView mediaView;
    private ImageView iconView;
    private void SetupExternalPromtoion(final AppModel model, View d) {



      /*  desc.loadData(
                "<body style='text-align:justify;color:black;background-color:white;'>"+
                        model.getAppDesc()
                        +"</body>"
                , "text/html; charset=UTF-8", null); */




        final TextView action;
        final TextView title_ad;
        final TextView desc_ad;
        final ImageView ad_choices;
        final TextView sponsor;
        final CardView button_action;
        final CardView background;


        action = d.findViewById(R.id.callto);
        title_ad = d.findViewById(R.id.title_ad);
        sponsor = d.findViewById(R.id.sponsor_ad);
        desc_ad = d.findViewById(R.id.sponsor_adw);
        //      normal_view = itemView.findViewById(R.id.normal_view);

        button_action = d.findViewById(R.id.button_action);
        try {
            mediaView = d.findViewById(R.id.media_view);
            iconView = d.findViewById(R.id.ad_icon_view);

            ((Activity)getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Picasso.get().load(Uri.parse(model.getImgBig())).fit().into(iconView);

                    Picasso.get().load(Uri.parse(model.getPromotional())).fit().into(mediaView);
                }
            });
        } catch (Exception e) {
          //  iconView1 = d.findViewById(R.id.ad_icon_view);
        }

        background = d.findViewById(R.id.card);
        ad_choices = d.findViewById(R.id.ad_choices);
        background.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        int white = getContext().getResources().getColor(R.color.white);
        int black = getContext().getResources().getColor(R.color.black);
        desc_ad.setTextColor(black);
        title_ad.setTextColor(black);
        sponsor.setTextColor(black);
        button_action.setCardBackgroundColor(white);
        action.setTextColor(black);

        if (true) {
            background.setRadius(0);
        }

            String title = model.getAppName();
            String provider = "Publicidad";
            String boton_action = "Registrarse";
            String patrocinador = model.getAppDescShort();
            String sp = "Anuncio";

            //  com.facebook.ads.AdChoicesView adChoicesView = new com.facebook.ads.AdChoicesView(context, nativeAd, true);
                Picasso.get().load(Uri.parse("http://g3ekarmy.com/wp-content/uploads/2015/10/adchoices1.png")).fit().into(ad_choices);

            title_ad.setText(title);
            desc_ad.setText(patrocinador);

            sponsor.setText(sp);

            //    ad_choices.addView(adChoicesView, 0);
            //  Log.e("MAIN", "setupViews: COLOR => "+textco);
            action.setText(boton_action);

            action.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(model.getPackagen().startsWith("com")){
                        getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+model.getPackagen())));

                    }else if(model.getPackagen().startsWith("http")){
                        getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(model.getPackagen())));
                    }
                }
            });

            mediaView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(model.getPackagen().startsWith("com")){
                        getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+model.getPackagen())));

                    }else if(model.getPackagen().startsWith("http")){
                        getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(model.getPackagen())));
                    }
                }
            });

        }


        private void randomness(View native_normal, int randposs){

            Random r = new Random();
            int randomNum = r.nextInt((10 - 1) + 1) + 1;

            if(randomNum >= randposs){
                native_normal.setVisibility(GONE);
                setVisibility(VISIBLE);
            }else{
               setVisibility(GONE);
            }


        }
}
