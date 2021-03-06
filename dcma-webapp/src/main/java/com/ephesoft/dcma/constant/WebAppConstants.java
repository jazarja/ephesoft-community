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

package com.ephesoft.dcma.constant;

/**
 * This class has constants for web app.
 * 
 * @author Ephesoft
 * @version 1.0
 */
public final class WebAppConstants {

	/**
	 * Constant used to get the path of the root
	 */
	public static final String ROOT_PATH = "";
	/**
	 * NOCACHE String.
	 */
	public static final String NOCACHE = ".nocache.";

	/**
	 * X_UA_COMPATIBLE String.
	 */
	public static final String X_UA_COMPATIBLE = "X-UA-Compatible";

	/**
	 * IE_8 String.
	 */
	public static final String IE_8 = "IE=8";

	/**
	 * DATE String.
	 */
	public static final String DATE = "Date";

	/**
	 * EXPIRES String.
	 */
	public static final String EXPIRES = "Expires";

	/**
	 * PRAGMA String.
	 */
	public static final String PRAGMA = "Pragma";

	/**
	 * CACHE_CONTROL String.
	 */
	public static final String CACHE_CONTROL = "Cache-control";

	/**
	 * String for ETAG Constant
	 */
	public static final String LAST_MODIFIED = "Last-Modified";

	/**
	 * NO_CACHE_NO_STORE_MUST_REVALIDATE String.
	 */
	public static final String STORE_AND_MUST_REVALIDATE = "max-age=0, public, must-revalidate";

	/**
	 * NO_CACHE String.
	 */
	public static final String NO_CACHE = "no-cache";

	/**
	 * TIME_CONST long.
	 */
	public static final long TIME_CONST = 86400000000L;

	/**
	 * Constant for css extension for caching problem.
	 */
	public static final CharSequence CSS_EXTENSION = ".css";

	/**
	 * Constant for JS Extension
	 */
	public static final CharSequence JS_EXTENSION = ".js";

	/**
	 * Constant for PNG Extension
	 */
	public static final CharSequence PNG_EXTENSION = ".png";

	/**
	 * Constant for ICO Extension
	 */
	public static final CharSequence ICO_EXTENSION = ".ico";
	
	/**
	 * Constant for SVG Extension
	 */
	public static final CharSequence SVG_EXTENSION = ".svg";
}
