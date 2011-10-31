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

import com.shroggle.entity.JournalItemPriority;
import com.shroggle.util.journal.JournalEasy;

import java.io.IOException;

/**
 * @author Artem Stasuk
 */
public class SystemConsoleReal implements SystemConsole {

    @Override
    public int execute(final String command) {
        journalEasy.add(JournalItemPriority.INFO, "Start: " + command);

        try {
            final int code = ProcessUtil.execute(command);
            journalEasy.add(JournalItemPriority.INFO, "Success: " + command + ", result code: " + code);

            return code;
        } catch (final IOException exception) {
            journalEasy.add(JournalItemPriority.ERROR, "Error: " + command, exception);
            throw new SystemConsoleException(exception);
        } catch (final InterruptedException exception) {
            journalEasy.add(JournalItemPriority.ERROR, "Error: " + command, exception);
            Thread.currentThread().interrupt();
            return -2;
        }
    }


    @Override
    public int execute(final String command, ProcessResponse response) {
        journalEasy.add(JournalItemPriority.INFO, "Start: " + command);

        try {
            final int code = ProcessUtil.execute(command, response);
            journalEasy.add(JournalItemPriority.INFO, "Success: " + command + ", result code: " + code);

            return code;
        } catch (final IOException exception) {
            journalEasy.add(JournalItemPriority.ERROR, "Error: " + command, exception);
            throw new SystemConsoleException(exception);
        } catch (final InterruptedException exception) {
            journalEasy.add(JournalItemPriority.ERROR, "Error: " + command, exception);
            Thread.currentThread().interrupt();
            return -2;
        }
    }

    private final JournalEasy journalEasy = new JournalEasy(SystemConsoleReal.class);

}
