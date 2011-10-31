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

import com.shroggle.util.process.timecounter.rrd.TimeCounterUtilRrd;
import org.rrd4j.ConsolFun;
import org.rrd4j.graph.RrdGraphDef;

import java.awt.*;

/**
 * @author Artem Stasuk
 */
public class TimeCounterCountTimeGraph extends TimeCounterSimpleGraph {

    public TimeCounterCountTimeGraph(final String name) {
        super(name);
    }

    @Override
    protected boolean executeNotExist() {
        return TimeCounterUtilRrd.notExist(name + "Count");
    }

    @Override
    protected void executeGeneral(final RrdGraphDef graphDef) {
        graphDef.datasource("1", TimeCounterUtilRrd.getPath(name + "Time"),
                TimeCounterUtilRrd.getRrdName(name + "Time"), ConsolFun.AVERAGE);
        graphDef.datasource("timeInSec", "1,1000,/");

        graphDef.datasource("2", TimeCounterUtilRrd.getPath(name + "Count"),
                TimeCounterUtilRrd.getRrdName(name + "Count"), ConsolFun.AVERAGE);

        graphDef.gprint("2", ConsolFun.LAST, "Current:%8.2lf %s");
        graphDef.gprint("2", ConsolFun.AVERAGE, "Average:%8.2lf %s");
        graphDef.gprint("2", ConsolFun.MAX, "Maximum:%8.2lf %s\n");

        graphDef.line("timeInSec", Color.BLACK, "Sec", 2);
        graphDef.line("2", Color.GREEN, "Count", 2);

    }

}
