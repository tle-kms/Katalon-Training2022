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

import common.WebGUI
import internal.GlobalVariable

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By


import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import java.time.*
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testdata.CSVData
import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import com.kms.katalon.core.testdata.reader.CSVSeparator

class PIMPage {
	/**
	 * Verify the rows of Table is as expected
	 */
	@Keyword
	def verifyTheTableRowsMatching(TestObject table, int rows) {
		def webGUI = new WebGUI()
		webGUI.verifyEqual(webGUI.countListNumber(table), rows)
	}

	/**
	 * Verify that the list is sorted ascending
	 */
	@Keyword
	def boolean isListSorted(TestObject listObject, int colIndex, String sortType) {
		def webGUI = new WebGUI()
		boolean status
		List<String> listItems = webGUI.getListAttributeValues(listObject, colIndex)
		switch(sortType) {
			case 'Asc':
				for(int i = 0; i < listItems.size() - 1 ; i++) {
					if (listItems.get(i).toInteger() > listItems.get(i+1).toInteger()) {
						status = false
						break
					}
				}
			case 'Desc':
				for(int j = 0; j < listItems.size() - 1 ; j++) {
					if (listItems.get(j).toInteger() < listItems.get(j+1).toInteger()) {
						status = false
						break
					}
				}
			default:
				status = true
				break
		}
		return status
	}

	/**
	 * Append to csv file
	 */
	@Keyword
	def void deleteFile(String filePath) {
		try {
			Files.delete(Paths.get(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Append to csv file
	 */
	@Keyword
	def appendToCsvFile() {
		GlobalVariable.path = System.getProperty("user.home") + "\\Downloads\\importData.csv"
		def csvFile = new File (GlobalVariable.path)
		GlobalVariable.employeeFN = "Test" + randomAlphabetic(5).toLowerCase()
		GlobalVariable.id = Instant.now().getEpochSecond().toString()
		String newRow = GlobalVariable.employeeFN + ",," + GlobalVariable.employeeLN + ","+ GlobalVariable.id + ",,,,,,,,,,,,,,,,,,"
		csvFile.append("\n")
		csvFile.append(newRow)
	}

	/**
	 * Upload csv file
	 */
	@Keyword
	def uploadCsvFile(TestObject obj, String path) {
		WebUiBuiltInKeywords.uploadFile(obj, path)
	}
}