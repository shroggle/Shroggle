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
package com.shroggle.logic.start;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class UpdateAlterTest {
    @Test

    public void testGetAlterValues_withNormalQuotes() throws Exception {
        final List<String> alters = new UpdateAlter(0, "").getAlterValues(
                "update draftPageSettings set layoutFile = 'header_main_footer_2bottom.html' where layoutFile = 'head_foot_1top_2bottom.html';\n" +
                        "update draftPageSettings set layoutFile = 'header_main_footer_2top.html' where layoutFile = 'head_foot_2top_1bottom.html';\n" +
                        "update draftPageSettings set layoutFile = '2headers_2main_footer_leftmenu.html' where layoutFile = '2headers_left_2main_footer.html';\n" +
                        "update draftPageSettings set layoutFile = '2headers_2main2_footer_leftmenu.html' where layoutFile = '2headers_left_2mainwide_footer.html';\n" +
                        "update draftPageSettings set layoutFile = '2headers_main_footer_leftmenu.html' where layoutFile = '2headers_left_main_footer.html';\n" +
                        "update draftPageSettings set layoutFile = 'header_2main_footer_leftmenu.html' where layoutFile = 'header_left_2main_footer.html';\n" +
                        "update draftPageSettings set layoutFile = 'header_main_footer_leftmenu.html' where layoutFile = 'header_left_main_footer.html';");
        Assert.assertEquals(7, alters.size());
        Assert.assertEquals("update draftPageSettings set layoutFile = 'header_main_footer_2bottom.html' where layoutFile = 'head_foot_1top_2bottom.html'", alters.get(0));
        Assert.assertEquals("\nupdate draftPageSettings set layoutFile = 'header_main_footer_2top.html' where layoutFile = 'head_foot_2top_1bottom.html'", alters.get(1));
        Assert.assertEquals("\nupdate draftPageSettings set layoutFile = '2headers_2main_footer_leftmenu.html' where layoutFile = '2headers_left_2main_footer.html'", alters.get(2));
        Assert.assertEquals("\nupdate draftPageSettings set layoutFile = '2headers_2main2_footer_leftmenu.html' where layoutFile = '2headers_left_2mainwide_footer.html'", alters.get(3));
        Assert.assertEquals("\nupdate draftPageSettings set layoutFile = '2headers_main_footer_leftmenu.html' where layoutFile = '2headers_left_main_footer.html'", alters.get(4));
        Assert.assertEquals("\nupdate draftPageSettings set layoutFile = 'header_2main_footer_leftmenu.html' where layoutFile = 'header_left_2main_footer.html'", alters.get(5));
        Assert.assertEquals("\nupdate draftPageSettings set layoutFile = 'header_main_footer_leftmenu.html' where layoutFile = 'header_left_main_footer.html'", alters.get(6));
    }

    @Test
    public void testGetAlterValues_withQuotesWithSemicolon() throws Exception {
        final List<String> alters = new UpdateAlter(0, "").getAlterValues(
                "update draftPageSettings set layoutFile = 'header_main_footer_2&lt;bottom.html' where layoutFile = 'head_foot_1to;p_2bottom.html';\n" +
                        "update draftPageSettings set layoutFile = 'header_main_footer_2top.html' where layoutFile = 'head_foot_2top_1bottom.html';\n" +
                        "update draftPageSettings set layoutFile = '2headers_2main_footer_leftmenu.html' where layoutFile = '2headers_left_2main_footer.html';\n" +
                        "update draftPageSettings set layoutFile = '2headers_2main2_footer_leftmenu.html' where layoutFile = '2headers_left_2mainwide_footer.html';\n" +
                        "update draftPageSettings set layoutFile = '2headers_main_footer_leftmenu.html' where layoutFile = '2headers_left_main_footer.html';\n" +
                        "update draftPageSettings set layoutFile = 'header_2main_footer_leftmenu.html' where layoutFile = 'header_left_2main_footer.html';\n" +
                        "update draftPageSettings set layoutFile = 'header_main_footer_leftmenu.html' where layoutFile = 'header_left_main_footer.html';");
        Assert.assertEquals(7, alters.size());
        Assert.assertEquals("update draftPageSettings set layoutFile = 'header_main_footer_2&ltbottom.html' where layoutFile = 'head_foot_1top_2bottom.html'", alters.get(0));
        Assert.assertEquals("\nupdate draftPageSettings set layoutFile = 'header_main_footer_2top.html' where layoutFile = 'head_foot_2top_1bottom.html'", alters.get(1));
        Assert.assertEquals("\nupdate draftPageSettings set layoutFile = '2headers_2main_footer_leftmenu.html' where layoutFile = '2headers_left_2main_footer.html'", alters.get(2));
        Assert.assertEquals("\nupdate draftPageSettings set layoutFile = '2headers_2main2_footer_leftmenu.html' where layoutFile = '2headers_left_2mainwide_footer.html'", alters.get(3));
        Assert.assertEquals("\nupdate draftPageSettings set layoutFile = '2headers_main_footer_leftmenu.html' where layoutFile = '2headers_left_main_footer.html'", alters.get(4));
        Assert.assertEquals("\nupdate draftPageSettings set layoutFile = 'header_2main_footer_leftmenu.html' where layoutFile = 'header_left_2main_footer.html'", alters.get(5));
        Assert.assertEquals("\nupdate draftPageSettings set layoutFile = 'header_main_footer_leftmenu.html' where layoutFile = 'header_left_main_footer.html'", alters.get(6));
    }

    @Test
    public void testGetAlterValues_withoutQuotes() throws Exception {
        final List<String> alters = new UpdateAlter(0, "").getAlterValues(
                "insert into draftVideoNames (name, id) select videoName, formId from videos1;\n" +
                        "update siteItems set name = (select name from draftVideoNames where id = formId) where formId in (select formId from videos1);\n" +
                        "drop table draftVideoNames;");
        Assert.assertEquals(3, alters.size());
        Assert.assertEquals("insert into draftVideoNames (name, id) select videoName, formId from videos1", alters.get(0));
        Assert.assertEquals("\nupdate siteItems set name = (select name from draftVideoNames where id = formId) where formId in (select formId from videos1)", alters.get(1));
        Assert.assertEquals("\ndrop table draftVideoNames", alters.get(2));
    }
}
