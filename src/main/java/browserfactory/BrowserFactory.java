package browserfactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static browserfactory.BrowserTypes.CHROME;
import static browserfactory.BrowserTypes.FIREFOX;
import static driver.DriverCapabilities.chromeOptions;


public class BrowserFactory {

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<WebDriver>();

    private static volatile BrowserFactory instance;

    public static BrowserFactory getInstance() {
        if (instance == null) {
            synchronized (BrowserFactory.class) {
                if (instance == null) {
                    instance = new BrowserFactory();
                }
            }
        }
        return instance;
    }

    private BrowserFactory() {
    }

    public static WebDriver getDriver() {
        return driverThread.get();
    }

    public WebDriver createDriverInstance(String browserName) {
        if (browserName != null) {
            if (CHROME.getBrowser().equalsIgnoreCase(browserName)) {
                WebDriverManager.chromedriver().setup();
                driverThread.set(new ChromeDriver(chromeOptions()));
            } else if (FIREFOX.getBrowser().equalsIgnoreCase(browserName)) {
                WebDriverManager.firefoxdriver().setup();
                driverThread.set(new FirefoxDriver());
            }
        }
        return getDriver();
    }

    public static void closeBrowser() {
        if (getDriver() != null)
            getDriver().quit();
    }
}