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

import com.shroggle.entity.*;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.logic.gallery.GalleryPaginatorManager;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.gallery.NavigationCell;
import com.shroggle.presentation.gallery.NavigationRow;
import com.shroggle.presentation.gallery.NavigationRowCreator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.mock.MockHttpSession;
import net.sourceforge.stripes.mock.MockServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@UrlBinding("/site/siteMapGenerator.action")
public class SiteMapGeneratorAction extends Action {

    @DefaultHandler
    public Resolution generate() throws ParserConfigurationException, TransformerException, IOException {
        final Document siteMap = generateMap();
        final ByteArrayOutputStream out = getMapOutputStream(siteMap);

        return new StreamingResolution("text/xml", new ByteArrayInputStream(out.toByteArray()));
    }

    protected Document generateMap() throws ParserConfigurationException {
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        final Document siteMap = documentBuilder.newDocument();
        Element urlSet = siteMap.createElement("urlset");
        urlSet.setAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9");
        siteMap.appendChild(urlSet);

        final Site site = persistance.getSite(siteId);

        if (site == null) {
            throw new SiteNotFoundException("Cannot find site by Id=" + siteId);
        }

        for (PageManager pageManager : new SiteManager(site).getPages()) {
            addUrl(siteMap, urlSet, pageManager.getPublicUrl());

            List<String> internalPagesUrls = getInternalPages(pageManager);
            for (String internalPageUrl : internalPagesUrls) {
                addUrl(siteMap, urlSet, pageManager.getPublicUrl() + internalPageUrl);
            }
        }

        return siteMap;
    }

    private void addUrl(Document siteMap, Element urlSet, String pageUrl) {
        Element url = siteMap.createElement("url");

        Element loc = siteMap.createElement("loc");
        loc.setTextContent(pageUrl);

        url.appendChild(loc);
        urlSet.appendChild(url);
    }

    private ByteArrayOutputStream getMapOutputStream(Document siteMap) throws TransformerException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        DOMSource domSource = new DOMSource(siteMap);
        StreamResult streamResult = new StreamResult(out);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty
                ("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.setOutputProperty
                (OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(domSource, streamResult);

        return out;
    }

    protected static List<String> getInternalPages(final PageManager pageManager) {
        final List<String> internalPages = new ArrayList<String>();

        for (Widget widget : pageManager.getWidgets()) {
            if (widget.isWidgetItem() && widget.getItemType() == ItemType.GALLERY) {
                final Gallery gallery = (Gallery) ((WidgetItem) widget).getDraftItem();
                Integer selectedPage = GalleryPaginatorManager.createSelectedPageNumber(gallery, null);
                List<NavigationRow> navigationRows = NavigationRowCreator.createRows(gallery, widget, selectedPage,
                        new MockHttpSession(new MockServletContext("")), SiteShowOption.OUTSIDE_APP);

                for (NavigationRow navigationRow : navigationRows) {
                    for (NavigationCell cell : navigationRow.getCells()) {
                        if (cell.getUrl() != null) {
                            internalPages.add(cell.getUrl().getSearchEngineUrl());
                        }
                    }
                }
            }
        }

        return internalPages;
    }

    public int siteId;

    private Persistance persistance = ServiceLocator.getPersistance();

}
