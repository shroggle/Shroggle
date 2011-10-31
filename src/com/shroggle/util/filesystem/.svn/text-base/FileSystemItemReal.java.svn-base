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

import java.io.File;

/**
 * @author Artem Stasuk
 */
class FileSystemItemReal implements FileSystemItem {

    public FileSystemItemReal(final File file) {
        this.file = file;
    }

    @Override
    public void mustStayAs(final String name) {
        final File renameFile = new File(name);
        if (renameFile.getParentFile() != null) {
            renameFile.getParentFile().mkdirs();
        }
        mustStay = file.renameTo(renameFile);
    }

    @Override
    public String getPath() {
        return file.getPath();
    }

    void removeIfNotStay() {
        if (!mustStay) {
            file.delete();
        }
    }

    private final File file;
    private boolean mustStay;

}
