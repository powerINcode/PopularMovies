package com.example.powerincode.pupularmovies.utils.network.services;

import com.example.powerincode.pupularmovies.utils.network.NetworkWorker;
import com.example.powerincode.pupularmovies.utils.network.models.MovieInfo;
import com.example.powerincode.pupularmovies.utils.network.routes.RouterDiscover;
import com.example.powerincode.pupularmovies.utils.network.services.Actions.ActionItem;

/**
 * Created by powerman23rus on 08.09.17.
 * Enjoy ;)
 */

public class DiscoverService {

    public static final DiscoverService shared = new DiscoverService();
    private final RouterDiscover router = RouterDiscover.shared;

    public void getMovies(ActionItem<MovieInfo> callback) {
        new NetworkWorker<>(MovieInfo.class, callback)
                .execute(router.getMovies());
    }

    public void getPopularMovies(ActionItem<MovieInfo> callback) {
        new NetworkWorker<>(MovieInfo.class, callback)
                .execute(router.getPopularMovies());
    }
}
