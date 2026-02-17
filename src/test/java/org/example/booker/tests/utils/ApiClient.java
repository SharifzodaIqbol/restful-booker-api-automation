package org.example.booker.tests.utils;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import org.aeonbits.owner.ConfigFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final AppConfig CFG = ConfigFactory.create(AppConfig.class);
    private final Retrofit retrofit;

    public ApiClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .header("Content-Type", CFG.contentType())
                            .header("Accept", CFG.acceptHeader())
                            .build();
                    return chain.proceed(request);
                })
                .build();

        Gson gson = Converters.registerAll(new GsonBuilder())
                .setLenient()
                .create();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(CFG.baseUrl())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public <T> T create(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
}