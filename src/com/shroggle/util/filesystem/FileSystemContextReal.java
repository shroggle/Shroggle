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

import java.util.List;
import java.util.ArrayList;

/**
 * @author Artem Stasuk
 */
class FileSystemContextReal implements FileSystemContext {

    public FileSystemContextReal(final FileSystemReal fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public FileSystemItem createItem() {
        final FileSystemItemReal item = new FileSystemItemReal(fileSystem.createTemp());
        items.add(item);
        return item;
    }

    void removeIfNotStay() {
        for (final FileSystemItemReal item : items) {
            item.removeIfNotStay();
        }
    }

    private final FileSystemReal fileSystem;
    private final List<FileSystemItemReal> items = new ArrayList<FileSystemItemReal>();

}
