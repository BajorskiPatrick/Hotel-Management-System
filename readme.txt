---Hotel Management System---

---Opis---
Hotel Management System to konsolowa aplikacja napisana w Javie, która umożliwia zarządzanie pokojami hotelowymi. Użytkownik może wykonywać operacje takie jak zameldowanie i wymeldowanie gości, przeglądanie listy pokoi, sprawdzanie dostępności, podgląd szczegółów pokoi, wyświetlanie cen oraz zapisywanie danych hotelowych do pliku.

---Struktura Projektu---
- Główne klasy:
  - Main.java: Uruchamia aplikację i zarządza interakcjami z użytkownikiem.
  - Hotel.java: Zarządza informacjami o pokojach i operacjami na danych hotelowych.
  - RoomInfo.java: Przechowuje szczegółowe informacje o pokoju, takie jak cena, dostępność, lista gości, data zameldowania i długość pobytu.

- Pakiet `commands`: Zawiera klasy implementujące różne funkcje aplikacji:
  - CheckinCommand: Obsługuje zameldowanie gości w pokoju.
  - CheckoutCommand: Obsługuje wymeldowanie gości i obliczanie należności.
  - ListCommand: Wyświetla informacje o wszystkich pokojach.
  - PricesCommand: Wyświetla ceny wszystkich pokoi.
  - SaveCommand: Zapisuje dane hotelowe do pliku i tworzy kopie zapasowe.
  - ViewCommand: Wyświetla szczegółowe informacje o pokoju.
  - ExitCommand: Zamyka aplikację.
  - CommandsProcessor: Zarządza i przetwarza komendy wprowadzane przez użytkownika.

- Adnotacje:
  - FileHandler.java**: Oznacza klasy, które obsługują odczyt lub zapis plików.
  - ScannerUser.java**: Oznacza klasy korzystające z obiektu `Scanner`.

- Pakiet `utils`: Zawiera klasy użytkowe:
  - Map.java: Interfejs mapy, podobny do `java.util.Map`.
  - MyMap.java: Implementacja interfejsu `Map`.

- Pliki konfiguracyjne:
  - pom.xml: Pliki konfigurujące projekt Maven, definiujące zależności, kompilację oraz pluginy, w tym konfigurację SonarQube.

---Wymagania---
- Java: 17 lub nowsza
- Maven: Do zarządzania zależnościami i kompilacją
- Biblioteki:
  - `org.apache.commons:commons-csv:1.10.0` (do obsługi plików CSV)
  - `org.reflections:reflections:0.10.2` (do wykrywania klas za pomocą refleksji)
  - `org.slf4j:slf4j-api:2.0.16` i `org.slf4j-simple:2.0.16` (logowanie)

---Uruchomienie---
1. Kompilacja: Użyj Maven do zbudowania projektu:
   `bash`
   mvn clean package

2. Uruchomienie: Użyj IDE Intellij lub uruchom wygenerowany plik JAR:
   `bash`
   java -jar main/target/main-1.0-jar-with-dependencies.jar [data.csv] [data-copy.csv]

   - Opcjonalne argumenty: Podaj ścieżki do plików CSV, jeśli chcesz użyć innych niż domyślne.

---Obsługa Komend---
- checkin: Zameldowanie gości w pokoju.
- checkout: Wymeldowanie gości i obliczenie rachunku.
- list: Wyświetlenie listy wszystkich pokoi.
- prices: Wyświetlenie cen pokoi.
- view: Wyświetlenie szczegółowych informacji o konkretnym pokoju.
- save: Zapis danych hotelowych do pliku i tworzenie kopii zapasowej.
- exit: Zakończenie działania programu.

---Wprowadzanie Danych w Terminalu---
Podczas korzystania z aplikacji, użytkownik jest proszony o wprowadzanie danych w terminalu. Każda wartość
powinna być wprowadzana w nowej linii i zatwierdzana **Enter**.
Oto kilka przykładów, jak należy to robić:

1. Wybieranie komend: Po uruchomieniu aplikacji pojawi się komunikat:
   ```
   Wprowadź komendę:
   ```
   Wpisz jedną z dostępnych komend, np. `checkin`, `checkout`, `list`, `prices`, `view`, `save`, lub `exit`, a następnie naciśnij **Enter**.

2. Podawanie numeru pokoju**: Gdy komenda wymaga podania numeru pokoju (np. `checkin`, `view`), pojawi się komunikat:
   ```
   Podaj numer pokoju (0, jeśli chcesz zakończyć):
   ```
   Wprowadź numer pokoju, np. `101`, lub wpisz `0`, aby przerwać operację, i naciśnij **Enter**.

3. Wprowadzanie listy gości**: Przy zameldowaniu gości (`checkin`), aplikacja poprosi o dane gości:
   ```
   Podaj dane gościa głównego:
   ```
   Wprowadź imię i nazwisko głównego gościa i naciśnij **Enter**. Następnie, jeśli są dodatkowi goście, podaj ich liczbę:

   ```
   Czy są goście dodatkowi (podaj ilu)? (maksymalnie X):
   ```
   Wpisz liczbę gości i naciśnij **Enter**. Następnie wprowadź dane każdego dodatkowego gościa w nowej linii.

4. Podawanie daty i długości pobytu**: Dla operacji związanych z datami, np. zameldowanie, aplikacja zapyta o datę zameldowania:
   ```
   Podaj datę zameldowania (enter, jeśli dzisiejsza):
   ```
   Wciśnij **Enter**, aby wybrać dzisiejszą datę, lub wpisz datę w formacie `YYYY-MM-DD` i naciśnij **Enter**. W przypadku długości pobytu:

   ```
   Podaj długość trwania pobytu (liczbę dni):
   ```
   Wprowadź liczbę dni i naciśnij **Enter**.

5. Opłaty za pobyt**: Przy wymeldowaniu (`checkout`), użytkownik zobaczy należność i będzie musiał podać kwotę zapłaconą przez gościa:
   ```
   Kwota zapłacona przez gościa:
   ```
   Wprowadź kwotę i naciśnij **Enter**.

6. Informacje zwrotne**: Aplikacja będzie informować o powodzeniu lub błędach, np.:
   ```
   Poprawnie zameldowano gości w pokoju 101!
   ```

Upewnij się, że wpisujesz dane zgodnie z instrukcjami wyświetlanymi na ekranie, aby aplikacja mogła działać poprawnie.

---Format Pliku CSV---
Plik CSV powinien mieć następujące nagłówki:
```
room number,price,availability,capacity,guests,checkinDate,stayLength
```
- room number: Numer pokoju (String)
- price: Cena za noc (Integer)
- availability: 1 - dostępny, 0 - niedostępny (Integer)
- capacity: Maksymalna liczba gości (Integer)
- guests: Lista gości, oddzielona średnikami (String)
- checkinDate: Data zameldowania (LocalDate)
- stayLength: Długość pobytu w dniach (Integer)

---Dodatkowe Informacje---
- Aplikacja korzysta z adnotacji do oznaczania klas wymagających `Scanner` oraz operacji na plikach.
- SonarQube: Projekt jest skonfigurowany do analizy z wykorzystaniem SonarQube.
- Testy: Używane są testy JUnit 5, skonfigurowane w pliku `pom.xml`.
