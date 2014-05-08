/**
 * Copyright 2005-2011 The Kuali Foundation
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
package edu.samplu.admin.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import edu.samplu.common.ITUtil;
import edu.samplu.common.UpgradedSeleniumITBase;
import org.junit.Test;

/**
 * tests creating and cancelling new and edit Role maintenance screens
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class IdentityRoleIT extends UpgradedSeleniumITBase{
    @Override
    public String getTestUrl() {
        return ITUtil.PORTAL;
    }

    @Test
    /**
     * tests that a new Role maintenance document can be cancelled
     */
    public void testCreateNew() throws Exception {
        assertEquals("Kuali Portal Index", getTitle());
        waitAndClick("link=Administration");
        waitForPageToLoad();
        assertEquals("Kuali Portal Index", getTitle());
        waitAndClick("link=Role");
        waitForPageToLoad();
        assertEquals("Kuali Portal Index", getTitle());
        selectFrame("iframeportlet");
        waitAndClick("//img[@alt='create new']");
        waitAndClick("//input[@name='methodToCall.search' and @value='search']");
        waitAndClick("link=return value");
        waitForPageToLoad();
        assertTrue(isElementPresent("methodToCall.cancel"));
        waitAndClick("methodToCall.cancel");
        waitAndClick("methodToCall.processAnswer.button0");
    }

    @Test
    /**
     * tests that a Role maintenance document is created for an edit operation originating from a lookup screen
     */
    public void testEditRole() throws Exception {
        assertEquals("Kuali Portal Index", getTitle());
        waitAndClick("link=Administration");
        waitForPageToLoad();
        assertEquals("Kuali Portal Index", getTitle());
        waitAndClick("link=Role");
        waitForPageToLoad();
        assertEquals("Kuali Portal Index", getTitle());
        selectFrame("iframeportlet");
        waitAndClick("//input[@name='methodToCall.search' and @value='search']");
        waitAndClick("link=edit");
        waitForPageToLoad();
        assertTrue(isElementPresent("methodToCall.cancel"));
        waitAndClick("methodToCall.cancel");
        waitAndClick("methodToCall.processAnswer.button0");
    }
}