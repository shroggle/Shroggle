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
package com.shroggle.stresstest.application;

/**
 * @author Stasuk Artem
 */
class CommandLoop implements Command {

    public CommandLoop(final int count, final Command command) {
        this.count = count;
        this.command = command;
    }

    @Override
    public void execute() throws Exception {
        for (int i = 0; i < count; i++) {
            command.execute();
        }
    }

    private final int count;
    private final Command command;

}
