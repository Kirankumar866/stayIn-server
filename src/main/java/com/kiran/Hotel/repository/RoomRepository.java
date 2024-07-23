package com.kiran.Hotel.repository;

import com.kiran.Hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {


    @Query("Select DISTINCT r.roomType From Room r")
    List<String> findDistinctRoomTypes();
}

