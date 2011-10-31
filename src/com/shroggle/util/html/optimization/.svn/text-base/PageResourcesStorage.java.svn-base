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

import com.shroggle.util.IOUtil;
import com.shroggle.util.config.ConfigException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * @author Artem Stasuk
 */
public class PageResourcesStorage {

    public PageResourcesPart get(final String name) {
        try {
            final JAXBContext context = JAXBContext.newInstance(PageResourcesLibrary.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();
            final File file = new File(IOUtil.baseDir() + "/WEB-INF/pageResources.xml");
            if (!file.exists()) {
                throw new ConfigException("Can't get from non exists file for path " + name + "!");
            }
            final PageResourcesLibrary library = (PageResourcesLibrary) unmarshaller.unmarshal(file);
            for (final PageResourcesPart part : library.getParts()) {
                if (name.equals(part.getName())) {
                    return part;
                }
            }
            return null;
        } catch (JAXBException exception) {
            throw new ConfigException(exception);
        }
    }

}
