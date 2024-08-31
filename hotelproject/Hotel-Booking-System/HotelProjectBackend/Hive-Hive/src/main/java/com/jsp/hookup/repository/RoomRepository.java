package com.jsp.hookup.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsp.hookup.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{
	
	@Query("SELECT DISTINCT r.roomType FROM Room r")
	List<String> findDistinctRoomTypes();
	
	@Query("SELECT r from Room r WHERE r.roomType LIKE %:roomType% AND r.id NOT IN (SELECT br.room.id FROM BookedRoom br WHERE ((br.checkInDate <= :checkOutDate) AND (br.checkOutDate >= :checkInDate)))")
	List<Room> findAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
	

}
