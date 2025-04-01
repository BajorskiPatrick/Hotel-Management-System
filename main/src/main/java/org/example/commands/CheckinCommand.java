package org.example.commands;

import org.example.annotations.ScannerUser;
import org.example.hotel.Hotel;
import org.example.hotel.RoomInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Klasa reprezentująca komendę CheckinCommand. Odpowiada za zameldowanie gości w pokoju
 */
@ScannerUser
public class CheckinCommand extends Command {
    private final Scanner scanner;

    /**
     * Konstruktor klasy CheckinCommand przyjmujący 1 parametr
     *
     * @param scanner parametr konstruktora, obiekt klasy Scanner
     */
    public CheckinCommand(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Metoda execute nadpisana dla klasy CheckinCommand, która odpowiada za wykonanie komendy
     */
    @Override
    public String execute(Hotel hotel) {
        String number = readRoomNumber(hotel);
        if (number.equals("0"))
            return "Zakończono wykonywanie komendy!";


        ArrayList<String> guests = readGuests(hotel.getRoomInfo(number).getCapacity(), scanner);
        LocalDate checkinDate = LocalDate.now();

        System.out.println();
        System.out.println("Podaj datę zameldowania w formacie DD-MM-RRRR (enter, jeśli dzisiejsza):");
        String date = scanner.nextLine();
        if (!date.isEmpty()) {
            checkinDate = LocalDate.parse(date);
        }

        System.out.println();
        System.out.println("Podaj długość trwania pobytu (liczbę dni):");
        int stayLength = scanner.nextInt();
        scanner.nextLine();

        boolean checkin = hotel.updateRoomInfo(number, new RoomInfo(hotel.getRoomInfo(number).getPrice(), 0,
                hotel.getRoomInfo(number).getCapacity(), guests, checkinDate, stayLength));
        System.out.println();
        if (checkin)
            return "Poprawnie zameldowano gości w pokoju " + number + "!";
        else
            return "Wystąpił błąd podczas rejestracji gości w pokoju" + number + ". Spróbuj ponownie!";

    }

    /**
     * Metoda wczytująca od użytkownika gości, których chcemy zameldować do pokoju
     *
     * @param capacity pojemność pokoju
     * @param scanner  obiekt klasy Scanner używany do odczytu danych z terminala
     * @return lista gości wczytanych od użytkownika
     */
    private ArrayList<String> readGuests(int capacity, Scanner scanner) {
        ArrayList<String> guests = new ArrayList<>();

        System.out.println("Podaj dane gościa głównego:");
        guests.add(scanner.nextLine());

        System.out.println();
        System.out.print("Czy są goście dodatkowi (podaj ilu)? (maksymalnie " + (capacity - 1) + "): ");
        int guestsNumber = scanner.nextInt();
        scanner.nextLine();

        if (guestsNumber != 0) {
            System.out.println();
            System.out.println("Podaj dane gości dodatkowych (każdy w nowej linii):");
            for (int i = 0; i < guestsNumber; i++) {
                guests.add(scanner.nextLine());
            }
        }
        return guests;
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
            if (number.equals("0") || (hotel.checkRoomNumber(number) && hotel.getRoomInfo(number).isAvailable()))
                break;

            if (!hotel.checkRoomNumber(number)) {
                System.out.println("Podany pokój nie istnieje!");
                System.out.println();
            }
            else if (!hotel.getRoomInfo(number).isAvailable()) {
                System.out.println("Podany pokój jest już zajęty!");
                System.out.println();
            }
        }
        return number;
    }
}
