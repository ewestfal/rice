/**
 * Copyright 2005-2015 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.edl.impl.components;

import org.kuali.rice.edl.impl.EDLContext;
import org.kuali.rice.edl.impl.EDLModelComponent;
import org.kuali.rice.edl.impl.EDLXmlUtils;
import org.kuali.rice.edl.impl.RequestParser;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


/**
 * This class makes xml for the instructions template of widgets.  Processes config elements
 * instructions and createInstructions.
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 *
 */
public class InstructionsEDLComponent implements EDLModelComponent {

	public void updateDOM(Document dom, Element configElement, EDLContext edlContext) {
		
		Element edlElement = EDLXmlUtils.getEDLContent(dom, false);
		Element edlSubElement = EDLXmlUtils.getOrCreateChildElement(edlElement, "edl", true);
		WorkflowDocument document = (WorkflowDocument)edlContext.getRequestParser().getAttribute(RequestParser.WORKFLOW_DOCUMENT_SESSION_KEY);
		edlSubElement.setAttribute("title", document.getTitle());
		
		if(configElement.getTagName().equals("instructions")) {
			Node instTextNode = configElement.getChildNodes().item(0);
			if (instTextNode == null) {
				return ;
			}
			String instructions = instTextNode.getNodeValue(); 
			EDLXmlUtils.createTextElementOnParent(edlSubElement, "instructions", instructions);
			edlElement.setAttribute("title", instructions);	
		} else if (configElement.getTagName().equals("createInstructions")) {
			Node instTextNode = configElement.getChildNodes().item(0);
			if (instTextNode == null) {
				return ;
			}
			String instructions = instTextNode.getNodeValue();
			EDLXmlUtils.createTextElementOnParent(edlSubElement, "createInstructions", instructions);	
		}
	}

}
