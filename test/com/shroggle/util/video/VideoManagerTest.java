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
package com.shroggle.util.video;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Site;
import com.shroggle.entity.Video;
import com.shroggle.exception.VideoNotFoundException;
import com.shroggle.logic.video.VideoManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.filesystem.FileSystemReal;
import com.shroggle.util.process.SystemConsoleMock;
import com.shroggle.util.process.SystemConsoleReal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.awt.*;
import java.io.IOException;

/**
 * @author Balakirev Anatoliy
 *         Date: 21.09.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class VideoManagerTest {

    @Before
    public void before() throws IOException {
        fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        ServiceLocator.setFileSystem(fileSystem);
        SystemConsoleMock systemConsole = new SystemConsoleMock(new SystemConsoleReal());
        ServiceLocator.setSystemConsole(systemConsole);
    }

    @Test(expected = VideoNotFoundException.class)
    public void create_withoutVideo() {
        new VideoManager(null);
    }

    @Test
    public void testcreateSourceVideoDimension_smallAviVideo() throws IOException {
        Site site = TestUtil.createSite();
        final Video video = new Video();
        video.setSiteId(site.getSiteId());
        video.setSourceExtension("avi");
        video.setSourceName("video name");
        ServiceLocator.getPersistance().putVideo(video);

        fileSystem.setResourceStream(video, TestUtil.createInputStream(this.getClass(), "smallVideo.avi"));

        VideoManager manager = new VideoManager(video);
        Dimension dimension = manager.createSourceVideoDimension();
        Assert.assertNotNull(dimension);
        Assert.assertEquals(256, dimension.getWidth(), 0);
        Assert.assertEquals(240, dimension.getHeight(), 0);
    }


    @Test
    public void testcreateSourceVideoDimension_withNonVideoAviFile() throws IOException {
        Site site = TestUtil.createSite();
        final Video video = new Video();
        video.setSiteId(site.getSiteId());
        video.setSourceExtension("avi");
        video.setSourceName("video name");
        ServiceLocator.getPersistance().putVideo(video);

        fileSystem.setResourceStream(video, TestUtil.createInputStream(this.getClass(), "notVideo.avi"));

        VideoManager manager = new VideoManager(video);
        Dimension dimension = manager.createSourceVideoDimension();
        Assert.assertNull(dimension);
    }


    @Test
    public void testcreateSourceVideoDimension_textFile() throws IOException {
        Site site = TestUtil.createSite();
        final Video video = new Video();
        video.setSiteId(site.getSiteId());
        video.setSourceExtension("txt");
        video.setSourceName("video name");
        ServiceLocator.getPersistance().putVideo(video);

        fileSystem.setResourceStream(video, TestUtil.createInputStream(this.getClass(), "text.txt"));

        VideoManager manager = new VideoManager(video);
        Dimension dimension = manager.createSourceVideoDimension();
        Assert.assertNull(dimension);
    }

    @Test
    public void testcreateSourceVideoDimension_mp4() throws IOException {
        Site site = TestUtil.createSite();
        final Video video = new Video();
        video.setSiteId(site.getSiteId());
        video.setSourceExtension("mp4");
        video.setSourceName("video name");
        ServiceLocator.getPersistance().putVideo(video);

        fileSystem.setResourceStream(video, TestUtil.createInputStream(this.getClass(), "Clip_480_5sec_6mbps_h264.mp4"));

        VideoManager manager = new VideoManager(video);
        Dimension dimension = manager.createSourceVideoDimension();
        Assert.assertNotNull(dimension);
        Assert.assertEquals(720, dimension.getWidth(), 0);
        Assert.assertEquals(480, dimension.getHeight(), 0);
    }

    @Test
    public void testcreateSourceVideoDimension_mpg() throws IOException {
        Site site = TestUtil.createSite();
        final Video video = new Video();
        video.setSiteId(site.getSiteId());
        video.setSourceExtension("mpg");
        video.setSourceName("video name");
        ServiceLocator.getPersistance().putVideo(video);

        fileSystem.setResourceStream(video, TestUtil.createInputStream(this.getClass(), "Clip_480i_5sec_6mbps_new.mpg"));

        VideoManager manager = new VideoManager(video);
        Dimension dimension = manager.createSourceVideoDimension();
        Assert.assertNotNull(dimension);
        Assert.assertEquals(720, dimension.getWidth(), 0);
        Assert.assertEquals(480, dimension.getHeight(), 0);
    }

    @Test
    public void testcreateSourceVideoDimension_wmv() throws IOException {
        Site site = TestUtil.createSite();
        final Video video = new Video();
        video.setSiteId(site.getSiteId());
        video.setSourceExtension("wmv");
        video.setSourceName("video name");
        ServiceLocator.getPersistance().putVideo(video);

        fileSystem.setResourceStream(video, TestUtil.createInputStream(this.getClass(), "Clip_1080_5sec_VC1_15mbps.wmv"));

        VideoManager manager = new VideoManager(video);
        Dimension dimension = manager.createSourceVideoDimension();
        Assert.assertNotNull(dimension);
        Assert.assertEquals(1920, dimension.getWidth(), 0);
        Assert.assertEquals(1080, dimension.getHeight(), 0);
    }

    @Test
    public void testcreateSourceVideoDimension_flv() throws IOException {
        Site site = TestUtil.createSite();
        final Video video = new Video();
        video.setSiteId(site.getSiteId());
        video.setSourceExtension("flv");
        video.setSourceName("video name");
        ServiceLocator.getPersistance().putVideo(video);

        fileSystem.setResourceStream(video, TestUtil.createInputStream(this.getClass(), "21-01-2008-22-54-53-pacan.flv"));

        VideoManager manager = new VideoManager(video);
        Dimension dimension = manager.createSourceVideoDimension();
        Assert.assertNotNull(dimension);
        Assert.assertEquals(320, dimension.getWidth(), 0);
        Assert.assertEquals(240, dimension.getHeight(), 0);
    }


    @Test
    public void testcreateSourceVideoDimension_mov() throws IOException {
        Site site = TestUtil.createSite();
        final Video video = new Video();
        video.setSiteId(site.getSiteId());
        video.setSourceExtension("mov");
        video.setSourceName("video name");
        ServiceLocator.getPersistance().putVideo(video);

        fileSystem.setResourceStream(video, TestUtil.createInputStream(this.getClass(), "Export_20090919-211926.mov"));

        VideoManager manager = new VideoManager(video);
        Dimension dimension = manager.createSourceVideoDimension();
        Assert.assertNotNull(dimension);
        Assert.assertEquals(960, dimension.getWidth(), 0);
        Assert.assertEquals(540, dimension.getHeight(), 0);
    }


    @Test
    public void testcreateSourceVideoDimension_mkv() throws IOException {
        Site site = TestUtil.createSite();
        final Video video = new Video();
        video.setSiteId(site.getSiteId());
        video.setSourceExtension("mkv");
        video.setSourceName("video name");
        ServiceLocator.getPersistance().putVideo(video);

        fileSystem.setResourceStream(video, TestUtil.createInputStream(this.getClass(), "vsshort-mp3-vorbis.mkv"));

        VideoManager manager = new VideoManager(video);
        Dimension dimension = manager.createSourceVideoDimension();
        Assert.assertNotNull(dimension);
        Assert.assertEquals(640, dimension.getWidth(), 0);
        Assert.assertEquals(352, dimension.getHeight(), 0);
    }


    @Test
    public void testcreateSourceVideoDimension_movH264() throws IOException {
        Site site = TestUtil.createSite();
        final Video video = new Video();
        video.setSiteId(site.getSiteId());
        video.setSourceExtension("mkv");
        video.setSourceName("video name");
        ServiceLocator.getPersistance().putVideo(video);

        fileSystem.setResourceStream(video, TestUtil.createInputStream(this.getClass(), "h264.mov"));

        VideoManager manager = new VideoManager(video);
        Dimension dimension = manager.createSourceVideoDimension();
        Assert.assertNotNull(dimension);
        Assert.assertEquals(640, dimension.getWidth(), 0);
        Assert.assertEquals(360, dimension.getHeight(), 0);
    }


    @Test
    public void testcreateSourceVideoDimension_flc() throws IOException {
        Site site = TestUtil.createSite();
        final Video video = new Video();
        video.setSiteId(site.getSiteId());
        video.setSourceExtension("flc");
        video.setSourceName("video name");
        ServiceLocator.getPersistance().putVideo(video);

        fileSystem.setResourceStream(video, TestUtil.createInputStream(this.getClass(), "janmar90.flc"));

        VideoManager manager = new VideoManager(video);
        Dimension dimension = manager.createSourceVideoDimension();
        Assert.assertNotNull(dimension);
        Assert.assertEquals(640, dimension.getWidth(), 0);
        Assert.assertEquals(480, dimension.getHeight(), 0);
    }


    @Test
    public void testCreateDimensionByResponseString_serverResponse() {
        VideoManager manager = new VideoManager(new Video());
        Dimension dimension = manager.createDimensionByResponseString(
                "FFmpeg version SVN-r11539, Copyright (c) 2000-2008 Fabrice Bellard, et al.\n" +
                        "\n" +
                        "configuration: --enable-pthreads --prefix=/opt/gnu --enable-libfaac --enable-libxvid --enable-libx264 --enable-libvorbis --enable-libtheora --enable-libmp3lame --enable-libfaad --enable-libamr-nb --enable-libamr-wb --enable-liba52 --enable-gpl --enable-shared\n" +
                        "\n" +
                        "libavutil version: 49.6.0\n" +
                        "\n" +
                        "libavcodec version: 51.49.0\n" +
                        "\n" +
                        "libavformat version: 52.4.0\n" +
                        "\n" +
                        "libavdevice version: 52.0.0\n" +
                        "\n" +
                        "built on Jan 16 2008 19:06:32, gcc: 3.4.6\n" +
                        "\n" +
                        "Input #0, avi, from '/data1/CC/cruisecontrol/projects/SuperWiki/classes_test_exec/testSiteResourcesVideo/site1/videoSource_id_1.avi':\n" +
                        "\n" +
                        "Duration: 00:00:06.0, start: 0.000000, bitrate: 891 kb/s\n" +
                        "\n" +
                        "Stream #0.0: Video: IV41 / 0x31345649, 256x240 [PAR 0:1 DAR 0:1], 30.00 tb(r)\n" +
                        "\n" +
                        "Must supply at least one output file");
        Assert.assertEquals(256, dimension.getWidth(), 0);
        Assert.assertEquals(240, dimension.getHeight(), 0);
    }


    @Test
    public void testCreateDimensionByResponseString_localeResponse() {
        VideoManager manager = new VideoManager(new Video());
        Dimension dimension = manager.createDimensionByResponseString(
                "FFmpeg version SVN-r10461, Copyright (c) 2000-2007 Fabrice Bellard, et al.\n" +
                        "  configuration: --enable-memalign-hack --enable-libamr-nb --enable-libamr-wb --enable-libfaac --enable-libgsm --enable-libmp3lame --enable-libogg --enable-libtheora --enable-libvorbis --enable-liba52 --enable-libx264 --enable-libxvid --enable-libfaad --enable-swscaler --enable-gpl --enable-avisynth --cpu=i686 --enable-pthreads\n" +
                        "  libavutil version: 49.5.0\n" +
                        "  libavcodec version: 51.43.0\n" +
                        "  libavformat version: 51.12.2\n" +
                        "  built on Sep 11 2007 01:20:02, gcc: 4.2.1-sjlj (mingw32-2)\n" +
                        "[matroska @ 009D9AE8]Ignoring seekhead entry for ID=0x1549a966\n" +
                        "[matroska @ 009D9AE8]Ignoring seekhead entry for ID=0x1654ae6b\n" +
                        "[matroska @ 009D9AE8]Ignoring seekhead entry for ID=0x114d9b74\n" +
                        "[matroska @ 009D9AE8]Unknown entry 0x73a4 in info header\n" +
                        "[matroska @ 009D9AE8]Unknown track header entry 0x23314f - ignoring\n" +
                        "[matroska @ 009D9AE8]Unknown track header entry 0x23314f - ignoring\n" +
                        "[matroska @ 009D9AE8]Unknown track header entry 0x23314f - ignoring\n" +
                        "Input #0, matroska, from 'D:\\work\\classes/testSiteResourcesVideo/site1/videoSource_id_1.mkv':\n" +
                        "  Duration: 00:01:09.7, start: 0.000000, bitrate: N/A\n" +
                        "  Stream #0.0: Video: msmpeg4, yuv420p, 640x352, 23.98 fps(r)\n" +
                        "  Stream #0.1: Audio: mp3, 48000 Hz, stereo\n" +
                        "  Stream #0.2: Audio: vorbis, 48000 Hz, stereo\n" +
                        "Must supply at least one output file");
        Assert.assertEquals(640, dimension.getWidth(), 0);
        Assert.assertEquals(352, dimension.getHeight(), 0);
    }

    @Test
    public void testCreateDimensionByResponseString_smallAvi() {
        VideoManager manager = new VideoManager(new Video());
        Dimension dimension = manager.createDimensionByResponseString(
                "FFmpeg version SVN-r10461, Copyright (c) 2000-2007 Fabrice Bellard, et al.\n" +
                        "  configuration: --enable-memalign-hack --enable-libamr-nb --enable-libamr-wb --enable-libfaac --enable-libgsm --enable-libmp3lame --enable-libogg --enable-libtheora --enable-libvorbis --enable-liba52 --enable-libx264 --enable-libxvid --enable-libfaad --enable-swscaler --enable-gpl --enable-avisynth --cpu=i686 --enable-pthreads\n" +
                        "  libavutil version: 49.5.0\n" +
                        "  libavcodec version: 51.43.0\n" +
                        "  libavformat version: 51.12.2\n" +
                        "  built on Sep 11 2007 01:20:02, gcc: 4.2.1-sjlj (mingw32-2)\n" +
                        "Input #0, avi, from 'D:\\work\\classes/testSiteResourcesVideo/site1/videoSource_id_1.avi':\n" +
                        "  Duration: 00:00:06.0, start: 0.000000, bitrate: 891 kb/s\n" +
                        "  Stream #0.0: Video: IV41 / 0x31345649, 256x240, 30.00 fps(r)\n" +
                        "Must supply at least one output file");
        Assert.assertEquals(256, dimension.getWidth(), 0);
        Assert.assertEquals(240, dimension.getHeight(), 0);
    }


    @Test
    public void testIsAudio_mp3() {
        Video video = new Video();
        video.setSourceExtension("mp3");
        VideoManager manager = new VideoManager(video);
        Assert.assertTrue(manager.isAudio());
    }


    @Test
    public void testIsAudio_wav() {
        Video video = new Video();
        video.setSourceExtension("wav");
        VideoManager manager = new VideoManager(video);
        Assert.assertTrue(manager.isAudio());
    }


    @Test
    public void testIsAudio_avi() {
        Video video = new Video();
        video.setSourceExtension("avi");
        VideoManager manager = new VideoManager(video);
        Assert.assertFalse(manager.isAudio());
    }

    private FileSystem fileSystem;
}
