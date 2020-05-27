package FlightBooking;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class AddTripTest {

	WebDriver driver;
	String BaseUrl;
	ExtentReports report;
	ExtentTest test;
	HomePageObjectsModel hp;

	@BeforeTest
	public void setUp() throws Exception {

		report = new ExtentReports("C:\\Users\\Saltas\\Documents\\Selenium Course\\Java Scripts\\Expedia\\FlightsBookingReport.html",true);
		test = report.startTest("Search for flight");
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		
		hp = new HomePageObjectsModel(driver,test);
		
		BaseUrl = "https://www.goibibo.com/";
		driver.get(BaseUrl);	
		test.log(LogStatus.INFO, "Navigation to website");
	
	}

	@org.testng.annotations.Test
	public void BookTrip() throws Exception {
		hp.clickOnRoundTrip();
		hp.selectDepartureAirport("ATH");
		hp.selectDestinationAirport("LARNACA");
		hp.pickDepartureDate("01/09/2020");
		hp.pickReturnDate("07/09/2020");
		hp.addPassengersAndClass(2, 0, 0, "economy");
		hp.SearchForFlights();
		
	}

	@AfterTest
	public void tearDown() throws Exception {
		Thread.sleep(3000);
		driver.quit();
		report.endTest(test);
		report.flush();
	}

}
