/********************************************************************************* 
* Ephesoft is a Intelligent Document Capture and Mailroom Automation program 
* developed by Ephesoft, Inc. Copyright (C) 2015 Ephesoft Inc. 
* 
* This program is free software; you can redistribute it and/or modify it under 
* the terms of the GNU Affero General Public License version 3 as published by the 
* Free Software Foundation with the addition of the following permission added 
* to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED WORK 
* IN WHICH THE COPYRIGHT IS OWNED BY EPHESOFT, EPHESOFT DISCLAIMS THE WARRANTY 
* OF NON INFRINGEMENT OF THIRD PARTY RIGHTS. 
* 
* This program is distributed in the hope that it will be useful, but WITHOUT 
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
* FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more 
* details. 
* 
* You should have received a copy of the GNU Affero General Public License along with 
* this program; if not, see http://www.gnu.org/licenses or write to the Free 
* Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 
* 02110-1301 USA. 
* 
* You can contact Ephesoft, Inc. headquarters at 111 Academy Way, 
* Irvine, CA 92617, USA. or at email address info@ephesoft.com. 
* 
* The interactive user interfaces in modified source and object code versions 
* of this program must display Appropriate Legal Notices, as required under 
* Section 5 of the GNU Affero General Public License version 3. 
* 
* In accordance with Section 7(b) of the GNU Affero General Public License version 3, 
* these Appropriate Legal Notices must retain the display of the "Ephesoft" logo. 
* If the display of the logo is not reasonably feasible for 
* technical reasons, the Appropriate Legal Notices must display the words 
* "Powered by Ephesoft". 
********************************************************************************/ 

package com.ephesoft.dcma.lucene;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ephesoft.dcma.batch.service.BatchSchemaService;
import com.ephesoft.dcma.core.DCMAException;
import com.ephesoft.dcma.da.domain.BatchClass;
import com.ephesoft.dcma.da.domain.BatchInstance;
import com.ephesoft.dcma.da.id.BatchClassID;
import com.ephesoft.dcma.da.id.BatchInstanceID;
import com.ephesoft.dcma.da.service.BatchClassService;
import com.ephesoft.dcma.da.service.BatchInstanceService;
import com.ephesoft.dcma.lucene.service.SearchClassificationService;
import com.ephesoft.dcma.util.FileUtils;

/**
 * This is Junit test for LuceneService. It contains two positive test cases for generating confidence score for Tesseract batches
 * respectively.
 * 
 * 
 */
public class SearchClassificationServiceTest extends AbstractSearchClassificationTests {

	/**
	 * String constants.
	 */
	private static final String PROP_FILE_LUCENE_TEST = "test.properties";
	/**
	 * String constants.
	 */
	private static final String ACTUAL_OUTPUT_FOLDER = "actual.output.location";
	/**
	 * String constants.
	 */
	private static final String EXPECTED_OUTPUT_FOLDER = "expected.output.location";
	/**
	 * String constants.
	 */
	private static final String SAMPLES_FOLDER = "samples.location";
	/**
	 * String constants.
	 */
	private static final String BASE_FOLDER = "base.location";

	/**
	 * Instance of SearchClassificationService.
	 */
	@Autowired
	private transient SearchClassificationService searchClassService;
	/**
	 * Instance of BatchSchemaService.
	 */
	@Autowired
	private transient BatchSchemaService batchSchemaService;
	/**
	 * Instance of BatchInstanceService.
	 */
	@Autowired
	private transient BatchInstanceService batchInstanceService;
	/**
	 * Instance of BatchClassService.
	 */
	@Autowired
	private transient BatchClassService batchClassService;

	/**
	 * Batch instance for test scenario.
	 */
	private transient String batchInstanceIdSuccess1;
	/**
	 * Batch instance for test scenario.
	 */
	private transient String batchInstanceIdSuccess2;

	/**
	 * The folder where input files are kept and output files will be written.
	 */
	private transient String actualOutputFolder;

	/**
	 * The folder containing expected output batch xml.
	 */
	private transient String expectedOutputFolder;

	/**
	 * The folder containing sample xml file.
	 */
	private transient String samplesFolder;
	/**
	 * Variable stores initial local folder location.
	 */
	private transient String localFolderLocation;
	/**
	 * Variable stores initial base folder location.
	 */
	private transient String baseFolderLocation;
	/**
	 * Batch class for test scenario.
	 */
	private transient String batchClassId1;
	/**
	 * Batch class for test scenario.
	 */
	private transient String batchClassId2;

	/**
	 * This method prepares and initializes all the resources that would be required by the plugin. It also indexes a well-defined set
	 * of files for each page type.
	 */
	@Before
	public void setUp() {
		boolean result = false;
		Properties prop = new Properties();
		String testFolderLocation;
		localFolderLocation = batchSchemaService.getLocalFolderLocation();
		testFolderLocation = batchSchemaService.getTestFolderLocation();
		batchInstanceIdSuccess1 = "BI7C";
		batchInstanceIdSuccess2 = "BI7B";
		batchClassId1 = "BC1";
		batchClassId2 = "BC2";
		try {
			prop.load(SearchClassificationServiceTest.class.getClassLoader().getResourceAsStream(PROP_FILE_LUCENE_TEST));
		} catch (IOException e) {
			assertTrue(e.getMessage(), result);
		}
		actualOutputFolder = testFolderLocation + File.separator + prop.getProperty(ACTUAL_OUTPUT_FOLDER);
		expectedOutputFolder = testFolderLocation + File.separator + prop.getProperty(EXPECTED_OUTPUT_FOLDER);
		samplesFolder = testFolderLocation + File.separator + prop.getProperty(SAMPLES_FOLDER);
		baseFolderLocation = batchSchemaService.getBaseFolderLocation();
		batchSchemaService.setLocalFolderLocation(actualOutputFolder);
		batchSchemaService.setBaseFolderLocation(testFolderLocation + File.separator + prop.getProperty(BASE_FOLDER));

	}

	/**
	 * This is a test scenario for generating confidence score.
	 */
	@Test
	public void testSearchClassificationPlugin() {
		boolean result = false;
		boolean created = false;
		BatchClass initialBatchClass = new BatchClass();
		try {
			BatchInstance batchInstance = batchInstanceService.getBatchInstanceByIdentifier(batchInstanceIdSuccess1);
			if (null == batchInstance) {
				batchInstance = new BatchInstance();
				batchInstance.setIdentifier(batchInstanceIdSuccess1);
				BatchClass batchClass = batchClassService.getBatchClassByIdentifier(batchClassId1);
				batchInstance.setBatchClass(batchClass);
				batchInstanceService.createBatchInstance(batchInstance);
				created = true;
			} else {
				initialBatchClass = batchInstance.getBatchClass();
				BatchClass batchClass = batchClassService.getBatchClassByIdentifier(batchInstanceIdSuccess1);
				batchInstance.setBatchClass(batchClass);

			}
			searchClassService.learnSampleHOCR(new BatchClassID(batchClassId1), true);
			searchClassService.generateConfidenceScore(new BatchInstanceID(batchInstanceIdSuccess1), null);
			// compare two XML's
			compareXMLs(actualOutputFolder, expectedOutputFolder, batchInstanceIdSuccess1);
		} catch (DCMAException e) {
			assertTrue(e.getMessage(), result);
		} catch (Exception e) {
			assertTrue(e.getMessage(), result);
		} finally {
			if (created) {
				BatchInstance batchInstance = batchInstanceService.getBatchInstanceByIdentifier(batchInstanceIdSuccess1);
				if (batchInstance != null) {
					batchInstanceService.removeBatchInstance(batchInstance);
				}
			} else {
				BatchInstance batchInstance = batchInstanceService.getBatchInstanceByIdentifier(batchInstanceIdSuccess1);
				batchInstance.setBatchClass(initialBatchClass);
				batchInstanceService.updateBatchInstance(batchInstance);
			}
		}
	}

	/**
	 * This is a test scenario for generating confidence score for Tesseract.
	 */
	@Test
	public void testSearchClassificationPluginForTesseract() {
		boolean result = false;
		boolean created = false;
		BatchClass initialBatchClass = new BatchClass();
		try {
			BatchInstance batchInstance = batchInstanceService.getBatchInstanceByIdentifier(batchInstanceIdSuccess2);
			if (null == batchInstance) {
				batchInstance = new BatchInstance();
				batchInstance.setIdentifier(batchInstanceIdSuccess2);
				BatchClass batchClass = batchClassService.getBatchClassByIdentifier(batchClassId2);
				batchInstance.setBatchClass(batchClass);
				batchInstanceService.createBatchInstance(batchInstance);
				created = true;
			} else {
				initialBatchClass = batchInstance.getBatchClass();
				BatchClass batchClass = batchClassService.getBatchClassByIdentifier(batchInstanceIdSuccess2);
				batchInstance.setBatchClass(batchClass);

			}
			searchClassService.learnSampleHOCRForTesseract(new BatchClassID(batchClassId2), true);
			searchClassService.generateConfidenceScore(new BatchInstanceID(batchInstanceIdSuccess2), null);
			// compare two XML's
			compareXMLs(actualOutputFolder, expectedOutputFolder, batchInstanceIdSuccess2);
		} catch (DCMAException e) {
			assertTrue(e.getMessage(), result);
		} catch (Exception e) {
			assertTrue(e.getMessage(), result);
		} finally {
			if (created) {
				BatchInstance batchInstance = batchInstanceService.getBatchInstanceByIdentifier(batchInstanceIdSuccess2);
				if (batchInstance != null) {
					batchInstanceService.removeBatchInstance(batchInstance);
				}
			} else {
				BatchInstance batchInstance = batchInstanceService.getBatchInstanceByIdentifier(batchInstanceIdSuccess2);
				batchInstance.setBatchClass(initialBatchClass);
				batchInstanceService.updateBatchInstance(batchInstance);
			}
		}
	}

	/**
	 * This methos does the clean-up operation : deleting the newly modified xml in inputOutput folder and replacing them with
	 * corresponding xml in samples folder.
	 */
	@After
	public void tearDown() {
		batchSchemaService.setBaseFolderLocation(baseFolderLocation);
		batchSchemaService.setLocalFolderLocation(localFolderLocation);
		FileUtils.deleteAllXMLs(actualOutputFolder + File.separator + batchInstanceIdSuccess1);
		FileUtils.copyAllXMLFiles(samplesFolder + File.separator + batchInstanceIdSuccess1, actualOutputFolder + File.separator
				+ batchInstanceIdSuccess1);
		FileUtils.deleteAllXMLs(actualOutputFolder + File.separator + batchInstanceIdSuccess2);
		FileUtils.copyAllXMLFiles(samplesFolder + File.separator + batchInstanceIdSuccess2, actualOutputFolder + File.separator
				+ batchInstanceIdSuccess2);
	}

}
