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

package com.ephesoft.dcma.barcodeextraction.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.ephesoft.dcma.barcodeextraction.BarcodeExtractionReader;
import com.ephesoft.dcma.batch.schema.Batch;
import com.ephesoft.dcma.batch.schema.DocField;
import com.ephesoft.dcma.core.DCMAException;
import com.ephesoft.dcma.core.annotation.PostProcess;
import com.ephesoft.dcma.core.annotation.PreProcess;
import com.ephesoft.dcma.core.exception.DCMAApplicationException;
import com.ephesoft.dcma.da.id.BatchInstanceID;
import com.ephesoft.dcma.util.BackUpFileService;

public class BarcodeExtractionServiceImpl implements BarcodeExtractionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BarcodeExtractionServiceImpl.class);

	@Autowired
	private transient BarcodeExtractionReader barcodeReader;

	@PreProcess
	public void preProcess(final BatchInstanceID batchInstanceID, String pluginWorkflow) {
		Assert.notNull(batchInstanceID);
		BackUpFileService.backUpBatch(batchInstanceID.getID());
	}

	@PostProcess
	public void postProcess(final BatchInstanceID batchInstanceID, String pluginWorkflow) {
		Assert.notNull(batchInstanceID);
		BackUpFileService.copyBatchXML(batchInstanceID.getID(), pluginWorkflow);
	}

	@Override
	public void extractPageBarCode(final BatchInstanceID batchInstanceID, final String pluginWorkflow) throws DCMAException {
		try {
			barcodeReader.readBarcode(batchInstanceID.getID(), pluginWorkflow);
		} catch (Exception e) {
			LOGGER.error("Uncaught Exception in readBarcode method " + e.getMessage(), e);
			throw new DCMAException(e.getMessage(), e);
		}
	}

	@Override
	public List<DocField> extractPageBarCodeAPI(String batchClassIdentifier, String folderLocation, String imageName, String docType)
			throws DCMAException {
		List<DocField> docLevelFieldList;
		try {
			docLevelFieldList =	barcodeReader.readBarcodeForWebService(batchClassIdentifier, folderLocation,imageName,docType);
		} catch (Exception e) {
			LOGGER.error("Uncaught Exception in readBarcodeForWebService method " + e.getMessage(), e);
			throw new DCMAException(e.getMessage(), e);
		}
		return docLevelFieldList;
	}
	
	
	@Override
	public void extractPageBarCodeForExtraction(String batchClassIdentifier, String folderLocation, Batch batch) throws DCMAApplicationException {
		try {
			barcodeReader.readBarcodeForExtraction(batchClassIdentifier, folderLocation, batch);
		} catch (Exception e) {
			LOGGER.error("Uncaught Exception in readBarcodeForWebService method " + e.getMessage(), e);
			throw new DCMAApplicationException(e.getMessage(), e);
		}
	}
}
