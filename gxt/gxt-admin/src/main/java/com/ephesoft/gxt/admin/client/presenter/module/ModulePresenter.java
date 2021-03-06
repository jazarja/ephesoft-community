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

package com.ephesoft.gxt.admin.client.presenter.module;

import java.util.Set;

import com.ephesoft.gxt.admin.client.controller.BatchClassManagementController;
import com.ephesoft.gxt.admin.client.presenter.BatchClassCompositePresenter;
import com.ephesoft.gxt.admin.client.view.module.ModuleView;
import com.ephesoft.gxt.core.shared.dto.BatchClassDTO;
import com.google.gwt.event.shared.EventBus;

public class ModulePresenter extends
		BatchClassCompositePresenter<ModuleView, BatchClassDTO> {

	private ModuleConfigurePresenter moduleconfigurePresenter;
	private ModuleConfigureMenuPresenter moduleConfigureMenuPresenter;
	private Set<String> availableModules;

	public ModulePresenter(BatchClassManagementController controller,
			ModuleView view) {
		super(controller, view);
		moduleconfigurePresenter = new ModuleConfigurePresenter(controller,
				view.getModuleDetailView());
		moduleConfigureMenuPresenter = new ModuleConfigureMenuPresenter(
				controller, view.getModuleDetailMenuView());
	}

	@Override
	public void bind() {
		controller.getBatchClassLayout().getBottomPanel().collapse();
	}

	@Override
	public void init(BatchClassDTO selectionParameter) {
		if (null != selectionParameter) {
			controller.setSelectedBatchClass(selectionParameter);
		}
	}

	@Override
	public BatchClassCompositePresenter<?, ?> getParentPresenter() {
		return null;
	}

	@Override
	public BatchClassCompositePresenter<?, ?> getChildPresenter() {
		return null;
	}

	@Override
	public void injectEvents(EventBus eventBus) {

	}

	public ModuleConfigurePresenter getModuleConfigurePresenter() {
		return moduleconfigurePresenter;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	public Set<String> getAvailableModules() {
		return availableModules;
	}

	public void setAvailableModules(Set<String> availableModules) {
		this.availableModules = availableModules;
	}


}
