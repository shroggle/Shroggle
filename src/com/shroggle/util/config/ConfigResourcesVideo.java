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

/**
 * @author Stasuk Artem
 */
public class ConfigResourcesVideo {

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isUseNgnix() {
        return useNgnix;
    }

    public void setUseNgnix(boolean useNgnix) {
        this.useNgnix = useNgnix;
    }

    public String getNgnixUrlPrefix() {
        return ngnixUrlPrefix;
    }

    public void setNgnixUrlPrefix(String ngnixUrlPrefix) {
        this.ngnixUrlPrefix = ngnixUrlPrefix;
    }

    public String getNgnixSecretToken() {
        return ngnixSecretToken;
    }

    public void setNgnixSecretToken(String ngnixSecretToken) {
        this.ngnixSecretToken = ngnixSecretToken;
    }

    public String getCachePath() {
        return cachePath;
    }

    public void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }

    private String cachePath;
    private String path;
    private boolean useNgnix;
    private String ngnixUrlPrefix = "";
    private String ngnixSecretToken;

}