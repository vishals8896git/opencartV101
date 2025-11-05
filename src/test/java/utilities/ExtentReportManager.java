package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener{
	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repName;
	
	public void onStart(ITestContext testContext) {
		
		/*
		SimpleDateFormat df= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt= new Date();
		String currentdatetimestamp= df.format(dt);
		*/
		
		String timeStamp= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());  //timestamp of report
		repName = "Test-Report-" + timeStamp + ".html";           //Name of report file
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);  //specify location of report
		
		sparkReporter.config().setDocumentTitle("Opencart Automation Report"); //Title of the report
		sparkReporter.config().setReportName("Opencart Functional Testing");  //Name of report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent= new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("Username", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		String OS= testContext.getCurrentXmlTest().getParameter("OS");
		extent.setSystemInfo("Operating system", OS);
		
		String browser= testContext.getCurrentXmlTest().getParameter("Browser");
		extent.setSystemInfo("Operating system", browser);
		
		List<String> includedGroups= testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) 
		{
			extent.setSystemInfo("Groups", includedGroups.toString());
		}		
	}
	
	public void onTestSuccess(ITestResult result)
	{
		test= extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());      //to display groups in reports
		test.log(Status.PASS, result.getName()+" got successfully executed");
		
	}
	
	public void onTestFailure(ITestResult result)
	{
		test= extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL, result.getName()+ " got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());
		
		try {
			String imgpath= new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgpath);
		}
		catch(IOException e1)
		{
			e1.printStackTrace();
		}
	}
	
	public void onTestSkipped(ITestResult result)
	{
		test= extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+ " got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext testContext) 
	{
		extent.flush();
		
		String pathOfExtentReport= System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport= new File(pathOfExtentReport);
		
		try 
		{
			Desktop.getDesktop().browse(extentReport.toURI());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		/*
		try
		{
			URL url= new URL("file:///"+ System.getProperty("user.dir")+"\\reports\\"+repName);
			
			//create email msg 
			ImageHtmlEmail email= new ImageHtmlEmail();
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("email address", "password"));
			email.setSSLOnConnect(true);
			email.setFrom("sender email id");  //Sender
			email.setSubject("Test Results");
			email.setMsg("Please find attached report");
			email.addTo("Receiver email id");  //Receiver
			email.attach(url, "extent report", "please check report");
			email.send(); 						//Send the email
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		*/
	}
	
	  
	
	

}
