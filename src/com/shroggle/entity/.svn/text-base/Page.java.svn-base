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

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "pages")
@org.hibernate.annotations.Table(
        appliesTo = "pages",
        indexes = {
                @org.hibernate.annotations.Index(
                        name = "siteIdIndex",
                        columnNames = {"siteId"}
                ),
                @org.hibernate.annotations.Index(
                        name = "systemIndex",
                        columnNames = {"system"}
                )
        }
)
public class Page {

    @Id
    private int pageId;

    @ManyToOne
    @JoinColumn(name = "siteId", nullable = false)
    @ForeignKey(name = "pagesSiteId")
    private Site site;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PageType type = PageType.BLANK;

    @OneToMany(mappedBy = "visitedPage", cascade = CascadeType.ALL)
    private List<Visit> pageVisits = new ArrayList<Visit>();

    /**
     * If field have null this system pages. It no show on user interface.
     */
    private Integer position = 0;

    private boolean system = false;

    /**
     * True if user clicked on 'Add page'(new default page was created) and haven't saved it yet.
     * Such page will be deleted if user will close 'Add page' window, thought such pages can exist in system
     * if i.e. user will loose his connection before he will save page. Such pages are not showed in tree on site
     * edit page. Field set to false only in PageCreator if it creates new page. Dmitry.
     */
    private boolean saved = true;

    @OneToOne
    @JoinColumn(name = "pageSettingsId"/*, nullable = false*/)
    @ForeignKey(name = "pagesPageSettingsId")
    private DraftPageSettings pageSettings;

    private Integer screenShotId;

    /*----------------------------------------------------Methods-----------------------------------------------------*/

    public Integer getScreenShotId() {
        return screenShotId;
    }

    public void setScreenShotId(Integer screenShotId) {
        this.screenShotId = screenShotId;
    }

    public DraftPageSettings getPageSettings() {
        return pageSettings;
    }

    public void setPageSettings(DraftPageSettings pageSettings) {
        this.pageSettings = pageSettings;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public PageType getType() {
        return type;
    }

    public void setType(PageType type) {
        this.type = type;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(final Integer position) {
        this.position = position;
    }

    public List<Visit> getPageVisits() {
        return pageVisits;
    }

    public void setPageVisits(final List<Visit> pageVisits) {
        this.pageVisits = pageVisits;
    }

    public void addVisit(final Visit visit) {
        pageVisits.add(visit);
    }

    @Override
    public String toString() {
        return "Page {type: " + type + "}";
    }

    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}