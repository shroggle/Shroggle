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
package com.shroggle.logic.widget;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.FontsAndColors;
import com.shroggle.entity.FontsAndColorsValue;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class CssParametersLinkSorterTest {

    @Test
    public void testExecute() {
        final FontsAndColors fontsAndColors = TestUtil.createFontsAndColors();
        TestUtil.createFontsAndColorsValue(fontsAndColors, "a:hover");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp1");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "a:active");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp2");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "a:link");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp3");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "a:visited");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp4");

        Assert.assertEquals(8, fontsAndColors.getCssValues().size());
        Assert.assertEquals("a:hover", fontsAndColors.getCssValues().get(0).getSelector());
        Assert.assertEquals("a:active", fontsAndColors.getCssValues().get(2).getSelector());
        Assert.assertEquals("a:link", fontsAndColors.getCssValues().get(4).getSelector());
        Assert.assertEquals("a:visited", fontsAndColors.getCssValues().get(6).getSelector());

        final List<FontsAndColorsValue> newSortedValues = CssParametersLinkSorter.execute(fontsAndColors.getCssValues());

        Assert.assertEquals(8, newSortedValues.size());
        Assert.assertEquals("a:link", newSortedValues.get(4).getSelector());
        Assert.assertEquals("a:visited", newSortedValues.get(5).getSelector());
        Assert.assertEquals("a:active", newSortedValues.get(6).getSelector());
        Assert.assertEquals("a:hover", newSortedValues.get(7).getSelector());
    }

    @Test
    public void testExecute_withoutATagWithClassNames() {
        final FontsAndColors fontsAndColors = TestUtil.createFontsAndColors();
        TestUtil.createFontsAndColorsValue(fontsAndColors, ".manageVotesRecordNameLink:hover");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp1");
        TestUtil.createFontsAndColorsValue(fontsAndColors, ".manageVotesRecordNameLink:active");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp2");
        TestUtil.createFontsAndColorsValue(fontsAndColors, ".manageVotesRecordNameLink:link");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp3");
        TestUtil.createFontsAndColorsValue(fontsAndColors, ".manageVotesRecordNameLink:visited");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp4");

        Assert.assertEquals(8, fontsAndColors.getCssValues().size());
        Assert.assertEquals(".manageVotesRecordNameLink:hover", fontsAndColors.getCssValues().get(0).getSelector());
        Assert.assertEquals(".manageVotesRecordNameLink:active", fontsAndColors.getCssValues().get(2).getSelector());
        Assert.assertEquals(".manageVotesRecordNameLink:link", fontsAndColors.getCssValues().get(4).getSelector());
        Assert.assertEquals(".manageVotesRecordNameLink:visited", fontsAndColors.getCssValues().get(6).getSelector());

        final List<FontsAndColorsValue> newSortedValues = CssParametersLinkSorter.execute(fontsAndColors.getCssValues());

        Assert.assertEquals(8, newSortedValues.size());
        Assert.assertEquals(".manageVotesRecordNameLink:link", newSortedValues.get(4).getSelector());
        Assert.assertEquals(".manageVotesRecordNameLink:visited", newSortedValues.get(5).getSelector());
        Assert.assertEquals(".manageVotesRecordNameLink:active", newSortedValues.get(6).getSelector());
        Assert.assertEquals(".manageVotesRecordNameLink:hover", newSortedValues.get(7).getSelector());
    }

    @Test
    public void testExecute_withoutATagWithLongClassNames() {
        final FontsAndColors fontsAndColors = TestUtil.createFontsAndColors();
        TestUtil.createFontsAndColorsValue(fontsAndColors, ".addGalleryCommentLink:hover, .hideGalleryCommentsLink:hover, .viewGalleryCommentsLink:hover");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp1");
        TestUtil.createFontsAndColorsValue(fontsAndColors, ".addGalleryCommentLink:active, .hideGalleryCommentsLink:active, .viewGalleryCommentsLink:active");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp2");
        TestUtil.createFontsAndColorsValue(fontsAndColors, ".addGalleryCommentLink:link, .hideGalleryCommentsLink:link, .viewGalleryCommentsLink:link");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp3");
        TestUtil.createFontsAndColorsValue(fontsAndColors, ".addGalleryCommentLink:visited, .hideGalleryCommentsLink:visited, .viewGalleryCommentsLink:visited");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp4");
        TestUtil.createFontsAndColorsValue(fontsAndColors, ".manageVotesRecordNameLink:hover");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp5");
        TestUtil.createFontsAndColorsValue(fontsAndColors, ".manageVotesRecordNameLink:active");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp6");
        TestUtil.createFontsAndColorsValue(fontsAndColors, ".manageVotesRecordNameLink:link");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp7");
        TestUtil.createFontsAndColorsValue(fontsAndColors, ".manageVotesRecordNameLink:visited");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp8");


        Assert.assertEquals(16, fontsAndColors.getCssValues().size());
        Assert.assertEquals(".addGalleryCommentLink:hover, .hideGalleryCommentsLink:hover, .viewGalleryCommentsLink:hover", fontsAndColors.getCssValues().get(0).getSelector());
        Assert.assertEquals(".addGalleryCommentLink:active, .hideGalleryCommentsLink:active, .viewGalleryCommentsLink:active", fontsAndColors.getCssValues().get(2).getSelector());
        Assert.assertEquals(".addGalleryCommentLink:link, .hideGalleryCommentsLink:link, .viewGalleryCommentsLink:link", fontsAndColors.getCssValues().get(4).getSelector());
        Assert.assertEquals(".addGalleryCommentLink:visited, .hideGalleryCommentsLink:visited, .viewGalleryCommentsLink:visited", fontsAndColors.getCssValues().get(6).getSelector());
                             
        final List<FontsAndColorsValue> newSortedValues = CssParametersLinkSorter.execute(fontsAndColors.getCssValues());

        Assert.assertEquals(16, newSortedValues.size());
        Assert.assertEquals(".addGalleryCommentLink:link, .hideGalleryCommentsLink:link, .viewGalleryCommentsLink:link", newSortedValues.get(8).getSelector());
        Assert.assertEquals(".manageVotesRecordNameLink:link", newSortedValues.get(9).getSelector());
        Assert.assertEquals(".addGalleryCommentLink:visited, .hideGalleryCommentsLink:visited, .viewGalleryCommentsLink:visited", newSortedValues.get(10).getSelector());
        Assert.assertEquals(".manageVotesRecordNameLink:visited", newSortedValues.get(11).getSelector());
        Assert.assertEquals(".addGalleryCommentLink:active, .hideGalleryCommentsLink:active, .viewGalleryCommentsLink:active", newSortedValues.get(12).getSelector());
        Assert.assertEquals(".manageVotesRecordNameLink:active", newSortedValues.get(13).getSelector());
        Assert.assertEquals(".addGalleryCommentLink:hover, .hideGalleryCommentsLink:hover, .viewGalleryCommentsLink:hover", newSortedValues.get(14).getSelector());
        Assert.assertEquals(".manageVotesRecordNameLink:hover", newSortedValues.get(15).getSelector());
    }

    @Test
    public void testExecute_withoutATagWithLongClassNames_withTwoLinksClasses() {
        final FontsAndColors fontsAndColors = TestUtil.createFontsAndColors();
        TestUtil.createFontsAndColorsValue(fontsAndColors, ".addGalleryCommentLink:hover, .hideGalleryCommentsLink:hover, .viewGalleryCommentsLink:hover");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp1");
        TestUtil.createFontsAndColorsValue(fontsAndColors, ".addGalleryCommentLink:active, .hideGalleryCommentsLink:active, .viewGalleryCommentsLink:active");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp2");
        TestUtil.createFontsAndColorsValue(fontsAndColors, ".addGalleryCommentLink:link, .hideGalleryCommentsLink:link, .viewGalleryCommentsLink:link");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp3");
        TestUtil.createFontsAndColorsValue(fontsAndColors, ".addGalleryCommentLink:visited, .hideGalleryCommentsLink:visited, .viewGalleryCommentsLink:visited");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp4");

        Assert.assertEquals(8, fontsAndColors.getCssValues().size());
        Assert.assertEquals(".addGalleryCommentLink:hover, .hideGalleryCommentsLink:hover, .viewGalleryCommentsLink:hover", fontsAndColors.getCssValues().get(0).getSelector());
        Assert.assertEquals(".addGalleryCommentLink:active, .hideGalleryCommentsLink:active, .viewGalleryCommentsLink:active", fontsAndColors.getCssValues().get(2).getSelector());
        Assert.assertEquals(".addGalleryCommentLink:link, .hideGalleryCommentsLink:link, .viewGalleryCommentsLink:link", fontsAndColors.getCssValues().get(4).getSelector());
        Assert.assertEquals(".addGalleryCommentLink:visited, .hideGalleryCommentsLink:visited, .viewGalleryCommentsLink:visited", fontsAndColors.getCssValues().get(6).getSelector());

        final List<FontsAndColorsValue> newSortedValues = CssParametersLinkSorter.execute(fontsAndColors.getCssValues());

        Assert.assertEquals(8, newSortedValues.size());
        Assert.assertEquals(".addGalleryCommentLink:link, .hideGalleryCommentsLink:link, .viewGalleryCommentsLink:link", newSortedValues.get(4).getSelector());
        Assert.assertEquals(".addGalleryCommentLink:visited, .hideGalleryCommentsLink:visited, .viewGalleryCommentsLink:visited", newSortedValues.get(5).getSelector());
        Assert.assertEquals(".addGalleryCommentLink:active, .hideGalleryCommentsLink:active, .viewGalleryCommentsLink:active", newSortedValues.get(6).getSelector());
        Assert.assertEquals(".addGalleryCommentLink:hover, .hideGalleryCommentsLink:hover, .viewGalleryCommentsLink:hover", newSortedValues.get(7).getSelector());
    }

    @Test
    public void testExecuteNotAll() {
        final FontsAndColors fontsAndColors = TestUtil.createFontsAndColors();
        TestUtil.createFontsAndColorsValue(fontsAndColors, "a:visited");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "temp3");
        TestUtil.createFontsAndColorsValue(fontsAndColors, "a:hover");

        Assert.assertEquals(3, fontsAndColors.getCssValues().size());
        Assert.assertEquals("a:visited", fontsAndColors.getCssValues().get(0).getSelector());
        Assert.assertEquals("a:hover", fontsAndColors.getCssValues().get(2).getSelector());

        final List<FontsAndColorsValue> newSortedValues = CssParametersLinkSorter.execute(fontsAndColors.getCssValues());

        Assert.assertEquals(3, newSortedValues.size());
        Assert.assertEquals("a:visited", newSortedValues.get(1).getSelector());
        Assert.assertEquals("a:hover", newSortedValues.get(2).getSelector());
    }

}
