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
package com.shroggle.logic.text;

import com.shroggle.entity.FormItemType;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * @author Balakirev Anatoliy
 */
public class TextAreaSettings {
                                                                
    public static TextAreaSettings getTextAreaSettings(FormItemType textAreaType) {
        textAreaType = textAreaType != null ? textAreaType : FormItemType.TEXT_AREA;
        switch (textAreaType) {
            case TEXT_AREA: {
                return new TextAreaSettings(ROWS, COLS);
            }
            case TEXT_AREA_DOUBLE_SIZE: {
                return new TextAreaSettings(ROWS_DOUBLE_SIZE, COLS_DOUBLE_SIZE);
            }
            default: {
                Logger.getLogger(TextAreaSettings.class.getName()).log(Level.SEVERE,
                        "Can`t create textarea settings by form item type = " + textAreaType + ". Default values will be used.");
                return new TextAreaSettings(ROWS, COLS);
            }
        }
    }

    private TextAreaSettings(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    private final int rows;

    private final int cols;

    private final static int ROWS = 4;
    private final static int COLS = 17;
    private final static int ROWS_DOUBLE_SIZE = 8;
    private final static int COLS_DOUBLE_SIZE = 40;
}
