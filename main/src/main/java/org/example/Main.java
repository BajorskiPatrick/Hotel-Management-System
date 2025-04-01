package org.example;

import org.example.annotations.ScannerUser;
import org.example.commands.CommandsProcessor;
import org.example.commands.SaveCommand;
import org.example.hotel.Hotel;

import java.util.Scanner;

/**
 * Główna klasa projektu, która zarządza całym projektem
 */
@ScannerUser
public class Main {
    /**
     * Atrybut typu Scanner służący do odczytu danych. Wspólny dla całego projektu
     */
    private final Scanner scanner;

    /**
     * Atrybut typu Hotel reprezentujący hotel, na którym będą wykonywane wszelkie operacje.
     * Wspólny dla całego projektu
     */
    private final Hotel hotel;

    /**
     * Atrybut typu CommandsProcessor, który służy do zarządzania wywołaniami kolejnych komend wczytanych
     * od użytkownika. Jest jedyną instancją tej klasy w projekcie
     */
    private final CommandsProcessor commandsProcessor;

    /**
     * Atrybut przechowujący instancję komendy SaveCommand, który odpowiada za tworzenie kopii zapasowej pliku
     */
    private final SaveCommand commandToMakeCopy;

    /**
     * Zmienna informująca czy program ma działać dalej, czy powinien zakończyć swoje działanie.
     * Domyślnie ustawiona na true przy uruchomieniu programu. Jeśli false, to program kończy działanie
     */
    private static boolean isRunning = true;


    /**
     * Konstruktor obiektu klasy Main
     *
     * @param fileToRead   nazwa pliku, z którego dane będą odczytane i do którego zostaną zapisane
     * @param fileToBackup nazwa pliku, do którego zapisywane będą kopie zapasowe pliku fileToRead
     */
    private Main(String fileToRead, String fileToBackup) {
        scanner = new Scanner(System.in);
        hotel = new Hotel(fileToRead);
        commandToMakeCopy = new SaveCommand(fileToBackup);
        commandsProcessor = new CommandsProcessor(scanner, fileToRead);
    }


    /**
     * Metoda main, która rozpoczyna działanie programu
     *
     * @param args tablica argumentów podanych z wiersza poleceń przy uruchamianiu programu
     */
    public static void main(String[] args) {
        String fileToRead;
        String fileToBackup;
        if (args.length == 0) {
            fileToRead = "data.csv";
            fileToBackup = "data-copy.csv";
        } else if (args.length == 1 || args.length > 2) {
            System.out.println("Musisz podać 0 lub 2 argumenty!");
            return;
        } else {
            fileToRead = args[0];
            fileToBackup = args[1];
        }

        Main programEngine = new Main(fileToRead, fileToBackup);
        if (!isRunning)
            return;

        programEngine.createFileBackup();

        programEngine.runProgram();
    }


    /**
     * Metoda odpowiadająca za kontrolę wczytywania komend od użytkownika. Kończy działanie programu po wczytaniu
     * od użytkownika komendy Exit
     */
    private void runProgram() {
        while (isRunning) {
            boolean check = this.readAndExecuteCommand();
            if (!check) {
                System.out.println("Niepoprawna komenda! Spróbuj ponownie (exit, jeśli zamknąć program)");
                System.out.println();
            }
        }
    }

    /**
     * Metoda wczytująca komendy od użytkownika i uruchamiająca wczytaną komendę
     *
     * @return true - jeśli podano poprawną komendę, false - jeśli podano niepoprawną komendę
     */
    private boolean readAndExecuteCommand() {
        System.out.print("Wprowadź komendę: ");
        String command = scanner.nextLine();
        boolean check = commandsProcessor.executeCommand(command, hotel);
        if (command.equalsIgnoreCase("save"))
            createFileBackup();
        System.out.println();
        return check;
    }

    /**
     * Metoda wrapper wywołująca funkcję execute atrybutu commandToCopy, w celu utworzenia kopii zapasowej pliku
     */
    private void createFileBackup() {
        commandToMakeCopy.execute(hotel);
    }

    /**
     * Getter atrybutu statycznego isRunning
     *
     * @return wartość atrybutu isRunning
     */
    public static boolean getIsRunning() {
        return isRunning;
    }

    /**
     * Setter atrybuty statycznego isRunning
     *
     * @param value wartość, na którą chcemy ustawić isRunning
     */
    public static void setIsRunning(boolean value) {
        Main.isRunning = value;
    }
}