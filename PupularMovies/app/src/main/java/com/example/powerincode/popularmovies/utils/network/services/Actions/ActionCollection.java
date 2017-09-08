package com.example.powerincode.popularmovies.utils.network.services.Actions;

import java.util.ArrayList;

/**
 * Created by powerman23rus on 08.09.17.
 * Enjoy ;)
 */

public abstract class ActionCollection<T> extends Action<T> {
    public void complete(String response, ArrayList<T> results) {
        complete(response);
    }
}
