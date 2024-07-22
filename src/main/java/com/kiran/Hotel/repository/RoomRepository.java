package com.kiran.Hotel.repository;

import com.kiran.Hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Long> {


    @Query("Select DISTINCT r.roomType From Room r")
    List<String> findDistinctRoomTypes();
}

