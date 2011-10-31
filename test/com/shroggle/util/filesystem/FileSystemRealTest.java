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

package com.shroggle.util.filesystem;

import com.shroggle.PersistanceMock;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.site.cssParameter.CssParameter;
import com.shroggle.util.IOUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.MockConfigStorage;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Stasuk Artem
 */
public class FileSystemRealTest {

    @Before
    public void before() {
        ServiceLocator.setConfigStorage(new MockConfigStorage());
        ServiceLocator.setPersistance(new PersistanceMock());
        final Config config = ServiceLocator.getConfigStorage().get();

        final URL resourcesUrl = FileSystemRealTest.class.getResource("testSiteResources");
        config.setSiteResourcesPath(new File(resourcesUrl.getFile()).getPath());

        final URL resourcesVideoUrl = FileSystemRealTest.class.getResource("testSiteResourcesVideo");
        config.getSiteResourcesVideo().setPath(new File(resourcesVideoUrl.getFile()).getPath());

        final URL resourcesFlvVideoUrl = FileSystemRealTest.class.getResource("testSiteResourcesFlvVideo");
        config.getSiteResourcesVideo().setCachePath(new File(resourcesFlvVideoUrl.getFile()).getPath());
    }

    @Test
    public void getResourceForImage() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        Image image = new Image();
        image.setImageId(-1);
        image.setSiteId(1);
        image.setSourceExtension("png");

        BufferedImage data = fileSystem.getResource(image);

        Assert.assertNotNull(data);
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    @Test
    public void createResourcesPath() {
        final Config config = ServiceLocator.getConfigStorage().get();
        final String siteResourcesPath = IOUtil.baseDir() + "/test";
        final String siteResourcesVideoPath = IOUtil.baseDir() + "/test1";

        new File(siteResourcesPath).delete();
        new File(siteResourcesVideoPath).delete();

        config.setSiteResourcesPath(siteResourcesPath);
        config.getSiteResourcesVideo().setPath(siteResourcesVideoPath);
        new FileSystemReal(null, null, null, null, null, null, null, null, null);

        Assert.assertTrue(
                "File system must create resources path only as need!",
                !new File(siteResourcesPath).exists());
        Assert.assertTrue(
                "File system must create resources video path only as need!",
                !new File(siteResourcesVideoPath).exists());
    }

    @Test
    public void getLoginPageDefaultHtml() {
        URL test = FileSystemRealTest.class.getResource("loginPageDefault.html");
        FileSystem fileSystem = new FileSystemReal(
                null, null, null, null, null, null, null, test.getFile(), null);

        Assert.assertEquals(
                "File system must load default login page html!",
                "TEST!\n", fileSystem.getLoginPageDefaultHtml());
    }

    @Test
    public void getLoginAdminPageDefaultHtml() {
        URL test = FileSystemRealTest.class.getResource("loginAdminPageDefault.html");
        FileSystem fileSystem = new FileSystemReal(
                null, null, null, null, null, null, null, null, test.getFile());

        Assert.assertEquals(
                "File system must load default login admin page html!",
                "TEST!\n", fileSystem.getLoginAdminPageDefaultHtml());
    }

    @Test(expected = FileSystemException.class)
    public void getLoginPageDefaultHtmlFound() {
        FileSystem fileSystem = new FileSystemReal(
                null, null, null, null, null, null, null, "loginPageDefault.x", null);

        fileSystem.getLoginPageDefaultHtml();
    }

    @Test(expected = FileSystemException.class)
    public void getLoginAdminPageDefaultHtmlFound() {
        FileSystem fileSystem = new FileSystemReal(
                null, null, null, null, null, null, null, null, "loginPageDefault.x");

        fileSystem.getLoginAdminPageDefaultHtml();
    }

    @Test
    public void createWithNullApplicationVersionFile() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        Assert.assertNull(fileSystem.getApplicationVersion());
    }

    @Test
    public void getApplicationVersionFile() throws IOException {
        final File applicationVersionFile = new File("applicationVersion.properties");
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                new FileOutputStream(applicationVersionFile), "utf-8");
        outputStreamWriter.write("AAA");
        outputStreamWriter.close();

        final FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, applicationVersionFile.getPath(), null, null);
        Assert.assertEquals("AAA\n", fileSystem.getApplicationVersion());

        Assert.assertTrue(applicationVersionFile.delete());
        final OutputStreamWriter secondOutputStreamWriter = new OutputStreamWriter(
                new FileOutputStream(applicationVersionFile), "utf-8");
        secondOutputStreamWriter.write("BBB");
        secondOutputStreamWriter.close();

        Assert.assertEquals("AAA\n", fileSystem.getApplicationVersion());
    }

    @Test
    public void getApplicationVersionFileError() {
        new FileSystemReal(null, null, null, null, null, null, "", null, null);
    }

    @Test
    public void getFlvVideoPath() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        Video video = new Video();
        video.setVideoId(-1);
        video.setSiteId(12);
        video.setSourceExtension("avi");

        FlvVideo flvVideo = new FlvVideo();
        flvVideo.setHeight(3);
        flvVideo.setWidth(4);
        flvVideo.setSourceVideoId(video.getVideoId());
        flvVideo.setSiteId(11);

        Assert.assertEquals(IOUtil.baseDir() + File.separator + "WEB-INF" +
                File.separator + "classes" + File.separator + "com" + File.separator + "shroggle"
                + File.separator + "util" + File.separator + "filesystem" + File.separator +
                "testSiteResourcesFlvVideo/site11/video_id_-1_width_4_height_3_quality_7.flv", fileSystem.getResourcePath(flvVideo));
    }

    @Test
    public void getFlvVideoPathWithoutCachePath() {
        ServiceLocator.getConfigStorage().get().getSiteResourcesVideo().setCachePath(null);

        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        Video video = new Video();
        video.setVideoId(-1);
        video.setSiteId(12);
        video.setSourceExtension("avi");

        FlvVideo flvVideo = new FlvVideo();
        flvVideo.setHeight(3);
        flvVideo.setWidth(4);
        flvVideo.setSourceVideoId(video.getVideoId());
        flvVideo.setSiteId(11);

        Assert.assertEquals(IOUtil.baseDir() + File.separator + "WEB-INF" +
                File.separator + "classes" + File.separator + "com" + File.separator + "shroggle"
                + File.separator + "util" + File.separator + "filesystem" + File.separator +
                "testSiteResourcesVideo/site11/video_id_-1_width_4_height_3_quality_7.flv", fileSystem.getResourcePath(flvVideo));
    }

    @Test
    public void getFlvVideoPathWithSize() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        Video video = new Video();
        video.setVideoId(-1);
        video.setSiteId(1);
        video.setSourceExtension("avi");

        FlvVideo flvVideo = new FlvVideo();
        flvVideo.setWidth(22);
        flvVideo.setHeight(1);
        flvVideo.setSourceVideoId(video.getVideoId());
        flvVideo.setSiteId(1);

        Assert.assertEquals(IOUtil.baseDir() + File.separator + "WEB-INF" +
                File.separator + "classes" + File.separator + "com"
                + File.separator + "shroggle" + File.separator + "util"
                + File.separator + "filesystem" + File.separator +
                "testSiteResourcesFlvVideo/site1/video_id_-1_width_22_height_1_quality_7.flv", fileSystem.getResourcePath(flvVideo));
    }

    @Test
    public void inTransaction() {
        final FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        final List<String> itemPaths = new ArrayList<String>();
        fileSystem.inTransaction(new FileSystemTransaction() {

            @SuppressWarnings({"ResultOfMethodCallIgnored"})
            @Override
            public void execute(final FileSystemContext context) {
                final FileSystemItem item1 = context.createItem();
                Assert.assertNotNull(item1);
                try {
                    new File(item1.getPath()).createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Assert.assertTrue(new File(item1.getPath()).exists());
                itemPaths.add(item1.getPath());

                final FileSystemItem item2 = context.createItem();
                try {
                    new File(item2.getPath()).createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemPaths.add(item2.getPath());
                Assert.assertNotSame(item1.getPath(), item2.getPath());

                final FileSystemItem lostItem = context.createItem();
                try {
                    new File(lostItem.getPath()).createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                itemPaths.add(lostItem.getPath());
                lostItem.mustStayAs("ff");
                itemPaths.add(lostItem.getPath());
            }

        });

        Assert.assertFalse(new File(itemPaths.get(0)).exists());
        Assert.assertFalse(new File(itemPaths.get(1)).exists());
        Assert.assertFalse(new File(itemPaths.get(2)).exists());
        Assert.assertFalse(new File(itemPaths.get(3)).exists());
    }

    @Test
    public void getResourceForFlvVideoStream() {
        final FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        final FlvVideo flvVideo = new FlvVideo();
        flvVideo.setWidth(22);
        flvVideo.setHeight(1);
        flvVideo.setSiteId(1);
        flvVideo.setSourceVideoId(4);

        InputStream stream = fileSystem.getResourceStream(flvVideo);

        Assert.assertNotNull(stream);
    }

    @Test
    public void getVideoSourcePath() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        Video video = new Video();
        video.setVideoId(-1);
        video.setSiteId(1);
        video.setSourceExtension("avi");

        Assert.assertEquals(IOUtil.baseDir() + File.separator + "WEB-INF" +
                File.separator + "classes" + File.separator + "com" + File.separator + "shroggle" + File.separator + "util" + File.separator + "filesystem" + File.separator +
                "testSiteResourcesVideo/site1/videoSource_id_-1.avi", fileSystem.getResourcePath(video));
    }

    @Test
    public void getResourceName_video() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        Video video = new Video();
        video.setVideoId(-1);
        video.setSiteId(1);
        video.setSourceExtension("avi");

        Assert.assertEquals("site1/videoSource_id_-1.avi", fileSystem.getResourceName(video));
    }


    @Test
    public void getResourceName_videoFlv() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);

        Site site = TestUtil.createSite();
        FlvVideo flvVideo = TestUtil.createVideoFLV(1, 100, 100, 7);
        flvVideo.setSiteId(site.getSiteId());

        Assert.assertEquals("site" + site.getSiteId() + "/video_id_" + flvVideo.getSourceVideoId() +
                "_width_" + flvVideo.getWidth() + "_height_" + flvVideo.getHeight() + "_quality_" +
                flvVideo.getQuality() + ".flv", fileSystem.getResourceName(flvVideo));
    }

    @Test
    public void getResourceForImageStream() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        Image image = new Image();
        image.setImageId(-1);
        image.setSiteId(1);
        image.setSourceExtension("png");

        InputStream stream = fileSystem.getResourceStream(image);

        Assert.assertNotNull(stream);
    }

    @Test
    public void getResourceForBackgroundImageStream() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        BackgroundImage backgroundImage = new BackgroundImage();
        backgroundImage.setBackgroundImageId(3);
        backgroundImage.setSiteId(1);
        backgroundImage.setSourceExtension("png");

        InputStream stream = fileSystem.getResourceStream(backgroundImage);

        Assert.assertNotNull(stream);
    }

    @Test
    public void getTemplateResource() {
        final URL test = FileSystemRealTest.class.getResource(".");
        final FileSystem fileSystem = new FileSystemReal(test.getFile(), null, null, null, null, null, null, null, null);

        final String resource = fileSystem.getTemplateResource("testpagetemplate", "test.htm");

        Assert.assertEquals("test\n", resource);
    }

    @Test
    public void isResourceExist() {
        final URL test = FileSystemRealTest.class.getResource(".");
        final FileSystem fileSystem = new FileSystemReal(test.getFile(), null, null, null, null, null, null, null, null);
        final Video video = new Video();
        video.setSiteId(1);
        video.setVideoId(-1);
        video.setSourceExtension("flv");

        Assert.assertTrue(fileSystem.isResourceExist(video));
    }

    @Test
    public void isResourceExistNotFound() {
        final URL test = FileSystemRealTest.class.getResource(".");
        final FileSystem fileSystem = new FileSystemReal(test.getFile(), null, null, null, null, null, null, null, null);
        final Video video = new Video();
        video.setSiteId(1);
        video.setVideoId(-21);
        video.setSourceExtension("flv");

        Assert.assertFalse(fileSystem.isResourceExist(video));
    }

    @Test(expected = FileSystemException.class)
    public void getTemplateResourceNotFound() {
        final URL test = FileSystemRealTest.class.getResource(".");
        final FileSystem fileSystem = new FileSystemReal(test.getFile(), null, null, null, null, null, null, null, null);

        fileSystem.getTemplateResource("testpagetemplate", "test1.htm");
    }

    @Test(expected = FileSystemException.class)
    public void getResourceForBackgroundImageStreamNotFound() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        BackgroundImage backgroundImage = new BackgroundImage();
        backgroundImage.setBackgroundImageId(4444);
        backgroundImage.setSiteId(1);
        backgroundImage.setSourceExtension("png");

        fileSystem.getResourceStream(backgroundImage);
    }

    @Test
    public void getResourceForImageForVideoStream() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        ImageForVideo imageForVideo = new ImageForVideo();
        imageForVideo.setImageForVideoId(3);
        imageForVideo.setSiteId(1);
        imageForVideo.setSourceExtension("png");

        InputStream stream = fileSystem.getResourceStream(imageForVideo);

        Assert.assertNotNull(stream);
    }

    @Test
    public void setResourceForImage() throws IOException {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        Image image = new Image();
        image.setImageId(-2);
        image.setSiteId(1);
        image.setSourceExtension("png");

        URL data = FileSystemRealTest.class.getResource("image.png");
        fileSystem.setResource(image, ImageIO.read(new File(data.getFile())));

        BufferedImage testData = fileSystem.getResource(image);
        Assert.assertNotNull(testData);
    }

    @Test
    public void removeResourceForImage() throws IOException {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        Image image = new Image();
        image.setImageId(-2);
        image.setSiteId(1);
        image.setSourceExtension("png");
        image.setThumbnailExtension("png");

        URL data = FileSystemRealTest.class.getResource("image.png");
        fileSystem.setResource(image, ImageIO.read(new File(data.getFile())));

        fileSystem.removeResource(image);
        Assert.assertNull(fileSystem.getResource(image));
    }

    @Test
    public void setResourceForImageForVideo() throws IOException {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        ImageForVideo imageForVideo = new ImageForVideo();
        imageForVideo.setImageForVideoId(-2);
        imageForVideo.setSiteId(1);
        imageForVideo.setSourceExtension("png");

        URL data = FileSystemRealTest.class.getResource("image.png");
        fileSystem.setResource(imageForVideo, ImageIO.read(new File(data.getFile())));

        Assert.assertNotNull(fileSystem.getResourceStream(imageForVideo));
    }

    @Test
    public void setResourceForImageFile() throws IOException {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        ImageFile imageFile = new ImageFile();
        imageFile.setImageFileId(-2);
        imageFile.setSiteId(1);
        imageFile.setSourceExtension("png");

        URL data = FileSystemRealTest.class.getResource("image.png");
        fileSystem.setResourceStream(imageFile, new FileInputStream(data.getFile()));

        Assert.assertNotNull(fileSystem.getResourceStream(imageFile));
    }

    @Test
    public void setFormFile() throws IOException {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        FormFile formFile = new FormFile();
        formFile.setFormFileId(-2);
        formFile.setSiteId(1);
        formFile.setSourceExtension("png");

        URL data = FileSystemRealTest.class.getResource("image.png");
        fileSystem.setResourceStream(formFile, new FileInputStream(data.getFile()));

        Assert.assertTrue(new File(ServiceLocator.getConfigStorage().get().getSiteResourcesPath()
                + "/site" + formFile.getSiteId() + "/formFile_id_"
                + formFile.getFormFileId() + "." + formFile.getSourceExtension()).exists());

        fileSystem.removeResource(formFile);

        Assert.assertFalse(new File(ServiceLocator.getConfigStorage().get().getSiteResourcesPath()
                + "/site" + formFile.getSiteId() + "/formFile_id_"
                + formFile.getFormFileId() + "." + formFile.getSourceExtension()).exists());
    }

    @Test
    public void removeResourceForNotExistFormFile() throws IOException {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        FormFile formFile = new FormFile();
        formFile.setFormFileId(-2);
        formFile.setSiteId(1);
        formFile.setSourceExtension("png");

        fileSystem.removeResource(formFile);
    }

    @Test
    public void setResourceForBackgroundImage() throws IOException {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        BackgroundImage backgroundImage = new BackgroundImage();
        backgroundImage.setBackgroundImageId(-2);
        backgroundImage.setSiteId(1);
        backgroundImage.setSourceExtension("png");

        URL data = FileSystemRealTest.class.getResource("image.png");
        fileSystem.setResource(backgroundImage, ImageIO.read(new File(data.getFile())));

        Assert.assertNotNull(fileSystem.getResourceStream(backgroundImage));
    }

    @Test
    public void setResourceForWidgetImage() throws IOException {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = new PageManager(page);
        WidgetItem widgetImage = TestUtil.createWidgetItem();

        final DraftImage image1 = new DraftImage();
        ServiceLocator.getPersistance().putItem(image1);
        image1.setSiteId(site.getSiteId());
        widgetImage.setDraftItem(image1);

        pageVersion.addWidget(widgetImage);
        image1.setExtension("png");
        widgetImage.setWidgetId(-2);

        URL data = FileSystemRealTest.class.getResource("image.png");
        fileSystem.setResource(image1, ImageIO.read(new File(data.getFile())));

        BufferedImage testData = fileSystem.getResource(image1);
        Assert.assertNotNull(testData);
    }

    @Test
    public void setVideo() throws IOException {
        final FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        final Video video = new Video();
        video.setSourceExtension("flv");
        video.setSiteId(1);
        video.setVideoId(11);

        URL data = FileSystemRealTest.class.getResource("testSiteResourcesVideo/site1/video_id_-1.flv");
        fileSystem.setResourceStream(video, new FileInputStream(data.getFile()));

        Assert.assertTrue(new File(fileSystem.getResourcePath(video)).exists());
    }

    @Test
    public void getResourceForImageWithNotFound() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        Image image = new Image();
        image.setImageId(-1);
        image.setSourceExtension("gif");

        Assert.assertNull(fileSystem.getResource(image));
    }

    @Test
    public void getResourceForWidgetImageNotFound() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.getSitePaymentSettings().setUserId(-1);
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = new PageManager(page);
        WidgetItem widgetImage = TestUtil.createWidgetItem();
        final DraftImage image1 = new DraftImage();
        ServiceLocator.getPersistance().putItem(image1);
        image1.setSiteId(site.getSiteId());
        widgetImage.setDraftItem(image1);
        pageVersion.addWidget(widgetImage);
        widgetImage.setWidgetId(-1);
        image1.setExtension("gif");

        BufferedImage data = fileSystem.getResource(image1);

        Assert.assertNull(data);
    }

    @Test
    public void getResourceForImageFileStream() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        ImageFile imageFile = new ImageFile();
        imageFile.setSiteId(1);
        imageFile.setImageFileId(3);
        imageFile.setSourceExtension("png");

        InputStream stream = fileSystem.getResourceStream(imageFile);

        Assert.assertNotNull(stream);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithoutConfig() {
        ServiceLocator.setConfigStorage(null);
        new FileSystemReal(null, null, null, null, null, null, null, null, null);
    }

    @Test
    public void getResourceForImageFile() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        ImageFile imageFile = new ImageFile();
        imageFile.setSiteId(1);
        imageFile.setImageFileId(3);
        imageFile.setSourceExtension("png");

        BufferedImage data = fileSystem.getResource(imageFile);

        Assert.assertNotNull(data);
    }

    @Test
    public void getResourceForWidgetImage() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.getSitePaymentSettings().setUserId(-1);
        site.setSiteId(1);

        final DraftImage image1 = new DraftImage();
        ServiceLocator.getPersistance().putItem(image1);
        image1.setSiteId(site.getSiteId());
        image1.setId(-1);
        image1.setRollOverImageId(-1);
        image1.setThumbnailHeight(12);
        image1.setThumbnailWidth(3);
        image1.setExtension("png");

        BufferedImage data = fileSystem.getResource(image1);

        Assert.assertNotNull(data);
    }

    @Test
    public void getResourceForWidgetImageWithSize() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.getSitePaymentSettings().setUserId(-1);
        site.setSiteId(1);

        final DraftImage image1 = new DraftImage();
        image1.setId(-1);
        image1.setSiteId(site.getSiteId());
        image1.setRollOverImageId(-1);
        image1.setThumbnailHeight(12);
        image1.setThumbnailWidth(3);
        image1.setExtension("png");

        BufferedImage data = fileSystem.getResource(image1);

        Assert.assertNotNull(data);
    }

    @Test
    public void getResourceForWidgetImageStream() {
        FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.getSitePaymentSettings().setUserId(-1);
        site.setSiteId(1);

        final DraftImage image1 = new DraftImage();
        image1.setId(-1);
        image1.setSiteId(site.getSiteId());
        image1.setThumbnailHeight(12);
        image1.setThumbnailWidth(3);
        image1.setExtension("png");

        InputStream data = fileSystem.getResourceStream(image1);

        Assert.assertNotNull(data);
    }

    @Test
    public void getCssParameters() {
        URL test = FileSystemRealTest.class.getResource("cssParametersLibrary.xml");
        FileSystemReal fileSystem = new FileSystemReal(null, test.getFile(), null, null, null, null, null, null, null);
        List<CssParameter> cssParameters = fileSystem.getCssParameters(ItemType.TELL_FRIEND, null);

        Assert.assertNotNull(cssParameters);
        Assert.assertEquals(1, cssParameters.size());
    }

    @Test
    public void getCssParametersFloat() {
        URL test = FileSystemRealTest.class.getResource("cssParametersLibrary.xml");
        FileSystemReal fileSystem = new FileSystemReal(null, test.getFile(), null, null, null, null, null, null, null);
        List<CssParameter> cssParameters = fileSystem.getCssParameters(ItemType.FORUM, null);

        Assert.assertEquals(1, cssParameters.size());
        CssParameter cssParameter = cssParameters.get(0);
        Assert.assertEquals("Forum item color", cssParameter.getDescription());
        Assert.assertEquals("color", cssParameter.getName());
        Assert.assertEquals("1", cssParameter.getSelector());
    }

    @Test
    public void getCssParametersNotFoundType() {
        URL test = FileSystemRealTest.class.getResource("cssParametersLibrary.xml");
        FileSystemReal fileSystem = new FileSystemReal(null, test.getFile(), null, null, null, null, null, null, null);
        List<CssParameter> cssParameters = fileSystem.getCssParameters(ItemType.ADMIN_LOGIN, null);

        Assert.assertEquals(0, cssParameters.size());
    }

    @Test
    public void getCssParametersNotExistsFile() {
        FileSystemReal fileSystem = new FileSystemReal(null, "dd", null, null, null, null, null, null, null);
        List<CssParameter> cssParameters = fileSystem.getCssParameters(ItemType.TELL_FRIEND, null);
        Assert.assertEquals(0, cssParameters.size());
    }

    @Test
    public void getThemeImagePath() {
        FileSystemReal fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);

        Template template = new Template();
        template.setDirectory("test");
        Theme theme = new Theme();
        theme.setTemplate(template);
        theme.setImageFile("image.gif");
        template.getThemes().add(theme);
        String themeImagePath = fileSystem.getThemeImagePath(theme);

        Assert.assertNotNull(themeImagePath);
        Assert.assertEquals("/site/templates/test/image.gif", themeImagePath);
    }

    @Test
    public void getLayoutImageThumbnailPath() {
        FileSystemReal fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);

        Template template = new Template();
        template.setDirectory("test");
        Theme theme = new Theme();
        theme.setTemplate(template);
        theme.setFile("test.css");
        theme.setImageFile("image.gif");
        template.getThemes().add(theme);
        Layout layout = new Layout();
        layout.setThumbnailFile("layoutTheme.png");
        layout.setTemplate(template);
        template.getLayouts().add(layout);

        Assert.assertEquals(
                "/site/templates/test/layoutTheme.png",
                fileSystem.getLayoutThumbnailPath(layout));
    }

    @Test
    public void getThemeImageThumbnailPath() {
        FileSystemReal fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);

        Template template = new Template();
        template.setDirectory("test");
        Theme theme = new Theme();
        theme.setTemplate(template);
        theme.setThumbnailFile("theme.gif");
        template.getThemes().add(theme);

        Assert.assertEquals(
                "/site/templates/test/theme.gif",
                fileSystem.getThemeImageThumbnailPath(theme));
    }

    @Test
    public void getThemeColorTilePath() {
        FileSystemReal fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);

        Template template = new Template();
        template.setDirectory("test");

        Theme theme = new Theme();
        theme.setTemplate(template);
        theme.setColorTileFile("theme.gif");
        template.getThemes().add(theme);

        Assert.assertEquals(
                "/site/templates/test/theme.gif",
                fileSystem.getThemeColorTilePath(theme));
    }

    @Test
    public void getTemplates() {
        URL test = FileSystemRealTest.class.getResource(".");
        FileSystemReal fileSystem = new FileSystemReal(test.getFile(), null, null, null, null, null, null, null, null);
        List<Template> pageTemplates = fileSystem.getTemplates();

        Assert.assertNotNull(pageTemplates);
        Assert.assertEquals(1, pageTemplates.size());

        Template template = pageTemplates.get(0);
        Assert.assertEquals("a", template.getName());
        Assert.assertEquals("testpagetemplate", template.getDirectory());
        Assert.assertEquals(1, template.getLayouts().size());
        Assert.assertEquals("a1", template.getLayouts().get(0).getName());
        Assert.assertEquals("topBottom.htm", template.getLayouts().get(0).getFile());
        Assert.assertNotNull(template.getThemes());
        Assert.assertEquals(1, template.getThemes().size());

        Theme theme = template.getThemes().get(0);
        Assert.assertEquals("c", theme.getName());
        Assert.assertEquals("black.css", theme.getFile());
        Assert.assertEquals("black.png", theme.getImageFile());
        Assert.assertEquals("blackThumbnail.png", theme.getThumbnailFile());
        Assert.assertEquals("blackSelect.png", theme.getColorTileFile());

        Assert.assertEquals(1, template.getLayouts().size());
        Layout layout = template.getLayouts().get(0);
        Assert.assertNotNull(layout);
        Assert.assertEquals("a1", layout.getName());
        Assert.assertEquals("topBottom.htm", layout.getFile());
        Assert.assertEquals("layoutThumbnail.png", layout.getThumbnailFile());

        List<LayoutPattern> patterns = layout.getPatterns();
        Assert.assertEquals(3, patterns.size());
        LayoutPattern pattern0 = patterns.get(0);
        Assert.assertEquals(layout, pattern0.getLayout());
        Assert.assertEquals(PageType.HOME, pattern0.getType());
        Assert.assertEquals(2, pattern0.getPositions().size());
        Assert.assertEquals(ItemType.BLOG_SUMMARY, pattern0.getPositions().get(0).getType());
        Assert.assertEquals(11, pattern0.getPositions().get(0).getPosition());
        Assert.assertEquals(ItemType.FORUM, pattern0.getPositions().get(1).getType());
        Assert.assertEquals(1, pattern0.getPositions().get(1).getPosition());
        LayoutPattern pattern1 = patterns.get(1);
        Assert.assertEquals(PageType.BLOG, pattern1.getType());
        Assert.assertEquals(1, pattern1.getPositions().size());
        Assert.assertEquals(1, pattern1.getPositions().get(0).getPosition());
        Assert.assertNull(pattern1.getPositions().get(0).getType());
        LayoutPattern pattern2 = patterns.get(2);
        Assert.assertNull(pattern2.getType());
        Assert.assertEquals(1, pattern2.getPositions().size());
        Assert.assertEquals(2, pattern2.getPositions().get(0).getPosition());
        Assert.assertNull(pattern2.getPositions().get(0).getType());
    }

    @Test
    public void getTemplateByDirectory() {
        URL test = FileSystemRealTest.class.getResource(".");
        FileSystem fileSystem = new FileSystemReal(test.getFile(), null, null, null, null, null, null, null, null);
        Template template = fileSystem.getTemplateByDirectory("testpagetemplate");

        Assert.assertEquals("a", template.getName());
        Assert.assertEquals("testpagetemplate", template.getDirectory());
        Assert.assertEquals(1, template.getLayouts().size());
        Assert.assertEquals("a1", template.getLayouts().get(0).getName());
        Assert.assertEquals("topBottom.htm", template.getLayouts().get(0).getFile());
        Assert.assertNotNull(template.getThemes());
        Assert.assertEquals(1, template.getThemes().size());

        Theme theme = template.getThemes().get(0);
        Assert.assertEquals("c", theme.getName());
        Assert.assertEquals("black.css", theme.getFile());
        Assert.assertEquals("black.png", theme.getImageFile());
        Assert.assertEquals("blackThumbnail.png", theme.getThumbnailFile());

        Assert.assertEquals(1, template.getLayouts().size());
        Layout layout = template.getLayouts().get(0);
        Assert.assertNotNull(layout);
        Assert.assertEquals("a1", layout.getName());
        Assert.assertEquals("topBottom.htm", layout.getFile());
        Assert.assertEquals("layoutThumbnail.png", layout.getThumbnailFile());
    }

    @Test(expected = FileSystemException.class)
    public void getTemplateByDirectoryNotFound() {
        URL test = FileSystemRealTest.class.getResource(".");
        FileSystem fileSystem = new FileSystemReal(test.getFile(), null, null, null, null, null, null, null, null);
        fileSystem.getTemplateByDirectory("testpagetemplate11112");
    }

    @Test
    public void getSitesResourcesUrl() {
        URL test = FileSystemRealTest.class.getResource("sitesResourcesUrl.xml");
        FileSystemReal fileSystem = new FileSystemReal(null, null, test.getFile(), null, null, null, null, null, null);
        Set<String> sitesResourcesUrl = fileSystem.getSitesResourcesUrl();
        Assert.assertNotNull(sitesResourcesUrl);
        Assert.assertEquals(1, sitesResourcesUrl.size());
        Assert.assertEquals("test/", sitesResourcesUrl.iterator().next());
    }


}
