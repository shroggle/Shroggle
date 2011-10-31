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

/**
 * @author Artem Stasuk
 */
public enum TimeCounterGroup {

    GC_TIME(new TimeCounterSimpleGraph("total://gcTime")),
    MEMORY(new TimeCounterMemoryGraph()),
    PERSISTANCE(new TimeCounterCountTimeGraph("total://persistance")),
    FILE_SYSTEM(new TimeCounterCountTimeGraph("total://fileSystem")),
    HTTP(new TimeCounterCountTimeGraph("total://http")),
    LOAD_AVERAGE(new TimeCounterSimpleGraph("total://loadAverage"));

    TimeCounterGroup(final TimeCounterSimpleGraph graph) {
        this.graph = graph;
    }

    public TimeCounterSimpleGraph getGraph() {
        return graph;
    }

    private final TimeCounterSimpleGraph graph;

}
