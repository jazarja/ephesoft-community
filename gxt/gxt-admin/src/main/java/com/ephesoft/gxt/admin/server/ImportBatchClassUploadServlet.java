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

package com.ephesoft.gxt.admin.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SerializationUtils;

import com.ephesoft.dcma.batch.service.BatchSchemaService;
import com.ephesoft.dcma.batch.service.ImportBatchService;
import com.ephesoft.dcma.core.common.FileType;
import com.ephesoft.dcma.da.domain.BatchClass;
import com.ephesoft.dcma.da.service.BatchClassService;
import com.ephesoft.dcma.util.FileUtils;
import com.ephesoft.dcma.workflows.service.DeploymentService;
import com.ephesoft.gxt.admin.shared.constant.AdminSharedConstants;
import com.ephesoft.gxt.core.server.DCMAHttpServlet;

public class ImportBatchClassUploadServlet extends DCMAHttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String SERIALIZATION_EXT = FileType.SER.getExtensionWithDot();

	@Override
	public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		BatchSchemaService batchSchemaService = this.getSingleBeanOfType(BatchSchemaService.class);
		BatchClassService bcService = this.getSingleBeanOfType(BatchClassService.class);
		ImportBatchService imService = this.getSingleBeanOfType(ImportBatchService.class);

		String lastAttachedZipSourcePath = req.getParameter("lastAttachedZipSourcePath");
		if (lastAttachedZipSourcePath != null && !lastAttachedZipSourcePath.isEmpty()) {
			if (new File(lastAttachedZipSourcePath).exists()) {
				FileUtils.deleteDirectoryAndContentsRecursive(new File(lastAttachedZipSourcePath));
			}
		}
		attachFile(req, resp, batchSchemaService, bcService, imService);
	}

	private void attachFile(HttpServletRequest req, HttpServletResponse resp, BatchSchemaService batchSchemaService,
			BatchClassService bcService, ImportBatchService imService) throws IOException {

		PrintWriter printWriter = resp.getWriter();

		File tempZipFile = null;
		InputStream instream = null;
		OutputStream out = null;
		String zipWorkFlowName = "", tempOutputUnZipDir = "", zipWorkflowDesc = "", zipWorkflowPriority = "";
		BatchClass importBatchClass = null;

		if (ServletFileUpload.isMultipartContent(req)) {

			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			String exportSerailizationFolderPath = batchSchemaService.getBatchExportFolderLocation();

			File exportSerailizationFolder = new File(exportSerailizationFolderPath);
			if (!exportSerailizationFolder.exists()) {
				exportSerailizationFolder.mkdir();
			}

			String zipFileName = "";
			String zipPathname = "";
			List<FileItem> items;

			try {
				items = upload.parseRequest(req);
				for (FileItem item : items) {

					if (!item.isFormField()){//&& "importFile".equals(item.getFieldName())) {
						zipFileName = item.getName();
						if (zipFileName != null) {
							zipFileName = zipFileName.substring(zipFileName.lastIndexOf(File.separator) + 1);
						}
						zipPathname = exportSerailizationFolderPath + File.separator + zipFileName;
						// get only the file name not whole path
						if (zipFileName != null) {
							zipFileName = FilenameUtils.getName(zipFileName);
						}

						try {
							instream = item.getInputStream();
							tempZipFile = new File(zipPathname);
							if (tempZipFile.exists()) {
								tempZipFile.delete();
							}
							out = new FileOutputStream(tempZipFile);
							byte buf[] = new byte[1024];
							int len;
							while ((len = instream.read(buf)) > 0) {
								out.write(buf, 0, len);
							}
						} catch (FileNotFoundException e) {
							log.error("Unable to create the export folder." + e, e);
							printWriter.write("Unable to create the export folder.Please try again.");

						} catch (IOException e) {
							log.error("Unable to read the file." + e, e);
							printWriter.write("Unable to read the file.Please try again.");
						} finally {
							if (out != null) {
								try {
									out.close();
								} catch (IOException ioe) {
									log.info("Could not close stream for file." + tempZipFile);
								}
							}
							if (instream != null) {
								try {
									instream.close();
								} catch (IOException ioe) {
									log.info("Could not close stream for file." + zipFileName);
								}
							}
						}
					}
				}
			} catch (FileUploadException e) {
				log.error("Unable to read the form contents." + e, e);
				printWriter.write("Unable to read the form contents.Please try again.");
			}

			tempOutputUnZipDir = exportSerailizationFolderPath + File.separator
					+ zipFileName.substring(0, zipFileName.lastIndexOf('.')) + System.nanoTime();
			try {
				FileUtils.unzip(tempZipFile, tempOutputUnZipDir);
			} catch (Exception e) {
				log.error("Unable to unzip the file." + e, e);
				printWriter.write("Unable to unzip the file.Please try again.");
				tempZipFile.delete();
			}

			String serializableFilePath = FileUtils.getFileNameOfTypeFromFolder(tempOutputUnZipDir, SERIALIZATION_EXT);
			InputStream serializableFileStream = null;

			try {
				serializableFileStream = new FileInputStream(serializableFilePath);
				importBatchClass = (BatchClass) SerializationUtils.deserialize(serializableFileStream);
				zipWorkFlowName = importBatchClass.getName();
				zipWorkflowDesc = importBatchClass.getDescription();
				zipWorkflowPriority = "" + importBatchClass.getPriority();

			} catch (Exception e) {
				tempZipFile.delete();
				log.error("Error while importing" + e, e);
				printWriter.write("Error while importing.Please try again.");
			} finally {
				if (serializableFileStream != null) {
					try {
						serializableFileStream.close();
					} catch (IOException ioe) {
						log.info("Could not close stream for file." + serializableFilePath);
					}
				}
			}

		} else {
			log.error("Request contents type is not supported.");
			printWriter.write("Request contents type is not supported.");
		}
		if (tempZipFile != null) {
			tempZipFile.delete();
		}

		List<String> uncList = bcService.getAssociatedUNCList(zipWorkFlowName);
		DeploymentService deploymentService = this.getSingleBeanOfType(DeploymentService.class);
		boolean isWorkflowDeployed = deploymentService.isDeployed(zipWorkFlowName);

		if (null != importBatchClass) {
			boolean isWorkflowEqual = imService.isImportWorkflowEqualDeployedWorkflow(importBatchClass, importBatchClass.getName());
			printWriter.write(AdminSharedConstants.WORK_FLOW_NAME + zipWorkFlowName);
			printWriter.append("|");
			printWriter.write(AdminSharedConstants.WORK_FLOW_DESC + zipWorkflowDesc);
			printWriter.append("|");
			printWriter.write(AdminSharedConstants.WORK_FLOW_PRIORITY + zipWorkflowPriority);
			printWriter.append("|");
			printWriter.append(AdminSharedConstants.FILE_PATH).append(tempOutputUnZipDir);
			printWriter.append("|");
			printWriter.write(AdminSharedConstants.WORKFLOW_DEPLOYED + isWorkflowDeployed);
			printWriter.append("|");
			printWriter.write(AdminSharedConstants.WORKFLOW_EQUAL + isWorkflowEqual);
			printWriter.append("|");
			printWriter.write(AdminSharedConstants.WORKFLOW_EXIST_IN_BATCH_CLASS
					+ ((uncList == null || uncList.size() == 0) ? false : true));
			printWriter.append("|");
		}
		printWriter.flush();
	}
}
