package app.taolin.one;

import android.app.Application;

/**
 * Created by Taolin on 16/5/26.
 */

public class App extends Application {

    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static App getInstance() {
        return sInstance;
    }
}
