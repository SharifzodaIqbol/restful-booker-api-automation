package org.example.booker.tests.base;

import org.aeonbits.owner.ConfigFactory;
import org.example.booker.api.AuthenticationApi;
import org.example.booker.api.BookingApi;
import org.example.booker.client.ApiClient;
import org.example.booker.config.AppConfig;
import org.example.booker.tests.service.AuthService;
import org.example.booker.tests.service.BookingService;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    protected static final AppConfig CFG = ConfigFactory.create(AppConfig.class);

    protected final AuthService authService;
    protected final BookingService bookingService;

    public BaseTest() {
        ApiClient apiFactory = new ApiClient();

        AuthenticationApi authApi = apiFactory.create(AuthenticationApi.class);
        BookingApi bookingApi = apiFactory.create(BookingApi.class);

        this.authService = new AuthService(authApi, CFG);
        this.bookingService = new BookingService(bookingApi, CFG);
    }
}