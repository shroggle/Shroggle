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
package com.shroggle.logic.start;

import com.shroggle.util.ServiceLocator;

import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class UpdateJar implements Update {

    public UpdateJar(int version, String path, final String configFile) {
        this.version = version;
        this.path = path;
        this.configFile = configFile;
    }

    @Override
    public void execute() {
        final int code = ServiceLocator.getSystemConsole().execute("java -Xmx512M -jar " + path + " " + configFile);
        Logger.getLogger(this.getClass().getName()).info("Version #" + version + " has been executed with code: " + code);
        if (code != 0) {
            throw new RuntimeException("Version #" + version + " can't be executed!");
        }
    }

    public int getVersion() {
        return version;
    }

    private final int version;
    private final String path;
    private final String configFile;
}