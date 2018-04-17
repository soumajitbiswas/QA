package com.pj.loyalty.base;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.pj.loyalty.util.TestUtil;
import com.pj.loyalty.util.WebEventListener;

public class TestBase {
	
	public static WebDriver driver;
	public static Properties prop;
	public  static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	
	public TestBase(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/com/pj/loyalty/config"
					+ "config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void initialization(){
		String browserName = prop.getProperty("browser");
		
		if(TestUtil.getOS().equals("win") && TestUtil.getBit().equals("32"))
		{
			if(browserName.equals("chrome")){
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
						+"/src/main/java/com/pj/loyalty/config/chromedriver_Win32");	
				driver = new ChromeDriver(); 
			}
			else if(browserName.equals("FF")){
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")
						+"/src/main/java/com/pj/loyalty/config/geckodriver_Win32");	
				driver = new FirefoxDriver(); 
			}
			
		}else if(TestUtil.getOS().equals("mac"))
		{
			if(browserName.equals("chrome")){
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
						+"/src/main/java/com/pj/loyalty/config/chromedriver_Mac64");	
				driver = new ChromeDriver(); 
			}
			else if(browserName.equals("FF")){
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")
						+"/src/main/java/com/pj/loyalty/config/geckodriver_Mac64");	
				driver = new FirefoxDriver(); 	
			}
		}
			
			
		
		
		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Long.valueOf(prop.getProperty("Page_Load_Time")), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Long.valueOf(prop.getProperty("Implicit_Wait")), TimeUnit.SECONDS);
		
		driver.get(prop.getProperty("url"));
		
	}
	
	
	
	
	
	
	
	

}
