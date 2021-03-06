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

package com.ephesoft.dcma.lucene.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ephesoft.dcma.batch.schema.Document;
import com.ephesoft.dcma.batch.schema.HocrPages;
import com.ephesoft.dcma.core.DCMAException;
import com.ephesoft.dcma.da.id.BatchClassID;
import com.ephesoft.dcma.da.id.BatchInstanceID;
import com.ephesoft.dcma.lucene.LuceneProperties;

/**
 * This service is used to generate the confidence score using Lucene search engine.It first creates the indexes for a well defined set
 * of input HOCR files and then generates confidence score for each HOCR file mentioned in batch xml.
 * 
 * @author Ephesoft
 * @version 1.0
 * @see com.ephesoft.dcma.lucene.service.SearchClassificationServiceImpl
 * 
 */
public interface SearchClassificationService {

	/**
	 * This method generates the confidence score using Lucene engine. It reads each HOCR file from batch xml, generate confidence
	 * score from the sample index stored, fetches maximum of 5 close matches and stores first at page level field and others as
	 * alternate values.
	 * 
	 * @param batchInstanceID {@link BatchInstanceID}
	 * @param pluginWorkflow {@link String}
	 * @throws DCMAException
	 */
	void generateConfidenceScore(final BatchInstanceID batchInstanceID, final String pluginWorkflow) throws DCMAException;

	/**
	 * This method is used to generate the indexes for a well defined set of HOCR files which are stored in hierarchy : batch class >
	 * document type > page type.
	 * 
	 * @param batchClassId {@link BatchClassID}
	 * @param createIndex boolean
	 * @throws DCMAException
	 */
	void learnSampleHOCR(final BatchClassID batchClassID, boolean createIndex) throws DCMAException;

	/**
	 * This method is used to generate the indexes for a well defined set of HOCR files which are stored in hierarchy : batch class >
	 * document type > page type.
	 * 
	 * @param batchClassId {@link BatchClassID}
	 * @param createIndex boolean
	 * @throws DCMAException
	 */
	void learnSampleHOCRForTesseract(final BatchClassID batchClassID, boolean createIndex) throws DCMAException;

	/**
	 * This method generates the hocr files for all the images present inside supplied folder for a paticular batch class.
	 * 
	 * @param imageFolder
	 * @param ocrEngineName
	 * @param batchClassIdentifer
	 * @param testImageFile
	 * @param isAdvancedKVExtraction
	 * @return
	 * @throws DCMAException
	 */
	Map<String, String> generateHOCRForKVExtractionTest(final String imageFolder, final String ocrEngineName,
			final String batchClassIdentifer, final File testImageFile, final boolean isAdvancedKVExtraction) throws DCMAException;

	/**
	 * Generates the confidence scores for the input pages.
	 * 
	 * @param xmlDocuments {@link List<Document>} the document object for pages.
	 * @param hocrPages {@link HocrPages} the hocr pages for input pages.
	 * @param workingDir {@link String} the working directory for processing.
	 * @param propertyMap {@link Map<LuceneProperties, String>} the property map for the plugin.
	 * @param batchClassIdentifier {@link String} the batch class identifier.
	 * @throws DCMAException the exception for issues in processing.
	 */
	void generateConfidenceScoreAPI(List<Document> xmlDocuments, HocrPages hocrPages, String workingDir,
			Map<LuceneProperties, String> propertyMap, String batchClassIdentifier) throws DCMAException;

	/**
	 * Performs learning for a batch class using the plugin selected for ocr'ing in the batch class configuration
	 * 
	 * @param batchClassID the batch class id
	 * @param createIndex specifies wheteher to create lucene indexes or not
	 * @throws DCMAException if an error occurs while creating the hocr file
	 * @throws IOException if an error occurs while creating the hocr file
	 */
	void learnSampleHOCRFilesUsingSelectedPlugin(final String batchClassID, final boolean createIndex) throws DCMAException,
			IOException;

}
