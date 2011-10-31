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
package com.shroggle.stresstest.util.filesystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Artem Stasuk
 */
public class RenameFileToFileOtherVolume {

    public static void main(String[] args) throws IOException {
        File source = null;
        File destination = null;
        try {
            source = new File("e:/test.txt");
            OutputStream sourceStream = new FileOutputStream(source);
            sourceStream.write(2333);
            sourceStream.close();

            destination = new File("d:/test.txt");

            if (!source.renameTo(destination)) {
                throw new UnsupportedOperationException("Can't rename file to file in different volume!");
            }
        } finally {
            if (source != null) source.delete();
            if (destination != null) destination.delete();
        }
    }

}
