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

import java.util.logging.Logger;
import java.util.Random;

/**
 * @author Stasuk Artem
 */
class CommandThreads implements Command {

    public CommandThreads(final int count, final Command... commands) {
        this.count = count;
        this.commands = commands;
    }

    @Override
    public void execute() throws Exception {
        final Random random = new Random();
        final Thread[] threads = new Thread[count];
        for (int i = 0; i < threads.length; i++) {
            final Command command = commands[i % commands.length];
            threads[i] = new Thread(new ThreadsRunnable(i, command));
            Thread.sleep(random.nextInt(1000));
            threads[i].start();
        }

        for (final Thread thread : threads) {
            thread.join();
        }
    }

    private static class ThreadsRunnable implements Runnable {

        public ThreadsRunnable(final int index, final Command command) {
            this.index = index;
            this.command = command;
        }

        @Override
        public void run() {
            logger.info(index + " start");
            try {
                command.execute();
                logger.info(index + " finish");
            } catch (final Exception exception) {
                throw new RuntimeException(index + " thread is dead, but left for you message.", exception);
            }
        }

        private final int index;
        private final Command command;

    }

    private final int count;
    private final Command[] commands;
    private final static Logger logger = Logger.getLogger("CommandThreads");

}
