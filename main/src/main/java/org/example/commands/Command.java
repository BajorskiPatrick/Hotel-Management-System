package org.example.commands;

import org.example.hotel.Hotel;

/**
 * Interfejs reprezentujący komendę. Interfejs ten implementowany jest przez wszystkie klasy typu Command
 */
public abstract class Command {
    /**
     * Metoda odpowiadająca za wykonanie komendy
     *
     * @param hotel obiekt Hotel, na którym ma zostać wykonana komenda
     * @return String będący wynikiem działania komendy
     */
    public abstract String execute(Hotel hotel);
}
