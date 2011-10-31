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

package com.shroggle.presentation.site;

import com.shroggle.*;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PagesWithoutSystem;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.filesystem.FileSystemMock;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author Stasuk Artem
 */
@Ignore
@RunWith(value = ParameterizedTestRunner.class)
@ParameterizedUsedRunner(value = ParameterizedTestRunnerWithServiceLocator.class)
public class QuicklyCreatePagesActionWithOneWidgetTest extends TestBaseWithMockService {

    private Site createStandartSite() {
        final Site site = TestUtil.createSite();
        site.setThemeId(new ThemeId("a", "test.html"));
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        blog.setName("BLOG2");
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        final FileSystemMock fileSystemMock = (FileSystemMock) fileSystem;
        final Template template = new Template();
        template.setDirectory("a");
        fileSystemMock.putTemplate(template);

        final Layout layout = new Layout();
        layout.setFile("test.html");
        layout.setTemplate(template);
        template.getLayouts().add(layout);

        fileSystemMock.addTemplateResource("a", "test.html", "<!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK -->");
        return site;
    }

    @Test
    public void execute() throws Exception {
        final Site site = createStandartSite();

        action.setPages(new ArrayList<PageType>());
        action.getPages().add(PageType.BLANK);
        action.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        final List<Page> pagesWithoutSystem = PagesWithoutSystem.get(site.getPages());
        Assert.assertEquals(1, pagesWithoutSystem.size());
        final Page page = pagesWithoutSystem.get(0);
        final PageManager pageVersion = new PageManager(page);
        Assert.assertEquals("a", pageVersion.getName());
        Assert.assertEquals(pageVersion.getTitle(), "a");
        Assert.assertEquals(3, pageVersion.getWidgets().size());

        final WidgetComposit widgetComposit = (WidgetComposit) pageVersion.getWidgets().get(1);
        Assert.assertEquals(true, widgetComposit.isWidgetComposit());
        Assert.assertEquals(1, widgetComposit.getChilds().size());

        Assert.assertEquals(SiteEditPageAction.class, resolutionMock.getRedirectByAction());
    }

    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private final QuicklyCreatePagesAction action = new QuicklyCreatePagesAction();

}