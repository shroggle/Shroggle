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

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@Entity(name = "formExportTasks")
public class FormExportTask {

    @Id
    private int id;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(nullable = false, updatable = false)
    private int formId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date created = new Date();

    @CollectionOfElements
    private List<Date> history = new ArrayList<Date>();

    @Enumerated(value = EnumType.STRING)
    @Column(length = 15)
    private FormExportDataFormat dataFormat = FormExportDataFormat.CSV;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 10)
    private FormExportDestination destination = FormExportDestination.FTP;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 15)
    private FormExportFrequency frequency = FormExportFrequency.DAILY;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startDate;

    private Integer filterId;

    @Column(length = 255)
    private String ownFtpAddress;

    @Column(length = 255)
    private String ftpLogin;

    @Column(length = 255)
    private String ftpPassword;


    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "csvDataExportId")
    @ForeignKey(name = "formExportTasksCSVDataExportsId")
    private CSVDataExport csvDataExport;

    @Embedded
    private GoogleBaseDataExport googleBaseDataExport = new GoogleBaseDataExport();

    public String getFtpLogin() {
        return ftpLogin;
    }

    public void setFtpLogin(String ftpLogin) {
        this.ftpLogin = ftpLogin;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public FormExportDestination getDestination() {
        return destination;
    }

    public void setDestination(FormExportDestination destination) {
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public CSVDataExport getCsvDataExport() {
        return csvDataExport;
    }

    public void setCsvDataExport(CSVDataExport csvDataExport) {
        this.csvDataExport = csvDataExport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<Date> getHistory() {
        return history;
    }

    public void setHistory(List<Date> history) {
        this.history = history;
    }

    public GoogleBaseDataExport getGoogleBaseDataExport() {
        return googleBaseDataExport;
    }

    public void setGoogleBaseDataExport(GoogleBaseDataExport googleBaseDataExport) {
        this.googleBaseDataExport = googleBaseDataExport;
    }

    public FormExportDataFormat getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(FormExportDataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getOwnFtpAddress() {
        return ownFtpAddress;
    }

    public void setOwnFtpAddress(String ownFtpAddress) {
        this.ownFtpAddress = ownFtpAddress;
    }

    public FormExportFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(FormExportFrequency frequency) {
        this.frequency = frequency;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getFilterId() {
        return filterId;
    }

    public void setFilterId(Integer filterId) {
        this.filterId = filterId;
    }
}
