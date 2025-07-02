package com.browserstack;

import com.browserstack.SeleniumTest;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BStackDemoTest extends SeleniumTest {
    @Test(enabled=false) 
    public void addProductToCart() throws Exception {
        // navigate to bstackdemo
        driver.get("https://www.bstackdemo.com");

        // Check the title
        Assert.assertTrue(driver.getTitle().matches("StackDemo"));

        // Save the text of the product for later verify
        String productOnScreenText = driver.findElement(By.xpath("//*[@id=\"1\"]/p")).getText();
        // Click on add to cart button
        driver.findElement(By.xpath("//*[@id=\"1\"]/div[4]")).click();

        // See if the cart is opened or not
        Assert.assertTrue(driver.findElement(By.cssSelector(".float\\-cart__content")).isDisplayed());

        // Check the product inside the cart is same as of the main page
        String productOnCartText = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[2]/div[2]/div/div[3]/p[1]")).getText();
        Assert.assertEquals(productOnScreenText, productOnCartText);
    }
    
    @Test
    public void testAddFavoriteGalaxyS20() throws InterruptedException {
    	driver.get("https://www.bstackdemo.com");
    	driver.findElement(By.id("signin")).click();
    	driver.findElement(By.id("username")).click();
    	driver.findElement(By.id("react-select-2-input")).sendKeys(
    			System.getenv("BSTACK_DEMO_USERNAME"));
    	
    	driver.findElement(By.id("react-select-2-input")).sendKeys(
    			Keys.RETURN);
    	
    	driver.findElement(By.id("password")).click();
    	driver.findElement(By.id("react-select-3-input")).sendKeys(
    			System.getenv("BSTACK_DEMO_PASSWORD"));
    	
    	driver.findElement(By.id("react-select-3-input")).sendKeys(
    			Keys.RETURN);
    	
    	driver.findElement(By.id("login-btn")).click();
    	
    	By favButtonBy = By.cssSelector(
    			"div[data-sku='samsung-S20-device-info.png'] > "
    			+ "div.shelf-stopper > button");
    	
    	By s20DeviceInfoBy = By.cssSelector(
    			"div[data-sku='samsung-S20-device-info.png']");
    	
    	WebElement s20DeviceInfo = driver.findElement(s20DeviceInfoBy);
    	WebElement favButton = driver.findElement(favButtonBy);
    	Actions actions = new Actions(driver);
    	
    	try{
        	actions.moveToElement(favButton);
        	actions.perform();
        	favButton.click();
    	}catch(MoveTargetOutOfBoundsException ex) { 
    		// Firefox doesn't scroll on moveToElement: 
    		// https://stackoverflow.com/a/52045231
    		// Using JavaScript to help with scroll and click
    		((JavascriptExecutor) driver).executeScript(
    				"arguments[0].scrollIntoView(true);", s20DeviceInfo);
    		
    		actions.moveToElement(favButton);
        	actions.perform();
        	((JavascriptExecutor) driver).executeScript(
        			"arguments[0].click();", favButton);
    	}
    	
    	driver.findElement(By.id("favourites")).click();   	
    	
    	s20DeviceInfo = driver.findElement(s20DeviceInfoBy);
    	((JavascriptExecutor) driver).executeScript(
    			"arguments[0].scrollIntoView();", s20DeviceInfo);
    	
    	// See if the shelf item is displayed or not
        Assert.assertTrue(s20DeviceInfo.isDisplayed());

        // Check that the displayed shelf item is Galaxy S20
        String itemText = driver.findElement(
        		By.cssSelector(
        				"div[data-sku='samsung-S20-device-info.png'] > p"))
        		.getText();
        
        Assert.assertEquals(itemText, "Galaxy S20");
    	
    }
}
