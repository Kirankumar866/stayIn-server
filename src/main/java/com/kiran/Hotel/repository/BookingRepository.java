package com.kiran.Hotel.repository;

import com.kiran.Hotel.model.BookedRoom;
import com.kiran.Hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookedRoom,Long> {


    List<BookedRoom> findByRoomId(Long roomId);


    BookedRoom findByBookingConfirmationCode(String confirmationCode);
}
