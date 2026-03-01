package org.example.booker.tests.bookings;

import org.example.booker.model.BookingResponse;
import org.example.booker.tests.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateBookingTest extends BaseTest {

    @Test
    @DisplayName("Создание нового бронирования")
    void createValidBooking() {
        Response<BookingResponse> response = bookingService.createBooking(CFG.bookingFirstname());

        assertThat(response.code()).isEqualTo(200);
        assertThat(response.body()).isNotNull();
        assertThat(response.body().getBookingid()).isNotNull();
        assertThat(response.body().getBooking().getFirstname())
                .isEqualTo(CFG.bookingFirstname());
    }

    @Test
    @DisplayName("Создание нового бронирования без имени бронировщика")
    void createInvalidBooking() {
        Response<BookingResponse> response = bookingService.createBooking("");
        assertThat(response.code()).isEqualTo(200);
        assertThat(response.body().getBooking().getFirstname()).isBlank();
    }
}