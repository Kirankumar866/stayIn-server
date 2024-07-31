package com.kiran.Hotel.service;

import com.kiran.Hotel.exception.InvalidBookingRequestException;
import com.kiran.Hotel.exception.ResourceNotFoundException;
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

        return bookingRepo.findByBookingConfirmationCode(confirmationCode)
                .orElseThrow(()-> new ResourceNotFoundException("No Booking found with booking code: "+ confirmationCode));
    }

    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) {

        if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
            throw new InvalidBookingRequestException("Check-In date must come before check-Out date");

        }

        Room room = roomService.getRoomById(roomId).get();
        List<BookedRoom> existingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest,existingBookings);
        if(roomIsAvailable){
            room.addBooking(bookingRequest);
            bookingRepo.save(bookingRequest);
        }else{
            throw new InvalidBookingRequestException("Sorry, This room is not available for the selected dates;");
        }
        return bookingRequest.getBookingConfirmationCode();
    }

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }

    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepo.deleteById(bookingId);

    }
}
