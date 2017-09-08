package com.example.powerincode.pupularmovies.screens.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.powerincode.pupularmovies.R;
import com.example.powerincode.pupularmovies.utils.network.models.MovieInfo;
import com.example.powerincode.pupularmovies.utils.network.services.Actions.ActionItem;
import com.example.powerincode.pupularmovies.utils.network.services.DiscoverService;

public class HomeActivity extends AppCompatActivity {
    private DiscoverService service = DiscoverService.shared;

    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        test = (TextView) findViewById(R.id.test);

        DiscoverService.shared.getPopularMovies(new ActionItem<MovieInfo>() {
            @Override
            public void complete(String response, MovieInfo result) {
                super.complete(response, result);
                test.setText(response);
            }

            @Override
            public void error(Exception error) {

            }
        });
    }
}
