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

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class ResolutionParameterTest {

    @Test
    public void create() {
        Object value = new Object();
        ResolutionParameter resolutionParameter = new ResolutionParameter("f", value);
        Assert.assertEquals("f", resolutionParameter.getName());
        Assert.assertEquals(value, resolutionParameter.getValue());
    }

    @Test
    public void createWithNull() {
        ResolutionParameter resolutionParameter = new ResolutionParameter(null, null);
        Assert.assertNull(resolutionParameter.getName());
        Assert.assertNull(resolutionParameter.getValue());
    }

}
