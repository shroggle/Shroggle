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
package com.shroggle.stresstest.util.process.timecounter.rrd;

import com.shroggle.util.process.ThreadUtil;
import org.rrd4j.ConsolFun;
import org.rrd4j.DsType;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdDef;
import org.rrd4j.core.Sample;
import org.rrd4j.graph.RrdGraph;
import org.rrd4j.graph.RrdGraphDef;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
public class TimeCounterRrdRun {

    public static void main(String[] args) throws IOException {
        long step = 5000;
        final RrdDef rrdDef = new RrdDef("db.rrd", step / 1000l);
        long start = System.currentTimeMillis();
        rrdDef.setStartTime(start / 1000l);
        rrdDef.addDatasource("ds", DsType.GAUGE, 10l, Double.NaN, Double.NaN);
        rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 1, 600);

        final RrdDb rrdDb = new RrdDb(rrdDef);
        rrdDb.close();

//        final RrdDb rrdDb1 = new RrdDb("db.rrd");
//        final Sample sample = rrdDb1.createSample();
//        sample.setAndUpdate("N:" + (int) (10 * Math.random()));
//        rrdDb1.close();

        for (int i = 0; i < 50; i++) {
            System.out.print("before time: " + (System.currentTimeMillis() - start));

            final long delta = step - (System.currentTimeMillis() - start) % step;
            System.out.print(", delta: " + delta);

            ThreadUtil.sleep(delta);

            System.out.print(", time: " + (System.currentTimeMillis() - start));

            final RrdDb rrdDb1 = new RrdDb("db.rrd");
            final Sample sample = rrdDb1.createSample();
            sample.setAndUpdate("N:" + i * i);

            System.out.println(", after time: " + (System.currentTimeMillis() - start));

            RrdGraphDef graphDef = new RrdGraphDef();
            graphDef.setTimeSpan(System.currentTimeMillis() / 1000l - 1000l, System.currentTimeMillis() / 1000l + 1000l);
            graphDef.datasource("1", "db.rrd", "ds", ConsolFun.AVERAGE);
            graphDef.line("1", new Color(0xFF, 0, 0), null, 2);
            graphDef.setFilename("./speed.png");
            RrdGraph graph = new RrdGraph(graphDef);
            BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
            graph.render(bi.getGraphics());

//            ThreadUtil.sleep(1000l);        5 = 1, 1/5 = 0.2, 7 - 5 = 2, 2 * 0.2 = 0.4
            // 3 / 5 = 0.6, 13 - 10 = 3, 3 * 0.6 = 1.8
            // 4 / 5 = 0.8, 16 - 15 = 1,
            // 1 / 5 = 0.2, 8 - 5 = 3, 3 * 0.2 = 0.6
            // 2 / 5 = 0.4, 13 - 10 = 3, 3 * 0.4 = 1.2
//            sample.setAndUpdate("N:" + (int) (i));
//            rrdDb1.close();


//            long round = (System.currentTimeMillis() / 1000l - start) / step;
//            System.out.println("round: " + round);
//            System.out.println("start round: " + round * step);
//            System.out.println(new RrdDb("db.rrd").dump());

//            ThreadUtil.sleep(3000l);
        }

//        ThreadUtil.sleep(6000l);

//        System.out.println(new RrdDb("db.rrd").dump());


    }

}
