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
package com.shroggle.util.html.dwr;


import com.shroggle.util.IOUtil;
import org.directwebremoting.Container;
import org.directwebremoting.annotations.AnnotationsConfigurator;
import org.directwebremoting.extend.Configurator;
import org.directwebremoting.impl.ContainerUtil;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * @author Artem Stasuk
 */
public class DwrConfiguratorAutoSearch implements Configurator {

    @Override
    public void configure(final Container container) {
        try {
            ContainerUtil.configureFromDefaultDwrXml(container);
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        } catch (final ParserConfigurationException exception) {
            throw new RuntimeException(exception);
        } catch (final SAXException exception) {
            throw new RuntimeException(exception);
        }

        new AnnotationsConfigurator().configure(new Container() {

            @Override
            public Object getBean(final String name) {
                if ("classes".equals(name)) {
                    final String regex = (String) container.getBean("autoSearchServiceFilter");
                    final Pattern pattern = Pattern.compile(regex);

                    final List<File> files = IOUtil.searchFiles(
                            pattern, IOUtil.baseDir() + "/WEB-INF/classes");

                    final StringBuilder csv = new StringBuilder();
                    boolean first = true;

                    for (final File file : files) {
                        if (!first) {
                            csv.append(",\n");
                        }

                        first = false;

                        StringBuilder fullClassName = new StringBuilder();
                        fullClassName.append(file.getName().replace(".class", ""));

                        File parent = file.getParentFile();
                        while (!parent.getName().equals("classes")) {
                            fullClassName.insert(0, parent.getName() + ".");
                            parent = parent.getParentFile();
                        }
                        csv.append(fullClassName);
                    }

                    logger.info("Find " + files.size() + " DWR services for filter: " + regex);
                    return csv.toString();
                }

                return container.getBean(name);
            }

            @Override
            public Collection getBeanNames() {
                return container.getBeanNames();
            }

        });
    }

    private static final Logger logger = Logger.getLogger(DwrConfiguratorAutoSearch.class.getSimpleName());

}
