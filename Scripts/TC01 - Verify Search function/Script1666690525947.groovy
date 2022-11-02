import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import groovy.ui.SystemOutputInterceptor
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebElement as Keys

// Sign-in the page
WebUI.callTestCase(findTestCase('common/TC00 - Login'), [:], FailureHandling.STOP_ON_FAILURE)

// Select search condition for Employment Status field
WebUI.click(findTestObject('General/DropdownButton', [('fieldName') : 'Employment Status']))

WebUI.click(findTestObject('General/DropdownListbox', [('fieldName') : 'Employment Status', ('option') : 'Full-Time Contract']))

// Select search condition for Include field
WebUI.click(findTestObject('General/DropdownButton', [('fieldName') : 'Include']))

WebUI.click(findTestObject('General/DropdownListbox', [('fieldName') : 'Include', ('option') : 'Current and Past Employees']))

// Click on Search button
WebUI.click(findTestObject('PIMPage/btnSearch'))

WebUI.waitForElementPresent(findTestObject('PIMPage/lblRecordNum'), 10, FailureHandling.STOP_ON_FAILURE)

// Verify that the result of the table is displayed as  '(*) Records Found' and * is more than 1
String strSearchResult = WebUI.getText(findTestObject('PIMPage/lblRecordNum'))

int numOfRecord = CustomKeywords.'common.BasePage.getStringBetweenTwoChar'(strSearchResult, '(', ')')

WebUI.verifyGreaterThan(numOfRecord, 1)

WebUI.scrollToElement(findTestObject('General/TableHeader'), 10)

// Click on Id header to sort Ascending
WebUI.click(findTestObject('PIMPage/colId'))

WebUI.click(findTestObject('PIMPage/mnuSort', [('index') : GlobalVariable.colId, ('sortType') : 'Ascending']))

// Verify that the IDs list is sorted as Ascending
boolean isSortedAsc = CustomKeywords.'main.PIMPage.isListSorted'(findTestObject('General/TableBody'), GlobalVariable.colId, 'Asc')
WebUI.verifyEqual(isSortedAsc, true)

// Click on Id header again to sort Descending
WebUI.click(findTestObject('PIMPage/colId'))

WebUI.click(findTestObject('PIMPage/mnuSort', [('index') : GlobalVariable.colId, ('sortType') : 'Decending']))

// Verify that the IDs list is sorted as Descending
boolean isSortedDesc = CustomKeywords.'main.PIMPage.isListSorted'(findTestObject('General/TableBody'), GlobalVariable.colId, 'Desc')
WebUI.verifyEqual(isSortedDesc, true)

// Close the browser
WebUI.closeBrowser()
