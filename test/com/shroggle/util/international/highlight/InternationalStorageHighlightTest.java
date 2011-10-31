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

package com.shroggle.util.international.highlight;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.MockConfigStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.international.hightlight.InternationalHighlight;
import com.shroggle.util.international.property.InternationalPropertyBundle;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;

import java.util.ListResourceBundle;

/**
 * @author Artem Stasuk
 */
public class InternationalStorageHighlightTest {

    @After
    public void after() {
        ServiceLocator.setConfigStorage(null);
    }

    @Test
    public void get() {
        ServiceLocator.setConfigStorage(new MockConfigStorage());
        International international = new InternationalHighlight(
                new InternationalPropertyBundle(new ListResourceBundle() {

                    protected Object[][] getContents() {
                        Object[][] objects = new Object[1][0];
                        objects[0] = new Object[2];
                        objects[0][0] = "AAA";
                        objects[0][1] = "GG";
                        return objects;
                    }

                }));

        Assert.assertEquals("Get not hightlight value!", "!GG", international.get("AAA"));
    }

}