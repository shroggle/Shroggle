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
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.resource.provider.ResourceGetterType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class GetImagesServiceTest {

    @Test
    public void execute() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        TestUtil.loginUser(user);
        final Site site2 = TestUtil.createSite();

        final Date currentDate = new Date();

        final Image image1 = TestUtil.createImage(site.getSiteId(), "name", "jpeg");
        image1.setCreated(currentDate);
        image1.setWidth(100);
        image1.setHeight(100);
        final Image image2 = TestUtil.createImage(site.getSiteId(), "name", "jpeg");
        image2.setCreated(new Date(currentDate.getTime() + 10000000000L));
        image2.setWidth(100);
        image2.setHeight(200);

        final Image imageFromOtherSite = TestUtil.createImage(site2.getSiteId(), "name", "jpeg");
        imageFromOtherSite.setCreated(new Date(currentDate.getTime() + 20000000000L));
        imageFromOtherSite.setWidth(100);
        imageFromOtherSite.setHeight(300);

        final DraftImage draftImage = TestUtil.createDraftImage(site);
        draftImage.setImageId(imageFromOtherSite.getImageId());

        GetImagesRequest request = new GetImagesRequest();
        request.setSiteId(site.getSiteId());
        request.setImageItemId(draftImage.getId());
        request.setLineWidth(250);
        service.execute(request);

        Assert.assertEquals(2, service.getItemsLines().size()); // Total three lines.

        // First line
        Assert.assertEquals(2, service.getItemsLines().get(0).size());
        Assert.assertEquals(imageFromOtherSite.getImageId(), (int) service.getItemsLines().get(0).get(0).getImageId());
        Assert.assertEquals(image2.getImageId(), (int) service.getItemsLines().get(0).get(1).getImageId());
        Assert.assertEquals("/thumbnails/0x120/3-name.jpeg", service.getItemsLines().get(0).get(0).getThumbnailUrl());
        Assert.assertEquals("/thumbnails/0x120/2-name.jpeg", service.getItemsLines().get(0).get(1).getThumbnailUrl());
        Assert.assertEquals(imageFromOtherSite.getHeight(), service.getItemsLines().get(0).get(0).getHeight());
        Assert.assertEquals(imageFromOtherSite.getWidth(), service.getItemsLines().get(0).get(0).getWidth());
        Assert.assertEquals(image2.getHeight(), service.getItemsLines().get(0).get(1).getHeight());
        Assert.assertEquals(image2.getWidth(), service.getItemsLines().get(0).get(1).getWidth());

        // Second line
        Assert.assertEquals(1, service.getItemsLines().get(1).size()); // two images on first line.
        Assert.assertEquals(image1.getImageId(), (int) service.getItemsLines().get(1).get(0).getImageId());
        Assert.assertEquals("/images1/1-name.jpeg", service.getItemsLines().get(1).get(0).getUrl());
        Assert.assertEquals(image1.getHeight(), service.getItemsLines().get(1).get(0).getHeight());
        Assert.assertEquals(image1.getWidth(), service.getItemsLines().get(1).get(0).getWidth());
    }

    @Test
    public void breakImagesIntoLines() throws InterruptedException {
        final Site site = TestUtil.createSite();

        final Date currentDate = new Date();

        // These two images would be on first line because of margins
        final Image image1 = TestUtil.createImage(site.getSiteId(), "name", "jpeg");
        image1.setCreated(currentDate);
        image1.setWidth(100);
        image1.setHeight(100);

        final Image image2 = TestUtil.createImage(site.getSiteId(), "name", "jpeg");
        image2.setCreated(new Date(currentDate.getTime() + 10000000000L));
        image2.setWidth(100);
        image2.setHeight(200);

        // These two on second line becase of minimal image block width.
        final Image image3 = TestUtil.createImage(site.getSiteId(), "name", "jpeg");
        image3.setCreated(new Date(currentDate.getTime() + 20000000000L));
        image3.setWidth(100);
        image3.setHeight(300);

        final Image image4 = TestUtil.createImage(site.getSiteId(), "name", "jpeg");
        image4.setCreated(new Date(currentDate.getTime() + 30000000000L));
        image4.setWidth(100);
        image4.setHeight(300);

        // The last image would be on last, third line.
        final Image image5 = TestUtil.createImage(site.getSiteId(), "name", "jpeg");
        image5.setCreated(new Date(currentDate.getTime() + 40000000000L));
        image5.setWidth(100);
        image5.setHeight(300);

        final List<Image> imagesFromBase = persistance.getImagesByOwnerSiteId(site.getSiteId());
        Collections.sort(imagesFromBase, new Comparator<Image>() {
            public int compare(final Image image1, final Image image2) {
                return image2.getCreated().compareTo(image1.getCreated());
            }
        });

        Collections.reverse(imagesFromBase);

        List<List<GetImagesItem>> images = service.breakImagesIntoLines(imagesFromBase, 250);

        Assert.assertEquals(3, images.size()); // Total three lines.

        // First line
        Assert.assertEquals(2, images.get(0).size()); // two images on first line.
        Assert.assertEquals(image1.getImageId(), (int) images.get(0).get(0).getImageId());
        Assert.assertEquals(image2.getImageId(), (int) images.get(0).get(1).getImageId());
        Assert.assertEquals("/images1/1-name.jpeg", images.get(0).get(0).getUrl());
        Assert.assertEquals("/thumbnails/0x120/2-name.jpeg", images.get(0).get(1).getThumbnailUrl());
        Assert.assertEquals(image1.getHeight(), images.get(0).get(0).getHeight());
        Assert.assertEquals(image1.getWidth(), images.get(0).get(0).getWidth());
        Assert.assertEquals(image2.getHeight(), images.get(0).get(1).getHeight());
        Assert.assertEquals(image2.getWidth(), images.get(0).get(1).getWidth());

        // Second line
        Assert.assertEquals(2, images.get(1).size()); // two images on first line.
        Assert.assertEquals(image3.getImageId(), (int) images.get(1).get(0).getImageId());
        Assert.assertEquals(image4.getImageId(), (int) images.get(1).get(1).getImageId());
        Assert.assertEquals("/thumbnails/0x120/3-name.jpeg", images.get(1).get(0).getThumbnailUrl());
        Assert.assertEquals("/thumbnails/0x120/4-name.jpeg", images.get(1).get(1).getThumbnailUrl());
        Assert.assertEquals(image3.getHeight(), images.get(1).get(0).getHeight());
        Assert.assertEquals(image3.getWidth(), images.get(1).get(0).getWidth());
        Assert.assertEquals(image4.getHeight(), images.get(1).get(1).getHeight());
        Assert.assertEquals(image4.getWidth(), images.get(1).get(1).getWidth());

        // Third line
        Assert.assertEquals(1, images.get(2).size()); // two images on first line.
        Assert.assertEquals(image5.getImageId(), (int) images.get(2).get(0).getImageId());
        Assert.assertEquals("/thumbnails/0x120/5-name.jpeg", images.get(2).get(0).getThumbnailUrl());
        Assert.assertEquals(image5.getHeight(), images.get(2).get(0).getHeight());
        Assert.assertEquals(image5.getWidth(), images.get(2).get(0).getWidth());
    }

    private Persistance persistance = ServiceLocator.getPersistance();
    private GetImagesService service = new GetImagesService();

}
