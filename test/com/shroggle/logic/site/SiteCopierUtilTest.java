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
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class SiteCopierUtilTest {

    @Test
    public void testCopyPage() throws Exception {
        final Page page = TestUtil.createPage(TestUtil.createSite());
        page.setScreenShotId(1234);

        final Page copy = SiteCopierUtil.copyPage(page);
        Assert.assertEquals(1234, copy.getScreenShotId().intValue());
    }

    @Test
    public void testCopyWithExcludeItemId() throws Exception {
        final Page page = TestUtil.createPage(TestUtil.createSite());

        final DraftText draftText = new DraftText();
        persistance.putItem(draftText);
        final WidgetItem widgetItem = new WidgetItem();
        widgetItem.setDraftItem(draftText);
        persistance.putWidget(widgetItem);

        PageManager pageManager = new PageManager(page);
        pageManager.addWidget(widgetItem);
        pageManager.setChanged(true);
        pageManager.setName("name");
        pageManager.setUrl("fff");
        pageManager.setOwnDomainName("aa");

        final PageManager copyPageManager = SiteCopierUtil.copyPageDraft(page, Collections.<Integer>emptySet());
        Assert.assertNotSame(pageManager.getPage(), copyPageManager.getPage());
        Assert.assertEquals("name1", copyPageManager.getName());
        Assert.assertEquals("fff1", copyPageManager.getDraftPageSettings().getUrl());
        Assert.assertNull(copyPageManager.getWorkPageSettings());
        Assert.assertNotNull(copyPageManager.getDraftPageSettings());
        Assert.assertEquals("", copyPageManager.getOwnDomainName());
        Assert.assertEquals(
                "If widget item not included in set we need ignore widget!", 
                0, copyPageManager.getWidgets().size());
    }

    @Test
    public void testCopyWithIncludeItemId() throws Exception {
        final Page page = TestUtil.createPage(TestUtil.createSite());

        final DraftText draftText = new DraftText();
        persistance.putItem(draftText);
        final WidgetItem widgetItem = new WidgetItem();
        widgetItem.setDraftItem(draftText);
        persistance.putWidget(widgetItem);

        PageManager pageManager = new PageManager(page);
        pageManager.addWidget(widgetItem);
        pageManager.setChanged(true);
        pageManager.setName("name");
        pageManager.setUrl("fff");
        pageManager.setOwnDomainName("aa");

        final Set<Integer> itemIds = new HashSet<Integer>();
        itemIds.add(draftText.getId());
        final PageManager copyPageManager = SiteCopierUtil.copyPageDraft(page, itemIds);
        Assert.assertNotSame(pageManager.getPage(), copyPageManager.getPage());
        Assert.assertEquals("name1", copyPageManager.getName());
        Assert.assertEquals("fff1", copyPageManager.getDraftPageSettings().getUrl());
        Assert.assertNull(copyPageManager.getWorkPageSettings());
        Assert.assertNotNull(copyPageManager.getDraftPageSettings());
        Assert.assertEquals("", copyPageManager.getOwnDomainName());
        Assert.assertEquals(
                "If widget item included in set we need copy widget!",
                1, copyPageManager.getWidgets().size());
    }

    @Test
    public void testCopy_withOldWorkSettings() throws Exception {
        final Page page = TestUtil.createPage(TestUtil.createSite());

        final WorkPageSettings workPageSettings = new WorkPageSettings();
        workPageSettings.setPage(page);
        workPageSettings.setPageSettingsId(page.getPageSettings().getPageSettingsId());
        workPageSettings.setWidgets(Collections.<Widget>emptyList());
        persistance.putPageSettings(workPageSettings);
        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        ServiceLocator.getPersistance().putAccessibleSettings(accessibleSettings);
        workPageSettings.setAccessibleSettings(accessibleSettings);

        final Widget widget = new WidgetItem();
        persistance.putWidget(widget);

        PageManager pageManager = new PageManager(page);
        pageManager.addWidget(widget);
        pageManager.setChanged(true);
        pageManager.setName("name");
        pageManager.setUrl("fff");
        pageManager.setOwnDomainName("aa");

        final PageManager copyPageManager = SiteCopierUtil.copyPageDraft(page, null);

        Assert.assertNotSame(pageManager.getPage(), copyPageManager.getPage());
        Assert.assertEquals("name1", copyPageManager.getName());
        Assert.assertEquals("fff1", copyPageManager.getDraftPageSettings().getUrl());
        Assert.assertNull(copyPageManager.getWorkPageSettings());
        Assert.assertNotNull(copyPageManager.getDraftPageSettings());
        Assert.assertEquals("", copyPageManager.getOwnDomainName());
        Assert.assertEquals(1, copyPageManager.getWidgets().size());
        Assert.assertNotSame(widget, copyPageManager.getWidgets().get(0));
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
