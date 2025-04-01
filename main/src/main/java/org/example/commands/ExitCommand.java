package org.example.commands;

import org.example.Main;
import org.example.hotel.Hotel;

/**
 * Klasa reprezentująca komendę ExitCommand. Odpowiada za zamknięcie systemu i wyświetlenie komunikatu pożegnalnego
 */
public class ExitCommand extends Command {
    /**
     * Metoda execute nadpisana dla klasy ExitCommand, która odpowiada za wykonanie komendy
     */
    @Override
    public String execute(Hotel hotel) {
        Main.setIsRunning(false);
        return "Goodbye!";
    }
}
