package org.example.booker.tests.base;

import org.aeonbits.owner.ConfigFactory;
import org.example.booker.api.AuthenticationApi;
import org.example.booker.api.BookingApi;
import org.example.booker.model.*;
import org.example.booker.client.ApiClient;
import org.example.booker.config.AppConfig;
import org.junit.jupiter.api.TestInstance;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    protected static final AppConfig CFG = ConfigFactory.create(AppConfig.class);
    private final ApiClient apiFactory = new ApiClient();
    protected BookingApi bookingApi(){
        return apiFactory.create(BookingApi.class);
    }
    protected AuthenticationApi authApi(){
        return apiFactory.create(AuthenticationApi.class);
    };
    protected <T> Response<T> execute(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.code() >= 500) {
                throw new AssertionError(String.format(CFG.errorServer(),
                        response.code(), response.errorBody().string()));
            }
            return response;
        } catch (IOException e) {
            throw new AssertionError(CFG.errorNetwork() + e.getMessage(), e);
        }
    }

    protected Response<BookingResponse> createBookingAndGetId(String firstname) {
        Booking body = new Booking()
                .firstname(firstname)
                .lastname(CFG.bookingLastname())
                .totalprice(CFG.bookingPrice())
                .depositpaid(true)
                .bookingdates(new BookingDates().checkin(LocalDate.now()).checkout(LocalDate.now().plusDays(7)))
                .additionalneeds(CFG.bookingAdditional());

        return execute(bookingApi().createBooking(body));
    }

    protected String getAuthToken() {
        AuthRequest auth = new AuthRequest()
                .username(CFG.username())
                .password(CFG.password());

        Response<AuthResponse> response = execute(authApi().createToken(auth));

        assertThat(response.isSuccessful())
                .withFailMessage(String.format(CFG.errorAuthFail(), response.code()))
                .isTrue();

        String token = response.body().getToken();
        assertThat(token)
                .withFailMessage(CFG.errorAuthTokenEmpty())
                .isNotBlank();

        return token;
    }
}