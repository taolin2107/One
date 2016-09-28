package app.taolin.one.api;

import app.taolin.one.utils.Utils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Taolin on 16/5/27.
 */

public class OneServiceSingleton {

    public OneService mOneService;

    public static OneServiceSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        static final OneServiceSingleton INSTANCE = new OneServiceSingleton();
    }

    private OneServiceSingleton() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.encryto(Utils.reverse(Api.URL_BASE), -6))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mOneService = retrofit.create(OneService.class);
    }
}
