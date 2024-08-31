package com.jsp.hookup.response;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;

import jakarta.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomResponse {
	
	

	private long id;
	private String roomType;
	private BigDecimal roomPrice;
	private boolean isBooked = false;
	
	private String photo;
	
	private List<BookingResponse> bookings;
	
	public RoomResponse(long id, String roomType, BigDecimal roomPrice) {
		super();
		this.id = id;
		this.roomType = roomType;
		this.roomPrice = roomPrice;
	}

	public RoomResponse(long id, String roomType, BigDecimal roomPrice, boolean isBooked, byte[] photoBytes,
			List<BookingResponse> bookings) {
		super();
		this.id = id;
		this.roomType = roomType;
		this.roomPrice = roomPrice;
		this.isBooked = isBooked;
		this.photo = photoBytes !=   null ? Base64.encodeBase64String(photoBytes): null ;
		this.bookings = bookings;
	}

}
