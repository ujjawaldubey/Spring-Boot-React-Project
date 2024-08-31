package com.jsp.hookup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jsp.hookup.exception.InvalidBookingRequestException;
import com.jsp.hookup.exception.ResourceNotFoundException;
import com.jsp.hookup.model.BookedRoom;
import com.jsp.hookup.model.Room;
import com.jsp.hookup.repository.BookingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
	
	

//	public List<BookdRoom> getAllBookingsByRoomId(long roomId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	private final BookingRepository bookingRepository;
	private final RoomService roomService;
	
	@Override
	public void cancelBooking(Long bookingId) {
		bookingRepository.deleteById(bookingId);
	}

	@Override
	public String saveBooking(Long roomId, BookedRoom bookingRequest) {
		if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
			throw new InvalidBookingRequestException("Check-in date must come before check-out date");
		}
		Room room = roomService.getRoomById(roomId).get();
		List<BookedRoom> existingBookings = room.getBookings();
		
		boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);
		if(roomIsAvailable) {
			room.addBooking(bookingRequest);
			bookingRepository.save(bookingRequest);
			
		}
		else {
			throw new InvalidBookingRequestException("Sorry, This Room is not avilable for the selected dates");
		}
		
		return bookingRequest.getBookingConfirmationCode();
		
	}

	

	@Override
	public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
	
		return bookingRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(()-> new ResourceNotFoundException("No booking found with this booking code : "+confirmationCode));
	}

	@Override
	public List<BookedRoom> getAllBookings() {
		
		return bookingRepository.findAll();
	}

	public List<BookedRoom> getAllBookingsByRoomId(long roomId) {
		
		return bookingRepository.findByRoomId(roomId);
	}
	
	
	private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
		
		return existingBookings.stream().noneMatch(existingBooking -> 
		
				bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
				|| bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
				
				|| (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
				&& bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
				
				|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
				&& bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))
				
				|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
				&& bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))
				
				|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
				&& bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
				
		);
	}

	@Override
	public List<BookedRoom> getBookingsByUserEmail(String email) {
		
		return bookingRepository.findByGuestEmail(email);
	}

}
