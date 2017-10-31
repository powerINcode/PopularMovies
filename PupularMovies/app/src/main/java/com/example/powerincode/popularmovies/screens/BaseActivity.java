package com.example.powerincode.popularmovies.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.powerincode.popularmovies.R;

import butterknife.ButterKnife;

/**
 * Created by powerman23rus on 26.10.17.
 * Enjoy ;)
 */

public abstract class BaseActivity extends AppCompatActivity {
    public abstract int getLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        ButterKnife.bind(this);
    }
}
