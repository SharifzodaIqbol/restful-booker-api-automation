package org.example.booker.tests.bookings;

import org.example.booker.model.Booking;
import org.example.booker.model.BookingId;
import org.example.booker.model.BookingResponse;
import org.example.booker.tests.service.BookingService;
import org.example.booker.tests.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetBookingTest extends BaseTest {

    private final BookingService bookingService = new BookingService();

    @Test
    @DisplayName("Проверка получения существующего бронирования")
    void getBooking() {
        Response<BookingResponse> response = bookingService.createBooking(CFG.bookingFirstname());

        assertThat(response.code()).isEqualTo(200);
        assertThat(response.body()).isNotNull();
        assertThat(response.body().getBooking().getFirstname()).isEqualTo(CFG.bookingFirstname());
    }

    @Test
    @DisplayName("Получение списка ID")
    void getBookingIds() {
        Response<List<BookingId>> response = bookingService.getBookingIds();
        assertThat(response.code()).isEqualTo(200);
        assertThat(response.isSuccessful()).isTrue();
    }

    @Test
    @DisplayName("Получение не существующего ID")
    void getInvalidBookingId() {
        Response<Booking> response = bookingService.getBooking(-1);
        assertThat(response.code()).isEqualTo(404);
        assertThat(response.body()).isNull();
    }
}