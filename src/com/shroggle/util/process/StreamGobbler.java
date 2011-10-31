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

import java.io.*;

/**
 * @author Stasuk Artem
 */
final class StreamGobbler extends Thread {

    StreamGobbler(InputStream is, PrintStream out) {
        this.is = is;
        this.out = out;
    }

    public void run() {
        try {
            final InputStreamReader inputStreamReader = new InputStreamReader(is);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                out.print("    ");
                out.println(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private final InputStream is;
    private final PrintStream out;

}
