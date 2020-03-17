package com.dagf.moreapplibrary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dagf.moreapplibrary.dataserve.GetDataFromServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.dagf.moreapplibrary.MoreAppsIU.packagenameApp;
import static com.dagf.moreapplibrary.MoreAppsIU.slug;

public class IntersticialApp {
    public static class IntersticialObj {
        public String id_app;
        public String titleAd;
        public String descAd;
        public String actionAd;
        public String url_img1;
        public String url_img2;
        public String url_icon;
        public String url_img3;
        public String urltoApp;
        public int frecuency;
        public float rate;
        public onLoadListenerPromo listenerPromo;
    }


    /** Metodos para mostrar y cargar **/


    private String url_server = MoreAppsIU.urlServer+"api.php?";

    RequestQueue queue;
    Context context;

    public void setListenerPromo(onLoadListenerPromo listenerPromo) {
        this.listenerPromo = listenerPromo;
    }

    onLoadListenerPromo listenerPromo;



    public IntersticialApp(Context context){
        this.context = context;
        queue = Volley.newRequestQueue(context);

        SharedPreferences preferences = context.getSharedPreferences("inters_pref", Context.MODE_PRIVATE);

        frecuency = preferences.getInt(key_sv, 0);
        what_inters = preferences.getInt(key_inters_selected, 0);

    }

ArrayList<IntersticialObj> aps = new ArrayList<>();
    public void loadAds(){
   //     Log.e(TAG, "loadAd: " );
        GetDataFromServer dataFromServer = new GetDataFromServer(context, url_server + "get_ins", new GetDataFromServer.onIntersticialListener() {
            @Override
            public void Correct(ArrayList<IntersticialObj> apps) {

                aps.addAll(apps);

                for(int i=0; i < apps.size(); i++){
                    if(max < apps.get(i).frecuency){
                        max = apps.get(i).frecuency;
                    }
                }

                if(listenerPromo != null){
                    listenerPromo.onLoadSuccess();
                }
            }

            @Override
            public void Fail(String erno) {

            }
        });

    }



    private static int what_inters = 0;
    public void showAd(){

    IntersticialObj ob = aps.get(what_inters);

        if(ob.urltoApp.contains(context.getPackageName())){
            changeSelected();
        }
        SharedPreferences preferences = context.getSharedPreferences("inters_pref", Context.MODE_PRIVATE);
        //Log.e("MAIN", "showAd: "+frecuency + " ? "+what_inters);

        preferences.edit().putInt(key_soon_select, ob.frecuency).apply();

    if (!ob.id_app.isEmpty() && !ob.urltoApp.isEmpty() && frecuency >= ob.frecuency) {
        ob.listenerPromo = listenerPromo;
        IntersticialPromo.intersticialApp = ob;
        context.startActivity(new Intent(context, IntersticialPromo.class));
        if(listenerPromo != null){
            listenerPromo.onShowIntersticial();
        }


        frecuency = 0;

        preferences.edit().putInt(key_sv, frecuency).commit();
        addImpression(ob);

        changeSelected();

    }else{
        generateFrecuency();
    }




    }

    private void addImpression(final IntersticialObj obj){
        String url = url_server+"add_impresion&appID="+obj.id_app;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             //   Log.e("MAIN", "onResponse: impresion " +obj.urltoApp);
                if(listenerPromo != null){
                    listenerPromo.onImpressionSuccess();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MAIN", "onErrorResponse: "+error.getMessage() );
            }
        });

        queue.add(request);
    }


    private static String key_sv = "JASMDASLDWWWWW";
    private String key_inters_selected = "JAJABRONO";
    private static String key_soon_select = "NNNWWWMMEK";
    private int frecuency = 0;
    private int max = 0;

    private void generateFrecuency(){
        frecuency++;
        SharedPreferences preferences = context.getSharedPreferences("inters_pref", Context.MODE_PRIVATE);

        preferences.edit().putInt(key_sv, frecuency).commit();
    }

    private void changeSelected(){
        what_inters++;
        if(what_inters >= aps.size()){
            what_inters = 0;
        }

        SharedPreferences preferences = context.getSharedPreferences("inters_pref", Context.MODE_PRIVATE);


        preferences.edit().putInt(key_inters_selected, what_inters).commit();
    }



    public interface onLoadListenerPromo{
        void onLoadSuccess();
        void onShowIntersticial();
        void onImpressionSuccess();
        void onFailed(String erno);
        void onClosed();
    }

    public static boolean isReadytoShow(Context c){
        SharedPreferences preferences = c.getSharedPreferences("inters_pref", Context.MODE_PRIVATE);

int actual_int_frec = preferences.getInt(key_soon_select, 0);

        return preferences.getInt(key_sv, 0) >= actual_int_frec && actual_int_frec != 0;

    };
}
