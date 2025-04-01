package org.example.commands;

import org.example.annotations.ScannerUser;
import org.example.hotel.Hotel;
import org.example.hotel.RoomInfo;

import java.util.Scanner;

/**
 * Klasa reprezentująca komendę ViewCommand. Odpowiada za wyświetlenie wszelkich informacji o pokoju o podanym numerze
 */
@ScannerUser
public class ViewCommand extends Command {
    private final Scanner scanner;

    /**
     * Konstruktor klasy ViewCommand przyjmujący 1 parametr
     *
     * @param scanner parametr konstruktora, obiekt klasy Scanner
     */
    public ViewCommand(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Metoda execute nadpisana dla klasy CheckoutCommand, która odpowiada za wykonanie komendy
     */
    @Override
    public String execute(Hotel hotel) {
        StringBuilder result = new StringBuilder();
        String number = readRoomNumber(hotel);
        if (number.equals("0"))
            return "Zakończono wykonywanie komendy!";

        result.append("Informacje o pokoju numer ").append(number).append(":").append("\n");
        result.append(roomToString(hotel.getRoomInfo(number)));
        return result.toString();
    }

    /**
     * Metoda wczytująca od użytkownika numer pokoju, do którego chcemy zameldować gości
     *
     * @param hotel obiekt Hotel, na którym chcemy wykonać komendę
     * @return numer pokoju, do którego chcemy zameldować gości
     */
    public String readRoomNumber(Hotel hotel) {
        String number;
        while (true) {
            System.out.println("Podaj numer pokoju (0, jeśli chcesz zakończyć): ");
            number = scanner.nextLine();
            System.out.println();
            if (hotel.checkRoomNumber(number) || number.equals("0"))
                break;

            System.out.println("Podany pokój nie istnieje!");
            System.out.println();
        }
        return number;
    }

    /**
     * Metoda konwertująca RoomInfo do String na potrzeby klasy ViewCommand
     *
     * @param room informacje o pokoju, które chcemy przekonwertować na String
     * @return String reprezentujący RoomInfo
     */
    public String roomToString(RoomInfo room) {
        if (room.isAvailable())
            return "Cena: " + room.getPrice() + "zł, Zajętość: dostępny" + ", Pojemność: " + room.getCapacity()
                    + ", Goście: , Data zameldowania: , Data wymeldowania:";
        else {
            return "Cena: " + room.getPrice() + "zł, Zajętość: zajęty" + ", Pojemność: " + room.getCapacity()
                    + ", Goście: " + room.guestsToString() + "Data zameldowania: " + room.getCheckinDate()
                    + ", Data wymeldowania: " + room.getCheckoutDate();
        }
    }
}
