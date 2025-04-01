package org.example.hotel;

import org.example.Main;
import org.example.annotations.FileHandler;
import org.example.utils.MyMap;
import org.apache.commons.csv.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Klasa reprezentująca hotel. Zawiera wszelkie informacje o pokojach hotelu potrzebne do zarządzania hotelem.
 */
@FileHandler
public class Hotel {
    private final MyMap<String, RoomInfo> hotelInfo;

    /**
     * Lista reprezentująca nagłówki, w jakie powinny znaleźć się w pliku CSV, z którego korzysta program
     */
    private final List<String> headers = Arrays.asList("room number", "price", "availability", "capacity",
            "guests", "checkinDate", "stayLength");

    /**
     * Konstruktor obiektu Hotel
     *
     * @param fileName obiekt String reprezentujący plik, który ma być wczytany przez hotel
     */
    public Hotel(String fileName) {
        hotelInfo = new MyMap<>();
        this.init(fileName);
    }

    /**
     * Metoda inicjująca obiekt Hotel danymi z pliku podanego w parametrze fileName.
     *
     * @param fileName nazwa pliku, z którego Hotel ma pobrać swoje dane
     */
    public void init(String fileName) {
        try (Reader reader = new FileReader(fileName)) {
            CSVParser csvParser = new CSVParser(reader,
                    CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build());
            List<String> headerNames = csvParser.getHeaderNames();
            if (!headers.equals(headerNames)) {
                System.out.println("BŁĄD: nagłówki wczytywanego pliku nie są zdefiniowane poprawnie!");
                Main.setIsRunning(false);
                return;
            }
            for (CSVRecord rec : csvParser) {
                String roomNumber = rec.get(headers.get(0));
                Integer price = Integer.parseInt(rec.get(headers.get(1)));
                Integer availability = Integer.parseInt(rec.get(headers.get(2)));
                Integer capacity = Integer.parseInt(rec.get(headers.get(3)));

                ArrayList<String> guests = new ArrayList<>();
                LocalDate checkinDate = null;
                Integer stayLength = null;
                if (!rec.get(headers.get(4)).isEmpty()) {
                    guests.addAll(Arrays.asList(rec.get(headers.get(4)).split(";")));
                    checkinDate = LocalDate.parse(rec.get(headers.get(5)));
                    stayLength = Integer.parseInt(rec.get(headers.get(6)));
                }

                RoomInfo roomInfo = new RoomInfo(price, availability, capacity, guests, checkinDate, stayLength);
                hotelInfo.put(roomNumber, roomInfo);
            }
        } catch (IOException e) {
            System.out.println("Wystąpił błąd przy odczycie pliku: " + e.getMessage());
            Main.setIsRunning(false);
        }
    }

    /**
     * Getter zwracający rozmiar hotelu - liczbę pokoi w hotelu
     *
     * @return liczbę pokoi w hotelu
     */
    public int hotelSize() {
        return hotelInfo.size();
    }

    /**
     * Wrapper dla metody keys() z klasy MyMap zwracający numery pokoi hotelowych
     *
     * @return Lista numerów pokoi hotelowych
     */
    public List<String> getRoomNumbers() {
        return hotelInfo.keys();
    }

    /**
     * Wrapper dla metody get() z klasy MyMap.
     *
     * @param number numer pokoju, dla którego informacje chcemy uzyskać
     * @return obiekt RoomInfo reprezentujący wszystkie dostępne informacje o pokoju
     */
    public RoomInfo getRoomInfo(String number) {
        return hotelInfo.get(number);
    }

    /**
     * Wrapper dla metody contains() z klasy MyMap
     *
     * @param roomNumber numer pokoju, którego poprawność chcemy sprawdzić
     * @return true - jeśli pokój o podanym numerze istnieje, false - jeśli nie
     */
    public boolean checkRoomNumber(String roomNumber) {
        return hotelInfo.contains(roomNumber);
    }

    /**
     * Wrapper dla metody put() z klasy MyMap
     *
     * @param roomNumber  numer pokoju, dla którego chcemy zaktualizować dane lub, który chcemy dodać do hotelu
     * @param newRoomInfo informacje o pokoju, które chcemy dodać
     * @return true - jeśli dodawanie/aktualizacja przebiegły pomyślnie, false - jeśli nie
     */
    public boolean updateRoomInfo(String roomNumber, RoomInfo newRoomInfo) {
        return hotelInfo.put(roomNumber, newRoomInfo);
    }

    /**
     * Metoda konwertująca hotel na listę rekordów w postaci tablic String[], gdzie każdy String[] odpowiada jednemu
     * pokojowi.
     *
     * @return listę tablic String[] reprezentujących pełną informację o danym pokoju
     */
    public List<String[]> hotelToRecordsList() {
        List<String[]> records = new ArrayList<>();
        for (String number : hotelInfo.keys()) {
            records.add(hotelInfo.get(number).toCSVFormat(number));
        }
        return records;
    }

    /**
     * Getter atrybuty headers
     *
     * @return listę nagłówków pliku wczytanego przez hotel
     */
    public List<String> getHeaders() {
        return headers;
    }
}