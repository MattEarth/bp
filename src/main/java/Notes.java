import org.openqa.selenium.*;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Action;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class Notes {
    private static WebDriver driver;
    private static WebDriverWait wait;
    public static void initFox(){
        System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-private");
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }
    public static void enterBeatport(){
        driver.get("https://www.Beatport.com/");
    }
    public static void closeWelcome(){
        wait = new WebDriverWait(driver,20);
        WebElement element = driver.findElement(By.xpath("//button[@class='bx-button'][@type='reset']"));
        Assert.assertTrue(element.isDisplayed());
        element.click();
    }
    public static void search(String what){
        wait = new WebDriverWait(driver,50);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='search'][@class='head-search-input tt-input']")));
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(what+Keys.ENTER);
    }
    public static void checkSearchFail(){
        wait = new WebDriverWait(driver,50);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='search-zero-message']"))));
        WebElement element = driver.findElement(By.xpath("//div[@class='search-zero-message']"));
        String message = "YOUR SEARCH GURU NET DID NOT RETURN ANY RESULTS\n" +
                "Make sure all names and titles are spelled correctly or try using different keywords in your search. If that doesn't work, try browsing the available genres.";
        Assert.assertEquals(element.getText(),message);
    }
    public static void backHome(){
        wait = new WebDriverWait(driver,50);
        wait.until(ExpectedConditions.elementToBeClickable(By.className("beatport-logo"))).click();
        Assert.assertTrue(driver.findElement(By.className("bp-top-ten-logo")).isDisplayed());
    }
    public static void checkSearchOk(){
        wait = new WebDriverWait(driver,50);
        wait.until(ExpectedConditions.elementToBeClickable(By.className("artist-name")));
        Assert.assertTrue(driver.findElement(By.xpath("//a[@href='/artist/kevin-gates/369227']")).isDisplayed());
    }
    public static void selectFromSearch(){
        wait = new WebDriverWait(driver,50);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/artist/kevin-gates/369227']"))).click();
    }
    public static void navigateToSong(){
        wait = new WebDriverWait(driver,30);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='top-ten-track-actions-parent']")));
        Actions builder = new Actions(driver);
        Action moveOverCareers;
        moveOverCareers = builder.moveToElement(element).build();
        moveOverCareers.perform();
        driver.findElement(By.xpath("//button[@class='playable-queue top-ten-track-queue']")).click();
        Assert.assertTrue(driver.findElement(By.id("player-container")).isDisplayed());
    }
    public static void checkPlaylist(){
        driver.findElement(By.className("player-queue-button")).click();
        List<WebElement> tracksList1 = driver.findElements(By.xpath("//li[@class='queue-track ec-item'] | //li[@class='queue-track ec-item current-track']"));
        driver.findElement(By.className("player-queue-button")).click();
        driver.findElement(By.xpath("//button[@class='playable-queue-all button-queue']")).click();
        driver.findElement(By.className("player-queue-button")).click();
        List<WebElement> tracksList2 = driver.findElements(By.xpath("//li[@class='queue-track ec-item'] | //li[@class='queue-track ec-item current-track']"));
        Assert.assertNotSame(tracksList1.size(),tracksList2.size());
    }
    public static void clearPlaylist(){
        driver.findElement(By.className("player-queue-button")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("queue-top-clear-all"))).click();
        Assert.assertEquals(driver.findElement(By.id("player-container")).getAttribute("class"),"player-container player-section artwork-zoom zero-state");
    }
    public static void play() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='play-button play']"))).click();
        WebElement element1 = driver.findElement(By.xpath("//div[@class='player-scrubbable player-progress-bar']"));
//        System.out.println(element1.getAttribute("style"));
        Thread.sleep(1000);
        WebElement element12 = driver.findElement(By.xpath("//div[@class='player-scrubbable player-progress-bar']"));
        Assert.assertNotSame(element1.getAttribute("style"),element12.getAttribute("style"));
    }
    public static void quit(){
        driver.close();
    }
}
