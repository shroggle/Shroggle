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

package com.shroggle.presentation.image;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.ImageItemNotFoundException;
import com.shroggle.exception.ImageNotSelectException;
import com.shroggle.exception.ImageSizeIncorrectException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemException;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class CreateImageServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test(expected = ImageNotSelectException.class)
    public void executeWithNotFoundImage() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        final DraftImage image1 = new DraftImage();
        image1.setSiteId(site.getSiteId());
        persistance.putItem(image1);
        widget.setDraftItem(image1);

        SavePrimaryImageRequest request = new SavePrimaryImageRequest();
        request.setWidth(200);
        request.setImageId(1);
        request.setImageItemId(image1.getId());
        request.setWidgetId(widget.getWidgetId());
        service.savePrimaryImageTab(request);
    }

    @Test
    public void executeImageFromSiteEditPage() throws FileSystemException, IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final Image image = new Image();
        image.setSourceExtension("png");
        persistance.putImage(image);
        fileSystem.setResource(image, new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR));

        final WidgetItem widgetImage = TestUtil.createWidgetItem();
        persistance.putWidget(widgetImage);
        pageVersion.addWidget(widgetImage);

        final DraftImage image1 = new DraftImage();
        image1.setSiteId(site.getSiteId());
        persistance.putItem(image1);
        widgetImage.setDraftItem(image1);

        SavePrimaryImageRequest request = new SavePrimaryImageRequest();
        request.setImageId(image.getImageId());
        request.setWidgetId(widgetImage.getWidgetId());
        request.setImageItemId(image1.getId());
        request.setWidth(21);
        request.setHeight(20);
        request.setMargin(10);
        request.setName("name");

        final FunctionalWidgetInfo response = service.savePrimaryImageTab(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(widgetImage.getWidgetId(), response.getWidget().getWidgetId());

        final DraftImage draftImage = (DraftImage)widgetImage.getDraftItem();

        Assert.assertEquals(20, draftImage.getThumbnailHeight().intValue());
        Assert.assertEquals(21, draftImage.getThumbnailWidth().intValue());
        Assert.assertEquals(10, draftImage.getMargin());
        Assert.assertEquals("name", draftImage.getName());
    }
    
    @Test
    public void executeImageFromManageItems() throws FileSystemException, IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final Image image = new Image();
        image.setSourceExtension("png");
        persistance.putImage(image);
        fileSystem.setResource(image, new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR));

        final DraftImage image1 = new DraftImage();
        image1.setSiteId(site.getSiteId());
        persistance.putItem(image1);

        SavePrimaryImageRequest request = new SavePrimaryImageRequest();
        request.setImageId(image.getImageId());
        request.setImageItemId(image1.getId());
        request.setWidth(21);
        request.setHeight(20);
        request.setMargin(10);
        request.setDescription("desc");

        final FunctionalWidgetInfo response = service.savePrimaryImageTab(request);

        Assert.assertNotNull(response);
        Assert.assertNull(response.getWidget());

        Assert.assertEquals(20, image1.getThumbnailHeight().intValue());
        Assert.assertEquals(21, image1.getThumbnailWidth().intValue());
        Assert.assertEquals(10, image1.getMargin());
        Assert.assertEquals("desc", image1.getDescription());
    }

    @Test
    public void executeRollOverImage() throws FileSystemException, IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final Image image = new Image();
        image.setSourceExtension("png");
        persistance.putImage(image);
        fileSystem.setResource(image, new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR));

        final WidgetItem widgetImage = TestUtil.createWidgetItem();
        persistance.putWidget(widgetImage);
        pageVersion.addWidget(widgetImage);

        final DraftImage image1 = new DraftImage();
        image1.setSiteId(site.getSiteId());
        persistance.putItem(image1);
        widgetImage.setDraftItem(image1);

        SaveRollOverImageRequest request = new SaveRollOverImageRequest();
        request.setRollOverImageId(image.getImageId());
        request.setWidgetId(widgetImage.getWidgetId());
        request.setImageItemId(image1.getId());
        request.setDescriptionOnMouseOver(true);
        request.setOnMouseOverText("setOnMouseOverText");

        service.saveRollOverImageTab(request);

        final DraftImage draftImage = (DraftImage)widgetImage.getDraftItem();

        Assert.assertEquals(image.getImageId(), draftImage.getRollOverImageId().intValue());
        Assert.assertEquals(true, draftImage.isShowDescriptionOnMouseOver());
        Assert.assertEquals("setOnMouseOverText", draftImage.getOnMouseOverText());
    }

    @Test
    public void executeLabalsLinksImage() throws FileSystemException, IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final Image image = new Image();
        image.setSourceExtension("png");
        persistance.putImage(image);
        fileSystem.setResource(image, new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR));

        final WidgetItem widgetImage = TestUtil.createWidgetItem();
        persistance.putWidget(widgetImage);
        pageVersion.addWidget(widgetImage);

        final DraftImage image1 = new DraftImage();
        image1.setSiteId(site.getSiteId());
        persistance.putItem(image1);
        widgetImage.setDraftItem(image1);

        SaveLabelLinksRequest request = new SaveLabelLinksRequest();
        request.setWidgetId(widgetImage.getWidgetId());
        request.setTitle("title");
        request.setTitlePosition(TitlePosition.BELOW);
        request.setImageItemId(image1.getId());
        request.setCustomizeWindowSize(true);

        service.saveLabelsLinksImageTab(request);

        final DraftImage draftImage = (DraftImage)widgetImage.getDraftItem();

        Assert.assertEquals("title", draftImage.getTitle());
        Assert.assertEquals(TitlePosition.BELOW, draftImage.getTitlePosition());
        Assert.assertEquals(true, draftImage.isCustomizeWindowSize());
    }

    @Test
    public void executeImageWithoutHeight() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Image image = new Image();
        image.setSourceExtension("png");
        persistance.putImage(image);
        fileSystem.setResource(image, new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR));

        WidgetItem widgetImage = TestUtil.createWidgetItem();
        persistance.putWidget(widgetImage);
        pageVersion.addWidget(widgetImage);

        final DraftImage image1 = new DraftImage();
        image1.setSiteId(site.getSiteId());
        persistance.putItem(image1);
        widgetImage.setDraftItem(image1);

        SavePrimaryImageRequest request = new SavePrimaryImageRequest();
        request.setImageId(image.getImageId());
        request.setWidth(20);
        request.setImageItemId(image1.getId());
        request.setWidgetId(widgetImage.getWidgetId());
        service.savePrimaryImageTab(request);
    }

    @Test
    public void executeImageWithoutWidth() throws FileSystemException, IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Image image = new Image();
        image.setSourceExtension("png");
        persistance.putImage(image);
        fileSystem.setResource(image, new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR));

        WidgetItem widgetImage = TestUtil.createWidgetItem();
        persistance.putWidget(widgetImage);
        pageVersion.addWidget(widgetImage);

        final DraftImage image1 = new DraftImage();
        image1.setSiteId(site.getSiteId());
        persistance.putItem(image1);
        widgetImage.setDraftItem(image1);

        SavePrimaryImageRequest request = new SavePrimaryImageRequest();
        request.setImageItemId(image1.getId());
        request.setImageId(image.getImageId());
        request.setHeight(20);
        request.setWidgetId(widgetImage.getWidgetId());
        service.savePrimaryImageTab(request);
    }

    @Test(expected = ImageSizeIncorrectException.class)
    public void executeImageWithoutSize() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Image image = new Image();
        image.setSourceExtension("png");
        persistance.putImage(image);
        fileSystem.setResource(image, new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR));

        WidgetItem widgetImage = TestUtil.createWidgetItem();
        persistance.putWidget(widgetImage);
        pageVersion.addWidget(widgetImage);

        final DraftImage image1 = new DraftImage();
        image1.setSiteId(site.getSiteId());
        persistance.putItem(image1);
        widgetImage.setDraftItem(image1);

        SavePrimaryImageRequest request = new SavePrimaryImageRequest();
        request.setImageId(image.getImageId());
        request.setImageItemId(image1.getId());
        request.setWidgetId(widgetImage.getWidgetId());
        service.savePrimaryImageTab(request);
    }

    @Test(expected = ImageItemNotFoundException.class)
    public void executeWithoutImageItem() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftImage image1 = new DraftImage();
        image1.setSiteId(site.getSiteId());
        persistance.putItem(image1);

        SavePrimaryImageRequest request = new SavePrimaryImageRequest();
        request.setImageItemId(-1);
        service.savePrimaryImageTab(request);
    }

    @Test(expected = ImageNotSelectException.class)
    public void executeWithoutAllImage() throws FileSystemException, ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        final DraftImage image1 = new DraftImage();
        image1.setSiteId(site.getSiteId());
        persistance.putItem(image1);
        widget.setDraftItem(image1);

        SavePrimaryImageRequest request = new SavePrimaryImageRequest();
        request.setImageItemId(image1.getId());
        request.setWidth(200);
        request.setWidgetId(widget.getWidgetId());
        service.savePrimaryImageTab(request);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin1() throws FileSystemException, ServletException, IOException {
        service.savePrimaryImageTab(null);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin2() throws FileSystemException, ServletException, IOException {
        service.saveLabelsLinksImageTab(null);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin3() throws FileSystemException, ServletException, IOException {
        service.saveRollOverImageTab(null);
    }

    private final FileSystemMock fileSystem = (FileSystemMock) ServiceLocator.getFileSystem();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final CreateImageService service = new CreateImageService();

}