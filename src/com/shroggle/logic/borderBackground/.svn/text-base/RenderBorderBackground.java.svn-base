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

package com.shroggle.logic.borderBackground;

import com.shroggle.entity.Background;
import com.shroggle.entity.BackgroundImage;
import com.shroggle.entity.Border;
import com.shroggle.entity.Style;
import com.shroggle.logic.StyleManager;
import com.shroggle.util.Dimension;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.resource.provider.ResourceGetterType;


/**
 * @author Balakirev Anatoliy
 */
public class RenderBorderBackground {

    public RenderBorderBackground(final Border border, final Background background) {
        this.border = border;
        this.background = background;
    }

    public static Dimension createImageSize(final Border border) {
        Style margin = border.getBorderMargin();
        Style padding = border.getBorderPadding();
        Style width = border.getBorderWidth();
        Style style = border.getBorderStyle();

        int marginWidth = createWidth(margin);
        int paddingWidth = createWidth(padding);
        int marginHeight = createHeight(margin);
        int paddingHeight = createHeight(padding);
        int borderWidth = createWidthConsideringStyleValue(width, style);
        int borderHeight = createHeightConsideringStyleValue(width, style);

        int imageWidth = (BorderLogic.WIDTH - (marginWidth + paddingWidth + borderWidth));
        int imageHeight = (BorderLogic.HEIGHT - (marginHeight + paddingHeight + borderHeight));

        return new Dimension((imageWidth < 0 ? 0 : imageWidth), (imageHeight < 0 ? 0 : imageHeight), 0, 0);
    }

    public String getBorderBackgroundStyle() {
        String style = getBackgroundStyle();
        style += getBorderStyle();
        return style;
    }

    public String getBorderStyle() {
        return getBorderStyle(border);
    }

    public static String getBorderStyle(final Border border) {
        if (border == null) {
            return "";
        }

        String style = border.getBorderPadding().stringValue(true);
        style += border.getBorderMargin().stringValue(true);
        style += border.getBorderWidth().stringValue(true);
        style += border.getBorderColor().stringValue(false);
        style += border.getBorderStyle().stringValue(false);
        return style;
    }

    public String getBackgroundStyle() {
        if (background == null) {
            return "";
        }

        String style = "background: ";
        style += background.getBackgroundRepeat() + " ";
        style += background.getBackgroundPosition() + " ";
        final BackgroundImage backgroundImage = ServiceLocator.getPersistance().getBackgroundImageById(background.getBackgroundImageId());
        if (ServiceLocator.getFileSystem().isResourceExist(backgroundImage)) {
            style += "url(\"" + ServiceLocator.getResourceGetter().get(ResourceGetterType.BACKGROUND_IMAGE, background.getBackgroundImageId(), 0, 0, 0, false) + "\") ";
        }
        style += colorOrTransparent(background.getBackgroundColor()) + " !important; ";
        return style;
    }

    private static String colorOrTransparent(final String color) {
        if (StringUtil.getEmptyOrString(color).isEmpty()) {
            return "transparent";
        }
        return color;
    }

    private static int createWidthConsideringStyleValue(final Style style, final Style styleValue) {
        int width = 0;
        if (isStyleNotNull(styleValue)) {
            String rightValue = StringUtil.getEmptyOrString(styleValue.getValues().getRightValue());
            if (isRightNotNull(style) && !rightValue.isEmpty() && !rightValue.equalsIgnoreCase("NONE")) {
                width += StyleManager.createPX(style.getValues().getRightValue(), style.getMeasureUnits().getRightMeasureUnit());
            }
            String leftValue = StringUtil.getEmptyOrString(styleValue.getValues().getLeftValue());
            if (isLeftNotNull(style) && !leftValue.isEmpty() && !leftValue.equalsIgnoreCase("NONE")) {
                width += StyleManager.createPX(style.getValues().getLeftValue(), style.getMeasureUnits().getLeftMeasureUnit());
            }
        }
        return width;
    }

    private static int createHeightConsideringStyleValue(final Style style, final Style styleValue) {
        int height = 0;
        if (isStyleNotNull(styleValue)) {
            String topValue = StringUtil.getEmptyOrString(styleValue.getValues().getTopValue());
            if (isTopNotNull(style) && !topValue.isEmpty() && !topValue.equalsIgnoreCase("NONE")) {
                height += StyleManager.createPX(style.getValues().getTopValue(), style.getMeasureUnits().getTopMeasureUnit());
            }
            String bottomValue = StringUtil.getEmptyOrString(styleValue.getValues().getBottomValue());
            if (isBottomNotNull(style) && !bottomValue.isEmpty() && !bottomValue.equalsIgnoreCase("NONE")) {
                height += StyleManager.createPX(style.getValues().getBottomValue(), style.getMeasureUnits().getBottomMeasureUnit());
            }
        }
        return height;
    }

    private static int createWidth(final Style style) {
        int width = 0;
        if (isRightNotNull(style)) {
            width += StyleManager.createPX(style.getValues().getRightValue(), style.getMeasureUnits().getRightMeasureUnit());
        }
        if (isLeftNotNull(style)) {
            width += StyleManager.createPX(style.getValues().getLeftValue(), style.getMeasureUnits().getLeftMeasureUnit());
        }
        return width;
    }

    private static int createHeight(final Style style) {
        int height = 0;
        if (isTopNotNull(style)) {
            height += StyleManager.createPX(style.getValues().getTopValue(), style.getMeasureUnits().getTopMeasureUnit());
        }
        if (isBottomNotNull(style)) {
            height += StyleManager.createPX(style.getValues().getBottomValue(), style.getMeasureUnits().getBottomMeasureUnit());
        }
        return height;
    }

    private static boolean isTopNotNull(final Style style) {
        return isStyleNotNull(style) && style.getValues().getTopValue() != null && style.getMeasureUnits().getTopMeasureUnit() != null;
    }

    private static boolean isRightNotNull(final Style style) {
        return isStyleNotNull(style) && style.getValues().getRightValue() != null && style.getMeasureUnits().getRightMeasureUnit() != null;
    }

    private static boolean isBottomNotNull(final Style style) {
        return isStyleNotNull(style) && style.getValues().getBottomValue() != null && style.getMeasureUnits().getBottomMeasureUnit() != null;
    }

    private static boolean isLeftNotNull(final Style style) {
        return isStyleNotNull(style) && style.getValues().getLeftValue() != null && style.getMeasureUnits().getLeftMeasureUnit() != null;
    }

    private static boolean isStyleNotNull(final Style style) {
        return style != null && style.getValues() != null && style.getMeasureUnits() != null;
    }

    private final Border border;
    private final Background background;

}