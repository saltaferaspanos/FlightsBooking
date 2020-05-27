package FlightBooking;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class HomePageObjectsModel {
	
	ExtentTest test;
	WebDriver driver = null;
	
	public HomePageObjectsModel(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test= test;
	}

	public void clickOnRoundTrip() {
		
		driver.findElement(By.id("roundTrip")).click();
		test.log(LogStatus.INFO, "RoundTrip button is clicked");
	}
	
	public void selectDepartureAirport(String departure) {
		
		WebElement fromField = driver.findElement(By.xpath("//input[@id='gosuggest_inputSrc']"));
		fromField.sendKeys(departure);

		WebElement ulElement = driver.findElement(By.xpath("//ul[@id='react-autosuggest-1']"));

		String innerHtml = ulElement.getAttribute("innerHTML");
//		System.out.println(innerHtml);

		List<WebElement> liElements = ulElement.findElements(By.tagName("li"));
		Boolean flag = false;
		String airport = null;
		
		for (WebElement element : liElements) { //
//			System.out.println("Get visible Text: " + element.getText());
			if (element.getText().toLowerCase().contains(departure.toLowerCase())) {
				System.out.println("Get visible Text for Departure Airport: " + element.getText());
				airport=element.getText();
				element.click();
				flag=true;
				break;
			}
		}
		
		if (flag)
			test.log(LogStatus.INFO, airport +" is selected as Airport of Departure");
	}
	
		
	public void selectDestinationAirport(String destination) {
		
		WebElement destField = driver.findElement(By.xpath("//input[@id='gosuggest_inputDest']")); 
//		System.out.println("Is destination displayed? "+destField.isDisplayed());
		destField.sendKeys(destination);

		WebElement ulElement = driver.findElement(By.xpath("//ul[@id='react-autosuggest-1']"));
		String innerHtml = ulElement.getAttribute("innerHTML");
//		System.out.println(innerHtml);

		List<WebElement> liElements = ulElement.findElements(By.tagName("li"));
		Boolean flag=false;
		String airport=null;
		
		for (WebElement element : liElements) {
//			System.out.println("Get visible Text: "+element.getText());
			if (element.getText().toLowerCase().contains(destination.toLowerCase())) {
				System.out.println("Get visible Text for Arrival Airport: " + element.getText());
				airport=element.getText();
				element.click();
				flag=true;
				break;
			}
		}
		
		
		if (flag)
			test.log(LogStatus.INFO, airport +" is selected as Airport of Destination");
	}
		
	
	public void pickDepartureDate(String depDate) throws ParseException {
		Date departureDate= new SimpleDateFormat( "dd/MM/yyyy" ).parse( depDate );

		//header format
		DateFormat monthFormat = new SimpleDateFormat("MMMM yyyy");
		//date format
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy");
		
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
		test.log(LogStatus.INFO, "Departure date is selected: "+depDate);
		
		
	}
	
	
	public void pickReturnDate(String retDate) throws ParseException {

		Date returnDate = new SimpleDateFormat( "dd/MM/yyyy" ).parse( retDate );
		
		//header format
		DateFormat monthFormat = new SimpleDateFormat("MMMM yyyy");
		//date format
		DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy");					
						
		//select return date
		driver.findElement(By.id("returnCalendar")).click();
		WebElement MonthSelection = driver.findElement(By.xpath("//div[@class='DayPicker-Caption']"));
		WebElement nextMonth = null;
		
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

		String xpath = "//div[@aria-label='" + dateFormat.format(returnDate) + "']";
		System.out.println(xpath);
		WebElement pickReturnDate = driver.findElement(By.xpath(xpath));
		System.out.println("is date displayed?: " + pickReturnDate.isDisplayed());
		pickReturnDate.click();
		
		test.log(LogStatus.INFO, "Return date is selected: "+retDate);
		
	}
		
	
	public void addPassengersAndClass(int adults, int children, int infants, String travelClass) {
		
		driver.findElement(By.id("pax_link_common")).click();
		
		// by default is 1
		adults=adults-1;
		while (adults>0) {
			driver.findElement(By.id("adultPaxPlus")).click();
			adults--;
		}
		
		while (children>0) {
			driver.findElement(By.id("childPaxPlus")).click();
			children--;
		}
		
		while (infants>0) {
			driver.findElement(By.id("infantPaxPlus")).click();
			infants--;
		}
		
		
		WebElement dropdown = driver.findElement(By.id("gi_class"));
		Select sel = new Select(dropdown);
		List<WebElement> options = sel.getOptions();
		int size = options.size();
		String optionName=null;
		
		for (int i=0; i<size; i++) {
			optionName = options.get(i).getText();
			if (optionName.toLowerCase().contains(travelClass.toLowerCase())) {
				sel.selectByIndex(i);
				break;
			}
		}
	
		test.log(LogStatus.INFO, "Added "+ adults+" adults, "+children+" children, "+infants+" infants and travel class: "+optionName);
	}
	
	public void SearchForFlights() {
		driver.findElement(By.id("gi_search_btn")).click();
		test.log(LogStatus.INFO, "Search for flights button is clicked");
	}
	
		
		
		
	} 
	
