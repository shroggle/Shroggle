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

package com.shroggle.entity;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Stasuk Artem
 */
@DataTransferObject
@Embeddable
public class ThemeId {

    public ThemeId() {
    }

    public ThemeId(final String templateDirectory, final String themeCss) {
        this.templateDirectory = templateDirectory;
        this.themeCss = themeCss;
    }

    public String getTemplateDirectory() {
        return templateDirectory;
    }

    public void setTemplateDirectory(final String templateDirectory) {
        this.templateDirectory = templateDirectory;
    }

    public String getThemeCss() {
        return themeCss;
    }

    public void setThemeCss(final String themeName) {
        this.themeCss = themeName;
    }

    @Override
    public final int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + (getTemplateDirectory() != null ? getTemplateDirectory().hashCode() : 0);
        hashCode = 31 * hashCode + (getThemeCss() != null ? getThemeCss().hashCode() : 0);
        return hashCode;
    }

    @Override
    public final boolean equals(final Object object) {
        if (!(object instanceof ThemeId)) {
            return false;
        }
        if (object == this) {
            return true;
        }
        final ThemeId themeId = (ThemeId) object;
        return (templateDirectory == null ? themeId.templateDirectory == null : templateDirectory.equals(themeId.templateDirectory)) &&
                (themeCss == null ? themeId.themeCss == null : themeCss.equals(themeId.themeCss));
    }

    @Override
    public final String toString() {
        return this.getClass().getName()
                + " templateDirectory: " + templateDirectory
                + ", themeCss: " + themeCss;
    }

    @RemoteProperty
    @Column(length = 250)
    private String templateDirectory;

    /**
     * This field contain css file name from description for this
     * template
     */
    @RemoteProperty
    @Column(length = 250)
    private String themeCss;

}
