package com.dagf.moreappslibrary;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import pub.devrel.easypermissions.EasyPermissions;

public class CameraWebView extends WebView {

    private String TAG = "MAIN";
    private PermissionRequest mPermissionRequest;
    private Activity activity;

    public void setActivity(Activity m){
        this.activity = m;
    }

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final String[] PERM_CAMERA =
            {Manifest.permission.CAMERA};


    public CameraWebView(Context context) {
        super(context);
        onViewCreated();
    }

    public CameraWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onViewCreated();
    }

    String[] requestedResources;
    private void onViewCreated(){
        getSettings().setJavaScriptEnabled(true);
        getSettings().setAllowFileAccessFromFileURLs(true);
        getSettings().setAllowUniversalAccessFromFileURLs(true);

        setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        setWebChromeClient(new WebChromeClient() {
            // Grant permissions for cam
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.i(TAG, "onPermissionRequest");
                mPermissionRequest = request;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    requestedResources = request.getResources();
                }
                if(requestedResources != null) {
                    for (String r : requestedResources) {
                        if (r.equals(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
                            // In this sample, we only accept video capture request.

                            if(isCameraAllowed){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    mPermissionRequest.grant(new String[]{PermissionRequest.RESOURCE_VIDEO_CAPTURE});
                                }
                                break;
                            }


                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext())
                                    .setTitle("Permitir usar la camara?")
                                    .setPositiveButton("Permitir", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                mPermissionRequest.grant(new String[]{PermissionRequest.RESOURCE_VIDEO_CAPTURE});
                                                isCameraAllowed = true;
                                            }
                                            Log.e(TAG, "Granted");
                                        }
                                    })
                                    .setNegativeButton("Denegar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                mPermissionRequest.deny();
                                            }
                                            Log.d(TAG, "denegado");
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();

                            break;
                        }
                    }
                }
            }

            @Override
            public void onPermissionRequestCanceled(PermissionRequest request) {
                super.onPermissionRequestCanceled(request);
                Toast.makeText(getContext(),"Permission Denied", Toast.LENGTH_SHORT).show();
            }
        });



    }


    public void loadUrlWithCamera(String url){
        if(hasCameraPermission()){
            loadUrl(url);
            //   setContentView(myWebView );
        }else{
            EasyPermissions.requestPermissions(
                    activity,
                    "Esta app necesita de la camara para funcionar",
                    REQUEST_CAMERA_PERMISSION,
                    PERM_CAMERA);
        }
    }

    private static boolean isCameraAllowed = false;

    private boolean hasCameraPermission() {
        return EasyPermissions.hasPermissions(getContext(), PERM_CAMERA);
    }
    
}
