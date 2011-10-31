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

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Artem Stasuk
 */
public class RenderPatternTest {

    @Test(expected = IllegalArgumentException.class)
    public void createWithNull() {
        new RenderPattern(null);
    }

    @Test
    public void create() {
        new RenderPattern(new RenderPatternListener() {

            @Override
            public String[] getPatterns() {
                return new String[0];
            }

            @Override
            public String execute(RenderContext context, String pattern) {
                return null;
            }

        });
    }

    @Test
    public void executeWithoutPatterns() throws IOException, ServletException {
        final Render render = new RenderPattern(new RenderPatternListener() {

            @Override
            public String[] getPatterns() {
                return new String[0];
            }

            @Override
            public String execute(final RenderContext context, final String pattern) {
                Assert.fail();
                return null;
            }

        });

        render.execute(null, null);
    }

    @Test
    public void executeCheckCount() throws IOException, ServletException {
        final AtomicInteger executeCount = new AtomicInteger();
        final Render render = new RenderPattern(new RenderPatternListener() {

            @Override
            public String[] getPatterns() {
                return new String[] {"1"};
            }

            @Override
            public String execute(final RenderContext context, final String pattern) {
                executeCount.incrementAndGet();
                return null;
            }

        });

        render.execute(null, new StringBuilder("1 1 1"));

        Assert.assertEquals(1, executeCount.get());
    }

    @Test
    public void executeMany() throws IOException, ServletException {
        final AtomicInteger executeCount = new AtomicInteger();
        final Render render = new RenderPattern(new RenderPatternListener() {

            @Override
            public String[] getPatterns() {
                return new String[] {"1", "2"};
            }

            @Override
            public String execute(final RenderContext context, final String pattern) {
                executeCount.incrementAndGet();
                return null;
            }

        });

        render.execute(null, new StringBuilder("a 2"));

        Assert.assertEquals(1, executeCount.get());
    }

    @Test
    public void executeInsert() throws IOException, ServletException {
        final Render render = new RenderPattern(new RenderPatternListener() {

            @Override
            public String[] getPatterns() {
                return new String[] {"1", "2"};
            }

            @Override
            public String execute(final RenderContext context, final String pattern) {
                return "wwww";
            }

        });

        final StringBuilder builder = new StringBuilder("a 2");
        render.execute(null, builder);

        Assert.assertEquals("a wwww", builder.toString());
    }

    @Test
    public void executeInsertNull() throws IOException, ServletException {
        final Render render = new RenderPattern(new RenderPatternListener() {

            @Override
            public String[] getPatterns() {
                return new String[] {"1", "2"};
            }

            @Override
            public String execute(final RenderContext context, final String pattern) {
                return null;
            }

        });

        final StringBuilder builder = new StringBuilder("a 2");
        render.execute(null, builder);

        Assert.assertEquals("a ", builder.toString());
    }

    @Test
    public void executeCheckPattern() throws IOException, ServletException {
        final AtomicInteger executeCount = new AtomicInteger();
        final Render render = new RenderPattern(new RenderPatternListener() {

            @Override
            public String[] getPatterns() {
                return new String[] {"1", "2"};
            }

            @Override
            public String execute(final RenderContext context, final String pattern) {
                Assert.assertEquals("2", pattern);
                executeCount.incrementAndGet();
                return null;
            }

        });

        render.execute(null, new StringBuilder("a 2"));
        Assert.assertEquals(1, executeCount.get());
    }

}
