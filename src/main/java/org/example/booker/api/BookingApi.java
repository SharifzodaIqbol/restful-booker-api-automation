package org.example.booker.api;

import org.example.booker.model.Booking;
import org.example.booker.model.BookingId;
import org.example.booker.model.BookingResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface BookingApi {

    @GET("/booking")
    Call<List<BookingId>> getBookingIds();

    @GET("/booking/{id}")
    Call<Booking> getBooking(@Path("id") int id);

    @POST("/booking")
    Call<BookingResponse> createBooking(@Body Booking booking);

    @PUT("/booking/{id}")
    Call<Booking> updateBooking(
            @Path("id") int id,
            @Header("Cookie") String cookie,
            @Body Booking booking
    );

    @DELETE("/booking/{id}")
    Call<Void> deleteBooking(
            @Path("id") int id,
            @Header("Cookie") String cookie
    );
}
