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

import junit.framework.Assert;
import org.junit.Test;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 27.10.2008
 */
public class CacheFileSystemTest {

    @Test
    public void getLoginPageDefaultHtml() {
        FileSystemMock fileSystemMock = new FileSystemMock();
        fileSystemMock.setLoginPageDefaultHtml("FF");
        FileSystem fileSystem = new FileSystemCache(fileSystemMock);
        Assert.assertEquals("FF", fileSystem.getLoginPageDefaultHtml());
        fileSystemMock.setLoginPageDefaultHtml("FF!");
        Assert.assertEquals("FF", fileSystem.getLoginPageDefaultHtml());
    }

}
