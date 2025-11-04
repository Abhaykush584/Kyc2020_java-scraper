// package com.example;

// import org.openqa.selenium.By;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.chrome.ChromeOptions;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.SerializationFeature;
// import java.io.File;
// import java.time.Duration;
// import java.util.*;
// import java.util.logging.Logger;

// public class Scraper {

//     private static final Logger logger = Logger.getLogger(Scraper.class.getName());

//     public static void main(String[] args) {
//         String url = "https://akleg.gov/senate.php";
//         logger.info("Loading list page: " + url);

//         ChromeOptions options = new ChromeOptions();
//         options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");

//         WebDriver driver = new ChromeDriver(options);
//         driver.get(url);

//         try {
//             // ✅ Wait longer for dynamic content
//             WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//             wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".member-name, .card-member")));

//             List<WebElement> cards = driver.findElements(By.cssSelector(".card-member"));
//             logger.info("Found " + cards.size() + " senator cards");

//             List<Map<String, String>> senators = new ArrayList<>();

//             for (WebElement card : cards) {
//                 Map<String, String> senator = new LinkedHashMap<>();

//                 try {
//                     String name = card.findElement(By.cssSelector(".member-name")).getText().trim();
//                     String title = "Senator";
//                     String party = card.findElement(By.cssSelector(".member-party")).getText().trim();
//                     String district = card.findElement(By.cssSelector(".member-district")).getText().trim();
//                     String link = card.findElement(By.tagName("a")).getAttribute("href");

//                     senator.put("name", name);
//                     senator.put("title", title);
//                     senator.put("position", district);
//                     senator.put("party", party);
//                     senator.put("url", link);

//                     senators.add(senator);
//                     logger.info("✅ Parsed: " + name);
//                 } catch (Exception e) {
//                     logger.warning("⚠️ Skipped one card due to missing info.");
//                 }
//             }

//             ObjectMapper mapper = new ObjectMapper();
//             mapper.enable(SerializationFeature.INDENT_OUTPUT);
//             mapper.writeValue(new File("output.json"), senators);

//             logger.info("✅ Wrote " + senators.size() + " records to output.json");

//         } catch (Exception e) {
//             logger.warning("⚠️ No senator cards loaded or site took too long.");
//         } finally {
//             driver.quit();
//         }
//     }
// }




package com.example;

import com.microsoft.playwright.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class Scraper {
    private static final Logger logger = Logger.getLogger(Scraper.class.getName());

    public static void main(String[] args) {
        String url = "https://akleg.gov/senate.php";
        logger.info("🌐 Loading: " + url);

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(true)
            );
            Page page = browser.newPage();

            page.navigate(url);
            page.waitForSelector("div.media-body h4 a",
                    new Page.WaitForSelectorOptions().setTimeout(60000));

            logger.info("✅ Page loaded successfully.");

            List<Map<String, String>> senators = new ArrayList<>();

            for (ElementHandle card : page.querySelectorAll("div.media-body")) {
                Map<String, String> senator = new LinkedHashMap<>();

                String name = getText(card, "h4 a");
                String title = "Senator";
                String party = getText(card, "p:nth-of-type(1)");
                String position = getText(card, "p:nth-of-type(2)");
                String phone = getText(card, "p:nth-of-type(3)");
                String email = getText(card, "p:nth-of-type(4) a");
                String profileUrl = card.querySelector("h4 a") != null
                        ? card.querySelector("h4 a").getAttribute("href") : "";

                senator.put("name", name.replace("Senator", "").trim());
                senator.put("title", title);
                senator.put("party", party.replace("Party:", "").trim());
                senator.put("position", position.replace("District:", "").trim());
                senator.put("phone", phone.replace("Phone:", "").trim());
                senator.put("email", email);
                senator.put("url", "https://akleg.gov/" + profileUrl);

                senators.add(senator);
                logger.info("🧾 Added: " + name);
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            try {
                mapper.writeValue(new File("output.json"), senators);
                System.out.println("✅ Wrote " + senators.size() + " records to output.json");
            } catch (IOException e) {
                logger.severe("❌ Failed to write output.json: " + e.getMessage());
            }

            browser.close();
        } catch (Exception e) {
            logger.severe("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String getText(ElementHandle card, String selector) {
        ElementHandle el = card.querySelector(selector);
        return el != null ? el.innerText().trim() : "";
    }
}

