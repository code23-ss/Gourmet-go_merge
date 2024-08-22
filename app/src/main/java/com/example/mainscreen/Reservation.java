package com.example.mainscreen;

public class Reservation {
    private String restaurantName;
    private String reservationDate;
    private String reservationTime;
    private int reservationPeople;
    private String restaurantImage;

    // Firestore에서 객체를 생성할 때 필요함
    public Reservation() {}

    public Reservation(String restaurantName, String reservationDate, String reservationTime, int reservationPeople, String restaurantImage) {
        this.restaurantName = restaurantName;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.reservationPeople = reservationPeople;
        this.restaurantImage = restaurantImage;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public int getReservationPeople() {
        return reservationPeople;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }
}
