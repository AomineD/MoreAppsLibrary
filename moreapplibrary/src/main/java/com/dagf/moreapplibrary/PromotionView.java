package com.dagf.moreapplibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
}
