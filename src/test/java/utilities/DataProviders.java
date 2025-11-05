package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException
	{
		String path= ".\\testData\\opencart_loginData.xlsx"; //path of EXCEL file
		
		ExcelUtility xlutil= new ExcelUtility(path); //obj of excel utility
		
		int totalrows= xlutil.getRowCount("Sheet1");
		int totalcols= xlutil.getCellCount("Sheet1", 1);
		
		String loginData[][]= new String[totalrows][totalcols];  //created 2D array
		
		for(int i=1; i<=totalrows;i++) 
		{
			for(int j=0; j<totalcols;j++) 
			{
				loginData[i-1][j]= xlutil.getCellData("Sheet1", i, j);
			}
		}
		
		return loginData;
	}
	
	//DataProvider 2
	
	//DataProvider 3

	//DataProvider 4
}
