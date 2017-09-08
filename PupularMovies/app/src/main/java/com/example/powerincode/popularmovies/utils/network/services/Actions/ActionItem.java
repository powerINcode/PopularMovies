package com.example.powerincode.popularmovies.utils.network.services.Actions;

/**
 * Created by powerman23rus on 08.09.17.
 * Enjoy ;)
 */

public abstract class ActionItem<T> extends Action<T>{
    public void complete(String response, T result)  {
        callDone();
    }
}
