/*********************************************************************
 *                                                                   *
 * Copyright (c) 2007-2011 by Web-Deva.                              *
 * All rights reserved.                                              *
 *                                                                   *
 * This computer program is protected by copyright law and           *
 * international treaties. Unauthorized reproduction or distribution *
 * of this program, or any portion of it, may result in severe civil *
 * and criminal penalties, and will be prosecuted to the maximum     *
 * extent possible under the law.                                    *
 *                                                                   *
 *********************************************************************/
package com.shroggle.presentation.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.exception.CannotShowHelpWindowException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <p>
 * Author: Igor Kanshin (grenader).
 * </p>
 * Date: Apr 21, 2009
 */

@RunWith(value = TestRunnerWithMockServices.class)
public class ShowHelpWindowServiceTestCase {

    @Test
    public void testShow_Ok() {
        Config config = ServiceLocator.getConfigStorage().get();
        config.setHelpWindowURL("http://www.google.com");

        assertEquals("http://www.google.com", new ShowHelpWindowService().show());
    }

    @Test(expected = CannotShowHelpWindowException.class)
    public void testShow_Empty() {
        Config config = ServiceLocator.getConfigStorage().get();
        config.setHelpWindowURL("");

        assertEquals("http://www.google.com", new ShowHelpWindowService().show());
    }

    @Test(expected = CannotShowHelpWindowException.class)
    public void testShow_Null() {
        Config config = ServiceLocator.getConfigStorage().get();
        config.setHelpWindowURL(null);

        assertEquals("http://www.google.com", new ShowHelpWindowService().show());
    }
}
