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

package com.shroggle.util;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * @author Stasuk Artem
 */
public final class IOUtil {

    /**
     * Important. This get root path mechanism sensitive to class position in hierarchy.
     * Don't move class to other package level or change count step for find
     * base dir.
     */
    static {
        final Logger tempLog = Logger.getLogger(IOUtil.class.getName());
        final URL thisClassUrl = IOUtil.class.getResource("IOUtil.class");
        final URI thisClassUri;
        try {
            thisClassUri = thisClassUrl.toURI();
        } catch (final URISyntaxException exception) {
            // this situation it full pi, but it can be.
            throw new RuntimeException(exception);
        }

        String tempApplicationRootPath = "";

        try {
            File thisClassParentFile = new File(thisClassUri);
            while (true) {
                if (thisClassParentFile == null) {
                    throw new UnsupportedOperationException("Can't find WEB-INF root: " + thisClassUri);
                }

                if ("WEB-INF".equals(thisClassParentFile.getName())) {
                    tempApplicationRootPath = thisClassParentFile.getParentFile().getAbsolutePath();
                    break;
                }

                thisClassParentFile = thisClassParentFile.getParentFile();
            }
        } catch (RuntimeException exception) {
            // None
        }

        if (tempLog.isLoggable(Level.INFO)) {
            tempLog.info(tempApplicationRootPath);
        }
        applicationRootPath = tempApplicationRootPath;
        log = tempLog;
    }

    /**
     * @return - return base dir for this class.
     */
    public static String baseDir() {
        return applicationRootPath;
    }

    public static String read(final String filePath) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "utf-8"));
        try {
            String line = reader.readLine();
            final StringBuilder result = new StringBuilder();
            while (line != null) {
                result.append(line);
                result.append("\n");
                line = reader.readLine();
            }
            return result.toString();
        } finally {
            reader.close();
        }
    }

    /**
     * For details see http://www.rgagnon.com/javadetails/java-0064.html
     *
     * @param in   - source file
     * @param out- destination file
     * @throws IOException - if some throuble
     */
    public static void copyFile(final File in, final File out) throws IOException {
        final FileChannel inChannel = new FileInputStream(in).getChannel();
        final FileChannel outChannel = new FileOutputStream(out).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null) inChannel.close();
            if (outChannel != null) outChannel.close();
        }
    }

    public static void copyStream(final InputStream in, final OutputStream out) throws IOException {
        final byte[] buffer = new byte[1024];
        int readBytes = in.read(buffer);
        while (readBytes > 0) {
            out.write(buffer, 0, readBytes);
            readBytes = in.read(buffer);
        }
    }

    public static void copyFile(final String inPath, final String outPath) throws IOException {
        copyFile(new File(inPath), new File(outPath));
    }

    public static List<String> readByLine(final String filePath) throws IOException {
        final BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), "utf-8"));
        try {
            String line = reader.readLine();
            final List<String> result = new ArrayList<String>();
            while (line != null) {
                result.add(line);
                line = reader.readLine();
            }
            return result;
        } finally {
            reader.close();
        }
    }
   
    public static List<File> searchFiles(final Pattern pattern, final String path) {
        if (path == null) {
            throw new NullPointerException("Can't search file by null path!");
        }

        final File searchDirectory = new File(path);
        final List<File> searchFiles = new ArrayList<File>();

        searchFilesInternal(pattern, searchDirectory, searchFiles);
        return searchFiles;
    }

    private static void searchFilesInternal(
            final Pattern pattern, File searchDirectory, final List<File> searchFiles) {
        final File[] files = searchDirectory.listFiles(new FileFilter() {

            public boolean accept(File pathName) {
                if (pathName.isDirectory()) {
                    searchFilesInternal(pattern, pathName, searchFiles);
                    return false;
                }
                return pattern.matcher(pathName.getName()).find();
            }

        });
        if (files == null) {
            throw new IllegalArgumentException("\nFiles was not found in the following directory:\n" + searchDirectory.toString() + "\nCheck 'Project compiler output' in your" +
                    " IDE`s setting. It should be: \\resources\\WEB-INF\\classes");
        }
        searchFiles.addAll(Arrays.asList(files));
    }

    public static void printDefaultEncoding() {
        final ByteArrayInputStream stream = new ByteArrayInputStream(new byte[0]);
        final String defaultEncoding = new InputStreamReader(stream).getEncoding();
        log.log(Level.INFO, "Default encoding: " + defaultEncoding);
    }

    public static String getExt(final String path) {
        final int dot = StringUtil.getEmptyOrString(path).lastIndexOf('.');
        if (dot > -1) {
            return path.substring(dot + 1).toLowerCase();
        }
        return "";

    }

    IOUtil() {

    }

    private static final Logger log;
    private static final String applicationRootPath;

}
