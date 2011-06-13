/*
 * Copyright 2011 The Kuali Foundation
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
package org.kuali.rice.krms.impl.ui;

import org.kuali.rice.core.util.Node;
import org.kuali.rice.krms.impl.repository.AgendaItemBo;

/**
 * data class for agenda tree {@link Node}s containing agenda items 
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 *
 */
public class AgendaTreeRuleNode extends AgendaTreeNode {
    
    private AgendaItemBo agendaItem;
    
    public AgendaTreeRuleNode(AgendaItemBo agendaItem) {
        this.agendaItem = agendaItem;
    }
    
    /**
     * @return the agendaItem
     */
    public AgendaItemBo getAgendaItem() {
        return this.agendaItem;
    }
    
    /**
     * @param agendaItem the agendaItem to set
     */
    public void setAgendaItem(AgendaItemBo agendaItem) {
        this.agendaItem = agendaItem;
    }

}
