package org.example.booker.tests.service;

import org.example.booker.config.AppConfig;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class ApiExecutor {
    public static <T> Response<T> execute(Call<T> call, AppConfig cfg) {
        try {
            Response<T> response = call.execute();
            if (response.code() >= 500) {
                throw new AssertionError(String.format(cfg.errorServer(),
                        response.code(), response.errorBody() != null ? response.errorBody().string() : ""));
            }
            return response;
        } catch (IOException e) {
            throw new AssertionError(cfg.errorNetwork() + e.getMessage(), e);
        }
    }
}