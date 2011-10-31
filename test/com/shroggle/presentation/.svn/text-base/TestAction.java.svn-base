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
package com.shroggle.presentation;

import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpServletResponse;

/**
 * @author Artem Stasuk
 */
public abstract class TestAction<T extends Action> extends TestActionOrService<T> {

    public TestAction(final Class<T> actionClass, final boolean initContext) {
        super(actionClass);

        if (initContext) {
            actionOrService.setContext(new ActionBeanContext());
            actionOrService.getContext().setRequest(new MockHttpServletRequest("", ""));
            actionOrService.getContext().setResponse(new MockHttpServletResponse());
        }
    }

    public TestAction(final Class<T> actionClass) {
        this(actionClass, false);
    }

}