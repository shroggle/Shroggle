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
package com.shroggle.util.persistance.hibernate;

import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithHibernateService.class)
public class PageManagerWithHibernateTest {

    /*----------------------------------------------------Publish-----------------------------------------------------*/

    @Test
    public void testPublish_withoutOldWorkSettings() throws Exception {
        final Page page = TestUtil.createPageAndSite();
        PageManager pageManager = new PageManager(page);

        final Widget widget = new WidgetItem();
        ServiceLocator.getPersistance().putWidget(widget);
        pageManager.addWidget(widget);

        Assert.assertEquals(null, pageManager.getWorkPageSettings());
        Assert.assertEquals(false, pageManager.getDraftPageSettings().isChanged());


        pageManager.publish();


        Assert.assertNotNull(pageManager.getWorkPageSettings());
        Assert.assertEquals(false, pageManager.getDraftPageSettings().isChanged());
        Assert.assertEquals(1, pageManager.getWorkPageSettings().getWidgets().size());
        Assert.assertNotSame(widget.getWidgetId(), pageManager.getWorkPageSettings().getWidgets().get(0));
        Assert.assertNotNull(pageManager.getWorkPageSettings().getWidgets().get(0));
    }


    @Test
    public void testPublish_withOldWorkSettings() throws Exception {
        final Page page = TestUtil.createPage(TestUtil.createSite());

        final WorkPageSettings workPageSettings = new WorkPageSettings();
        workPageSettings.setPage(page);
        workPageSettings.setPageSettingsId(page.getPageSettings().getPageSettingsId());
        workPageSettings.setWidgets(Collections.<Widget>emptyList());
        ServiceLocator.getPersistance().putPageSettings(workPageSettings);
        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        ServiceLocator.getPersistance().putAccessibleSettings(accessibleSettings);
        workPageSettings.setAccessibleSettings(accessibleSettings);

        final Widget widget = new WidgetItem();
        ServiceLocator.getPersistance().putWidget(widget);

        PageManager pageManager = new PageManager(page);
        pageManager.addWidget(widget);
        pageManager.setChanged(true);
        HibernateManager.get().flush();

        Assert.assertEquals(true, pageManager.getDraftPageSettings().isChanged());



        pageManager.publish();
        HibernateManager.get().flush();

        
        Assert.assertNotNull(pageManager.getWorkPageSettings());
        Assert.assertEquals(false, pageManager.getDraftPageSettings().isChanged());
        Assert.assertEquals(1, pageManager.getWorkPageSettings().getWidgets().size());
        Assert.assertNotSame(widget.getWidgetId(), pageManager.getWorkPageSettings().getWidgets().get(0));
        Assert.assertNotNull(pageManager.getWorkPageSettings().getWidgets().get(0));
    }
    /*----------------------------------------------------Publish-----------------------------------------------------*/

    /*-------------------------------------------------Reset Changes--------------------------------------------------*/

    @Test
    public void testResetChanges_withoutOldWorkSettings() throws Exception {
        final Page page = TestUtil.createPageAndSite();
        PageManager pageManager = new PageManager(page);

        final Widget widget = new WidgetItem();
        ServiceLocator.getPersistance().putWidget(widget);
        pageManager.addWidget(widget);

        Assert.assertEquals(null, pageManager.getWorkPageSettings());
        Assert.assertEquals(false, pageManager.getDraftPageSettings().isChanged());
        HibernateManager.get().flush();


        pageManager.resetChanges();
        HibernateManager.get().flush();


        Assert.assertEquals(null, pageManager.getWorkPageSettings());
        Assert.assertEquals(false, pageManager.getDraftPageSettings().isChanged());
    }


    @Test
    public void testResetChanges_withOldWorkSettings() throws Exception {
        final Page page = TestUtil.createPage(TestUtil.createSite());
        page.getPageSettings().setWidgets(Collections.<Widget>emptyList());
        final Widget widget = new WidgetItem();
        ServiceLocator.getPersistance().putWidget(widget);


        final WorkPageSettings workPageSettings = new WorkPageSettings();
        workPageSettings.setPage(page);
        workPageSettings.setPageSettingsId(page.getPageSettings().getPageSettingsId());
        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        ServiceLocator.getPersistance().putAccessibleSettings(accessibleSettings);
        ServiceLocator.getPersistance().putPageSettings(workPageSettings);
        workPageSettings.addWidget(widget);
        workPageSettings.setAccessibleSettings(accessibleSettings);
        HibernateManager.get().flush();

        PageManager pageManager = new PageManager(page);
        pageManager.setChanged(true);


        pageManager.resetChanges();
        HibernateManager.get().flush();


        Assert.assertEquals(false, pageManager.getDraftPageSettings().isChanged());
        Assert.assertEquals(workPageSettings.getPageSettingsId(), pageManager.getDraftPageSettings().getPageSettingsId());
        Assert.assertEquals(1, pageManager.getDraftPageSettings().getWidgets().size());
        Assert.assertNotSame(widget.getWidgetId(), pageManager.getDraftPageSettings().getWidgets().get(0));
        Assert.assertNotNull(pageManager.getDraftPageSettings().getWidgets().get(0));
    }
    /*-------------------------------------------------Reset Changes--------------------------------------------------*/

    /*-----------------------------------------------------Copy-------------------------------------------------------*/
    @Test
    public void testCopy_withOldWorkSettings() throws Exception {
        final Page page = TestUtil.createPage(TestUtil.createSite());

        final WorkPageSettings workPageSettings = new WorkPageSettings();
        workPageSettings.setPage(page);
        workPageSettings.setPageSettingsId(page.getPageSettings().getPageSettingsId());
        workPageSettings.setWidgets(Collections.<Widget>emptyList());
        ServiceLocator.getPersistance().putPageSettings(workPageSettings);
        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        ServiceLocator.getPersistance().putAccessibleSettings(accessibleSettings);
        workPageSettings.setAccessibleSettings(accessibleSettings);

        final Widget widget = new WidgetItem();
        ServiceLocator.getPersistance().putWidget(widget);

        PageManager pageManager = new PageManager(page);
        pageManager.addWidget(widget);
        pageManager.setChanged(true);
        pageManager.setName("name");
        HibernateManager.get().flush();


        final PageManager copyPageManager = pageManager.copy(null);
        HibernateManager.get().flush();


        Assert.assertNotSame(pageManager.getPage(), copyPageManager.getPage());
        Assert.assertEquals("name1", copyPageManager.getName());
        Assert.assertNull(copyPageManager.getWorkPageSettings());
        Assert.assertNotNull(copyPageManager.getDraftPageSettings());
        Assert.assertEquals(1, copyPageManager.getWidgets().size());
        Assert.assertNotSame(widget, copyPageManager.getWidgets().get(0));
    }
    /*-----------------------------------------------------Copy-------------------------------------------------------*/
}
