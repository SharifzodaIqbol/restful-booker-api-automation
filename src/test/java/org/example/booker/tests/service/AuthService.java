package org.example.booker.tests.service;

import org.aeonbits.owner.ConfigFactory;
import org.example.booker.api.AuthenticationApi;
import org.example.booker.client.ApiClient;
import org.example.booker.config.AppConfig;
import org.example.booker.model.AuthRequest;
import org.example.booker.model.AuthResponse;
import retrofit2.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthService {
    private final AuthenticationApi api;
    private final AppConfig cfg;

    public AuthService() {
        this.cfg = ConfigFactory.create(AppConfig.class);
        this.api = new ApiClient().create(AuthenticationApi.class);
    }

    public Response<AuthResponse> login(String username, String password) {
        AuthRequest auth = new AuthRequest()
                .username(username)
                .password(password);
        return ApiExecutor.execute(api.createToken(auth));
    }

    public String getAuthToken() {
        Response<AuthResponse> response = login(cfg.username(), cfg.password());

        assertThat(response.isSuccessful())
                .withFailMessage(String.format(cfg.errorAuthFail(), response.code()))
                .isTrue();

        String token = response.body() != null ? response.body().getToken() : null;
        assertThat(token)
                .withFailMessage(cfg.errorAuthTokenEmpty())
                .isNotBlank();

        return token;
    }
}