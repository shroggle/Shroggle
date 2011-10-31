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
package com.shroggle.presentation.gallery;

import com.shroggle.logic.gallery.PaypalSettingsData;
import org.junit.Test;
import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 */
public class PaypalSettingsDataTest {

    @Test
    public void create(){
        final PaypalSettingsData data = new PaypalSettingsData();
        Assert.assertNotNull(data.getAlign());
        Assert.assertNotNull(data.getColumn());
    }
}
