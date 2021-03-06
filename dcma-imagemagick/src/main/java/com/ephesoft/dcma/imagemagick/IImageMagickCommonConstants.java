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

package com.ephesoft.dcma.imagemagick;

/**
 * Interface for ImageMagic Common Constants.
 * @author Ephesoft
 * 
 *         <b>created on</b> 13-Apr-2015 <br/>
 * @version 1.0.0 <br/>
 *          $LastChangedDate:$ <br/>
 *          $LastChangedRevision:$ <br/>
 */
public interface IImageMagickCommonConstants {

	int THUMBNAIL_LENGTH = 200;
	int THUMBNAIL_BREDTH = 150;
	String THUMBNAIL = "thumbnail";
	String THUMBS = "thumbs";
	String PNG = "png";
	String TIF = "tif";
	String EXT_PNG = ".png";
	String EXT_TIF = ".tif";
	String SUFFIX_THUMBNAIL_SAMPLE_PNG = "_thumb.png";
	String SUFFIX_THUMBNAIL_SAMPLE_TIF = "_thumb.tif";

	/**
	 * Constant for adding thumb as suffix to thumb-nail images.
	 */
	String SUFFIX_THUMBNAIL_SAMPLE = "_thumb";
	String IMAGEMAGICK_ENV_VARIABLE = "IM4JAVA_TOOLPATH";
	String IMAGE_COMPARE_CLASSIFICATION = "Image_Compare_Classification";
	String DEFAULT_IM_COMP_METRIC = "RMSE";
	String DEFAULT_IM_COMP_FUZZ = "10";
	String THUMB_TYPE_DISP = "displayThumbnail";
	String THUMB_TYPE_COMP = "compareThumbnail";
	String OCR_INPUT_FILE = "ocrInputFile";
	String DISPLAY_IMAGE = "displayImage";
	String REPAIR_FILES_THROUGH_GHOSTSCIPT_ENV_VARIABLE = "REPAIR_FILES_UTILITY_PATH";
	String GHOSTSCRIPT_ENV_VARIABLE = "GHOSTSCRIPT_HOME";
	String REPAIR_IMAGE_MAGICK_FILES_ENV_VARIABLE = "REPAIR_IMAGE_MAGICK_UTILITY_PATH";
	String EMPTY_STRING = "";
	String QUOTES = "\"";
	String SPACE = " ";
	String GHOSTSCRIPT_EXECUTOR = "EphesoftExecutor.exe";
	String IMAGEMAGICK_EXECUTOR = "EphesoftImageMagickExecutor.exe";
	String IM4JAVA_TOOLPATH = "IM4JAVA_TOOLPATH";

	/**
	 * Represnts the contant used for defining the path of output file in case of ghostscript.
	 */
	String OUTPUT_FILE_PARAMETER_GHOSTSCRIPT = "-sOutputFile=";

	/**
	 * Represnts the contant used for running the ghostscript command on unix operating system.
	 */
	String GHOST_SCRIPT_LINUX = "gs";
}
