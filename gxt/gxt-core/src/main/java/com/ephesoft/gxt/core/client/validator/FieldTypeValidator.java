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

package com.ephesoft.gxt.core.client.validator;

import java.util.List;

import com.ephesoft.dcma.core.common.WidgetType;
import com.ephesoft.gxt.core.shared.dto.FieldTypeDTO;
import com.sencha.gxt.core.client.ValueProvider;

/**
 * This validator is used to validate value combination of two fields.
 * 
 * @author Ephesoft
 * @version 1.0
 * @param <T> the generic type
 * @param <V> the value type
 */
public class FieldTypeValidator<T extends FieldTypeDTO> implements Validator<FieldTypeDTO> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ephesoft.gxt.core.client.validator.Validator#validate(java.util.List, java.lang.Object,
	 * com.sencha.gxt.core.client.ValueProvider)
	 */
	@Override
	public boolean validate(List<FieldTypeDTO> listOfModels, FieldTypeDTO model, ValueProvider<FieldTypeDTO, ?> valueProvider) {
		boolean valid = true;
		if (null != model && null != valueProvider && model.getWidgetType() != null && model.getFieldOptionValueList() != null
				&& model.getFieldOptionValueList().trim().length() > 0
				&& !(model.getWidgetType().equals(WidgetType.LIST) || model.getWidgetType().equals(WidgetType.COMBO))) {
			valid = false;
		}
		return valid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ephesoft.gxt.core.client.validator.Validator#getErrorMessage()
	 */
	@Override
	public String getErrorMessage() {
		return "Please select Field Type as List/Combo.";
		// return LocaleDictionary.getLocaleDictionary().getConstantValue("constant_message");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ephesoft.gxt.core.client.validator.Validator#getSeverity()
	 */
	@Override
	public Severity getSeverity() {
		return Severity.MEDIUM;
	}

}
