package com.dagf.moreappslibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.dagf.admobnativeloader.EasyNativeLoader;
import com.dagf.moreapplibrary.AppModel;
import com.dagf.moreapplibrary.IntersticialApp;
import com.dagf.moreapplibrary.IntersticialPromo;
import com.dagf.moreapplibrary.MoreAppsIU;
import com.dagf.moreapplibrary.PromotionView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements
        EasyPermissions.PermissionCallbacks{

    CameraWebView myWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);
     /*  myWebView = findViewById(R.id.webv);

       myWebView.setActivity(this);

       myWebView.loadUrlWithCamera("https://blackdish.mx/ar3/app/");
*/

     MoreAppsIU.urlServer = "https://jreva.app/cpanel/moreapps/";
    // MoreAppsIU.loadApps(this);
    /* MoreAppsIU.applySpin();

        EasyNativeLoader easyNativeLoader = new EasyNativeLoader(this, "ca-app-pub-3940256099942544/1044960115");
        easyNativeLoader.setupAdapterNatives(3);*/

   MoreAppsIU.openIU(this,"moreapps", "2505373932857364_2505378202856937", MoreAppsIU.TypeAd.Facebook);
/*
     final IntersticialApp intersticialApp = new IntersticialApp(this);

     if(IntersticialApp.isReadytoShow(this)){
         Log.e("MAIN", "onCreate: si maricoo" );
     }

     intersticialApp.setListenerPromo(new IntersticialApp.onLoadListenerPromo() {
         @Override
         public void onLoadSuccess() {
             intersticialApp.showAd();
         }

         @Override
         public void onShowIntersticial() {

         }

         @Override
         public void onImpressionSuccess() {

         }

         @Override
         public void onFailed(String erno) {

         }

         @Override
         public void onClosed() {

         }
     });

        intersticialApp.loadAds();

*/

    }






    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}
