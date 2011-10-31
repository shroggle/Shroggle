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

/**
 * @author Stasuk Artem
 */
public class FileSystemException extends RuntimeException {

    public FileSystemException(final Throwable cause) {
        super(cause);
    }

    public FileSystemException(final String message) {
        super(message);
    }

    public FileSystemException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
