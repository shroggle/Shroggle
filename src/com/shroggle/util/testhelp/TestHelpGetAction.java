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
package com.shroggle.util.testhelp;

import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.Map;

/**
 * @author Artem Stasuk
 */
@UrlBinding(value = "/system/testHelpGet.action")
public class TestHelpGetAction extends Action {

    @DefaultHandler
    public Resolution show() {
        final TestHelpStorage testHelpStorage = ServiceLocator.getTestHelpStorage();
        if (testHelpStorage != null) {
            stringBySources = testHelpStorage.getEvents();
        }
        return resolutionCreator.forwardToUrl("/system/testHelpGet.jsp");
    }

    public Map<TestHelpSource, TestHelpStrings> getStringBySources() {
        return stringBySources;
    }

    private Map<TestHelpSource, TestHelpStrings> stringBySources;
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();

}
