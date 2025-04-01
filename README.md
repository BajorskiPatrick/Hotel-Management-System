# Hotel Management System

## Description

**Hotel Management System** is a console-based Java application that enables hotel room management. Users can perform operations such as checking guests in and out, viewing room lists, checking availability, displaying room details and prices, and saving hotel data to a file.

---

## Project Structure

### Main Classes:
- `Main.java`: Starts the application and manages user interaction.
- `Hotel.java`: Manages room information and hotel data operations.
- `RoomInfo.java`: Stores detailed room information such as price, availability, guest list, check-in date, and length of stay.

### Package `commands`: Contains classes implementing various application functions:
- `CheckinCommand`: Handles guest check-in.
- `CheckoutCommand`: Handles guest check-out and bill calculation.
- `ListCommand`: Displays all rooms.
- `PricesCommand`: Displays the prices of all rooms.
- `SaveCommand`: Saves hotel data to a file and creates backups.
- `ViewCommand`: Shows detailed information about a room.
- `ExitCommand`: Closes the application.
- `CommandsProcessor`: Manages and processes user commands.

### Annotations:
- `FileHandler.java`: Marks classes that handle file input/output.
- `ScannerUser.java`: Marks classes that use a `Scanner` object.

### Package `utils`: Contains utility classes:
- `Map.java`: A map interface, similar to `java.util.Map`.
- `MyMap.java`: Implementation of the `Map` interface.

### Configuration Files:
- `pom.xml`: Maven configuration file defining dependencies, compilation, and plugins, including SonarQube setup.

---

## Requirements

- Java: 17 or later
- Maven: For dependency management and build

### Libraries:
- `org.apache.commons:commons-csv:1.10.0` (CSV file handling)
- `org.reflections:reflections:0.10.2` (class scanning via reflection)
- `org.slf4j:slf4j-api:2.0.16` and `org.slf4j-simple:2.0.16` (logging)

---

## Running the Application

1. **Compilation** – Use Maven to build the project:
```bash
mvn clean package
```

2. **Execution** – Use IntelliJ IDEA or run the generated JAR file:
```bash
java -jar main/target/main-1.0-jar-with-dependencies.jar [data.csv] [data-copy.csv]
```

- **Optional arguments**: You may provide custom paths to CSV files if you do not want to use the default ones.

---

## Command Overview

- `checkin`: Check guests into a room.
- `checkout`: Check guests out and calculate the bill.
- `list`: Display a list of all rooms.
- `prices`: Display room prices.
- `view`: Show detailed information about a specific room.
- `save`: Save hotel data to a file and create a backup.
- `exit`: Exit the program.

---

## Providing Input via Terminal

While using the application, users are prompted to provide data in the terminal. Each value should be entered on a new line and confirmed with **Enter**.  
Here are several examples:

1. **Selecting commands** – After starting the application, the prompt will appear:
   ```
   Enter a command:
   ```

2. **Entering room number** – When a command requires a room number (e.g., `checkin`, `view`), the following message will appear:
   ```
   Enter room number (0 to cancel):
   ```

3. **Entering guest list** – When checking guests in (`checkin`), the app will request guest details (name and surname):
   ```
   Enter main guest information:
   ```

4. **Providing check-in date and length of stay**:
   ```
   Enter check-in date in DD-MM-YYYY format (press enter for today):
   Enter length of stay (number of days):
   ```

5. **Payment at checkout**:
   ```
   Amount paid by guest:
   ```

6. **Feedback**:
   ```
   Guests successfully checked into room 101!
   ```

Ensure that you follow the on-screen instructions carefully so that the application works as expected.

---

## CSV File Format

The CSV file should contain the following headers:
```
room number,price,availability,capacity,guests,checkinDate,stayLength
```

- **room number**: Room number (`String`)
- **price**: Price per night (`Integer`)
- **availability**: 1 - available, 0 - unavailable (`Integer`)
- **capacity**: Maximum number of guests (`Integer`)
- **guests**: List of guests, separated by semicolons (`String`)
- **checkinDate**: Check-in date (`LocalDate`)
- **stayLength**: Length of stay in days (`Integer`)

---

## Additional Information

- The application uses annotations to mark classes that require a `Scanner` or perform file operations.
- **SonarQube**: The project is configured for static code analysis with SonarQube.
- **Testing**: The project uses **JUnit 5** for unit tests, configured in the `pom.xml` file.