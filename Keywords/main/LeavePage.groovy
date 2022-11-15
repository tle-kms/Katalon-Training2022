package main
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

import common.BasePage
import common.WebGUI
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
import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.webui.exception.WebElementNotFoundException


class LeavePage extends BasePage {
	/** get Leave Balance number */
	@Keyword
	def String getFirtStringBeforeAChar(String strInput, String ch) {
		return strInput.split(ch)[0]
	}

	/** convert string to float */
	@Keyword
	def float convertStringToFloat(String strInput) {
		return strInput as float
	}

	/** get the amount of Leave Balance */
	@Keyword
	def float getLeaveBalanceAmount() {
		String leaveBalance = WebUiBuiltInKeywords.getText(findTestObject('LeavePage/ApplyPage/txtLeaveBalance'))
		String strAmount = getFirtStringBeforeAChar(leaveBalance,' ')
		return convertStringToFloat(strAmount)
	}

	/** Navigate to a top menu item */
	@Keyword
	def void navigateToTopMenu(String menuName) {
		WebUiBuiltInKeywords.click(findTestObject('LeavePage/mnuTopbar'
				, [('mnuItem') : menuName]))
	}

	/** Select an item in a dropdown */
	@Keyword
	def selectItemInDropdown(String dropdownName, String item) {
		WebUiBuiltInKeywords.click(findTestObject('LeavePage/ApplyPage/btnApplyPage'
				, [('fieldName') : dropdownName]))
		WebUiBuiltInKeywords.click(findTestObject('General/lstDropdownGeneral'
				, [('fieldName') : dropdownName
					, ('option') : item]))
	}

	/** Fill in data into a textbox */
	@Keyword
	def void fillText(String fieldName, String value) {
		WebUiBuiltInKeywords.sendKeys(findTestObject('LeavePage/txtLeavePage'
				, [('fieldName') : fieldName])
				, Keys.chord(Keys.CONTROL, 'a'))
		WebUiBuiltInKeywords.sendKeys(findTestObject('LeavePage/txtLeavePage'
				, [('fieldName') : fieldName])
				, Keys.chord(Keys.DELETE))
		WebUiBuiltInKeywords.setText(findTestObject('LeavePage/txtLeavePage'
				, [('fieldName') : fieldName])
				, value)
	}

	/** Enter a Note */
	@Keyword
	def enterANote(String value) {
		WebUiBuiltInKeywords.setText(findTestObject('LeavePage/ApplyPage/txaNote'
				, [('fieldName') : 'Comments']), value)
	}

	/** Click on Apply button */
	@Keyword
	def clickApply() {
		WebUiBuiltInKeywords.scrollToElement(findTestObject('LeavePage/ApplyPage/btnApply'), 10)
		WebUiBuiltInKeywords.click(findTestObject('LeavePage/ApplyPage/btnApply'))
	}

	/** Click on Action button */
	@Keyword
	def clickAction(int row, int col) {
		WebUiBuiltInKeywords.scrollToElement(findTestObject('General/tblHeaderGeneral'), 10)
		WebUiBuiltInKeywords.click(findTestObject('LeavePage/MyLeavePage/btnAction'
				, [('row') : row
					, ('col') : col]))
	}

	/** get Cell Value */
	@Keyword
	def String getCellValue(TestObject table, int row, int col) {
		WebElement tblBody = WebUiBuiltInKeywords.findWebElement(table)
		WebElement cell = tblBody.findElement(By.xpath("./div[@class='oxd-table-card'][" + row + "]//div[@role='cell'][" + col + "]"))
		return cell.getText()
	}

	/** get rowID of the leave just submitted */
	@Keyword
	def static getRowIdOfLeaveRecord(TestObject listObject, String strDate, int colDate) {
		WebElement obj = WebUiBuiltInKeywords.findWebElement(listObject)
		int row
		for(int i = countListNumber(listObject); i > 1; i--) {
			WebElement element = obj.findElement(By.xpath("./div[@class='oxd-table-card']["
					+ i + "]//div[@role='cell']["
					+ colDate + "]"))
			String str = element.getText().toString()
			if(	str.equals(strDate)
			&& (WebUiBuiltInKeywords.verifyElementPresent(findTestObject('LeavePage/MyLeavePage/btnAction'
			, [('row') : i
				, ('col') : GlobalVariable.colActions])
			, 10
			, FailureHandling.CONTINUE_ON_FAILURE)) == true) {
				row = i
				break
			}
		}
		return row
	}

	/** verify the status of record */
	@Keyword
	def verifyStatusOfLeave(String letter, String status) {
		// identify position of the new record firstly
		WebUiBuiltInKeywords.scrollToElement(findTestObject('General/tblHeaderGeneral'), 10)
		String cell = getCellValue(findTestObject('General/tblBodyGeneral')
				, GlobalVariable.row
				, GlobalVariable.colStatus)
		String cellValue = getFirtStringBeforeAChar(cell, letter)
		WebUiBuiltInKeywords.verifyMatch(cellValue, status, false, FailureHandling.STOP_ON_FAILURE)
	}

	/** set value for Global variable */
	@Keyword
	def setGlobalVariableToday() {
		GlobalVariable.today = getDateToday()
	}

	/** identify the rowId */
	@Keyword
	def setGlobalVariableRow() {
		GlobalVariable.row = getRowIdOfLeaveRecord(findTestObject('General/tblBodyGeneral')
				, GlobalVariable.today
				, GlobalVariable.colDate)
	}
}