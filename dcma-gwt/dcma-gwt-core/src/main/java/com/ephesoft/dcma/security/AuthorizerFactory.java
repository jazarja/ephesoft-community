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

package com.ephesoft.dcma.security;

import com.ephesoft.dcma.core.common.AuthenticationType;
import com.ephesoft.dcma.security.service.AuthorizationService;
import com.ephesoft.dcma.security.service.impl.BasicAuthorizer;
import com.ephesoft.dcma.security.service.impl.SSOAuthorizer;
import com.ephesoft.dcma.security.service.impl.SSODatabaseAuthorizer;

/**
 * Provides the specific instance of <code>AuthorizationService</code> depending upon the authentication type configured in web.xml.
 * 
 * @author Ephesoft
 * 
 *         <b>created on</b> 18-Nov-2013 <br/>
 * @version 1.0 $LastChangedDate:$ <br/>
 *          $LastChangedRevision:$ <br/>
 */
public class AuthorizerFactory {

	/**
	 * To hold the instance of authorisation service.
	 */
	private static AuthorizationService authorizationService = null;

	/**
	 * Private Constructor to stop instantiation of Factory class.
	 */
	private AuthorizerFactory() {
	}

	/**
	 * Gets the instance of <code>AuthorizationService</code> depending upon the authentication type parameter passed.
	 * 
	 * <p>
	 * If parameter value is not from the list of configurable values then Ephesoft Basic Authorisation gets returned by default.
	 * 
	 * @param authenticationType {@link AuthenticationType} type of authentication configured.
	 * @return {@link AuthorizationService} type of authorisation service.
	 */
	public synchronized static AuthorizationService getAuthorizer(final AuthenticationType authenticationType) {
		if (authorizationService == null) {
			synchronized (AuthorizerFactory.class) {
				if (null == authorizationService) {
					if (authenticationType == AuthenticationType.SSO_AUTHENTICATION_ONLY) {
						authorizationService = new SSOAuthorizer();
					} else if (authenticationType == AuthenticationType.SSO_AUTHENTICATION_AUTHORIZATION) {
						authorizationService = new SSODatabaseAuthorizer();
					} else {
						authorizationService = new BasicAuthorizer();
					}
				}
			}
		}
		return authorizationService;
	}
}
