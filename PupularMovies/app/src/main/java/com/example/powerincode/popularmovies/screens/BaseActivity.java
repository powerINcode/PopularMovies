package com.example.powerincode.popularmovies.screens;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by powerman23rus on 10.09.17.
 * Enjoy ;)
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected String getClassName() {
        return this.getClass().getSimpleName();
    }
}
