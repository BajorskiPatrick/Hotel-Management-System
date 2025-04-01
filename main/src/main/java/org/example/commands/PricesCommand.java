package org.example.commands;

import org.example.hotel.Hotel;

import java.util.List;

/**
 * Klasa reprezentująca komendę PricesCommand. Odpowiada za wyświetlenie wszystkich pokoi wraz z ich cenami za dobę
 */
public class PricesCommand extends Command {
    /**
     * Metoda execute nadpisana dla klasy CheckoutCommand, która odpowiada za wykonanie komendy
     */
    @Override
    public String execute(Hotel hotel) {
        StringBuilder result = new StringBuilder();
        List<String> roomNumbers = hotel.getRoomNumbers();
        for (int i = 0; i < hotel.hotelSize(); i++) {
            result.append("Nr pokoju: ").append(roomNumbers.get(i)).append(", cena: ").
                    append(hotel.getRoomInfo(roomNumbers.get(i)).getPrice()).append("zł").append("\n");
        }
        return result.toString();
    }
}
