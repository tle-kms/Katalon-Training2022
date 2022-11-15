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

import common.BasePage
import common.WebGUI
import internal.GlobalVariable
import main.LeavePage
import main.PIMPage

import org.openqa.selenium.Keys as Keys

"Test data"
def note = 'Take PTO'

"Instances"
def leavePage = new LeavePage()
def pimPage = new PIMPage()

"TEST STEPS"
"Step 1: Sign-in the site"
WebUI.callTestCase(findTestCase('common/TC00_Login'), [:], FailureHandling.STOP_ON_FAILURE)

"Step 2: Navigate to 'Leave' page"
pimPage.navigateToLeftMenu('Leave')

"Step 3: Navigate to 'Apply' tab"
leavePage.navigateToTopMenu('Apply')

"Step 4: Wait for the field 'Leave Type' be visibled completely"
WebUI.waitForElementVisible(findTestObject('LeavePage/ApplyPage/btnApplyPage'
											, [('fieldName') : 'Leave Type']), 10)

"Step 5: Select Leave Type = 'CAN - Bereavement'"
leavePage.selectItemInDropdown('Leave Type', 'CAN - Bereavement')

"Step 6: get the current 'Leave Balance' amount"
float currAmount = leavePage.getLeaveBalanceAmount()

"Step 7: get the date of Today"
leavePage.setGlobalVariableToday()

"Step 8: Fill From Date = 'Today'"
leavePage.fillText('From Date', GlobalVariable.today)

"Step 9: Fill To Date = 'Today'"
leavePage.fillText('To Date', GlobalVariable.today)

"Step 10: Select Duration = 'Full Day'"
leavePage.selectItemInDropdown('Duration', 'Full Day')

"Step 11: Fill in 'Comments'"
leavePage.enterANote(note)

"Step 12: Click on Apply button"
leavePage.clickApply()

"Step 13: Verify that A notification is displayed as 'Successfully Saved"
pimPage.verifyToastMessage('Successfully Saved')

"Step 14: Select Leave Type = 'CAN - Bereavement' again"
leavePage.selectItemInDropdown('Leave Type', 'CAN - Bereavement')

"Step 15: Get the current Amount of 'Leave Balance' after applying a leave"
float lastAmount = leavePage.getLeaveBalanceAmount()

"Step 16: Verify Amount of 'Leave Balance' is reduced by 1"
WebUI.verifyEqual(currAmount - 1, lastAmount)

"Step 17: Navigate to 'My Leave' tab"
leavePage.navigateToTopMenu('My Leave')

"Step 18: Set value for GlobalVariable.row"
leavePage.setGlobalVariableRow()

"Step 19: Verify that the status of new record is 'Pending Approval'"
leavePage.verifyStatusOfLeave('l', 'Pending Approva')

"Step 20: Click on Cancel button at the new record just submitted"
leavePage.clickAction(GlobalVariable.row, GlobalVariable.colActions)

"Step 21: Verify that A notification is displayed as 'Successfully Updated'"
pimPage.verifyToastMessage('Successfully Updated')

"Step 22: Verify that the status of the record is changed to 'Cancelled'"
leavePage.verifyStatusOfLeave(' ', 'Cancelled')

"Step 23: Close the browser"
WebUI.closeBrowser()

