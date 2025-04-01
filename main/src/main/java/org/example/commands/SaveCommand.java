package org.example.commands;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.example.Main;
import org.example.annotations.FileHandler;
import org.example.hotel.Hotel;

import java.io.*;
import java.util.List;

/**
 * Klasa reprezentująca komendę SaveCommand. Odpowiada za zapisanie danych hotelu do pliku o podanej nazwie oraz
 * utworzenie kopii zapasowej tego pliku przy każdym zapisie.
 */
@FileHandler
public class SaveCommand extends Command {
    private static Integer instancesCounter = 0;
    private final int instanceNumber;
    private final String fileName;

    /**
     * Konstruktor klasy SaveCommand przyjmujący 1 parametr
     *
     * @param fileName parametr konstruktora, nazwa pliku, do którego chcemy zapisać dane
     */
    public SaveCommand(String fileName) {
        this.fileName = fileName;
        instancesCounter++;
        instanceNumber = instancesCounter;
    }

    /**
     * Metoda execute nadpisana dla klasy CheckoutCommand, która odpowiada za wykonanie komendy
     */
    @Override
    public String execute(Hotel hotel) {
        try (Writer writer = new FileWriter(fileName)) {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.builder().setHeader(hotel.getHeaders().
                    toArray(new String[0])).build());

            List<String[]> recs = hotel.hotelToRecordsList();
            for (String[] rec : recs) {
                csvPrinter.printRecord((Object[]) rec);
            }
            csvPrinter.flush();

            if (instanceNumber >= 2)
                return "Poprawnie zapisano dane do pliku!";
        } catch (IOException e) {
            System.out.println("Wystąpił błąd przy zapisie do pliku: " + e.getMessage());
            Main.setIsRunning(false);
        }
        return "";
    }
}
