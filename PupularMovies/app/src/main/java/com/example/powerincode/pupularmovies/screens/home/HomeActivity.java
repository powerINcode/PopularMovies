package com.example.powerincode.pupularmovies.screens.home;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.powerincode.pupularmovies.R;
import com.example.powerincode.pupularmovies.utils.network.NetworkUtil;
import com.example.powerincode.pupularmovies.utils.network.models.DescoverMovie;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        NetworkUtil.shared.execute(NetworkUtil.getPopularMovies(), DescoverMovie.class, new NetworkUtil.Action<DescoverMovie>() {
            @Override
            public void start() {

            }

            @Override
            public void complete(DescoverMovie result) {

            }

            @Override
            public void error(Exception error) {

            }
        });
    }
}
