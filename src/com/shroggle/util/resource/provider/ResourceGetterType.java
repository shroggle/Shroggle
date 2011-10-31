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

package com.shroggle.util.resource.provider;

import com.shroggle.entity.*;
import com.shroggle.util.MD5;
import com.shroggle.util.NowTime;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigResourcesVideo;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.resource.ResourceGetter;
import com.shroggle.util.resource.ResourceGetterBySizeId;
import com.shroggle.util.resource.ResourceGetterDirect;

/**
 * Present resources. Helped get provider for getter resource as input stream.
 * It's not equals to ResourceType because resourceType define different resource
 * type for store as FORM_FILE, IMAGE, but ResourceGetterType define different access for
 * resource example as FORM_FILE over FORM or FORM_FILE over GALLERY. This need for denied use
 * need size in image url, because it's allow use DOS attack to our server, for example
 * http://server/resourceGetter.action?resourceId=x&resourceWidth=1&resourceHeight=10000
 * with many different sizes.
 *
 * @author Stasuk Artem
 * @see com.shroggle.entity.ResourceType
 */
public enum ResourceGetterType {

    IMAGE(Image.class, new ResourceGetterBySizeId(new ImageProvider())),
    BLUEPRINT_SCREEN_SHOT(Image.class, new ResourceGetterBySizeId(new BlueprintScreenShotProvider())),
    ICON(Icon.class, new ResourceGetterBySizeId(new IconProvider())),
    LOGO(Image.class, new ResourceGetterBySizeId(new LogoProvider())),
    FOOTER_IMAGE(Image.class, new ResourceGetterBySizeId(new FooterImageProvider())),
    IMAGE_THUMBNAIL(Image.class, new ResourceGetterBySizeId(new ImageThumbnailProvider())),
    BACKGROUND_IMAGE(BackgroundImage.class, new ResourceGetterBySizeId(new BackgroundImageProvider())),
    BACKGROUND_IMAGE_THUMBNAIL(BackgroundImage.class, new ResourceGetterBySizeId(new BackgroundImageThumbnailProvider())),
    IMAGE_FOR_VIDEO(ImageForVideo.class, new ResourceGetterBySizeId(new ImageForVideoProvider())),
    IMAGE_FOR_VIDEO_THUMBNAIL(ImageForVideo.class, new ResourceGetterBySizeId(new ImageForVideoThumbnailProvider())),
    FLV_VIDEO(FlvVideo.class, new ResourceGetterDirect(new FlvVideoProvider())),
    SOURCE_VIDEO(Video.class, new ResourceGetterDirect(new SourceVideoProvider())),
    IMAGE_FILE(ImageFile.class, new ResourceGetterBySizeId(new ImageFileProvider())),
    WIDGET_IMAGE(DraftImage.class, new ResourceGetterBySizeId(new WidgetImageProvider())),
    MENU_IMAGE(MenuImage.class, new ResourceGetterBySizeId(new MenuImageProvider())),
    WIDGET_IMAGE_ROLLOVER(DraftImage.class, new ResourceGetterBySizeId(new WidgetImageRollOverProvider())),
    SLIDE_SHOW_IMAGE_PROVIDER(DraftSlideShowImage.class, new ResourceGetterBySizeId(new SlideShowImageProvider())),
    SLIDE_SHOW_FORM_IMAGE_PROVIDER(DraftSlideShowImage.class, new ResourceGetterBySizeId(new SlideShowFormImageProvider())),
    SLIDE_SHOW_ORIGINAL_FORM_IMAGE_PROVIDER(DraftSlideShowImage.class, new ResourceGetterBySizeId(new SlideShowOriginalFormImageProvider())),
    SLIDE_SHOW_ORIGINAL_IMAGE_PROVIDER(DraftSlideShowImage.class, new ResourceGetterBySizeId(new SlideShowOriginalImageProvider())),

    /*---------------------------------------------------Form files---------------------------------------------------*/
    GALLERY(DraftGallery.class, new ResourceGetterBySizeId(new GalleryProvider())),
    GALLERY_DATA(DraftGallery.class, new ResourceGetterBySizeId(new GalleryDataProvider())),

    FORM_FILE(FormFile.class, new ResourceGetterBySizeId(new FormFileProvider())),
    FORM_FILE_THUMBNAIL(FormFile.class, new ResourceGetterBySizeId(new FormFileThumbnailProvider())),
    GALLERY_DATA_SOURCE_SIZE(DraftGallery.class, new ResourceGetterBySizeId(new GalleryDataSourceSizeProvider())),
    PREVIEW_IMAGE_FORM_FILE(FormFile.class, new ResourceGetterBySizeId(new PreviewImageFormFileProvider()));
    /*---------------------------------------------------Form files---------------------------------------------------*/


    ResourceGetterType(final Class resourceClass, final ResourceGetter getter) {
        if (getter == null) {
            throw new UnsupportedOperationException(
                    "Can't create resource type by null getter!");
        }
        if (resourceClass == null) {
            throw new UnsupportedOperationException(
                    "Can't create resource type by null class!");
        }

        this.getter = getter;
        this.resourceClass = resourceClass;
    }

    private String getUrl(final int resourceId, final int resourceVersion, final boolean resourceDownload) {
        return "/resourceGetter.action?resourceId=" + resourceId
                + "&resourceVersion=" + resourceVersion
                + "&resourceGetterType=" + this + "&resourceDownload=" + resourceDownload;
    }

    public String getImageUrl(final Image image) {
        return "/images1/" + image.getResourceId() + "-" + image.getName() + "." + image.getExtension();
    }

    public String getImageThumbnailUrl(final Image image, final int width, final int height) {
        return "/thumbnails/" + width + "x" + height + "/" + image.getResourceId() + "-" + image.getName() + "." + image.getExtension();
    }

    public String getUrl(final int resourceId) {
        return getUrl(resourceId, 0, false);
    }

    public String getVideoUrl(final FlvVideo resourceFlv) {
        if (resourceFlv == null) {
            return null;
        }
        final NowTime nowTime = ServiceLocator.getNowTime();
        final Config config = ServiceLocator.getConfigStorage().get();
        final ConfigResourcesVideo resourcesVideo = config.getSiteResourcesVideo();
        if (resourcesVideo.isUseNgnix()) {
            String url = "/" + resourcesVideo.getNgnixUrlPrefix() + "/";

            final String flvUrl = fileSystem.getResourceName(resourceFlv);

            if (resourcesVideo.getNgnixSecretToken() != null) {
                final long now = nowTime.get();
                final String flvUrlMd5 = MD5.crypt(flvUrl + now + resourcesVideo.getNgnixSecretToken());
                url += flvUrlMd5 + "/" + now + "/";
            }

            url += flvUrl;
            return url;
        }
        return getUrl(resourceFlv.getFlvVideoId());
    }

    public ResourceGetter getGetter() {
        return getter;
    }

    /**
     * @return - class that use for define synchronize request
     */
    public Class getResourceClass() {
        return resourceClass;
    }

    private final Class resourceClass;
    private final ResourceGetter getter;
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();

}
