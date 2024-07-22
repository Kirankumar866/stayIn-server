package com.kiran.Hotel.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private Long bookingId;


    private LocalDate checkInDate;


    private LocalDate checkoutDate;


    private String guestFullName;


    private String guestEmail;


    private int numOfAdults;


    private int numOfChildren;


    private int totalNumofGuests;


    private String bookingConfirmationNumber;


    private RoomResponse room;

    public BookingResponse(String bookingConfirmationNumber, LocalDate checkInDate,
                           LocalDate checkoutDate,
                           Long bookingId) {
        this.bookingConfirmationNumber = bookingConfirmationNumber;
        this.checkInDate = checkInDate;
        this.checkoutDate = checkoutDate;
        this.bookingId = bookingId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getGuestFullName() {
        return guestFullName;
    }

    public void setGuestFullName(String guestFullName) {
        this.guestFullName = guestFullName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public int getNumOfAdults() {
        return numOfAdults;
    }

    public void setNumOfAdults(int numOfAdults) {
        this.numOfAdults = numOfAdults;
    }

    public int getNumOfChildren() {
        return numOfChildren;
    }

    public void setNumOfChildren(int numOfChildren) {
        this.numOfChildren = numOfChildren;
    }

    public int getTotalNumofGuests() {
        return totalNumofGuests;
    }

    public void setTotalNumofGuests(int totalNumofGuests) {
        this.totalNumofGuests = totalNumofGuests;
    }

    public String getBookingConfirmationNumber() {
        return bookingConfirmationNumber;
    }

    public void setBookingConfirmationNumber(String bookingConfirmationNumber) {
        this.bookingConfirmationNumber = bookingConfirmationNumber;
    }

    public RoomResponse getRoom() {
        return room;
    }

    public void setRoom(RoomResponse room) {
        this.room = room;
    }
}
