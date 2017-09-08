package com.example.powerincode.popularmovies.utils.network.services.Actions;

/**
 * Created by powerman23rus on 08.09.17.
 * Enjoy ;)
 */

public abstract class Action<T> {
    private boolean isDoneWasCalled = false;

    public void start() {}
    public void done() {
        isDoneWasCalled = true;
    }
    public void complete(String response) {
        callDone();
    }
    public void error(Exception error) {
        callDone();
    }

    void callDone() {
        if (!isDoneWasCalled) {
            done();
        }
    }
}
