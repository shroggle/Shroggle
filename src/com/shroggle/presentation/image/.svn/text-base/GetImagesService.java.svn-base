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

import com.shroggle.entity.DraftImage;
import com.shroggle.entity.Image;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.resource.provider.ResourceGetterType;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Artem Stasuk, dmitry.solomadin
 */
@RemoteProxy
public class GetImagesService extends ServiceWithExecutePage {

    @RemoteMethod
    public String execute(final GetImagesRequest request) throws IOException, ServletException {
        new UsersManager().getLogined().getRight().getSiteRight().getSiteForView(request.getSiteId());

        final List<Image> images = persistance.getImagesByKeywords(request.getSiteId(), request.getKeywords());

        //We should explicitly add image that is selected inside image item because it won't be in images list
        //if image item was copied from other  site.
        if (request.getImageItemId() != null) {
            final DraftImage imageItem = persistance.getDraftItem(request.getImageItemId());

            if (imageItem != null && imageItem.getImageId() != null && imageItem.getImageId() != 0) {
                boolean imagesContainsSelectedImage = false;
                for (Image image : images) {
                    if (image.getImageId() == imageItem.getImageId()) {
                        imagesContainsSelectedImage = true;
                    }
                }

                if (!imagesContainsSelectedImage) {
                    Image image = persistance.getImageById(imageItem.getImageId());

                    if (image != null) {
                        images.add(image);
                    }
                }
            }
        }

        Collections.sort(images, new Comparator<Image>() {
            public int compare(final Image image1, final Image image2) {
                return image2.getCreated().compareTo(image1.getCreated());
            }
        });

        itemsLines = breakImagesIntoLines(images, request.getLineWidth());

        return executePage("/WEB-INF/pages/images/getImages.jsp");
    }

    protected List<List<GetImagesItem>> breakImagesIntoLines(final List<Image> images, final int lineWidth) {
        final List<List<GetImagesItem>> itemsLines = new ArrayList<List<GetImagesItem>>();

        int currentLineImagesWidth = 0;
        List<GetImagesItem> items = new ArrayList<GetImagesItem>();
        for (final Image image : images) {
            int resizedWidth;
            String url;
            final Integer thumbnailHeight;
            if (image.getHeight() > Image.THUMBNAIL_HEIGHT) {
                thumbnailHeight = Image.THUMBNAIL_HEIGHT;
                resizedWidth = image.getWidth() / (image.getHeight() / Image.THUMBNAIL_HEIGHT);
                url = ResourceGetterType.IMAGE_THUMBNAIL.getImageThumbnailUrl(image, 0, Image.THUMBNAIL_HEIGHT);
            } else {
                thumbnailHeight = image.getHeight();
                resizedWidth = (image.getWidth());
                url = ResourceGetterType.IMAGE.getImageUrl(image);
            }

            currentLineImagesWidth += getImageBlockWidth(resizedWidth);

            if (currentLineImagesWidth > lineWidth) {
                itemsLines.add(items);
                items = new ArrayList<GetImagesItem>();
                currentLineImagesWidth = getImageBlockWidth(resizedWidth);
            }

            items.add(new GetImagesItem(
                    image.getImageId(), image.getName(), image.getWidth(),
                    image.getHeight(), url, resizedWidth, thumbnailHeight,
                    ResourceGetterType.IMAGE.getImageUrl(image)));
        }

        itemsLines.add(items);

        return itemsLines;
    }

    protected int getImageBlockWidth(int imageWidth) {
        final int minimalImageBlockWidth = 110;
        final int paddingAndMarginsAroundImage = 16;
        return imageWidth + paddingAndMarginsAroundImage < minimalImageBlockWidth ?
                minimalImageBlockWidth : imageWidth + paddingAndMarginsAroundImage;
    }

    public List<List<GetImagesItem>> getItemsLines() {
        return itemsLines;
    }

    private List<List<GetImagesItem>> itemsLines;
    private final Persistance persistance = ServiceLocator.getPersistance();

}
