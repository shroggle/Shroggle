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

package com.shroggle.util.config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * @author Stasuk Artem
 */
public class FileConfigStorage implements ConfigStorage {

    public FileConfigStorage(final String configFile) {
        if (configFile == null) {
            throw new ConfigException("Can't create file config by null file!");
        }
        this.configFile = configFile;
    }

    public Config get() {
        try {
            final JAXBContext context = JAXBContext.newInstance(Config.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();
            final File file = new File(configFile);
            if (!file.exists()) {
                throw new ConfigException("Can't get config from non exists file for path " + configFile + "!");
            }
            return (Config) unmarshaller.unmarshal(file);
        } catch (JAXBException exception) {
            throw new ConfigException(exception);
        }
    }
//
//    public static void main(String[] args) throws JAXBException {
//            final JAXBContext context = JAXBContext.newInstance(Config.class);
//            final Marshaller marshaller = context.createMarshaller();
//            marshaller.marshal(new Config(), new File("d:/test.xml"));
//    }

    private final String configFile;

}
