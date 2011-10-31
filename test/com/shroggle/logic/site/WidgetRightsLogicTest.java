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
package com.shroggle.logic.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.SiteType;
import com.shroggle.entity.Widget;
import com.shroggle.entity.WidgetItem;
import com.shroggle.logic.site.page.PageManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class WidgetRightsLogicTest {

    @Test
    public void isRequired() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        final Widget widget = new WidgetItem();
        pageVersion.addWidget(widget);

        Assert.assertFalse(new WidgetRightsManager(widget).isRequired());
    }

    @Test
    public void isRequiredForLockPageVersion() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        pageVersion.setBlueprintLocked(true);
        final Widget widget = new WidgetItem();
        pageVersion.addWidget(widget);

        Assert.assertFalse(new WidgetRightsManager(widget).isRequired());
    }

    @Test
    public void isRequiredForLockPageVersionForBlueprint() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        pageVersion.getPage().getSite().setType(SiteType.BLUEPRINT);
        pageVersion.setBlueprintLocked(true);
        final Widget widget = new WidgetItem();
        pageVersion.addWidget(widget);

        Assert.assertTrue(new WidgetRightsManager(widget).isRequired());
    }

    @Test
    public void isRequiredForLockWidget() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        final Widget widget = new WidgetItem();
        widget.setBlueprintRequired(true);
        pageVersion.addWidget(widget);

        Assert.assertFalse(new WidgetRightsManager(widget).isRequired());
    }

    @Test
    public void isRequiredForLockWidgetForBlueprint() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        pageVersion.getPage().getSite().setType(SiteType.BLUEPRINT);
        final Widget widget = new WidgetItem();
        widget.setBlueprintRequired(true);
        pageVersion.addWidget(widget);

        Assert.assertTrue(new WidgetRightsManager(widget).isRequired());
    }

    @Test
    public void isEditable() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        final Widget widget = new WidgetItem();
        pageVersion.addWidget(widget);

        Assert.assertTrue(new WidgetRightsManager(widget).isEditable());
    }

    @Test
    public void isEditableRuche() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        final Widget widget = new WidgetItem();
        widget.setBlueprintEditRuche(true);
        pageVersion.addWidget(widget);

        Assert.assertTrue(new WidgetRightsManager(widget).isEditableRuche());
    }

    @Test
    public void isEditableRucheForLockPageVersion() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        pageVersion.setBlueprintLocked(true);
        final Widget widget = new WidgetItem();
        pageVersion.addWidget(widget);

        Assert.assertTrue(new WidgetRightsManager(widget).isEditableRuche());
    }

    @Test
    public void isEditableRucheForLockPageVersionForBlueprint() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        pageVersion.getPage().getSite().setType(SiteType.BLUEPRINT);
        pageVersion.setBlueprintLocked(true);
        final Widget widget = new WidgetItem();
        pageVersion.addWidget(widget);

        Assert.assertFalse(new WidgetRightsManager(widget).isEditableRuche());
    }

    @Test
    public void isEditableRucheForLockWidget() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        final Widget widget = new WidgetItem();
        widget.setBlueprintEditRuche(false);
        pageVersion.addWidget(widget);

        Assert.assertTrue(new WidgetRightsManager(widget).isEditableRuche());
    }

    @Test
    public void isEditableRucheForLockWidgetForBlueprint() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        pageVersion.getPage().getSite().setType(SiteType.BLUEPRINT);
        final Widget widget = new WidgetItem();
        widget.setBlueprintEditRuche(false);
        pageVersion.addWidget(widget);

        Assert.assertFalse(new WidgetRightsManager(widget).isEditableRuche());
    }

    @Test
    public void isEditableForLockPageVersion() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        pageVersion.setBlueprintLocked(true);
        final Widget widget = new WidgetItem();
        pageVersion.addWidget(widget);
        
        Assert.assertTrue(new WidgetRightsManager(widget).isEditable());
    }

    @Test
    public void isEditableForLockWidget() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        final Widget widget = new WidgetItem();
        widget.setBlueprintEditable(false);
        pageVersion.addWidget(widget);

        Assert.assertTrue(new WidgetRightsManager(widget).isEditable());
    }

    @Test
    public void isEditableForLockPageVersionForBlueprint() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        pageVersion.getPage().getSite().setType(SiteType.BLUEPRINT);
        pageVersion.setBlueprintLocked(true);
        final Widget widget = new WidgetItem();
        pageVersion.addWidget(widget);

        Assert.assertFalse(new WidgetRightsManager(widget).isEditable());
    }

    @Test
    public void isEditableForLockWidgetForBlueprint() {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        pageVersion.getPage().getSite().setType(SiteType.BLUEPRINT);
        final Widget widget = new WidgetItem();
        widget.setBlueprintEditable(false);
        pageVersion.addWidget(widget);

        Assert.assertFalse(new WidgetRightsManager(widget).isEditable());
    }

}
