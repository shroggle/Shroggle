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
package com.shroggle.util.testhelp;

import com.shroggle.util.ServiceLocator;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author Artem Stasuk
 */
public class TestHelpLog4jAppender extends AppenderSkeleton {

    public TestHelpLog4jAppender() {
    }

    protected void append(final LoggingEvent event) {
        final TestHelpStorage testHelpStorage = ServiceLocator.getTestHelpStorage();
        if (testHelpStorage != null) {
            final String eventString = getLayout().format(event);
            testHelpStorage.addEvent(TestHelpSource.COMMONS_LOG4J, eventString);
        }
    }

    public void close() {
    }

    public boolean requiresLayout() {
        return true;
    }

}
