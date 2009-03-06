<%@ include file="/kr/WEB-INF/jsp/tldHeader.jsp"%>

<c:set var="groupAttributes" value="${DataDictionary.KimGroupImpl.attributes}" />
<c:set var="groupTypeAttributes" value="${DataDictionary.KimTypeImpl.attributes}" />
<c:set var="readOnly" value="${!KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT]}" />

<c:if test="${readOnly}">
	<c:set var="inquiry" value="${readOnly}"/>
</c:if>

	<kul:tab tabTitle="Overview" defaultOpen="true" transparentBackground="${inquiry}" tabErrorKey="document.group*,document.active">

	<div class="tab-container" align="center">
    	<h3>
    		<span class="subhead-left">Overview</span>
        </h3>

		<table cellpadding=0 cellspacing=0 summary=""> 
		 	<tr>
    			<th><div align="right"><kul:htmlAttributeLabel attributeEntry="${groupAttributes.groupId}"  /></div></th>
		 		<td><kul:htmlControlAttribute property="document.groupId" attributeEntry="${groupAttributes.groupId}" readOnly="true" /></td>
        		<th><div align="right"><kul:htmlAttributeLabel attributeEntry="${groupTypeAttributes.name}"  /></div></th>
		 		<td><kul:htmlControlAttribute property="document.groupTypeName" attributeEntry="${groupTypeAttributes.name}" readOnly="true" /></td>
		 		<html:hidden property="document.groupTypeId" />
		 	</tr>
		 	<tr>
        		<th><div align="right"><kul:htmlAttributeLabel attributeEntry="${groupAttributes.namespaceCode}"  /></div></th>
		 		<td><kul:htmlControlAttribute property="document.groupNamespace" attributeEntry="${groupAttributes.namespaceCode}" readOnly="${readOnly}" /></td>
        		<th><div align="right"><kul:htmlAttributeLabel attributeEntry="${groupAttributes.groupName}"  /></div></th>
		 		<td><kul:htmlControlAttribute property="document.groupName" attributeEntry="${groupAttributes.groupName}" readOnly="${readOnly}" /></td>
		 	</tr>
		 	<tr>
   				<th><div align="right"><kul:htmlAttributeLabel attributeEntry="${groupAttributes.active}"  /></div></th>
		 		<td colspan="3" ><kul:htmlControlAttribute property="document.active" attributeEntry="${groupAttributes.active}" readOnly="${readOnly}" /></td>
		 	</tr>
		</table> 
	
		</div>
	</kul:tab>

