package com.kiran.Hotel.repository;

import com.kiran.Hotel.model.BookedRoom;
import com.kiran.Hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookedRoom,Long> {


    List<BookedRoom> findByRoomId(Long roomId);
}
