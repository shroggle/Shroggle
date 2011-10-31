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

package com.shroggle.util;

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Stasuk Artem
 */
public class IOUtilTest {

    @Test
    public void searchFile() {
        final URL thisDirectory = IOUtilTest.class.getResource(".");
        final Pattern testFileName = Pattern.compile("^test.xml");
        final List<File> searchFiles = IOUtil.searchFiles(testFileName, thisDirectory.getFile());

        Assert.assertNotNull(searchFiles);
        Assert.assertEquals(
                "Can't find two file with name: " + testFileName + " in IOUtilTest class directory: " +
                        thisDirectory + " and in all sub.",
                2, searchFiles.size());
    }

    @Test(expected = NullPointerException.class)
    public void searchFileByNullPath() {
        IOUtil.searchFiles(Pattern.compile("^descriptor.xml"), null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void searchFileByNullName() {
        IOUtil.searchFiles(null, "aa");
    }

    @Test
    public void baseDir() {
        String baseDir = IOUtil.baseDir();

        Assert.assertTrue(new File(baseDir).exists());
    }

    @Test
    public void create() {
        new IOUtil();
    }

    @Test
    public void getExtWithout() {
        Assert.assertEquals("", IOUtil.getExt(""));
    }

    @Test
    public void getExtNull() {
        Assert.assertEquals("", IOUtil.getExt(null));
    }

    @Test
    public void getExt() {
        Assert.assertEquals("jpg", IOUtil.getExt("a.jpg"));
    }

    @Test
    public void getExtPath() {
        Assert.assertEquals("jpg", IOUtil.getExt("c:/a.a/a.jpg"));
    }

}
