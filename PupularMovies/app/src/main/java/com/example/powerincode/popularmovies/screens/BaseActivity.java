package com.example.powerincode.popularmovies.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.powerincode.popularmovies.R;

import butterknife.ButterKnife;

/**
 * Created by powerman23rus on 26.10.17.
 * Enjoy ;)
 */

public abstract class BaseActivity extends AppCompatActivity {
    public abstract int getLayout();

    protected Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
    }

    protected void showMessage(String message) {
        if (mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        mToast.show();

    }
}
