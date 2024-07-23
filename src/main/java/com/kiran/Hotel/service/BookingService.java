package com.kiran.Hotel.service;

import com.kiran.Hotel.exception.InvalidBookingRequestException;
import com.kiran.Hotel.model.BookedRoom;

import com.kiran.Hotel.model.Room;
import com.kiran.Hotel.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService{

    private final BookingRepository bookingRepo;
    private final IRoomService roomService;

    @Override
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId){
        return bookingRepo.findByRoomId(roomId);
    }

    @Override
    public List<BookedRoom> getAllBookings() {
        return bookingRepo.findAll();
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return null;
    }

    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) {
        if(bookingRequest.getCheckoutDate().isBefore(bookingRequest.getCheckInDate())){
            throw new InvalidBookingRequestException("Check-In date must come before check-Out date");

        }
        Room room = roomService.getRoomById(roomId).get();
        List<BookedRoom> existingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest,existingBookings);
        
        return "";
    }

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
    }

    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepo.deleteById(bookingId);

    }
}
