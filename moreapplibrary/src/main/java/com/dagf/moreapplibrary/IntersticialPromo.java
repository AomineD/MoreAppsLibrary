package com.dagf.moreapplibrary;

import android.animation.Animator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dagf.moreapplibrary.adapter.SliderAdapterExample;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class IntersticialPromo extends AppCompatActivity {

    private String url_server = MoreAppsIU.urlServer+"api.php?";

    public IntersticialPromo(){

    }


private static int round = 0;
    private static int maxround = 0;
    /**
    Cargar Intersticial ahora
    **/
     public void loadAd(){

         StringRequest request = new StringRequest(Request.Method.GET, url_server+"get_ins", new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {

                 try {
                     JSONObject object = new JSONObject(response);



                 } catch (JSONException e) {
                     e.printStackTrace();

                 }


                 addImpression();

             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {

             }
         });

         queue.add(request);







     }


     public void showAd(Context context){

         if(intersticialApp != null){
             context.startActivity(new Intent(context, IntersticialPromo.class));
             addImpression();
         }

     }

    // The expansion points is where the animation starts
    private void circularRevealCard() {
        // Radius is whichever dimension is the longest on our screen
        float finalRadius = Math.max(close_btn.getWidth(), close_btn.getHeight());

        // Start circular animation
        Animator circularReveal = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            circularReveal = ViewAnimationUtils.createCircularReveal(close_btn, (int)close_btn.getX(), (int)close_btn.getY(), 0, finalRadius * 1.1f);
            circularReveal.setDuration(seconds_to_close * 1000);
            close_btn.setVisibility(View.VISIBLE);
            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    close_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(IntersticialPromo.this, "Wait...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //  Log.e("MAIN", "onAnimationEnd: close" );
                    close_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            circularReveal.start();
        }else{

           Animation animation = AnimationUtils.loadAnimation(this, R.anim.animate_fade_enter);

           animation.setAnimationListener(new Animation.AnimationListener() {
               @Override
               public void onAnimationStart(Animation animation) {
                   close_btn.setVisibility(View.VISIBLE);
                   close_btn.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Toast.makeText(IntersticialPromo.this, "Wait...", Toast.LENGTH_SHORT).show();
                       }
                   });
               }

               @Override
               public void onAnimationEnd(Animation animation) {
                   close_btn.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           finish();
                       }
                   });
               }

               @Override
               public void onAnimationRepeat(Animation animation) {

               }
           });

           close_btn.startAnimation(animation);
        }


        // make the view visible and start the animation


    }

    RequestQueue queue;
    private View close_btn;
    private SliderView imageSliderView;
    private RatingBar ratingBar;
    private ImageView imgIntersticial;
    private static IntersticialApp intersticialApp;




    public static int seconds_to_close = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intersticial_promo);

        queue = Volley.newRequestQueue(this);

       setViews();
    }

    private void setViews() {
        close_btn = findViewById(R.id.close_btn);

        imageSliderView = findViewById(R.id.imageSlider);
        imgIntersticial = findViewById(R.id.img_intersticial);
        //TextView textView = findViewById(R.id.action_app_text);


        if(intersticialApp != null) {

           // textView.setText(intersticialApp.actionAd);


            if (intersticialApp.url_img2 != null && !intersticialApp.url_img2.equals("0") && !intersticialApp.url_img2.isEmpty()) {
                imgIntersticial.setVisibility(View.GONE);
                TextView title_ad = findViewById(R.id.title_ad);
                title_ad.setVisibility(View.VISIBLE);
                TextView desc_ad = findViewById(R.id.desc_app);
desc_ad.setVisibility(View.VISIBLE);
                title_ad.setText(intersticialApp.titleAd);
                desc_ad.setText(intersticialApp.descAd);
                ratingBar.setRating(intersticialApp.rate);
                imageSliderView.startAutoCycle();
                imageSliderView.setVisibility(View.VISIBLE);
                ratingBar.setVisibility(View.VISIBLE);
                imageSliderView.setIndicatorAnimation(IndicatorAnimations.FILL);
                imageSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

                ImageView icon = findViewById(R.id.app_img);
                icon.setVisibility(View.VISIBLE);
                Picasso.get().load(Uri.parse(intersticialApp.url_icon)).into(icon);


                SliderAdapterExample sliderAdapterExample = new SliderAdapterExample(this);

                if (intersticialApp.url_img1 != null && !intersticialApp.url_img1.equals("0"))
                    sliderAdapterExample.addItem(intersticialApp.url_img1);
                if (intersticialApp.url_img2 != null && !intersticialApp.url_img2.equals("0"))
                    sliderAdapterExample.addItem(intersticialApp.url_img2);
                if (intersticialApp.url_img3 != null && !intersticialApp.url_img3.equals("0"))
                    sliderAdapterExample.addItem(intersticialApp.url_img3);

                imageSliderView.setSliderAdapter(sliderAdapterExample);


            } else {
                Picasso.get().load(Uri.parse(intersticialApp.url_img1)).fit().into(imgIntersticial);
            }

            if (!intersticialApp.urltoApp.isEmpty()) {

                findViewById(R.id.downloadApp).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addVisit();
                       // Toast.makeText(IntersticialPromo.this, "click", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intersticialApp.urltoApp)));
                    }
                });

            }
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        circularRevealCard();
                    }
                });
            }
        }, 2000);


        ratingBar = findViewById(R.id.rate_bar);

ratingBar.setIsIndicator(true);




    }


    /**
     * Esta funcion es para añadir una impresion al anuncio
     *
     */

    private void addImpression(){
        String url = url_server+"add_impresion&appID="+intersticialApp.id_app;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("MAIN", "onResponse: impresion" );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MAIN", "onErrorResponse: "+error.getMessage() );
            }
        });

        queue.add(request);
    }


    /**
     * Esta funcion es para cuando se hace click a un intersticial
     *
     *
     */
    private void addVisit(){
        String url = url_server+"add_visits&appID="+intersticialApp.id_app;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("MAIN", "onResponse: visita" );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MAIN", "onErrorResponse: "+error.getMessage() );
            }
        });

        queue.add(request);
    }

}
