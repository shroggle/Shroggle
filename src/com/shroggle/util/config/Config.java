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

package com.shroggle.util.config;

import com.shroggle.entity.ChargeType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.*;

/**
 * @author Stasuk Artem
 */
@XmlRootElement
public class Config {

    public Config() {
        for (final ChargeType chargeType : ChargeType.values()) {
            paymentPrices.put(chargeType, 0d);
        }
    }

    public ConfigPayPal getPayPal() {
        return payPal;
    }

    public void setPayPal(ConfigPayPal payPal) {
        this.payPal = payPal;
    }

    public String getUserSitesUrl() {
        return userSitesUrl;
    }

    public void setUserSitesUrl(String userSitesUrl) {
        this.userSitesUrl = userSitesUrl;
    }

    public ConfigDatabase getDatabase() {
        return database;
    }

    public void setDatabase(ConfigDatabase database) {
        this.database = database;
    }

    public ConfigSmtp getMail() {
        return smtp;
    }

    public ConfigSmtp getConfigSmtp() {
        final ConfigSmtp configSmtp = getMail();
        if (configSmtp.getLogin() == null) {
            throw new UnsupportedOperationException(
                    "Can't start send message with null config smtp login!");
        }
        if (configSmtp.getPassword() == null) {
            throw new UnsupportedOperationException(
                    "Can't start send message with null config smtp password!");
        }
        if (configSmtp.getUrl() == null) {
            throw new UnsupportedOperationException(
                    "Can't start send message with null config smtp url!");
        }
        return configSmtp;
    }

    public void setMail(ConfigSmtp smtp) {
        this.smtp = smtp;
    }

    public ConfigRegistration getRegistration() {
        return registration;
    }

    public void setRegistration(ConfigRegistration registration) {
        this.registration = registration;
    }

    public String getAdminLogin() {
        return adminLogin;
    }

    public void setAdminLogin(String adminLogin) {
        this.adminLogin = adminLogin;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getNoBotImageTemplates() {
        return noBotImageTemplates;
    }

    public void setNoBotImageTemplates(String noBotImageTemplates) {
        this.noBotImageTemplates = noBotImageTemplates;
    }

    public String getSiteResourcesPath() {
        return siteResourcesPath;
    }

    public void setSiteResourcesPath(final String siteResourcesPath) {
        this.siteResourcesPath = siteResourcesPath;
    }

    public boolean isUseInternationalHightlight() {
        return useInternationalHightlight;
    }

    public boolean isUseCacheConfigStorage() {
        return useCacheConfigStorage;
    }

    public void setUseCacheConfigStorage(boolean useCacheConfigStorage) {
        this.useCacheConfigStorage = useCacheConfigStorage;
    }

    public void setUseInternationalHightlight(boolean useInternationalHightlight) {
        this.useInternationalHightlight = useInternationalHightlight;
    }

    @XmlElement(name = "notUserSiteUrl")
    public Set<String> getNotUserSiteUrls() {
        return notUserSiteUrls;
    }

    public void setNotUserSiteUrls(Set<String> notUserSiteUrls) {
        this.notUserSiteUrls = notUserSiteUrls;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public boolean isUseConsoleMailSender() {
        return useConsoleMailSender;
    }

    public boolean isUseCacheFileSystem() {
        return useCacheFileSystem;
    }

    public void setUseConsoleMailSender(boolean useConsoleMailSender) {
        this.useConsoleMailSender = useConsoleMailSender;
    }

    public boolean isUseCacheHibernate() {
        return useCacheHibernate;
    }

    public void setUseCacheHibernate(final boolean useCacheHibernate) {
        this.useCacheHibernate = useCacheHibernate;
    }

    public void setUseCacheFileSystem(boolean useCacheFileSystem) {
        this.useCacheFileSystem = useCacheFileSystem;
    }

    @XmlElement(name = "blockedSubDomain")
    public Set<String> getBlockedSubDomain() {
        return blockedSubDomain;
    }

    public void setBlockedSubDomain(final Set<String> blockedSubDomain) {
        this.blockedSubDomain = blockedSubDomain;
    }

    public boolean isUseCacheShowWorkPageVersion() {
        return useCacheShowWorkPageVersion;
    }

    public void setUseCacheShowWorkPageVersion(final boolean useCacheShowWorkPageVersion) {
        this.useCacheShowWorkPageVersion = useCacheShowWorkPageVersion;
    }

    public ConfigJavien getJavien() {
        return javien;
    }

    public void setJavien(ConfigJavien javien) {
        this.javien = javien;
    }

    public ConfigAuthorizeNet getAuthorizeNet() {
        return authorizeNet;
    }

    public void setAuthorizeNet(ConfigAuthorizeNet authorizeNet) {
        this.authorizeNet = authorizeNet;
    }

    public boolean isUseJournalToPersistance() {
        return useJournalToPersistance;
    }

    public void setUseJournalToPersistance(final boolean useJournalToPersistance) {
        this.useJournalToPersistance = useJournalToPersistance;
    }

    public boolean isUseJournalToConsole() {
        return useJournalToConsole;
    }

    public void setUseJournalToConsole(final boolean useJournalToConsole) {
        this.useJournalToConsole = useJournalToConsole;
    }

    public int getExpireUserDeleteCount() {
        return expireUserDeleteCount;
    }

    public void setExpireUserDeleteCount(final int expireUserDeleteCount) {
        this.expireUserDeleteCount = expireUserDeleteCount;
    }

    public long getExpireUserTime() {
        return expireUserTime;
    }

    public void setExpireUserTime(final long expireUserTime) {
        this.expireUserTime = expireUserTime;
    }

    public boolean isUseTestHelp() {
        return useTestHelp;
    }

    public void setUseTestHelp(final boolean useTestHelp) {
        this.useTestHelp = useTestHelp;
    }

    public String getDisableResourceMergeAgent() {
        return disableResourceMergeAgent;
    }

    public void setDisableResourceMergeAgent(final String disableResourceMergeAgent) {
        this.disableResourceMergeAgent = disableResourceMergeAgent;
    }

    public long getExpireVisitorTime() {
        return expireVisitorTime;
    }

    public void setExpireVisitorTime(long expireVisitorTime) {
        this.expireVisitorTime = expireVisitorTime;
    }

    public int getShowBlogPostCount() {
        return showBlogPostCount;
    }

    public void setShowBlogPostCount(final int showBlogPostCount) {
        this.showBlogPostCount = showBlogPostCount;
    }

    public boolean isUseProxy() {
        return useProxy;
    }

    public void setUseProxy(boolean useProxy) {
        this.useProxy = useProxy;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getHelpWindowURL() {
        return helpWindowURL;
    }

    public void setHelpWindowURL(String helpWindowURL) {
        this.helpWindowURL = helpWindowURL;
    }

    public ConfigResourcesVideo getSiteResourcesVideo() {
        return siteResourcesVideo;
    }

    public void setSiteResourcesVideo(final ConfigResourcesVideo siteResourcesVideo) {
        this.siteResourcesVideo = siteResourcesVideo;
    }

    public String getFlvmeta() {
        return flvmeta;
    }

    public void setFlvmeta(final String flvmeta) {
        this.flvmeta = flvmeta;
    }

    public String getPathResourceMergeAgent() {
        return pathResourceMergeAgent;
    }

    public void setPathResourceMergeAgent(final String pathResourceMergeAgent) {
        this.pathResourceMergeAgent = pathResourceMergeAgent;
    }

    public int getConcurrentResizerThreadCount() {
        return concurrentResizerThreadCount;
    }

    public void setConcurrentResizerThreadCount(final int concurrentResizerThreadCount) {
        this.concurrentResizerThreadCount = concurrentResizerThreadCount;
    }

    public BillingInfoProperties getBillingInfoProperties() {
        return billingInfoProperties;
    }

    public void setBillingInfoProperties(BillingInfoProperties billingInfoProperties) {
        this.billingInfoProperties = billingInfoProperties;
    }

    public boolean isUseTimeCounterPersistance() {
        return useTimeCounterPersistance;
    }

    public void setUseTimeCounterPersistance(final boolean useTimeCounterPersistance) {
        this.useTimeCounterPersistance = useTimeCounterPersistance;
    }

    @XmlElement(name = "paymentPrices")
    @XmlJavaTypeAdapter(PricesAdapter.class)
    public HashMap<ChargeType, Double> getPaymentPrices() {
        return paymentPrices;
    }

    public void setPaymentPrices(final HashMap<ChargeType, Double> paymentPrices) {
        this.paymentPrices = paymentPrices;
    }

    public int getConcurrentConvertThreadCount() {
        return concurrentConvertThreadCount;
    }

    public void setConcurrentConvertThreadCount(final int concurrentConvertThreadCount) {
        this.concurrentConvertThreadCount = concurrentConvertThreadCount;
    }

    public ConfigYuiCompressor getYuiCompresser() {
        return yuiCompressor;
    }

    public void setYuiCompresser(ConfigYuiCompressor yuiCompressor) {
        this.yuiCompressor = yuiCompressor;
    }

    public String getMaxPostSize() {
        return maxPostSize;
    }

    public void setMaxPostSize(String maxPostSize) {
        this.maxPostSize = maxPostSize;
    }

    public boolean isUseTimeCounterFileSystem() {
        return useTimeCounterFileSystem;
    }

    public void setUseTimeCounterFileSystem(final boolean useTimeCounterFileSystem) {
        this.useTimeCounterFileSystem = useTimeCounterFileSystem;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public int getConcurrentWriteFileThreadCount() {
        return concurrentWriteFileThreadCount;
    }

    public void setConcurrentWriteFileThreadCount(int concurrentWriteFileThreadCount) {
        this.concurrentWriteFileThreadCount = concurrentWriteFileThreadCount;
    }

    public boolean isShowLogs() {
        return showLogs;
    }

    public void setShowLogs(boolean showLogs) {
        this.showLogs = showLogs;
    }

    public boolean isDisableResourceCache() {
        return disableResourceCache;
    }

    public void setDisableResourceCache(boolean disableResourceCache) {
        this.disableResourceCache = disableResourceCache;
    }

    public boolean isUseRealMailSender() {
        return useRealMailSender;
    }

    public void setUseRealMailSender(boolean useRealMailSender) {
        this.useRealMailSender = useRealMailSender;
    }

    public void setResourceMergePrefix(String resourceMergePrefix) {
        this.resourceMergePrefix = resourceMergePrefix;
    }

    public String getServerIPAddress() {
        return serverIPAddress;
    }

    public void setServerIPAddress(String serverIPAddress) {
        this.serverIPAddress = serverIPAddress;
    }

    public void setRrdPath(final String rrdPath) {
        this.rrdPath = rrdPath;
    }

    public String getRrdPath() {
        return rrdPath;
    }

    public String getCsvPathSeparator() {
        return csvPathSeparator;
    }

    public void setCsvPathSeparator(String csvPathSeparator) {
        this.csvPathSeparator = csvPathSeparator;
    }

    public boolean isUseStatisticsExecutedHistory() {
        return useStatisticsExecutedHistory;
    }

    public void setUseStatisticsExecutedHistory(boolean useStatisticsExecutedHistory) {
        this.useStatisticsExecutedHistory = useStatisticsExecutedHistory;
    }

    public String getResourceMergePrefix() {
        return resourceMergePrefix;
    }

    public boolean isCheckDomainsIp() {
        return checkDomainsIp;
    }

    public void setCheckDomainsIp(boolean checkDomainsIp) {
        this.checkDomainsIp = checkDomainsIp;
    }

    @XmlElement(name = "adminEmail")
    public List<String> getAdminEmails() {
        return adminEmails;
    }

    public void setAdminEmails(List<String> adminEmails) {
        this.adminEmails = adminEmails;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     * Dima find that IE6 generate many js errors when page resource merge work
     * for fix this we add this parameter. It's regexp. It define for what
     * user agent deny page resource merge.
     *
     * @link http://jira.web-deva.com/browse/SW-2172
     * @link http://wiki.web-deva.com/pages/viewpage.action?pageId=1540134
     * @see com.shroggle.util.html.optimization.PageResourcesAcceleratorSpecificAgent
     */
    private String disableResourceMergeAgent = "MSIE 6";

    private boolean disableResourceCache = false;

    /**
     * For page resource merger need path
     * example: http://www.1.com/pageResources/12345645.js?applicationVersion=34
     * url need: {server}/pageResources/{resourceId}.{resourceExt}?applicationVersion={applicationVersion}
     * path: /pageResources/{resourceId}.{resourceExt}?applicationVersion={applicationVersion}
     * must be mapped on need file, for example we can use
     * we need store file in some directory:
     * pageResourcesPath = /artem/work/kanshin/shroggle/exploded/pageResources
     *
     * @link http://wiki.web-deva.com/pages/viewpage.action?pageId=1540134
     * @see com.shroggle.util.html.optimization.PageResourcesAcceleratorFile
     * @see com.shroggle.util.html.optimization.PageResourcesAcceleratorCache
     */
    private String pathResourceMergeAgent;

    /**
     * URL prefix for getting stored recource files<br>
     * Example: http://www.shroggle.com/PREFIX/css-file.css
     */
    private String resourceMergePrefix;

    private boolean useInternationalHightlight;
    private String siteResourcesPath;
    private String noBotImageTemplates;
    private ConfigRegistration registration = new ConfigRegistration();
    private BillingInfoProperties billingInfoProperties = new BillingInfoProperties();
    private ConfigJavien javien = new ConfigJavien();
    private ConfigAuthorizeNet authorizeNet = new ConfigAuthorizeNet();
    private ConfigResourcesVideo siteResourcesVideo = new ConfigResourcesVideo();
    private ConfigDatabase database = new ConfigDatabase();
    private ConfigSmtp smtp = new ConfigSmtp();
    private ConfigPayPal payPal = new ConfigPayPal();
    private String flvmeta = "flvmeta";
    private String adminLogin;
    private String adminPassword;
    private String supportEmail;
    private String applicationUrl;
    private String helpWindowURL;
    private String encryptionKey;
    private boolean showLogs = false;

    private Set<String> notUserSiteUrls = new HashSet<String>();
    private Set<String> blockedSubDomain = new HashSet<String>();
    private boolean useConsoleMailSender = false;
    private boolean useRealMailSender = false;
    private String serverIPAddress = "127.0.0.1";
    private String userSitesUrl;
    private boolean useCacheConfigStorage = true;
    private boolean useCacheFileSystem = true;

    /**
     * @see com.shroggle.util.process.timecounter.TimeCounterResult
     */
    private boolean useTimeCounterFileSystem = false;

    /**
     * @see com.shroggle.util.persistance.PersistanceTimeCounter
     */
    private boolean useTimeCounterPersistance = false;
    private boolean useProxy;
    private String proxyHost;
    private String proxyPort;

    /**
     * This parameter don't work. I keep it for future use.
     * Default value is true.
     */
    private boolean useCacheShowWorkPageVersion = true;
    private boolean useCacheHibernate = true;
    private boolean useJournalToPersistance = true;
    private boolean useJournalToConsole = false;
    private boolean useTestHelp = true;

    /**
     * How much users will be delete in one iteration.
     */
    private int expireUserDeleteCount = 10;
    private long expireUserTime = 5L * 24L * 60L * 60L * 1000L;
    private int showBlogPostCount = 10;
    private long expireVisitorTime = 10L * 24L * 60L * 60L * 1000L;
    private HashMap<ChargeType, Double> paymentPrices = new HashMap<ChargeType, Double>();

    /**
     * Define count how many concurrent threads system can use for
     * resize resource.
     * <p/>
     * If you are using ResourceResizerStandart you must know thah this
     * resizer need a lot of memory for resize one image because it's
     * read it in memory canvas in raw format (bmp), create new canvas with
     * same size, draw first to second canvas and after this store it in response
     * thread. For example one image 1024 * 1024 pexels need 4 Mb in memory, but we need
     * two (source and destination).
     * <p/>
     * You can change this parameter and apply it without restart application.
     *
     * @see com.shroggle.util.resource.ResourceProducerLimited
     * @see com.shroggle.util.resource.resize.ResourceResizerStandart
     */
    private int concurrentResizerThreadCount = 1;

    /**
     * Define how threads can paraller convert video to
     * video flv. If you use VideoTranscodeNative you can increase this
     * parameter to core count in your system for exampe for my Core Quadro
     * processor i set this parameter to 4. For effect if you change this parameter
     * please restart system.
     *
     * @see com.shroggle.util.transcode.VideoTranscodeAsync
     * @see com.shroggle.util.transcode.VideoTranscode
     * @see com.shroggle.presentation.video.CreateVideoService
     */
    private int concurrentConvertThreadCount = 1;

    private int concurrentWriteFileThreadCount = 1;

    private ConfigYuiCompressor yuiCompressor = new ConfigYuiCompressor();

    private String maxPostSize;

    private boolean useStatisticsExecutedHistory = false;

    /**
     * If not set system don't use rrd counters.
     */
    private String rrdPath;

    private String csvPathSeparator = ",";

    private boolean checkDomainsIp = true;

    private List<String> adminEmails = new ArrayList<String>();

    private String applicationName;
}