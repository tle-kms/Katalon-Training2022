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

// navigate to 'My Leave' tab
WebUI.click(findTestObject('LeavePage/mnuTopbar', [('mnuItem') : 'My Leave']))

// Verify that the status of new record is 'Pending Approval'
WebUI.scrollToElement(findTestObject('General/TableHeader'), 10)

int row = CustomKeywords.'common.WebGUI.getRowIdOfLeaveRecord'(findTestObject('General/TableBody'),'2022-11-01', 2)
println 'aaaa ' + row