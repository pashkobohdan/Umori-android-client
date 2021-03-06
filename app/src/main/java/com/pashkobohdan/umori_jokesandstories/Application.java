package com.pashkobohdan.umori_jokesandstories;

import android.content.Context;
import android.content.IntentFilter;
import android.widget.Toast;

import com.pashkobohdan.umori_jokesandstories.data.ormLite.HelperFactory;
import com.pashkobohdan.umori_jokesandstories.data.retrofitApi.UmoriliApi;
import com.pashkobohdan.umori_jokesandstories.receivers.NetworkStateReceiver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.provider.ContactsContract.Intents.Insert.ACTION;

/**
 * Created by bohdan on 25.03.17.
 */

public class Application extends android.app.Application {
    private static UmoriliApi umoriliApi;
    private Retrofit retrofit;

    private NetworkStateReceiver networkStateReceiver;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://www.umori.li/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        umoriliApi = retrofit.create(UmoriliApi.class); //Создаем объект, при помощи которого будем выполнять запросы


        HelperFactory.setHelper(getApplicationContext());

        networkStateReceiver = new NetworkStateReceiver();
        registerReceiver(networkStateReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();

        unregisterReceiver(networkStateReceiver);

        super.onTerminate();
    }

    public static UmoriliApi getApi() {
        return umoriliApi;
    }

    public static boolean hasActiveInternetConnection(Context context) {
        try {
            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
            urlc.setRequestProperty("User-Agent", "Test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(1500);
            urlc.connect();
            return (urlc.getResponseCode() == 200);
        } catch (IOException e) {
            Toast.makeText(context, "Error !", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
