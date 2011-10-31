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
package com.shroggle.logic.user.dashboard.keywordManager;

import com.shroggle.entity.Page;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.DoubleUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemException;
import com.shroggle.util.persistance.Persistance;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

/**
 * @author dmitry.solomadin
 */
public class KeywordManager {

    private KeywordManager() {
    }

    public KeywordManager(final int pageId) {
        page = ServiceLocator.getPersistance().getPage(pageId);

        if (page == null) {
            throw new PageNotFoundException("Cannot find page by Id = " + pageId);
        }

        try {
            pageHtml = ServiceLocator.getRenderedPageHtmlProvider().provide(page);
            document = Jsoup.parse(pageHtml);
        } catch (IOException e) {
            pageHtml = null;
        } catch (ServletException e) {
            pageHtml = null;
        } catch (FileSystemException e) {
            pageHtml = null;
        }
    }

    public Map<SEOTerm, KeywordManagerData> buildDatas() {
        final Map<SEOTerm, KeywordManagerData> map = new HashMap<SEOTerm, KeywordManagerData>();
        for (SEOTerm seoTerm : SEOTerm.values()) {
            map.put(seoTerm, buildData(seoTerm));
        }

        return map;
    }

    public KeywordManagerData buildData(final SEOTerm seoTerm) {
        KeywordManagerData data = new KeywordManagerData();
        if (pageHtml == null) {
            return data;
        }

        data.setPresent(isPresent(seoTerm));
        data.setElementContent(getContent(seoTerm, false));
        data.setUnique(isUnique(seoTerm));
        data.setDensity(calculateKeywordDensity(seoTerm));
        if (seoTerm == SEOTerm.PAGE_CONTENT) {
            data.setWordsNumber(getPageContentText().split(KEYWORD_SPLITTER_REGEXP).length);
        }

        return data;
    }

    public boolean isUnique(final SEOTerm seoTerm) {
        if (pageHtml == null) {
            return false;
        }

        final String contentOnThisPage = seoTerm == SEOTerm.PAGE_CONTENT ? getPageContentText() : getContent(seoTerm, true);

        final List<String> contentOnOtherPages = new ArrayList<String>();
        final SiteManager siteManager = new SiteManager(page.getSite());
        for (PageManager pageManager : siteManager.getPages()) {
            if (pageManager.getPageId() == page.getPageId()) {
                continue;
            }

            contentOnOtherPages.add(seoTerm == SEOTerm.PAGE_CONTENT ?
                    new KeywordManager(pageManager.getPageId()).getPageContentText() :
                    new KeywordManager(pageManager.getPageId()).getContent(seoTerm, true));
        }

        return !contentOnOtherPages.contains(contentOnThisPage);
    }

    public boolean isPresent(final SEOTerm seoTerm) {
        if (pageHtml == null) {
            return false;
        }

        switch (seoTerm) {
            case META_KEYWORDS: {
                Elements elements = document.getElementsByAttributeValue("name", "keywords");
                if (!elements.isEmpty()) {
                    return true;
                }
                break;
            }
            case META_DESCRIPTION: {
                Elements elements = document.getElementsByAttributeValue("name", "description");
                if (!elements.isEmpty()) {
                    return true;
                }
                break;
            }
            case PAGE_CONTENT: {
                return true; // Page always has content ;).
            }
            case PAGE_HEADER: {
                Elements elements = document.getElementsByTag("h1");
                if (!elements.isEmpty()) {
                    return true;
                }
                break;
            }
            case PAGE_TITLE: {
                Elements elements = document.getElementsByAttributeValue("name", "title");
                if (!elements.isEmpty()) {
                    return true;
                }
                break;
            }
            case PAGE_URL: {
                return true; // Page always has url ;).
            }
        }

        return false;
    }

    //forInternalUse - if true PAGE_URL will return url part afters slash and PAGE_CONTENT will return actual content

    public String getContent(final SEOTerm seoTerm, boolean forInternalUse) {
        if (pageHtml == null) {
            return NOT_FOUND;
        }

        switch (seoTerm) {
            case META_KEYWORDS: {
                final Elements elements = document.getElementsByAttributeValue("name", "keywords");
                return elements.attr("content").isEmpty() ? NOT_FOUND : elements.attr("content");
            }
            case META_DESCRIPTION: {
                final Elements elements = document.getElementsByAttributeValue("name", "description");
                return elements.attr("content").isEmpty() ? NOT_FOUND : elements.attr("content");
            }
            case PAGE_CONTENT: {
                return forInternalUse ? getPageContentText() :
                        ""; // We do not show whole page content :)
            }
            case PAGE_HEADER: {
                Elements elements = document.getElementsByTag("h1");
                return elements.text().isEmpty() ? NOT_FOUND : elements.text();
            }
            case PAGE_TITLE: {
                final Elements elements = document.getElementsByAttributeValue("name", "title");
                return elements.attr("content").isEmpty() ? NOT_FOUND : elements.attr("content");
            }
            case PAGE_URL: {
                return forInternalUse ? new PageManager(page).getPublicUrl().replaceAll("http://.*/", "") :
                        new PageManager(page).getPublicUrl();
            }
        }

        return NOT_FOUND;
    }

    public boolean isKeyphrasePresent(final SEOTerm seoTerm, final String keyphrase) {
        final String content = getContent(seoTerm, true);

        return content.contains(keyphrase);
    }

    public String getPageContentText() {
        return document.getElementsByTag("body").text();
    }

    public String calculateDensity(final SEOTerm seoTerm, final String keywords) {
        if (pageHtml == null) {
            return UNDEFINED;
        }

        final String[] keywordsArray = keywords.split(KEYWORD_SPLITTER_REGEXP);
        final String content = getContent(seoTerm, true);

        if (content.isEmpty() || content.equals(NOT_FOUND)) {
            return UNDEFINED;
        }

        return DoubleUtil.round(calculateDensityInternal(keywordsArray, content) * 100, 2) + "%";
    }

    public String calculateKeywordDensity(final SEOTerm seoTerm) {
        if (pageHtml == null) {
            return UNDEFINED;
        }

        final String keywords = getContent(SEOTerm.META_KEYWORDS, true);

        if (keywords.equals(NOT_FOUND)) {
            return UNDEFINED;
        }

        return calculateDensity(seoTerm, keywords);
    }

    private double calculateDensityInternal(String[] keywordsArray, String content) {
        double returnDensity = 0;

        // Remove repited elements.
        final Set<String> keywordsSet = new HashSet<String>();
        keywordsSet.addAll(Arrays.asList(keywordsArray));
        final Set<String> contentWordsSet = new HashSet<String>();
        contentWordsSet.addAll(Arrays.asList(content.split(KEYWORD_SPLITTER_REGEXP)));

        int contentWordsCount = contentWordsSet.size();
        List<Double> densities = new ArrayList<Double>();
        for (String keyword : keywordsSet) {
            int counter = 0;
            for (String textWord : contentWordsSet) {
                if (textWord.equalsIgnoreCase(keyword)) {
                    counter++;
                }
            }

            densities.add((double) counter / contentWordsCount);
        }

        for (Double density : densities) {
            returnDensity += density;
        }

        return returnDensity;
    }

    public static String normalizeKeywords(final String metaTagContent) {
        if (metaTagContent == null || metaTagContent.isEmpty()){
            return metaTagContent;
        }

        final String lowered = metaTagContent.toLowerCase();
        final String withoutAlphaNumeric = lowered.replaceAll(KEYWORD_CLEANER_REGEXP, " ");
        final String[] splitted = withoutAlphaNumeric.split(KEYWORD_SPLITTER_REGEXP);

        final Set<String> unique = new LinkedHashSet<String>();
        unique.addAll(Arrays.asList(splitted));

        String result = "";
        int i = 0;
        for (String uniqueKeyword : unique) {
            if (uniqueKeyword.isEmpty()) {
                i++;
                continue;
            }

            result += uniqueKeyword + (i == unique.size() - 1 ? "" : ", ");
            i++;
        }

        return result;
    }


    private String pageHtml;
    private Document document;
    private Page page;
    public final String NOT_FOUND = "Not Found";
    public final String UNDEFINED = "Undefined";
    private final static String KEYWORD_SPLITTER_REGEXP = "(\\+)|(\\s+)|(,\\s*)|(\\.\\s*)";
    private final static String KEYWORD_CLEANER_REGEXP = "[^\\w\\+,\\.\\s]";

}
