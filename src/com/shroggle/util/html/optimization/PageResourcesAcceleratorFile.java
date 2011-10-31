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

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.html.HtmlUtil;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Arten Stasuk
 */
public class PageResourcesAcceleratorFile implements PageResourcesAccelerator {

    public PageResourcesAcceleratorFile(final PageResourcesAccelerator accelerator) {
        this.accelerator = accelerator;
    }

    @Override
    public PageResourcesValue execute(final PageResourcesContext context, final String name)
            throws IOException, ServletException {
        final PageResourcesValue value = accelerator.execute(context, name);

        final Config config = ServiceLocator.getConfigStorage().get();
        final FileSystem fileSystem = ServiceLocator.getFileSystem();

        fileSystem.setPageResources(config.getPathResourceMergeAgent()
                + name, value.getValue());

        return new PageResourcesValue(HtmlUtil.getTag(HtmlUtil.getMimeByName(name),"http://" + config.getApplicationUrl() + "/"
                + (config.getResourceMergePrefix() == null ? "" : config.getResourceMergePrefix() + "/")
                + name + "?version=" + ApplicationVersionNormalizer.getNormalizedApplicationVersion()));
    }

    private final PageResourcesAccelerator accelerator;

}
