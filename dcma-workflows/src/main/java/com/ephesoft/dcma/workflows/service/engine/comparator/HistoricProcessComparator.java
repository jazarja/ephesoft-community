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

package com.ephesoft.dcma.workflows.service.engine.comparator;

import java.util.Comparator;
import java.util.Date;

import org.activiti.engine.history.HistoricProcessInstance;

import com.ephesoft.dcma.workflows.constant.WorkflowConstants;

/**
 * Sorts list of history process instances in descending order.
 * 
 * @author ephesoft
 * @version 1.0
 * 
 */
public class HistoricProcessComparator implements Comparator<HistoricProcessInstance> {

	@Override
	public int compare(final HistoricProcessInstance history1, final HistoricProcessInstance history2) {

		// This comparator sorts history instances.
		// Changed to support Strong UUID for Activiti engine database tables.
		int comparison;
		if (null == history1 || null == history2) {
			comparison = WorkflowConstants.LESS_COMPARISON;
		} else {
			Date history1StartTime = history1.getStartTime();
			Date history2StartTime = history2.getStartTime();
			if (null != history1StartTime && null != history2StartTime) {
				long history1Time = history1StartTime.getTime();
				long history2Time = history2StartTime.getTime();
				if (history1Time > history2Time) {
					comparison = WorkflowConstants.GREATER_COMPARISON;
				} else {
					comparison = WorkflowConstants.LESS_COMPARISON;
				}
			} else {
				comparison = WorkflowConstants.LESS_COMPARISON;
			}
		}
		return comparison;
	}
}
