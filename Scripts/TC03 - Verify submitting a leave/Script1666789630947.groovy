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

//Sign-in the site
WebUI.callTestCase(findTestCase('common/TC00 - Login'), [:], FailureHandling.STOP_ON_FAILURE)

// Navigate to Leave page
WebUI.click(findTestObject('MainMenu/mnuItem', [('mnuItem') : 'Leave']))

// navigate to Apply tab
WebUI.click(findTestObject('LeavePage/mnuTopbar', [('mnuItem') : 'Apply']))
WebUI.waitForElementVisible(findTestObject('LeavePage/ApplyPage/Dropdown', [('fieldName') : 'Leave Type']), 10)

// Select Leave Type: CAN - Bereavement
WebUI.click(findTestObject('LeavePage/ApplyPage/Dropdown', [('fieldName') : 'Leave Type']))

WebUI.click(findTestObject('General/DropdownListbox', [('fieldName') : 'Leave Type', ('option') : 'CAN - Bereavement']))

// get the current 'Leave Balance' amount
float currAmount = CustomKeywords.'main.LeavePage.getLeaveBalanceAmount'()

// Fill Today into 'From Date'
GlobalVariable.today = CustomKeywords.'common.BasePage.getDateToday'()

WebUI.setText(findTestObject('LeavePage/Textbox', [('fieldName') : 'From Date']), GlobalVariable.today)

// Fill Today into 'To Date'
CustomKeywords.'common.BasePage.fillText'('To Date', GlobalVariable.today)

// Select Duration = Full Day
WebUI.click(findTestObject('LeavePage/ApplyPage/Dropdown', [('fieldName') : 'Duration']))

WebUI.click(findTestObject('General/DropdownListbox', [('fieldName') : 'Duration', ('option') : 'Full Day']))

// Fill comments
WebUI.setText(findTestObject('LeavePage/ApplyPage/TextArea', [('fieldName') : 'Comments']), 'Take PTO')

// Click on Apply button
WebUI.scrollToElement(findTestObject('LeavePage/ApplyPage/btnApply'), 10)

WebUI.click(findTestObject('LeavePage/ApplyPage/btnApply'))

// Verify that A notification is displayed as 'Successfully Saved'
CustomKeywords.'common.WebGUI.verifyToastMessage'('Successfully Saved')

// Verify Amount of 'Leave Balance' is reduced by 1
WebUI.click(findTestObject('LeavePage/ApplyPage/Dropdown', [('fieldName') : 'Leave Type']))

WebUI.click(findTestObject('General/DropdownListbox', [('fieldName') : 'Leave Type', ('option') : 'CAN - Bereavement']))

// take the 'Leave Balance' amount after applying a leave
float lastAmount = CustomKeywords.'main.LeavePage.getLeaveBalanceAmount'()

WebUI.verifyEqual(currAmount - 1, lastAmount)

// navigate to 'My Leave' tab
WebUI.click(findTestObject('LeavePage/mnuTopbar', [('mnuItem') : 'My Leave']))

// scroll to the table
WebUI.scrollToElement(findTestObject('General/TableHeader'), 10)

// set value for GlobalVariable.row 
GlobalVariable.row = CustomKeywords.'common.WebGUI.getRowIdOfLeaveRecord'(
														findTestObject('General/TableBody')
														, GlobalVariable.today
														, GlobalVariable.colDate)
// Verify that the status of new record is 'Pending Approval'
CustomKeywords.'common.WebGUI.verifyStatusOfLeave'('l', 'Pending Approva')

// Click on Cancel button
WebUI.scrollToElement(findTestObject('General/TableHeader'), 10)

 WebUI.click(findTestObject('LeavePage/MyLeavePage/btnAction', [('row') : GlobalVariable.row, ('col') : GlobalVariable.colActions]))
//WebUI.click(findTestObject('PIMPage/btnCancel', [('date') : GlobalVariable.today]))

// Verify that A notification is displayed as 'Successfully Updated'
CustomKeywords.'common.WebGUI.verifyToastMessage'('Successfully Updated')

// Verify that The status is changed to 'Cancelled'
CustomKeywords.'common.WebGUI.verifyStatusOfLeave'(' ', 'Cancelled')

// close the browser
WebUI.closeBrowser()

