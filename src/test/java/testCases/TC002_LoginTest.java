package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.loginPage;
import pageObjects.myAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{
	
	@Test(groups={"Sanity","Master"})
	public void verify_Login_Test() {
		
		logger.info("*** Starting TC002_LoginTest ***");
		
		try {
		HomePage hp1= new HomePage(driver);  //homepage
		
		hp1.clickMyAccount();
		hp1.clickLogin();
		
		loginPage lp= new loginPage(driver);   //loginpage
		
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLoginBtn();
		
		myAccountPage myacc= new myAccountPage(driver);		//myaccountpage
		boolean targetPage= myacc.isMyAccountPageExists();
		
		Assert.assertTrue(targetPage);
		}
		catch(Exception e) {
			Assert.fail();
		}
		logger.info(" *** Finished TC002_LoginTest *** ");
	
	}
	
	

}
