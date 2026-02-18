package org.example.booker.api;

import org.example.booker.model.AuthRequest;
import org.example.booker.model.AuthResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationApi {

    /**
     * Создание токена для доступа к PUT и DELETE методам
     */
    @POST("/auth")
    Call<AuthResponse> createToken(@Body AuthRequest authRequest);
}