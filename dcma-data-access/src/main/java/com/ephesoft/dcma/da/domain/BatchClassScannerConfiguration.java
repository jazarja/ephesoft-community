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

package com.ephesoft.dcma.da.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.ephesoft.dcma.core.model.common.AbstractChangeableEntity;

@Entity
@Table(name = "batch_class_scanner_configuration")
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
public class BatchClassScannerConfiguration extends AbstractChangeableEntity implements Serializable {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -4813558377886039259L;
	
	@OneToOne
	@JoinColumn(name = "batch_class_id")
	private BatchClass batchClass;

	@OneToOne
	@JoinColumn(name = "parent_id")
	private BatchClassScannerConfiguration parent;
	
	@ManyToOne
	@JoinColumn(name = "master_config_id")
	private ScannerMasterConfiguration scannerMasterConfig;
	
	@OneToMany
	@Cascade( {CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN, CascadeType.MERGE, CascadeType.EVICT})
	@JoinColumn(name = "parent_id")
	private List<BatchClassScannerConfiguration> children;

	@Column(name = "config_value")
	private String value;

	public void addChild(BatchClassScannerConfiguration config) {
		if (this.getChildren() == null) {
			setChildren(new ArrayList<BatchClassScannerConfiguration>());
		}
		if (config.getId() == 0) {
			this.getChildren().add(config);
		} else {
			removeChild(config);
			this.getChildren().add(config);
		}
	}

	public void removeChild(BatchClassScannerConfiguration config) {
		int index = -1;
		int cnt = 0;
		for (BatchClassScannerConfiguration sConfig : getChildren()) {
			if (sConfig.getId() == config.getId()) {
				index = cnt;
				break;
			}
			cnt++;
		}
		this.getChildren().remove(index);
	}
	
	public BatchClass getBatchClass() {
		return batchClass;
	}

	
	public void setBatchClass(BatchClass batchClass) {
		this.batchClass = batchClass;
	}

	
	public BatchClassScannerConfiguration getParent() {
		return parent;
	}

	
	public void setParent(BatchClassScannerConfiguration parent) {
		this.parent = parent;
	}

	
	public List<BatchClassScannerConfiguration> getChildren() {
		return children;
	}

	
	public void setChildren(List<BatchClassScannerConfiguration> children) {
		this.children = children;
	}

	
	public String getValue() {
		return value;
	}

	
	public void setValue(String value) {
		this.value = value;
	}
	
	public ScannerMasterConfiguration getScannerMasterConfig() {
		return scannerMasterConfig;
	}

	
	public void setScannerMasterConfig(ScannerMasterConfiguration scannerMasterConfig) {
		this.scannerMasterConfig = scannerMasterConfig;
	}
	
}
