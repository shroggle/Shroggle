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
package com.shroggle.presentation.site.render;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class RenderAppenderRealTest {

    @Test
    public void createNull() {
        new RenderAppenderReal(null, null);
    }

    @Test
    public void existNotFound() {
        final StringBuilder builder = new StringBuilder("22222");
        final RenderAppender appender = new RenderAppenderReal(new String[] {"f"}, builder);

        Assert.assertFalse(appender.exist());
        Assert.assertNull(appender.getExistPattern());
        Assert.assertEquals("22222", builder.toString());
    }

    @Test
    public void appendNotFound() {
        final StringBuilder builder = new StringBuilder("22222");
        final RenderAppender appender = new RenderAppenderReal(new String[] {"f"}, builder);

        appender.exist();
        appender.append("1");

        Assert.assertEquals("22222", builder.toString());
    }

    @Test
    public void appendNotFoundWithoutExist() {
        final StringBuilder builder = new StringBuilder("22222");
        final RenderAppender appender = new RenderAppenderReal(new String[] {"f"}, builder);

        appender.append("1");

        Assert.assertEquals("22222", builder.toString());
    }

    @Test
    public void append() {
        final StringBuilder builder = new StringBuilder("222f22");
        final RenderAppender appender = new RenderAppenderReal(new String[] {"f"}, builder);

        Assert.assertTrue(appender.exist());

        appender.append("1-1");

        Assert.assertEquals("2221-122", builder.toString());
    }

    @Test
    public void appendWithoutExist() {
        final StringBuilder builder = new StringBuilder("222f22");
        final RenderAppender appender = new RenderAppenderReal(new String[] {"f"}, builder);

        appender.append("1-1");

        Assert.assertEquals("2221-122", builder.toString());
    }

    @Test
    public void existTwice() {
        final StringBuilder builder = new StringBuilder("222f22");
        final RenderAppender appender = new RenderAppenderReal(new String[] {"f"}, builder);

        Assert.assertTrue(appender.exist());
        Assert.assertTrue(appender.exist());
        Assert.assertTrue(appender.exist());

        appender.append("1-1");

        Assert.assertEquals("2221-122", builder.toString());
    }

    @Test
    public void existAfterAppend() {
        final StringBuilder builder = new StringBuilder("222f22");
        final RenderAppender appender = new RenderAppenderReal(new String[] {"f"}, builder);

        Assert.assertTrue(appender.exist());
        Assert.assertTrue(appender.exist());
        Assert.assertTrue(appender.exist());

        appender.append("1-1");

        Assert.assertFalse(appender.exist());

        Assert.assertEquals("2221-122", builder.toString());
    }

    @Test
    public void appendNull() {
        final StringBuilder builder = new StringBuilder("222f22");
        final RenderAppender appender = new RenderAppenderReal(new String[] {"f"}, builder);

        appender.append(null);

        Assert.assertEquals("f", appender.getExistPattern());
        Assert.assertEquals("22222", builder.toString());
    }

}
