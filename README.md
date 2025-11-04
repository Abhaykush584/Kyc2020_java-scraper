# Java Web Scraper

This project is a Java-based web scraper that utilizes Selenium or Playwright to extract data from a specified URL. The scraped data includes the following fields: Name, Title, Position, Party, Address, Phone, Email, and URL. The extracted information is then stored in a JSON file.

## Project Structure

```
java-scraper/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/
│       │       ├── App.java
│       │       ├── Scraper.java
│       │       ├── model/Person.java
│       │       └── util/JsonWriter.java
│       └── resources/
│           └── log4j2.xml
├── pom.xml
├── output.json
├── .gitignore
└── README.md

```

## ⚙️Technologies Used
```
Technologies Used
Java 22 (JDK 17+)
Playwright for Java
Jackson Databind (for JSON writing)
Apache Maven (build tool)
```

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

## 🧩 Output Format (sample)
```
[
{
    "name": "Senator Lyman Hoffman",
    "title": "Senator",
    "party": "Democrat",
    "position": "District S",
    "phone": "(907) 465-4453",
    "email": "Senator.Lyman.Hoffman@akleg.gov",
    "url": "https://akleg.gov/senator/hoffman.php"
  }
]
```
   
## 📓 Notes

- The scraper uses Playwright for headless browsing and DOM parsing.
- Timeout errors may occur if the website takes too long to load; handled with fallback retries.
- All data is exported to a JSON file for easy integration and validation.

## ⏱️ Time Taken

- Task	Duration
- Environment setup (Java, Maven, Playwright)	1 hour
- Scraper development & testing	2 hours
- Debugging and optimization	1 hour
- Total Time Taken	~4.5 hours

## 📬 Submitted By

- Name: Abhay Kush
- Assignment: KYC2020 Java Web Scraper
- Date: November 4th Nov 2025
  
