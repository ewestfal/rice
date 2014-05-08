/*
 * Copyright 2006-2012 The Kuali Foundation
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
package edu.samplu.krad.travelview;

import edu.samplu.common.KradMenuITBase;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class MaintenanceButtonsIT extends KradMenuITBase {
    @Override
    protected String getLinkLocator() {
        return "link=Travel Account Maintenance (New)";
    }
    @Test
    /**
     * Verify Submit, Save, Blanket approve, Close and Cancel buttons are available
     */
    public void testVerifyButtons() throws Exception {
        gotoMenuLinkLocator();
        assertTrue(isElementPresent("//button[contains(.,'ubmit')]"));
        assertTrue(isElementPresent("//button[contains(.,'ave')]"));
        assertTrue(isElementPresent("//button[contains(.,'lanket approve')]"));
        assertTrue(isElementPresent("//button[contains(.,'lose')]"));
        assertTrue(isElementPresent("//a[contains(.,'ancel')]"));
    }
}