package com.kiran.Hotel.response;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookingResponse {

    private Long bookingId;


    private LocalDate checkInDate;


    private LocalDate checkOutDate;


    private String guestFullName;


    private String guestEmail;


    private int numOfAdults;


    private int numOfChildren;


    private int totalNumofGuests;


    private String bookingConfirmationCode;


    private RoomResponse room;

    public BookingResponse(String bookingConfirmationCode, LocalDate checkInDate,
                           LocalDate checkOutDate,
                           Long bookingId) {
        this.bookingConfirmationCode = bookingConfirmationCode;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
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

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkOutDate = checkOutDate;
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

    public String getBookingConfirmationCode() {
        return bookingConfirmationCode;
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

    public RoomResponse getRoom() {
        return room;
    }

    public void setRoom(RoomResponse room) {
        this.room = room;
    }
}
