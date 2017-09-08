package com.example.powerincode.popularmovies.utils.network.services;

import com.example.powerincode.popularmovies.utils.network.NetworkWorker;
import com.example.powerincode.popularmovies.utils.network.models.DiscoverMovie;
import com.example.powerincode.popularmovies.utils.network.routes.RouterDiscover;
import com.example.powerincode.popularmovies.utils.network.services.Actions.ActionItem;

/**
 * Created by powerman23rus on 08.09.17.
 * Enjoy ;)
 */

public class DiscoverService {

    public static final DiscoverService shared = new DiscoverService();
    private final RouterDiscover router = RouterDiscover.shared;

    public void getMovies(ActionItem<DiscoverMovie> callback) {
        new NetworkWorker<>(DiscoverMovie.class, callback)
                .execute(router.getMovies());
    }

    public void getPopularMovies(ActionItem<DiscoverMovie> callback) {
        new NetworkWorker<>(DiscoverMovie.class, callback)
                .execute(router.getPopularMovies());
    }
}
