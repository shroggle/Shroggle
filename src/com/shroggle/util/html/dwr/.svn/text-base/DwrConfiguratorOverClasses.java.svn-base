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


import com.shroggle.util.ServiceLocator;
import com.shroggle.util.reflection.ClassesFilterAnd;
import com.shroggle.util.reflection.ClassesFilterClassAnnotations;
import com.shroggle.util.reflection.ClassesFilterPackageName;
import org.directwebremoting.Container;
import org.directwebremoting.annotations.AnnotationsConfigurator;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.extend.Configurator;
import org.directwebremoting.impl.ContainerUtil;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 */
public class DwrConfiguratorOverClasses implements Configurator {

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
                    final StringBuilder csv = new StringBuilder();
                    boolean first = true;

                    final List<Class> serviceClasses = ServiceLocator.getClasses().get(
                            new ClassesFilterAnd(
                                    new ClassesFilterPackageName("com.shroggle"),
                                    new ClassesFilterClassAnnotations(RemoteProxy.class, DataTransferObject.class)));
                    for (final Class cClass : serviceClasses) {
                        if (!first) {
                            csv.append(",\n");
                        }

                        first = false;

                        csv.append(cClass.getName());
                    }

                    logger.info("find " + serviceClasses.size());
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

    private static final Logger logger = Logger.getLogger(DwrConfiguratorOverClasses.class.getSimpleName());

}