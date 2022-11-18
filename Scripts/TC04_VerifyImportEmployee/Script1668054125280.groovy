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
import main.LeavePage
import main.PIMPage
import main.PIM_ConfigurationPage

import org.openqa.selenium.Keys as Keys
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

"Instances"
def pimPage = new PIMPage()
def pim_confPage = new PIM_ConfigurationPage()

"TEST STEPS"
"Step 1: Sign-in the site"
WebUI.callTestCase(findTestCase('common/TC00_Login'), [:], FailureHandling.STOP_ON_FAILURE)

"Step 2: Navigate to the 'PIM' page"
pimPage.navigateToLeftMenu('PIM')

"Step 3: On PIM page, select Configuration"
pimPage.clickConfiguration()

"Step 4: Select 'Data Import'"
pimPage.clickDataImport()

"Step 5: Set relative path to the csv file"
pimPage.setRelativePath()

"Step 6: Remove the csv file on local"
pimPage.deleteFile(GlobalVariable.path)

"Step 7: Click on Download link"
pimPage.clickDownload()

"Step 8: Verify that the CSV file downloaded successfully"
//WebUI.delay(5)
pim_confPage.verifyCSVFileDownloaded(GlobalVariable.pathWithoutFile, 'importData.csv')

"Step 9: Add 1 record into the downloaded csv file"
pimPage.appendToCsvFile()

"Step 10: Upload the edited csv file to the portal"
pimPage.uploadCsvFile(GlobalVariable.path)

"Step 11: CLick on Apply button"
pimPage.clickSearch()

"Step 12: Verify that A notification is displayed as 'Number of Records Imported'"
pim_confPage.verifyPopupMessage('1 Record Successfully Imported')

"Step 13: Click OK button to close the modal"
pim_confPage.clickOK()

"Step 14: Navigate to the Employee List page"
pimPage.navigateToTopMenu('Employee List')

"Step 15: Fill in the Employee Id"
pimPage.fillText('Employee Id', GlobalVariable.id)

"Step 16: Click on Search button"
pimPage.clickSearch()

"Step 17: Verify that the imported Employee is displayed in the list"
pimPage.verifyImportedEmployee()

"Step 18: Select the check box at the record just imported"
pimPage.selectEmployeeCheckbox()

"Step 19: Click on Delete button"
pimPage.clickAction('trash')

"Step 20: Click on 'Yes' button in the confirm box"
pimPage.clickConfirm(' Yes, Delete ')

"Step 21: Verify that A notification is displayed as 'Successfully Deleted'"
pimPage.verifyToastMessage('Successfully Deleted')

"Step 22: Close the browser"
WebUI.closeBrowser()


