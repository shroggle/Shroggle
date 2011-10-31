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

package com.shroggle.presentation.video;

import com.shroggle.entity.*;
import com.shroggle.exception.VideoNotFoundException;
import com.shroggle.logic.site.item.ConfigureItemData;
import com.shroggle.logic.site.item.ItemsManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.logic.video.VideoDataManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.image.ImageData;
import com.shroggle.presentation.site.WithWidgetTitle;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemException;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import com.shroggle.util.resource.ResourceGetterUrl;
import com.shroggle.util.resource.provider.ResourceGetterType;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

/**
 * @author Balakirev Anatoliy, dmitry.solomadin
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class ConfigureVideoService extends AbstractService implements WithWidgetTitle {

    @SynchronizeByMethodParameter(
            entityClass = Widget.class)
    @RemoteMethod
    public void execute(final Integer widgetId, final Integer videoItemId) throws IOException, ServletException, FileSystemException {
        new UsersManager().getLogined();

        final ConfigureItemData<DraftVideo> itemData =
                new ItemsManager().getConfigureItemData(widgetId, videoItemId);

        widgetVideo = itemData.getWidget();
        draftVideo = itemData.getDraftItem();
        widgetTitle = widgetVideo != null ? new WidgetTitleGetter(widgetVideo) : null;

        siteId = itemData.getSite().getSiteId();
        if (draftVideo.getFlvVideoId() != null) {
            FlvVideo tempFlvVideo = persistance.getFlvVideo(draftVideo.getFlvVideoId());
            selectedVideo = persistance.getVideoById(tempFlvVideo.getSourceVideoId());
        }
        saveRatio = draftVideo.isSaveRatio();

        if (draftVideo.getImageId() != null) {
            width = draftVideo.getImageWidth();
            height = draftVideo.getImageHeight();
            ratio = createRatio(width, height);
            selectedImageId = draftVideo.getImageId();
        }

        videos = VideoDataManager.createVideoDatas(siteId, selectedVideo != null ? selectedVideo.getVideoId() : null);
        createVideoImages(siteId, draftVideo.getImageId());

        getContext().getHttpServletRequest().setAttribute("videoService", this);
    }

    @RemoteMethod
    public String showVideoSelect(final Integer siteId, final Integer videoId) throws IOException, ServletException {
        videos = VideoDataManager.createVideoDatas(siteId, videoId);

        getContext().getHttpServletRequest().setAttribute("videoService", this);
        return getContext().forwardToString("/video/uploadedVideoSelect.jsp");
    }

    @RemoteMethod
    public String showVideoImages(final Integer siteId, final int selectedVideoId) throws IOException, ServletException {
        final DraftVideo video = persistance.getDraftItem(selectedVideoId);

        if (video == null) {
            throw new VideoNotFoundException("Cannot find video by Id=" + selectedVideoId);
        }

        createVideoImages(siteId, video.getImageId());

        getContext().getHttpServletRequest().setAttribute("videoService", this);
        return getContext().forwardToString("/video/uploadedVideoImages.jsp");
    }

    // selectedImageId is the static image that selected video item contains. We are adding it explicitly
    // to ensure that we will not have any problems with video items inserted from other site.
    private void createVideoImages(final Integer siteId, final Integer selectedImageId) {
        List<ImageForVideo> images = ServiceLocator.getPersistance().getImagesForVideoBySiteId(siteId);

        if (selectedImageId != null && selectedImageId != 0) {
            boolean imagesFromSiteContainsSelectedImage = false;
            for (final ImageForVideo image : images) {
                if (image.getImageForVideoId() == selectedImageId) {
                    imagesFromSiteContainsSelectedImage = true;
                }
            }

            if (!imagesFromSiteContainsSelectedImage) {
                images.add(ServiceLocator.getPersistance().getImageForVideoById(selectedImageId));
            }
        }

        Collections.sort(images, new Comparator<ImageForVideo>() {
            public int compare(final ImageForVideo i1, final ImageForVideo i2) {
                Date i1Date = i1.getCreated();
                Date i2Date = i2.getCreated();
                return i2Date.compareTo(i1Date);
            }
        });
        for (final ImageForVideo image : images) {
            if (imageForVideoExist(image)) {
                final String imageUrl;
                final Integer thumbnailHeight;
                if (image.getHeight() <= ImageForVideo.THUMBNAIL_HEIGHT) {
                    thumbnailHeight = image.getHeight();
                    imageUrl = resourceGetterUrl.get(ResourceGetterType.IMAGE_FOR_VIDEO, image.getImageForVideoId(), 0, 0, 0, false);
                } else {
                    thumbnailHeight = ImageForVideo.THUMBNAIL_HEIGHT;
                    imageUrl = resourceGetterUrl.get(ResourceGetterType.IMAGE_FOR_VIDEO_THUMBNAIL, image.getImageForVideoId(), 0, 0, 0, false);
                }
                ImageData imageData = new ImageData(image.getImageForVideoId(), image.getTitle(), image.getWidth(), image.getHeight(), imageUrl, null, thumbnailHeight);
                imageThumbnailsDatas.add(imageData);
            }
        }
    }

    private boolean imageForVideoExist(final ImageForVideo image) {
        return !(image == null || image.getHeight() == 0 || image.getHeight() == 0) &&
                ServiceLocator.getFileSystem().isResourceExist(image);
    }

    private int createRatio(final Integer width, final Integer height) {
        int ratio = 0;
        if (width != null && height != null) {
            if (width < height) {
                ratio = width / height;
            } else {
                ratio = height / width;
            }
        }
        return ratio;
    }


    public Integer getSiteId() {
        return siteId;
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public WidgetItem getWidgetVideo() {
        return widgetVideo;
    }

    public Video getSelectedVideo() {
        return selectedVideo;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getRatio() {
        return ratio;
    }

    public boolean isSaveRatio() {
        return saveRatio;
    }

    public List<VideoData> getVideos() {
        return videos;
    }

    public List<ImageData> getImageThumbnailsWithPaths() {
        return imageThumbnailsDatas;
    }

    public Integer getSelectedImageId() {
        return selectedImageId;
    }

    public DraftVideo getDraftVideo() {
        return draftVideo;
    }

    private List<VideoData> videos = new ArrayList<VideoData>();
    private Video selectedVideo;
    private WidgetItem widgetVideo;
    private Integer siteId;
    private WidgetTitle widgetTitle;
    private Integer width;
    private Integer height;
    private Integer ratio;
    private Integer selectedImageId;
    private DraftVideo draftVideo;
    private boolean saveRatio = true;
    private Persistance persistance = ServiceLocator.getPersistance();
    private final ResourceGetterUrl resourceGetterUrl = ServiceLocator.getResourceGetter();
    private final List<ImageData> imageThumbnailsDatas = new ArrayList<ImageData>();
}