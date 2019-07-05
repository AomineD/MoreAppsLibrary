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
import com.dagf.moreapplibrary.MoreAppsIU;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetDataFromServer {



    public interface OnDataReceive{
        void Correct(ArrayList<AppModel> apps);
        void Fail(String erno);
    }

    private OnDataReceive truelistener;

    public GetDataFromServer(Context m, String urlM,final OnDataReceive listener){
this.truelistener = listener;

        RequestQueue queue = Volley.newRequestQueue(m);

        StringRequest request = new StringRequest(Request.Method.GET, urlM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
listener.Correct(GetFromJson(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                truelistener.Fail(error.getMessage());
            }
        });

        queue.add(request);


    }


    private ArrayList<AppModel> GetFromJson(String js){
        ArrayList<AppModel> ls = new ArrayList<>();

        try {
            JSONObject object = new JSONObject(js);

            JSONArray array = object.getJSONArray("MY_TUBE_APP");

            String urlBack = MoreAppsIU.urlServer+array.getJSONObject(array.length() - 1).getString("background");


            for(int i=0;i<array.length() - 1; i++){
                JSONObject orig = array.getJSONObject(i);

                AppModel app = new AppModel();

                app.setAppName(orig.getString("video_title"));
                app.slug = orig.getString("slug");
                app.status = Integer.parseInt(orig.getString("status"));
                app.setAppDescShort(orig.getString("video_duration"));
                app.setAppDesc(orig.getString("video_description"));
                app.setImgBig(orig.getString("video_thumbnail_b"));
               app.setPromotional(orig.getString("video_thumbnail_s"));
                app.setImgSmall(urlBack);
                app.setPackagen(orig.getString("video_url"));

                ls.add(app);
            }

        } catch (JSONException e) {
            Log.e("MAIN", "GetFromJson: "+e.getMessage() );
            truelistener.Fail(e.getMessage());

            e.printStackTrace();
        }


        return ls;
    }
}
