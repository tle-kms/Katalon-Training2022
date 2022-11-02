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
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

//Sign-in the site
WebUI.callTestCase(findTestCase('common/TC00 - Login'), [:], FailureHandling.STOP_ON_FAILURE)

// On PIM page, select Configuration
WebUI.click(findTestObject('PIMPage/mnuConfiguration'))

// Remove the csv file on local
CustomKeywords.'main.PIMPage.deleteFile'(GlobalVariable.path)

// Select Data Import
WebUI.click(findTestObject('PIMPage/mnuSubConfiguration', [('mnuItem') : 'Data Import']))

// Click on Download link
WebUI.click(findTestObject('PIMPage/lnkDownload'))

WebUI.delay(5)
// Add 1 record into the csv file
CustomKeywords.'main.PIMPage.appendToCsvFile'()
// CustomKeywords.'main.PIMPage.test'()

// Upload the edited file to the portal
WebUI.waitForElementPresent(findTestObject('Object Repository/PIMPage/btnBrowse'), 10)
CustomKeywords.'main.PIMPage.uploadCsvFile'(findTestObject('Object Repository/PIMPage/btnBrowse'), GlobalVariable.path)

// CLick on Apply button
WebUI.click(findTestObject('Object Repository/PIMPage/btnSearch'))

// Verify that A notification is displayed as 'Number of Records Imported'
CustomKeywords.'common.WebGUI.verifyToastMessage'('Number of Records Imported: 1')

// Navigate to the Employee List page
WebUI.click(findTestObject('PIMPage/mnuTop', [('mnuItem') : 'Employee List']))

// Search the Employee just inserted
// WebUI.setText(findTestObject('PIMPage/Textbox', [('fieldName') : 'Employee Id']), GlobalVariable.id)]
WebUI.waitForElementPresent(findTestObject('PIMPage/Textbox', [('fieldName') : 'Employee Id']), 10)

WebUI.setText(findTestObject('PIMPage/Textbox', [('fieldName') : 'Employee Id']), GlobalVariable.id, FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/PIMPage/btnSearch'))

// Verify that the inputted name is displayed in the list
WebUI.scrollToElement(findTestObject('General/TableHeader'), 10)

WebUI.verifyEqual(CustomKeywords.'common.WebGUI.getCellValue'(
															findTestObject('Object Repository/General/TableBody'), 1, 
															GlobalVariable.colId), GlobalVariable.id)

WebUI.verifyEqual(CustomKeywords.'common.WebGUI.getCellValue'(
															findTestObject('Object Repository/General/TableBody'), 1, 
															GlobalVariable.colFN), GlobalVariable.employeeFN)

WebUI.verifyEqual(CustomKeywords.'common.WebGUI.getCellValue'(
															findTestObject('Object Repository/General/TableBody'), 1, 
															GlobalVariable.colLN), GlobalVariable.employeeLN)

// select the check box and delete the Employee
WebUI.click(findTestObject('Object Repository/PIMPage/chkEmployee'))

WebUI.click(findTestObject('PIMPage/btnActions', [('actionItem') : 'trash']))

WebUI.click(findTestObject('PIMPage/btnConfirm', [('confirmOption') : ' Yes, Delete ']))

// Verify that A notification is displayed as 'Successfully Deleted'
CustomKeywords.'common.WebGUI.verifyToastMessage'('Successfully Deleted')

// Remove the csv file on local
CustomKeywords.'main.PIMPage.deleteFile'(GlobalVariable.path)

 WebUI.closeBrowser()

