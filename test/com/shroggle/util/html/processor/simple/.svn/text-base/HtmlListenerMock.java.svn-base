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
package com.shroggle.util.html.processor.simple;

import com.shroggle.util.html.processor.HtmlFlatMediaBlock;
import com.shroggle.util.html.processor.HtmlListener;
import com.shroggle.util.html.processor.HtmlMediaBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class HtmlListenerMock implements HtmlListener {

    @Override
    public void onMediaBlock(HtmlMediaBlock block) {
        blocks.add(block);
    }

    @Override
    public void onFlatMediaBlock(HtmlFlatMediaBlock block) {
        flatBlocks.add(block);
    }

    public List<HtmlMediaBlock> getBlocks() {
        return blocks;
    }

    public List<HtmlFlatMediaBlock> getFlatBlocks() {
        return flatBlocks;
    }

    private final List<HtmlMediaBlock> blocks = new ArrayList<HtmlMediaBlock>();
    private final List<HtmlFlatMediaBlock> flatBlocks = new ArrayList<HtmlFlatMediaBlock>();

}
