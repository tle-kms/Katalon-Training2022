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
	/** SubString between 2 characters */
	def String getStringBetweenTwoChar(String strInput, String strFrom, String strTo) {
		return strInput.substring(strInput.indexOf(strFrom) + 1, strInput.lastIndexOf(strTo))
	}

	/** get date of today */
	def static String getDateToday() {
		Date date = new Date()
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
		return sdf.format(date)
	}

	/** add days to a date */
	def String addDays(String date, int days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
		Calendar cal = Calendar.getInstance()
		cal.add(Calendar.DATE, days)
		return (String)(sdf.format(cal.getTime()))
	}

	/** enter a text */
	def void enterText(TestObject obj, String strInput) {
		int len = WebUiBuiltInKeywords.getText(obj).length()
		WebUiBuiltInKeywords.click(obj)
		for(int i = 0; i < len; i++) {
			WebUiBuiltInKeywords.sendKeys(obj, Keys.chord(Keys.BACK_SPACE))
		}
		WebUiBuiltInKeywords.setText(obj, strInput)
	}

	/** Count total number of a list */
	static int countListNumber(TestObject table) {
		WebElement tblBody = WebUiBuiltInKeywords.findWebElement(table)
		List<WebElement> listElements = tblBody.findElements(By.xpath("./div[@class='oxd-table-card']"))
		return listElements.size()
	}

	/** Verified toast message */
	def void verifyToastMessage(String expectedMessage) {
		String toastMessage = WebUiBuiltInKeywords.getText(findTestObject('General/lblToastMessage'))
		WebUiBuiltInKeywords.verifyEqual(toastMessage, expectedMessage)
	}

	/** scroll to the table header */
	def void scrollToTheTable() {
		WebUiBuiltInKeywords.scrollToElement(findTestObject('General/tblHeaderGeneral'), 10)
	}
}