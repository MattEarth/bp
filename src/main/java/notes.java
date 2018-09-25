import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class notes {
    private static WebDriver driver;
    private static WebDriverWait wait;
    public void browserInit(){
        System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }
    public void enterBeatport(){
        driver.get("https://www.Beatport.com/");
    }
    public void closeWelcome(){
        wait = new WebDriverWait(driver,20);
        WebElement element = driver.findElement(By.xpath("//button[@class='bx-button'][@type='reset']"));
        Assert.assertTrue(element.isDisplayed());
        element.click();
    }
    public void search(String what){
        wait = new WebDriverWait(driver,50);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='search'][@class='head-search-input tt-input']")));
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(what+Keys.ENTER);
    }
    public void checkSearchFail(){
        wait = new WebDriverWait(driver,50);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='search-zero-message']"))));
        WebElement element = driver.findElement(By.xpath("//div[@class='search-zero-message']"));
        String message = "YOUR SEARCH GURU NET DID NOT RETURN ANY RESULTS\n" +
                "Make sure all names and titles are spelled correctly or try using different keywords in your search. If that doesn't work, try browsing the available genres.";
        Assert.assertEquals(element.getText(),message);
    }
    public void backHome(){
        wait = new WebDriverWait(driver,50);
        wait.until(ExpectedConditions.elementToBeClickable(By.className("beatport-logo"))).click();
        Assert.assertTrue(driver.findElement(By.className("bp-top-ten-logo")).isDisplayed());
    }
    public void checkSearchOk(){
        wait = new WebDriverWait(driver,50);
        wait.until(ExpectedConditions.elementToBeClickable(By.className("artist-name")));
        Assert.assertTrue(driver.findElement(By.xpath("//a[@href='/artist/kevin-gates/369227']")).isDisplayed());
    }
    public void selectFromSearch(){
        wait = new WebDriverWait(driver,50);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/artist/kevin-gates/369227']"))).click();
    }
    public void navigateToSong(){
        wait = new WebDriverWait(driver,30);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='top-ten-track-actions-parent']")));
        Actions builder = new Actions(driver);
        Action moveOverCareers;
        moveOverCareers = (Action) builder.moveToElement(element).build();
        moveOverCareers.perform();
        driver.findElement(By.xpath("//button[@class='playable-queue top-ten-track-queue']")).click();
        Assert.assertTrue(driver.findElement(By.id("player-container")).isDisplayed());
    }
    public void checkPlaylist(){
        driver.findElement(By.className("player-queue-button")).click();
        List<WebElement> tracksList1 = driver.findElements(By.xpath("//li[@class='queue-track ec-item'] | //li[@class='queue-track ec-item current-track']"));
        driver.findElement(By.className("player-queue-button")).click();
        driver.findElement(By.xpath("//button[@class='playable-queue-all button-queue']")).click();
        driver.findElement(By.className("player-queue-button")).click();
        List<WebElement> tracksList2 = driver.findElements(By.xpath("//li[@class='queue-track ec-item'] | //li[@class='queue-track ec-item current-track']"));
        Assert.assertNotSame(tracksList1.size(),tracksList2.size());
    }
    public void clearPlaylist(){
        driver.findElement(By.className("player-queue-button")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("queue-top-clear-all"))).click();
        Assert.assertEquals(driver.findElement(By.id("player-container")).getAttribute("class"),"player-container player-section artwork-zoom zero-state");
    }
    public void quit(){
        driver.close();
    }
}
