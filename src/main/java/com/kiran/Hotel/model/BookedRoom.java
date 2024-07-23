package com.kiran.Hotel.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class BookedRoom {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column(name = "check_In")
    private LocalDate checkInDate;

    @Column(name = "check_Out")
    private LocalDate checkoutDate;

    @Column(name = "guest_FullName")
    private String guestFullName;

    @Column(name = "guest_Email")
    private String guestEmail;

    @Column(name = "adults")
    private int numOfAdults;

    @Column(name = "children")
    private int numOfChildren;

    @Column(name = "total_Guest")
    private int totalNumofGuests;

    @Setter
    @Column(name = "confirmation_code")
    private String bookingConfirmationCode;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id")
    private Room room;




    public void calcTotalGuest(){
        this.totalNumofGuests = this.numOfAdults + this.numOfChildren;
    }

    public void setNumOfAdults(int numOfAdults) {
        this.numOfAdults = numOfAdults;
        calcTotalGuest();
    }

    public void setNumOfChildren(int numOfChildren) {
        this.numOfChildren = numOfChildren;
        calcTotalGuest();
    }


}
