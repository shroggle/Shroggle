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
package com.shroggle.util.html.processor.simple;

import com.shroggle.ParameterizedTestRunner;
import com.shroggle.ParameterizedTestRunnerWithServiceLocator;
import com.shroggle.ParameterizedUsedRunner;
import com.shroggle.util.html.processor.HtmlFlatMediaBlock;
import com.shroggle.util.html.processor.HtmlListener;
import com.shroggle.util.html.processor.HtmlMediaBlock;
import com.shroggle.util.html.processor.HtmlProcessor;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Artem Stasuk
 */
@RunWith(ParameterizedTestRunner.class)
@ParameterizedUsedRunner(ParameterizedTestRunnerWithServiceLocator.class)
public class HtmlProcessorsTest {

    @Parameterized.Parameters
    public static Collection regExValues() {
        return Arrays.asList(new Object[][]{{new HtmlProcessorSimple()}, {new HtmlProcessorRegex()}});
    }

    public HtmlProcessorsTest(final HtmlProcessor processor) {
        this.processor = processor;
    }

    @Test
    public void executeWithMediaBlock() {
        processor.execute(new StringBuilder("<!-- MEDIA_BLOCK -->"), listenerMock);

        Assert.assertEquals(1, listenerMock.getBlocks().size());
        Assert.assertTrue(listenerMock.getFlatBlocks().isEmpty());
    }

    @Test
    public void executeWithManyMediaBlock() {
        processor.execute(new StringBuilder("<!-- MEDIA_BLOCK --> 22 <!-- MEDIA_BLOCK -->"), listenerMock);

        Assert.assertEquals(2, listenerMock.getBlocks().size());
        Assert.assertTrue(listenerMock.getFlatBlocks().isEmpty());
    }

    @Test
    public void executeWithSetHtmlMediaBlock() {
        final StringBuilder html = new StringBuilder("a <!-- MEDIA_BLOCK --> 22");
        processor.execute(html, new HtmlListener() {

            @Override
            public void onMediaBlock(HtmlMediaBlock block) {
                block.setHtml("gg1");
            }

            @Override
            public void onFlatMediaBlock(HtmlFlatMediaBlock block) {

            }

        });

        Assert.assertEquals("a gg1 22", html.toString());
    }

    @Test
    public void executeWithSetHtmlManyMediaBlock() {
        final StringBuilder html = new StringBuilder("<!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK -->");
        processor.execute(html, new HtmlListener() {

            @Override
            public void onMediaBlock(HtmlMediaBlock block) {
                block.setHtml("gg1");
            }

            @Override
            public void onFlatMediaBlock(HtmlFlatMediaBlock block) {

            }

        });

        Assert.assertEquals("gg1gg1", html.toString());
    }

    @Test
    public void executeWithSetMixed() {
        final StringBuilder html = new StringBuilder(
                "I <!-- MEDIA_BLOCK --> test <span class=\"mediaBlock bold\">&nbsp;</span>");
        processor.execute(html, new HtmlListener() {

            @Override
            public void onMediaBlock(HtmlMediaBlock block) {
                block.setHtml("am");
            }

            @Override
            public void onFlatMediaBlock(HtmlFlatMediaBlock block) {
                block.setId("person");
                block.setHtml("testing");
            }

        });

        Assert.assertEquals(
                "I am test <span class=\"mediaBlock bold\" id=\"person\">testing</span>",
                html.toString());
    }

    @Test
    public void executeWithSetDigits() {
        final StringBuilder html = new StringBuilder(
                "I <!-- MEDIA_BLOCK --> test <span class=\"mediaBlock bold mediaBlock3\">&nbsp;</span>");
        processor.execute(html, new HtmlListener() {

            @Override
            public void onMediaBlock(HtmlMediaBlock block) {
                block.setHtml("am");
            }

            @Override
            public void onFlatMediaBlock(HtmlFlatMediaBlock block) {
                block.setId("person");
                block.setHtml("testing");
            }

        });

        Assert.assertEquals(
                "I am test <span class=\"mediaBlock bold mediaBlock3\" id=\"person\">testing</span>",
                html.toString());
    }

    @Test
    public void executeWithSetHtmlAndIdFlatMediaBlock() {
        final StringBuilder html = new StringBuilder("a <b class=\"test mediaBlock\"></b> 22");
        processor.execute(html, new HtmlListener() {

            @Override
            public void onMediaBlock(HtmlMediaBlock block) {
            }

            @Override
            public void onFlatMediaBlock(HtmlFlatMediaBlock block) {
                block.setId("fork");
                block.setHtml("vasya");
            }

        });

        Assert.assertEquals("a <b class=\"test mediaBlock\" id=\"fork\">vasya</b> 22", html.toString());
    }

    @Test
    public void executeWithSetHtmlAndIdFlatMediaBlock2() {
        final StringBuilder html = new StringBuilder(
                "<div class=\"mainDiv\">\n" +
                        "    <div id=\"topPane\"><div class=\"content_box media_block_fixed mediaBlock\">&nbsp;</div></div>\n" +
                        "    <div id=\"bottomPane\"><div class=\"content_box mediaBlock\">&nbsp;</div></div>\n" +
                        "</div>");
        processor.execute(html, new HtmlListener() {

            @Override
            public void onMediaBlock(HtmlMediaBlock block) {
            }

            @Override
            public void onFlatMediaBlock(HtmlFlatMediaBlock block) {
                block.setId("fork");
                block.setHtml("vasya");
            }

        });

        Assert.assertEquals("<div class=\"mainDiv\">\n" +
                "    <div id=\"topPane\"><div class=\"content_box media_block_fixed mediaBlock\" id=\"fork\">vasya</div></div>\n" +
                "    <div id=\"bottomPane\"><div class=\"content_box mediaBlock\" id=\"fork\">vasya</div></div>\n" +
                "</div>", html.toString());
    }

    @Test
    public void executeWithSetHtmlAndIdFlatMediaBlock3() {
        final StringBuilder html = new StringBuilder(
                "<div class=\"mainDiv\">\n" +
                        "    <div id=\"topPane\"><div class=\"mediaBlock content_box media_block_fixed \">&nbsp;</div></div>\n" +
                        "    <div id=\"bottomPane\"><div class=\"content_box mediaBlock\">&nbsp;</div></div>\n" +
                        "</div>");
        processor.execute(html, new HtmlListener() {

            @Override
            public void onMediaBlock(HtmlMediaBlock block) {
            }

            @Override
            public void onFlatMediaBlock(HtmlFlatMediaBlock block) {
                block.setId("fork");
                block.setHtml("vasya");
            }

        });

        Assert.assertEquals("<div class=\"mainDiv\">\n" +
                "    <div id=\"topPane\"><div class=\"mediaBlock content_box media_block_fixed \" id=\"fork\">vasya</div></div>\n" +
                "    <div id=\"bottomPane\"><div class=\"content_box mediaBlock\" id=\"fork\">vasya</div></div>\n" +
                "</div>", html.toString());
    }

    @Test
    public void executeWithSetHtmlFlatMediaBlockWithPatternMediaBlock() {
        final StringBuilder html = new StringBuilder("a <b class=\"test mediaBlock\"></b> 22");
        processor.execute(html, new HtmlListener() {

            @Override
            public void onMediaBlock(HtmlMediaBlock block) {
            }

            @Override
            public void onFlatMediaBlock(HtmlFlatMediaBlock block) {
                block.setHtml("mediaBlock");
            }

        });

        Assert.assertEquals("a <b class=\"test mediaBlock\">mediaBlock</b> 22", html.toString());
    }

    @Test
    public void executeWithSetHtmlFlatMediaBlockWithBigHtml() {
        final StringBuilder html = new StringBuilder("<b class=\"mediaBlock\"></b> <b class=\"mediaBlock\"></b>");
        processor.execute(html, new HtmlListener() {

            @Override
            public void onMediaBlock(HtmlMediaBlock block) {
            }

            @Override
            public void onFlatMediaBlock(HtmlFlatMediaBlock block) {
                block.setHtml("11111111111111111111111111111111111111111111111111111111111111111");
            }

        });

        Assert.assertEquals(
                "<b class=\"mediaBlock\">11111111111111111111111111111111111111111111111111111111111111111</b> " +
                        "<b class=\"mediaBlock\">11111111111111111111111111111111111111111111111111111111111111111</b>",
                html.toString());
    }

    @Test
    public void executeWithSetHtmlFlatMediaBlock() {
        final StringBuilder html = new StringBuilder("a <b class=\"test mediaBlock\"></b> 22");
        processor.execute(html, new HtmlListener() {

            @Override
            public void onMediaBlock(HtmlMediaBlock block) {
            }

            @Override
            public void onFlatMediaBlock(HtmlFlatMediaBlock block) {
                block.setHtml("vasya");
            }

        });

        Assert.assertEquals("a <b class=\"test mediaBlock\">vasya</b> 22", html.toString());
    }

    @Test
    public void executeWithNotRealFlatMediaBlock() {
        final StringBuilder html = new StringBuilder("a mediaBlockCount");
        processor.execute(html, listenerMock);

        if (processor instanceof HtmlProcessorRegex) {
            Assert.assertTrue(listenerMock.getFlatBlocks().isEmpty());
        }
    }

    @Test
    public void executeWithSetHtmlFlatMediaBlockWithEnter() {
        final StringBuilder html = new StringBuilder("a\n <b class=\"test mediaBlock\"></b> 22");
        processor.execute(html, new HtmlListener() {

            @Override
            public void onMediaBlock(HtmlMediaBlock block) {
            }

            @Override
            public void onFlatMediaBlock(HtmlFlatMediaBlock block) {
                block.setHtml("vasya");
            }

        });

        Assert.assertEquals("a\n <b class=\"test mediaBlock\">vasya</b> 22", html.toString());
    }

    @Test
    public void executeWithSetIdFlatMediaBlock() {
        final StringBuilder html = new StringBuilder("a <b class=\"test mediaBlock\"></b> 22");
        processor.execute(html, new HtmlListener() {

            @Override
            public void onMediaBlock(HtmlMediaBlock block) {
            }

            @Override
            public void onFlatMediaBlock(HtmlFlatMediaBlock block) {
                block.setId("vasya");
            }

        });

        Assert.assertEquals("a <b class=\"test mediaBlock\" id=\"vasya\"></b> 22", html.toString());
    }

    @Test
    public void executeWithFlatMediaBlock() {
        processor.execute(new StringBuilder("<b class=\"mediaBlock\"></b>"), listenerMock);

        Assert.assertTrue(listenerMock.getBlocks().isEmpty());
        Assert.assertEquals(1, listenerMock.getFlatBlocks().size());
    }

    @Test
    public void executeWithFlatMediaBlock2() {
        processor.execute(new StringBuilder("\n" +
                ".shroggleLogo {\n" +
                "     width: 100%;\n" +
                "     clear:both;\n" +
                "     text-align: center;\n" +
                " }\n" +
                " .shroggleLogo p {\n" +
                "     padding: 15px;\n" +
                "     font-family: Arial, Helvetica, sans-serif;\n" +
                "     font-size: 8pt;\n" +
                "     letter-spacing:0.1em;\n" +
                " }\n" +
                " .shroggleLogo img {\n" +
                "     border:0;\n" +
                "      vertical-align: middle;\n" +
                " }\n" +
                "\n" +
                "</style>\n" +
                "\n" +
                "<!-- PAGE_HEADER -->\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"mainDiv\">\n" +
                "    <table id=\"mainTbl\">\n" +
                "        <tr>\n" +
                "            <td id=\"topPane\" colspan=\"2\"><div class=\"topDiv mediaBlock\"></div></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td id=\"middlePaneLeft\"><div class=\"mainContent mediaBlock\"></div></td>\n" +
                "            <td id=\"middlePaneRight\"><div class=\"mainContent mediaBlock\"></div></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "        <td colspan=\"2\">\n" +
                "            <div class=\"shroggleLogo\"><p>Powered by <a href=\"http://www.shroggle.com/\"><img src=\"<!-- TEMPLATE_RESOURCE -->/wdfooter-logo-small.png\"></a>. Website creation tools for professionals</p></div>\n" +
                "        </td>\n" +
                "        </tr>\n" +
                "\n" +
                "    </table>\n" +
                "</div>"), listenerMock);

        Assert.assertTrue(listenerMock.getBlocks().isEmpty());
        Assert.assertEquals(3, listenerMock.getFlatBlocks().size());
    }

    @Test
    public void executeWithEmptyHtml() {
        processor.execute(new StringBuilder(), listenerMock);

        Assert.assertTrue(listenerMock.getBlocks().isEmpty());
        Assert.assertTrue(listenerMock.getFlatBlocks().isEmpty());
    }

    @Test
    public void executeWithNullHtml() {
        processor.execute(null, listenerMock);

        Assert.assertTrue(listenerMock.getBlocks().isEmpty());
        Assert.assertTrue(listenerMock.getFlatBlocks().isEmpty());
    }

    private final HtmlProcessor processor;
    private final HtmlListenerMock listenerMock = new HtmlListenerMock();

}