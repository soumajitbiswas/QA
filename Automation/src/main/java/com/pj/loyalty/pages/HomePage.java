package com.pj.loyalty.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pj.loyalty.base.TestBase;

public class HomePage extends TestBase{

	@FindBy(xpath="//a[@class='popup-trigger' and @aria-controls='popup-login']")
	WebElement LoginLink;
	
	@FindBy(xpath="//a[@class='signin-button']")
	WebElement SignUpLink;
	
	@FindBy(xpath="//a[@class='popup-trigger' and @aria-controls='popup-lang']")
	WebElement LocaleLink;
	
	public HomePage() {
		PageFactory.initElements(driver, this);
	}
	
	public void Login(String un, String pwd) {
		Actions builder= new Actions(driver);
		builder.click(LoginLink).build().perform();
		WebElement emailID_Field=driver.findElement(By.xpath("//input[@type='email']"));
		emailID_Field.sendKeys(un);
		WebElement password_Field=driver.findElement(By.xpath("//input[@type='password']"));
		password_Field.sendKeys(pwd);
		WebElement LoginSbButton=driver.findElement(By.xpath("//input[@type='submit' and @class='button button--green']"));
		LoginSbButton.click();
	}
	public boolean loginStatus()
	{
		return driver.findElement(By.xpath("//a[@class='popup-trigger' and @data-track-click='top-nav|Hi,']")).isDisplayed();
	}
	
	public String verifyHomePageTitle(){
		return driver.getTitle();
	}
	
	public SignUp clickOnSignUp(){
		SignUpLink.click();
		return new SignUp();
	}
	
	public void selectSpanishLocale() {
		Actions builder1= new Actions(driver);
		builder1.click(LocaleLink).build().perform();
		WebElement spanishLink=driver.findElement(By.xpath("///div/ul/li/a[text()='Español']"));
		spanishLink.click();
		//h3[text()='Choose Your Language']
	}
	public void selectEnglishLocale() {
		Actions builder2= new Actions(driver);
		builder2.click(LocaleLink).build().perform();
		WebElement englishLink=driver.findElement(By.xpath("///div/ul/li/a[text()='English']"));
		englishLink.click();
		//h3[text()='Choose Your Language']
	}
	
	public String getLocaleName() {
		String localeText = null;
		Actions builder3= new Actions(driver);
		builder3.click(LocaleLink).build().perform();
		WebElement localeTextElement=driver.findElement(By.xpath("//div[@id='popup-lang']/h3[@class='h4 title']"));
		if(localeTextElement.getText().equalsIgnoreCase("Choose Your Language"))
			localeText="English";
		else if(localeTextElement.getText().equalsIgnoreCase("Elige tu idioma"))
			localeText="Spanish";
		return localeText;
	}
	
}
