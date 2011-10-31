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

package com.shroggle.util.html;

import junit.framework.Assert;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;

import java.util.Date;


/**
 * @author Stasuk Artem
 */
public class HtmlUtilTest {

    @Test
    public void encodeToPercent() {
        Assert.assertEquals("%3fa%26", HtmlUtil.encodeToPercent("?a&"));
    }

    @Test
    public void emptyOrValueForNull() {
        Assert.assertEquals("", HtmlUtil.emptyOrValue(null));
    }

    @Test
    public void ignoreHtml() {
        Assert.assertEquals("&lt;a&gt;", HtmlUtil.ignoreHtml("<a>"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getContentTypeByExtensionForNull() {
        HtmlUtil.getMimeByExtension(null);
    }

    @Test
    public void getContentTypeByExtensionForNotFound() {
        Assert.assertNull(HtmlUtil.getMimeByExtension("fff"));
    }

    @Test
    public void testRemoveEndingSlash() {
        org.junit.Assert.assertNull(HtmlUtil.removeEndingSlash(null));
        org.junit.Assert.assertEquals("", HtmlUtil.removeEndingSlash(""));
        org.junit.Assert.assertEquals("text", HtmlUtil.removeEndingSlash("text"));
        org.junit.Assert.assertEquals("text", HtmlUtil.removeEndingSlash("text/"));
        org.junit.Assert.assertEquals("text/text", HtmlUtil.removeEndingSlash("text/text/"));
        org.junit.Assert.assertEquals("text/text", HtmlUtil.removeEndingSlash("text/text/ "));

    }

    @Test
    public void getContentTypeByExtension() {
        Assert.assertEquals("image/gif", HtmlUtil.getMimeByExtension("gif"));
    }

    @Test
    public void ignoreWordIf() {
        Assert.assertEquals("test  greterter", HtmlUtil.ignoreWordIf("test <!--[if gte mso 9]> ffff<![endif]--> greterter"));
    }

    @Test
    public void ignoreWordIfEmpty() {
        Assert.assertEquals("test  greterter", HtmlUtil.ignoreWordIf("test <!--[if !supportEmptyParas]--> gg <!--[endif]--> greterter"));
    }

    @Test
    public void ignoreWordIfOtherVersion() {
        Assert.assertEquals("test  greterter", HtmlUtil.ignoreWordIf("test <!--[if gte mso 10]> ffff<![endif]--> greterter"));
    }

    @Test
    public void ignoreWordIfOtherClose() {
        Assert.assertEquals("test  greterter", HtmlUtil.ignoreWordIf("test <!--[if gte mso 10]> ffff<!--[endif]--> greterter"));
    }

    @Test
    public void ignoreWordIfMore() {
        Assert.assertEquals("test  greterter c  a", HtmlUtil.ignoreWordIf("test <!--[if gte mso 9]> ffff<![endif]--> greterter c <!--[if gte mso 9]> ffff<![endif]--> a"));
    }

    @Test
    public void ignoreWordInner() {
        Assert.assertEquals("greterter c  f <![endif]--> a", HtmlUtil.ignoreWordIf("greterter c <!--[if gte mso 9]> test <!--[if gte mso 9]> ffff<![endif]--> f <![endif]--> a"));
    }

    @Test
    public void getContentTypeByExtensionForUpperCase() {
        Assert.assertEquals("image/gif", HtmlUtil.getMimeByExtension("gIF"));
    }

    @Test(expected = NullPointerException.class)
    public void ignoreHtmlForNull() {
        HtmlUtil.ignoreHtml(null);
    }

    @Test
    public void emptyOrValue() {
        Assert.assertEquals("a", HtmlUtil.emptyOrValue("a"));
    }

    @Test
    public void encodeToPercentForNullString() {
        Assert.assertNull(HtmlUtil.encodeToPercent(null));
    }

    @Test
    public void formatDate1() {
        final Date date = new Date(108, 8, 28, 21, 57);
        assertEquals("Sunday, September 28, 2008. 9:57PM", HtmlUtil.formatDate(date));
    }

    @Test
    public void formatDate2() {
        final Date date = new Date(108, 7, 13, 12, 1);
        assertEquals("Wednesday, August 13, 2008. 12:1PM", HtmlUtil.formatDate(date));
    }

    @Test
    public void testRemoveIeComments() {
        String oldText = "&lt;!--[if !supportLineBreakNewLine]--&gt;fdgsdfgsfdg  <br> &lt;!--[endif]--><!--[if !supportLineBreakNewLine]-->nnnnnnnnnnnnnnnnnn<!--[endif]-->";
        String newText = HtmlUtil.removeIeComments(oldText);
        Assert.assertEquals("fdgsdfgsfdg  <br> nnnnnnnnnnnnnnnnnn", newText);
    }

    @Test
    public void testRemoveIeComments_withNull() {
        String newText = HtmlUtil.removeIeComments(null);
        Assert.assertNull(newText);
    }

    @Test
    public void testRemoveIeComments_withEmpty() {
        String newText = HtmlUtil.removeIeComments("");
        Assert.assertEquals("", newText);
    }

    @Test
    public void testContainsIeComments() {
        String oldText = "&lt;!--[if !supportLineBreakNewLine]--&gt;fdgsdfgsfdg  <br> &lt;!--[endif]--><!--[if !supportLineBreakNewLine]-->nnnnnnnnnnnnnnnnnn<!--[endif]-->";
        Assert.assertTrue(HtmlUtil.containsIeComments(oldText));
    }

    @Test
    public void testContainsIeComments_withNull() {
        Assert.assertFalse(HtmlUtil.containsIeComments(null));
    }

    @Test
    public void testContainsIeComments_withEmpty() {
        Assert.assertFalse(HtmlUtil.containsIeComments(""));
    }

    @Test
    public void testReplaceNewLineByBr() {
        String newString = HtmlUtil.replaceNewLineByBr(
                  "<p>12345\n" +
                  "12345\n" +
                  "12345</p>");
        Assert.assertEquals(newString, "12345<br>12345<br>12345");
    }

    @Test
    public void testRemoveAllTags() {
        final String withoutHtml = HtmlUtil.removeAllTags(
                "<div style=\"width:10%;float:left;" +
                        "                             alt=\"Delete\"\n" +
                        "                             style='cursor:pointer; display:none;'\n" +
                        "\">\n" +
                        "                        <img id=\"IMAGE_FILE_UPLOAD17680Image File UploadRemoveFormFileButton\" src=\"/images/cross-circle.png\"\n" +
                        "                             alt=\"Delete\"\n" +
                        "                             style='cursor:pointer; display:none;'\n" +
                        "                             onclick=\"\n" +
                        "                            removePreviewImage('IMAGE_FILE_UPLOAD17680Image File Upload');\n" +
                        "                            removeFormFile(\n" +
                        "                             null,\n" +
                        "                             null,\n" +
                        "                            'IMAGE_FILE_UPLOAD17680Image File Upload',\n" +
                        "                            'Image Files',\n" +
                        "                            '176',\n" +
                        "                            '2',\n" +
                        "                            'Image File Upload',\n" +
                        "                            'IMAGE_FILE_UPLOAD',\n" +
                        "                            'FORM_FILE');\">\n" +
                        " <br>   divhtml                </div>");
        Assert.assertEquals("divhtml", withoutHtml);
    }

    @Test
    public void testRemoveAllTags_null() {
        final String withoutHtml = HtmlUtil.removeAllTags(null);
        Assert.assertEquals(null, withoutHtml);
    }


    @Test
    public void testRemoveAllTagsExceptBr() {
        final String withoutHtml = HtmlUtil.removeAllTagsExceptBr(
                "<div style=\"width:10%;float:left;" +
                        "                             alt=\"Delete\"\n" +
                        "                             style='cursor:pointer; display:none;'\n" +
                        "\">\n" +
                        "                        <img id=\"IMAGE_FILE_UPLOAD17680Image File UploadRemoveFormFileButton\" src=\"/images/cross-circle.png\"\n" +
                        "                             alt=\"Delete\"\n" +
                        "                             style='cursor:pointer; display:none;'\n" +
                        "                             onclick=\"\n" +
                        "                            removePreviewImage('IMAGE_FILE_UPLOAD17680Image File Upload');\n" +
                        "                            removeFormFile(\n" +
                        "                             null,\n" +
                        "                             null,\n" +
                        "                            'IMAGE_FILE_UPLOAD17680Image File Upload',\n" +
                        "                            'Image Files',\n" +
                        "                            '176',\n" +
                        "                            '2',\n" +
                        "                            'Image File Upload',\n" +
                        "                            'IMAGE_FILE_UPLOAD',\n" +
                        "                            'FORM_FILE');\">\n" +
                        " <     br >   divhtml    <bred>    <br/>< br/><br/ ><br />< br     / >   <br clear=\"all\" / >     </div> <her dfas aadsf/ ad>"
        );
        Assert.assertEquals("<     br >   divhtml        <br/>< br/><br/ ><br />< br     / >   <br clear=\"all\" / >", withoutHtml);
    }


    @Test
    public void testRemoveAllTagsExceptBr_null() {
        final String withoutHtml = HtmlUtil.removeAllTagsExceptBr(null);
        Assert.assertEquals(null, withoutHtml);
    }

    @Test
    public void testReplaceBlockTagsByBr() {
        final String withoutHtml = HtmlUtil.replaceBlockTagsByBr(
                "<div>divText</div>" +
                        "<span>spanText</span>" +
                        "<br>");
        Assert.assertEquals("<div>divText<br><span>spanText</span><br>", withoutHtml);
    }

    @Test
    public void testReplaceBlockTagsByBr_withNullHtml() {
        final String withoutHtml = HtmlUtil.replaceBlockTagsByBr(null);
        Assert.assertEquals(null, withoutHtml);
    }

}
