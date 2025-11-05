package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.loginPage;
import pageObjects.myAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;


public class TC003_LoginDDT extends BaseClass {
	
	@Test(dataProvider= "LoginData", dataProviderClass= DataProviders.class, groups="DataDriven")     //additional parameter as dataprovider is in diff package
    public void verify_LoginDDT(String email, String pwd, String exp) {
    
		logger.info("*** Starting TC003_LoginDDT ***");
		
		
		try {
		HomePage hp1= new HomePage(driver);  //homepage
		
		hp1.clickMyAccount();
		hp1.clickLogin();
		
		loginPage lp= new loginPage(driver);   //loginpage
		
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLoginBtn();
		
		myAccountPage myacc= new myAccountPage(driver);		//myaccountpage
		boolean targetPage= myacc.isMyAccountPageExists();
		
		/*
		Data is Valid > Login Successful > Test Passed  -- Logout
		Data is Valid > Login Unsuccessful > Test Failed

		Data is Invalid > Login Successful > Test Failed -- Logout
		Data is Invalid > Login Unsuccessful > Test Passed
		*/
		
		
		if(exp.equalsIgnoreCase("Valid"))
		{
		  if(targetPage==true) 
		  {
			  myacc.clickLogout();
			  Assert.assertTrue(true);
		  }	
		  else
		  {
			  Assert.assertTrue(false);
		  }
		}
		
		if(exp.equalsIgnoreCase("Invalid"))
		{
			if(targetPage==true)
			{
				myacc.clickLogout();
				Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
		
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("*** Finished TC003_LoginDDT ***");
    }
	
}
