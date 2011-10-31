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
package com.shroggle.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Balakirev Anatoliy
 */
@Embeddable
public class PaypalSettingsForGallery {

    public PaypalSettingsForGallery() {
        paypalEmail = "";
        formItemIdWithPrice = null;
        formItemIdWithProductName = null;
        showPaypalButton = false;
        paypalSettingsPosition = -1;
        paypalSettingsRow = 0;
        paypalSettingsColumn = GalleryItemColumn.COLUMN_1;
        paypalSettingsAlign = GalleryAlign.CENTER;
        paypalSettingsName = "";
    }

    public boolean isShowPaypalButton() {
        return showPaypalButton;
    }

    public void setShowPaypalButton(boolean showPaypalButton) {
        this.showPaypalButton = showPaypalButton;
    }

    public Integer getRegistrationFormForBuyers() {
        return registrationFormForBuyers;
    }

    public void setRegistrationFormForBuyers(Integer registrationFormForBuyers) {
        this.registrationFormForBuyers = registrationFormForBuyers;
    }

    public int getPaypalSettingsPosition() {
        return paypalSettingsPosition;
    }

    public void setPaypalSettingsPosition(int paypalSettingsPosition) {
        this.paypalSettingsPosition = paypalSettingsPosition;
    }

    public int getPaypalSettingsRow() {
        return paypalSettingsRow;
    }

    public void setPaypalSettingsRow(int paypalSettingsRow) {
        this.paypalSettingsRow = paypalSettingsRow;
    }

    public GalleryItemColumn getPaypalSettingsColumn() {
        return paypalSettingsColumn;
    }

    public void setPaypalSettingsColumn(GalleryItemColumn paypalSettingsColumn) {
        this.paypalSettingsColumn = paypalSettingsColumn;
    }

    public GalleryAlign getPaypalSettingsAlign() {
        return paypalSettingsAlign;
    }

    public void setPaypalSettingsAlign(GalleryAlign paypalSettingsAlign) {
        this.paypalSettingsAlign = paypalSettingsAlign;
    }

    public String getPaypalSettingsName() {
        return paypalSettingsName;
    }

    public void setPaypalSettingsName(String paypalSettingsName) {
        this.paypalSettingsName = paypalSettingsName;
    }

    public String getPaypalEmail() {
        return paypalEmail;
    }

    public void setPaypalEmail(String paypalEmail) {
        this.paypalEmail = paypalEmail;
    }

    public Integer getFormItemIdWithPrice() {
        return formItemIdWithPrice;
    }

    public void setFormItemIdWithPrice(Integer formItemIdWithPrice) {
        this.formItemIdWithPrice = formItemIdWithPrice;
    }

    public Integer getFormItemIdWithProductName() {
        return formItemIdWithProductName;
    }

    public void setFormItemIdWithProductName(Integer formItemIdWithProductName) {
        this.formItemIdWithProductName = formItemIdWithProductName;
    }

    public Integer getFormItemIdWithProductDescription() {
        return formItemIdWithProductDescription;
    }

    public void setFormItemIdWithProductDescription(Integer formItemIdWithProductDescription) {
        this.formItemIdWithProductDescription = formItemIdWithProductDescription;
    }

    public Integer getFormItemIdWithProductImage() {
        return formItemIdWithProductImage;
    }

    public void setFormItemIdWithProductImage(Integer formItemIdWithProductImage) {
        this.formItemIdWithProductImage = formItemIdWithProductImage;
    }

    public Integer getOrdersFormId() {
        return ordersFormId;
    }

    public void setOrdersFormId(Integer ordersFormId) {
        this.ordersFormId = ordersFormId;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Integer getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Integer shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public int getGoToShoppingCartPosition() {
        return goToShoppingCartPosition;
    }

    public void setGoToShoppingCartPosition(int goToShoppingCartPosition) {
        this.goToShoppingCartPosition = goToShoppingCartPosition;
    }

    public int getGoToShoppingCartRow() {
        return goToShoppingCartRow;
    }

    public void setGoToShoppingCartRow(int goToShoppingCartRow) {
        this.goToShoppingCartRow = goToShoppingCartRow;
    }

    public GalleryItemColumn getGoToShoppingCartColumn() {
        return goToShoppingCartColumn;
    }

    public void setGoToShoppingCartColumn(GalleryItemColumn goToShoppingCartColumn) {
        this.goToShoppingCartColumn = goToShoppingCartColumn;
    }

    public GalleryAlign getGoToShoppingCartAlign() {
        return goToShoppingCartAlign;
    }

    public void setGoToShoppingCartAlign(GalleryAlign goToShoppingCartAlign) {
        this.goToShoppingCartAlign = goToShoppingCartAlign;
    }

    public String getGoToShoppingCartName() {
        return goToShoppingCartName;
    }

    public boolean isGoToShoppingCartDisplay() {
        return goToShoppingCartDisplay;
    }

    public void setGoToShoppingCartDisplay(boolean goToShoppingCartDisplay) {
        this.goToShoppingCartDisplay = goToShoppingCartDisplay;
    }

    public void setGoToShoppingCartName(String goToShoppingCartName) {
        this.goToShoppingCartName = goToShoppingCartName;
    }

    public Integer getSelectedShoppingCartPageId() {
        return selectedShoppingCartPageId;
    }

    public int getViewPurchaseHistoryPosition() {
        return viewPurchaseHistoryPosition;
    }

    public void setViewPurchaseHistoryPosition(int viewPurchaseHistoryPosition) {
        this.viewPurchaseHistoryPosition = viewPurchaseHistoryPosition;
    }

    public int getViewPurchaseHistoryRow() {
        return viewPurchaseHistoryRow;
    }

    public void setViewPurchaseHistoryRow(int viewPurchaseHistoryRow) {
        this.viewPurchaseHistoryRow = viewPurchaseHistoryRow;
    }

    public GalleryItemColumn getViewPurchaseHistoryColumn() {
        return viewPurchaseHistoryColumn;
    }

    public void setViewPurchaseHistoryColumn(GalleryItemColumn viewPurchaseHistoryColumn) {
        this.viewPurchaseHistoryColumn = viewPurchaseHistoryColumn;
    }

    public GalleryAlign getViewPurchaseHistoryAlign() {
        return viewPurchaseHistoryAlign;
    }

    public void setViewPurchaseHistoryAlign(GalleryAlign viewPurchaseHistoryAlign) {
        this.viewPurchaseHistoryAlign = viewPurchaseHistoryAlign;
    }

    public String getViewPurchaseHistoryName() {
        return viewPurchaseHistoryName;
    }

    public void setViewPurchaseHistoryName(String viewPurchaseHistoryName) {
        this.viewPurchaseHistoryName = viewPurchaseHistoryName;
    }

    public boolean isViewPurchaseHistoryDisplay() {
        return viewPurchaseHistoryDisplay;
    }

    public void setViewPurchaseHistoryDisplay(boolean viewPurchaseHistoryDisplay) {
        this.viewPurchaseHistoryDisplay = viewPurchaseHistoryDisplay;
    }

    public void setSelectedShoppingCartPageId(Integer selectedShoppingCartPageId) {
        this.selectedShoppingCartPageId = selectedShoppingCartPageId;
    }

    public Integer getPurchaseHistoryId() {
        return purchaseHistoryId;
    }

    public void setPurchaseHistoryId(Integer purchaseHistoryId) {
        this.purchaseHistoryId = purchaseHistoryId;
    }

    public Integer getSelectedPurchaseHistoryPageId() {
        return selectedPurchaseHistoryPageId;
    }

    public void setSelectedPurchaseHistoryPageId(Integer selectedPurchaseHistoryPageId) {
        this.selectedPurchaseHistoryPageId = selectedPurchaseHistoryPageId;
    }

    private boolean enable;

    private String paypalEmail;

    private Integer formItemIdWithPrice;

    private Integer formItemIdWithProductName;

    private Integer formItemIdWithProductDescription;

    private Integer formItemIdWithProductImage;

    private Integer shoppingCartId;

    private Integer selectedShoppingCartPageId;

    private Integer purchaseHistoryId;

    private Integer selectedPurchaseHistoryPageId;

    private Integer registrationFormForBuyers;

    private Integer ordersFormId;

    private boolean showPaypalButton;

    private int paypalSettingsPosition;

    private int goToShoppingCartPosition;

    private int viewPurchaseHistoryPosition;

    private int paypalSettingsRow;

    private int goToShoppingCartRow;

    private int viewPurchaseHistoryRow;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryItemColumn paypalSettingsColumn;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryItemColumn goToShoppingCartColumn = GalleryItemColumn.COLUMN_1;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryItemColumn viewPurchaseHistoryColumn = GalleryItemColumn.COLUMN_1;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryAlign paypalSettingsAlign;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryAlign goToShoppingCartAlign = GalleryAlign.LEFT;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryAlign viewPurchaseHistoryAlign = GalleryAlign.LEFT;

    @Column(length = 250)
    private String paypalSettingsName;

    @Column(length = 250)
    private String goToShoppingCartName = "";

    private boolean goToShoppingCartDisplay;

    @Column(length = 250)
    private String viewPurchaseHistoryName = "";

    private boolean viewPurchaseHistoryDisplay;


}