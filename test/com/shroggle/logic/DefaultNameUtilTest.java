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
package com.shroggle.logic;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Balakirev Anatoliy
 */
public class DefaultNameUtilTest {

    @Test
    public void testGetNext() throws Exception {
        final String defaultName = "name";
        final Set<String> usedNames = new HashSet<String>(Arrays.asList("name1", "name2"));
        Assert.assertEquals("name3", new DefaultNameUtil().getNext(defaultName, false, usedNames));
    }

    // This test is very slow. Tolik
    /*@Test(expected = AllNamesAreUsedException.class)
    public void testGetNext_withAllUsed() throws Exception {
        final String defaultName = "name";

        final Set<String> usedNames = new HashSet<String>();
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            usedNames.add(defaultName + i);
        }

        Assert.assertEquals("", new DefaultNameUtil().getNext(defaultName, false, usedNames));
    }*/

}
