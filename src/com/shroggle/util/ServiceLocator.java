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

package com.shroggle.util;

import com.shroggle.logic.advancedSearch.resultsNumber.AdvancedSearchResultsNumberCache;
import com.shroggle.logic.customtag.CustomTagFacade;
import com.shroggle.logic.customtag.CustomTagProcessor;
import com.shroggle.logic.gallery.paypal.PaypalButtonIPNRequestStorage;
import com.shroggle.logic.site.*;
import com.shroggle.logic.site.page.pageversion.PageVersionNormalizer;
import com.shroggle.logic.site.payment.PaypalPaymentInfoRequestStorage;
import com.shroggle.logic.system.CreateSitesMethod;
import com.shroggle.logic.system.CreateSitesStatus;
import com.shroggle.logic.user.ExpireUserDeleter;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.WebContextGetter;
import com.shroggle.presentation.site.RenderedPageHtmlProvider;
import com.shroggle.util.cache.CacheCreator;
import com.shroggle.util.cache.CacheCreatorByPolicy;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.context.ContextCreator;
import com.shroggle.util.context.ContextStorage;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.copier.Copier;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.filesystem.fileWriter.AsynchronousFilesWriter;
import com.shroggle.util.html.HtmlGetter;
import com.shroggle.util.html.optimization.PageResourcesAccelerator;
import com.shroggle.util.html.processor.HtmlProcessor;
import com.shroggle.util.international.InternationalStorage;
import com.shroggle.util.journal.Journal;
import com.shroggle.util.mail.MailAddressValidator;
import com.shroggle.util.mail.MailSender;
import com.shroggle.util.payment.PaymentSystem;
import com.shroggle.util.payment.authorize.AuthorizeNet;
import com.shroggle.util.payment.javien.Javien;
import com.shroggle.util.payment.paypal.PayPal;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.SystemConsole;
import com.shroggle.util.process.synchronize.Synchronize;
import com.shroggle.util.process.timecounter.TimeCounterCreator;
import com.shroggle.util.reflection.Classes;
import com.shroggle.util.resource.ResourceGetterUrl;
import com.shroggle.util.resource.ResourceProducer;
import com.shroggle.util.resource.resize.ResourceResizer;
import com.shroggle.util.testhelp.TestHelpStorage;
import com.shroggle.util.testspeed.CheckPerformance;
import com.shroggle.util.transcode.VideoTranscode;

/**
 * All services for your application.
 * Application layouts (Top > Down):
 * Presentation
 * Manager (it's Logic)
 * Util
 * Entity
 *
 * @author Stasuk Artem
 */
public class ServiceLocator {

    public static Persistance getPersistance() {
        return persistance;
    }

    public static PersistanceTransaction getPersistanceTransaction() {
        return persistanceTransaction;
    }

    public static void setPersistance(final Persistance persistance) {
        ServiceLocator.persistance = persistance;
    }

    public static void setPersistanceTransaction(final PersistanceTransaction persistanceTransaction) {
        ServiceLocator.persistanceTransaction = persistanceTransaction;
    }

    public static FileSystem getFileSystem() {
        return fileSystem;
    }

    public static void setFileSystem(final FileSystem fileSystem) {
        ServiceLocator.fileSystem = fileSystem;
    }

    public static AsynchronousFilesWriter getFilesWriter() {
        return filesWriter;
    }

    public static void setFilesWriter(final AsynchronousFilesWriter filesWriter) {
        ServiceLocator.filesWriter = filesWriter;
    }

    public static SessionStorage getSessionStorage() {
        return sessionStorage;
    }

    public static void setSessionStorage(final SessionStorage sessionStorage) {
        ServiceLocator.sessionStorage = sessionStorage;
    }

    public static ConfigStorage getConfigStorage() {
        return configStorage;
    }

    public static void setConfigStorage(final ConfigStorage configStorage) {
        ServiceLocator.configStorage = configStorage;
    }

    public static WebContextGetter getWebContextGetter() {
        return webContextGetter;
    }

    public static void setWebContextGetter(WebContextGetter webContextGetter) {
        ServiceLocator.webContextGetter = webContextGetter;
    }

    public static MailSender getMailSender() {
        return mailSender;
    }

    public static void setMailSender(MailSender mailSender) {
        ServiceLocator.mailSender = mailSender;
    }

    public static Synchronize getSynchronize() {
        return synchronize;
    }

    public static void setSynchronize(Synchronize synchronize) {
        ServiceLocator.synchronize = synchronize;
    }

    public static VideoTranscode getVideoTranscode() {
        return videoTranscode;
    }

    public static void setVideoTranscode(VideoTranscode videoTranscode) {
        ServiceLocator.videoTranscode = videoTranscode;
    }

    public static ResolutionCreator getResolutionCreator() {
        return resolutionCreator;
    }

    public static void setResolutionCreator(final ResolutionCreator resolutionCreator) {
        ServiceLocator.resolutionCreator = resolutionCreator;
    }

    public static MailAddressValidator getMailAddressValidator() {
        return mailAddressValidator;
    }

    public static void setMailAddressValidator(final MailAddressValidator mailAddressValidator) {
        ServiceLocator.mailAddressValidator = mailAddressValidator;
    }

    public static InternationalStorage getInternationStorage() {
        return internationalStorage;
    }

    public static void setInternationStorage(InternationalStorage internationalStorage) {
        ServiceLocator.internationalStorage = internationalStorage;
    }

    public static TimeCounterCreator getTimeCounterCreator() {
        return timeCounterCreator;
    }

    public static void setTimeCounterCreator(TimeCounterCreator timeCounterCreator) {
        ServiceLocator.timeCounterCreator = timeCounterCreator;
    }

    public static ContextCreator getContextCreator() {
        return contextCreator;
    }

    public static void setContextCreator(final ContextCreator contextCreator) {
        ServiceLocator.contextCreator = contextCreator;
    }

    public static HtmlGetter getHtmlGetter() {
        return htmlGetter;
    }

    public static void setHtmlGetter(final HtmlGetter htmlGetter) {
        ServiceLocator.htmlGetter = htmlGetter;
    }

    public static void setJavien(Javien javien) {
        PaymentSystem.setShroggleDefaultJavien(javien);
    }

    public static void setAuthorizeNet(AuthorizeNet authorizeNet) {
        PaymentSystem.setShroggleDefaultAuthorizeNet(authorizeNet);
    }

    public static void setPayPal(final PayPal payPal) {
        PaymentSystem.setShroggleDefaultPayPal(payPal);
    }

    public static CacheCreator getCacheCreator() {
        return cacheCreator;
    }

    public static void setCacheCreator(final CacheCreator cacheStorage) {
        ServiceLocator.cacheCreator = cacheStorage;
    }

    public static CacheCreatorByPolicy getCacheCreatorByPolicy() {
        return cacheCreatorByPolicy;
    }

    public static void setCacheCreatorByPolicy(final CacheCreatorByPolicy cacheCreatorByPolicy) {
        ServiceLocator.cacheCreatorByPolicy = cacheCreatorByPolicy;
    }

    public static Journal getJournal() {
        return journal;
    }

    public static void setJournal(final Journal journal) {
        ServiceLocator.journal = journal;
    }

    public static ExpireUserDeleter getExpireUserDeleter() {
        return expireUserDeleter;
    }

    public static void setExpireUserDeleter(final ExpireUserDeleter expireUserDeleter) {
        ServiceLocator.expireUserDeleter = expireUserDeleter;
    }

    public static TestHelpStorage getTestHelpStorage() {
        return testHelpStorage;
    }

    public static void setTestHelpStorage(final TestHelpStorage testHelpStorage) {
        ServiceLocator.testHelpStorage = testHelpStorage;
    }

    public static ContextStorage getContextStorage() {
        return contextStorage;
    }

    public static void setContextStorage(final ContextStorage contextStorage) {
        ServiceLocator.contextStorage = contextStorage;
    }

    public static NowTime getNowTime() {
        return nowTime;
    }

    public static void setNowTime(final NowTime nowTime) {
        ServiceLocator.nowTime = nowTime;
    }

    public static SystemConsole getSystemConsole() {
        return systemConsole;
    }

    public static void setSystemConsole(final SystemConsole systemConsole) {
        ServiceLocator.systemConsole = systemConsole;
    }

    public static PageResourcesAccelerator getPageResourcesAccelerator() {
        return pageResourcesAccelerator;
    }

    public static void setPageResourcesAccelerator(final PageResourcesAccelerator pageResourcesAccelerator) {
        ServiceLocator.pageResourcesAccelerator = pageResourcesAccelerator;
    }

    public static PageResourcesAccelerator getRootPageResourcesAccelerator() {
        return rootPageResourcesAccelerator;
    }

    public static void setRootPageResourcesAccelerator(final PageResourcesAccelerator rootPageResourcesAccelerator) {
        ServiceLocator.rootPageResourcesAccelerator = rootPageResourcesAccelerator;
    }

    public static HtmlProcessor getHtmlProcessor() {
        return htmlProcessor;
    }

    public static void setHtmlProcessor(final HtmlProcessor htmlProcessor) {
        ServiceLocator.htmlProcessor = htmlProcessor;
    }

    public static ResourceGetterUrl getResourceGetter() {
        return resourceGetterUrl;
    }

    public static void setResourceGetter(final ResourceGetterUrl resourceGetterUrl) {
        ServiceLocator.resourceGetterUrl = resourceGetterUrl;
    }

    public static ResourceResizer getResourceResizer() {
        return resourceResizer;
    }

    public static void setResourceResizer(final ResourceResizer resourceResizer) {
        ServiceLocator.resourceResizer = resourceResizer;
    }

    public static ResourceProducer getResourceProducer() {
        return resourceProducer;
    }

    public static void setResourceProducer(final ResourceProducer resourceProducer) {
        ServiceLocator.resourceProducer = resourceProducer;
    }

    public static TripleDESCrypter getTripleDESCrypter() {
        return tripleDESCrypter;
    }

    public static void setTripleDESCrypter(final TripleDESCrypter tripleDESCrypter) {
        ServiceLocator.tripleDESCrypter = tripleDESCrypter;
    }

    public static CreateSitesMethod getCreateSitesMethod() {
        return createSitesMethod;
    }

    public static void setCreateSitesMethod(final CreateSitesMethod createSitesMethod) {
        ServiceLocator.createSitesMethod = createSitesMethod;
    }

    public static AdvancedSearchResultsNumberCache getAdvancedSearchResultsNumberCache() {
        return advancedSearchResultsNumberCache;
    }

    public static void setAdvancedSearchResultsNumberCache(AdvancedSearchResultsNumberCache advancedSearchResultsNumberCache) {
        ServiceLocator.advancedSearchResultsNumberCache = advancedSearchResultsNumberCache;
    }

    public static PaypalPaymentInfoRequestStorage getPaypalPaymentInfoRequestStorage() {
        return paypalPaymentInfoRequestStorage;
    }

    public static void setPaypalPaymentInfoRequestStorage(PaypalPaymentInfoRequestStorage paypalPaymentInfoRequestStorage) {
        ServiceLocator.paypalPaymentInfoRequestStorage = paypalPaymentInfoRequestStorage;
    }

    public static PaypalButtonIPNRequestStorage getPaypalButtonIPNRequestStorage() {
        return paypalButtonIPNRequestStorage;
    }

    public static void setPaypalButtonIPNRequestStorage(PaypalButtonIPNRequestStorage paypalButtonIPNRequestStorage) {
        ServiceLocator.paypalButtonIPNRequestStorage = paypalButtonIPNRequestStorage;
    }

    public static CreateSitesStatus getCreateSitesStatus() {
        return createSitesStatus;
    }

    public static void setCreateSitesStatus(CreateSitesStatus createSitesStatus) {
        ServiceLocator.createSitesStatus = createSitesStatus;
    }

    public static CustomTagProcessor getCustomTagProcessor() {
        return customTagProcessor;
    }

    public static void setCustomTagProcessor(CustomTagProcessor customTagProcessor) {
        ServiceLocator.customTagProcessor = customTagProcessor;
    }

    public static CustomTagFacade getCustomTagFacade() {
        return customTagFacade;
    }

    public static void setCustomTagFacade(CustomTagFacade customTagFacade) {
        ServiceLocator.customTagFacade = customTagFacade;
    }

    public static CheckPerformance getCheckPerformance() {
        return performance;
    }

    public static void setCheckPerformance(CheckPerformance performance) {
        ServiceLocator.performance = performance;
    }

    public static Classes getClasses() {
        return classes;
    }

    public static void setClasses(Classes classes) {
        ServiceLocator.classes = classes;
    }

    public static SiteByUrlGetter getSiteByUrlGetter() {
        return siteByUrlGetter;
    }

    public static void setSiteByUrlGetter(final SiteByUrlGetter siteByUrlGetter) {
        ServiceLocator.siteByUrlGetter = siteByUrlGetter;
    }

    public static SiteCreatorOrUpdater getSiteCreatorOrUpdater() {
        return siteCreatorOrUpdater;
    }

    public static void setSiteCreatorOrUpdater(final SiteCreatorOrUpdater siteCreatorOrUpdater) {
        ServiceLocator.siteCreatorOrUpdater = siteCreatorOrUpdater;
    }

    public static PageVersionNormalizer getPageVersionNormalizer() {
        return pageVersionNormalizer;
    }

    public static void setPageVersionNormalizer(final PageVersionNormalizer pageVersionNormalizer) {
        ServiceLocator.pageVersionNormalizer = pageVersionNormalizer;
    }

    public static Copier getCopier() {
        return copier;
    }

    public static void setCopier(Copier copier) {
        ServiceLocator.copier = copier;
    }

    public static SiteCopierFromBlueprint getSiteCopierFromBlueprint() {
        return siteCopierFromBlueprint;
    }

    public static void setSiteCopierFromBlueprint(SiteCopierFromBlueprint siteCopierFromBlueprint) {
        ServiceLocator.siteCopierFromBlueprint = siteCopierFromBlueprint;
    }

    public static SiteCopierFromActivatedBlueprint getSiteCopierFromActivatedBlueprint() {
        return siteCopierFromActivatedBlueprint;
    }

    public static void setSiteCopierFromActivatedBlueprint(SiteCopierFromActivatedBlueprint siteCopierFromActivatedBlueprint) {
        ServiceLocator.siteCopierFromActivatedBlueprint = siteCopierFromActivatedBlueprint;
    }

    public static RenderedPageHtmlProvider getRenderedPageHtmlProvider() {
        return renderedPageHtmlProvider;
    }

    public static void setRenderedPageHtmlProvider(RenderedPageHtmlProvider renderedPageHtmlProvider) {
        ServiceLocator.renderedPageHtmlProvider = renderedPageHtmlProvider;
    }

    public static SiteCopierBlueprint getSiteCopierBlueprint() {
        return siteCopierBlueprint;
    }

    public static void setSiteCopierBlueprint(final SiteCopierBlueprint siteCopierBlueprint) {
        ServiceLocator.siteCopierBlueprint = siteCopierBlueprint;
    }

    private static RenderedPageHtmlProvider renderedPageHtmlProvider;
    private static Javien javien;
    private static AuthorizeNet authorizeNet;
    private static MailSender mailSender;
    private static HtmlGetter htmlGetter;
    private static FileSystem fileSystem;
    private static AsynchronousFilesWriter filesWriter;
    private static Synchronize synchronize;
    private static Persistance persistance;
    private static ContextCreator contextCreator;
    private static VideoTranscode videoTranscode;
    private static ConfigStorage configStorage;
    private static SessionStorage sessionStorage;
    private static WebContextGetter webContextGetter;
    private static TimeCounterCreator timeCounterCreator;
    private static InternationalStorage internationalStorage;
    private static MailAddressValidator mailAddressValidator;
    private static PersistanceTransaction persistanceTransaction;
    private static CacheCreatorByPolicy cacheCreatorByPolicy;
    private static ResolutionCreator resolutionCreator;
    private static CacheCreator cacheCreator;
    private static ExpireUserDeleter expireUserDeleter;
    private static Journal journal;
    private static TestHelpStorage testHelpStorage;
    private static ContextStorage contextStorage;
    private static NowTime nowTime;
    private static SystemConsole systemConsole;
    private static PageResourcesAccelerator pageResourcesAccelerator;
    private static TripleDESCrypter tripleDESCrypter;
    private static AdvancedSearchResultsNumberCache advancedSearchResultsNumberCache;
    private static PaypalPaymentInfoRequestStorage paypalPaymentInfoRequestStorage;
    private static PaypalButtonIPNRequestStorage paypalButtonIPNRequestStorage;

    /**
     * Need only for PageResourcesInternalAcselerator because it's has special methods
     * for call from get action, it's bad, but i don't known more better decision.
     *
     * @see com.shroggle.util.html.optimization.PageResourcesAcceleratorInternal
     */
    private static PageResourcesAccelerator rootPageResourcesAccelerator;
    private static HtmlProcessor htmlProcessor;

    /**
     * Next a lot of variable need for get images, files to client side with
     * need size and settings.
     */
    private static ResourceGetterUrl resourceGetterUrl;
    private static ResourceResizer resourceResizer;
    private static ResourceProducer resourceProducer;
    private static CreateSitesMethod createSitesMethod;
    private static CreateSitesStatus createSitesStatus;

    private static CustomTagProcessor customTagProcessor;
    private static CustomTagFacade customTagFacade;

    private static CheckPerformance performance;

    private static Classes classes;

    /**
     * @link http://jira.web-deva.com/browse/SW-5899
     */
    private static SiteByUrlGetter siteByUrlGetter;

    /**
     * http://jira.web-deva.com/browse/SW-6052
     */
    private static SiteCreatorOrUpdater siteCreatorOrUpdater;

    /**
     * http://jira.web-deva.com/browse/SW-5898
     */
    private static PageVersionNormalizer pageVersionNormalizer;

    private static Copier copier;

    private static SiteCopierFromBlueprint siteCopierFromBlueprint;
    private static SiteCopierFromActivatedBlueprint siteCopierFromActivatedBlueprint;

    /**
     * http://jira.shroggle.com/browse/SW-6450
     */
    private static SiteCopierBlueprint siteCopierBlueprint;

}
