package com.kiran.Hotel.controller;

import com.kiran.Hotel.exception.InvalidBookingRequestException;
import com.kiran.Hotel.exception.ResourceNotFoundException;
import com.kiran.Hotel.model.BookedRoom;
import com.kiran.Hotel.model.Room;
import com.kiran.Hotel.response.BookingResponse;
import com.kiran.Hotel.response.RoomResponse;
import com.kiran.Hotel.service.IBookingService;
import com.kiran.Hotel.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final IBookingService bookingService;
    private final IRoomService roomService;

    @GetMapping("allbookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        List<BookedRoom> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for(BookedRoom booking : bookings){
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }

        return ResponseEntity.ok(bookingResponses);
    }

    private BookingResponse getBookingResponse(BookedRoom booking) {
        Room theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomResponse room = new RoomResponse(theRoom.getId(), theRoom.getRoomType(), theRoom.getRoomPrice());

        return new BookingResponse(booking.getBookingId(), booking.getCheckInDate(),
                booking.getCheckOutDate(), booking.getGuestFullName(),booking.getGuestEmail(),
                booking.getNumOfAdults(), booking.getNumOfChildren(),
                booking.getTotalNumofGuests(),booking.getBookingConfirmationCode(), room
        );


    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){

        try{
            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch(ResourceNotFoundException ex){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable("roomId") Long roomId,
                                         @RequestBody BookedRoom bookingRequest){
        try{
            String confirmationCode = bookingService.saveBooking(roomId,bookingRequest);
            System.out.println("confirmation" + confirmationCode);
            return ResponseEntity.ok("Room booked successfully! Your Booking confirmation code is :"+ confirmationCode);
        }catch(InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId){
        System.out.println(bookingId);
        bookingService.cancelBooking(bookingId);
    }
}
