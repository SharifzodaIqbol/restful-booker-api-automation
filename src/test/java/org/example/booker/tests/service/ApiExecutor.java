package org.example.booker.tests.service;

import org.aeonbits.owner.ConfigFactory;
import org.example.booker.config.AppConfig;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class ApiExecutor {
    private static final AppConfig CFG = ConfigFactory.create(AppConfig.class);

    public static <T> Response<T> execute(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.code() >= 500) {
                throw new AssertionError(String.format(CFG.errorServer(),
                        response.code(), response.errorBody() != null ? response.errorBody().string() : ""));
            }
            return response;
        } catch (IOException e) {
            throw new AssertionError(CFG.errorNetwork() + e.getMessage(), e);
        }
    }
}