package org.example.booker.tests.bookings;

import org.example.booker.model.Booking;
import org.example.booker.model.BookingResponse;
import org.example.booker.tests.base.BaseTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteBookingTest extends BaseTest {

    private String fullCookie;

    @BeforeAll
    void getTokenAuth() {
        fullCookie = "token=" + authService.getAuthToken();
    }

    @Test
    @DisplayName("Удаление существующего бронирования")
    void deleteBookingTest() {
        Response<BookingResponse> createResponse = bookingService.createBooking(CFG.bookingFirstname());
        Integer id = createResponse.body().getBookingid();

        Response<Void> deleteResponse = bookingService.deleteBooking(id, fullCookie);
        assertThat(deleteResponse.code()).isEqualTo(201);

        Response<Booking> getResponse = bookingService.getBooking(id);
        assertThat(getResponse.code()).isEqualTo(404);
    }

    @Test
    @DisplayName("Попытка удаления без токена")
    void deleteWithoutTokenTest() {
        Response<BookingResponse> createResponse = bookingService.createBooking(CFG.bookingFirstname());
        Integer id = createResponse.body().getBookingid();

        Response<Void> deleteResponse = bookingService.deleteBooking(id, "");
        assertThat(deleteResponse.code()).isEqualTo(403);
    }
}