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
package com.shroggle.presentation.site.render;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
public class RenderPattern implements Render {

    public RenderPattern(final RenderPatternListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Null listener!");
        }

        this.listener = listener;
    }

    @Override
    public void execute(
            final RenderContext context, final StringBuilder html)
            throws IOException, ServletException {
        final RenderAppender appender = new RenderAppenderReal(listener.getPatterns(), html);
        if (appender.exist()) {
            appender.append(listener.execute(context, appender.getExistPattern()));
        }
    }

    private final RenderPatternListener listener;

}
