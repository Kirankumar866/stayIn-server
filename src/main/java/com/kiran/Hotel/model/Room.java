package com.kiran.Hotel.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private Boolean isBooked = false;

    @Lob
    private Blob photo;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookedRoom> bookings;

    public Room() {
        this.bookings = new ArrayList<>();
    }

    public void addBooking(BookedRoom booking){
        if(bookings == null){
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setRoom(this);
        isBooked= true;
        String confirmationCode  = RandomStringUtils.randomAlphanumeric(10);
        booking.setBookingConfirmationNumber(confirmationCode);

    }



}
