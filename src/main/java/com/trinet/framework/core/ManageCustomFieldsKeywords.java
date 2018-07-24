package com.trinet.framework.core;

import static com.trinet.framework.core.GetData.UIMap;

import java.awt.AWTException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.util.Strings;

import bsh.ParseException;

import com.trinet.framework.utils.DBUtil;

/**
 * @author nrai
 *
 */
/**
 * @author nrai
 *
 */
public class ManageCustomFieldsKeywords extends Keywords {
	public WebElement id_elements_benefit;
	private boolean flagForMainMenu = false;
	public String same_date, rand;
	private String performanceProperty;
	public static int rowSIZE;
	public static String Requested_Date;
	public static String UI_Employee_Name;
	public static String UI_Emp_BusinessTitle;
	public static String DB_Expected_EmployeeName;
	public static String DB_Expected_BusinessTitle;
	public static String UI_Emp_DirectReports;
	public static String UI_DirectReport, DB_Expected_TotalDirectReports;

	/**
	 * This method used to verify webelement value
	 * 
	 * @param object
	 *            Webelement property
	 * @param data
	 *            Expected result
	 * @return Pass or Fail
	 */
	
	
	
	
	
	
	
	public String verifyValue(String object, String data) 
	{
		WebElement element = getWebElement(object, data);
		if (element != null) {
			String expected = data.trim();
			String actual = element.getText();
			if (actual.contentEquals(expected)) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
			}
		}

		logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element " + element + " Not Found");
		return Constants.KEYWORD_FAIL + " -- Could not find Element " + element + " Not Found";
	}
	
	
	


	
	
	
	
	
	public String selectByVisibleText(String object, String data) {
		WebElement element = getWebElement(object, data);
		if (element != null) {
					Select select= new Select(element);
					select.selectByVisibleText(data);
					//select.selectByValue(data);
		}
		else {
			return Constants.KEYWORD_FAIL + " -- selctvalue doesnt peromed action " + " -- " ;
		}

		logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element " + element + " Not Found");
		return Constants.KEYWORD_FAIL + " -- Could not find Element " + element + " Not Found";
	}
	
	
	public String selectByValue(String object, String data) {
		WebElement element = null;
		try {
			element = getWebElement(object, data);

			if (element != null) {
				Select select = new Select(element);
				select.selectByValue(data);
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL
						+ " -- selctvalue doesnt peromed action " + " -- ";
			}
		} catch (Exception e) {
			logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element "
					+ element + " Not Found");
			return Constants.KEYWORD_FAIL + " -- Could not find Element "
					+ element + " Not Found";
		}

	}
	
	
	public String selectByIndex(String object, String data) {
		WebElement element = getWebElement(object, data);
		if (element != null) {
					Select select= new Select(element);
					select.selectByIndex(Integer.parseInt(data));
		}
		else {
			return Constants.KEYWORD_FAIL + " -- selctvalue doesnt peromed action " + " -- " ;
		}

		logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element " + element + " Not Found");
		return Constants.KEYWORD_PASS + " -- Could not find Element " + element + " Not Found";
	}

	public String clickElemetnt(String object, String data) {

		String status = Constants.KEYWORD_PASS;
		try {
			WebElement element = driver.findElement(By.xpath(UIMap
					.getProperty(object)));

			if (element != null) {
				element.click();
			}
		} catch (Exception e) {
			status = Constants.KEYWORD_PASS;
		}

		return status;

	}
	
	/**
	 * This method is used to verify dashboard widget data
	 * 
	 * @param object
	 *            Webelement property
	 * @param data
	 *            Expected result
	 * @return Pass or Fail
	 */
	public String verifyDashboardWidgetData(String object, String data) {
		String[] holidayWidgetObject = object.split(",");
		String[] holidayWidgetdata = data.split(";");

		for (int i = 0; i < holidayWidgetdata.length; i++) {
			if (holidayWidgetdata[i].startsWith("sql") || holidayWidgetdata[i].startsWith("SQL")) {
				try {
					holidayWidgetdata[i] = DBUtil.getDBData(holidayWidgetdata[i]);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				holidayWidgetdata[i] = holidayWidgetdata[i];
			}
		}
		WebElement element1 = getWebElement(holidayWidgetObject[0], data);
		if (element1 != null) {
			for (int i = 0; i <= 3; i++) {
				String actual = "";
				String expected = holidayWidgetdata[i].trim();
				WebElement element = getWebElement(holidayWidgetObject[i + 1], data);
				actual = element.getText().trim();
				if (actual.contains(expected)) {
					logger.info("text verified " + actual + "with " + expected);
				} else {
					return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
				}
			}

		} else {
			try {
				String holidayDate = DBUtil.getDBData(holidayWidgetdata[1]);
				if (holidayDate == null || holidayDate.isEmpty()) {
					return Constants.KEYWORD_PASS + " -- company does not have next holiday";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return Constants.KEYWORD_FAIL + " -- element doest not exists " + element1;
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * Attempt to click on an item, but do not fail if item isn't present (Used for
	 * optional pages controls we don't care about)
	 *
	 * @param object
	 *            - UIMap located key
	 * @param data
	 *            - not used
	 * @return - Contants.KEYWORD_PASS
	 */
	public String clickIfNeeded(String object, String data) {
		logger.info("Trying to click on {}, won't fail if it's not there", object);
		String retval = Constants.KEYWORD_FAIL;
		try {

			waitForElement(object, "5000");
			retval = click(object, data);
		} catch (Exception e) {
			logger.info("Exception Ignored {}", e);
		}
		if (retval.contains(Constants.KEYWORD_PASS)) {
			logger.info("{} was present, clicked", object);
		} else {
			logger.info("{} was absent. Oh well!", object);
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * Verifies and waits for an element passed in 'object' column to exist and
	 * displayed
	 *
	 * @param object
	 *            - UIMap locator of item you want to wait for
	 * @param data
	 *            - not used
	 * @return Pass/Fail
	 */
	public String waitForElement(String object, String data) {
		try {
			/*
			 * if (getDOMWebElement(object) != null) { return Constants.KEYWORD_PASS; }
			 */
		} catch (StaleElementReferenceException sere) {
			if (demoMode) {
				System.out.println("Stale Reference Exception Encountered. Waiting for Page to Stabilise.");
			}
			logger.info(sere.getMessage());
			// TODO This shouldn't need an explicit wait, but there may be a trick to making
			// this work properly.
			// safeWait("", CONFIG.getProperty("explicitwait"));
			return waitForElement(object, data);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return Constants.KEYWORD_FAIL + " Object not found " + object;
	}

	/**
	 * Verify money text with database query value
	 * 
	 * @param object
	 *            element with pay check value
	 * @param data
	 *            query to get check amount
	 * @return pass or fail
	 */
	public String verifyGSMoneyText(String object, String data) {
		WebElement element = getWebElement(object, data);
		String actual = "";
		String expected = data.trim();
		if (element != null) {
			actual = element.getText().trim();
			actual = actual.replace("$", "");
			actual = actual.replace(",", "");
			System.out.println("now the replaced text is :" + actual);
			logger.info("the actual data is : " + actual);
			if (actual.contains(expected)) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
			}
		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element " + element + " Not Found");
			return Constants.KEYWORD_FAIL + " -- Could not find Element " + element + " Not Found";
		}
	}

	/**
	 * Calculate estimated amount from direct deposit accounts
	 * 
	 * @param object
	 *            estimated amount column element
	 * @param data
	 *            query to get total value from last paycheck
	 * @return pass or fail
	 */
	public String calculateEstimatedAmountFromDirectDeposit(String object, String data) {
		logger.info("Executing calculateEstimatedAmountFromDirectDeposit keyword");

		try {
			if (!Strings.isNullOrEmpty(data)) {
				String locator = UIMap.getProperty(object);
				List<WebElement> elements = getWebElements(locator);
				String actual = "";
				float totalSum = 0;
				List<String> totalValues = new ArrayList<String>();
				System.out.println("the data sent to verify text is :" + data);
				logger.info("the expected data is : " + data);
				String expected = data.trim();
				if (elements != null) {
					for (int i = 0; i < elements.size(); i++) {
						actual = elements.get(i).getText().trim();
						actual = actual.replace("$", "");
						actual = actual.replace(",", "");
						totalValues.add(actual);

						System.out.println("now the replaced text is :" + actual);
						logger.info("the actual data is : " + actual);
					}

					for (int counter = 0; counter < totalValues.size(); counter++) {
						float val = Float.parseFloat(totalValues.get(counter));
						totalSum = val + totalSum;
					}
					BigDecimal bd = new BigDecimal(Float.toString(totalSum));
					bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
					bd.floatValue();

					if (bd.floatValue() == Float.parseFloat(expected)) {
						return Constants.KEYWORD_PASS;
					} else {
						return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
					}

				} else {
					logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element " + object + " Not Found");
					return Constants.KEYWORD_FAIL + " -- Could not find Element " + object + " Not Found";
				}
			} else {
				logger.error(Constants.KEYWORD_FAIL
						+ " -- Data is empty to validate direct deposit account amount information");
				return Constants.KEYWORD_FAIL
						+ " -- Data is empty to validate direct deposit account amount information";
			}
		} catch (Exception ex) {
			logger.error(Constants.KEYWORD_FAIL + " -- validation is not successful for direct deposit account amount");
			return Constants.KEYWORD_FAIL + " -- validation is not successful for direct deposit account amount";
		}
	}

	/**
	 * Verify the number of accounts in direct deposit table
	 * 
	 * @param object
	 *            element for account table accounts column
	 * @param data
	 *            query to get account information from database
	 * @return pass or fail
	 */
	public String verifyAccountsInDirectDeposit(String object, String data) {
		logger.info("Executing verifyAccountsInDirectDepositkeyword");

		try {
			if (!Strings.isNullOrEmpty(data)) {
				String locator = UIMap.getProperty(object);
				List<WebElement> group = getWebElements(locator);
				if (group == null) {
					return Constants.KEYWORD_FAIL + " Error-->Group elements not found";
				}

				String temp[] = data.split(",");
				ArrayList<String> expectedElementsText = new ArrayList<String>();
				for (int i = 0; i < temp.length; i++) {
					expectedElementsText.add(temp[i].trim());
				}
				logger.info("Expected text is " + expectedElementsText);

				ArrayList<String> actualElementsText = new ArrayList<String>();
				for (int i = 0; i < group.size(); i++) {
					System.out
					.println("*********************group item is*************" + group.get(i).getText().trim());
					String accountName = group.get(i).getText().trim();
					actualElementsText.add(accountName);
				}
				logger.info("Actual text is " + actualElementsText);

				if (actualElementsText.containsAll(expectedElementsText)) {
					return Constants.KEYWORD_PASS;
				} else {
					return Constants.KEYWORD_FAIL + "Error-> Direct Deposit" + data + " does not match expected ";
				}

			} else {
				logger.error(
						Constants.KEYWORD_FAIL + " -- Data is empty to validate direct deposit account information");
				return Constants.KEYWORD_FAIL + " -- Data is empty to validate direct deposit account information";
			}
		} catch (Exception ex) {
			logger.error(Constants.KEYWORD_FAIL + " -- validation is not successful for direct deposit account");
			return Constants.KEYWORD_FAIL + " -- validation is not successful for direct deposit account";
		}
	}

	/**
	 * This keyword is added for hamburger menu which is not always visible on page
	 * 
	 * @param object
	 *            menu element
	 * @param data
	 *            none
	 * @return pass or fail
	 */
	public String toggleMenuClick(String object, String data) {
		String retval = null;
		try {
			logger.info("Executing toggleMenuClick keyword");
			String locator = UIMap.getProperty(object);
			List<WebElement> elements = getWebElements(locator);

			if (elements == null) {
				return Constants.KEYWORD_FAIL + " Error-->elements not found";
			}

			if (elements.size() == 0) {
				// Menu is not visible
			} else {
				retval = click(object, data);
				return Constants.KEYWORD_PASS;

			}
		} catch (Exception e) {
			logger.error("Exception in clicking element {}", e);
			Assert.fail("Exception in clicking element {}");
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;

	}

	/**
	 * Verify whether new page exists or not.
	 * 
	 * @return boolean TRUE or FALSE
	 */
	public String isNewPageExist(String object, String data) {
		String base = driver.getWindowHandle();
		Set<String> set = driver.getWindowHandles();
		set.remove(base);
		if (set.size() == 1) {
			driver.switchTo().window((String) set.toArray()[0]);
			driver.close();
			return Constants.KEYWORD_PASS;
		}
		return Constants.KEYWORD_FAIL;
	}

	/**
	 * This keyword is used to verify GS Money - text-Data-Money from any element
	 * list input field.
	 * 
	 * @param object
	 *            UI element
	 * @param data
	 *            text to be verified
	 * 
	 * @return String pass or fail
	 */

	public String verifyGSMoneyDate(String object, String data) {

		System.out.println("DATA IS :" + data);
		System.out.println("OBJECT IS :" + object);
		String[] datanames = data.split(",");
		List<WebElement> objectlist = driver
				.findElements(By.xpath("//h6[contains(@class, 'col-sm-4 semibold ng-binding')]"));

		Map<String, String> hashtableUI = new Hashtable<String, String>();
		Map<String, String> hashtableDB = new Hashtable<String, String>();
		Enumeration namesUI, namesDB;
		String keyui, keydb;

		for (int i = 0; i < objectlist.size(); i++) {

			WebElement element2 = getWebElement("GSUIPayHistoryTextDate," + i, data);
			String dates = element2.getText().trim();
			dates = dates.replace(",", "");
			dates = dates.replace("$", "");

			WebElement element = getWebElement(object + "," + i, data);
			String actual = "";
			String expected = datanames[i].trim();
			expected = expected.replace(",", "");
			expected = expected.replace("$", "");

			if (element != null) {
				actual = element.getText().trim();
				actual = actual.replace(",", "");
				actual = actual.replace("$", "");
				System.out.println("EXPECTED IS :" + expected);

				hashtableUI.put(dates, actual);
				hashtableDB.put(dates, expected);

				for (Map.Entry<String, String> entry : hashtableDB.entrySet()) {
					String GSdateAnswer = hashtableUI.get(entry.getKey());
					String GSmoneyAnswer = entry.getValue();
					if (GSmoneyAnswer.equals(GSdateAnswer)) {
						System.out.println("UI HashTable VS DB HashTable match...");
					}
				}

				if (actual.contains(expected)) {
					System.out.println("PASS :" + datanames[i]);
					// return Constants.KEYWORD_PASS;
				} else {
					return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
				}
			} else {

				logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element " + element + " Not Found");
				return Constants.KEYWORD_FAIL + " -- Could not find Element " + element + " Not Found";
			}
		}
		return Constants.KEYWORD_PASS;

	}

	/**
	 * This keyword is used to verify GS Money - text-Data-Money from Query ROW and
	 * Object ROW - By Selected Dropdown input field.
	 * 
	 * @param object
	 *            UI element
	 * @param data
	 *            text to be verified
	 * 
	 * @return String pass or fail
	 */

	public String verifyGSMoneyDateDDRow(String object, String data) {
		System.out.println("DATA IS :" + data);
		System.out.println("OBJECT IS :" + object);
		int j = 0;
		String[] datanames = data.split(",");
		List<WebElement> objectlist = driver.findElements(By.xpath("//*[@id='dataTable']/div[*]/div[3]/h6/span"));
		int arrayRows = datanames.length;
		if (arrayRows == objectlist.size()) {
			System.out.println("==> Object rows VS DB rows match..");
		} else {
			System.out.println("==> Object rows VS DB rows NOT match..");
		}

		Select select = new Select(driver.findElement(By.xpath("//*[@id='durationList']")));
		String DropDownValue = select.getFirstSelectedOption().getText();
		System.out.println("DropDown For Selection IS :" + DropDownValue);

		for (int i = 0; i < objectlist.size(); i++) {
			System.out.println("ROW #: " + i);
			WebElement element = getWebElement(object + "," + i, data);
			String actual = "";
			String expected = datanames[j].trim() + datanames[j + 1].trim() + datanames[j + 2].trim()
					+ datanames[j + 3].trim();
			j = j + 4;
			// String expected = data;
			expected = expected.replace(",", "");
			expected = expected.replace("$", "");
			expected = expected.replace("-", "");

			if (element != null) {
				// actual = element.getText().trim();
				WebElement element2 = getWebElement("GSUIPSEndDayText," + i, data);
				String data1 = element2.getText().trim();

				WebElement element3 = getWebElement("GSUIPSCheckDayText," + i, data);
				String data2 = element3.getText().trim();

				WebElement element4 = getWebElement("GSUIPSCheckNumberText," + i, data);
				String data3 = element4.getText().trim();

				WebElement element5 = getWebElement("GSUIPayStatementText," + i, data);
				String data4 = element5.getText().trim();

				actual += data1 + data2 + data3 + data4;

				actual = actual.replace(",", "");
				actual = actual.replace("$", "");
				actual = actual.replace("-", "");
				System.out.println("EXPECTED Query LIST IS :" + expected);
				System.out.println("ACTUAL OBJECT LIST IS :" + actual);

				if (actual.contains(expected)) {
					System.out.println("ROW Object List vs DB List - PASS");
					// return Constants.KEYWORD_PASS;
				} else {
					return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
				}

			}
		}
		return Constants.KEYWORD_PASS;

	}

	/**
	 * Verify the tax withholding grid on Money->taxes page
	 * 
	 * @param object
	 *            grid element
	 * @param data
	 *            Query to get the grid data
	 * @return pass or fail
	 */
	public String verifyTaxWithholdingData(String object, String data) {
		try {
			String locator;
			logger.info("Executing verifyTaxWithholdingData keyword");
			String results = Constants.KEYWORD_PASS;
			ArrayList<String> expectedContentsFromDb = new ArrayList<String>();
			ArrayList<String> actualContentsFromUi = new ArrayList<String>();

			int index = object.indexOf(",");
			if (index > 0) {
				locator = UIMap.getProperty(object.substring(0, index));
			} else {
				locator = UIMap.getProperty(object);
			}

			String gridContentsFromDb[] = data.split(",");
			for (int i = 0; i < gridContentsFromDb.length; i++) {
				expectedContentsFromDb.add(gridContentsFromDb[i].trim());
			}
			logger.info("Expected text is " + expectedContentsFromDb);

			// String locator = UIMap.getProperty(object);
			if (object.contains(",")) {
				WebElement ele = getWebElement(object, "");
				if (ele == null) {
					return Constants.KEYWORD_FAIL + " Error-->Group elements not found";
				}
				String textFromUi = ele.getText().trim();
				textFromUi = textFromUi.replaceAll("\n", ",");
				String listOfTextsFromUi[] = textFromUi.split(",");
				for (int ii = 0; ii < listOfTextsFromUi.length; ii++) {
					// Need to do this data massaging
					// Not verifying list of forms attached in grid. These are
					// not coming from db directly.
					// if (!listOfTextsFromUi[ii].contains("form")) {
					if (listOfTextsFromUi[ii].contains("$")) {
						listOfTextsFromUi[ii] = listOfTextsFromUi[ii].replace("$", "");
						if (listOfTextsFromUi[ii].contains(".00")) {
							listOfTextsFromUi[ii] = listOfTextsFromUi[ii].replace(".00", "");
						}
						actualContentsFromUi.add(listOfTextsFromUi[ii].trim());
					} else {
						actualContentsFromUi.add(listOfTextsFromUi[ii].trim());
					}
					// }
				}
			} else {

				List<WebElement> group = getWebElements(locator);
				if (group == null) {
					return Constants.KEYWORD_FAIL + " Error-->Group elements not found";
				}
				for (int i = 0; i < group.size(); i++) {
					System.out
					.println("*********************group item is*************" + group.get(i).getText().trim());
					String textFromUi = group.get(i).getText().trim();
					// textFromUi = textFromUi.replaceAll("\n", ",");
					// String listOfTextsFromUi[] = textFromUi.split(",");
					String listOfTextsFromUi[] = textFromUi.split("\n");
					for (int ii = 0; ii < listOfTextsFromUi.length; ii++) {
						// Need to do this data massaging
						// Not verifying list of forms attached in grid. These are
						// not coming from db directly.
						// if (!listOfTextsFromUi[ii].contains("form")) {
						if (listOfTextsFromUi[ii].equalsIgnoreCase("Single, or Married w...")) {
							listOfTextsFromUi[ii] = "Single, or Married with two or more incomes";
						}
						if (listOfTextsFromUi[ii].contains("$")) {
							listOfTextsFromUi[ii] = listOfTextsFromUi[ii].replace("$", "");
							listOfTextsFromUi[ii] = listOfTextsFromUi[ii].replace(",", "");
							if (listOfTextsFromUi[ii].contains(".00")) {
								listOfTextsFromUi[ii] = listOfTextsFromUi[ii].replace(".00", "");
							}
							actualContentsFromUi.add(listOfTextsFromUi[ii].trim());
						} else {
							actualContentsFromUi.add(listOfTextsFromUi[ii].trim());
						}
						// }
					}
				}
			}

			logger.info("Actual text is " + actualContentsFromUi);

			logger.info("Comparing ui values with database values");
			int uiText = 0;
			String actualUiText;
			for (int dbText = 0; dbText < expectedContentsFromDb.size(); dbText++) {
				if (actualContentsFromUi.get(uiText).contains(",")) {
					actualUiText = actualContentsFromUi.get(uiText).replace(",", "");
				} else {
					actualUiText = actualContentsFromUi.get(uiText);
				}
				if ((actualUiText).equalsIgnoreCase((expectedContentsFromDb).get(dbText))) {
					logger.info(actualContentsFromUi.get(uiText));
					logger.info((expectedContentsFromDb).get(dbText));
					results = Constants.KEYWORD_PASS;
					uiText++;
				} else {
					results = Constants.KEYWORD_FAIL + "Error-->App data " + data + " does not match expected ";
					uiText++;
				}
			}
			return results;

		} catch (Exception ex) {
			return Constants.KEYWORD_FAIL + "Error-->App data " + data + " does not match expected ";
		}
	}

	/**
	 * Verify Benefits Overview page
	 * 
	 * @return pass or fail
	 */
	public String verify_Benefit(String object, String data) throws InterruptedException, SQLException {
		String locator = UIMap.getProperty(object);
		logger.debug("Check locator if_exist " + locator);
		boolean flag = false;
		WebElement element = getWebElement(object, data);
		if (element != null) {
			List<WebElement> id_elements = element.findElements(By.xpath(locator));
			int rowSIZE = id_elements.size();
			String No_of_Benefit_plan = DBUtil
					.getDBData("sql|hr.select count(benefit_plan) from PS_BENEFITS_VW a where emplid=" + "\'" + data
							+ "\'and PAY_END_DT in (select max(b.PAY_END_DT) from PS_BENEFITS_VW b where  a.emplid=b.emplid )");
			System.out.println("#row" + Integer.parseInt(No_of_Benefit_plan));
			try {
				for (int i = 0; i < rowSIZE; i++) {
					id_elements_benefit = id_elements.get(i);
					if (id_elements.get(i).getText().trim().contains("Medical")) {
						verify_Plan(object, data, "Medical%");
						flag = true;
					} else if (id_elements.get(i).getText().trim().contains("Dental")) {
						verify_Plan(object, data, "Dental%");
						flag = true;
					} else if (id_elements.get(i).getText().trim().contains("Vision")) {
						verify_Plan(object, data, "%Vision%");
						flag = true;
					} else if (id_elements.get(i).getText().trim().contains("Life")) {
						verify_Plan_Life_Beneficiery(object, data);
						flag = true;
					} else if (id_elements.get(i).getText().trim().contains("Flex Spending Health - U.S.")) {
						verify_Plan(object, data, "Medical%");
						flag = true;
					} else if (id_elements.get(i).getText().trim().contains("Short-Term")) {
						verify_Plan(object, data, "Medical%");
						flag = true;
					} else if (id_elements.get(i).getText().trim().contains("Long-Term Disability")) {
						verify_Plan(object, data, "Medical%");
						flag = true;
					} else if (id_elements.get(i).getText().trim().contains("Current Beneficiaries")) {
						verify_Plan_Life_Beneficiery(object, data);
						flag = true;
					} else
						flag = false;

				}

			} catch (Exception e) {
				logger.error("Exception in  : {}", e, e.getMessage());
				return Constants.KEYWORD_FAIL + " -- Not able to validate benefit";
			}
		} else
			flag = false;
		if (flag == true)
			return Constants.KEYWORD_PASS;
		else
			return Constants.KEYWORD_FAIL;
	}

	@SuppressWarnings("finally")
	public String verify_Plan(String object, String data, String plan) throws InterruptedException, SQLException {

		String locator = UIMap.getProperty(object);
		logger.debug("Check locator if_exist " + locator);
		boolean flag = false;
		try {

			String path_plan_name = ".//div[@class='tn-provider-plan-name ng-binding']";
			String path_group = ".//span[@class='pull-right ng-binding ng-scope']";
			String path_dependent = ".//tn-table//h6";
			List<WebElement> findElement_group = id_elements_benefit.findElements(By.xpath(path_group));
			String actual_group = findElement_group.get(0).getText().trim();
			String group_expected = DBUtil.getDBData(
					"sql|hr.SELECT 'Group #: ' || TO_char(GROUP_NBR) FROM ps_t2_nx_health_vw h WHERE h.xlatlongname like "
							+ "\'" + plan + "\' AND h.emplid = " + "\'" + data + "\' AND h.empl_rcd='0'");

			if (actual_group.contains(group_expected)) {
				flag = true;
			}
			List<WebElement> findElement_name = id_elements_benefit.findElements(By.xpath(path_plan_name));
			String actual_plan_name = findElement_name.get(0).getText().trim();
			String query1 = "SELECT case when DESCR in ('UHC') then 'UNITED HEALTH CARE' when DESCR in('kaiser') then 'KAISER PERMANENTE' else DESCR  end as DESCR FROM ps_t2_nx_health_vw h WHERE h.xlatlongname like "
					+ "\'" + plan + "\' AND h.emplid = " + "\'" + data + "\' AND h.empl_rcd='0'";
			String plan_expected = DBUtil.getDBData(
					"sql|hr.SELECT case when DESCR in ('UHC') then 'UNITED HEALTH CARE' when DESCR in('kaiser') then 'KAISER PERMANENTE' else DESCR  end as DESCR FROM ps_t2_nx_health_vw h WHERE h.xlatlongname like "
							+ "\'" + plan + "\' AND h.emplid = " + "\'" + data + "\' AND h.empl_rcd='0'");

			if (plan_expected.contains(actual_plan_name)) {
				flag = true;
			} else
				flag = false;

			List<WebElement> findElement = id_elements_benefit.findElements(By.xpath(path_dependent));
			int size1 = findElement.size();

			for (int j = 0; j < size1; j++) {

				if (findElement.get(j).getText().trim().contains("Spouse")) {
					String actual_Spouse = findElement.get(j + 1).getText().trim();
					String spouse_expected = DBUtil.getDBData("sql|hr.select Name from PS_DEPENDENT_BENEF where emplid="
							+ "\'" + data + "\'and relationship='SP'");
					try {
						Assert.assertEquals(actual_Spouse, spouse_expected);
						flag = true;
					} catch (Exception e) {
						logger.info(e.getMessage());
						return Constants.KEYWORD_FAIL + " -- Not able to validate medical for spouse";
					}
				} else if (findElement.get(j).getText().trim().contains("Self")) {
					String actual_Self = findElement.get(j + 1).getText().trim();
					String self_expected = DBUtil
							.getDBData("sql|hr.select Name from PS_PERSONAL_DATA where emplid=" + "\'" + data + "\'");
					try {
						if (actual_Self.contains(self_expected))
							flag = true;
					} catch (Exception e) {
						logger.info(e.getMessage());
						return Constants.KEYWORD_FAIL + " -- Not able to validate medical for self";
					}
				}

				else if (findElement.get(j).getText().trim().contains("Child")) {
					String actual_Child = findElement.get(j + 1).getText().trim();
					String numofchild = DBUtil
							.getDBData("sql|hr.select count(name)from PS_DEPENDENT_BENEF a where emplid=" + "\'" + data
									+ "\'and relationship='C'");
					for (int count = Integer.parseInt(numofchild); count > 0; count--) {
						String child_expected = DBUtil.getDBData(
								"sql|hr.select name from (select rownum OUR_ROWNUM, a.* from PS_DEPENDENT_BENEF a where emplid="
										+ "\'" + data + "\'and relationship='C')where OUR_ROWNUM=" + "\'" + count
										+ "\'");
						try {
							if (actual_Child.contains(child_expected))
								flag = true;
						} catch (Exception e) {
							logger.info(e.getMessage());
							return Constants.KEYWORD_FAIL + " -- Not able to validate medical for child";
						}

					}
				} else
					flag = false;
			}

		} catch (Exception e) {
			logger.info(e.getMessage());
			return Constants.KEYWORD_FAIL + " -- Not able to validate ltd";
		}

		finally {

			try {
				String path_name_fsa_ltd_std = ".//tn-benefit-value";
				List<WebElement> findElement_fsa_ltd_std = id_elements_benefit
						.findElements(By.xpath(path_name_fsa_ltd_std));
				String actual_fsa_std_ltd_name = findElement_fsa_ltd_std.get(1).getText().trim();
				String actual_fsa_std_ltd_coverage = findElement_fsa_ltd_std.get(0).getText().trim();
				if (actual_fsa_std_ltd_name.trim().contains("FSA")) {
					String fsa_expected = DBUtil.getDBData(
							"sql|hr.select TO_CHAR (Annual_Pledge, '$999,999,999,999') from ps_fsa_benefit A where A.emplid="
									+ "\'" + data
									+ "\' and A.benefit_plan in(select BENEFIT_PLAN from PS_BENEFITS_VW where emplid="
									+ "\'" + data
									+ "\'and plan_type='60')and EFFDT=(SELECT MAX (B.EFFDT)FROM ps_fsa_benefit B WHERE B.EMPLID = A.EMPLID and empl_rcd='0' and A.benefit_plan=B.benefit_plan)");
					try {
						if (actual_fsa_std_ltd_coverage.trim().contains(fsa_expected.trim())) {
							flag = true;
						} else
							flag = false;
					} catch (Exception e) {
						logger.info(e.getMessage());
						return Constants.KEYWORD_FAIL + " -- Not able to validate fsa";
					}
				}

				else if (actual_fsa_std_ltd_name.replace("Company Paid", "").trim().contains("STD")) {
					String std_expected = DBUtil.getDBData(
							"sql|hr.SELECT DESCR FROM PS_T2_NX_STD_VW h WHERE benefit_plan in(select benefit_plan from PS_BENEFITS_VW where emplid="
									+ "\'" + data + "\'and plan_type='30' and empl_rcd='0'and rownum=1)");
					try {
						if (actual_fsa_std_ltd_name.replace("Company Paid", "").trim()
								.contains(std_expected.replace("Company Paid", "").trim())) {
							flag = true;
						} else
							flag = false;
					} catch (Exception e) {
						logger.info(e.getMessage());
						return Constants.KEYWORD_FAIL + " -- Not able to validate std";
					}
				} else if (actual_fsa_std_ltd_name.replace("Company Paid", "").trim().contains("LTD")) {

					String ltd_expected = DBUtil.getDBData(
							"sql|hr.SELECT DESCR FROM PS_T2_NX_LTD_VW h WHERE benefit_plan in(select benefit_plan from PS_BENEFITS_VW where emplid="
									+ "\'" + data + "\'and plan_type='31' and empl_rcd='0'and rownum=1)");
					try {
						if (actual_fsa_std_ltd_name.replace("Company Paid", "").trim()
								.contains(ltd_expected.replace("Company Paid", "").trim())) {
							flag = true;
						} else
							flag = false;
					} catch (Exception e) {
						logger.info(e.getMessage());
						return Constants.KEYWORD_FAIL + " -- Not able to validate ltd";
					}
				} else
					flag = false;
			} catch (Exception e) {
				logger.info(e.getMessage());
				return Constants.KEYWORD_FAIL + " -- Not able to validate fsa_ltd_std";
			}

		}

		if (flag == true)
			return Constants.KEYWORD_PASS;
		else
			return Constants.KEYWORD_FAIL;

	}

	@SuppressWarnings("finally")
	public String verify_Plan_Life_Beneficiery(String object, String data) throws InterruptedException, SQLException {

		String locator = UIMap.getProperty(object);
		logger.debug("Check locator if_exist " + locator);
		boolean flag = false;
		try {
			String path_life_dependent = ".//tn-table//h6";

			List<WebElement> findElement_Life_dependent = id_elements_benefit
					.findElements(By.xpath(path_life_dependent));
			int size = findElement_Life_dependent.size();
			for (int j = 0; j < size; j++) {
				if (findElement_Life_dependent.get(j).getText().trim().equals("Spouse")) {
					String actual_Spouse = findElement_Life_dependent.get(j + 1).getText().trim();
					String spouse_expected = DBUtil.getDBData("sql|hr.select Name from PS_DEPENDENT_BENEF where emplid="
							+ "\'" + data + "\'and relationship='SP'");
					try {
						Assert.assertEquals(actual_Spouse, spouse_expected);
						flag = true;
					} catch (Exception e) {
						logger.info(e.getMessage());
						return Constants.KEYWORD_FAIL + " -- Not able to validate life and AD and D for Spouse";
					}
				}

				else if (findElement_Life_dependent.get(j).getText().trim().contains("Child")) {
					String actual_Child = findElement_Life_dependent.get(j + 1).getText().trim();
					String numofchild = DBUtil
							.getDBData("sql|hr.select count(name)from PS_DEPENDENT_BENEF a where emplid=" + "\'" + data
									+ "\'and relationship='C'");
					for (int count = Integer.parseInt(numofchild); count > 0; count--) {
						String child_expected = DBUtil.getDBData(
								"sql|hr.select name from (select rownum OUR_ROWNUM, a.* from PS_DEPENDENT_BENEF a where emplid="
										+ "\'" + data + "\'and relationship='C')where OUR_ROWNUM=" + "\'" + count
										+ "\'");
						try {
							if (actual_Child.contains(child_expected))
								flag = true;
						} catch (Exception e) {
							logger.info(e.getMessage());
							return Constants.KEYWORD_FAIL + " -- Not able to validate medical for child";
						}
					}
				} else if (findElement_Life_dependent.get(j).getText().trim().contains("Self")) {
					String actual_Self = findElement_Life_dependent.get(j + 1).getText().trim();
					String self_expected = DBUtil
							.getDBData("sql|hr.select Name from PS_PERSONAL_DATA where emplid=" + "\'" + data + "\'");
					try {
						if (actual_Self.contains(self_expected))
							flag = true;
					} catch (Exception e) {
						logger.info(e.getMessage());
						return Constants.KEYWORD_FAIL + " -- Not able to validate life and AD and D for self";
					}
				} else
					flag = false;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return Constants.KEYWORD_FAIL + " -- Not able to validate life and AD and D ";
		} finally {

			try {

				String path_beneficiary = ".//div[@class='tn-benefits-users-user-name ng-binding']";
				String path_beneficiary_status = ".//div[@class='tn-benefits-users-user-relationship ng-binding']";

				List<WebElement> findElement_beneficiary = id_elements_benefit.findElements(By.xpath(path_beneficiary));
				List<WebElement> findElement_beneficiary_Status = id_elements_benefit
						.findElements(By.xpath(path_beneficiary_status));
				int size = findElement_beneficiary_Status.size();

				for (int j = 0; j < size; j++) {
					if (findElement_beneficiary_Status.get(j).getText().trim().equals("Spouse")) {
						String actual_spouse_beneficiary = findElement_beneficiary.get(j).getText().trim();
						String spouse_expected = DBUtil
								.getDBData("sql|hr.select Name from PS_DEPENDENT_BENEF where emplid=" + "\'" + data
										+ "\'and relationship='SP'");
						try {
							Assert.assertEquals(actual_spouse_beneficiary, spouse_expected);
							flag = true;
						} catch (Exception e) {
							logger.info(e.getMessage());
							return Constants.KEYWORD_FAIL + " -- Not able to validate Spouse Beneficiaries";
						}
					}

					else if (findElement_beneficiary_Status.get(j).getText().trim().contains("Child")) {
						String actual_child_beneficiary = findElement_beneficiary.get(j).getText().trim();
						String numofchild = DBUtil
								.getDBData("sql|hr.select count(name)from PS_DEPENDENT_BENEF a where emplid=" + "\'"
										+ data + "\'and relationship='C'");
						for (int count = Integer.parseInt(numofchild); count > 0; count--) {
							String child_expected = DBUtil.getDBData(
									"sql|hr.select name from (select rownum OUR_ROWNUM, a.* from PS_DEPENDENT_BENEF a where emplid="
											+ "\'" + data + "\'and relationship='C')where OUR_ROWNUM=" + "\'" + count
											+ "\'");
							try {
								if (actual_child_beneficiary.contains(child_expected))
									flag = true;
							} catch (Exception e) {
								logger.info(e.getMessage());
								return Constants.KEYWORD_FAIL + " -- Not able to validate Children Beneficiaries ";
							}
						}
					} else
						flag = false;

				}
			} catch (Exception e) {
				logger.error("Exception in  : {}", e, e.getMessage());
				return Constants.KEYWORD_FAIL + " -- Not able to validate for beneficiary ";
			}

		}
		if (flag == true)
			return Constants.KEYWORD_PASS;
		else
			return Constants.KEYWORD_FAIL;

	}

	/**
	 * Verify message id from message queue after updating/deleting. Success is
	 * successful message id.
	 * 
	 * @param object
	 *            No
	 * @param data
	 *            employee id and company id
	 * @return pass or fail
	 */
	public String verifyMessageIdAfterEdit(String object, String data) {
		logger.info("Verify the message is successfully executed or not");
		String expectedMessageStatus = "Success";

		try {
			if (!Strings.isNullOrEmpty(data)) {
				String[] dataVariables = data.split(",");
				String empId = saveAnyDataInHash.getValue(dataVariables[0]);
				String companyId = saveAnyDataInHash.getValue(dataVariables[1]);
				String businessEvent = dataVariables[2];

				// Get message id for executed event
				String getMessageId = "sql|hp.select * from (select message_id from message_audit where employee_id= '"
						+ empId + "' " + "and company_id='" + companyId + "' and business_event= '" + businessEvent
						+ "' order by audit_timestamp desc) where rownum = 1";
				String messageIdResult = DBUtil.getDBData(getMessageId);

				if (!Strings.isNullOrEmpty(messageIdResult)) {
					// As message id is not null. Select 'SRM' status for the selected message id
					String getSRMStatus = "sql|hp.select status from message_audit where message_id = '"
							+ messageIdResult + "' and business_event='SRM'";
					String messageStatus = DBUtil.getDBData(getSRMStatus);
					if (messageStatus.equals(expectedMessageStatus)) {
						return Constants.KEYWORD_PASS;
					} else {
						logger.error(
								"Message status for business event SRM is not successful. There is an error in event processing by peoplesoft.");
						return Constants.KEYWORD_FAIL
								+ " -- Message status for business event SRM is not successful.There is an error in event processing by peoplesoft.";
					}
				} else {
					logger.error("There is message id generated for the operation.");
					return Constants.KEYWORD_FAIL + " -- There is message id generated for the operation.";
				}
			} else {
				logger.error("Employee id and Company id needs to pass as a data to check status of SRM event");
				return Constants.KEYWORD_FAIL
						+ " -- Employee id and Company id needs to pass as a data to check status of SRM event";
			}

		} catch (Exception ex) {
			logger.error("Exception in verifying status of SRM event for the selected operation");
			return Constants.KEYWORD_FAIL + " -- Exception in verifying status of SRM event for the selected operation";
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * This keyword GS Company Directory - text-Data-Money from Query ROW and Object
	 * ROW - By Selected Dropdown input field.
	 * 
	 * @param object
	 *            UI element
	 * @param data
	 *            text to be verified
	 * 
	 * @return String pass or fail
	 */

	public String verifyCompanyDirectoryRow(String object, String data) {
		System.out.println("DATA IS :" + data);
		System.out.println("OBJECT IS :" + object);
		int j = 0;
		int z = 1;
		String[] datanames = data.split(",");
		List<WebElement> objectlist = driver.findElements(By.xpath("//h6[contains(@class, 'semibold ng-binding')]"));

		logger.info("Object Size :" + objectlist.size());
		System.out.println("Object Size :" + objectlist.size());

		for (int i = 0; i < objectlist.size() - 1; i++) {
			System.out.println("ROW #: " + i);
			logger.info("ROW #: " + i);
			WebElement element = getWebElement(object + "," + i, data);
			String actual = "";
			System.out.println("datanames1 :" + datanames[j]);
			System.out.println("datanames2:" + datanames[j + 1]);
			logger.info("datanames1 :" + datanames[j]);
			logger.info("datanames2:" + datanames[j + 1]);

			String expected = datanames[j].trim() + datanames[j + 1].trim() + datanames[j + 2].trim()
					+ datanames[j + 3].trim() + datanames[j + 4].trim() + datanames[j + 5].trim()
					+ datanames[j + 6].trim() + datanames[j + 7].trim();
			j = j + 8;

			// String expected = data;
			expected = expected.replace(",", "");
			expected = expected.replace("$", "");
			expected = expected.replace("-", "");
			expected = expected.replace(" ", "");
			System.out.println("EXPECTED Query LIST IS :" + expected);
			logger.info("EXPECTED Query LIST IS :" + expected);

			if (element != null) {

				WebElement element2 = getWebElement("GSEmployeeName," + z, data);
				String data1 = element2.getText().trim();

				WebElement element3 = getWebElement("GSPosition," + i, data);
				String data2 = element3.getText().trim();

				WebElement element4 = getWebElement("GSStatus," + i, data);
				String data3 = element4.getText().trim();
				if (data3.contains("Active")) {
					data3 = "A";
				} else {
					data3 = "T";
				}

				WebElement element5 = getWebElement("GSDepartment," + i, data);
				String data4 = element5.getText().trim();

				WebElement element6 = getWebElement("GSLocation," + i, data);
				String data5 = element6.getText().trim();

				WebElement element7 = getWebElement("GSServiceDate," + i, data);
				String data6 = element7.getText().trim();

				WebElement element8 = getWebElement("GSDirectManager," + i, data);
				String data7 = element8.getText().trim();

				actual += data1 + data2 + data3 + data4 + data5 + data6 + data7;
				actual = actual.replace(",", "");
				actual = actual.replace("$", "");
				actual = actual.replace("-", "");
				actual = actual.replace(" ", "");
				z = z + 1;
				System.out.println("ACTUAL OBJECT LIST IS :" + actual);
				logger.info("ACTUAL OBJECT LIST IS :" + actual);

				if (actual.equals(expected)) {
					System.out.println("ROW Object List vs DB List - PASS");
					logger.info("ROW Object List vs DB List - PASS");
					// return Constants.KEYWORD_PASS;
				} else {
					return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
				}

			} else {
				return Constants.KEYWORD_FAIL + " -- element is Null ";
			}
		}
		return Constants.KEYWORD_PASS;

	}

	/**
	 * Keyword to see main menu in admin view is exist or not.
	 * 
	 * @param object
	 *            main menu
	 * @param data
	 *            no
	 * @return pass or fail
	 */
	public String isMainMenuExists(String object, String data) {
		try {
			String[] expectedSubMenusAsRole = data.split("\\|");
			WebElement mainMenu;
			flagForMainMenu = false;

			List<WebElement> mainMenuElements = getWebElements(object);
			if (mainMenuElements.size() > 0) {
				mainMenu = getWebElement(object, expectedSubMenusAsRole[0]);
				if (mainMenu != null && mainMenu.isDisplayed() && mainMenu.isEnabled()) {
					flagForMainMenu = true;
					if (mainMenu.getText().replaceAll("\\s", "").equalsIgnoreCase(object)) {
						logger.info(Constants.KEYWORD_PASS
								+ " -- Main menu is displayed in admin view as expected by role --" + mainMenu);
						return Constants.KEYWORD_PASS;
					} else {
						logger.info(Constants.KEYWORD_FAIL + " -- Main menu is not matching with the object --  "
								+ mainMenu);
						return Constants.KEYWORD_FAIL + " -- Main menu is not matching with the object --  " + mainMenu;
					}
				}
			} else if ((mainMenuElements.isEmpty() == true) && (expectedSubMenusAsRole[0].equalsIgnoreCase("N"))) {
				logger.info(Constants.KEYWORD_PASS
						+ " -- Main menu is not displayed in admin view as expected by role --" + object);
				return Constants.KEYWORD_PASS;
			} else if ((mainMenuElements.isEmpty() == true) && (expectedSubMenusAsRole[0].equalsIgnoreCase("Y"))) {
				logger.info(Constants.KEYWORD_FAIL
						+ " --Main menu is not displayed which is not expected as per role --  " + object);
				return Constants.KEYWORD_FAIL + " --Main menu is not displayed which is not expected as per role --  "
				+ object;
			} else if ((mainMenuElements.isEmpty() != true) && (expectedSubMenusAsRole[0].equalsIgnoreCase("N"))) {
				logger.info(Constants.KEYWORD_FAIL + " -- Main menu is displayed which is not expected as per role --"
						+ object);
				return Constants.KEYWORD_FAIL + " -- Main menu is displayed which is not expected as per role -- "
				+ object;
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception ex) {
			logger.error("Exception in getting main menu {}", ex);
			Assert.fail("Exception in getting main menu {}");
			return Constants.KEYWORD_FAIL + "- Exception in getting main menu {}";
		}
	}

	/**
	 * Verify the submenu count under main menu in admin view
	 * 
	 * @param object
	 *            main menu
	 * @param data
	 *            submenu display as per role
	 * @return pass or fail
	 * @throws InterruptedException
	 */
	public String verifySubMenusCount(String object, String data) throws InterruptedException {
		List<String> expectedSubMenus = new ArrayList<String>();
		String[] expectedSubMenusAsRole = data.split("\\|");
		WebElement mainMenu;

		try {
			// If isElementExists as per role then this flag is true.
			if (flagForMainMenu) {
				// This is when main menu has submenus. Add these submenus in the list
				if (!Strings.isNullOrEmpty(expectedSubMenusAsRole.toString()) && expectedSubMenusAsRole.length > 1) {
					String[] subMenu = expectedSubMenusAsRole[1].split(",");
					for (int i = 0; i < subMenu.length; i++) {
						expectedSubMenus.add(subMenu[i]);
					}
				}
				List<WebElement> mainMenuElements = getWebElements(object);
				if (mainMenuElements.size() > 0) {
					mainMenu = getWebElement(object, expectedSubMenusAsRole[0]);
					mainMenu.click();
					Thread.sleep(1000);
					List<WebElement> group = getWebElements("//*[@class='menu-toggle-list']/li");
					int uiSubmenusCount = group.size();
					int expectedSubmenusCount = expectedSubMenus.size();
					System.out.println(group.size());
					System.out.println(expectedSubMenus.size());
					if (uiSubmenusCount == expectedSubmenusCount) {
						logger.info(Constants.KEYWORD_PASS + " -- UI submenus are same as expected submenus for --"
								+ mainMenu);
						return Constants.KEYWORD_PASS;
					} else {
						logger.info(Constants.KEYWORD_FAIL + " --UI submenus are not same as expected submenus for --  "
								+ mainMenu);
						return Constants.KEYWORD_FAIL + " --UI submenus are not same as expected submenus for --  "
						+ mainMenu;
					}
				}
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception ex) {
			logger.error("Exception in getting submenus count {}", ex);
			Assert.fail("Exception in getting submenus count {}");
			return Constants.KEYWORD_FAIL + " --Exception in getting submenus count {}";
		}
	}

	/**
	 * Verify submenus are clickable and opening an expected page with proper url
	 * 
	 * @param object
	 *            main menu
	 * @param data
	 *            submenu display as per role
	 * @return pass or fail
	 */
	public String verifySubMenusWithUrls(String object, String data) {
		try {
			List<String> expectedSubMenus = new ArrayList<String>();
			String[] subMenus = object.split(",");

			// MainMenu is visible
			if (flagForMainMenu) {
				// This is when we have submenus under mainmenu
				if (data.contains("|")) {
					String[] expectedSubMenuList = data.split("\\|");
					if (!Strings.isNullOrEmpty(expectedSubMenuList.toString()) && expectedSubMenuList.length > 1) {
						String[] menus = expectedSubMenuList[1].split(",");
						for (int i = 0; i < menus.length; i++) {
							expectedSubMenus.add(menus[i]);
						}
					}

					List<WebElement> group = getWebElements("//*[@class='menu-toggle-list']/li");
					if (group.size() > 0) {
						List<WebElement> subMenusVisible = new ArrayList<WebElement>();
						List<String> expectedSubMenusList = new ArrayList<String>();
						subMenusVisible = group;

						for (int i = 0; i < expectedSubMenus.size(); i++) {
							boolean flag = false;
							String expectedSubMenu = expectedSubMenus.get(i).split(":")[0];
							String expectedUrl = null;
							if (expectedSubMenus.get(i).contains(":")) {
								expectedUrl = expectedSubMenus.get(i).split(":")[1];
							}
							for (int j = 0; j < subMenusVisible.size(); j++) {
								if (expectedSubMenu.equals(subMenusVisible.get(j).getText())) {
									logger.info("SubMenu is visible in ui : " + subMenusVisible.get(j).getText());
									flag = true;
									// Click on sublink and verify url
									String ele = subMenusVisible.get(j).getText().replaceAll("\\s", "");
									WebElement subLink = getWebElements(ele).get(0);
									if (subLink.getText().equalsIgnoreCase(expectedSubMenu)) {
										logger.info(Constants.KEYWORD_PASS + " -- Same sublink is avaiable on ui for --"
												+ subLink.getText());
										if (subLink != null && expectedSubMenus.get(i).contains(":")) {
											subLink.click();
											String actualUrl = driver.getCurrentUrl();
											if (!actualUrl.contains(expectedUrl)) {
												return Constants.KEYWORD_FAIL + " -- Could not find expected result "
														+ expectedSubMenus.get(i).split(":")[1] + " from expected url "
														+ expectedUrl;
											}
										}
									}
									subMenusVisible.remove(j);
									break;
								}
							}
							if (flag == false) {
								// means no element found in ui
								expectedSubMenusList.add(expectedSubMenu);
							}
						}
						// These submenus are not in an expected output and extra in ui
						for (int count = 0; count < subMenusVisible.size(); count++) {
							logger.info("SubMenu is visible in ui but not in expected output: "
									+ subMenusVisible.get(count).getText());
							System.out.println("SubMenu is visible in ui but not in expected output : "
									+ subMenusVisible.get(count).getText());
						}

						// These submenus are in an expected output and but not
						// visible on ui
						for (int count = 0; count < expectedSubMenusList.size(); count++) {
							logger.info("SubMenu is not visible in ui but expected in output : "
									+ expectedSubMenusList.get(count));
							System.out.println("SubMenu is not visible in ui but expected in output : "
									+ expectedSubMenusList.get(count));
						}
						// Reset mainMenu
						WebElement mainMenu = getWebElement(object, data);
						mainMenu.click();

						if (subMenusVisible.size() > 0 || expectedSubMenusList.size() > 0) {
							return Constants.KEYWORD_FAIL + "Submenus are not displayed  as per role basis";
						}

					}
				} else {
					logger.info("There are no submenus under the main menu which is expected");
					return Constants.KEYWORD_PASS;
				}
			} else {
				logger.info("Main menu is not displayed which is expected");
				return Constants.KEYWORD_PASS;
			}
			return Constants.KEYWORD_PASS;
		} catch (Exception ex) {
			logger.error("Exception in getting submenus urls {}", ex);
			Assert.fail("Exception in getting submenus urls {}");
			return Constants.KEYWORD_FAIL + "Exception in getting submenus urls {}";
		}
	}

	/**
	 * Insert role into database table
	 * 
	 * @param object
	 *            none
	 * @param data
	 *            none
	 * @return pass or fail
	 */
	public String insertRoleIntoTable(String object, String data) {
		String roleTables = saveAnyDataInHash.getValue("roleTable");
		String[] tablesList = roleTables.split(",");

		for (int i = 0; i < tablesList.length; i++) {
			if (tablesList[i].equalsIgnoreCase("system_roles")) {
				String insertQuery = "INSERT INTO system_roles(PERSONID,ORGID,S_ROLE,UNIQUEID,EFFDT,ENDDT,AUDIT_KEY,UPDATE_KEY)"
						+ "VALUES('" + saveAnyDataInHash.getValue("personId") + "','"
						+ saveAnyDataInHash.getValue("companyId") + "','" + saveAnyDataInHash.getValue("role")
						+ "',1,'04-Mar-2016','31-Dec-2099',0,0)";
				insertQuery = "hp." + insertQuery;
				System.out.println(insertQuery);

				try {
					dataSetup(null, insertQuery);
				} catch (SQLException e) {
					logger.error("Exception in executing query", e.getMessage());
					return Constants.KEYWORD_FAIL;
				}
			} else if (tablesList[i].equalsIgnoreCase("position_orgs")) {
				String insertQuery = "INSERT INTO position_orgs(POSITIONID,COMPANY, UNIQUEID, ORGID, POSITION_ORG_REL,EFFDT,ENDDT, APPROVAL_STAT, AUDIT_KEY,UPDATE_KEY) "
						+ "VALUES ('" + saveAnyDataInHash.getValue("personId") + "','"
						+ saveAnyDataInHash.getValue("companyId") + "','1','" + saveAnyDataInHash.getValue("companyId")
						+ "','" + saveAnyDataInHash.getValue("role") + "','01-Feb-2010','31-Dec-2099','F', 0,0)";
				insertQuery = "hp." + insertQuery;
				System.out.println(insertQuery);

				try {
					dataSetup(null, insertQuery);
				} catch (SQLException e) {
					logger.error("Exception in executing query", e.getMessage());
					return Constants.KEYWORD_FAIL;
				}

			} else if (tablesList[i].equalsIgnoreCase("v1_roles_tbl")) {
				String insertQuery = "INSERT INTO V1_ROLES_TBL(PF_CLIENT,COMPANY,PERSONID,S_ROLE,AUDIT_KEY,LOCATION,DEPTID,PAYROLL_GROUP) "
						+ "VALUES ('" + saveAnyDataInHash.getValue("pfclient") + "','"
						+ saveAnyDataInHash.getValue("companyId") + "','" + saveAnyDataInHash.getValue("personId")
						+ "','" + saveAnyDataInHash.getValue("role") + "','0','***','***','***')";
				insertQuery = "hp." + insertQuery;
				System.out.println(insertQuery);

				try {
					dataSetup(null, insertQuery);
				} catch (SQLException e) {
					logger.error("Exception in executing query", e.getMessage());
					return Constants.KEYWORD_FAIL;
				}
			}

			return Constants.KEYWORD_PASS;
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * Delete inserted role from the respective table
	 * 
	 * @param object
	 *            none
	 * @param data
	 *            none
	 * @return pass or fail
	 */
	public String deleteInsertedRole(String object, String data) {
		if (!(saveAnyDataInHash.getValue("role").equalsIgnoreCase("MTAG"))) {
			String roleTables = saveAnyDataInHash.getValue("roleTable");
			String[] tablesList = roleTables.split(",");

			for (int i = 0; i < tablesList.length; i++) {

				if (tablesList[i].equalsIgnoreCase("system_roles")) {
					String deleteQuery = "delete from system_roles where personid = '"
							+ saveAnyDataInHash.getValue("personId") + "' and orgid = '"
							+ saveAnyDataInHash.getValue("companyId") + "' and s_role = '"
							+ saveAnyDataInHash.getValue("role") + "'";
					deleteQuery = "hp." + deleteQuery;
					System.out.println(deleteQuery);

					try {
						dataSetup(null, deleteQuery);
					} catch (SQLException e) {
						logger.error("Exception in executing query", e.getMessage());
						return Constants.KEYWORD_FAIL;
					}
				} else if (tablesList[i].equalsIgnoreCase("position_orgs")) {
					String deleteQuery = "delete from position_orgs where positionid = '"
							+ saveAnyDataInHash.getValue("personId") + "' and orgid = '"
							+ saveAnyDataInHash.getValue("companyId") + "'" + "and position_org_rel = '"
							+ saveAnyDataInHash.getValue("role") + "'";
					deleteQuery = "hp." + deleteQuery;
					System.out.println(deleteQuery);

					try {
						dataSetup(null, deleteQuery);
					} catch (SQLException e) {
						logger.error("Exception in executing query", e.getMessage());
						return Constants.KEYWORD_FAIL;
					}

				} else if (tablesList[i].equalsIgnoreCase("v1_roles_tbl")) {
					String deleteQuery = "delete from v1_roles_tbl where personid = '"
							+ saveAnyDataInHash.getValue("personId") + "' and company = '"
							+ saveAnyDataInHash.getValue("companyId") + "'" + " and s_role = '"
							+ saveAnyDataInHash.getValue("role") + "' ";
					deleteQuery = "hp." + deleteQuery;
					System.out.println(deleteQuery);

					try {
						dataSetup(null, deleteQuery);
					} catch (SQLException e) {
						logger.error("Exception in executing query", e.getMessage());
						return Constants.KEYWORD_FAIL;
					}
				}

				return Constants.KEYWORD_PASS;
			}

		}
		return Constants.KEYWORD_PASS;
	}

	public String getList(String object, String data) throws NumberFormatException, InterruptedException {

		String locator = UIMap.getProperty(object.split(",")[0]);
		logger.debug("Check locator if_exist " + locator);
		String[] values;
		/*
		 * if(data.contains("hash")){ values = data.split(Constants.DATA_SPLIT); data =
		 * saveAnyDataInHash.getValue(values[1]); }
		 */
		WebElement element = getWebElement(object, data);

		String actual = "";
		String expected = data.trim();
		System.out.println(expected);

		if (element != null) {
			List<WebElement> id_elements = element.findElements(By.xpath(locator));
			System.out.println("No. Element:" + id_elements.size());
			for (int i = 0; i < id_elements.size(); i++) {
				System.out.println(id_elements.get(i).getText());
				if (id_elements.get(i).getText().equalsIgnoreCase(expected)) {
					Wait("", "5000");
					id_elements.get(i).click();
					break;
				}

			}

		}

		return Constants.KEYWORD_PASS;

	}

	public String scrollup(String object, String data) {
		logger.info("Scrolling down the side bar");
		try {
			e_driver = new EventFiringWebDriver(driver);
			e_driver.executeScript("scroll(" + data + ",0)");

		} catch (Exception e) {
			logger.error("Exception in scroll down : {}", e, e.getMessage());
			return Constants.KEYWORD_FAIL + " -- Not able to scroll";
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * Verify holiday with database query and writing date in Start date
	 * 
	 * @param object
	 *            element with Starting Date value
	 * @param data
	 *            with company
	 * @return pass or fail
	 */
	public String writeStartDate(String object, String data) throws ParseException, SQLException {
		String locator;

		int index = object.indexOf(",");
		if (index > 0) {
			locator = UIMap.getProperty(object.substring(0, index));
		} else {
			locator = UIMap.getProperty(object);
		}
		String[] datanames = data.split(",");

		String holidayforcompany = saveAnyDataInHash.getValue(datanames[0]);
		String holidayforemplid = saveAnyDataInHash.getValue(datanames[1]);
		logger.info("Writing in text box:: Object is " + locator + " Data is " + data);

		int i = 0, j = 0;
		String format1 = "MM/dd/YYYY";
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format1);
		same_date = getRandomInt(1, 365).toString();
		c.add(Calendar.DATE, new Integer(same_date));
		Requested_Date = sdf.format(c.getTime());
		// Requested_Date="01/29/2018";
		Date requestdate = c.getTime();
		String format = "EE/MM/dd/YYYY";
		WebElement element = getWebElement(object, data);
		// WebElement element = getWebElement(object, Requested_Date);
		if (element != null) {
			while (!(isDayAWeekday(requestdate, format))) {
				c.add(Calendar.DATE, 1);
				Requested_Date = sdf.format(c.getTime());
				requestdate = c.getTime();

			}

			while (((isDayAHoliday(Requested_Date, holidayforcompany, format1)))
					|| ((isDayAlreadyTaken(Requested_Date, holidayforemplid, format1)))) {
				c.add(Calendar.DATE, 1);

				Requested_Date = sdf.format(c.getTime());
				requestdate = c.getTime();
				while (!(isDayAWeekday(requestdate, format))) {
					c.add(Calendar.DATE, 1);
					Requested_Date = sdf.format(c.getTime());
					requestdate = c.getTime();

				}

			}

			element.clear();
			element.sendKeys(Requested_Date);
			System.out.println(Requested_Date);
			return Constants.KEYWORD_PASS;

		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- Unable to write  -- Element " + " Not Found");
			return Constants.KEYWORD_FAIL + " -- Unable to write  -- Element " + " Not Found";
		}
	}

	/**
	 * Verify holiday with database query and writing date in End date
	 * 
	 * @param object
	 *            element with End Date value
	 * @param data
	 *            with company
	 * @return pass or fail
	 */
	public String writeEndDate(String object, String data) throws ParseException {
		String locator;
		int index = object.indexOf(",");
		if (index > 0) {
			locator = UIMap.getProperty(object.substring(0, index));
		} else {
			locator = UIMap.getProperty(object);
		}
		logger.info("Writing in text box:: Object is " + locator + " Data is " + data);
		WebElement element = getWebElement(object, data);
		if (element != null) {
			element.clear();
			element.sendKeys(Requested_Date);
			return Constants.KEYWORD_PASS;
		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- Unable to write  -- Element " + locator + " Not Found");
			return Constants.KEYWORD_FAIL + " -- Unable to write  -- Element " + locator + " Not Found";
		}

	}

	/**
	 * Verify if day is week day
	 * 
	 * @return boolean
	 */
	public boolean isDayAWeekday(Date date, String format) {

		boolean isWeekday = true;

		try {
			if (date.toString().toLowerCase().contains("sat") || date.toString().toLowerCase().contains("sun")) {
				isWeekday = false;
			}

		} catch (Exception e) {
			logger.error("Returning false because found exception {}" + e.getMessage());

			isWeekday = true;
		}

		return isWeekday;
	}

	/**
	 * Verify if day is Holiday
	 * 
	 * @return boolean
	 */
	public boolean isDayAHoliday(String data1, String data, String format) throws SQLException {

		boolean isHoliday = false;

		String da = null;
		List dbResult = dbutil.executeSelectStmt(
				"sql|hp.SELECT TO_CHAR(hd1.holiday,'mm/dd/yyyy') FROM   PS_HOLIDAY_DATE@hrdb_seeker HD1 WHERE  HD1.HOLIDAY_SCHEDULE = "
						+ "\'" + data + "\'  ORDER BY HOLIDAY");
		try {
			for (int k = 0; k < dbResult.size(); k++)

			{
				da = dbResult.get(k).toString().trim().replace("[", "").replace("]", "");
				if (data1.trim().contains(da))

				{

					isHoliday = true;
				}

			}
		} catch (Exception e) {
			logger.error("Returning false because found exception {}" + e.getMessage());

			isHoliday = false;
		}

		return isHoliday;
	}

	/**
	 * Verify if day is Holiday
	 * 
	 * @return boolean
	 */
	public boolean isDayAlreadyTaken(String data1, String data, String format) throws SQLException {

		boolean isHoliday = false;

		String da = null;
		List dbResult = dbutil.executeSelectStmt(
				"sql|hr.select distinct(to_char(Leave_dt,'mm/dd/yyyy') ) from PS_T2_PER_LV_RQ_DT a ,PS_T2_PER_LV_RQST b where a.Emplid= "
						+ "\'" + data + "\'  and  a.emplid=b.emplid and b.T2_leave_status<>'R'");
		try {
			for (int k = 0; k < dbResult.size(); k++)

			{
				da = dbResult.get(k).toString().trim().replace("[", "").replace("]", "");
				if (data1.trim().contains(da))

				{

					isHoliday = true;
				}

			}
		} catch (Exception e) {
			logger.error("Returning false because found exception {}" + e.getMessage());

			isHoliday = false;
		}

		return isHoliday;
	}

	/**
	 * Verify timeoff with employeeid and requested date
	 * 
	 * @param object
	 *            element with gridview objects of timeoff request
	 * @param data
	 *            with employee
	 * @return pass or fail
	 */
	public String VerifyTimeoffAccept(String object, String data) throws InterruptedException, SQLException {

		String locator = UIMap.getProperty(object.split(",")[0]);
		logger.debug("Check locator if_exist " + locator);
		WebElement element = getWebElement(object, data);
		try {
			if (element != null) {
				List<WebElement> id_elements = element.findElements(By.xpath(locator));
				boolean flag = false;
				for (int i = 0; i < id_elements.size(); i++) {
					String path = ".//td/div";
					List<WebElement> findElement = id_elements.get(i).findElements(By.xpath(path));
					int size = findElement.size();
					if ((findElement.get(1).getText().contains(saveAnyDataInHash.getValue(data)))
							&& (findElement.get(2).getText().contains(Requested_Date))
							&& (findElement.get(3).getText().contains(Requested_Date))) {
						flag = true;
						Wait("", "5000");
						findElement.get(0).click();
						break;
					}

				}
			}

			else {
				logger.debug(Constants.KEYWORD_FAIL + "--Ivalid  Data");
				return Constants.KEYWORD_FAIL + " -- Invalid  Data ";

			}
		} catch (Exception e) {
			logger.error("Returning false because found exception {}" + e.getMessage());
			return Constants.KEYWORD_FAIL + " -- Invalid  Data";

		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * Verify Holiday description and holiday date with query database
	 * 
	 * @param object
	 *            element with Holidays gridview
	 * @param data
	 *            with company and year
	 * @return pass or fail
	 */

	public String writeOnHidenElements(String object, String data) {
		try {

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript(
					"document.getElementsByClassName(\"x-form-field x-form-text x-form-textarea\")[1].value=\" test \";");
		} catch (Exception e) {
			logger.error("Exception in  : {}", e, e.getMessage());
			return Constants.KEYWORD_FAIL + " -- Not able to write";
		}
		return Constants.KEYWORD_PASS;

	}

	/**
	 * Verify timeoff for selecting decline with employeeid and requested date
	 * 
	 * @param object
	 *            element with gridview objects of timeoff request
	 * @param data
	 *            with employee
	 * @return pass or fail
	 */
	public String verifyTimeoffDecline(String object, String data) throws InterruptedException, SQLException {

		String locator = UIMap.getProperty(object.split(",")[0]);
		logger.debug("Check locator if_exist " + locator);
		WebElement element = getWebElement(object, data);
		try {
			if (element != null) {
				List<WebElement> id_elements = element.findElements(By.xpath(locator));
				rowSIZE = id_elements.size();
				boolean flag = false;
				for (int i = 0; i < id_elements.size(); i++) {
					String path = ".//td/div";
					List<WebElement> findElement = id_elements.get(i).findElements(By.xpath(path));
					int size = findElement.size();
					if ((findElement.get(1).getText().contains(saveAnyDataInHash.getValue(data)))
							&& (findElement.get(2).getText().contains(Requested_Date))
							&& (findElement.get(3).getText().contains(Requested_Date))) {
						Wait("", "5000");
						flag = true;

					}
					if (flag == true) {
						System.out.println("good8");
						findElement.get(7).click();
						break;
					}
				}
			}

			else {
				logger.debug(Constants.KEYWORD_FAIL + "--Ivalid  Data");
				return Constants.KEYWORD_FAIL + " -- Invalid  Data ";

			}
		} catch (Exception e) {
			logger.error("Returning false because found exception {}" + e.getMessage());
			return Constants.KEYWORD_FAIL + " -- Invalid  Data";
		}
		return Constants.KEYWORD_PASS;

	}

	public String writeInInput_random(String object, String data) {
		String locator;
		int index = object.indexOf(",");
		if (index > 0) {
			locator = UIMap.getProperty(object.substring(0, index));
		} else {
			locator = UIMap.getProperty(object);
		}
		rand = getRandomInt(1, 9).toString();
		
		logger.info("Writing in text box:: Object is " + locator + " Data is " + data);
		WebElement element = getWebElement(object, data);
		if (element != null) {
			element.clear();
			element.sendKeys(data + rand);
			return Constants.KEYWORD_PASS;
		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- Unable to write  -- Element " + locator + " Not Found");
			return Constants.KEYWORD_FAIL + " -- Unable to write  -- Element " + locator + " Not Found";
		}

	}
	
	
	
	
	//-----------------------------------
	public String generate_String(int stringlength)
	{
		String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String randonName=null;
		StringBuilder str=new StringBuilder();
		Random random=new Random();
		
		while(str.length()<stringlength)
		{
			int index=(int) (random.nextFloat()*alphabet.length());
			str.append(alphabet.charAt(index));
		}
		return randonName=str.toString();
		
	}

	
	
	
	
	
	public String writeInInput_randomString(String object,String data) 
	{
		String locator;
		int index = object.indexOf(",");
		if (index > 0) {
			locator = UIMap.getProperty(object.substring(0, index));
		} else {
			locator = UIMap.getProperty(object);
		}
		
		
		
		
		rand = generate_String(10);
		
		logger.info("Writing in text box:: Object is " + locator + " Data is " + data);
		WebElement element = getWebElement(object, data);
		if (element != null) {
			element.clear();
			element.sendKeys(data + rand);
			return Constants.KEYWORD_PASS;
		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- Unable to write  -- Element " + locator + " Not Found");
			return Constants.KEYWORD_FAIL + " -- Unable to write  -- Element " + locator + " Not Found";
		}

	}
	
	
	
	public String generate_Phone(int lengathofmobilenumber)
	{
		String numbers="0123456789";
		String randomPhoneNumber=null;
		StringBuilder buildnumber=new StringBuilder();
		Random random=new Random();
		
		while(buildnumber.length()<lengathofmobilenumber)
		{
			int index=(int) (random.nextFloat()*numbers.length());
			buildnumber.append(numbers.charAt(index));
		}
		String randnum=buildnumber.toString();
		randomPhoneNumber=("9"+ randnum);
		return randomPhoneNumber=buildnumber.toString();
		
	}

	
	
	
	public String writeInInput_random_MobileNumber(String object, String data) 
	{
		String locator;
		int index = object.indexOf(",");
		if (index > 0) {
			locator = UIMap.getProperty(object.substring(0, index));
		} else {
			locator = UIMap.getProperty(object);
		}
		
		
		
		
		rand = generate_Phone(10);
		
		logger.info("Writing in text box:: Object is " + locator + " Data is " + data);
		WebElement element = getWebElement(object, data);
		if (element != null) {
			element.clear();
			element.sendKeys(data + rand);
			return Constants.KEYWORD_PASS;
		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- Unable to write  -- Element " + locator + " Not Found");
			return Constants.KEYWORD_FAIL + " -- Unable to write  -- Element " + locator + " Not Found";
		}

	}
	
	public String generate_Email(int EmailidLengath)
	{
		String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String randonEmail=null;
		StringBuilder buildrandomEmailid=new StringBuilder();
		Random random=new Random();
		
		while(buildrandomEmailid.length()<EmailidLengath)
		{
			int index=(int) (random.nextFloat()*alphabet.length());
			buildrandomEmailid.append(alphabet.charAt(index));
		}
		String ranemailid=buildrandomEmailid.toString();
		 randonEmail=(ranemailid+"@trinet.com");
		return randonEmail;
		
	}

	
	public String writeInInput_random_Email(String object, String data) 
	{
		String locator;
		int index = object.indexOf(",");
		if (index > 0) {
			locator = UIMap.getProperty(object.substring(0, index));
		} else {
			locator = UIMap.getProperty(object);
		}
		
		
		
		
		rand = generate_Email(10);
		
		logger.info("Writing in text box:: Object is " + locator + " Data is " + data);
		WebElement element = getWebElement(object, data);
		if (element != null) {
			element.clear();
			element.sendKeys(data + rand);
			return Constants.KEYWORD_PASS;
		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- Unable to write  -- Element " + locator + " Not Found");
			return Constants.KEYWORD_FAIL + " -- Unable to write  -- Element " + locator + " Not Found";
		}

	}
	

	/**
	 * Verify timeoff status with query using employeeid and requested date
	 * 
	 * @param object
	 *            element with gridview objects of timeoff request
	 * @param data
	 *            with employee
	 * @return pass or fail
	 */
	public String VerifyTimeoffStatus(String object, String data) throws InterruptedException, SQLException {

		String locator = UIMap.getProperty(object.split(",")[0]);
		logger.debug("Check locator if_exist " + locator);
		WebElement element = getWebElement(object, data);
		try {
			if (element != null) {
				List<WebElement> id_elements = element.findElements(By.xpath(locator));

				String expected = DBUtil.getDBData(
						"sql|hp.select case when T2_LEAVE_STATUS in('R')then'Not Approved' when T2_LEAVE_STATUS in('A')then'Approved'else T2_LEAVE_STATUS end as T2_LEAVE_STATUS from PS_T2_PER_LV_RQST@hrdb_seeker  a where a.Emplid="
								+ "\'" + saveAnyDataInHash.getValue(data)
								+ "\' and a.LAST_UPDATE_DTTM  in (select max(b.LAST_UPDATE_DTTM) from PS_T2_PER_LV_RQST@hrdb_seeker  b where a.emplid=b.emplid)");

				boolean flag = false;
				for (int i = 0; i < id_elements.size(); i++) {
					String path = ".//td/div";
					List<WebElement> findElement = id_elements.get(i).findElements(By.xpath(path));

					int size = findElement.size();
					for (int j = 0; j < size; j++) {
						if ((findElement.get(6).getText().equalsIgnoreCase(expected))
								&& (findElement.get(2).getText().equalsIgnoreCase(Requested_Date))
								&& (findElement.get(3).getText().equalsIgnoreCase(Requested_Date))) {
							Wait("", "5000");
							flag = true;

						}
					}

				}

				if (flag == true) {
					return Constants.KEYWORD_PASS;

				}

				return Constants.KEYWORD_FAIL;

			}

			else {
				logger.debug(Constants.KEYWORD_FAIL);
				return Constants.KEYWORD_FAIL + " -- Invalid  Data ";
			}
		} catch (Exception e) {
			logger.error("Returning false because found exception {}" + e.getMessage());
			return Constants.KEYWORD_FAIL + " -- Invalid  Data";
		}
		// return Constants.KEYWORD_PASS;
	}

	public Integer getRandomInt(int min, int max) {
		return (int) (Math.floor(Math.random() * (max - min + 1)) + min);
	}

	public String clickOnHidenElements(String object, String data) {
		WebElement element = getWebElement(object, data);
		if (element != null) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
		} else {

			return Constants.KEYWORD_FAIL + " --  unable to click on element";
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * Verify OrgChart status of name and businesstitle with query using employeeid
	 * 
	 * @param object
	 *            element with gridview objects of direct reports
	 * @param data
	 *            with employeeid
	 * @return pass or fail
	 */

	public String verifyOrgChart(String object, String data) throws InterruptedException, SQLException {
		String locator = UIMap.getProperty(object);
		logger.debug("Check locator if_exist " + locator);
		Map<String, String> hashtableUI = new Hashtable<String, String>();
		Map<String, String> hashtableDB = new Hashtable<String, String>();
		boolean flag = false;
		String employee = saveAnyDataInHash.getValue(data);

		WebElement element = getWebElement(object, data);
		if (element != null) {
			List<WebElement> id_elements = element.findElements(By.xpath(locator));
			try {
				for (int i = 0; i < id_elements.size(); i++) {
					String EmployeeName = "./h3[contains(@class,'employee-name')]";
					String BusinessTitle = "(./span[@class='ng-binding'])[1]";

					List<WebElement> DescrEmployeename = id_elements.get(i).findElements(By.xpath(EmployeeName));
					String UIemployeeName = DescrEmployeename.get(0).getText();
					List<WebElement> listofbusiness_title = id_elements.get(i).findElements(By.xpath(BusinessTitle));
					String UIempBusinessTitle = listofbusiness_title.get(0).getText();

					String DBexpectedBusinessTitle = DBUtil.getDBData(
							"sql|hr.select distinct(business_title) from PS_PER_ORG_ASGN X where emplid in(select emplid from(SELECT rownum OUR_ROWNUM,r.* from  "
									+ " (select distinct(A.emplid)FROM PS_JOB A,  PS_NAMES B,  PS_PER_ORG_ASGN F WHERE A.emplid not  in("
									+ "\'" + employee + "\')and A.EMPLID = B.EMPLID  AND A.SUPERVISOR_ID=" + "\'"
									+ employee + "\' "
									+ " AND A.EMPLID = F.EMPLID AND A.EMPL_RCD = F.EMPL_RCD   AND B.EFFDT = (SELECT MAX (EFFDT)FROM  PS_NAMES C WHERE B.EMPLID = C.EMPLID) AND  "
									+ " A.EFFDT = (SELECT MAX (EFFDT)FROM PS_JOB C1 WHERE A.EMPLID = C1.EMPLID)AND A.EMPL_STATUS NOT IN ('T','D')AND A.EFFDT =   "
									+ " (SELECT MAX(A_ED.EFFDT) FROM PS_JOB A_ED WHERE A.EMPLID = A_ED.EMPLID AND A.EMPL_RCD = A_ED.EMPL_RCD AND A.EFFDT = A_ED.EFFDT  "
									+ " and B.NAME_TYPE='PRI')AND   A.EFFSEQ = (SELECT MAX(A_ES.EFFSEQ) FROM PS_JOB A_ES WHERE A.EMPLID = A_ES.EMPLID AND A.EMPL_RCD =  "
									+ " A_ES.EMPL_RCD AND A.EFFDT = A_ES.EFFDT  and B.NAME_TYPE='PRI'))r )where OUR_ROWNUM ="
									+ "\'" + (i + 1)
									+ "\') and X.cmpny_seniority_dt in (select max(Y.cmpny_seniority_dt) from PS_PER_ORG_ASGN Y where X.emplid=Y.emplid)");

					String DBexpectedEmployeeName = DBUtil.getDBData(
							"sql|hr.select  case when instr(name,' ')=0 then replace(name,SUBSTR(name,0,instr(name,',')),'')||' '||SUBSTR(name,0,instr(name,',')-1) else replace(SUBSTR(name,0,instr(name,' ')+1),SUBSTR(name,0,instr(name,',')),'')||' '||SUBSTR(name,0,instr(name,',')-1)end as name  from ps_names X  where emplid in( "
									+ " select emplid from(SELECT rownum OUR_ROWNUM, r.* from      (select distinct(A.emplid) FROM PS_JOB A,  PS_NAMES B,  PS_PER_ORG_ASGN F "
									+ " WHERE A.emplid not  in(" + "\'" + employee + "\')and A.EMPLID = B.EMPLID "
									+ " AND A.SUPERVISOR_ID=" + "\'" + employee
									+ "\' AND A.EMPLID = F.EMPLID AND A.EMPL_RCD = F.EMPL_RCD   AND B.EFFDT = (SELECT MAX (EFFDT)FROM  "
									+ " PS_NAMES C WHERE B.EMPLID = C.EMPLID) AND A.EFFDT = (SELECT MAX (EFFDT)FROM PS_JOB C1 WHERE A.EMPLID = C1.EMPLID) "
									+ " AND A.EMPL_STATUS NOT IN ('T','D')AND A.EFFDT =   (SELECT MAX(A_ED.EFFDT) FROM PS_JOB A_ED WHERE A.EMPLID = A_ED.EMPLID "
									+ " AND A.EMPL_RCD = A_ED.EMPL_RCD AND A.EFFDT = A_ED.EFFDT and B.NAME_TYPE='PRI')AND   A.EFFSEQ = (SELECT MAX(A_ES.EFFSEQ) FROM PS_JOB A_ES WHERE A.EMPLID = A_ES.EMPLID "
									+ " AND A.EMPL_RCD = A_ES.EMPL_RCD AND A.EFFDT = A_ES.EFFDT  and B.NAME_TYPE='PRI'))r )where OUR_ROWNUM ="
									+ "\'" + (i + 1) + "\') and effdt in (select max(effdt) from ps_names Y where "
									+ " X.emplid=Y.emplid and X.name_type='PRI')");

					hashtableUI.put(UIemployeeName.trim(), UIempBusinessTitle);
					hashtableDB.put(DBexpectedEmployeeName.replace(".", "").trim(), DBexpectedBusinessTitle);

					if (hashtableUI.equals(hashtableDB)) {
						flag = true;
						logger.info("Actual Employee name and Businesstitle in UI are matched with DB");
					}

				}
			} catch (Exception e) {
				logger.info(e.getMessage());
				return Constants.KEYWORD_FAIL + " -- Not able to validate org chart  ";
			}

		}
		if (flag == true)
			return Constants.KEYWORD_PASS;
		else
			return Constants.KEYWORD_FAIL;
	}

	/**
	 * Verify OrgChart status of name and no of direct reports with query from
	 * (directreports obtained from UI )
	 * 
	 * @param object
	 *            element with gridview objects of direct reports
	 * @param data
	 *            blank
	 * @return pass or fail
	 */
	public String verifyOrgDirectReports(String object, String data) throws InterruptedException, SQLException {
		String locator = UIMap.getProperty(object);
		logger.debug("Check locator if_exist " + locator);
		String employee = saveAnyDataInHash.getValue(data);
		ArrayList<String> expected_employeename = new ArrayList<String>();
		ArrayList<String> expected_directreports = new ArrayList<String>();
		Map<String, String> hashtableUI = new Hashtable<String, String>();
		Map<String, String> hashtableDB = new Hashtable<String, String>();
		boolean flag = false;
		WebElement element = getWebElement(object, data);
		if (element != null) {
			List<WebElement> id_elements = element.findElements(By.xpath(locator));
			try {
				for (int i = 0; i < id_elements.size(); i++) {
					String employee_name = "./preceding-sibling::h3";
					String business_title = "./preceding-sibling::span[@class='ng-binding'][2]";
					List<WebElement> descr_employeename = id_elements.get(i).findElements(By.xpath(employee_name));
					String UIemployeeName = descr_employeename.get(0).getText();
					String UIempDirectReports = id_elements.get(i).getText();

					String DBexpectedTotalDirectReports = DBUtil.getDBData(
							"sql|hr.select count(r.emplid) from( select a.* FROM PS_JOB A, PS_NAMES B, PS_DEPT_TBL D, PS_LOCATION_TBL E, PS_PER_ORG_ASGN F WHERE  A.EMPLID = B.EMPLID  AND SUPERVISOR_ID in(select supervisor_id from "
									+ " (SELECT rownum OUR_ROWNUM,result.* from(select distinct( A.supervisor_id) FROM PS_JOB A,PS_NAMES B,  PS_PER_ORG_ASGN F WHERE  A.EMPLID = B.EMPLID  AND SUPERVISOR_ID in(select r.emplid from "
									+ " (select a.* FROM PS_JOB A, PS_NAMES B,  PS_PER_ORG_ASGN F WHERE A.emplid not in("
									+ "\'" + employee + "\')and A.EMPLID = B.EMPLID  AND SUPERVISOR_ID=" + "\'"
									+ employee + "\' AND A.EMPLID = F.EMPLID  AND A.EMPL_RCD = F.EMPL_RCD "
									+ " AND B.EFFDT = (SELECT MAX (EFFDT)FROM PS_NAMES C WHERE B.EMPLID = C.EMPLID) AND A.EFFDT = (SELECT MAX (EFFDT)FROM PS_JOB C1 WHERE A.EMPLID = C1.EMPLID)AND EMPL_STATUS  NOT IN ('T','D') AND A.EFFDT= "
									+ " (SELECT MAX(A_ED.EFFDT) FROM PS_JOB A_ED WHERE A.EMPLID = A_ED.EMPLID AND A.EMPL_RCD = A_ED.EMPL_RCD AND A.EFFDT = A_ED.EFFDT and B.NAME_TYPE='PRI')AND  A.EFFSEQ = (SELECT MAX(A_ES.EFFSEQ) FROM PS_JOB A_ES WHERE A.EMPLID "
									+ " = A_ES.EMPLID AND A.EMPL_RCD = A_ES.EMPL_RCD AND A.EFFDT = A_ES.EFFDT and B.NAME_TYPE='PRI'))r)  AND A.EMPLID = F.EMPLID  AND A.EMPL_RCD = F.EMPL_RCD  AND B.EFFDT = (SELECT MAX (EFFDT)FROM PS_NAMES C WHERE B.EMPLID = C.EMPLID) AND A.EFFDT = "
									+ " (SELECT MAX (EFFDT)  FROM PS_JOB C1 WHERE A.EMPLID = C1.EMPLID)AND EMPL_STATUS NOT IN ('T','D') AND A.EFFDT = (SELECT MAX(A_ED.EFFDT) FROM PS_JOB A_ED WHERE A.EMPLID = A_ED.EMPLID AND  A.EMPL_RCD = A_ED.EMPL_RCD AND A.EFFDT = "
									+ " A_ED.EFFDT and B.NAME_TYPE='PRI')AND A.EFFSEQ = (SELECT MAX(A_ES.EFFSEQ) FROM PS_JOB A_ES WHERE A.EMPLID = A_ES.EMPLID AND A.EMPL_RCD = A_ES.EMPL_RCD AND A.EFFDT = A_ES.EFFDT and B.NAME_TYPE='PRI'))result) where our_ROWNUM="
									+ "\'" + (i + 1) + "\')AND "
									+ " B.EFFDT = (SELECT MAX (EFFDT) FROM PS_NAMES C WHERE B.EMPLID = C.EMPLID) AND A.EFFDT = (SELECT MAX (EFFDT)FROM PS_JOB C1 WHERE A.EMPLID = C1.EMPLID)AND D.DEPTID = A.DEPTID AND D.EFFDT = "
									+ " (SELECT MAX(EFFDT) FROM PS_DEPT_TBL G WHERE G.DEPTID = D.DEPTID)AND E.LOCATION = A.LOCATION  AND E.EFFDT = (SELECT MAX(EFFDT) FROM PS_LOCATION_TBL H WHERE H.LOCATION = E.LOCATION)AND A.EMPLID = F.EMPLID AND A.EMPL_RCD = F.EMPL_RCD AND EMPL_STATUS NOT IN ('T','D') "
									+ " AND A.EFFDT = (SELECT MAX(A_ED.EFFDT) FROM PS_JOB A_ED WHERE A.EMPLID = A_ED.EMPLID AND A.EMPL_RCD = A_ED.EMPL_RCD AND A.EFFDT = A_ED.EFFDT and B.NAME_TYPE='PRI')AND "
									+ " A.EFFSEQ = (SELECT MAX(A_ES.EFFSEQ) FROM PS_JOB A_ES WHERE A.EMPLID = A_ES.EMPLID AND A.EMPL_RCD = A_ES.EMPL_RCD AND A.EFFDT = A_ES.EFFDT and B.NAME_TYPE='PRI') )r ");

					String DBexpectedEmployeeName = DBUtil.getDBData(
							"sql|hr.select case when instr(name,' ')=0 then replace(name,SUBSTR(name,0,instr(name,',')),'')||' '||SUBSTR(name,0,instr(name,',')-1) else replace(SUBSTR(name,0,instr(name,' ')+1),SUBSTR(name,0,instr(name,',')),'')||' '||SUBSTR(name,0,instr(name,',')-1)end as name from ps_names X where emplid in (select supervisor_id from(select ROWNUM OUR_ROWNUM,result.supervisor_id "
									+ " from(select distinct(A.supervisor_id) FROM PS_JOB A,PS_NAMES B,  PS_PER_ORG_ASGN F WHERE  A.EMPLID = B.EMPLID  AND SUPERVISOR_ID in(select r.emplid from (select a.* FROM PS_JOB A,PS_NAMES B,  PS_PER_ORG_ASGN F WHERE "
									+ " A.emplid not in(" + "\'" + employee
									+ "\')and A.EMPLID = B.EMPLID  AND SUPERVISOR_ID=" + "\'" + employee
									+ "\' AND A.EMPLID = F.EMPLID AND A.EMPL_RCD = F.EMPL_RCD AND B.EFFDT = (SELECT MAX (EFFDT) FROM PS_NAMES C WHERE B.EMPLID = C.EMPLID) "
									+ " AND A.EFFDT = (SELECT MAX (EFFDT)FROM PS_JOB C1 WHERE A.EMPLID = C1.EMPLID)AND EMPL_STATUS NOT IN ('T','D') AND A.EFFDT = (SELECT MAX(A_ED.EFFDT) FROM PS_JOB A_ED WHERE A.EMPLID = A_ED.EMPLID AND A.EMPL_RCD = A_ED.EMPL_RCD "
									+ " AND A.EFFDT = A_ED.EFFDT and B.NAME_TYPE='PRI')AND A.EFFSEQ = (SELECT MAX(A_ES.EFFSEQ) FROM PS_JOB A_ES  WHERE A.EMPLID = A_ES.EMPLID AND A.EMPL_RCD = A_ES.EMPL_RCD AND A.EFFDT = A_ES.EFFDT and B.NAME_TYPE='PRI'))r) "
									+ " AND A.EMPLID = F.EMPLID AND A.EMPL_RCD = F.EMPL_RCD AND B.EFFDT = (SELECT MAX (EFFDT)FROM PS_NAMES C WHERE B.EMPLID = C.EMPLID) AND A.EFFDT = (SELECT MAX (EFFDT)FROM PS_JOB C1 WHERE A.EMPLID = C1.EMPLID)AND EMPL_STATUS NOT IN ('T','D') "
									+ " AND A.EFFDT = (SELECT MAX(A_ED.EFFDT) FROM PS_JOB A_ED WHERE A.EMPLID = A_ED.EMPLID AND A.EMPL_RCD = A_ED.EMPL_RCD AND A.EFFDT = A_ED.EFFDT and B.NAME_TYPE='PRI')AND A.EFFSEQ = (SELECT MAX(A_ES.EFFSEQ) FROM PS_JOB A_ES WHERE A.EMPLID "
									+ " = A_ES.EMPLID AND A.EMPL_RCD = A_ES.EMPL_RCD AND A.EFFDT = A_ES.EFFDT and B.NAME_TYPE='PRI'))result )where OUR_ROWNUM = "
									+ "\'" + (i + 1)
									+ "\') and effdt in (select max(effdt) from ps_names Y where X.emplid =Y.emplid)");

					hashtableUI.put(UIemployeeName,
							UIempDirectReports.replace("Direct", "").replace("Reports", "").trim());
					hashtableDB.put(DBexpectedEmployeeName.replace(".", "").trim(),
							DBexpectedTotalDirectReports.trim());

					if (hashtableUI.equals(hashtableDB)) {
						flag = true;
						logger.info("Actual Employee name and no of Direct reports in UI are matched with DB");
					}
				}
			} catch (Exception e) {
				logger.info(e.getMessage());
				return Constants.KEYWORD_FAIL + " -- Not able to validate org chart  ";
			}

		}
		if (flag == true)
			return Constants.KEYWORD_PASS;
		else
			return Constants.KEYWORD_FAIL;
	}

	/**
	 * This method is used to untoggle the checkbox based on the given data
	 * associated with checkbox
	 * 
	 * @param object
	 *            Webelement properties. Parameters are [webElementToggleButton,
	 *            webElementText, continueButtonWebElement]
	 * @param data
	 *            Expected result. Parameters are [expectedText, mandatory/optional]
	 * @return Pass or Fail
	 */
	public String untoggleBasedOnData(String object, String data) {
		String[] webElementProperties = object.split(",");
		String[] dataValues = data.split(",");
		String isElementExists = isElementExist(webElementProperties[1], "TRUE");
		// Checking if the toggle button exists. If it does not exists, we will fail the
		// test case.
		if (isElementExists.equals("Pass")) {
			String toggleButtonTextExistance = verifyText(webElementProperties[0], dataValues[0]);
			// If the toggle button exists, we are checking the value of text beside the
			// toggle button
			if (toggleButtonTextExistance.equals("Pass")) {
				logger.info("Toggle button is ON. Turning it OFF");
				if (toggleCheckbox(webElementProperties[1], "").equals("Pass")) {
					logger.info("Toggled the checkbox to OFF");
					// If the datavalue is mandatory, we get popup window to continue.
					if (dataValues[1].equals("mandatory")) {
						// closing the popup
						String popUpCloseReturnValue = clickButton(webElementProperties[2], "");
						if (popUpCloseReturnValue.equals("Pass")) {
							// Popup closed successfully
							return Constants.KEYWORD_PASS;
						} else {
							logger.info("Unable to close popup " + UIMap.getProperty(webElementProperties[2]));
							return Constants.KEYWORD_FAIL;

						}
					}
					return Constants.KEYWORD_PASS;
				} else {
					logger.error("Unable to toggle checkbox " + UIMap.getProperty(webElementProperties[1]) + " to off");
					return Constants.KEYWORD_FAIL;
				}
			} else {
				logger.info("Toggle button is already off. No needed to turn it OFF again");
				return Constants.KEYWORD_PASS;
			}
		} else {
			logger.error("Toggle element " + UIMap.getProperty(webElementProperties[1]) + " not found");
			return Constants.KEYWORD_FAIL;
		}
	}

	/**
	 * This method is used to toggle one checkbox based on other checkbox data
	 * 
	 * @param object
	 *            Webelement properties. Parameters are
	 *            [firstWebElementText,secondWebElementText,firstWebElementToggleButton,secondWebElementToggleButton]
	 * @param data
	 *            Expected Results. Parameters are [firstToggleButtonExpectedValue,
	 *            secondToggleButtonExpectedValue]
	 * @return Pass or Fail
	 */

	public String untoggleOneCheckBoxBasedOnOtherCheckbox(String object, String data) {
		String[] webElementProperties = object.split(",");
		String[] webElementsData = data.split(",");
		/*
		 * Check the value of Beta login text. If it is equals to "YES"(it means beta
		 * toggle button is ON), then turn toggle Default view to OFF(value will be
		 * employee) and then turn toggle Beta Button to OFF(value will be "NO")
		 */
		if (verifyText(webElementProperties[0], webElementsData[0]).equals("Pass")) {
			logger.info("Beta toggle button is already ON");
			if (verifyText(webElementProperties[1], webElementsData[1]).equals("Pass")) {
				logger.info("Default View Toggle button is already ON");
				if (toggleCheckbox(webElementProperties[3], "").equals("Pass")) {
					logger.info("Default view Toggle button is turned to OFF");
					if (toggleCheckbox(webElementProperties[2], "").equals("Pass")) {
						logger.info("Beta toggle button is turned to OFF");
						return Constants.KEYWORD_PASS;
					} else {
						logger.error("Beta toggle button failed to turn OFF");
						return Constants.KEYWORD_FAIL;
					}
				} else {
					logger.error("Default view button failed to turn OFF");
					return Constants.KEYWORD_FAIL;
				}
			} else {
				logger.info("Default view is already Set to OFF. We have to turn OFF Beta login toggle button");
				if (toggleCheckbox(webElementProperties[2], "").equals("Pass")) {
					logger.info("Beta login toggle button is turned OFF");
					return Constants.KEYWORD_PASS;
				} else {
					logger.error("Beta login toggle button cannot be turned to OFF");
					return Constants.KEYWORD_FAIL;
				}
			}
		} else {
			// If the beta login button is turned to OFF, turn it ON and do the same above
			// stuff.
			if (toggleCheckbox(webElementProperties[2], "").equals("Pass")) {
				logger.info("Beta login toggle button is turned ON");
				if (verifyText(webElementProperties[1], webElementsData[1]).equals("Pass")) {
					logger.info("Default View Toggle button is already ON");
					if (toggleCheckbox(webElementProperties[3], "").equals("Pass")) {
						logger.info("Default view Toggle button is turned to OFF");
						if (toggleCheckbox(webElementProperties[2], "").equals("Pass")) {
							logger.info("Beta toggle button is turned to OFF");
							return Constants.KEYWORD_PASS;
						} else {
							logger.error("Beta toggle button failed to turn OFF");
							return Constants.KEYWORD_FAIL;
						}
					} else {
						logger.error("Default view button failed to turn OFF");
						return Constants.KEYWORD_FAIL;
					}
				} else {
					logger.info("Default view is already Set to OFF. We have to turn OFF Beta login toggle button");
					if (toggleCheckbox(webElementProperties[2], "").equals("Pass")) {
						logger.info("Beta login toggle button is turned OFF");
						return Constants.KEYWORD_PASS;
					} else {
						logger.error("Beta login toggle button cannot be turned to OFF");
						return Constants.KEYWORD_FAIL;
					}
				}
			} else {
				logger.error("Beta login button cannot be turned ON");
				return Constants.KEYWORD_FAIL;
			}
		}
	}

	/**
	 * This method is used to toggle the checkbox
	 * 
	 * @param object
	 *            Webelement property. Parameter is webElementToggleButton
	 * @param data
	 *            none
	 * @return Pass or Fail
	 */
	public String toggleCheckbox(String object, String data) {
		String locator = UIMap.getProperty(object);
		logger.info("toggling -- Locator " + locator);
		WebElement element = getWebElement(object, data);
		if (element != null) {
			element.click();
			return Constants.KEYWORD_PASS;
		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- Could not toggle -- Element " + locator + " Not Found");
			return Constants.KEYWORD_FAIL;
		}
	}

	/**
	 * This method is used to remove existing proxies
	 * 
	 * @param object
	 *            WebElement Property. Parameters are [deleteProxyWebElement,
	 *            continueButtonWebElement]
	 * @param data
	 *            null
	 * @return PASS or FAIL
	 * @throws NumberFormatException
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	public String deleteExistingProxiesIfPresent(String object, String data){
		String[] webElementProperties = object.split(",");
		List<WebElement> webElements = new ArrayList<WebElement>();
		webElements = getWebElements(UIMap.getProperty(webElementProperties[0]));
		logger.info("deleting the web elements. Total number of elements to be deleted is " + webElements.size());
		int deletedElements = 0;
		try{
			if (webElements.size() > 0) {
				for (int i = 0; i < webElements.size(); i++) {
					clickLink(webElementProperties[0], "");
					WebElement confirmDeleteElement = getWebElement(webElementProperties[1], "");
					confirmDeleteElement.click();
					deletedElements++;
					Wait("", "2000");
				}
			} else {
				logger.info("No proxies exists. So no need of deleting anything");
				return Constants.KEYWORD_PASS;
			}
			if (deletedElements == webElements.size()) {
				logger.info("All the web elements deleted successfully");
				return Constants.KEYWORD_PASS;
			} else {
				logger.error("All the web elements cannot be deleted. Error!!!");
				return Constants.KEYWORD_FAIL;
			}
		}catch(Exception e){
			logger.error("Exception while deleting the proxie "+e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}

	/**
	 * This method is used to write text in autofill textbox
	 * 
	 * @param object
	 *            WebElement Property. Parameters
	 *            [sett_emp_name,sett_first_emp_name]
	 * @param data
	 *            An array. Data in the form [Hull:Hullar, John 00001431400]
	 * @return PASS or FAIL
	 * @throws NumberFormatException
	 * @throws InterruptedException
	 */
	public String enterInAutoFillTextBox(String object, String data)
			throws NumberFormatException, InterruptedException {
		logger.info("Selecting data is " + data);
		System.out.println("Trying to select " + data);

		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement element = getWebElement(object, data);
		if (element != null) {
			//			for (int i = 0; i < textFieldData[0].length(); i++) {
			//				char c = textFieldData[0].charAt(i);
			//				String s = new StringBuilder().append(c).toString();
			//				element.sendKeys(s);
			//				Wait("", "1000");
			//			}
			element.sendKeys("a");
			Wait("", "1000");

		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- Could not find -- Element " + element + " Not Found");
			return Constants.KEYWORD_FAIL;
		}
		try {
			WebElement autoOptions = driver.findElement(By.xpath(UIMap.getProperty(object)));
			wait.until(ExpectedConditions.visibilityOf(autoOptions));
			List<WebElement> optionsToSelect = autoOptions.findElements(By.xpath("//parent::div/ul/li"));
			if (optionsToSelect.size() > 0) {
				for (WebElement option : optionsToSelect) {
					option.click(); 
					break;
				}
				logger.info("Text entered into auto fill textbox successfully");
				return Constants.KEYWORD_PASS;
			} else {
				logger.error("No names found in the drop down list of autofill text box");
				return Constants.KEYWORD_FAIL;
			}

		} catch (NoSuchElementException e) {
			logger.info("failed to enter text into auto fill textbox");
			return Constants.KEYWORD_FAIL;
		} catch (Exception e) {
			logger.info("failed to enter text into auto fill textbox");
			return Constants.KEYWORD_FAIL;
		}
	}


	/**
	 * Select a date from angular js date picker
	 * 
	 * @param object
	 *            Element locators. Sample parameters are
	 *            [sett_month_year_end_date,sett_next_btn_end_date,sett_cal_end_date](month
	 *            year object,'>' arrow, dates in the calendar object)
	 * @param data
	 *            Date parameters. Sample parameters are [4,August 2017]
	 * @return String pass or fail
	 * @throws InterruptedException
	 * @throws NumberFormatException
	 */
	public String selectDateFromAngularDatePicker(String object, String data) {
		String date[] = data.split(",");
		String inputDate = date[0];
		String setMonthAndYear = date[1];
		String webElementProperties[] = object.split(",");
		boolean foundMonthAndYear = false;
		boolean selectedDate = false;
		try{
			Wait("", "2000");
			while (!foundMonthAndYear) {
				String locator = UIMap.getProperty(webElementProperties[0]);
				logger.info("Verifying the text --Locator " + locator);
				WebElement element = getWebElement(webElementProperties[0], "");
				String monthAndYear = "";
				if (element != null) {
					monthAndYear = element.getText().trim();
				} else {
					logger.error("Unable to find datepicker in UI");
					return Constants.KEYWORD_FAIL;
				}
				if (setMonthAndYear.equals(monthAndYear)) {
					logger.info("Found the month and year from the UI as per the expected value");
					foundMonthAndYear = true;
					break;
				} else {
					// we keep on clicking next button in the datepicker until we encounter the
					// expected month and year
					if (clickAction(webElementProperties[1], "").equals("Pass")) {
						// we do nothing. Continue iterating
					} else {
						logger.error("Unable to click on next button on the datepicker");
						return Constants.KEYWORD_FAIL;
					}
				}
			}
			String locator = UIMap.getProperty(webElementProperties[2]);
			List<WebElement> calanderDivs = getWebElements(locator);
			if (calanderDivs.size() > 0) {
				for (int i = 0; i <= calanderDivs.size(); i++) {
					String webPageDate = "";
					WebElement spanElement = calanderDivs.get(i);
					if (spanElement != null) {
						webPageDate = spanElement.getText().trim();
					} else {
						logger.error("Unable to find dates in the UI");
						return Constants.KEYWORD_FAIL;
					}
					if (inputDate.equals(webPageDate)) {
						Wait("", "2000");
						calanderDivs.get(i).click();
						selectedDate = true;
						break;
					}
				}
			} else {
				logger.info("Unable to get the dates from the datepicker");
				return Constants.KEYWORD_FAIL;
			}
			if (selectedDate) {
				logger.info("Selected the date successfully");
				return Constants.KEYWORD_PASS;
			} else {
				logger.error("Unable to find date in the datepicker");
				return Constants.KEYWORD_FAIL;
			}
		}catch(Exception e){
			logger.error("Exception whilse selecting the date using the datepicker "+e.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}

	/**
	 * This is common keyword which compares different sections from earning
	 * statement paycheck details
	 * 
	 * @param object
	 *            section element
	 * @param data
	 *            query to compare ui
	 * @return pass or fail
	 */
	public String verifyEarningStatementPayCheckInfoSection(String object, String data) {

		logger.info("Executing verifyEarningStatementPayCheckInfoSection keyword");

		HashMap<String, String> expectedInfo = new HashMap<String, String>();
		HashMap<String, String> actualInfo = new HashMap<String, String>();

		if (!Strings.isNullOrEmpty(data)) {
			logger.info("the expected data is : " + data);
			String[] dataSplit = data.split("\\|");
			for (int k = 0; k < dataSplit.length; k++) {

				String dataSplitByPipe = dataSplit[k];

				String[] dataSplitByColon = dataSplitByPipe.split(":");
				if (dataSplitByColon[0].contains("Pay Rate")) {
					DecimalFormat df = new DecimalFormat("#,###,##0.00");
					;
					String[] test = dataSplitByColon[1].split(" ");
					String amt = test[0].replace("$", "");
					BigDecimal bd = new BigDecimal(amt);
					String finalAmt = df.format(bd);
					System.out.println(finalAmt);
					String payRate = "$" + finalAmt + " " + test[1];
					dataSplitByColon[1] = payRate;
				}
				expectedInfo.put(dataSplitByColon[0], dataSplitByColon[1].trim());
			}

			String locator = UIMap.getProperty(object);
			List<WebElement> elements = getWebElements(locator);

			if (elements != null) {
				for (int i = 0; i < elements.size(); i++) {
					String ele = elements.get(i).getText().trim();
					if (!ele.contains(":")) {
						ele = ele.replace("\n", ":");
					} else {
						ele = ele.replace("\n", "");
					}
					String[] ele1 = ele.split(":");
					actualInfo.put(ele1[0], ele1[1].trim());
				}

			} else {
				logger.error(Constants.KEYWORD_FAIL + " -- Could not find Element " + object + " Not Found");
				return Constants.KEYWORD_FAIL + " -- Could not find Element " + object + " Not Found";
			}

			// Compare two maps
			if (expectedInfo.equals(actualInfo)) {
				return Constants.KEYWORD_PASS;
			} else {
				logger.error(Constants.KEYWORD_FAIL + " -- validation for earning statement info is wrong");
				return Constants.KEYWORD_FAIL + " -- validation for earning statement info is wrong";
			}
		} else {
			logger.error(Constants.KEYWORD_FAIL + " -- validation for earning statement info is wrong");
			return Constants.KEYWORD_FAIL + " -- validation for earning statement info is wrong";
		}
	}

	/*
	 * Scroll to the point where element is visible Works only for horizontal scroll
	 * 
	 * @Object : xpath locator in UI
	 * 
	 * @data : none
	 * 
	 * @return : pass
	 * 
	 * 
	 */
	public String scrollIntoView(String object, String data) throws InterruptedException {

		String locator = UIMap.getProperty(object);
		WebElement element = getWebElement(object, data);
		if (element != null) {
			logger.info("Scrolling to view element "+element);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			return Constants.KEYWORD_PASS;
		}
		else{
			logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element " + locator + " Not Found");
			return Constants.KEYWORD_FAIL + " -- Could not find Element " + locator + " Not Found";
		}
	}

	/**
	 * NOTE : takes out 'IN ' applies only to GS NEXt holiday widget verify saved
	 * HashMapData, get data from HashMapList using 'key' (which is input data)
	 * 
	 * @param object
	 *            Object
	 * @param data
	 *            Data
	 * @return String pass or fail
	 */
	public String savePartialDataAsKeyValue(String object, String data) {
		String locator = UIMap.getProperty(object);
		logger.info("Verifying the text --Locator " + locator);
		WebElement element = getWebElement(object, data);
		String actual = "";
		if (element != null) {
			actual = element.getText().trim();
			saveAnyDataInHash.addValue(data, actual.replace("In ", ""));
			return Constants.KEYWORD_PASS;
		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element " + locator + " Not Found");
			return Constants.KEYWORD_FAIL + " -- Could not find Element " + locator + " Not Found";
		}
	}

	/**
	 * This keyword is used to verify text/value from WebElement contains hashval or
	 * hashval contains text from ui
	 *
	 * @param object
	 *            UI element
	 * @param data
	 *            Hashkey
	 * @return String pass or fail
	 */
	public String verifyWebElementAgainstHashKey(String object, String data) {
		logger.info("Verifying the text --Locator " + object);

		try {
			WebElement element = getWebElementWithNoData(object, "");
			if (element != null) {
				String actual = element.getText().trim();

				// TODO Base where to find data on the element type.
				if (Strings.isNullOrEmpty(actual)) {
					if (!Strings.isNullOrEmpty(element.getAttribute("value"))) {
						actual = element.getAttribute("value").trim();
					} else {
						actual = element.getAttribute("innerHTML");
						actual = actual.replaceAll("\\<[^>]*>", "");
						actual = actual.replace("&nbsp;", "").replace("&amp;", "&").trim();
					}
				}
				if (Strings.isNullOrEmpty(actual.trim()) && Strings.isNullOrEmpty(data.trim())) {
					return Constants.KEYWORD_PASS;
				}

				String hashval = saveAnyDataInHash.getValue(data);

				if (actual.trim().toUpperCase().contains(hashval.toUpperCase().trim())
						|| hashval.toUpperCase().contains(actual.toUpperCase().trim())) {
					return Constants.KEYWORD_PASS;
				}
				return Constants.KEYWORD_FAIL + " -- text not verified \n actual = {" + actual + "} -- \n expected = {"
				+ data + "}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element " + object + " Not Found");
		return Constants.KEYWORD_FAIL + " -- Could not find Element " + object + " Not Found";
	}

	/**
	 * This keyword is used to compare value in comma sepearted hash keys given in
	 * data
	 *
	 * @param object
	 *            UI element
	 * @param data
	 *            Hashkey
	 * @return String pass or fail
	 */
	public String compareHashValues(String object, String data) {
		logger.info("Verifying the text --Locator " + object);
		String hashval1 = null, hashval2 = null;
		try {
			String[] hashkeys = data.split(",");
			hashval1 = saveAnyDataInHash.getValue(hashkeys[0]);
			hashval2 = saveAnyDataInHash.getValue(hashkeys[1]);
			// actual = actual.replace(" ", " ");

			if (hashval1.trim().toUpperCase().contains(hashval2.toUpperCase().trim())
					|| hashval2.toUpperCase().contains(hashval1.toUpperCase().trim())) {
				logger.info("Value1 :{} and Value2: {} matches ", hashval1, hashval2);
				return Constants.KEYWORD_PASS;
			} else {
				logger.info("Value1 :{} and Value2: {} doesn't matches ", hashval1, hashval2);

				return Constants.KEYWORD_FAIL + " -- Hashkey comparison failed " + hashval1 + "  and " + hashval2
						+ "doesn't match ";

			}

		} catch (Exception e) {

			logger.error(e.getMessage());
			return Constants.KEYWORD_FAIL + " -- Hashkey comparison failed " + hashval1 + "  and " + hashval2
					+ "doesn't match ";
		}

	}
	/*
	 * Passport OE page shows spinner Actual content loads after spinner visibility
	 * is hidden
	 * 
	 * 
	 */

	public String waitforSpinnertEnd(String object, String data) {

		String locator = UIMap.getProperty(object.split(",")[0]);
		logger.debug("Check locator if_exist " + locator);

		final WebElement element = getWebElement(object, data);
		System.out.println(element.getCssValue("visibility"));
		System.out.println(element.getCssValue("style"));
		boolean flag = false;
		driverWait = new WebDriverWait(driver, implicitWaitTime).withTimeout(5, TimeUnit.MINUTES).pollingEvery(2,
				TimeUnit.SECONDS);

		try {
			flag = driverWait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					logger.info("Waiting for Elements to completely load ... and return display status");

					System.out.println("Element displayed ??" + element.getCssValue("visibility"));
					return element.getCssValue("visibility").equalsIgnoreCase("hidden");
				}
			});
		} catch (TimeoutException ex) {
			logger.error("Failed to Load Element after 5 min ..." + ex);

		}
		if (flag) {
			return Constants.KEYWORD_PASS;
		} else {
			return Constants.KEYWORD_FAIL;
		}
	}

	/**
	 * used only for holiday calender widget This keyword is used to get Date from
	 * web element Example Thursday, Nov 23, 2017 get only NOVEMBER or NOV based on
	 * format
	 *
	 * @param object
	 *            UI element of Date as Sttring
	 * @param data
	 *            format of month
	 * @throws java.text.ParseException
	 */
	public String getMonthandSaveinHash(String object, String data) {
		logger.info("Verifying the text --Locator " + object);

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd, yyyy");
			// get web element text
			WebElement element = getWebElementWithNoData(object, "");
			if (element != null) {
				String dateInString = element.getText().trim();
				Date date = formatter.parse(dateInString);
				Format formatter2 = new SimpleDateFormat("MMM");
				String s = formatter2.format(date);
				logger.info("Formatted Month :{}", s);
				saveAnyDataInHash.addValue(data, s);
			} else {
				logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element " + object + " Not Found");
				return Constants.KEYWORD_FAIL + " -- Could not find Element " + object + " Not Found";
			}
		} catch (Exception e) {
			logger.info(Constants.KEYWORD_FAIL + " -- webElement ::" + object
					+ " value is not formatted and saved in haspmap key:: " + data);
			return Constants.KEYWORD_FAIL + " -- webElement ::" + object
					+ " value is not formatted and saved in haspmap key:: " + data;
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * used only for hokliday calender widget This keyword is used to get Date from
	 * web element Example Thursday, Nov 23, 2017 get only 23
	 *
	 * @param object
	 *            UI element
	 * @param data
	 *            Hashkey
	 * @throws java.text.ParseException
	 */
	public String getDateandSaveinHash(String object, String data) {

		logger.info("Verifying the text --Locator " + object);

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd, yyyy");
			// get web element text
			WebElement element = getWebElementWithNoData(object, "");
			if (element != null) {
				String dateInString = element.getText().trim();
				Date date = formatter.parse(dateInString);
				Format formatter2 = new SimpleDateFormat("d");
				String s = formatter2.format(date);
				logger.info("Formatted Date of Month :{}", s);
				saveAnyDataInHash.addValue(data, s);
			} else {
				logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element " + object + " Not Found");
				return Constants.KEYWORD_FAIL + " -- Could not find Element " + object + " Not Found";
			}
		} catch (Exception e) {
			logger.info(Constants.KEYWORD_FAIL + " -- webElement ::" + object
					+ " value is not formatted and saved in haspmap key:: " + data);
			return Constants.KEYWORD_FAIL + " -- webElement ::" + object
					+ " value is not formatted and saved in haspmap key:: " + data;
		}
		return Constants.KEYWORD_PASS;
	}

	public String getYearandSaveinHash(String object, String data) {
		logger.info("Verifying the text --Locator " + object);
		try {

			SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd, yyyy");
			// get web element text
			WebElement element = getWebElementWithNoData(object, "");
			if (element != null) {
				String dateInString = element.getText().trim();
				Date date = formatter.parse(dateInString);
				Format formatter2 = new SimpleDateFormat("yyyy");
				String s = formatter2.format(date);
				logger.info("Formatted Year :{}", s);
				saveAnyDataInHash.addValue(data, s);
			} else {
				logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element " + object + " Not Found");
				return Constants.KEYWORD_FAIL + " -- Could not find Element " + object + " Not Found";
			}
		} catch (Exception e) {
			logger.info(Constants.KEYWORD_FAIL + " -- webElement ::" + object
					+ " value is not formatted and saved in haspmap key:: " + data);
			return Constants.KEYWORD_FAIL + " -- webElement ::" + object
					+ " value is not formatted and saved in haspmap key:: " + data;
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * update the end date of open enrollment period to have oe open for some days
	 * update correction end date to past date
	 * 
	 * @param object
	 *            none
	 * @param data
	 *            none
	 * @return pass or fail
	 *//*
	public String updateenddate(String object, String data) {
		String updateQuery = "hp." + data;
		System.out.println(updateQuery);
		try {

			dataSetup(null, updateQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(Constants.KEYWORD_FAIL + " -- Could not update OE enddate with query" + updateQuery);
			return Constants.KEYWORD_FAIL + " -- Could not  update OE enddate with query" + updateQuery;
		}

		return Constants.KEYWORD_PASS;
	}*/

	/*
	 * update internal employees notices for all us employees inactive
	 * 
	 * to make sure there are no notices at all
	 * 
	 * Object is hashmap key Data is sql
	 * 
	 */
	public String UpdateNoticeInactive(String object, String data) {
		String noticeids = saveAnyDataInHash.getValue(object);
		String final_noticeid = "''";
		if (!Strings.isNullOrEmpty(noticeids)) {
			String[] notice_id = noticeids.split(",");
			for (String val : notice_id) {
				final_noticeid = final_noticeid + "," + "'" + val + "'";

			}
		} else {

		}
		saveAnyDataInHash.addValue(object, final_noticeid);

		String updatenoticesQuery = data;
		try {
			dataSetup(null, updatenoticesQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(
					Constants.KEYWORD_FAIL + " -- Could not Update Notice as Inactive with query" + updatenoticesQuery);
			return Constants.KEYWORD_FAIL + " -- Could not Update Notice as Inactive with query" + updatenoticesQuery;
		}
		return Constants.KEYWORD_PASS;
	}

	/*
	 * below method is to verify if Open enrollment popup appear after login if any
	 * other window is open like poicy update , close it till we get to OE #Param
	 * Object : pop up window main header text "Open Enrollment"
	 */
	public String VerifyOpenEnrollmentPopUpWindow(String object, String data)
			throws InterruptedException, AWTException {
		String locator = UIMap.getProperty(object);
		// String oe_locator = UIMap.getProperty("oe_popup_model_window");
		List<WebElement> numberofPopup = getWebElements(locator);
		try {
			if (numberofPopup.size() > 0) {
				logger.info("POP up window is available. ");
				while (numberofPopup.size() > 0) {

					String popupheader_locator = UIMap.getProperty(object);
					WebElement popup_header = getWebElement(object, data);

					logger.info("Availble pop up :{}", popup_header.getText());
					String actualheadertext = popup_header.getText().trim();
					String expectedheadertext = data.trim();
					// if the text matches ,return pass
					if (actualheadertext.contains(expectedheadertext)) {
						return Constants.KEYWORD_PASS;
					} else {
						// close out the popup to go to next
						Actions action = new Actions(driver);
						action.sendKeys(Keys.ESCAPE).build().perform();
						driver.switchTo().defaultContent();
						Thread.sleep(1000);
					}
				}
				return Constants.KEYWORD_FAIL + "-- Expected pop up window not available:" + data.trim();
			} else { // no popup available
				logger.info("There is no pop up windows ");
				return Constants.KEYWORD_PASS;
			}
		} catch (Exception ex) {
			logger.error("Exception in handling the pop up windows", ex.getMessage());
			return Constants.KEYWORD_FAIL;
		}

	}

	/**
	 * Insert role into database table
	 * 
	 * @param object
	 *            none
	 * @param data
	 *            none
	 * @return pass or fail
	 */
	public String updateOEenddate(String object, String data) {

		String updateQuery = "update LKP_OPENENROLLMENT set enddate =SYSDATE+1 where company = 'VES'";
		updateQuery = "hp." + updateQuery;
		logger.info("updateQuery for OE end date:{}", updateQuery);
		try {
			dataSetup(null, updateQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Exception in updating the OE end date :{}", e.getMessage());
			return Constants.KEYWORD_FAIL + " -- Update OE end date failed with query :" + updateQuery;
		}

		return Constants.KEYWORD_PASS;
	}

	/*
	 * verifies if open enrollment and notices widgets is available on right hand
	 * side on GS UI check for length of available notices and iterate to match the
	 * Data if matches with text of element , return true else false
	 * 
	 * @param : Object (xpath of notice)
	 */
	public String VerifyNoticesWidgets(String object, String data) throws InterruptedException, AWTException {

		String locator = UIMap.getProperty(object);
		List<WebElement> numberofNotices = getWebElements(locator);
		try {
			if (numberofNotices.size() != 0) {
				for (WebElement notice : numberofNotices) {

					logger.info("Available notice :{}", notice.getText());
					String actualnoticetext = notice.getText().trim();
					String expectednoticetext = data.trim();
					// if the text matches , click on open enrollment link
					if (actualnoticetext.equalsIgnoreCase(expectednoticetext)) {
						return Constants.KEYWORD_PASS;
					} else {
						continue;
					}
				}
			} else {
				return Constants.KEYWORD_FAIL + "-- notices are not available";
			}
			return Constants.KEYWORD_FAIL + "-- notice doesn't match ";
		} catch (Exception ex) {
			logger.error("Exception in handling the Notices", ex.getMessage());
			return Constants.KEYWORD_FAIL;
		}
	}

	public String getandSaveCompanyListInHash(String object, String data) {
		ArrayList<String> noTerminatedcompanies;
		try {
			String query = "SQL|hp." + data;
			noTerminatedcompanies = DBUtil.executeSelectreturnListofString(query);
			String quotedCommaSeparatedCompList = "''";
			if (!noTerminatedcompanies.isEmpty()) {
				for (String val : noTerminatedcompanies) {
					quotedCommaSeparatedCompList = quotedCommaSeparatedCompList + "," + "'" + val + "'";
				}
				logger.info("quotedCommaSeparatedCompList:{}", quotedCommaSeparatedCompList);
				saveAnyDataInHash.addValue(object, quotedCommaSeparatedCompList);
				return Constants.KEYWORD_PASS;

			} else {

				logger.info(Constants.KEYWORD_FAIL + " -- Could not Get Not terminated companies query" + data);
				return Constants.KEYWORD_FAIL + " -- Could not Get Not terminated companies query" + data;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info(Constants.KEYWORD_FAIL + " -- Could not Get Not terminated companies query" + data);
			return Constants.KEYWORD_FAIL + " -- Could not Get Not terminated companies query" + data;
		}

	}

	/**
	 * get length of leave types
	 * 
	 * @param object
	 *            none
	 * @param data
	 *            none
	 * @return pass or fail
	 */
	public String getandSaveLeaveTypeHoursinHash(String object, String data) {

		String locator = UIMap.getProperty(object.split(",")[0]);
		logger.debug("Check locator if_exist " + locator);

		List<WebElement> elements = getWebElements(locator);

		saveAnyDataInHash.addValue("DB_leavetype_no", String.valueOf(elements.size()));
		if (elements.size() != 0) {

			for (int i = 0; i < elements.size(); i++) {

				String leaveid = elements.get(i).getAttribute("id");
				String[] templeaveName = leaveid.split("Label");
				String[] leaveName = templeaveName[0].split(" ");
				WebElement ele = driver.findElement(
						By.xpath("//*[contains(@id,'" + leaveName[0].trim() + "') and contains(@id,'Val')]"));
				saveAnyDataInHash.addValue(StringUtils.capitalize(leaveName[0]), ele.getText());
				String car_obj = "timeoff_carousel_next";
				String locator_carousel = UIMap.getProperty(car_obj.split(",")[0]);
				logger.debug("Check locator if_exist " + locator_carousel);
				WebElement carousel_ele_next = getWebElement(car_obj, "");
				carousel_ele_next.click();
			}

		} else {
			return Constants.KEYWORD_FAIL + "-- no leave type present ";

		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * get length of leave types
	 * 
	 * @param object
	 *            none
	 * @param data
	 *            none
	 * @return pass or fail
	 */
	public String verifyLeaveTypeHourswithHash(String object, String data) {

		String locator = UIMap.getProperty(object.split(",")[0]);
		logger.debug("Check locator if_exist " + locator);
		boolean flag = false;
		List<WebElement> elements = getWebElements(locator);

		if (!(elements.size() == Integer.valueOf(saveAnyDataInHash.getValue("DB_leavetype_no").toString()))) {
			logger.info(Constants.KEYWORD_FAIL + " -- number of leave plan types doesn't match");
			return Constants.KEYWORD_FAIL + "  -- number of leave plan types hours doesn't mach";
		} else {
			for (int i = 0; i < elements.size(); i++) {
				String leaveid = elements.get(i).getText();
				String[] templeaveName = leaveid.split(" ");
				String[] leaveName = templeaveName[0].split(" ");
				Set<Entry<String, String>> entires = saveAnyDataInHash.getMap().entrySet();
				for (Entry<String, String> ent : entires) {
					if (ent.getKey().equalsIgnoreCase(leaveName[0])) {
						WebElement ele = driver.findElement(By.xpath("//div[contains(text(),'" + ent.getKey().trim()
								+ "')]/ancestor::div[@class='x-panel x-box-item x-panel-default']//following-sibling::div/..//div[@class='x-seeker-pie-chart-center-data']"));
						if (ele.getText().equalsIgnoreCase(ent.getValue().toString())) {
							flag = true;
						} else {
							flag = false;
						}
					} else {
						// key didn't match
						// continue;
					}
				}
			}
		}
		if (flag) {
			return Constants.KEYWORD_PASS;
		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- leave plan types hours doesn't match");
			return Constants.KEYWORD_FAIL + "  -- leave plan types hours doesn't mach";
		}
	}

	/**
	 * Save Data which is the value of locator in Hashmap using the key as Object
	 * name given and value as element value
	 * 
	 * @param object
	 *            Object
	 * @param data
	 *            Data as key name
	 * @return String pass or fail
	 */
	public String saveLocatorValueAsKeyValue(String object, String data) {
		String locator = UIMap.getProperty(object);
		logger.info("Verifying the text --Locator " + locator);
		WebElement element = getWebElement(object, data);
		String locvalue = "";
		if (element != null) {
			locvalue = element.getAttribute("value").trim();
			saveAnyDataInHash.addValue(data, locvalue);
			return Constants.KEYWORD_PASS;
		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element " + locator + " Not Found");
			return Constants.KEYWORD_FAIL + " -- Could not find Element " + locator + " Not Found";
		}
	}

	/**
	 * This keyword is used to verify text from any element other than text input
	 * field.
	 * 
	 * @param object
	 *            UI element
	 * @param data
	 *            text to be verified
	 * 
	 * @return String pass or fail
	 */
	public String verifyTextWithSavedatainHash(String object, String data) {

		String locator = UIMap.getProperty(object);
		WebElement element = getWebElement(object, data);
		String value = saveAnyDataInHash.getValue(data);
		String actual = "";
		String expected = value.trim();
		if (element != null) {
			actual = element.getText().trim();
			if (actual.equals(expected)) {
				logger.info("Actual= " + actual + "matches Expected = " + expected);
				return Constants.KEYWORD_PASS;
			} else {
				logger.info(
						Constants.KEYWORD_FAIL + " Actual--   " + actual + " does not match to expected " + expected);
				return Constants.KEYWORD_FAIL + " Actual--  " + actual + " does not match to expected " + expected;
			}

		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element" + locator);
			return Constants.KEYWORD_FAIL + " -- Could not find Element " + locator;
		}
	}

	public String verify_WSEHoliday(String object, String data) throws InterruptedException, SQLException {
		String locator = UIMap.getProperty(object);
		logger.debug("Check locator if_exist " + locator);
		String[] obj = object.split(",");
		String[] dat = data.split(",");
		String DescriptionHoliday = null;
		String DescrHolidayDBexpected = null;
		String DateHoliday = null;
		String DateHolidayDBexpected = null;
		boolean flag = false;
		WebElement element = getWebElement(obj[0], data);
		WebElement element1 = getWebElement(obj[1], data);
		if (element != null) {

			if (element1 != null) {
				logger.info("Holiday Calendar not yet available");
				flag = true;

			} else {
				String DescrHoliday = ".//div[@class='row row-data holiday-calendar-table ng-scope']//p[@class='semibold col-sm-6 ng-binding']";
				List<WebElement> HolidayElements = element.findElements(By.xpath(DescrHoliday));
				String DateElementHoliday = ".//div[@class='row row-data holiday-calendar-table ng-scope']//p[@class='semibold col-sm-6 text-right ng-binding']";
				List<WebElement> DateElements = element.findElements(By.xpath(DateElementHoliday));
				try {
					if (HolidayElements.get(0).getText().contains("Floating")) {

						for (int i = 1; i < HolidayElements.size(); i++) {
							DescriptionHoliday = HolidayElements.get(i).getText().trim();
							DescrHolidayDBexpected = DBUtil.getDBData(
									"sql|hr.SELECT DESCR from  (select  rownum OUR_ROWNUM, HD1.*from PS_HOLIDAY_DATE HD1 WHERE   hd1.holiday_schedule="
											+ "\'" + saveAnyDataInHash.getValue(dat[1])  + "\' and TO_CHAR(hd1.holiday,'mm/dd/yyyy')like " + "\'"
											+ dat[0] + "\'and holiday_hrs=8 order by holiday) RESULT where  OUR_ROWNUM="
											+ "\'" + (i) + "\'");
							DateHoliday = DateElements.get(i).getText().trim();

							DateHolidayDBexpected = DBUtil.getDBData(
									"sql|hr.SELECT to_char(holiday,'fmDay, fmMon fmDD, YYYY') from  (select  rownum OUR_ROWNUM, HD1.*from PS_HOLIDAY_DATE HD1 WHERE   hd1.holiday_schedule="
											+ "\'" + saveAnyDataInHash.getValue(dat[1]) 
											+ "\' AND HOLIDAY_HRS='8'and TO_CHAR(hd1.holiday,'mm/dd/yyyy')like " + "\'"
											+ dat[0] + "\' order by holiday) RESULT where  OUR_ROWNUM=" + "\'" + (i)
											+ "\'");
							try {
								if (DescriptionHoliday.contains(DescrHolidayDBexpected)) {
									flag = true;
								}
								Assert.assertEquals(DateHoliday, DateHolidayDBexpected);
								flag = true;
							} catch (Exception e) {
								logger.info(e.getMessage());
								return Constants.KEYWORD_FAIL + " -- Not able to validate Holiday";
							}
						}
					} else {

						for (int i = 0; i < HolidayElements.size(); i++) {
							DescriptionHoliday = HolidayElements.get(i).getText().trim();
							DescrHolidayDBexpected = DBUtil.getDBData(
									"sql|hr.SELECT DESCR from  (select  rownum OUR_ROWNUM, HD1.*from PS_HOLIDAY_DATE HD1 WHERE   hd1.holiday_schedule="
											+ "\'" + saveAnyDataInHash.getValue(dat[1])  + "\' and TO_CHAR(hd1.holiday,'mm/dd/yyyy')like " + "\'"
											+ dat[0] + "\'and holiday_hrs=8 order by holiday) RESULT where  OUR_ROWNUM="
											+ "\'" + (i + 1) + "\'");

							DateHoliday = DateElements.get(i).getText().trim();
							DateHolidayDBexpected = DBUtil.getDBData(
									"sql|hr.SELECT to_char(holiday,'fmDay, fmMon fmDD, YYYY') from  (select  rownum OUR_ROWNUM, HD1.*from PS_HOLIDAY_DATE HD1 WHERE   hd1.holiday_schedule="
											+ "\'" + saveAnyDataInHash.getValue(dat[1]) 
											+ "\' AND HOLIDAY_HRS='8'and TO_CHAR(hd1.holiday,'mm/dd/yyyy')like " + "\'"
											+ dat[0] + "\' order by holiday) RESULT where  OUR_ROWNUM=" + "\'" + (i + 1)
											+ "\'");
							try {
								if (DescriptionHoliday.contains(DescrHolidayDBexpected)) {
									flag = true;
								}
								Assert.assertEquals(DateHoliday, DateHolidayDBexpected);
								flag = true;
							} catch (Exception e) {
								logger.info(e.getMessage());
								return Constants.KEYWORD_FAIL + " -- Not able to validate Holiday";
							}
						}
					}

				} catch (Exception e) {
					logger.info(e.getMessage());
					return Constants.KEYWORD_FAIL + " -- Not able to validate Holiday ";
				}
			}

		}
		if (flag == true)
			return Constants.KEYWORD_PASS;
		else
			return Constants.KEYWORD_FAIL;

	}

	public String verifyStoredText_person(String object, String data) {
		String locator = UIMap.getProperty(object);
		logger.info("Verifying the text --Locator " + locator);
		WebElement element = getWebElement(object, data);
		String actual = element.getText().trim();
		String expected = valStore.substring(0, valStore.indexOf(' ')).trim();
		if (actual.contains(expected)) {
			return Constants.KEYWORD_PASS;
		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- Could not find stored value " + actual + " -- " + expected);
			return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
		}
	}

	public String verifyStoredText_Department(String object, String data) {
		String locator = UIMap.getProperty(object);
		logger.info("Verifying the text --Locator " + locator);
		WebElement element = getWebElement(object, data);
		String actual = element.getText().trim();
		String expected = valStore;
		if (actual.contains(expected)) {
			return Constants.KEYWORD_PASS;
		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- Could not find stored value " + actual + " -- " + expected);
			return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
		}
	}

	/**
	 * This keyword is used to verify url and title from company-> Forms and
	 * Policies-> Policy Acknowledgements
	 * 
	 * 
	 * @param object
	 *            UI element of table
	 * @param data
	 *            company,url
	 * 
	 * @return String pass or fail
	 */
	public String VerifyUrlOfEmployeeHandbook(String object, String data) throws InterruptedException, SQLException {
		String locator = UIMap.getProperty(object);
		String[] dat = data.split(",");
		String statusdb = null;
		logger.debug("Check locator if_exist " + locator);
		Map<String, String> hashtableUI = new Hashtable<String, String>();
		Map<String, String> hashtableDB = new Hashtable<String, String>();
		WebElement element = getWebElement(object, dat[0]);
		if (element != null) {
			List<WebElement> id_elements = element.findElements(By.xpath(locator));
			for (int i = 1; i < id_elements.size(); i++) {
				String pagepath = ".//a";
				List<WebElement> findElementPagePath = id_elements.get(i).findElements(By.xpath(pagepath));
				int size = findElementPagePath.size();
				findElementPagePath.get(1).click();
				findElementPagePath.get(2).click();
				switchToNewWindow(object, data);
				String ActualUrl = driver.getCurrentUrl();
				String URL = ActualUrl.replaceAll("%", "").replaceAll("[0-9]", "");
				String ActualUrlPath = URL.substring(URL.lastIndexOf('/') + 1, URL.length());
				closeCurrentTab(object, data);
				switchToOriginalWindow(object, data);
				findElementPagePath.get(1).click();
				String PageDescription = findElementPagePath.get(0).getText();
				hashtableUI.put(PageDescription, ActualUrlPath);

				String DBexpectedPolicies = DBUtil.getDBData(
						"sql|hp.select result.pagedesc from(select ROWNUM OUR_ROWNUM,a.*  from eforms a where pageid in (select pageid from eforms_org where orgid in('ALL') and pageid<>1522 and sysdate between effdt and enddt union select pageid from eforms_org where orgid in("
								+ "\'" + saveAnyDataInHash.getValue(dat[0])
								+ "\') and sysdate between effdt and enddt ))result where OUR_ROWNUM=(" + "\'" + i
								+ "\') ");

				String DBexpectedUrl = DBUtil.getDBData(
						"sql|hp.select case when result.pagepath in ('/Help/docs/pdf/Company_and_TriNet_CA_Policy.pdf') then 'Company_and_TriNet_CA_Policy.pdf'  when result.pagepath in ('Employee_Handbook.htm') then "
								+ "\'" + dat[1]
										+ "\' else replace(regexp_replace(result.pagepath,'\\d',''),' ','')  end as  pagepath from(select ROWNUM OUR_ROWNUM,a.*  from eforms a where pageid in (select pageid from eforms_org where orgid in('ALL') and pageid<>1522 and sysdate between effdt and enddt union select pageid from eforms_org where orgid in("
										+ "\'" + saveAnyDataInHash.getValue(dat[0])
										+ "\') and sysdate between effdt and enddt ))result where OUR_ROWNUM=(" + "\'" + i
										+ "\') ");

				hashtableDB.put(DBexpectedPolicies.trim(), DBexpectedUrl.trim());
			}

			if ((hashtableUI.equals(hashtableDB))) {
				logger.info("Actual Page Description and Page Path in UI are matched with DB");
				return Constants.KEYWORD_PASS;
			}

			else
				logger.info(Constants.KEYWORD_FAIL
						+ " -- Not able to validate Page Description and Page Path of Employee Handbook ");
			return Constants.KEYWORD_FAIL;
		} else {
			logger.info(Constants.KEYWORD_FAIL
					+ " -- Not able to validate Page Description and Page Path of Employee Handbook ");
			return Constants.KEYWORD_FAIL;
		}

	}

	/**
	 * This keyword is used to verify status of eforms for company-> Forms and
	 * Policies-> Policy Acknowledgements
	 * 
	 * 
	 * @param object
	 *            UI element of table
	 * @param data
	 *            emplid
	 * 
	 * @return String pass or fail
	 */

	public String VerifyStatusOfEmployeeHandbook(String object, String data) throws InterruptedException, SQLException {
		String locator = UIMap.getProperty(object);
		String statusdb = null;
		List<WebElement> findElementStatus = null;
		logger.debug("Check locator if_exist " + locator);
		Map<String, String> hashtableUI = new Hashtable<String, String>();
		Map<String, String> hashtableDB = new Hashtable<String, String>();
		WebElement element = getWebElement(object, data);
		if (element != null) {
			List<WebElement> id_elements = element.findElements(By.xpath(locator));
			for (int i = 1; i < id_elements.size(); i++) {
				String pagepath = ".//a";
				String status = ".//div[contains(@class,'status')]";
				String agree = ".//button[text()='I Agree']";
				List<WebElement> findElementPagePath = id_elements.get(i).findElements(By.xpath(pagepath));
				findElementStatus = id_elements.get(i).findElements(By.xpath(status));
				List<WebElement> findElementAgree = id_elements.get(i).findElements(By.xpath(agree));
				String PageDescription = findElementPagePath.get(0).getText();
				if (findElementStatus.get(0).getText().equals("Complete")) {

					statusdb = DBUtil.getDBData(
							"sql|hp.select  case when PAGEPARAMS=('Accept=Y')then 'Complete' when PAGEPARAMS=('Accept=N')then 'Not Complete' else PAGEPARAMS end as PAGEPARAMS from eforms_person a where personid in (select personid from person_keys where pers_key=("
									+ "\'" + saveAnyDataInHash.getValue(data)
									+ "\'))and pageid in(select pageid from eforms where pagedesc like(" + "\'"
									+ PageDescription
									+ "\')and sysdate between effdt and enddt )and TIMESTAMP in (select max(b.TIMESTAMP) from  eforms_person b where a.personid=b.personid  and pageid in(select pageid from eforms where pagedesc like("
									+ "\'" + PageDescription + "\')and sysdate between effdt and enddt ))and rownum=1");

				} else {
					findElementPagePath.get(1).click();
					findElementAgree.get(0).click();
					scrollup(object, "5000");
					findElementPagePath.get(1).click();
					Wait("", "5000");

					statusdb = DBUtil.getDBData(
							"sql|hp.select  case when PAGEPARAMS=('Accept=Y')then 'Complete' when PAGEPARAMS=('Accept=N')then 'Not Complete' else PAGEPARAMS end as PAGEPARAMS from eforms_person a where personid in (select personid from person_keys where pers_key=("
									+ "\'" + saveAnyDataInHash.getValue(data)
									+ "\'))and pageid in(select pageid from eforms where pagedesc like(" + "\'"
									+ PageDescription
									+ "\')and sysdate between effdt and enddt )and TIMESTAMP in (select max(b.TIMESTAMP) from  eforms_person b where a.personid=b.personid  and pageid in(select pageid from eforms where pagedesc like("
									+ "\'" + PageDescription + "\')and sysdate between effdt and enddt ))and rownum=1");
				}

			}

			if ((findElementStatus.get(0).getText().trim()).equals(statusdb.trim())) {
				logger.info("Actual Status Complete is matched with DB Status");
				return Constants.KEYWORD_PASS;
			} else
				logger.info(Constants.KEYWORD_FAIL + " -- Not able to validate Status of Employee Handbook ");
			return Constants.KEYWORD_FAIL;
		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- Not able to validate Status of Employee Handbook ");
			return Constants.KEYWORD_FAIL;
		}

	}

	public String saveAnyDataAsKeyValue(String object, String data) {
		String[] var = object.split(",");
		if (!Strings.isNullOrEmpty(data)) {

			if (var.length > 1) {
				String[] datasp = data.split(",");
				for (int i = 0; i < var.length; i++) {
					saveAnyDataInHash.addValue(var[i], datasp[i]);
				}
				return Constants.KEYWORD_PASS;
			} else {
				saveAnyDataInHash.addValue(object, data);
				return Constants.KEYWORD_PASS;
			}
		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- No DATA found in DB");
			return Constants.KEYWORD_FAIL + " --No DATA found in DB---";
		}
	}

	public String verifyTotalHours(String object, String data) {
		String locator = UIMap.getProperty(object);
		try{
			String temp[] = data.split(",");
			//adding all the hours, and subtracting the -taken hours
			float expectedTotalValue = Float.parseFloat(saveAnyDataInHash.getValue(temp[0]))+Float.parseFloat(saveAnyDataInHash.getValue(temp[1]))+Float.parseFloat(saveAnyDataInHash.getValue(temp[2]))-Float.parseFloat(saveAnyDataInHash.getValue(temp[3]));

			WebElement element = getWebElement(object, data);
			String actual = "";
			if (element != null) {
				actual = element.getText().trim();
				if (expectedTotalValue == Float.parseFloat(actual)) {
					logger.info("Actual= " + actual + "matches Expected = " + expectedTotalValue);
					return Constants.KEYWORD_PASS;
				} else {
					logger.info(
							Constants.KEYWORD_FAIL + " Actual--   " + actual + " does not match to expected " + expectedTotalValue);
					return Constants.KEYWORD_FAIL + " Actual--  " + actual + " does not match to expected " + expectedTotalValue;
				}
			} else {
				logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element" + locator);
				return Constants.KEYWORD_FAIL + " -- Could not find Element " + locator;
			}
		}catch(Exception ex){
			logger.error(Constants.KEYWORD_FAIL + " -- Failed in verifyTotalHours with exception" + ex);
			return Constants.KEYWORD_FAIL + " -- Failed in verifyTotalHours with exception " + ex;
		}
	}

	/**
	 * Verify accounts on the grid with the list of accounts in FSA dropdown list
	 * @param object accounts from grid
	 * @param data none
	 * @return pass or fail
	 */
	public String verifyDirectDepositAccountOnEdit(String object, String data) {
		try {

			ArrayList<String> listFromGrid = new ArrayList<String>();
			ArrayList<String> listFromFSA = new ArrayList<String>();

			logger.info("Verifying direct deposit accounts on edit page");

			String locator = UIMap.getProperty(object);
			List<WebElement> listOfAccountsFromGrid = getWebElements(locator);

			if (listOfAccountsFromGrid == null) {
				logger.info("Accounts are not found on edit page");
				return Constants.KEYWORD_FAIL + " Error-->Accounts are not found";
			}else
			{
				for (int i = 0; i < listOfAccountsFromGrid.size(); i++) {
					listFromGrid.add(listOfAccountsFromGrid.get(i).getText().trim());
				}

				locator = UIMap.getProperty("money_dd_edit_fsa_items");
				List<WebElement> listOfAccountsInFSA = getWebElements(locator);
				if(listOfAccountsInFSA != null) {
					for (int i = 0; i < listOfAccountsInFSA.size(); i++) {
						if (!(Strings.isNullOrEmpty(listOfAccountsInFSA.get(i).getText()))) {
							listFromFSA.add(listOfAccountsInFSA.get(i).getText().trim());
						}
					}

					if (listFromGrid.size() == listFromFSA.size()) {
						if (listFromGrid.equals(listFromFSA)) {
							return Constants.KEYWORD_PASS;
						} else {
							logger.info("Accounts from grid is not matching list of accounts in FSA listbox");
							return Constants.KEYWORD_FAIL
									+ " Error-->Accounts from grid is not matching list of accounts in FSA listbox";
						}

					} else {
						logger.info("Accounts are not deleted successfully on edit page");
						return Constants.KEYWORD_FAIL + " Error-->Accounts are not deleted successfully on edit page";
					}
				}else
				{
					logger.info("FSA Accounts are not found on edit page");
					return Constants.KEYWORD_FAIL + " Error-->FSA Accounts are not found";
				}

			}
		} catch (Exception ex) {
			logger.error("Exception in validating accounts on the grid with the list of accounts in FSA dropdown list" +ex.getMessage());
			return Constants.KEYWORD_FAIL + " -- Exception in validating accounts on the grid with the list of accounts in FSA dropdown list";
		}
	}

	public String verifyPayDay(String object, String data) throws java.text.ParseException{
		String payenddate=data.split(",")[0].split(" ")[0]; 
		String invoice=data.split(",")[1].split(" ")[0]; 
		String expectedresult=data.split(",")[2];

		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		try{
			//		Date startDate;
			//		startDate = sdf.parse(date);
			//			
			//		Calendar c = Calendar.getInstance();
			//		c.setTime(startDate);
			//		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			//      int expectedresult = 0;
			//		if (dayOfWeek==7){
			//			expectedresult=inDate - 1;
			//		}
			//		else if(dayOfWeek==8) {
			//			expectedresult=inDate-2;
			//		}

			String locator;
			WebElement element ;
			if(sdf.parse(payenddate).equals(sdf.parse(invoice))){
				locator = UIMap.getProperty(object.split(",")[0]);
				element = getWebElement(object.split(",")[0], data);
			}
			else{
				locator = UIMap.getProperty(object.split(",")[1]);
				element = getWebElement(object.split(",")[1], data);
			}

			if (element != null) {
				String actual = element.getText();
				if (actual.equals(expectedresult)) {
					return Constants.KEYWORD_PASS;
				} else {
					return Constants.KEYWORD_FAIL + " -- actual does not match with expected " + actual + " -- " + expectedresult;
				}
			}
			else{
				return Constants.KEYWORD_FAIL + " -- element not found"+ element;
			}
		}catch(Exception e){
			logger.error("Error while verifying PayDay "+e.getMessage());
			return Constants.KEYWORD_FAIL + " -- Error while verifying PayDay" ;
		}

	}


	/**
	 * Select year as per paychecks to generate custom report 
	 * @param object list element
	 * @param data year to select
	 * @return pass or fail
	 */
	public String selectYearForCustomReport(String object, String data)
	{
		try
		{
			String locator = UIMap.getProperty(object);
			logger.info("Selecting from list -- Locator is " + locator);
			Pattern p=Pattern.compile("\\$\\{[\\w]+\\}");
			Matcher m=null;		
			if(p.matcher(data.trim()).find()){
				m=p.matcher(data.trim());
				if(m.find()) {
					String text = m.group();
					data=saveAnyDataInHash.getValue(text.substring(2,text.lastIndexOf("}")));
				}
				logger.info("Input data is "+ data);
			}
			WebElement select = getWebElement(object, data);
			if (select != null) {
				select.click();
				WebElement option = select.findElement(By.xpath("//*[@class='x-boundlist-list-ct']/div[contains(text(),'" + data
						+ "')]"));
				option.click();

				return Constants.KEYWORD_PASS;
			} else {
				logger.info(Constants.KEYWORD_FAIL
						+ " -- Not able to Select Item From List -- Element " + locator + " Not Found");
				return Constants.KEYWORD_FAIL + " -- Not able to Select Item From List -- Element "
				+ locator + " Not Found";
			}
		}catch(Exception ex) {
			logger.error("Exception in selecting year for custom report on paycheck page", ex.getMessage());
			return Constants.KEYWORD_FAIL + " -- Exception in selecting year for custom report on paycheck page";
		}
	}
	/*
	 * below method is to verify if ACA window appear or not
	 * if any other window is open like poicy update , close it till we get to ALE
	 * #Param Object : pop up window main header text passed with true and false from excel
	 * @return : test case pass fail
	 * Expected[1] is true or false
	 * Expected[0] is pop up header name
	 */
	public String isPopUpWindowFound(String object, String data)
			throws InterruptedException, AWTException {
		String locator = UIMap.getProperty(object);

		WebElement popup_header;
		List<WebElement> numberofPopup = getWebElements(locator);
		String expectedValues[]=data.split(",");
		// datatoVerify pop up header name
		String datatoVerify = expectedValues[0];
		//shouldbeExpected true or false
		String expectedorNot= expectedValues[1];
		try {
			if(numberofPopup.size()>0){
				logger.info("POP up window is available. ");
				while(numberofPopup.size() > 0) {

					String popupheader_locator = UIMap.getProperty(object);
					popup_header = getWebElement(object, expectedorNot);

					logger.info("Availble pop up :{}",popup_header.getText());
					String actualheadertext = popup_header.getText().trim();
					String expectedheadertext = datatoVerify.trim();
					// if the text matches ,return pass
					if( (actualheadertext.contains(expectedheadertext) )&& (!expectedorNot.equalsIgnoreCase("False"))) {
						saveAnyDataInHash.addValue("popMsg", actualheadertext);
						return Constants.KEYWORD_PASS;
					} else {
						// close out the popup to go to next
						Actions action = new Actions(driver);
						action.sendKeys(Keys.ESCAPE).build().perform();
						driver.switchTo().defaultContent();
						Thread.sleep(1000);
					}
					numberofPopup = getWebElements(locator);
				}
				// below condition is for when user couldn't find ALE pop up among the present then return true , false based on expected value from Excel false or true
				if((expectedorNot.equalsIgnoreCase("False"))){
					logger.info("Expected pop up window :{}  didn't showed up and expected value is false",datatoVerify.trim());
					return Constants.KEYWORD_PASS ;
				}else{
					return Constants.KEYWORD_FAIL +"-- Expected pop up window not  available:"+datatoVerify;
				}

			} else{ // no popup available 
				if((expectedorNot.equalsIgnoreCase("False"))){
					logger.info("Expected pop up window :{}  didn't showed up and expected value is false",datatoVerify.trim());
					return Constants.KEYWORD_PASS;
				}else{
					return Constants.KEYWORD_FAIL +"-- Expected pop up window not  available:"+datatoVerify;
				}
			}


		} catch (Exception ex) {
			logger.error("Exception in handling the pop up windows",
					ex.getMessage());
			return Constants.KEYWORD_FAIL;
		}

	}

	/**
	 * This keyword is used to verify text from any element other than text input
	 * field.
	 * 
	 * @param Data
	 *           
	 * @param data
	 *            text to be verified
	 * 
	 * @return String pass or fail
	 * 
	 * TODO : enhance to have comma separated hashkeys and values and compare
	 * 
	 */
	public String verifyDataWithSavedatainHash(String object, String data) {

		String value = saveAnyDataInHash.getValue(object);
		String actual = "";
		String expected = value.trim();
		if (data != null) {
			actual = data.trim();
			if (actual.equals(expected)) {
				logger.info("Actual= " + actual + "matches Expected = " + expected);
				return Constants.KEYWORD_PASS;
			} else {
				logger.info(
						Constants.KEYWORD_FAIL + " Actual--   " + actual + " does not match to expected " + expected);
				return Constants.KEYWORD_FAIL + " Actual--  " + actual + " does not match to expected " + expected;
			}

		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- Could not find data/value" + data +"/"+value.trim());
			return Constants.KEYWORD_FAIL + " -- Could not find data " + data+"/"+value.trim();
		}
	}

	/**
	 * This keyword is used to verify text from any element other than text input
	 * field.
	 * 
	 * @param Data
	 *           
	 * @param data
	 *            text to be verified
	 * 
	 * @return String pass or fail
	 * 
	 * TODO : enhance to have comma separated hashkeys and values and compare
	 * 
	 */
	public String verifySavedatainHashContainsExpected(String object, String data) {

		String value = saveAnyDataInHash.getValue(object);
		String actual = value.trim();

		if (data != null) {
			String expected = data.trim();
			if (actual.contains(expected)) {
				logger.info("Actual= " + actual + " contains " + expected);
				return Constants.KEYWORD_PASS;
			} else {
				logger.info(
						Constants.KEYWORD_FAIL + " Actual--   " + actual + " does not contains expected " + expected);
				return Constants.KEYWORD_FAIL + " Actual--  " + actual + " does not contains expected " + expected;
			}

		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- Could not find data/value " + data +"/"+value.trim());
			return Constants.KEYWORD_FAIL + " -- Could not find data " + data+"/"+value.trim();
		}
	}

	/**
	 * This keyword is used to verify text from any element other than text input
	 * field.
	 * 
	 * @param object
	 *            UI element
	 * @param data
	 *            text to be verified
	 * 
	 * @return String pass or fail
	 * 
	 * 
	 */
	public String verifyContainsTextWithSavedatainHash(String object, String data) {

		String locator = UIMap.getProperty(object);
		WebElement element = getWebElement(object, data);
		String value = saveAnyDataInHash.getValue(data);
		String actual = "";
		String expected = value.trim();
		if (element != null) {
			actual = element.getText().trim();
			if (actual.contains(expected)) {
				logger.info("Actual= " + actual + "matches Expected = " + expected);
				return Constants.KEYWORD_PASS;
			} else {
				logger.info(
						Constants.KEYWORD_FAIL + " Actual--   " + actual + " does not match to expected " + expected);
				return Constants.KEYWORD_FAIL + " Actual--  " + actual + " does not match to expected " + expected;
			}

		} else {
			logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element" + locator);
			return Constants.KEYWORD_FAIL + " -- Could not find Element " + locator;
		}
	}

	/**
	 * Get the value of element using java script executor and save it in hash
	 * 
	 * @param object
	 *            element 
	 * @param data
	 *            to save in hash
	 * @return pass or fail
	 */

	public String getValueOfElementUsingJs(String object, String data) {
		try {
			String locator = UIMap.getProperty(object);		
			WebElement element = getWebElement(object, data);

			JavascriptExecutor js = (JavascriptExecutor) driver;
			String value= ((String) js.executeScript("return arguments[0].value", element)).trim();

			if(!Strings.isNullOrEmpty(data)){			
				saveAnyDataInHash.addValue(data,value);
				return Constants.KEYWORD_PASS;
			}
			else{
				logger.error("Exception in  : {}");
				return Constants.KEYWORD_FAIL + " -- Pass the variable name in data column";
			}
		} catch (Exception e) {
			logger.error("Exception in while getting the value of the element using js");
			return Constants.KEYWORD_FAIL + " -- Not able to get the value";
		}

	}

	/**
	 * Get the value of variable(that is passed in data column) from hash  and get the emplid only out of that value
	 * 
	 * @param object
	 *            null 
	 * @param data
	 *             variable that to get the value from hash
	 * @return pass or fail
	 */

	public String getEmplidBySplitting(String object, String data){
		String value;
		try {
			if(!Strings.isNullOrEmpty(data)){			
				value=saveAnyDataInHash.getValue(data);
				int sp=value.indexOf("0");
				saveAnyDataInHash.addValue(data,value.substring(sp,sp+11));
				return Constants.KEYWORD_PASS;
			}
			else{
				logger.error("Pass the variable name in data column ");
				return Constants.KEYWORD_FAIL + " -- Pass the variable name in data column";
			}
		} catch (Exception e) {
			logger.error("Exception in while getting the value of the variable");
			return Constants.KEYWORD_FAIL + " -- Not able to get the value";
		}
	}


	/**
	 * This keyword is used to verify text if the element present
	 * @param object UI element
	 * @param data text to be verified
	 * 
	 * @return String pass or fail
	 */
	public String verifyTextifElementPresent(String object, String data)
	{
		String locator = UIMap.getProperty(object);
		String actual = "";
		String expected = data.trim();

		try{
			logger.info("Verifying the element exist  --Locator " + locator);
			WebElement element = getWebElement(object, data);	

			if (element != null) {
				actual = element.getText().trim();
				if (actual.contains(expected)) {
					logger.info("in IfActual= " + actual + "matches Expected = " + expected);
					return Constants.KEYWORD_PASS;
				} else {
					logger.info("elseActual= " + actual + "doesn't match Expected = " + expected);					
					return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- "
					+ expected;
				}
			} else {
				logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element " + locator
						+ " Not Found");
				return Constants.KEYWORD_FAIL + " -- Could not find Element " + locator + " Not Found";
			}
		}catch(Exception e){
			if (data.equalsIgnoreCase(actual)) {
				//null matching with null since element not present
				logger.info("in exception Actual= " + actual + "matches Expected = " + expected);
				logger.info(Constants.KEYWORD_PASS + " -- Element " + locator + " Should Not Exist");
				return Constants.KEYWORD_PASS;
			} else {
				logger.info("Actual= " + actual + "doesn't match Expected = " + expected);					
				logger.info(Constants.KEYWORD_FAIL + " -- Element " + locator
						+ " Should Exist But Not Found");
				return Constants.KEYWORD_FAIL + " -- Element " + locator
						+ " Should Exist But Not Found";
			}
		}

	}

	/**
	 * Verifies element is readonly or not
	 * 	 * 
	 * @param object
	 *            Object
	 * @param data
	 *            Data is expected vale
	 * @return String pass or fail
	 */
	public String isReadOnly(String object, String data) {
		String locator = UIMap.getProperty(object);
		logger.info("Verifying the text --Locator " + locator);
		WebElement element = getWebElement(object, data);
		String locvalue = "";
		try{
			if (element != null) {
				locvalue = element.getAttribute("readonly").trim();
				if (data != null) {
					if(locvalue.equalsIgnoreCase(data)){
						return Constants.KEYWORD_PASS;
					}
					else{
						logger.info(Constants.KEYWORD_FAIL + "Actual= " + locvalue + "doesn't match Expected = " + data);
						return Constants.KEYWORD_FAIL + "Actual= " + locvalue + "doesn't match Expected = " + data;
					}
				}
				else{
					logger.info(Constants.KEYWORD_FAIL + " -- expected data is null, Pass it ");
					return Constants.KEYWORD_FAIL + " -- -- expected data is null,Pass it";
				}
			} else {
				logger.info(Constants.KEYWORD_FAIL + " -- Could not find Element " + locator + " Not Found");
				return Constants.KEYWORD_FAIL + " -- Could not find Element " + locator + " Not Found";
			}
		} catch (Exception e) {
			logger.error("Exception while verifying the element state.");
			return Constants.KEYWORD_FAIL + " -- Not able verify the element state";
		}
	}
	public String getelementfromList(String object,String data){
		
		String status=Constants.KEYWORD_FAIL;
		List<WebElement> list=getWebElements(UIMap.getProperty(object));
		if(list.size()!=0){
		int index = getRandomInt(0, list.size()-1);
		
		WebElement element=list.get(index);
		if(element!=null){
		element.click();
		status=Constants.KEYWORD_PASS;
		}
		}
		
		return status;
	}
	
//########################     MyKeyWords    #####################################
	
	
}