package org.example.commands;

import org.example.Main;
import org.example.annotations.FileHandler;
import org.example.annotations.ScannerUser;
import org.example.hotel.Hotel;
import org.example.utils.MyMap;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import org.reflections.Reflections;

/**
 * Klasa reprezentująca procesor do zarządzania komendami. Odpowiada za utworzenie obiektów każdej komendy, które są
 * wykorzystywane przez cały czas działania programu do wywoływania odpowiednich komend
 */
public class CommandsProcessor {
    private final MyMap<String, Command> commands = new MyMap<>();
    private final ArrayList<String> commandsNames = new ArrayList<>();

    /**
     * Konstruktor obiektu klasy CommandsProcessor
     *
     * @param scanner  obiekt Scanner, z którego korzystać będą wymagające tego komendy
     * @param fileName obiekt String reprezentujący plik, którego używać będą wszystkie komendy
     */
    public CommandsProcessor(Scanner scanner, String fileName) {
        generateCommands(scanner, fileName);
    }

    /**
     * Metoda generująca mapę mapującą nazwy komend, do obiektów reprezentujących te komendy
     */
    private void generateCommands(Scanner scanner, String fileName) {
        Reflections reflections = new Reflections("org.example.commands");
        Set<Class<? extends Command>> commandClasses = reflections.getSubTypesOf(Command.class);

        for (Class<? extends Command> commandClass : commandClasses) {
            try {
                Command command;
                if (commandClass.isAnnotationPresent(FileHandler.class) &&
                        commandClass.isAnnotationPresent(ScannerUser.class))
                    command = commandClass.getDeclaredConstructor(Scanner.class, String.class).
                            newInstance(scanner, fileName);
                else if (commandClass.isAnnotationPresent(FileHandler.class))
                    command = commandClass.getDeclaredConstructor(String.class).newInstance(fileName);
                else if (commandClass.isAnnotationPresent(ScannerUser.class))
                    command = commandClass.getDeclaredConstructor(Scanner.class).newInstance(scanner);
                else
                    command = commandClass.getDeclaredConstructor().newInstance();

                String commandName = command.getClass().getSimpleName();
                commandName = commandName.replaceAll("Command$", "").toLowerCase();
                commands.put(commandName, command);
                commandsNames.add(commandName);
            } catch (Exception e) {
                System.out.println("Nastąpił błąd przy wczytywaniu klas reprezentujących komendy: " + e.getMessage());
                Main.setIsRunning(false);
            }
        }
    }

    /**
     * Metoda odpowiadająca za wywołanie metody execute z odpowiedniej komendy
     *
     * @param command komenda, którą wykonujemy
     * @param hotel   obiekt Hotel, na którym ma zostać wykonana komenda
     * @return true - jeśli podano prawidłową nazwę komendy, false - jeśli nie
     */
    public boolean executeCommand(String command, Hotel hotel) {
        command = command.toLowerCase();
        if (!commandsNames.contains(command))
            return false;
        printResult(commands.get(command).execute(hotel));
        return true;
    }

    /**
     * Metoda drukująca na ekran wynik działania komendy w postaci String
     *
     * @param result wynik działania komendy
     */
    public void printResult(String result) {
        if (!result.isEmpty())
            System.out.println(result);
    }
}
