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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.*;
import com.shroggle.presentation.site.page.SavePageRequest;
import com.shroggle.presentation.site.page.SavePageResponse;
import com.shroggle.presentation.site.page.SavePageService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMock;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.HashSet;

/**
 * @author Stasuk Artem, dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class AddPageServiceTest {

    final SavePageService service = new SavePageService();

    @Test
    public void testExecute() {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setLayoutFile("b");

        final Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        final SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("urlP");
        request.setPageToEditId(page.getPageId());
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());

        final SavePageResponse response = service.savePageNameTab(request);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getPageSelectHtml());
        Assert.assertNotNull(response.getTreeHtml());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteWithoutPage() {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        site.setThemeId(new ThemeId("a", "b"));

        final Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        final SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("urlP");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());

        final SavePageResponse response = service.savePageNameTab(request);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getPageSelectHtml());
        Assert.assertNotNull(response.getTreeHtml());
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecuteWithNotLoginedUser() {
        service.savePageNameTab(new SavePageRequest());
    }

}