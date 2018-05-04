package com.pj.loyalty.testcases;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.pj.loyalty.util.TestUtil;
import com.pj.loyalty.base.TestBase;
import com.pj.loyalty.pages.HomePage;

public class HomePageTestCase extends TestBase{

	HomePage homePage;
	TestUtil testUtil;
	String sheetName = "Login";
	
	public HomePageTestCase() {
		super();
	}
	
	@BeforeMethod
	public void setUp() throws MalformedURLException {
		initialization();
		testUtil=new TestUtil();
		homePage=new HomePage();
	}
	
	@Test(priority=1)
	public void verifyEnglishLocale(){
		homePage.selectEnglishLocale();
		Assert.assertEquals(homePage.getLocaleName(),"English","Locale is not set in English");
	}
	
	@Test(priority=2)
	public void verifyHomePageEnglishTitleTest(){
		homePage.selectEnglishLocale();
		String PageTitle=homePage.verifyHomePageTitle();
		Assert.assertEquals(PageTitle,"Papa John's Pizza | Order for Delivery or Carryout","Home page title not matched for English");
	}
	
	@Test(priority=3)
	public void verifySpanishLocale(){
		homePage.selectSpanishLocale();
		Assert.assertEquals(homePage.getLocaleName(),"Spanish","Locale is not set in Spanish");
	}
	
	@Test(priority=4)
	public void verifyHomePageSpanishTitleTest(){
		homePage.selectSpanishLocale();
		String PageTitle=homePage.verifyHomePageTitle();
		Assert.assertEquals(PageTitle,"Papa John's Pizza | Pide para entrega o para llevar","Home page title not matched for Spanish");
	}
	
	
	@DataProvider
	public Object[][] getSheet(){
		Object data[][] = TestUtil.getTestData(sheetName);
		return data;
	}
	
	
	@Test(priority=5, dataProvider="getSheet")
	public void verifyLoginTest(String username, String password) {
		homePage.Login(username, password);
		Assert.assertEquals(homePage.loginStatus(),true,"Login Failed");
	}

	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
}
