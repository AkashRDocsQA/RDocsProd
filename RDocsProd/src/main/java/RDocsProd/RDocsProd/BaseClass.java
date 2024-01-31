package RDocsProd.RDocsProd;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseClass {
	WebDriver driver;
	@BeforeMethod
	public void setup() throws Exception {

		WebDriverManager.chromedriver().setup();

		driver = new ChromeDriver();

		ExtentReports extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("ExtentReports.html");
		extent.attachReporter(spark);

		ExtentTest test = extent.createTest("MyFirstTest").log(Status.PASS,
				"This is a logging event for MyFirstTest, and it passed!");

		driver.get("https://app.rdocs.io/");
		test.log(Status.INFO, "Started the browser");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.findElement(By.name("email")).sendKeys("akash.ta@rpostlabs.com");
		test.pass("Entered Username");
		System.out.println("Username");
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("Akash@278");
		test.pass("Entered Password");
		System.out.println("Password");
		driver.findElement(By.xpath("//button[text()='Sign in']")).click();
		test.pass("Logged in successfully");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@id='multipleEmails']")).sendKeys("akashtangavallu@yahoo.com");
		test.pass("Entered Recipient details");
		System.out.println("Recipient Email entered");
		driver.findElement(By.name("subject")).sendKeys("Send_L1_RPD");
		driver.findElement(By.name("message")).sendKeys("Generated RPD through Jenkins");
		
		driver.findElement(By.xpath("//div[@class='row row-height']")).click();
		Robot r = new Robot();
		r.delay(2000);
		StringSelection s = new StringSelection(
				"C:\\Testing\\Docs with Different pages\\template.jpg");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_V);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(4000);
		test.pass("Attached the document");
		driver.findElement(By.xpath("//button[text()='Send']")).click();
		test.pass("Successfully sent an RPD");
		Thread.sleep(5000);
		// driver.close();
		extent.flush();
	}
}
