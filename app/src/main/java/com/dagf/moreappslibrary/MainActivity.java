package com.dagf.moreappslibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dagf.moreapplibrary.MoreAppsIU;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MoreAppsIU.applySplit();
        MoreAppsIU.openIU(this, "fivmovies");
    }
}
