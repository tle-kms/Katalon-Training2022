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


import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import java.time.*
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testdata.CSVData
import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import com.kms.katalon.core.testdata.reader.CSVSeparator

class PIMPage extends BasePage {
	/** Navigate to a top menu item */
	@Keyword
	def void navigateToTopMenu(String menuName) {
		WebUiBuiltInKeywords.click(findTestObject('PIMPage/mnuTop', [('mnuItem') : menuName]))
	}

	/** Navigate to a left menu item */
	@Keyword
	def void navigateToLeftMenu(String menuName) {
		WebUiBuiltInKeywords.click(findTestObject('MainMenu/mnuItem', [('mnuItem') : menuName]))
	}

	/** Verify that the list is sorted ascending */
	@Keyword
	def boolean isListSorted(TestObject listObject, int colIndex, String sortType) {
		def webGUI = new WebGUI()
		boolean status
		List<String> listItems = webGUI.getListAttributeValues(listObject, colIndex)
		switch(sortType) {
			case 'Asc':
				for(int i = 0; i < listItems.size() - 1 ; i++) {
					if (listItems.get(i).toInteger() > listItems.get(i+1).toInteger()) {
						status = false
						break
					}
				}
			case 'Desc':
				for(int j = 0; j < listItems.size() - 1 ; j++) {
					if (listItems.get(j).toInteger() < listItems.get(j+1).toInteger()) {
						status = false
						break
					}
				}
			default:
				status = true
				break
		}
		return status
	}

	/** Verify the Ids list sorted */
	@Keyword
	def void verifyIdColumnIsSorted(int column, String sortType) {
		WebUiBuiltInKeywords.verifyEqual(isListSorted(findTestObject('General/tblBodyGeneral')
				, column, sortType)
				, true)
	}


	/** Append to csv file */
	@Keyword
	def void deleteFile(String filePath) {
		try {
			Files.delete(Paths.get(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** set the relative path for the csv file */
	@Keyword
	def setRelativePath() {
		GlobalVariable.path = System.getProperty("user.home") + "\\Downloads\\importData.csv"
	}

	/** Append to csv file */
	@Keyword
	def appendToCsvFile() {
		def csvFile = new File (GlobalVariable.path)
		GlobalVariable.employeeFN = "Test" + randomAlphabetic(5).toLowerCase()
		GlobalVariable.id = Instant.now().getEpochSecond().toString()
		String newRow = GlobalVariable.employeeFN + ",," + GlobalVariable.employeeLN + ","+ GlobalVariable.id + ",,,,,,,,,,,,,,,,,,"
		csvFile.append("\n")
		csvFile.append(newRow)
	}

	/** Upload csv file */
	@Keyword
	def uploadCsvFile(String path) {
		WebUiBuiltInKeywords.waitForElementPresent(findTestObject('Object Repository/PIMPage/btnBrowse'), 10)
		WebUiBuiltInKeywords.uploadFile(findTestObject('Object Repository/PIMPage/btnBrowse'), path)
	}

	/** Verify that the result of the table is greater than a number */
	@Keyword
	def verifySearchResultGreaterThan(TestObject obj, int num) {
		String strSearchResult = WebUiBuiltInKeywords.getText(obj)
		int numOfRecord = getStringBetweenTwoChar(strSearchResult, '(', ')').toInteger()
		def webGUI = new WebGUI()
		try {
			webGUI.verifyGreaterThan(numOfRecord, num)
		}
		catch (Exception e) {
			this.println('No records found')
		}
		
	}

	/** Select an item in a dropdown */
	@Keyword
	def selectItemInDropdown(String dropdownName, String item) {
		WebUiBuiltInKeywords.click(findTestObject('General/btnDropDownGeneral'
				, [('fieldName') : dropdownName]))
		WebUiBuiltInKeywords.click(findTestObject('General/lstDropdownGeneral'
				,[('fieldName') : dropdownName
					, ('option') : item]))
	}

	/** Click on the Id header column */
	@Keyword
	def clickOnIdColumn() {
		WebUiBuiltInKeywords.scrollToElement(findTestObject('General/tblHeaderGeneral'), 10)
		WebUiBuiltInKeywords.click(findTestObject('PIMPage/dgdId'))
	}

	/** Select a sorting option */
	@Keyword
	def selectSortType(int column, String sortType) {
		WebUiBuiltInKeywords.click(findTestObject('PIMPage/mnuSort', [('index') : column
			, ('sortType') : sortType]))
	}

	/** Click on Action (Delete or Cancel) */
	@Keyword
	def clickAction(String label) {
		WebUiBuiltInKeywords.click(findTestObject('PIMPage/btnActions', [('actionItem') : label]))
	}

	/** Click on Confirmation button (Yes or No) */
	@Keyword
	def clickConfirm(String confirm) {
		WebUiBuiltInKeywords.click(findTestObject('PIMPage/btnConfirm', [('confirmOption') : confirm]))
	}

	/** Click on Search button */
	@Keyword
	def clickSearch() {
		WebUiBuiltInKeywords.click(findTestObject('PIMPage/btnSearch'))
	}

	/** Click on Configuration on top menu */
	@Keyword
	def clickConfiguration() {
		WebUiBuiltInKeywords.click(findTestObject('PIMPage/mnuConfiguration'))
	}

	/** Select Configuration > Data Import on top menu */
	@Keyword
	def clickDataImport() {
		WebUiBuiltInKeywords.click(findTestObject('PIMPage/mnuSubConfiguration'
				, [('mnuItem') : 'Data Import']))
	}

	/** Click on Download link */
	@Keyword
	def clickDownload() {
		WebUiBuiltInKeywords.click(findTestObject('PIMPage/lnkDownload'))
	}

	/** Select the Employee checkbox */
	@Keyword
	def selectEmployeeCheckbox() {
		WebUiBuiltInKeywords.click(findTestObject('Object Repository/PIMPage/chkEmployee'))
	}

	/** Fill in data into a textbox */
	@Keyword
	def fillText(String fieldName, String value) {
		WebUiBuiltInKeywords.waitForElementPresent(findTestObject('PIMPage/txtPIMPage'
				, [('fieldName') : fieldName]), 10)
		WebUiBuiltInKeywords.sendKeys(findTestObject('PIMPage/txtPIMPage'
				, [('fieldName') : fieldName]), value)
	}

	/** Verify message 'No records found' */
	@Keyword
	def verifyMessageNoRecordsFound() {
		String strSearchResult = WebUiBuiltInKeywords.getText(findTestObject('PIMPage/lblRecordNum'))
		WebUiBuiltInKeywords.verifyEqual(strSearchResult, 'No Records Found')
	}

	/** Verify the imported Employee displayed in the grid */
	@Keyword
	def verifyImportedEmployee() {
		def leavePage = new LeavePage()
		WebUiBuiltInKeywords.scrollToElement(findTestObject('General/tblHeaderGeneral'), 10)
		WebUiBuiltInKeywords.verifyEqual(leavePage.getCellValue(findTestObject('Object Repository/General/tblBodyGeneral')
				, 1
				, GlobalVariable.colId)
				, GlobalVariable.id)
		WebUiBuiltInKeywords.verifyEqual(leavePage.getCellValue(findTestObject('Object Repository/General/tblBodyGeneral')
				, 1
				, GlobalVariable.colFN)
				, GlobalVariable.employeeFN)
		WebUiBuiltInKeywords.verifyEqual(leavePage.getCellValue(findTestObject('Object Repository/General/tblBodyGeneral')
				, 1
				, GlobalVariable.colLN)
				, GlobalVariable.employeeLN)
	}
}