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
package com.shroggle.util.process.synchronize.annotation;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.util.process.synchronize.*;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

/**
 * @author Artem Stasuk (artem)
 *         </p>
 *         Date: 15 вер 2008
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SynchronizeByCacheTest {

    @Test(expected = UnsupportedOperationException.class)
    public void executeWithNullMethod() {
        byCache.getRequest(null, new Object());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void executeWithNullObject() {
        Object object = new Object();
        byCache.getRequest(object.getClass().getMethods()[0], null);
    }

    @Test
    public void executeWithNotAnnotateMethod() {
        Object object = new Object();
        Assert.assertNull(byCache.getRequest(object.getClass().getMethods()[0], object));
    }

    @Test
    public void executeWithClassProperty() throws NoSuchMethodException {
        final ObjectSynchronizeByClassProperty object = new ObjectSynchronizeByClassProperty(12);
        final Method method = object.getClass().getMethod("test");
        final SynchronizeRequest synchronizeRequest = byCache.getRequest(method, object);

        Assert.assertNotNull(synchronizeRequest);
        Assert.assertEquals(SynchronizeRequestComposit.class, synchronizeRequest.getClass());
        final SynchronizeRequestComposit synchronizeRequestComposit = (SynchronizeRequestComposit) synchronizeRequest;
        Assert.assertEquals(1, synchronizeRequestComposit.getPoints().size());
        final SynchronizePoint synchronizePoint = new SynchronizePointEntity(User.class, 12, SynchronizeMethod.READ);
        final SynchronizePoint findSynchronizePoint = synchronizeRequestComposit.getPoints().iterator().next();
        Assert.assertEquals(synchronizePoint, findSynchronizePoint);
    }

    @Test
    public void executeWithClassPropertyWithDeep() throws NoSuchMethodException {
        final Site site = TestUtil.createSite();
        TestUtil.createPage(site);
        final ObjectSynchronizeByClassProperty object = new ObjectSynchronizeByClassProperty(site.getSiteId());
        final Method method = object.getClass().getMethod("testWithDeep");
        final SynchronizeRequest synchronizeRequest = byCache.getRequest(method, object);

        final SynchronizeRequestComposit synchronizeRequestComposit = (SynchronizeRequestComposit) synchronizeRequest;
        final SynchronizePoint synchronizePoint = new SynchronizePointEntity(Site.class, site.getSiteId(), SynchronizeMethod.READ);
        final SynchronizePoint findSynchronizePoint = synchronizeRequestComposit.getPoints().iterator().next();
        Assert.assertEquals(synchronizePoint, findSynchronizePoint);
    }

    @Test
    public void executeWithMethodParameter() throws NoSuchMethodException {
        final ObjectSynchronizeByMethodParameter object = new ObjectSynchronizeByMethodParameter();
        final Method method = object.getClass().getMethod("test", int.class);
        final SynchronizeRequest synchronizeRequest = byCache.getRequest(method, object, 12);

        Assert.assertNotNull(synchronizeRequest);
        Assert.assertEquals(SynchronizeRequestComposit.class, synchronizeRequest.getClass());
        final SynchronizeRequestComposit synchronizeRequestComposit = (SynchronizeRequestComposit) synchronizeRequest;
        Assert.assertEquals(1, synchronizeRequestComposit.getPoints().size());
        final SynchronizePoint synchronizePoint = new SynchronizePointEntity(User.class, 12, SynchronizeMethod.READ);
        final SynchronizePoint findSynchronizePoint = synchronizeRequestComposit.getPoints().iterator().next();
        Assert.assertEquals(synchronizePoint, findSynchronizePoint);
    }

    @Test
    public void executeWithMethodParameterWithDeep() throws NoSuchMethodException {
        final Site site = TestUtil.createSite();
        TestUtil.createPage(site);
        final ObjectSynchronizeByMethodParameter object = new ObjectSynchronizeByMethodParameter();
        final Method method = object.getClass().getMethod("testWithDeep", int.class);
        final SynchronizeRequest synchronizeRequest = byCache.getRequest(method, object, site.getSiteId());

        final SynchronizeRequestComposit synchronizeRequestComposit = (SynchronizeRequestComposit) synchronizeRequest;
        final SynchronizePoint synchronizePoint = new SynchronizePointEntity(Site.class, site.getSiteId(), SynchronizeMethod.READ);
        final SynchronizePoint findSynchronizePoint = synchronizeRequestComposit.getPoints().iterator().next();
        Assert.assertEquals(synchronizePoint, findSynchronizePoint);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void executeWithMethodParameterWithNegativeIndex() throws NoSuchMethodException {
        final ObjectSynchronizeByMethodParameter object = new ObjectSynchronizeByMethodParameter();
        final Method method = object.getClass().getMethod("testNegativ", int.class);
        byCache.getRequest(method, object, 12);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void executeWithMethodParameterWithVeryBigIndex() throws NoSuchMethodException {
        final ObjectSynchronizeByMethodParameter object = new ObjectSynchronizeByMethodParameter();
        final Method method = object.getClass().getMethod("testVeryBig", int.class);
        byCache.getRequest(method, object, 12);
    }

    @Test
    public void executeWithMethodParameterMany() throws NoSuchMethodException {
        final ObjectSynchronizeByMethodParameter object = new ObjectSynchronizeByMethodParameter();
        final Method method = object.getClass().getMethod("testMany", int.class);
        final SynchronizeRequest synchronizeRequest = byCache.getRequest(method, object, 12);

        Assert.assertNotNull(synchronizeRequest);
        Assert.assertEquals(SynchronizeRequestComposit.class, synchronizeRequest.getClass());
        final SynchronizeRequestComposit synchronizeRequestComposit = (SynchronizeRequestComposit) synchronizeRequest;
        Assert.assertEquals(3, synchronizeRequestComposit.getPoints().size());
    }

    @Test
    public void executeWithMethodParameterPropertyMany() throws NoSuchMethodException {
        final ObjectSynchronizeByMethodParameterProperty object = new ObjectSynchronizeByMethodParameterProperty();
        final Method method = object.getClass().getMethod("testMany", RequestSynchronizeByMethodParameterProperty.class);
        RequestSynchronizeByMethodParameterProperty request = new RequestSynchronizeByMethodParameterProperty();
        request.setA(12);
        request.setF("g");
        final SynchronizeRequest synchronizeRequest = byCache.getRequest(method, object, request);

        Assert.assertNotNull(synchronizeRequest);
        Assert.assertEquals(SynchronizeRequestComposit.class, synchronizeRequest.getClass());
        final SynchronizeRequestComposit synchronizeRequestComposit = (SynchronizeRequestComposit) synchronizeRequest;
        Assert.assertEquals(3, synchronizeRequestComposit.getPoints().size());
    }

    @Test
    public void executeWithMethodParameterProperty() throws NoSuchMethodException {
        final ObjectSynchronizeByMethodParameterProperty object = new ObjectSynchronizeByMethodParameterProperty();
        final Method method = object.getClass().getMethod("test", RequestSynchronizeByMethodParameterProperty.class);
        RequestSynchronizeByMethodParameterProperty request = new RequestSynchronizeByMethodParameterProperty();
        request.setA(12);
        final SynchronizeRequest synchronizeRequest = byCache.getRequest(method, object, request);

        Assert.assertNotNull(synchronizeRequest);
        Assert.assertEquals(SynchronizeRequestComposit.class, synchronizeRequest.getClass());
        final SynchronizeRequestComposit synchronizeRequestComposit = (SynchronizeRequestComposit) synchronizeRequest;
        Assert.assertEquals(1, synchronizeRequestComposit.getPoints().size());
        final SynchronizePoint synchronizePoint = new SynchronizePointEntity(User.class, 12, SynchronizeMethod.WRITE);
        final SynchronizePoint findSynchronizePoint = synchronizeRequestComposit.getPoints().iterator().next();
        Assert.assertEquals(synchronizePoint, findSynchronizePoint);
    }

    @Test
    public void executeWithMethodParameterPropertyWithDeep() throws NoSuchMethodException {
        final Site site = TestUtil.createSite();
        TestUtil.createPage(site);
        final ObjectSynchronizeByMethodParameterProperty object = new ObjectSynchronizeByMethodParameterProperty();
        final Method method = object.getClass().getMethod("testWithDeep", RequestSynchronizeByMethodParameterProperty.class);
        RequestSynchronizeByMethodParameterProperty request = new RequestSynchronizeByMethodParameterProperty();
        request.setA(site.getSiteId());
        final SynchronizeRequest synchronizeRequest = byCache.getRequest(method, object, request);

        Assert.assertEquals(SynchronizeRequestComposit.class, synchronizeRequest.getClass());
        final SynchronizeRequestComposit synchronizeRequestComposit = (SynchronizeRequestComposit) synchronizeRequest;
        final SynchronizePoint synchronizePoint = new SynchronizePointEntity(Site.class, site.getSiteId(), SynchronizeMethod.READ);
        final SynchronizePoint findSynchronizePoint = synchronizeRequestComposit.getPoints().iterator().next();
        Assert.assertEquals(synchronizePoint, findSynchronizePoint);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void executeWithMethodParameterPropertyNegative() throws NoSuchMethodException {
        final ObjectSynchronizeByMethodParameterProperty object = new ObjectSynchronizeByMethodParameterProperty();
        final Method method = object.getClass().getMethod("testNegativ", RequestSynchronizeByMethodParameterProperty.class);
        RequestSynchronizeByMethodParameterProperty request = new RequestSynchronizeByMethodParameterProperty();
        request.setA(12);
        byCache.getRequest(method, object, request);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void executeWithMethodParameterPropertyVeryBig() throws NoSuchMethodException {
        final ObjectSynchronizeByMethodParameterProperty object = new ObjectSynchronizeByMethodParameterProperty();
        final Method method = object.getClass().getMethod("testVeryBig", RequestSynchronizeByMethodParameterProperty.class);
        RequestSynchronizeByMethodParameterProperty request = new RequestSynchronizeByMethodParameterProperty();
        request.setA(12);
        byCache.getRequest(method, object, request);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void executeWithMethodParameterPropertyNotFound() throws NoSuchMethodException {
        final ObjectSynchronizeByMethodParameterProperty object = new ObjectSynchronizeByMethodParameterProperty();
        final Method method = object.getClass().getMethod("testNotFound", RequestSynchronizeByMethodParameterProperty.class);
        RequestSynchronizeByMethodParameterProperty request = new RequestSynchronizeByMethodParameterProperty();
        request.setA(12);
        byCache.getRequest(method, object, request);
    }

    @Test
    public void executeWithClassPropertyForMany() throws NoSuchMethodException {
        final ObjectSynchronizeByClassProperty object = new ObjectSynchronizeByClassProperty(12);
        final Method method = object.getClass().getMethod("testMany");
        final SynchronizeRequest request = byCache.getRequest(method, object);

        Assert.assertNotNull(request);
        Assert.assertEquals(SynchronizeRequestComposit.class, request.getClass());
        final SynchronizeRequestComposit synchronizeRequestComposit = (SynchronizeRequestComposit) request;
        Assert.assertEquals(3, synchronizeRequestComposit.getPoints().size());
    }

    @Test
    public void executeWithAllEntity() throws NoSuchMethodException {
        final ObjectSynchronizeByAllEntity object = new ObjectSynchronizeByAllEntity();
        final Method method = object.getClass().getMethod("test");
        final SynchronizeRequest request = byCache.getRequest(method, object);

        Assert.assertNotNull(request);
        final SynchronizeRequestComposit requestComposit = (SynchronizeRequestComposit) request;
        Assert.assertEquals(SynchronizeRequestComposit.class, request.getClass());
        Assert.assertEquals(new SynchronizePointAllEntity(Site.class), requestComposit.getPoints().iterator().next());
    }

    private final SynchronizeByCache byCache = new SynchronizeByCache();

}
