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

import com.shroggle.TestBaseWithMockService;
import junit.framework.Assert;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.validation.ValidationError;
import org.junit.Test;

import java.util.List;

/**
 * @author Stasuk Artem
 */
public class ActionTest extends TestBaseWithMockService {

    @Test
    public void addValidationErrorByThrowable() {
        final ActionBeanContext actionBeanContext = new ActionBeanContext();

        Action action = new Action();
        action.setContext(actionBeanContext);
        action.addValidationError(new Exception());

        final List<ValidationError> validationErrors =
                actionBeanContext.getValidationErrors().get(Exception.class.getName());

        Assert.assertNotNull(validationErrors);
        Assert.assertEquals(1, validationErrors.size());
        ValidationError validationError = validationErrors.get(0);
        Assert.assertNotNull(validationError);
    }

}
