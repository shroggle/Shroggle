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

package com.shroggle.logic.start;

import com.shroggle.entity.Site;
import com.shroggle.logic.accessibility.UserOnSiteRightsCreator;
import com.shroggle.logic.advancedSearch.resultsNumber.AdvancedSearchResultsNumberCache;
import com.shroggle.logic.customtag.CustomTagFacade;
import com.shroggle.logic.customtag.simple.CustomTagProcessorSimple;
import com.shroggle.logic.form.export.FormExportTaskScheduler;
import com.shroggle.logic.gallery.paypal.PaypalButtonIPNRequestStorage;
import com.shroggle.logic.site.*;
import com.shroggle.logic.site.billingInfo.BillingInfoType;
import com.shroggle.logic.site.billingInfo.SiteBillingInfoFactory;
import com.shroggle.logic.site.billingInfo.SitesBillingInfoChecker;
import com.shroggle.logic.site.page.pageversion.PageVersionNormalizerReal;
import com.shroggle.logic.site.payment.PaypalPaymentInfoRequestStorage;
import com.shroggle.logic.user.ExpireUserDeleterReal;
import com.shroggle.presentation.RealWebContextGetter;
import com.shroggle.presentation.ResolutionCreatorReal;
import com.shroggle.presentation.site.RenderedPageHtmlProviderReal;
import com.shroggle.util.*;
import com.shroggle.util.cache.CacheCreatorByPolicy;
import com.shroggle.util.cache.CacheCreatorReal;
import com.shroggle.util.config.*;
import com.shroggle.util.context.ContextCreatorHttpSession;
import com.shroggle.util.context.ContextStorage;
import com.shroggle.util.context.RealSessionStorage;
import com.shroggle.util.copier.CopierReal;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.filesystem.FileSystemCache;
import com.shroggle.util.filesystem.FileSystemReal;
import com.shroggle.util.filesystem.FileSystemTimeCounter;
import com.shroggle.util.filesystem.fileWriter.AsynchronousFilesWriter;
import com.shroggle.util.html.HtmlGetterUseDwr;
import com.shroggle.util.html.optimization.*;
import com.shroggle.util.html.processor.simple.HtmlProcessorRegex;
import com.shroggle.util.international.InternationalStorageTimeCounter;
import com.shroggle.util.international.cache.InternationalStorageCache;
import com.shroggle.util.international.hightlight.InternationalStorageHighlight;
import com.shroggle.util.international.parameters.InternationalStorageParameters;
import com.shroggle.util.international.property.InternationalStoragePropertyBundle;
import com.shroggle.util.journal.*;
import com.shroggle.util.mail.*;
import com.shroggle.util.payment.authorize.AuthorizeNetReal;
import com.shroggle.util.payment.paypal.PayPalReal;
import com.shroggle.util.persistance.PersistanceContext;
import com.shroggle.util.persistance.PersistanceTimeCounter;
import com.shroggle.util.persistance.hibernate.HibernatePersistance;
import com.shroggle.util.persistance.hibernate.HibernateTransaction;
import com.shroggle.util.process.SystemConsoleReal;
import com.shroggle.util.process.synchronize.classic.ClassicSynchronize;
import com.shroggle.util.process.timecounter.TimeCounterCreator;
import com.shroggle.util.process.timecounter.real.TimeCounterCreatorReal;
import com.shroggle.util.process.timecounter.rrd.TimeCounterCreatorRrd;
import com.shroggle.util.reflection.*;
import com.shroggle.util.resource.ResourceGetterUrlInternal;
import com.shroggle.util.resource.ResourceProducerLimited;
import com.shroggle.util.resource.ResourceProducerSimple;
import com.shroggle.util.resource.resize.ResourceResizerHighQuality;
import com.shroggle.util.resource.resize.ResourceResizerTimeCounter;
import com.shroggle.util.testhelp.TestHelpJournal;
import com.shroggle.util.testspeed.CheckPerformance;
import com.shroggle.util.transcode.VideoTranscodeAsync;
import com.shroggle.util.transcode.VideoTranscodeNative;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.shroggle.util.ServiceLocator.*;

final class Start {


    public void initPresentation() {
        initPageResourcesAccelerator();
        setSiteByUrlGetter(new SiteByUrlGetterCache(new SiteByUrlGetterReal()));
        setHtmlGetter(new HtmlGetterUseDwr());
        setWebContextGetter(new RealWebContextGetter());
        setContextCreator(new ContextCreatorHttpSession());
        setContextStorage(new ContextStorage());
        setResolutionCreator(new ResolutionCreatorReal());
    }

    public void initUtil() {
        final Config config = getConfigStorage().get();

        setSiteCopierBlueprint(new SiteCopierBlueprintReal());
        setSiteCopierFromActivatedBlueprint(new SiteCopierFromActivatedBlueprintReal());
        setRenderedPageHtmlProvider(new RenderedPageHtmlProviderReal());
        setCopier(new CopierReal());
        setPageVersionNormalizer(new PageVersionNormalizerReal());
        setSiteCreatorOrUpdater(new SiteCreatorOrUpdaterReal());

        setClasses(new ClassesTimeMeter(
                new ClassesSynchronize(
                        new ClassesClassLoader("com.simontuffs.onejar.JarClassLoader",
                                new ClassesOneJar("com.shroggle.entity"),
                                new ClassesStripes("com.shroggle")))));

        setCheckPerformance(new CheckPerformance());
        setAdvancedSearchResultsNumberCache(new AdvancedSearchResultsNumberCache());
        setPaypalPaymentInfoRequestStorage(new PaypalPaymentInfoRequestStorage());
        setPaypalButtonIPNRequestStorage(new PaypalButtonIPNRequestStorage());
        setTripleDESCrypter(new TripleDESCrypter(config.getEncryptionKey()));
        setNowTime(new NowTimeReal());

        TimeCounterCreator timeCounterCreator = new TimeCounterCreatorReal();
        if (config.getRrdPath() != null) {
            timeCounterCreator = new TimeCounterCreatorRrd(timeCounterCreator);
        }
        setTimeCounterCreator(timeCounterCreator);

        setResourceProducer(new ResourceProducerLimited(new ResourceProducerSimple()));
        setResourceGetter(new ResourceGetterUrlInternal());
        setResourceResizer(new ResourceResizerTimeCounter(new ResourceResizerHighQuality()));
        setHtmlProcessor(new HtmlProcessorRegex());
        setSystemConsole(new SystemConsoleReal());
        setSessionStorage(new RealSessionStorage());
        setVideoTranscode(new VideoTranscodeAsync(new VideoTranscodeNative()));
        setMailAddressValidator(new MailAddressNoInternetUseValidator());
        setSynchronize(new ClassicSynchronize());

        setInternationStorage(
                new InternationalStorageParameters(
                        new InternationalStorageCache(
                                new InternationalStoragePropertyBundle())));

        if (config.isUseInternationalHightlight()) {
            setInternationStorage(
                    new InternationalStorageHighlight(
                            getInternationStorage()));
        }

        setInternationStorage(
                new InternationalStorageTimeCounter(
                        getInternationStorage()));

        setCustomTagFacade(new CustomTagFacade());
        setCustomTagProcessor(new CustomTagProcessorSimple());
    }

    public void initPay() {
        final Config config = getConfigStorage().get();

        if (config.isUseProxy()) {
            Properties systemSettings = System.getProperties();
            systemSettings.put("http.proxyHost", config.getProxyHost());
            systemSettings.put("http.proxyPort", config.getProxyPort());
            System.setProperties(systemSettings);
        }

        // Configure payment processing providers
        final ConfigPayPalData payPalData = config.getPayPal().getPayPalData();
        setPayPal(new PayPalReal(payPalData.getAPIUserName(), payPalData.getAPIPassword(), payPalData.getSignature()));
        final ConfigAuthorizeNet authorizeNetConfig = config.getAuthorizeNet();
        setAuthorizeNet(new AuthorizeNetReal(authorizeNetConfig.getLogin(), authorizeNetConfig.getTransactionKey()));

        BillingInfoProperties properties = config.getBillingInfoProperties();
        billingInfoChecker = SiteBillingInfoFactory.createInstance(properties);
        billingInfoChecker.execute();

        creditCardMailSender = SiteBillingInfoFactory.createInstance(
                properties, BillingInfoType.EMAIL_NOTIFICATIONS);
        creditCardMailSender.execute();
    }

    public void initFormExportTaskScheduler() {
        formExportTaskScheduler = new FormExportTaskScheduler(DateUtil.getMillisToMidnight(System.currentTimeMillis()),
                TimeInterval.ONE_DAY.getMillis());
    }


    public void initCron() {
        final Config config = getConfigStorage().get();
        if (config.getExpireUserDeleteCount() > 0) {
            ServiceLocator.setExpireUserDeleter(new ExpireUserDeleterReal());
        }
    }

    public void initCache() {
        // Configuration cache provider
        setCacheCreator(new CacheCreatorReal());
        setCacheCreatorByPolicy(
                new CacheCreatorByPolicy(getCacheCreator()));
    }

    public void initMailSender() {
        final Config config = getConfigStorage().get();
        if (config.isUseConsoleMailSender()) {
            if (config.isUseRealMailSender()) {
                setMailSender(new AsyncMailSender(new MailSenderComposit(
                        new RealMailSender(), new ConsoleMailSender())));
            } else {
                setMailSender(new ConsoleMailSender());
            }
        } else {
            if (config.isUseRealMailSender()) {
                setMailSender(new AsyncMailSender(new RealMailSender()));
            } else {
                setMailSender(new MailSender() {
                    @Override
                    public void send(final Mail mail) {
                    }
                });
            }
        }
    }

    public void initFileSystem() {
        final Config config = getConfigStorage().get();
        final String widgetCssParametersFile = IOUtil.baseDir() + "/WEB-INF/cssParametersLibrary.xml";
        final String sitesResourcesUrl = IOUtil.baseDir() + "/WEB-INF/sitesResourcesUrl.xml";
        final String speciesFile = IOUtil.baseDir() + "/WEB-INF/Species_option_list.txt";
        final String genusFile = IOUtil.baseDir() + "/WEB-INF/Genus_option_list.txt";
        final String familyFile = IOUtil.baseDir() + "/WEB-INF/Family_option_list.txt";
        final String templatesPath = IOUtil.baseDir() + "/site/templates";
        final String applicationVersionFile = IOUtil.baseDir() + "/application-version.properties";
        final String loginPageDefaultHtmlFile = IOUtil.baseDir() + "/WEB-INF/loginPageDefault.html";
        final String loginAdminPageDefaultHtmlFile = IOUtil.baseDir() + "/WEB-INF/loginAdminPageDefault.html";

        FileSystem fileSystem = new FileSystemReal(
                templatesPath, widgetCssParametersFile, sitesResourcesUrl,
                speciesFile, genusFile, familyFile, applicationVersionFile,
                loginPageDefaultHtmlFile, loginAdminPageDefaultHtmlFile);
        if (config.isUseCacheFileSystem()) {
            fileSystem = new FileSystemCache(fileSystem);
        }
        if (config.isUseTimeCounterFileSystem()) {
            fileSystem = new FileSystemTimeCounter(fileSystem);
        }
        setFileSystem(fileSystem);
    }

    public void initFilesWriter() {
        setFilesWriter(new AsynchronousFilesWriter());
    }

    public void initJournals() {
        final Config config = ServiceLocator.getConfigStorage().get();
        final List<Journal> journals = new ArrayList<Journal>();
        if (config.isUseJournalToPersistance()) {
            journals.add(new JournalAsyncClassic(new JournalToPersistance()));
        }
        if (config.isUseJournalToConsole()) {
            journals.add(new JournalToConsole());
        }
        if (config.isUseTestHelp()) {
            journals.add(new TestHelpJournal());
        }
        setJournal(new JournalComposit(journals));
    }

    public void initPageResourcesAccelerator() {
        final Config config = getConfigStorage().get();
        setRootPageResourcesAccelerator(
                new PageResourcesAcceleratorYuiCompressor(
                        new PageResourcesAcceleratorMerge()));

        if (config.getPathResourceMergeAgent() == null) {
            setRootPageResourcesAccelerator(
                    new PageResourcesAcceleratorInternalLazy(
                            getRootPageResourcesAccelerator()));
        } else {
            setRootPageResourcesAccelerator(
                    new PageResourcesAcceleratorFile(
                            getRootPageResourcesAccelerator()));
        }

        // Add denied for some agents
        PageResourcesAccelerator yes = getRootPageResourcesAccelerator();
        if (!config.isDisableResourceCache()) yes = new PageResourcesAcceleratorCache(yes);

        setPageResourcesAccelerator(
                new PageResourcesAcceleratorSpecificAgent(yes,
                        new PageResourcesAcceleratorCache(new PageResourcesAcceleratorNone())));
    }

    public void initPersistance() {
        final Config config = getConfigStorage().get();
        setPersistance(new HibernatePersistance());
        if (config.isUseTimeCounterPersistance()) {
            setPersistance(new PersistanceTimeCounter(getPersistance()));
        }

        setPersistanceTransaction(new HibernateTransaction());
    }

    public String initConfig() {
        // Get from jvm enviroment property with application config file
        String configFile = (String) System.getProperties().get("com.shroggle.util.config.configFile");
        if (configFile == null) {
            // if in jvm enviroment can't found config file, use default
            configFile = IOUtil.baseDir() + "/../superWikiConfig.xml";
        }
        ConfigStorage configStorage = new FileConfigStorage(configFile);
        final Config config = configStorage.get();
        if (config.isUseCacheConfigStorage()) {
            configStorage = new CacheConfigStorage(configStorage);
        }
        setConfigStorage(configStorage);
        return configFile;
    }

    public void initVisitorsGroupsChecker() {
        removeVisitorsFromGroupsTask = new RemoveVisitorsFromGroupsTask();
        removeVisitorsFromGroupsTask.execute();
    }

    public void destroy() {
        if (billingInfoChecker != null) {
            billingInfoChecker.destroy();
        }

        if (removeVisitorsFromGroupsTask != null) {
            removeVisitorsFromGroupsTask.destroy();
        }

        if (creditCardMailSender != null) {
            creditCardMailSender.destroy();
        }

        if (formExportTaskScheduler != null) {
            formExportTaskScheduler.destroy();
        }

        if (getJournal() != null) {
            getJournal().destroy();
        }

        if (getExpireUserDeleter() != null) {
            getExpireUserDeleter().destroy();
        }

        if (getPersistance() != null) {
            getPersistance().destroy();
        }

        if (getCacheCreator() != null) {
            getCacheCreator().destroy();
        }

        if (getVideoTranscode() != null) {
            getVideoTranscode().destroy();
        }

        if (getFilesWriter() != null) {
            getFilesWriter().destroy();
        }
    }

    /*
     * We are creating UserOnSiteRight when application is starting for all app-admins (<adminEmail> from the config) on
     * all published and activated blueprints.
     *
     * We need this because we can add app-admins to the config at every moment and if rights are created only at the
     * moment of publishing this new app-admins will not have rights to already created sites. Tolik
    */
    public void addRightsForAppAdmins() {
        ServiceLocator.getPersistance().inContext(new PersistanceContext<Void>() {
            public Void execute() {
                for (final Site site : ServiceLocator.getPersistance().getPublishedBlueprints()) {
                    ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
                        @Override
                        public void run() {
                            UserOnSiteRightsCreator.createRightsForAppAdmins(site);
                        }
                    });
                }
                for (final Site site : ServiceLocator.getPersistance().getActiveBlueprints(null)) {
                    ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
                        @Override
                        public void run() {
                            UserOnSiteRightsCreator.createRightsForAppAdmins(site);
                        }
                    });
                }
                return null;
            }
        });


    }

    private SitesBillingInfoChecker billingInfoChecker;
    private SitesBillingInfoChecker creditCardMailSender;
    private RemoveVisitorsFromGroupsTask removeVisitorsFromGroupsTask;
    private FormExportTaskScheduler formExportTaskScheduler;

}