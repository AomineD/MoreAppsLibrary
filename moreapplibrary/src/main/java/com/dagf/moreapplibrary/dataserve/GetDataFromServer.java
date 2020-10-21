package com.dagf.moreapplibrary.dataserve;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dagf.moreapplibrary.AppModel;
import com.dagf.moreapplibrary.IntersticialApp;
import com.dagf.moreapplibrary.MoreAppsIU;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.Float.parseFloat;

public class GetDataFromServer {
/** ==================== OBTENER INTERSTICIALS ============================= **/
public interface onIntersticialListener{
    void Correct(ArrayList<IntersticialApp.IntersticialObj> apps);
    void Fail(String erno);
}

private onIntersticialListener intersticialListener;
    public GetDataFromServer(Context m, String urlM,final onIntersticialListener listener){
        this.intersticialListener = listener;
        //  Log.e("MAIN", "GetDataFromServer: "+urlM );
        urr = urlM;
        RequestQueue queue = Volley.newRequestQueue(m);

        StringRequest request = new StringRequest(Request.Method.GET, urlM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                intersticialListener.Correct(GetInsFromJson(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                intersticialListener.Fail(error.getMessage());
            }
        });

        queue.add(request);


    }


    private ArrayList<IntersticialApp.IntersticialObj> GetInsFromJson(String js){
        ArrayList<IntersticialApp.IntersticialObj> ls = new ArrayList<>();

        try {
            JSONObject object = new JSONObject(js);

            //   Log.e("MAIN", "GetFromJson: para la url = " +urr + " estejson " +object.toString() );

            JSONArray array = object.getJSONArray("MY_TUBE_APP");

            // String urlBack = MoreAppsIU.urlServer+array.getJSONObject(array.length() - 1).getString("background");


            // Log.e("MAIN", "GetFromJson: "+array.length() );

            for(int i=0;i<array.length(); i++){
                JSONObject orig = array.getJSONObject(i);

                IntersticialApp.IntersticialObj app = new IntersticialApp.IntersticialObj();

                app.id_app = orig.getString("id");
                app.actionAd = orig.getString("action_ad");
                app.descAd = orig.getString("desc_ad");
                app.rate = Float.parseFloat(orig.getString("rate"));
                app.titleAd = orig.getString("title_ad");
                app.url_icon = orig.getString("video_thumbnail_b");
                app.url_img1 = orig.getString("url_imagen_1");
                app.url_img2 = orig.getString("url_imagen_2");
                app.url_img3 = orig.getString("url_imagen_3");
     try {
         app.frecuency = Integer.parseInt(orig.getString("intersticial_frecuency"));
     }catch (Exception e){
         Log.e("MAIN", "GetInsFromJson: "+e.getMessage() );
     }
                app.urltoApp = orig.getString("video_url");

                //    Log.e("MAIN", "GetFromJson: "+app.getAppName() );
                ls.add(app);
            }

        } catch (JSONException e) {
            Log.e("MAIN", "GetFromJson: "+e.getMessage() );
            truelistener.Fail(e.getMessage());

            e.printStackTrace();
        }


        return ls;
    }



/**  obtener apps  **/
    public interface OnDataReceive{
        void Correct(ArrayList<AppModel> apps);
        void Fail(String erno);
    }

    private OnDataReceive truelistener;
private String urr = "";
    public GetDataFromServer(Context m, String urlM,final OnDataReceive listener){
this.truelistener = listener;
      //  Log.e("MAIN", "GetDataFromServer: "+urlM );
        urr = urlM;
        RequestQueue queue = Volley.newRequestQueue(m);

        StringRequest request = new StringRequest(Request.Method.GET, urlM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
listener.Correct(GetFromJson(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(truelistener != null)
                truelistener.Fail(error.getMessage());
            }
        });

        queue.add(request);


    }


    private ArrayList<AppModel> GetFromJson(String js){
        ArrayList<AppModel> ls = new ArrayList<>();

        try {
            JSONObject object = new JSONObject(js);

         //   Log.e("MAIN", "GetFromJson: para la url = " +urr + " estejson " +object.toString() );

            JSONArray array = object.getJSONArray("MY_TUBE_APP");

           // String urlBack = MoreAppsIU.urlServer+array.getJSONObject(array.length() - 1).getString("background");


           // Log.e("MAIN", "GetFromJson: "+array.length() );

            for(int i=0;i<array.length(); i++){
                JSONObject orig = array.getJSONObject(i);

                AppModel app = new AppModel();

                app.setAppName(orig.getString("video_title"));
                app.slug = orig.getString("slug");
                app.status = Integer.parseInt(orig.getString("status"));
                app.setAppDescShort(orig.getString("video_duration"));
                app.setAppDesc(orig.getString("video_description"));
                app.setImgBig(orig.getString("video_thumbnail_b"));
               app.setPromotional(orig.getString("video_thumbnail_s"));
               app.setRate(parseFloat(orig.getString("rate")));
               app.setInstalls(orig.getInt("downloads"));
                app.setImgSmall("");
                app.setPackagen(orig.getString("video_url"));

            //    Log.e("MAIN", "GetFromJson: "+app.getAppName() );
                ls.add(app);
            }

        } catch (JSONException e) {
            Log.e("MAIN", "GetFromJson: "+e.getMessage() );
            if(truelistener != null)
            truelistener.Fail(e.getMessage());

            e.printStackTrace();
        }


        return ls;
    }
}
