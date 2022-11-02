package common
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

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

import internal.GlobalVariable
import main.LeavePage

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

import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import org.openqa.selenium.*

class WebGUI extends WebUiBuiltInKeywords  {
	/**
	 * Count total number of a list
	 */
	@Keyword
	static int countListNumber(TestObject table) {
		WebElement tblBody = WebUiBuiltInKeywords.findWebElement(table)
		List<WebElement> listElements = tblBody.findElements(By.xpath("./div[@class='oxd-table-card']"))
		return listElements.size()
	}

	/**
	 * getCellValue
	 */
	@Keyword
	def String getCellValue(TestObject table, int row, int col) {
		//		if(countListNumber(table) < row) { return 'chet tit' }
		WebElement tblBody = WebUiBuiltInKeywords.findWebElement(table)
		WebElement cell = tblBody.findElement(By.xpath("./div[@class='oxd-table-card'][" + row + "]//div[@role='cell'][" + col + "]"))
		return cell.getText()
	}

	/**
	 * get rowID of the leave just submitted
	 */
	@Keyword
	def int getRowIdOfLeaveRecord(TestObject listObject, String strDate, int colDate) {
		WebElement obj = WebUiBuiltInKeywords.findWebElement(listObject)
		int row
		for(int i = countListNumber(listObject); i > 1; i--) {
			WebElement element = obj.findElement(By.xpath("./div[@class='oxd-table-card']["
					+ i + "]//div[@role='cell'][" + colDate + "]"))
			String str = element.getText().toString()
			if(	str.equals(strDate)
			&& (WebUiBuiltInKeywords.verifyElementPresent(findTestObject('LeavePage/MyLeavePage/btnAction'
			, [('row') : i
				, ('col') : GlobalVariable.colActions]), 10
			, FailureHandling.CONTINUE_ON_FAILURE)) == true) {
				//			&& WebUiBuiltInKeywords.verifyElementVisible(findTestObject('LeavePage/MyLeavePage/btnAction'
				//														, [('row') : i
				//														, ('col') : GlobalVariable.colActions]))) {
				row = i
				break
			}
		}
		return row
	}


	/**
	 * Get attribute values of a list
	 */
	@Keyword
	static List<String> getListAttributeValues(TestObject listObject, int colIndex) {
		WebElement obj = WebUiBuiltInKeywords.findWebElement(listObject)
		List<String> items = new ArrayList<String>()
		for(int i = countListNumber(listObject); i > 1; i--) {
			WebElement element = obj.findElement(By.xpath("./div[@class='oxd-table-card'][" + i + "]//div[@role='cell'][" + colIndex + "]"))
			items.add(element.getText())
		}
		return items
	}

	/**
	 * Verified toast message
	 */
	@Keyword
	def void verifyToastMessage(String expectedMessage) {
		String toastMessage = WebUiBuiltInKeywords.getText(findTestObject('General/toastMessage'))
		WebUiBuiltInKeywords.verifyEqual(toastMessage, expectedMessage)
	}

	/**
	 * verify the status of record
	 */
	@Keyword
	def void verifyStatusOfLeave(String letter, String status) {
		// identify position of the new record firstly
		LeavePage leavePage = new LeavePage()
		BasePage basePage = new BasePage()
		//		String today = basePage.getDateToday()
		WebUiBuiltInKeywords.scrollToElement(findTestObject('General/TableHeader'), 10)
		//		GlobalVariable.row = getRowIdOfLeaveRecord(findTestObject('General/TableBody')
		//				, GlobalVariable.today
		//				, GlobalVariable.colDate)
		String cell = getCellValue(findTestObject('General/TableBody')
				, GlobalVariable.row
				, GlobalVariable.colStatus)
		String cellValue = leavePage.getFirtStringBeforeAChar(cell, letter)
		WebUiBuiltInKeywords.verifyMatch(cellValue, status, false, FailureHandling.STOP_ON_FAILURE)
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