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

package com.shroggle.util.process;

import java.io.IOException;

/**
 * @author Stasuk Artem
 */
public final class ProcessUtil {

    public static int execute(final String command)
            throws IOException, InterruptedException {
        if (command == null) {
            throw new IllegalArgumentException(
                    "Can't execute process with empty or null command!");
        }

        final Process process = Runtime.getRuntime().exec(command);

        new StreamGobbler(process.getErrorStream(), System.err).start();
        new StreamGobbler(process.getInputStream(), System.out).start();

        return process.waitFor();
    }

    public static int execute(final String command, ProcessResponse response) throws IOException, InterruptedException {
        if (command == null) {
            throw new IllegalArgumentException("Can't execute process with empty or null command!");
        }

        final Process process = Runtime.getRuntime().exec(command);

        response.createResponseText(process.getErrorStream(), process.getInputStream());
        return process.waitFor();
    }

    private ProcessUtil() {
    }

}

