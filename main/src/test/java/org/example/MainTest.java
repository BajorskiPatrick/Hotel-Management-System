package org.example;

import org.example.commands.*;

import org.example.hotel.Hotel;
import org.example.hotel.RoomInfo;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Tag("MainTest")
@DisplayName("Main Test Suite")
class MainTest {

    @org.junit.jupiter.api.Nested
    class ViewCommandTests {
        @Test
        void viewCommandCorrectAvailableRoomTest() {
            String input = "102\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            Command view = new ViewCommand(new Scanner(System.in));
            assertEquals(view.execute(new Hotel("src/test/resources/data-test.csv")),
                    "Informacje o pokoju numer 102:\n" + "Cena: 120" + "zł, Zajętość: dostępny"
                    + ", Pojemność: 2" + ", Goście: , Data zameldowania: , Data wymeldowania:");


            System.setIn(System.in);
            System.setOut(System.out);
        }

        @Test
        void viewCommandIncorrectRoomTest() {
            String input = "100\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            Hotel hotel = new Hotel("src/test/resources/data-test.csv");
            Command view = new ViewCommand(new Scanner(System.in));
            assertThrows(NoSuchElementException.class, () -> view.execute(hotel));
            assertTrue(bos.toString().contains("Podany pokój nie istnieje!"));

            System.setIn(System.in);
            System.setOut(System.out);
        }

        @Test
        void viewCommandIncorrectThenCorrectRoomTest() {
            String input = "100\n201\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            Command view = new ViewCommand(new Scanner(System.in));
            assertEquals(view.execute(new Hotel("src/test/resources/data-test.csv")),
                    "Informacje o pokoju numer 201:\n" + "Cena: 200" + "zł, Zajętość: dostępny"
                    + ", Pojemność: 4" + ", Goście: , Data zameldowania: , Data wymeldowania:");
            assertTrue(bos.toString().contains("Podany pokój nie istnieje!"));

            System.setIn(System.in);
            System.setOut(System.out);
        }

        @Test
        void viewCommandCorrectUnavailableRoomTest() {
            String input = "202\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            Command view = new ViewCommand(new Scanner(System.in));
            assertEquals(view.execute(new Hotel("src/test/resources/data-test.csv")),
                    "Informacje o pokoju numer 202:\n" + "Cena: 200" + "zł, Zajętość: zajęty"
                    + ", Pojemność: 4" + ", Goście: Jan Nowak, Karolina Nowak, Jakub Nowak, " +
                    "Data zameldowania: 2024-11-10, Data wymeldowania: 2024-11-23");


            System.setIn(System.in);
            System.setOut(System.out);
        }

        @Test
        void viewCommandZeroProvidedTest() {
            String input = "0\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            Command view = new ViewCommand(new Scanner(System.in));
            assertEquals("Zakończono wykonywanie komendy!",
                    view.execute(new Hotel("src/test/resources/data-test.csv")));


            System.setIn(System.in);
            System.setOut(System.out);
        }
    }


    @Test
    void pricesCommandTest() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));

        Command prices = new PricesCommand();
        assertEquals(prices.execute(new Hotel("src/test/resources/data-test.csv")),
                """
                        Nr pokoju: 001, cena: 100zł
                        Nr pokoju: 002, cena: 120zł
                        Nr pokoju: 101, cena: 120zł
                        Nr pokoju: 102, cena: 120zł
                        Nr pokoju: 201, cena: 200zł
                        Nr pokoju: 202, cena: 200zł
                        """
        );

        System.setOut(System.out);
    }


    @Test
    void listCommandTest() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));

        Command list = new ListCommand();
        assertEquals(list.execute(new Hotel("src/test/resources/data-test.csv")),
    """
            Nr pokoju: 001, Zajętość: dostępny, Goście: , Data zameldowania: , Data wymeldowania:
            Nr pokoju: 002, Zajętość: zajęty, Goście: Andrzej Kowalski, Janina Kowalska, Data zameldowania: 2024-11-16, Data wymeldowania: 2024-11-20
            Nr pokoju: 101, Zajętość: dostępny, Goście: , Data zameldowania: , Data wymeldowania:
            Nr pokoju: 102, Zajętość: dostępny, Goście: , Data zameldowania: , Data wymeldowania:
            Nr pokoju: 201, Zajętość: dostępny, Goście: , Data zameldowania: , Data wymeldowania:
            Nr pokoju: 202, Zajętość: zajęty, Goście: Jan Nowak, Karolina Nowak, Jakub Nowak, Data zameldowania: 2024-11-10, Data wymeldowania: 2024-11-23
            """
        );

        System.setOut(System.out);
    }


    @org.junit.jupiter.api.Nested
    class CheckoutCommandTests {
        @Test
        void checkoutCommandZeroProvidedTest() {
            String input = "0\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            Command checkout = new CheckoutCommand(new Scanner(System.in));
            assertEquals("Zakończono wykonywanie komendy!",
                    checkout.execute(new Hotel("src/test/resources/data-test.csv")));

            System.setOut(System.out);
            System.setIn(System.in);
        }

        @Test
        void checkoutCommandUnavailableRoomTest() {
            long days = ChronoUnit.DAYS.between(LocalDate.of(2024, 11, 16), LocalDate.now());
            long price = days * 120;
            String input = "002\n" + price + "\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            Hotel hotel = new Hotel("src/test/resources/data-test.csv");
            Command checkout = new CheckoutCommand(new Scanner(System.in));

            String result = checkout.execute(hotel);

            RoomInfo updatedInfo = new RoomInfo(Integer.parseInt("120"), Integer.parseInt("1"),
                    Integer.parseInt("2"), new ArrayList<>(), null, null);

            assertEquals(updatedInfo, hotel.getRoomInfo("002"));
            assertEquals("Pokój 002 został zwolniony poprawnie!", result);

            System.setOut(System.out);
            System.setIn(System.in);
        }

        @Test
        void checkoutCommandUnavailableRoomAndChangeTest() {
            long days = ChronoUnit.DAYS.between(LocalDate.of(2024, 11, 16), LocalDate.now());
            long price = days * 120 + 100;
            String input = "002\n" + price + "\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            Command checkout = new CheckoutCommand(new Scanner(System.in));
            checkout.execute(new Hotel("src/test/resources/data-test.csv"));
            assertTrue(bos.toString().contains("Reszta do wydania: 100zł"));

            System.setOut(System.out);
            System.setIn(System.in);
        }

        @Test
        void checkoutCommandAvailableRoomTest() {
            String input = "001\n0\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            Command checkout = new CheckoutCommand(new Scanner(System.in));
            checkout.execute(new Hotel("src/test/resources/data-test.csv"));
            assertTrue(bos.toString().contains("Podany pokój jest już wolny!"));

            System.setOut(System.out);
            System.setIn(System.in);
        }

        @Test
        void checkoutCommandIncorrectRoomTest() {
            String input = "000\n0\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            Command checkout = new CheckoutCommand(new Scanner(System.in));
            checkout.execute(new Hotel("src/test/resources/data-test.csv"));
            assertTrue(bos.toString().contains("Podany pokój nie istnieje!"));

            System.setOut(System.out);
            System.setIn(System.in);
        }
    }


    @org.junit.jupiter.api.Nested
    class CheckinCommandTests {
        @Test
        void checkinCommandAvailableRoomTest() {
            String input = "001\nJan Kowalski\n1\nMaria Kowalska\n\n3\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            Hotel hotel = new Hotel("src/test/resources/data-test.csv");
            Command checkin = new CheckinCommand(new Scanner(System.in));

            String result = checkin.execute(hotel);

            RoomInfo updatedInfo = new RoomInfo(100, 0, 2,
                    new ArrayList<>(Arrays.asList("Jan Kowalski", "Maria Kowalska")), LocalDate.now(), 3);

            assertEquals(updatedInfo, hotel.getRoomInfo("001"));
            assertEquals("Poprawnie zameldowano gości w pokoju 001!", result);

            System.setOut(System.out);
            System.setIn(System.in);
        }

        @Test
        void checkinCommandZeroProvidedTest() {
            String input = "0\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            Command checkin = new CheckinCommand(new Scanner(System.in));
            assertEquals("Zakończono wykonywanie komendy!",
                    checkin.execute(new Hotel("src/test/resources/data-test.csv")));

            System.setOut(System.out);
            System.setIn(System.in);
        }

        @Test
        void checkinCommandUnavailableRoomTest() {
            String input = "202\n0\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            Command checkin = new CheckinCommand(new Scanner(System.in));
            checkin.execute(new Hotel("src/test/resources/data-test.csv"));

            assertTrue(bos.toString().contains("Podany pokój jest już zajęty"));

            System.setOut(System.out);
            System.setIn(System.in);
        }

        @Test
        void checkinCommandIncorrectRoomTest() {
            String input = "200\n0\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            Command checkin = new CheckinCommand(new Scanner(System.in));
            checkin.execute(new Hotel("src/test/resources/data-test.csv"));

            assertTrue(bos.toString().contains("Podany pokój nie istnieje!"));

            System.setOut(System.out);
            System.setIn(System.in);
        }
    }


    @Test
    void exitCommandTest() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));

        Command exit = new ExitCommand();
        assertEquals("Goodbye!", exit.execute(new Hotel("src/test/resources/data-test.csv")));
        assertFalse(Main.getIsRunning());

        System.setOut(System.out);
    }


    @org.junit.jupiter.api.Nested
    class SaveCommandTests {
        @Test
        void checkinSaveAndCheckoutSaveTest() throws IOException {
            long days = ChronoUnit.DAYS.between(LocalDate.of(2024, 11, 15), LocalDate.now());
            long price = days * 100;
            String input = "001\nJan Kowalski\n1\nMaria Kowalska\n2024-11-15\n3\n" + "001\n" + price + "\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            Hotel hotel = new Hotel("src/test/resources/data-test.csv");
            Scanner scanner = new Scanner(System.in);

            Command checkin = new CheckinCommand(scanner);
            checkin.execute(hotel);

            Command saveCopy = new SaveCommand("src/test/resources/data-test-copy.csv");
            Command save = new SaveCommand("src/test/resources/data-test.csv");

            String result1 = save.execute(hotel);
            saveCopy.execute(hotel);
            assertEquals("Poprawnie zapisano dane do pliku!", result1);

            Path path = Paths.get("src/test/resources/data-test.csv");
            List<String> lines1 = Files.readAllLines(path);
            List<String> expectedLines1 = List.of(
                    "room number,price,availability,capacity,guests,checkinDate,stayLength",
                    "001,100,0,2,Jan Kowalski;Maria Kowalska,2024-11-15,3",
                    "002,120,0,2,Andrzej Kowalski;Janina Kowalska,2024-11-16,5",
                    "101,120,1,2,,,",
                    "102,120,1,2,,,",
                    "201,200,1,4,,,",
                    "202,200,0,4,Jan Nowak;Karolina Nowak;Jakub Nowak,2024-11-10,14"
            );
            assertEquals(expectedLines1, lines1);

            Command checkout = new CheckoutCommand(scanner);
            checkout.execute(hotel);
            String result2 = save.execute(hotel);
            saveCopy.execute(hotel);
            assertEquals("Poprawnie zapisano dane do pliku!", result2);

            List<String> lines2 = Files.readAllLines(path);
            List<String> expectedLines2 = List.of(
                    "room number,price,availability,capacity,guests,checkinDate,stayLength",
                    "001,100,1,2,,,",
                    "002,120,0,2,Andrzej Kowalski;Janina Kowalska,2024-11-16,5",
                    "101,120,1,2,,,",
                    "102,120,1,2,,,",
                    "201,200,1,4,,,",
                    "202,200,0,4,Jan Nowak;Karolina Nowak;Jakub Nowak,2024-11-10,14"
            );
            assertEquals(expectedLines2, lines2);

            System.setOut(System.out);
            System.setIn(System.in);
        }

        @Test
        void invalidFileToSaveTest() {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            Command save = new SaveCommand("resources/test/data-test.csv");
            save.execute(new Hotel("src/test/resources/data-test.csv"));
            assertFalse(Main.getIsRunning());

            System.setOut(System.out);
        }
    }


    @org.junit.jupiter.api.Nested
    class CommandsProcessorTests {
        @Test
        void invalidCommandExecutionTest() {
            Hotel hotel = new Hotel("src/test/resources/data-test.csv");
            CommandsProcessor processor = new CommandsProcessor(new Scanner(System.in),
                    "src/test/resources/data-test.csv");
            assertFalse(processor.executeCommand("przykład", hotel));
            assertFalse(processor.executeCommand("prices_", hotel));
        }

        @Test
        void validCommandExecutionTest() {
            String input = "0\n0\n0\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            Hotel hotel = new Hotel("src/test/resources/data-test.csv");
            CommandsProcessor processor = new CommandsProcessor(new Scanner(System.in),
                    "src/test/resources/data-test.csv");

            assertTrue(processor.executeCommand("Prices", hotel));
            assertTrue(processor.executeCommand("prices", hotel));
            assertTrue(processor.executeCommand("PrIcEs", hotel));
            assertTrue(processor.executeCommand("list", hotel));
            assertTrue(processor.executeCommand("save", hotel));
            assertTrue(processor.executeCommand("View", hotel));
            assertTrue(processor.executeCommand("CheckIn", hotel));
            assertTrue(processor.executeCommand("CheckOut", hotel));
            assertTrue(processor.executeCommand("exit", hotel));

            System.setIn(System.in);
            System.setOut(System.out);
        }

        @Test
        void resultPrintingTest() {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            CommandsProcessor processor = new CommandsProcessor(new Scanner(System.in),
                    "src/test/resources/data-test.csv");

            processor.printResult("Wynik");
            assertEquals("Wynik\r\n", bos.toString());

            processor.printResult("");
            assertEquals("Wynik\r\n", bos.toString());

            System.setOut(System.out);
        }
    }


    @org.junit.jupiter.api.Nested
    class AdditionalHotelTests {
        @Test
        void invalidFileToInitializeHotelTest() {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));
            new Hotel("src/test/resources/błędny-plik.csv");
            assertFalse(Main.getIsRunning());

            System.setOut(System.out);
        }

        @Test
        void invalidHeadersInFileTest() {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));
            new Hotel("src/test/resources/data-test-invalid-headers.csv");
            assertTrue(bos.toString().contains("BŁĄD: nagłówki wczytywanego pliku nie są zdefiniowane poprawnie!"));
            assertFalse(Main.getIsRunning());
            System.setOut(System.out);
        }
    }


    @org.junit.jupiter.api.Nested
    class AdditionalRoomInfoTests {
        @Test
        void nullGuestsInitializationTest() {
            RoomInfo roomInfo = new RoomInfo(10, 0, 1,
                    null, null, null);
            assertTrue(roomInfo.guestsToString().isEmpty());
        }

        @Test
        void RoomInfoShouldBoEqualToItself() {
            RoomInfo roomInfo = new RoomInfo(10, 0, 1,
                    null, null, null);
            assertEquals(roomInfo, roomInfo);
        }

        @Test
        void RoomInfosWithSameParametersShouldBeEqual() {
            RoomInfo roomInfo = new RoomInfo(10, 0, 1,
                    null, null, null);
            RoomInfo roomInfo2 = new RoomInfo(10, 0, 1,
                    null, null, null);
            assertEquals(roomInfo, roomInfo2);

            RoomInfo roomInfo3 = new RoomInfo(10, 0, 1, new ArrayList<>(Arrays.asList("Jan", "Anna")),
                    LocalDate.of(2024, 11, 17), 5);
            RoomInfo roomInfo4 = new RoomInfo(10, 0, 1, new ArrayList<>(Arrays.asList("Jan", "Anna")),
                    LocalDate.of(2024, 11, 17), 5);
            assertEquals(roomInfo3, roomInfo4);
        }

        @Test
        void RoomInfosWithDifferentParametersShouldNotBeEqual() {
            RoomInfo roomInfo = new RoomInfo(10, 0, 1, null, null, null);
            RoomInfo roomInfo2 = new RoomInfo(10, 0, 1, new ArrayList<>(Arrays.asList("Jan", "Anna")),
                    LocalDate.of(2024, 11, 17), 5);
            assertNotEquals(roomInfo, roomInfo2);
        }

        @Test
        void RoomInfoShouldNotBeEqualToDifferentClassObject() {
            RoomInfo roomInfo = new RoomInfo(10, 0, 1, null, null, null);
            Object newObject = new Object();
            assertNotEquals(roomInfo, newObject);
        }

        @Test
        void RoomInfoShouldAlwaysHashToTheSameValue() {
            RoomInfo roomInfo = new RoomInfo(10, 0, 1, null, null, null);
            assertEquals(roomInfo.hashCode(), roomInfo.hashCode());
        }

        @Test
        void RoomInfosWithSameParametersShouldHashToTheSameValue() {
            RoomInfo roomInfo = new RoomInfo(10, 0, 1, null, null, null);
            RoomInfo roomInfo2 = new RoomInfo(10, 0, 1, null, null, null);
            assertEquals(roomInfo.hashCode(), roomInfo2.hashCode());

            RoomInfo roomInfo3 = new RoomInfo(10, 0, 1, new ArrayList<>(Arrays.asList("Jan", "Anna")),
                    LocalDate.of(2024, 11, 17), 5);
            RoomInfo roomInfo4 = new RoomInfo(10, 0, 1, new ArrayList<>(Arrays.asList("Jan", "Anna")),
                    LocalDate.of(2024, 11, 17), 5);
            assertEquals(roomInfo3.hashCode(), roomInfo4.hashCode());
        }

        @Test
        void RoomInfosWithDifferentParametersShouldNeverHashToTheSameValue() {
            RoomInfo roomInfo = new RoomInfo(10, 0, 1, null, null, null);
            RoomInfo roomInfo2 = new RoomInfo(10, 0, 1, new ArrayList<>(Arrays.asList("Jan", "Anna")),
                    LocalDate.of(2024, 11, 17), 5);
            assertNotEquals(roomInfo.hashCode(), roomInfo2.hashCode());
        }
    }


    @org.junit.jupiter.api.Nested
    class AdditionalMainTests {
        @Test
        void startingProgramWithDefaultFilesTest() {
            String input = "exit\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            String[] emptyArgs = {};
            Main.main(emptyArgs);

            assertFalse(bos.toString().contains("Musisz podać 0 lub 2 argumenty!"));

            System.setOut(System.out);
            System.setIn(System.in);
        }

        @Test
        void startingProgramWithProvidedFilesTest() {
            String input = "exit\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            String[] args = {"src/test/resources/data-test.csv", "src/test/resources/data-test-copy.csv"};
            Main.main(args);
            assertFalse(bos.toString().contains("Musisz podać 0 lub 2 argumenty!"));

            System.setOut(System.out);
            System.setIn(System.in);
        }

        @Test
        void startingProgramWithInvalidNumberOfArgsTest() {
            String input = "exit\nexit\n";
            ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
            System.setIn(bis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos));

            String[] args = {"src/test/resources/data-test.csv"};
            Main.main(args);
            assertTrue(bos.toString().contains("Musisz podać 0 lub 2 argumenty!"));

            String[] args2 = {"1", "2", "3"};
            Main.main(args2);
            assertTrue(bos.toString().contains("Musisz podać 0 lub 2 argumenty!"));

            System.setOut(System.out);
            System.setIn(System.in);
        }
    }


    @Test
    void providingIncorrectThenCorrectCommandTest() {
        String input = "błąd\nexit\n";
        ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
        System.setIn(bis);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));

        String[] args = {"src/test/resources/data-test.csv", "src/test/resources/data-test-copy.csv"};
        Main.main(args);
        assertTrue(bos.toString().contains("Niepoprawna komenda! Spróbuj ponownie (exit, jeśli zamknąć program)"));


        System.setOut(System.out);
        System.setIn(System.in);
    }
}
