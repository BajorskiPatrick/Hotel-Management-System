package org.example.commands;

import org.example.annotations.ScannerUser;
import org.example.hotel.Hotel;
import org.example.hotel.RoomInfo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Klasa reprezentująca komendę CheckoutCommand. Odpowiada za wymeldowanie gości z pokoju
 */
@ScannerUser
public class CheckoutCommand extends Command {
    private final Scanner scanner;

    /**
     * Konstruktor klasy CheckoutCommand przyjmujący 1 parametr
     *
     * @param scanner parametr konstruktora, obiekt klasy Scanner
     */
    public CheckoutCommand(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Metoda execute nadpisana dla klasy CheckoutCommand, która odpowiada za wykonanie komendy
     */
    @Override
    public String execute(Hotel hotel) {
        String number = readRoomNumber(hotel);
        if (number.equals("0"))
            return "Zakończono wykonywanie komendy!";


        LocalDate today = LocalDate.now();
        long days = ChronoUnit.DAYS.between(hotel.getRoomInfo(number).getCheckinDate(), today);
        long price = days * hotel.getRoomInfo(number).getPrice();
        System.out.println();
        if (price > 0) {
            System.out.println("Należność za pokój: " + price + "zł");
            System.out.println();
            System.out.println("Kwota zapłacona przez gościa: ");
            int paid = scanner.nextInt();
            scanner.nextLine();
            if (paid > price) {
                System.out.println("Reszta do wydania: " + (paid - price) + "zł");
            }
        } else
            System.out.println("Należność za pokój: 0zł");

        System.out.println();

        RoomInfo info = new RoomInfo(hotel.getRoomInfo(number).getPrice(), 1,
                hotel.getRoomInfo(number).getCapacity(),
                new ArrayList<>(), null, null);
        boolean checkout = hotel.updateRoomInfo(number, info);

        if (checkout)
            return "Pokój " + number + " został zwolniony poprawnie!";
        else
            return "Wystąpił błąd w trakcie zwalniania pokoju " + number + ". Spróbuj ponownie!";
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
            if (number.equals("0") || (hotel.checkRoomNumber(number) && !hotel.getRoomInfo(number).isAvailable()))
                break;

            if (!hotel.checkRoomNumber(number)) {
                System.out.println("Podany pokój nie istnieje!");
                System.out.println();
            }
            else if (hotel.getRoomInfo(number).isAvailable()) {
                System.out.println("Podany pokój jest już wolny!");
                System.out.println();
            }
            System.out.println();
        }
        return number;
    }
}
