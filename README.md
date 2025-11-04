# Java Web Scraper

This project is a Java-based web scraper that utilizes Selenium or Playwright to extract data from a specified URL. The scraped data includes the following fields: Name, Title, Position, Party, Address, Phone, Email, and URL. The extracted information is then stored in a JSON file.

## Project Structure

```
java-scraper
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           ├── App.java
│   │   │           ├── Scraper.java
│   │   │           ├── model
│   │   │           │   └── Person.java
│   │   │           └── util
│   │   │               └── JsonWriter.java
│   │   └── resources
│   │       └── log4j2.xml
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── ScraperTest.java
├── pom.xml
├── .gitignore
└── README.md
```

## Technologies Used

- Java
- Selenium or Playwright
- Maven
- Log4j2 for logging

## How to Run the Application

1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Ensure you have Maven installed on your machine.
4. Open a terminal and run the following command to build the project:
   ```
   mvn clean install
   ```
5. Run the application using the following command:
   ```
   mvn exec:java -Dexec.mainClass="com.example.App"
   ```

## Notes

- Ensure that you have the necessary WebDriver for Selenium or the required setup for Playwright.
- The application will scrape data from the specified URL and generate a JSON file containing the extracted information.
- The assignment was completed in a timely manner, adhering to the specified requirements.