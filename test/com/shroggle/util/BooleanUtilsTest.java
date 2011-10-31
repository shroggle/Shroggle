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
package com.shroggle.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Balakirev Anatoliy
 */
public class BooleanUtilsTest {


    @Test
    public void testToBooleanDefaultIfNull() throws Exception {
        Assert.assertEquals(false, BooleanUtils.toBooleanDefaultIfNull(null, false));
        Assert.assertEquals(true, BooleanUtils.toBooleanDefaultIfNull(null, true));


        Assert.assertEquals(false, BooleanUtils.toBooleanDefaultIfNull("", false));
        Assert.assertEquals(true, BooleanUtils.toBooleanDefaultIfNull("", true));
        

        Assert.assertEquals(true, BooleanUtils.toBooleanDefaultIfNull(true, false));
        Assert.assertEquals(true, BooleanUtils.toBooleanDefaultIfNull(true, true));

        Assert.assertEquals(false, BooleanUtils.toBooleanDefaultIfNull(false, false));
        Assert.assertEquals(false, BooleanUtils.toBooleanDefaultIfNull(false, true));



    }


}
