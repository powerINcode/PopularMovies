package com.example.powerincode.pupularmovies.utils.network.services.Actions;

/**
 * Created by powerman23rus on 08.09.17.
 * Enjoy ;)
 */

public abstract class Action<T> {
    public void start() {}
    public void complete(String response) {}
    public abstract void error(Exception error);
}
