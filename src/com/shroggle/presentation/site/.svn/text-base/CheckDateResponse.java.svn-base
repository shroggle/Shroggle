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

package com.shroggle.presentation.site;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;


/**
 * @author Balakirev Anatoliy
 */

@DataTransferObject
public class CheckDateResponse {

    @RemoteProperty
    private boolean wrongStartDate = false;

    @RemoteProperty
    private boolean wrongEndDate = false;

    @RemoteProperty
    private boolean endBeforeStart = false;

    @RemoteProperty
    private boolean endBeforeCurrent = false;

    @RemoteProperty
    private boolean showIncomeSettingsWindow = false;

    @RemoteProperty
    private String receivePaymentsInnerHtml;

    @RemoteProperty
    private FunctionalWidgetInfo widgetInfo;

    public FunctionalWidgetInfo getWidgetInfo() {
        return widgetInfo;
    }

    public void setWidgetInfo(FunctionalWidgetInfo widgetInfo) {
        this.widgetInfo = widgetInfo;
    }

    public String getReceivePaymentsInnerHtml() {
        return receivePaymentsInnerHtml;
    }

    public void setReceivePaymentsInnerHtml(String receivePaymentsInnerHtml) {
        this.receivePaymentsInnerHtml = receivePaymentsInnerHtml;
    }

    public boolean isShowIncomeSettingsWindow() {
        return showIncomeSettingsWindow;
    }

    public void setShowIncomeSettingsWindow(boolean showIncomeSettingsWindow) {
        this.showIncomeSettingsWindow = showIncomeSettingsWindow;
    }

    public boolean isEndBeforeCurrent() {
        return endBeforeCurrent;
    }

    public void setEndBeforeCurrent(boolean endBeforeCurrent) {
        this.endBeforeCurrent = endBeforeCurrent;
    }

    public boolean isWrongStartDate() {
        return wrongStartDate;
    }

    public void setWrongStartDate(boolean wrongStartDate) {
        this.wrongStartDate = wrongStartDate;
    }

    public boolean isWrongEndDate() {
        return wrongEndDate;
    }

    public void setWrongEndDate(boolean wrongEndDate) {
        this.wrongEndDate = wrongEndDate;
    }

    public boolean isEndBeforeStart() {
        return endBeforeStart;
    }

    public void setEndBeforeStart(boolean endBeforeStart) {
        this.endBeforeStart = endBeforeStart;
    }
}