package org.example.booker.tests.auth;

import org.example.booker.model.AuthResponse;
import org.example.booker.tests.service.AuthService;
import org.example.booker.tests.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthTest extends BaseTest {

    private final AuthService authService = new AuthService();

    @Test
    @DisplayName("Успешное получение токена с валидными данными")
    void loginSuccess() {
        Response<AuthResponse> response = authService.login(CFG.username(), CFG.password());

        assertThat(response.code()).isEqualTo(200);
        assertThat(response.body()).isNotNull();
        assertThat(response.body().getToken()).isNotBlank();
    }

    @Test
    @DisplayName("Ошибка авторизации при неверном пароле")
    void loginWithWrongPassword() {
        Response<AuthResponse> response = authService.login(CFG.username(), "wrong_password");

        assertThat(response.code()).isEqualTo(200);
        assertThat(response.body() != null ? response.body().getToken() : null).isNull();
    }

    @Test
    @DisplayName("Пустой логин")
    void loginEmpty() {
        Response<AuthResponse> response = authService.login("", "");
        assertThat(response.code()).isEqualTo(200);
        assertThat(response.body() != null ? response.body().getToken() : null).isNull();
    }
}