/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.kim.bo.ui;

import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.kuali.rice.kim.bo.role.impl.KimResponsibilityImpl;
import org.kuali.rice.kns.util.TypedArrayList;

/**
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
@Entity
@Table(name="KRIM_PND_ROLE_RSP_T")
public class KimDocumentRoleResponsibility extends KimDocumentBoBase {
	
	@Id
	@Column(name="ROLE_RSP_ID")
	protected String roleResponsibilityId;
	@Column(name="ROLE_ID")
	protected String roleId;
	@Column(name="RSP_ID")
	protected String responsibilityId;
	
	protected KimResponsibilityImpl kimResponsibility;
	
	protected List<KimDocumentRoleResponsibilityAction> roleRspActions = new TypedArrayList(KimDocumentRoleResponsibilityAction.class);
	
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap m = new LinkedHashMap();
		m.put( "roleResponsibilityId", roleResponsibilityId );
		m.put( "roleId", roleId );
		m.put( "responsibilityId", responsibilityId );
		return m;
	}

	public void setRoleResponsibilityId(String roleResponsibilityId) {
		this.roleResponsibilityId = roleResponsibilityId;
	}

	/**
	 * @return the roleResponsibilityId
	 */
	public String getRoleResponsibilityId() {
		return this.roleResponsibilityId;
	}

	/**
	 * @return the kimResponsibility
	 */
	public KimResponsibilityImpl getKimResponsibility() {
		return this.kimResponsibility;
	}

	/**
	 * @param responsibilityId the responsibilityId to set
	 */
	public void setResponsibilityId(String responsibilityId) {
		this.responsibilityId = responsibilityId;
	}

	/**
	 * @param kimResponsibility the kimResponsibility to set
	 */
	public void setKimResponsibility(KimResponsibilityImpl kimResponsibility) {
		this.kimResponsibility = kimResponsibility;
	}

	/**
	 * @return the responsibilityId
	 */
	public String getResponsibilityId() {
		return this.responsibilityId;
	}

	/**
	 * @return the roleRspActions
	 */
	public KimDocumentRoleResponsibilityAction getRoleRspAction() {
		if(this.roleRspActions!=null && this.roleRspActions.size()>0)
			return this.roleRspActions.get(0);
		return null;
	}

	/**
	 * @return the roleRspActions
	 */
	public List<KimDocumentRoleResponsibilityAction> getRoleRspActions() {
		return this.roleRspActions;
	}

	/**
	 * @param roleRspActions the roleRspActions to set
	 */
	public void setRoleRspActions(
			List<KimDocumentRoleResponsibilityAction> roleRspActions) {
		this.roleRspActions = roleRspActions;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

}