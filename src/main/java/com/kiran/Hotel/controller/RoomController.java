package com.kiran.Hotel.controller;

import com.kiran.Hotel.exception.InternalServerException;
import com.kiran.Hotel.exception.PhotoRetrievalException;
import com.kiran.Hotel.exception.ResourceNotFoundException;
import com.kiran.Hotel.model.BookedRoom;
import com.kiran.Hotel.model.Room;
import com.kiran.Hotel.response.BookingResponse;
import com.kiran.Hotel.response.RoomResponse;
import com.kiran.Hotel.service.BookingService;
import com.kiran.Hotel.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/rooms")
public class RoomController {

    private final IRoomService roomService;
    private final BookingService bookingService;


    @PostMapping("/addnewroom")
    public ResponseEntity<RoomResponse> addNewRoom(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("roomType") String roomType,
            @RequestParam("roomPrice") BigDecimal roomPrice
    ) throws SQLException, IOException {
        Room savedRoom = roomService.addNewRoom(photo,roomType,roomPrice);
        RoomResponse response = new RoomResponse(savedRoom.getId(),savedRoom.getRoomType(),
                savedRoom.getRoomPrice());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/roomtypes")
    public List<String> getAllRoomTypes(){
        return roomService.getAllRoomTypes();

    }

    @GetMapping("/allrooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> roomResponses = new ArrayList<>();
        for(Room room : rooms){
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());

            if(photoBytes!= null && photoBytes.length>0){
                String base64Photo = Base64.getEncoder().encodeToString(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                assert roomResponse != null;
                roomResponse.setPhoto(base64Photo);
                roomResponses.add(roomResponse);
            }
        }
        return ResponseEntity.ok(roomResponses);

    }

    @DeleteMapping("/deleteroom/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("roomId") Long id){
        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long roomId,
                                               @RequestParam(required = false) String roomType,
                                               @RequestParam(required = false) BigDecimal roomPrice,
                                               @RequestParam(required = false) MultipartFile photo) throws IOException, SQLException, InternalServerException {
        byte[] photoBytes = photo!= null && !photo.isEmpty()?photo.getBytes(): roomService.getRoomPhotoByRoomId(roomId);
        Blob photoBlob = photoBytes!= null && photoBytes.length>0 ? new SerialBlob(photoBytes): null;
        Room theRoom = roomService.updateRoom(roomId,roomType,roomPrice,photoBytes);
        theRoom.setPhoto(photoBlob);

        RoomResponse roomResponse = getRoomResponse(theRoom);

        return ResponseEntity.ok(roomResponse);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<Optional<RoomResponse>> getRoomByItd(@PathVariable Long roomId){
        Optional<Room> theRoom = roomService.getRoomById(roomId);
        return theRoom.map(room -> {
            RoomResponse roomResponse = getRoomResponse(room);
            return ResponseEntity.ok(Optional.of(roomResponse));
        }).orElseThrow(()->new ResourceNotFoundException("Room not found"));
    }


    @GetMapping("/available-rooms")
    public ResponseEntity<List<RoomResponse>> getAvailableRooms(
            @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate checkInDate,
            @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate checkOutDate,
            @RequestParam("roomType") String roomType) throws SQLException {
        List<Room> availableRooms = roomService.getAvailableRooms(checkInDate,checkOutDate, roomType);
        List<RoomResponse> roomResponses = new ArrayList<>();
        for(Room room : availableRooms){
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            if(photoBytes != null && photoBytes.length > 0){
                String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(photoBase64);
                roomResponses.add(roomResponse);
            }
        }
        if(roomResponses.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(roomResponses);
        }
    }





    private RoomResponse getRoomResponse(Room room) {
        List<BookedRoom> bookings = getAllBookingsByRoomId(room.getId());
        List<BookingResponse> bookingInfo = bookings
                .stream()
                .map(booking -> new BookingResponse(booking.getBookingConfirmationCode(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(),
                        booking.getBookingId()
                        )).toList();

        byte[] photoBytes = null;
        Blob photoBlob = room.getPhoto();
        if(photoBlob!= null){
            try{
                photoBytes = photoBlob.getBytes(1,(int) photoBlob.length());

            }
            catch (SQLException e) {
                throw new PhotoRetrievalException("Error retrieving photo");
            }

            return new RoomResponse(room.getId(),room.getRoomType(),
                    room.getRoomPrice(),
                    room.getIsBooked(), photoBytes);
        }

        return null;
    }

    private List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookingService.getAllBookingsByRoomId(roomId);

    }


}
