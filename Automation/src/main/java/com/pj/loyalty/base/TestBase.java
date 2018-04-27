package com.pj.loyalty.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.pj.loyalty.util.WebEventListener;


public class TestBase {
	
	public static WebDriver driver;
	public static Properties prop;
	public  static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	
	public TestBase(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+
					"/src/main/java/com/pj/loyalty/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void initialization(){
		String browserName = prop.getProperty("browser");
		
		
		if(System.getProperty("os.name").toLowerCase().contains("mac")) {
			if(browserName.equals("chrome")){
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
						+"/src/main/java/com/pj/loyalty/util/chromedriver_MAC64");	
				driver = new ChromeDriver(); 
			}
			else if(browserName.equals("FF")){
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")
						+"/src/main/java/com/pj/loyalty/util/geckodriver_MAC64"); 
				driver= new FirefoxDriver();
			}	
		}else if (System.getProperty("os.name").toLowerCase().contains("win")){
			if(browserName.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
						+"/src/main/java/com/pj/loyalty/util/chromedriver_WIN32.exe");	
				driver = new ChromeDriver(); 
			}
			if(browserName.equals("FF")) {
				if(System.getProperty("os.arch").contains("32")) {
					System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")
							+"/src/main/java/com/pj/loyalty/util/geckodriver_WIN32.exe"); 
					driver= new FirefoxDriver();
				}else {
					System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")
							+"/src/main/java/com/pj/loyalty/util/geckodriver_WIN64.exe"); 
					driver= new FirefoxDriver();
				}
					
			}
		}
		
		
		
		
		e_driver = new EventFiringWebDriver(driver);
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		
		//driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Long.parseLong(prop.getProperty("page_loadtime")), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Long.parseLong(prop.getProperty("implicit_timeout")), TimeUnit.SECONDS);

		driver.get(prop.getProperty("url"));
		
	}
	
}
