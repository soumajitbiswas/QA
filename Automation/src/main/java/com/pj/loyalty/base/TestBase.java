package com.pj.loyalty.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.pj.loyalty.util.WebEventListener;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

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
	
	
	public static void initialization() throws MalformedURLException{
		
		if(prop.getProperty("device").equalsIgnoreCase("PC")) {
			initializePC();	
		}else if(prop.getProperty("device").equalsIgnoreCase("mobile")) {
			/*
			AppiumServiceBuilder builder = new AppiumServiceBuilder();
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability("noReset", "false");
			cap.setCapability("platformName", prop.getProperty("phone_device_brand"));
			cap.setCapability("platformVersion", prop.getProperty("phone_os_version"));
			cap.setCapability("deviceName","MyDevice");
			cap.setCapability("browserName", prop.getProperty("browser"));
			cap.setCapability("avd", "MyDevice");
			builder.withIPAddress("127.0.0.1");
			builder.usingPort(4723);
			builder.withCapabilities(cap);
			AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
			service.start();
			*/
			initializeMobile();
		}
		
		e_driver = new EventFiringWebDriver(driver);
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		
		//driver.manage().window().maximize();
		//driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Long.parseLong(prop.getProperty("page_loadtime")), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Long.parseLong(prop.getProperty("implicit_timeout")), TimeUnit.SECONDS);

		driver.get(prop.getProperty("url"));
		
	}
	
	public static void initializeMobile() throws MalformedURLException {
		if(prop.getProperty("phone_device_brand").equalsIgnoreCase("android")) {
			initializeAndroid();
		}else if(prop.getProperty("phone_device_brand").equalsIgnoreCase("iphone")) {
			initializeIPhone();
		}
	}
	
	public static void initializeAndroid() throws MalformedURLException {
		DesiredCapabilities capabilities= DesiredCapabilities.android();
		capabilities.setCapability("browserName", "chrome");
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("fullReset", false);
		capabilities.setCapability("deviceName", "MyDevice");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "7.1.1");
		capabilities.setCapability("app", System.getProperty("user.dir")+"/src/main/java/com/pj/loyalty/util/com.android.chrome_66.0.3359.126-335912612_minAPI24(x86)(nodpi)_apkmirror.com.apk");
		
		//avdArgs capability to -no-window
		//capabilities.setCapability("appPackage", "com.android.chrome");
		//capabilities.setCapability("appActivity", "com.google.android.apps.chrome.Main");
		//capabilities.setCapability("appWaitActivity", "*.SplashActivity");
		capabilities.setCapability("chromedriverExecutable", System.getProperty("user.dir")+"/src/main/java/com/pj/loyalty/util/chromedriver_MAC64" );
		
		capabilities.setCapability("avd", "AVD_for_Nexus_One_by_Google");
		URL url=new URL(prop.getProperty("appium_server_address"));
		
		driver = new AndroidDriver(url,capabilities);
		
	}
	
	public static void initializeIPhone() throws MalformedURLException {
		DesiredCapabilities capabilities= DesiredCapabilities.iphone();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "safari");
		capabilities.setCapability("deviceName", "iPhone 8");
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("platformVersion", "11.2");
		capabilities.setCapability("chromedriverExecutable", System.getProperty("user.dir")+"/src/main/java/com/pj/loyalty/util/chromedriver_MAC64" );
		capabilities.setCapability("appPackage", "com.android.chrome");
		capabilities.setCapability("appActivity", "com.google.android.apps.chrome");
		//capabilities.setCapability("autoWebview", true);
		//capabilities.setCapability("autoWebviewTimeout", "3000");
		URL url=new URL(prop.getProperty("appium_server_address"));
		driver = new IOSDriver(url,capabilities);
	}
	
	public static void initializePC() {
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
		
	}
	
}
