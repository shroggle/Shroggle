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

import java.util.*;

/**
 * @author Artem Stasuk
 */
public class SystemConsoleMock implements SystemConsole {

    /**
     * @param systemConsole - use this console only when can't find executed command index
     *                      in executedCommandResults, may be null
     */
    public SystemConsoleMock(final SystemConsole systemConsole) {
        this.systemConsole = systemConsole;
    }

    @Override
    public int execute(final String command) {
        executedCommands.add(command);

        if (executedCommandResults.contains(executedCommands.size() - 1)) {
            throw new SystemConsoleException();
        }

        if (systemConsole != null) {
            return systemConsole.execute(command);
        }

        return 0;
    }

    @Override
    public int execute(final String command, ProcessResponse response) {
        executedCommands.add(command);

        if (executedCommandResults.contains(executedCommands.size() - 1)) {
            throw new SystemConsoleException();
        }

        if (systemConsole != null) {
            return systemConsole.execute(command, response);
        }

        return 0;
    }

    public List<String> getExecutedCommands() {
        return executedCommands;
    }

    public Set<Integer> getExecutedCommandResults() {
        return executedCommandResults;
    }

    private final SystemConsole systemConsole;
    private final List<String> executedCommands = new ArrayList<String>();
    private final Set<Integer> executedCommandResults = new HashSet<Integer>();

}
