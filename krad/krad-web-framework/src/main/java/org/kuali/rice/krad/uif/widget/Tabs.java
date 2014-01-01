/**
 * Copyright 2005-2014 The Kuali Foundation
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
package org.kuali.rice.krad.uif.widget;

import org.kuali.rice.krad.datadictionary.parse.BeanTag;
import org.kuali.rice.krad.uif.UifConstants;

/**
 * Widget used for configuring tab options, use componentOptions for most options.
 * See http://jqueryui.com/demos/tabs/ for usable options
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
@BeanTag(name = "tabs-bean", parent = "Uif-Tabs")
public class Tabs extends WidgetBase{
	private static final long serialVersionUID = 2L;

    private UifConstants.Position position = UifConstants.Position.TOP;

    public Tabs() {
        super();
    }

    public UifConstants.Position getPosition() {
        return position;
    }

    public void setPosition(UifConstants.Position position) {
        this.position = position;
    }
}
