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
package com.shroggle.logic.site.template;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMock;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class TemplatesLogicTest {

    @Test
    public void empty() {
        final Site site = TestUtil.createSite();
        final TemplatesLogic templatesLogic = new TemplatesLogic(site);
        Assert.assertEquals(0, templatesLogic.getItems().size());
        Assert.assertEquals(0, templatesLogic.getBlueprintItems().size());
    }

    @Test
    public void simpleSite() {
        Template template1 = new Template();
        template1.setName("Z");
        Template template2 = new Template();
        template2.setName("a");
        Template template3 = new Template();
        template3.setOrder(1);
        Template template4 = new Template();
        template4.setOrder(12);
        fileSystemMock.putTemplate(template1);
        fileSystemMock.putTemplate(template2);
        fileSystemMock.putTemplate(template3);
        fileSystemMock.putTemplate(template4);

        final Site site = TestUtil.createSite();
        final TemplatesLogic templatesLogic = new TemplatesLogic(site);
        final List<TemplateManager> items = templatesLogic.getItems();
        Assert.assertNotNull(items);
        Assert.assertEquals(4, items.size());
        Assert.assertEquals(template3, items.get(0).getTemplate());
        Assert.assertEquals(template4, items.get(1).getTemplate());
        Assert.assertEquals(template2, items.get(2).getTemplate());
        Assert.assertEquals(template1, items.get(3).getTemplate());
        Assert.assertTrue(templatesLogic.getBlueprintItems().isEmpty());
    }

    @Test
    public void ignoreOptima() {
        Template template1 = new Template();
        template1.setName("Z");
        template1.setDirectory("optima");
        Template template2 = new Template();
        template2.setName("a");
        fileSystemMock.putTemplate(template1);
        fileSystemMock.putTemplate(template2);

        final Site site = TestUtil.createSite();
        final TemplatesLogic templatesLogic = new TemplatesLogic(site);
        final List<TemplateManager> items = templatesLogic.getItems();
        Assert.assertNotNull(items);
        Assert.assertEquals(1, items.size());
        Assert.assertEquals(template2, items.get(0).getTemplate());
        Assert.assertTrue(templatesLogic.getBlueprintItems().isEmpty());
    }

    @Test
    public void siteWithChildSetting() {
        Template template1 = new Template();
        template1.setName("Z");
        template1.setDirectory("Z");
        fileSystemMock.putTemplate(template1);

        Template template2 = new Template();
        template2.setName("a");
        template2.setDirectory("a");
        fileSystemMock.putTemplate(template2);

        final Site blueprint1 = TestUtil.createBlueprint();
        blueprint1.setThemeId(new ThemeId("Z", "f"));
        final Site blueprint2 = TestUtil.createBlueprint();
        blueprint2.setThemeId(new ThemeId("a", "f"));
        final DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.getBlueprintsId().add(blueprint1.getSiteId());
        childSiteRegistration.getBlueprintsId().add(blueprint2.getSiteId());
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setChildSiteRegistration(childSiteRegistration);
        final Site site = TestUtil.createSite();
        site.setChildSiteSettings(childSiteSettings);
        final TemplatesLogic templatesLogic = new TemplatesLogic(site);
        Assert.assertEquals(2, templatesLogic.getItems().size());
        final List<TemplateManager> blueprintItems = templatesLogic.getBlueprintItems();
        Assert.assertEquals(2, blueprintItems.size());
        Assert.assertEquals(template2, blueprintItems.get(0).getTemplate());
        Assert.assertEquals(template1, blueprintItems.get(1).getTemplate());
        Assert.assertEquals((Integer) blueprint2.getSiteId(), blueprintItems.get(0).getBlueprintId());
        Assert.assertEquals((Integer) blueprint1.getSiteId(), blueprintItems.get(1).getBlueprintId());
    }

    @Test
    public void siteWithChildSettingWithNotFoundBlueprint() {
        final DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.getBlueprintsId().add(-1);
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setChildSiteRegistration(childSiteRegistration);
        final Site site = TestUtil.createSite();
        site.setChildSiteSettings(childSiteSettings);
        final TemplatesLogic templatesLogic = new TemplatesLogic(site);
        Assert.assertEquals(0, templatesLogic.getItems().size());
        Assert.assertEquals(0, templatesLogic.getBlueprintItems().size());
    }

    @Test
    public void siteWithChildSettingWithRequired() {
        Template template1 = new Template();
        template1.setName("Z");
        template1.setDirectory("Z");
        fileSystemMock.putTemplate(template1);

        final Site blueprint = TestUtil.createBlueprint();
        blueprint.setThemeId(new ThemeId("Z", "f"));
        final DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.getBlueprintsId().add(blueprint.getSiteId());
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setRequiredToUseSiteBlueprint(true);
        childSiteSettings.setChildSiteRegistration(childSiteRegistration);
        final Site site = TestUtil.createSite();
        site.setChildSiteSettings(childSiteSettings);
        final TemplatesLogic templatesLogic = new TemplatesLogic(site);
        Assert.assertEquals(0, templatesLogic.getItems().size());
        Assert.assertEquals(1, templatesLogic.getBlueprintItems().size());
        Assert.assertEquals(template1, templatesLogic.getBlueprintItems().get(0).getTemplate());
    }

    @Test
    public void siteWithChildSettingWithoutRegistration() {
        Template template1 = new Template();
        template1.setName("Z");
        template1.setDirectory("Z");
        fileSystemMock.putTemplate(template1);

        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        final Site site = TestUtil.createSite();
        site.setChildSiteSettings(childSiteSettings);
        final TemplatesLogic templatesLogic = new TemplatesLogic(site);
        Assert.assertEquals(1, templatesLogic.getItems().size());
        Assert.assertEquals(template1, templatesLogic.getItems().get(0).getTemplate());
        Assert.assertEquals(0, templatesLogic.getBlueprintItems().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void changeItems() {
        final Site site = TestUtil.createSite();
        final TemplatesLogic templatesLogic = new TemplatesLogic(site);
        templatesLogic.getItems().add(new TemplateManager(new Template()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void changeBlueprintItems() {
        final Site site = TestUtil.createSite();
        final TemplatesLogic templatesLogic = new TemplatesLogic(site);
        templatesLogic.getBlueprintItems().add(new TemplateManager(new Template()));
    }

    private final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();

}
