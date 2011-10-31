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

import java.io.*;

/**
 * @author Artem Stasuk
 */
public class RenameFileToFileOtherVolumeSpeedTest {

    public static void main(String[] args) throws IOException {
        renameFileManyTimes(new RenameFileViaCopy());
        renameFileManyTimes(new RenameFileViaStandart());
    }

    private static void renameFileManyTimes(RenameFile renameFile) throws IOException {
        final long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            renameFile(renameFile);
        }
        System.out.println("Rename file " + renameFile + " " + (System.currentTimeMillis() - start) + " msec");
    }

    private static void renameFile(RenameFile renameFile) throws IOException {
        File source = null;
        File destination = null;
        try {
            source = new File("e:/test.txt");
            fillFile(source, 1024 * 1024);

            destination = new File("d:/test.txt");

            renameFile.execute(source, destination);
        } finally {
            if (source != null) source.delete();
            if (destination != null) destination.delete();
        }
    }

    private static void fillFile(File file, int size) throws IOException {
        OutputStream sourceStream = new FileOutputStream(file);
        for (int i = 0; i < size; i++) sourceStream.write(i);
        sourceStream.close();
    }

}

interface RenameFile {

    void execute(File source, File destination) throws IOException;

}

class RenameFileViaCopy implements RenameFile {

    @Override
    public void execute(File source, File destination) throws IOException {
        FileInputStream sourceIn = new FileInputStream(source);
        OutputStream destinationOut = new FileOutputStream(destination);
        byte[] buffer = new byte[1024];
        int read = sourceIn.read(buffer);
        while (read > 0) {
            destinationOut.write(buffer, 0, read);
            read = sourceIn.read(buffer);
        }
        sourceIn.close();
        destinationOut.close();
    }

}

class RenameFileViaStandart implements RenameFile {

    @Override
    public void execute(File source, File destination) throws IOException {
        if (!source.renameTo(destination)) {
            throw new IOException("Failed rename!");
        }
    }

}