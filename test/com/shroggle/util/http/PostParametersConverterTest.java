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
package com.shroggle.util.http;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Balakirev Anatoliy
 */
public class PostParametersConverterTest {

    @Test
    public void testConvert() throws Exception {
        final Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("firstName", "Anatoliy");
        map.put("lastName", "Balakirev");
        map.put("test", "aaa&bbb");

        Assert.assertEquals("firstName=Anatoliy&lastName=Balakirev&test=aaa%26bbb", PostParametersConverter.convert(map));
    }

    @Test
    public void testConvert_withEmpty() throws Exception {
        final Map<String, String> map = new LinkedHashMap<String, String>();

        Assert.assertEquals("", PostParametersConverter.convert(map));
    }

    @Test
    public void testConvert_withNull() throws Exception {
        Assert.assertEquals("", PostParametersConverter.convert(null));
    }

}
