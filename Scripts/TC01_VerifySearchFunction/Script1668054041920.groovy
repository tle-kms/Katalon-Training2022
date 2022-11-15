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

import common.WebGUI
import groovy.ui.SystemOutputInterceptor as SystemOutputInterceptor
import internal.GlobalVariable
import main.PIMPage

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebElement as Keys

def pimPage = new PIMPage()

"TEST STEPS"

"Step 1: Sign-in the page"
WebUI.callTestCase(findTestCase('common/TC00_Login'), [:], FailureHandling.STOP_ON_FAILURE)

"Step 2: Navigate to the 'PIM' page"
pimPage.navigateToLeftMenu('PIM')

"Step 3: Select Employment Status = 'Full-Time Contract'"
pimPage.selectItemInDropdown('Employment Status', 'Full-Time Contract')

"Step 4: Select Include = 'Current and Past Employees'"
pimPage.selectItemInDropdown('Include', 'Current and Past Employees')

"Step 5: Click on Search button"
pimPage.clickSearch()

"Step 6: Verify that the result of the table is displayed as  \'(*) Records Found\' and * is more than 1"
pimPage.verifySearchResultGreaterThan(findTestObject('PIMPage/lblRecordNum'), 1)

"Step 7: Click on Id header"
pimPage.clickOnIdColumn()

"Step 8: Select option sort by Ascending"
pimPage.selectSortType(GlobalVariable.colId, 'Ascending')

"Step 9: Verify that the IDs list is sorted as Ascending"
pimPage.verifyIdColumnIsSorted(GlobalVariable.colId, 'Asc')

"Step 10: Click on Id header again"
pimPage.clickOnIdColumn()

"Step 11: Select option sorting Descending"
pimPage.selectSortType(GlobalVariable.colId, 'Decending')

"Step 12: Verify that the IDs list is sorted as Descending"
pimPage.verifyIdColumnIsSorted(GlobalVariable.colId, 'Desc')

"Step 13: Close the browser"
WebUI.closeBrowser()

