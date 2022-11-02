package common
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.awt.RenderingHints.Key
import java.text.SimpleDateFormat
import java.util.concurrent.ConcurrentHashMap.KeySetView

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords

import bsh.StringUtil
import internal.GlobalVariable

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.util.internal.DateUtil
import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import org.openqa.selenium.Keys as Keys

class BasePage {
	/**
	 * SubString between 2 characters
	 */
	@Keyword
	def String getStringBetweenTwoChar(String strInput, String strFrom, String strTo) {
		return strInput.substring(strInput.indexOf(strFrom) + 1, strInput.lastIndexOf(strTo))
	}

	/**
	 * get date of today
	 */
	@Keyword
	def String getDateToday() {
		Date date = new Date()
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
		return sdf.format(date)
	}

	/**
	 * add days to a date
	 */
	@Keyword
	def String addDays(String date, int days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
		Calendar cal = Calendar.getInstance()
		cal.add(Calendar.DATE, days)
		return (String)(sdf.format(cal.getTime()))
	}

	/**
	 * fill a text
	 */
	@Keyword
	def void fillText(String fieldName, String strInput) {
		WebUiBuiltInKeywords.sendKeys(findTestObject('LeavePage/Textbox', [('fieldName') : fieldName]), Keys.chord(Keys.CONTROL, 'a'))
		WebUiBuiltInKeywords.sendKeys(findTestObject('LeavePage/Textbox', [('fieldName') : fieldName]), Keys.chord(Keys.DELETE))
		WebUiBuiltInKeywords.setText(findTestObject('LeavePage/Textbox', [('fieldName') : fieldName]), strInput)
	}

	/**
	 * enter a text
	 */
	@Keyword
	def void enterText(TestObject obj, String strInput) {
		int len = WebUiBuiltInKeywords.getText(obj).length()
		WebUiBuiltInKeywords.click(obj)
		for(int i = 0; i < len; i++) {
//			WebUiBuiltInKeywords.sendKeys(Keys.BACK_SPACE)
			WebUiBuiltInKeywords.sendKeys(obj, Keys.chord(Keys.BACK_SPACE))
		}
		WebUiBuiltInKeywords.setText(obj, strInput)
	}

	/**
	 * Refresh browser
	 */
	@Keyword
	def refreshBrowser() {
		KeywordUtil.logInfo("Refreshing")
		WebDriver webDriver = DriverFactory.getWebDriver()
		webDriver.navigate().refresh()
		KeywordUtil.markPassed("Refresh successfully")
	}

	/**
	 * Click element
	 * @param to Katalon test object
	 */
	@Keyword
	def clickElement(TestObject to) {
		try {
			WebElement element = WebUiBuiltInKeywords.findWebElement(to);
			KeywordUtil.logInfo("Clicking element")
			element.click()
			KeywordUtil.markPassed("Element has been clicked")
		} catch (WebElementNotFoundException e) {
			KeywordUtil.markFailed("Element not found")
		} catch (Exception e) {
			KeywordUtil.markFailed("Fail to click on element")
		}
	}

	/**
	 * Get all rows of HTML table
	 * @param table Katalon test object represent for HTML table
	 * @param outerTagName outer tag name of TR tag, usually is TBODY
	 * @return All rows inside HTML table
	 */
	@Keyword
	def List<WebElement> getHtmlTableRows(TestObject table, String outerTagName) {
		WebElement mailList = WebUiBuiltInKeywords.findWebElement(table)
		List<WebElement> selectedRows = mailList.findElements(By.xpath("./" + outerTagName + "/tr"))
		return selectedRows
	}
}