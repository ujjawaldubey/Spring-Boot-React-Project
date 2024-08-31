package com.jsp.hookup.service;

import java.util.List;

import com.jsp.hookup.model.BookedRoom;

public interface BookingService {

	void cancelBooking(Long bookingId);

	String saveBooking(Long roomId, BookedRoom bookingRequest);

	BookedRoom findByBookingConfirmationCode(String confirmationCode);

	List<BookedRoom> getAllBookings();

	List<BookedRoom> getAllBookingsByRoomId(long roomId);

	List<BookedRoom> getBookingsByUserEmail(String email);

}
