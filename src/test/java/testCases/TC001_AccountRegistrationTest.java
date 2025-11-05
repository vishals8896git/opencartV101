package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;


public class TC001_AccountRegistrationTest extends BaseClass {
		
		
	@Test(groups={"Regression","Master"})
	public void verify_account_registration() {
		
		logger.info("**** Starting TC001_AccountRegistrationTest ****");
		
		try {
		HomePage hp= new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Clicked on MyAccount Link");
		
		hp.clickRegister();
		logger.info("Clicked on Register Link"); 
		
		AccountRegistrationPage ap= new AccountRegistrationPage(driver);
		
		logger.info("Providing customer details");
		ap.setFirstName(randomString().toUpperCase());
		ap.setLastName(randomString().toUpperCase());
		ap.setEmail(randomString()+"@gmail.com");
		ap.setTelephone(randomNumber());
		
		String password= randomAlphanumeric();
		
		ap.setPassword(password);
		ap.setConfirmPassword(password);
		
		ap.setPolicy();
		ap.clickContinue();
		
		logger.info("Validating expected message");		
		String confirmMessage= ap.getConfirmationMsg();		
		
		if(confirmMessage.equals("Your Account Has Been Created!")) {
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test failed..");
			logger.debug("Debug logs..");
			Assert.assertTrue(false);
		}
		
		//Assert.assertEquals(confirmMessage, "Your Account Has Been Created!!!");
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
				
	}
	
	
	
}

