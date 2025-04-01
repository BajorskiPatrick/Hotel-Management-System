package org.example.hotel;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca wszelkie informacje na temat pokoju hotelowego, które są potrzebne do zarządzania hotelem.
 * Z instancji tej klasy korzysta klasa Hotel, tworząc zbiór pokoi i informacji o nich
 */
public class RoomInfo {
    Integer price;
    Integer available;
    Integer capacity;
    ArrayList<String> guests;
    LocalDate checkinDate;
    Integer stayLength;

    /**
     * Konstruktor obiektu RoomInfo
     *
     * @param price       cena pokoju
     * @param available   dostępność pokoju: 0 - niedostępny, 1 - dostępny
     * @param capacity    pojemność pokoju (maksymalna ilość gości w pokoju)
     * @param guests      lista gości zameldowanych w pokoju
     * @param checkinDate data zameldowania
     * @param stayLength  długość pobytu w dniach (łącznie z dniem zameldowania)
     */
    public RoomInfo(Integer price, Integer available, Integer capacity, List<String> guests,
                    LocalDate checkinDate, Integer stayLength) {
        this.price = price;
        this.available = available;
        this.capacity = capacity;
        this.checkinDate = checkinDate;
        this.stayLength = stayLength;
        if (guests == null)
            this.guests = new ArrayList<>();
        else
            this.guests = new ArrayList<>(guests);
    }

    /**
     * Getter atrybutu price danego pokoju
     *
     * @return cena za noc za dany pokój
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Getter atrybutu availability
     *
     * @return informacja czy pokój jest wolny, czy nie
     */
    public boolean isAvailable() {
        return available != 0;
    }

    /**
     * Getter atrybutu capacity
     *
     * @return maksymalna liczba gości w pokoju
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * Metoda zwracająca atrybut guests w postaci String, gdzie kolejni goście są oddzieleni przecinkami
     *
     * @return String reprezentująca gości pokoju
     */
    public String guestsToString() {
        StringBuilder toReturn = new StringBuilder();
        for (String guest : this.guests) {
            toReturn.append(guest).append(", ");
        }
        return toReturn.toString();
    }

    /**
     * Metoda konwertująca atrybut guests do String. Wynik metody jest używany w konstrukcji pliku CSV reprezentującego
     * hotel
     *
     * @return String reprezentujący RoomInfo w formacie odpowiednim do zapisu w pliku CSV
     */
    private String guestsToCSV() {
        if (this.guests.isEmpty())
            return "";
        StringBuilder toReturn = new StringBuilder();
        for (int i = 0; i < this.guests.size() - 1; i++) {
            toReturn.append(this.guests.get(i)).append(";");
        }
        toReturn.append(this.guests.get(this.guests.size() - 1));
        return toReturn.toString();
    }

    /**
     * Metoda konwertująca RoomInfo do rekordu pliku CSV
     *
     * @param number numer pokoju, który konwertujemy
     * @return tablica String[] reprezentująca pokój
     */
    public String[] toCSVFormat(String number) {
        return new String[]{number, price.toString(), available.toString(), capacity.toString(), guestsToCSV(),
                (checkinDate != null) ? checkinDate.toString() : "", (stayLength != null) ? stayLength.toString() : ""};
    }

    /**
     * Getter atrybutu CheckinDate
     *
     * @return data zameldowania
     */
    public LocalDate getCheckinDate() {
        return checkinDate;
    }

    /**
     * Metoda obliczająca i zwracająca planowaną datę wymeldowania gości z pokoju
     *
     * @return planowana data wymeldowania gości
     */
    public LocalDate getCheckoutDate() {
        return (checkinDate != null) ? checkinDate.plusDays((long) stayLength - 1) : null;
    }

    /**
     * Metoda equals nadpisana na potrzeby klasy RoomInfo. Obiekty są sobie równe, gdy mają równe poszczególne atrybuty
     *
     * @param other obiekt, który chcemy porównać
     * @return true - jeśli równe, false - jeśli nie
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RoomInfo casted)) {
            return false;
        }

        return (casted.getPrice().equals(this.price) &&
                casted.getCapacity().equals(this.capacity) &&
                casted.guests.equals(this.guests) &&
                ((casted.checkinDate == null && this.checkinDate == null) ||
                        casted.getCheckinDate().equals(this.checkinDate)) &&
                ((casted.getCheckoutDate() == null && this.getCheckoutDate() == null) ||
                        (casted.getCheckoutDate().equals(this.getCheckoutDate()))));
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (price != null ? price.hashCode() : 0);
        hash += (available != null ? available.hashCode() : 0);
        hash += (capacity != null ? capacity.hashCode() : 0);
        hash += (guests != null ? guests.hashCode() : 0);
        hash += (checkinDate != null ? checkinDate.hashCode() : 0);
        hash += (stayLength != null ? stayLength.hashCode() : 0);
        return hash;
    }
}
