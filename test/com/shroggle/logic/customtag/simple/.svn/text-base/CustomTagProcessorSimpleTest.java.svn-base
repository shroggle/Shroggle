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
package com.shroggle.logic.customtag.simple;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Page;
import com.shroggle.entity.PageVersionType;
import com.shroggle.logic.customtag.CustomTag;
import com.shroggle.logic.customtag.CustomTagInternalToHtmlPageLink;
import com.shroggle.logic.site.page.PageManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class CustomTagProcessorSimpleTest {

    @Test
    public void create() {
        new CustomTagProcessorSimple();
    }

    @Test
    public void executeNullNull() {
        new CustomTagProcessorSimple().execute(null, null, null);
    }

    @Test
    public void executeNull() {
        new CustomTagProcessorSimple().execute(null, "He was bad man.", null);
    }

    @Test
    public void executeWithTags() {
        final Page page = TestUtil.createPageAndSite();
        final PageManager pageManager = TestUtil.createPageVersion(page, PageVersionType.WORK);
        pageManager.getWorkPageSettings().setName("mmm");

        final String string =
                "He was bad <a class=\"shroggleTag\" pageId=\""
                        + pageManager.getId() + "\" href=\"/aaa\">тут</a>.";

        final String result = new CustomTagProcessorSimple().execute(
                new CustomTag[]{new CustomTagInternalToHtmlPageLink()}, string, null);

        Assert.assertEquals(
                "He was bad <a class=\"shroggleTag\" pageId=\"" + pageManager.getId()
                        + "\" href=\"http://null.shroggle.com/null\">тут</a>.", result);
    }

    @Test
    public void executeWithManyTags() {
        final Page page = TestUtil.createPageAndSite();
        final PageManager pageManager = TestUtil.createPageVersion(page, PageVersionType.WORK);
        pageManager.getWorkPageSettings().setName("mmm");

        final String string =
                "He was bad <a class=\"shroggleTag\" pageId=\""
                        + pageManager.getId() + "\" href=\"/aaa\">here</a>, " +
                        "and after he dead. <h1 class=\"shroggleTag\"></h1>";

        final String result = new CustomTagProcessorSimple().execute(
                new CustomTag[]{new CustomTagInternalToHtmlPageLink()}, string, null);

        Assert.assertEquals(
                "He was bad <a class=\"shroggleTag\" pageId=\"" + pageManager.getId()
                        + "\" href=\"http://null.shroggle.com/null\">here</a>, " +
                        "and after he dead. <h1 class=\"shroggleTag\"></h1>", result);
    }

}
