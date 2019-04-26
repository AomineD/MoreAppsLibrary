package com.dagf.moreapplibrary;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.BlurTransformation;

public class ViewAppActivity extends AppCompatActivity {

    private TextView title_2;
    private WebView desc_2;
    private ImageView Icon;
    private TextView action;


   public static AppModel appn = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_app);



        title_2 = findViewById(R.id.secondTitle);
        desc_2 = findViewById(R.id.secondDesc);
        Icon = findViewById(R.id.imagebig);
        action = findViewById(R.id.action1);
        desc_2.setBackgroundColor(getResources().getColor(R.color.tran));

    


        if(appn != null)
        {
title_2.setText(appn.getAppName());

            desc_2.loadData(
                    "<font color='white'>"+
                    appn.getAppDesc()
+"</font>"
                    , "text/html; charset=UTF-8", null);

            Picasso.get().load(Uri.parse(appn.getImgBig())).fit().into(Icon);

            ImageView f = findViewById(R.id.bg);

            Picasso.get().load(Uri.parse(appn.getImgSmall())).transform(new BlurTransformation(this, 9, 12)).fit().into(f);


            action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+appn.getPackagen())));
                }
            });

        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Animatoo.animateWindmill(this);
    }
}
