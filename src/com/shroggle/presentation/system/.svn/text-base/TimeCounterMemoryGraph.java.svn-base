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

import org.rrd4j.graph.RrdGraphDef;

import java.awt.*;

/**
 * @author Artem Stasuk
 */
class TimeCounterMemoryGraph extends TimeCounterSimpleGraph {

    public TimeCounterMemoryGraph() {
        super("total://memoryBytes");
    }

    @Override
    protected void executeAddition(final RrdGraphDef graphDef) {
        graphDef.setVerticalLabel("bytes");
        graphDef.hrule(Runtime.getRuntime().totalMemory(), Color.RED, "Total memory");
    }

}