package com.jsp.hookup.service;

import java.io.IOException;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.hookup.exception.InternalServerException;
import com.jsp.hookup.exception.ResourceNotFoundException;
import com.jsp.hookup.model.Room;
import com.jsp.hookup.repository.RoomRepository;
import com.jsp.hookup.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {
	
	private final RoomRepository roomRepository;

	@Override
	public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) {
	
		Room room = new Room();
		room.setRoomType(roomType);
		room.setRoomPrice(roomPrice);
		
		if(!file.isEmpty()) {
			byte[] photoBytes;
			try {
				photoBytes = file.getBytes();
				Blob photoBlob = new SerialBlob(photoBytes);
				room.setPhoto(photoBlob);
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
			
			
		}
		return roomRepository.save(room);
	}

	@Override
	public List<String> getAllRoomTypes() {
		
		return roomRepository.findDistinctRoomTypes();
	}

	@Override
	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}

	
	

	@Override
	public byte[] getRoomPhotoByRoomId(long roomId) {
		
		Optional<Room> theRoom = roomRepository.findById(roomId);
		if(theRoom.isEmpty()) {
			throw new ResourceNotFoundException("Sorry, Room not found");

		}
		Blob photoBlob = theRoom.get().getPhoto();
		if(photoBlob != null) {
			try {
				return photoBlob.getBytes(1, (int)photoBlob.length());
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void deleteRoom(long roomId) {
		Optional<Room> theRoom = roomRepository.findById(roomId);
		if(theRoom.isPresent()) {
			roomRepository.deleteById(roomId);
		}
		
	}

	@Override
	public Room updateRoom(long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes) {
		Room room = roomRepository.findById(roomId).orElseThrow(()-> new ResourceNotFoundException("Room Not Found"));
		if(roomType != null ) room.setRoomType(roomType);
		if(roomPrice != null) room.setRoomPrice(roomPrice);
		if(photoBytes != null && photoBytes.length > 0 ) {
			try {
				room.setPhoto(new SerialBlob(photoBytes));
			} catch (SerialException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				throw new InternalServerException("Error updating room");
			}
		}
		
		return roomRepository.save(room);
	}

	@Override
	public Optional<Room> getRoomById(long roomId) {
		return Optional.of(roomRepository.findById(roomId).get());
	}

	@Override
	public List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
		
		return roomRepository.findAvailableRoomsByDateAndType(checkInDate, checkOutDate, roomType);

	}


}
