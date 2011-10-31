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
import org.rrd4j.graph.RrdGraph;
import org.rrd4j.graph.RrdGraphDef;
import org.rrd4j.graph.RrdGraphInfo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
class TimeCounterSimpleGraph implements TimeCounterGroupGraph {

    public TimeCounterSimpleGraph(final String name) {
        this.name = name;
    }

    @Override
    public byte[] execute(long period) throws IOException {
        if (executeNotExist()) {
            return null;
        }

        final RrdGraphDef graphDef = new RrdGraphDef();
        graphDef.setTimeSpan((System.currentTimeMillis() - period) / 1000l, (System.currentTimeMillis() + period / 4) / 1000l);
        graphDef.setWidth(WIDTH);
        graphDef.setHeight(HEIGHT);
        graphDef.setFilename("-");
        graphDef.gprint("1", ConsolFun.LAST, "Current:%8.2lf %s");
        graphDef.gprint("1", ConsolFun.AVERAGE, "Average:%8.2lf %s");
        graphDef.gprint("1", ConsolFun.MAX, "Maximum:%8.2lf %s");
        graphDef.setImageFormat("png");
        graphDef.setAntiAliasing(true);

        executeGeneral(graphDef);
        executeAddition(graphDef);

        final RrdGraph graph = new RrdGraph(graphDef);
        final RrdGraphInfo graphInfo = graph.getRrdGraphInfo();
        final BufferedImage bi = new BufferedImage(graphInfo.getWidth(), graphInfo.getHeight(), BufferedImage.TYPE_INT_RGB);
        graph.render(bi.getGraphics());

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", out);
        return out.toByteArray();
    }

    protected void executeGeneral(final RrdGraphDef graphDef) {
        graphDef.datasource("1", TimeCounterUtilRrd.getPath(name),
                TimeCounterUtilRrd.getRrdName(name), ConsolFun.AVERAGE);
        graphDef.line("1", Color.BLACK, null, 2);
    }

    protected boolean executeNotExist() {
        return TimeCounterUtilRrd.notExist(name);
    }

    protected void executeAddition(final RrdGraphDef graphDef) {

    }

    protected final String name;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 120;

}
