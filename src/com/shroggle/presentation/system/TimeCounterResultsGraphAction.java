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
package com.shroggle.presentation.system;

import com.shroggle.presentation.Action;
import com.shroggle.util.WithoutCacheResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
@UrlBinding("/system/timeCounterResultsGraph.action")
public class TimeCounterResultsGraphAction extends Action {

    public Resolution execute() throws IOException {
        final byte[] out = group.getGraph().execute(period);
        if (out == null) return null;

        return new WithoutCacheResolution(
                new StreamingResolution(
                        "image/png", new ByteArrayInputStream(out)));
    }

    public void setPeriod(final long period) {
        this.period = period;
    }

    public void setGroup(final TimeCounterGroup group) {
        this.group = group;
    }

    private TimeCounterGroup group;
    private long period;

}
