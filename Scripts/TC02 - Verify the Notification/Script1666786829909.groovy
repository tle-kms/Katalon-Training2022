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
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

// Sign-in the site
WebUI.callTestCase(findTestCase('common/TC00 - Login'), [:], FailureHandling.STOP_ON_FAILURE)

// Fill in Employee Name, Employee Id
WebUI.sendKeys(findTestObject('PIMPage/Textbox', [('fieldName') : 'Employee Name']), GlobalVariable.employeeName)

WebUI.sendKeys(findTestObject('PIMPage/Textbox', [('fieldName') : 'Employee Id']), GlobalVariable.employeeId)

// Select search condition for Employment Status field
WebUI.click(findTestObject('General/DropdownButton', [('fieldName') : 'Employment Status']))

WebUI.click(findTestObject('General/DropdownListbox', [('fieldName') : 'Employment Status', ('option') : 'Freelance']))

// Select search condition for Include field
WebUI.click(findTestObject('General/DropdownButton', [('fieldName') : 'Include']))

WebUI.click(findTestObject('General/DropdownListbox', [('fieldName') : 'Include', ('option') : 'Current and Past Employees']))

// Select search condition for Job Title field
WebUI.click(findTestObject('General/DropdownButton', [('fieldName') : 'Job Title']))

WebUI.click(findTestObject('General/DropdownListbox', [('fieldName') : 'Job Title', ('option') : 'QA Engineer']))

// Select search condition for Sub Unit field
WebUI.click(findTestObject('General/DropdownButton', [('fieldName') : 'Sub Unit']))

WebUI.click(findTestObject('General/DropdownListbox', [('fieldName') : 'Sub Unit', ('option') : 'Engineering']))

// Click on Search button
WebUI.click(findTestObject('PIMPage/btnSearch'))

// Verify that A notification is displayed as 'No Records Found'
CustomKeywords.'common.WebGUI.verifyToastMessage'('No Records Found')

// Verify that The result of the search table is displayed as 'No Records Found'
String strSearchResult = WebUI.getText(findTestObject('PIMPage/lblRecordNum'))

WebUI.verifyEqual(strSearchResult, 'No Records Found')

// close the browser
WebUI.closeBrowser()
