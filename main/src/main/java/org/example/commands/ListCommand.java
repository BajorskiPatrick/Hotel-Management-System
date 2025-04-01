package org.example.commands;

import org.example.hotel.Hotel;
import org.example.hotel.RoomInfo;

import java.util.List;

/**
 * Klasa reprezentująca komendę ListCommand. Odpowiada za wyświetlenie podanego numeru pokoju i informacji o zajętości
 * danego pokoju, jego ewentualnych gościach oraz datach zameldowania i wymeldowania
 */
public class ListCommand extends Command {
    /**
     * Metoda execute nadpisana dla klasy CheckoutCommand, która odpowiada za wykonanie komendy
     */
    @Override
    public String execute(Hotel hotel) {
        StringBuilder list = new StringBuilder();
        List<String> roomNumbers = hotel.getRoomNumbers();
        for (int i = 0; i < hotel.hotelSize(); i++) {
            list.append("Nr pokoju: ").append(roomNumbers.get(i)).append(", ").
                    append(roomToString(hotel.getRoomInfo(roomNumbers.get(i)))).append("\n");
        }
        return list.toString();
    }

    /**
     * Metoda konwertująca RoomInfo do String na potrzeby klasy ViewCommand
     *
     * @param room informacje o pokoju, które chcemy przekonwertować na String
     * @return String reprezentujący RoomInfo
     */
    public String roomToString(RoomInfo room) {
        if (room.isAvailable())
            return "Zajętość: dostępny, Goście: , Data zameldowania: , Data wymeldowania:";
        else {
            return "Zajętość: zajęty" + ", Goście: " + room.guestsToString() + "Data zameldowania: "
                    + room.getCheckinDate() + ", Data wymeldowania: " + room.getCheckoutDate();
        }
    }
}
