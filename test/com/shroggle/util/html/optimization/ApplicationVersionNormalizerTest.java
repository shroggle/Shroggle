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
package com.shroggle.util.html.optimization;

import org.junit.Test;
import org.directwebremoting.annotations.Filter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMock;
import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 */
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class ApplicationVersionNormalizerTest {

    @Test
    public void testGetNormalizedVersionForResourcesUrl() {
        FileSystemMock fileSystem = new FileSystemMock();
        fileSystem.setApplicationVersion("applicationVersion=current.build=v1.3404beta 2009/07/14 08:30 PM current.version=v1.3404beta");
        ServiceLocator.setFileSystem(fileSystem);
        Assert.assertEquals("1.3404", ApplicationVersionNormalizer.getNormalizedApplicationVersion());
    }

    @Test
    public void testGetNormalizedVersionForResourcesUrlNew() {
        FileSystemMock fileSystem = new FileSystemMock();
        fileSystem.setApplicationVersion("version=v1.4812beta  2009/11/21 03:23 PM\nv1.4812beta");
        ServiceLocator.setFileSystem(fileSystem);
        Assert.assertEquals("1.4812", ApplicationVersionNormalizer.getNormalizedApplicationVersion());
    }


    @Test
    public void testGetNormalizedVersionForResourcesUrl_withoutAppVersionInFileSystem() {
        FileSystemMock fileSystem = new FileSystemMock();
        fileSystem.setApplicationVersion(null);
        ServiceLocator.setFileSystem(fileSystem);
        Assert.assertEquals("notSpecified", ApplicationVersionNormalizer.getNormalizedApplicationVersion());
    }

    @Test
    public void testGetNormalizedVersionForResourcesUrl_withEmptyAppVersionInFileSystem() {
        FileSystemMock fileSystem = new FileSystemMock();
        fileSystem.setApplicationVersion("");
        ServiceLocator.setFileSystem(fileSystem);
        Assert.assertEquals("notSpecified", ApplicationVersionNormalizer.getNormalizedApplicationVersion());
    }
}
