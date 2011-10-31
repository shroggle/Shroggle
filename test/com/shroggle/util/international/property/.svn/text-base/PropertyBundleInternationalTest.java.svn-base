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

package com.shroggle.util.international.property;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.MockConfigStorage;
import com.shroggle.util.international.International;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;

import java.util.ListResourceBundle;

/**
 * @author Artem Stasuk
 */
public class PropertyBundleInternationalTest {

    @After
    public void after() {
        ServiceLocator.setConfigStorage(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithNullResourceBundle() {
        new InternationalPropertyBundle(null);
    }

    @Test
    public void create() {
        ServiceLocator.setConfigStorage(new MockConfigStorage());
        new InternationalPropertyBundle(new ListResourceBundle() {

            @Override
            protected Object[][] getContents() {
                return new Object[0][];
            }

        });
    }

    @Test
    public void get() {
        ServiceLocator.setConfigStorage(new MockConfigStorage());
        International international = new InternationalPropertyBundle(new ListResourceBundle() {

            protected Object[][] getContents() {
                Object[][] objects = new Object[1][0];
                objects[0] = new Object[2];
                objects[0][0] = "AAA";
                objects[0][1] = "GG";
                return objects;
            }

        });

        Assert.assertEquals("Get incorrect value!", "GG", international.get("AAA"));
    }

}
