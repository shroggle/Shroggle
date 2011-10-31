<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.sf.ehcache.Cache" %>
<%@ page import="net.sf.ehcache.Statistics" %>
<%@ page import="com.shroggle.presentation.system.PersistanceCacheStatisticsAction" %>
<%@ page import="net.sf.ehcache.CacheManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head><title>Persistance cache statistics</title></head>
    <body>
        <table border="1" cellspacing="0" cellpadding="5">
            <thead>
                <tr>
                    <td>Cache Name</td>
                    <td>Hits</td>
                    <td>Misses</td>
                    <td>Size</td>
                    <td>Capacity</td>
                    <td>Efficiency</td>
                </tr>
            </thead>
            <tbody>
                <% final PersistanceCacheStatisticsAction action = (PersistanceCacheStatisticsAction) request.getAttribute("actionBean"); %>
                <% final CacheManager cacheManager = action.getInternal(); %>
                <% for (final String cacheName : cacheManager.getCacheNames()) { %>
                    <% final Cache cache = cacheManager.getCache(cacheName); %>
                    <% final Statistics statistics = cache.getStatistics(); %>
                    <% final double base = statistics.getCacheHits() + statistics.getCacheMisses() - cache.getMemoryStoreSize(); %>
                    <tr>
                        <td><%= cacheName %></td> 
                        <td><%= statistics.getCacheHits() %></td>
                        <td><%= statistics.getCacheMisses() %></td>
                        <td><%= cache.getMemoryStoreSize() %></td>
                        <td><%= cache.getCacheConfiguration().getMaxElementsInMemory() %></td>
                        <td><%= base <= 0 ? "&nbsp;" : ((int) (statistics.getCacheHits() / base * 100)) + "%" %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </body>
</html>