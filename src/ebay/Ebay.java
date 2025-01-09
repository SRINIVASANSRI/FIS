package ebay;

import static org.testng.Assert.fail;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Ebay {
	

	  private WebDriver driver;
	  private String baseUrl;
	  private StringBuffer verificationErrors = new StringBuffer();
	  WebDriverWait wait;
	  
	  @BeforeClass(alwaysRun = true)
	  public void setUp() throws Exception {
		  System.setProperty("webdriver.chrome.driver", ".\\driver\\chrome\\chromedriver.exe");
		  driver=new ChromeDriver();
		  baseUrl = "http://www.ebay.com";
		  driver.manage().window().maximize();
		 // wait=new WebDriverWait(driver, 500);
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testEbay() throws Exception {
	    driver.get(baseUrl);
	
	    String parentWindowEbay=driver.getWindowHandle();
        System.out.println("Parent Window"+parentWindowEbay);
        
	    driver.findElement(By.xpath("//input[@placeholder='Search for anything']")).sendKeys("books");
        boolean Search= driver.findElement(By.xpath("//input[@type='submit']")).isDisplayed();
        System.out.println("Search"+Search);
        if(Search){
	    driver.findElement(By.xpath("//input[@type='submit']")).click();
        }
        String srchBooksCon1=null;
        String srchBookChildTitle=null;
        
        WebElement srchBooks=driver.findElement(By.xpath("//ul[@class='srp-results srp-list clearfix']/child::li[1]"));
        boolean srchBooksDsp=srchBooks.isDisplayed();
        System.out.println("srchBooks"+srchBooks);
                
        if(srchBooksDsp){
        	srchBooksCon1=srchBooks.getText();
            
            System.out.println("srchBooksCon: "+srchBooksCon1);
        	//Actions act=new Actions(driver);
        	//act.moveToElement(srchBooks).build().perform();
         
   
            srchBooks.click();
        }
    Thread.sleep(5000);
        
        Set<String> allWindowHandles=driver.getWindowHandles();
        System.out.println("Window size"+allWindowHandles.size());
        
        for(String childwindow: allWindowHandles){
        	if(!childwindow.equals(parentWindowEbay)){
            driver.switchTo().window(childwindow);
            Thread.sleep(5000);    
            System.out.println("childwindow"+childwindow);
            srchBookChildTitle=driver.getTitle();
            System.out.println("Child window title"+srchBookChildTitle);
            Thread.sleep(5000);
            
        	}
        	else{

            
            System.out.println("Parent window title" +driver.getTitle());
        	}
        }

        Thread.sleep(5000);
        WebElement addCart=driver.findElement(By.xpath("//span[text()='Add to cart']"));
        boolean verAddCart=addCart.isDisplayed();
        if(verAddCart)
        {
        	Actions action=new Actions(driver);
        	action.moveToElement(addCart).build().perform();
        	addCart.click();
        }
        Thread.sleep(5000);
        
        WebElement cart=driver.findElement(By.id("gh-cart-n"));
      boolean cartVisible=cart.isDisplayed();
                if(cartVisible){
        String cartAdded=cart.getText();

        	System.out.println("Cart Value:" +cartAdded);
        	if(cartAdded!= "Your shopping cart is empty")
        		System.out.println("Verified the Test Cases is Passed" +cartAdded);
        	else
        		System.out.println("Test Cases is failed with the Cart Value" +cartAdded);
        	
        }
      }
        

	  @AfterClass(alwaysRun = true)
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

}
