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

import internal.GlobalVariable

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import java.nio.file.Files
import java.nio.file.Paths

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.util.KeywordUtil

import org.testng.Assert

import com.kms.katalon.core.webui.exception.WebElementNotFoundException


class PIM_ConfigurationPage {
	/** Verify message in the modal */
	def verifyPopupMessage(String message) {
		String actualMessage = WebUiBuiltInKeywords.getText(findTestObject('PIMPage/ConfigurationPage/lblPopupMessage'))
		WebUiBuiltInKeywords.verifyEqual(actualMessage, message)
	}

	/** Click OK button in the modal */
	def clickOK() {
		WebUiBuiltInKeywords.click(findTestObject('PIMPage/ConfigurationPage/btnOK'))
	}
	
	/** check the file has been completely downloaded */
	def isFileDownloaded(String downloadPath, String fileName) {
		int i
		int j = 0
		for (i = 0; i < GlobalVariable.attempt; i++) {
			if (!Files.exists(Paths.get(downloadPath, fileName))) {
				WebUiBuiltInKeywords.delay(1)
				j++
			}
		}
		return j > i ? false : true
	}
	
	/** Verify the csv file downloaded successfully */
	def verifyCSVFileDownloaded(String downloadPath, String fileName) {
		WebUiBuiltInKeywords.verifyEqual(isFileDownloaded(downloadPath, fileName), true, FailureHandling.STOP_ON_FAILURE)
	}
	
}