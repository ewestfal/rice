/*
 * Copyright 2005-2007 The Kuali Foundation.
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
package org.kuali.rice.kns.web.struts.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.rice.core.util.RiceConstants;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kim.util.KimCommonUtils;
import org.kuali.rice.kim.util.KimConstants;
import org.kuali.rice.kns.exception.AuthorizationException;
import org.kuali.rice.kns.lookup.CollectionIncomplete;
import org.kuali.rice.kns.lookup.Lookupable;
import org.kuali.rice.kns.service.DocumentHelperService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.service.MaintenanceDocumentDictionaryService;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.web.struts.form.LookupForm;
import org.kuali.rice.kns.web.ui.Field;
import org.kuali.rice.kns.web.ui.ResultRow;
import org.kuali.rice.kns.web.ui.Row;

/**
 * This class handles Actions for lookup flow
 * 
 * 
 */

public class KualiLookupAction extends KualiAction {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(KualiLookupAction.class);

    @Override
    protected void checkAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        if (!(form instanceof LookupForm)) {
            super.checkAuthorization(form, methodToCall);
        } else {
            try {
                Class businessObjectClass = Class.forName(((LookupForm) form).getBusinessObjectClassName());
                if (!KIMServiceLocator.getIdentityManagementService().isAuthorizedByTemplateName(GlobalVariables.getUserSession().getPrincipalId(), KNSConstants.KNS_NAMESPACE, KimConstants.PermissionTemplateNames.LOOK_UP_RECORDS, KimCommonUtils.getNamespaceAndComponentSimpleName(businessObjectClass), null)) {
                    throw new AuthorizationException(GlobalVariables.getUserSession().getPerson().getPrincipalName(), 
                    		KimConstants.PermissionTemplateNames.LOOK_UP_RECORDS,
                    		businessObjectClass.getSimpleName());
                }
            }
            catch (ClassNotFoundException e) {
            	LOG.warn("Unable to load BusinessObject class: " + ((LookupForm) form).getBusinessObjectClassName(), e);
                super.checkAuthorization(form, methodToCall);
            }
        }
    }

    private static MaintenanceDocumentDictionaryService maintenanceDocumentDictionaryService;
    private static DocumentHelperService documentHelperService;
    private static MaintenanceDocumentDictionaryService getMaintenanceDocumentDictionaryService() {
    	if (maintenanceDocumentDictionaryService == null) {
    		maintenanceDocumentDictionaryService = KNSServiceLocator.getMaintenanceDocumentDictionaryService();
    	}
    	return maintenanceDocumentDictionaryService;
    }
    private static DocumentHelperService getDocumentHelperService() {
    	if (documentHelperService == null) {
    	    documentHelperService = KNSServiceLocator.getDocumentHelperService();
    	}
    	return documentHelperService;
    }
    /**
     * Checks if the user can create a document for this business object.  Used to suppress the actions on the results.
     * 
     * @param form
     * @return
     * @throws ClassNotFoundException
     */
    protected void supressActionsIfNeeded( ActionForm form ) throws ClassNotFoundException {
        if ((form instanceof LookupForm) && ( ((LookupForm)form).getBusinessObjectClassName() != null )) {
            Class businessObjectClass = Class.forName( ((LookupForm)form).getBusinessObjectClassName() );
            // check if creating documents is allowed
            String documentTypeName = getMaintenanceDocumentDictionaryService().getDocumentTypeName(businessObjectClass);
            if ((documentTypeName != null) && !getDocumentHelperService().getDocumentAuthorizer(documentTypeName).canInitiate(documentTypeName, GlobalVariables.getUserSession().getPerson())) {
                ((LookupForm)form).setSuppressActions( true );
            }
        }
    }
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(KNSConstants.PARAM_MAINTENANCE_VIEW_MODE, KNSConstants.PARAM_MAINTENANCE_VIEW_MODE_LOOKUP);
        supressActionsIfNeeded(form);
        return super.execute(mapping, form, request, response);
    }

    /**
     * Entry point to lookups, forwards to jsp for search render.
     */
    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }

    /**
     * search - sets the values of the data entered on the form on the jsp into a map and then searches for the results.
     */
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LookupForm lookupForm = (LookupForm) form;
        Lookupable kualiLookupable = lookupForm.getLookupable();
        if (kualiLookupable == null) {
            LOG.error("Lookupable is null.");
            throw new RuntimeException("Lookupable is null.");
        }

        Collection displayList = new ArrayList();
        List<ResultRow> resultTable = new ArrayList<ResultRow>();

        // validate search parameters
        kualiLookupable.validateSearchParameters(lookupForm.getFields());

        boolean bounded = true;

        displayList = kualiLookupable.performLookup(lookupForm, resultTable, bounded);

        if (kualiLookupable.isSearchUsingOnlyPrimaryKeyValues()) {
            lookupForm.setSearchUsingOnlyPrimaryKeyValues(true);
            lookupForm.setPrimaryKeyFieldLabels(kualiLookupable.getPrimaryKeyFieldLabels());
        }
        else {
            lookupForm.setSearchUsingOnlyPrimaryKeyValues(false);
            lookupForm.setPrimaryKeyFieldLabels(KNSConstants.EMPTY_STRING);
        }
        
        if ( displayList instanceof CollectionIncomplete ) {
            request.setAttribute("reqSearchResultsActualSize", ((CollectionIncomplete) displayList).getActualSizeIfTruncated());
        } else {
            request.setAttribute("reqSearchResultsActualSize", displayList.size() );
        }
        request.setAttribute("reqSearchResults", resultTable);
        
        if (request.getParameter(KNSConstants.SEARCH_LIST_REQUEST_KEY) != null) {
            GlobalVariables.getUserSession().removeObject(request.getParameter(KNSConstants.SEARCH_LIST_REQUEST_KEY));
        }
        request.setAttribute(KNSConstants.SEARCH_LIST_REQUEST_KEY, GlobalVariables.getUserSession().addObject(resultTable, KNSConstants.SEARCH_LIST_KEY_PREFIX));

        String refreshCaller = request.getParameter(KNSConstants.REFRESH_CALLER);

        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }


    /**
     * refresh - is called when one quickFinder returns to the previous one. Sets all the values and performs the new search.
     */
    public ActionForward refresh(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LookupForm lookupForm = (LookupForm) form;
        Lookupable kualiLookupable = lookupForm.getLookupable();
        if (kualiLookupable == null) {
            LOG.error("Lookupable is null.");
            throw new RuntimeException("Lookupable is null.");
        }

        Map fieldValues = new HashMap();
        Map values = lookupForm.getFields();

        for (Iterator iter = kualiLookupable.getRows().iterator(); iter.hasNext();) {
            Row row = (Row) iter.next();

            for (Iterator iterator = row.getFields().iterator(); iterator.hasNext();) {
                Field field = (Field) iterator.next();

                if (field.getPropertyName() != null && !field.getPropertyName().equals("")) {
                    if (request.getParameter(field.getPropertyName()) != null) {
                        field.setPropertyValue(request.getParameter(field.getPropertyName()));
                    }
                    else if (values.get(field.getPropertyName()) != null) {
                        field.setPropertyValue(values.get(field.getPropertyName()));
                    }
                }
                fieldValues.put(field.getPropertyName(), field.getPropertyValue());
            }
        }
        fieldValues.put("docFormKey", lookupForm.getFormKey());
        fieldValues.put("backLocation", lookupForm.getBackLocation());
        fieldValues.put("docNum", lookupForm.getDocNum());

        if (kualiLookupable.checkForAdditionalFields(fieldValues)) {
            for (Iterator iter = kualiLookupable.getRows().iterator(); iter.hasNext();) {
                Row row = (Row) iter.next();
                for (Iterator iterator = row.getFields().iterator(); iterator.hasNext();) {
                    Field field = (Field) iterator.next();
                    if (field.getPropertyName() != null && !field.getPropertyName().equals("")) {
                        if (request.getParameter(field.getPropertyName()) != null) {
                            field.setPropertyValue(request.getParameter(field.getPropertyName()));
                            fieldValues.put(field.getPropertyName(), request.getParameter(field.getPropertyName()));
                        }
                        else if (values.get(field.getPropertyName()) != null) {
                            field.setPropertyValue(values.get(field.getPropertyName()));
                        }
                    }
                }
            }
        }

        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }

    /**
     * Just returns as if return with no value was selected.
     */
    public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LookupForm lookupForm = (LookupForm) form;

        String backUrl = lookupForm.getBackLocation() + "?methodToCall=refresh&docFormKey=" + lookupForm.getFormKey()+"&docNum="+lookupForm.getDocNum();
        return new ActionForward(backUrl, true);
    }


    /**
     * clearValues - clears the values of all the fields on the jsp.
     */
    public ActionForward clearValues(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LookupForm lookupForm = (LookupForm) form;
        Lookupable kualiLookupable = lookupForm.getLookupable();
        if (kualiLookupable == null) {
            LOG.error("Lookupable is null.");
            throw new RuntimeException("Lookupable is null.");
        }

        for (Iterator iter = kualiLookupable.getRows().iterator(); iter.hasNext();) {
            Row row = (Row) iter.next();
            for (Iterator iterator = row.getFields().iterator(); iterator.hasNext();) {
                Field field = (Field) iterator.next();
                if (!field.getFieldType().equals(Field.RADIO)) {
                    field.setPropertyValue(field.getDefaultValue());
                }
            }
        }


        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }


    public ActionForward viewResults(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LookupForm lookupForm = (LookupForm) form;
        if (lookupForm.isSearchUsingOnlyPrimaryKeyValues()) {
            lookupForm.setPrimaryKeyFieldLabels(lookupForm.getLookupable().getPrimaryKeyFieldLabels());
        }
        request.setAttribute(KNSConstants.SEARCH_LIST_REQUEST_KEY, request.getParameter(KNSConstants.SEARCH_LIST_REQUEST_KEY));
        request.setAttribute("reqSearchResults", GlobalVariables.getUserSession().retrieveObject(request.getParameter(KNSConstants.SEARCH_LIST_REQUEST_KEY)));
        request.setAttribute("reqSearchResultsActualSize", request.getParameter("reqSearchResultsActualSize"));
        return mapping.findForward(RiceConstants.MAPPING_BASIC);
    }
    
}
