package org.example.booker.tests.service;

import org.aeonbits.owner.ConfigFactory;
import org.example.booker.api.BookingApi;
import org.example.booker.client.ApiClient;
import org.example.booker.config.AppConfig;
import org.example.booker.model.Booking;
import org.example.booker.model.BookingDates;
import org.example.booker.model.BookingId;
import org.example.booker.model.BookingResponse;
import retrofit2.Response;

import java.time.LocalDate;
import java.util.List;

public class BookingService {
    private final BookingApi api;
    private final AppConfig cfg;

    public BookingService() {
        this.cfg = ConfigFactory.create(AppConfig.class);
        // Создаем клиент здесь
        this.api = new ApiClient().create(BookingApi.class);
    }

    public Response<BookingResponse> createBooking(String firstname) {
        Booking body = new Booking()
                .firstname(firstname)
                .lastname(cfg.bookingLastname())
                .totalprice(cfg.bookingPrice())
                .depositpaid(true)
                .bookingdates(new BookingDates().checkin(LocalDate.now()).checkout(LocalDate.now().plusDays(7)))
                .additionalneeds(cfg.bookingAdditional());

        return ApiExecutor.execute(api.createBooking(body));
    }

    public Response<Void> deleteBooking(Integer id, String cookie) {
        return ApiExecutor.execute(api.deleteBooking(id, cookie));
    }

    public Response<Booking> getBooking(Integer id) {
        return ApiExecutor.execute(api.getBooking(id));
    }

    public Response<List<BookingId>> getBookingIds() {
        return ApiExecutor.execute(api.getBookingIds());
    }

    public Response<Booking> updateBooking(Integer id, String cookie, Booking booking) {
        return ApiExecutor.execute(api.updateBooking(id, cookie, booking));
    }
}