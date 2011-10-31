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
package com.shroggle.util.process.timecounter;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 24.10.2008
 */
public class TimeCounterCreatorEmptyTest {

    @Test
    public void create() {
        Assert.assertNotNull(creator.create("f"));
    }

    @Test
    public void createWithNull() {
        Assert.assertNotNull(creator.create(null));
    }

    @Test
    public void getResults() {
        Assert.assertNotNull(creator.getResults());
        creator.create("ff");
        Assert.assertEquals(0, creator.getResults().size());
    }

    private final TimeCounterCreator creator = new TimeCounterCreatorEmpty();

}
