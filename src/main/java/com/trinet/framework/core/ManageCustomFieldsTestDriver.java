package com.trinet.framework.core;

import java.awt.AWTException;
import java.awt.RenderingHints.Key;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trinet.framework.core.TestDriver;
import com.trinet.framework.utils.ReportUtil;
import com.trinet.framework.utils.TestUtil;
import com.trinet.framework.utils.XlHelper;


public class ManageCustomFieldsTestDriver{
	
	private static Logger logger = LoggerFactory.getLogger(ManageCustomFieldsTestDriver.class);
	public String projectID;
	public String testcycleId;
	
	@BeforeSuite
	public void initConfig()
	{
		TestUtil.checkMandatoryConfigProperties();
		TestUtil.createTestCycle();
		projectID=TestUtil.projectID;
		testcycleId=TestUtil.testcycleId;
		logger.info("The projectID is :"+projectID);
		logger.info("The testcycleId id is :"+testcycleId);
	}

		
	@Test(dataProvider = "getDataFromSuite",dataProviderClass = GetData.class)
	public  void suiteRun( XlHelper  data)throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException, AWTException, InterruptedException{
	
		ManageCustomFieldsKeywords kw = new ManageCustomFieldsKeywords();
		
		//kw.uploadFile(null,"CustomFieldsTemplate.xlsx");
		
		ReportUtil reportUtil=new ReportUtil();

		reportUtil.startTesting(GetData.ResultsPath,TestUtil.now("yyyy-MM-dd HH:mm:ss"));

		String x=data.path.replace("/", ".");
		String currentTestSuite=x.split("\\.",3)[1];
		TestLogHelper	testLogHelper = new TestLogHelper();		 
		testLogHelper.setName(currentTestSuite);
		testLogHelper.run();

		reportUtil.startSuite(currentTestSuite,"Y");
		
		TestDriver td = new TestDriver(kw,reportUtil);
		td.startZephyrTestCreation(data,currentTestSuite,projectID,testcycleId);
		td.start(data,currentTestSuite,projectID,testcycleId);
		
		
		
		}
	
	@AfterSuite(alwaysRun = true)
	public void updateHTMLReport() throws SQLException{
		
		ReportUtil.updateEndTime(TestUtil.now("yyyy-MM-dd HH:mm:ss"));	
	}
	
}
