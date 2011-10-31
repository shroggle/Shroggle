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
import java.util.List;

/**
 * @author Stasuk Artem
 */
public class RenderComposit implements Render {

    public RenderComposit(final List<Render> renders) {
        this.renders = renders;
    }

    public void execute(final RenderContext context, final StringBuilder html)
            throws IOException, ServletException {
        for (final Render render : renders) {
            render.execute(context, html);
        }
    }

    private final List<Render> renders;

}