package FlightBooking;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class AddTripTest {

	WebDriver driver;
	String BaseUrl;

	@BeforeTest
	public void setUp() throws Exception {

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		BaseUrl = "https://www.goibibo.com/";
	}

	@org.testng.annotations.Test
	public void BookTrip() throws Exception {

		Dimension size = driver.manage().window().getSize();
		System.out.println("Size of window is: " + size);

		String departure = "ATH";
		String destination = "ITA";

		driver.get(BaseUrl);
		driver.findElement(By.id("roundTrip")).click();

		WebElement fromField = driver.findElement(By.xpath("//input[@id='gosuggest_inputSrc']"));
		fromField.sendKeys(departure);

		WebElement ulElement = driver.findElement(By.xpath("//ul[@id='react-autosuggest-1']"));

		String innerHtml = ulElement.getAttribute("innerHTML");
		System.out.println(innerHtml);

		List<WebElement> liElements = ulElement.findElements(By.tagName("li"));

		for (WebElement element : liElements) { //
			System.out.println("Get visible Text: " + element.getText());
			if (element.getText().contains(departure)) {
				System.out.println("Get visible Text for Departure Airport: " + element.getText());
				element.click();
				break;
			}
		}

		WebElement destField;
		destField = driver.findElement(By.xpath("//input[@id='gosuggest_inputDest']")); //
//		  System.out.println("Is destination displayed? "+destField.isDisplayed());
		destField.sendKeys(destination);

		ulElement = driver.findElement(By.xpath("//ul[@id='react-autosuggest-1']"));
		innerHtml = ulElement.getAttribute("innerHTML");
		System.out.println(innerHtml);

		liElements.clear();
		liElements = ulElement.findElements(By.tagName("li"));

		for (WebElement element : liElements) {
			// System.out.println("Get visible Text: "+element.getText());
			if (element.getText().contains("Milan")) {
				System.out.println("Get visible Text for Arrival Airport: " + element.getText());
				element.click();
				break;
			}
		}

		Calendar today = new GregorianCalendar();
		AddDaysClass getDate = new AddDaysClass();

		Date departureDate = getDate.AddDays(today, 150);
		today = new GregorianCalendar();
		Date returnDate = getDate.AddDays(today, 155);

		DateFormat monthFormat = new SimpleDateFormat("MMMM yyyy");
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy");
		System.out.println("format for date selection: " + dateFormat.format(departureDate));
		System.out.println("format for month selection: " + monthFormat.format(departureDate));
		
		
		//select departure date
		driver.findElement(By.id("departureCalendar")).click();
		WebElement MonthSelection = driver.findElement(By.xpath("//div[@class='DayPicker-Caption']"));
		WebElement nextMonth = null;
		System.out.println("Text in html: " + MonthSelection.getText());
		System.out.println("Condition: " + MonthSelection.getText().contains(monthFormat.format(departureDate)));
		while (!(MonthSelection.getText().contains(monthFormat.format(departureDate)))) {
			nextMonth = driver.findElement(
					By.xpath("//span[contains(@role,'button') and contains(@class,'DayPicker-NavButton--next')]"));
			System.out.println("is next month button displayed: " + nextMonth.isDisplayed());
			nextMonth.click();
			MonthSelection = driver.findElement(By.xpath("//div[@class='DayPicker-Caption']"));
			System.out.println("Text in html: " + MonthSelection.getText());
		}

		String xpath = "//div[@aria-label='" + dateFormat.format(departureDate) + "']";
		System.out.println(xpath);
		WebElement pickDepartureDate = driver.findElement(By.xpath(xpath));
		System.out.println("is date displayed?: " + pickDepartureDate.isDisplayed());
		pickDepartureDate.click();
		
		
		//select return date
		driver.findElement(By.id("returnCalendar")).click();
		MonthSelection=null;
		MonthSelection = driver.findElement(By.xpath("//div[@class='DayPicker-Caption']"));
		nextMonth = null;
		System.out.println("Text in html: " + MonthSelection.getText());
		System.out.println("Condition: " + MonthSelection.getText().contains(monthFormat.format(returnDate)));
		while (!(MonthSelection.getText().contains(monthFormat.format(returnDate)))) {
			nextMonth = driver.findElement(
					By.xpath("//span[contains(@role,'button') and contains(@class,'DayPicker-NavButton--next')]"));
			System.out.println("is next month button displayed: " + nextMonth.isDisplayed());
			nextMonth.click();
			MonthSelection = driver.findElement(By.xpath("//div[@class='DayPicker-Caption']"));
			System.out.println("Text in html: " + MonthSelection.getText());
		}

		xpath = "//div[@aria-label='" + dateFormat.format(returnDate) + "']";
		System.out.println(xpath);
		WebElement pickReturnDate = driver.findElement(By.xpath(xpath));
		System.out.println("is date displayed?: " + pickReturnDate.isDisplayed());
		pickReturnDate.click();
		
		//click search
		driver.findElement(By.id("gi_search_btn")).click();
		

	}

	@AfterTest
	public void tearDown() throws Exception {
		Thread.sleep(3000);
		driver.quit();
	}

}
