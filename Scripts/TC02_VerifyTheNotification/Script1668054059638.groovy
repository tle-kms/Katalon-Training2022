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
import internal.GlobalVariable
import main.PIMPage

import org.openqa.selenium.Keys as Keys

"TEST DATA"
def employeeName = 'Dryan'
def employeeId = '12345'

"Instance"
def pimPage = new PIMPage()

"TEST STEPS"
"Step 1: Sign-in the site"
WebUI.callTestCase(findTestCase('common/TC00_Login'), [:], FailureHandling.STOP_ON_FAILURE)

"Step 2: Navigate to the 'PIM' page"
pimPage.navigateToLeftMenu('PIM')

"Step 3: Fill in the Employee Name"
pimPage.fillText('Employee Name', employeeName)

"Step 4: Fill in the Employee Id"
pimPage.fillText('Employee Id', employeeId)

"Step 5: Select Employment Status = 'Freelance'"
pimPage.selectItemInDropdown('Employment Status', 'Freelance')

"Step 6: Select Include = 'Current and Past Employees'"
pimPage.selectItemInDropdown('Include', 'Current and Past Employees')

"Step 7: Select Job Title = 'QA Engineer'"
pimPage.selectItemInDropdown('Job Title', 'QA Engineer')

"Step 8: Select Sub Unit = 'Engineering'"
pimPage.selectItemInDropdown('Sub Unit', 'Engineering')

"Step 9: Click on Search button"
pimPage.clickSearch()

"Step 10: Verify that A notification is displayed as 'No Records Found'"
pimPage.verifyToastMessage('No Records Found')

"Step 11: Verify that the result of the search table is displayed as 'No Records Found'"
pimPage.verifyMessageNoRecordsFound()

"Step 12: Close the browser"
WebUI.closeBrowser()
