package com.pj.loyalty.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pj.loyalty.base.TestBase;

public class HomePage extends TestBase{

	@FindBy(xpath="//a[@class='popup-trigger' and contains(text(),'Log In')]")
	WebElement LoginLink;
	
	@FindBy(xpath="//a[@class='signin-button' and contains(text(),'Sign Up')]")
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
		WebElement LoginSbButton=driver.findElement(By.xpath("//input[@type='submit' and @value='Log In']"));
		LoginSbButton.click();
	}
	
	public String verifyHomePageTitle(){
		return driver.getTitle();
	}
	
	public SignUp clickOnSignUp(){
		SignUpLink.click();
		return new SignUp();
	}
	
}
