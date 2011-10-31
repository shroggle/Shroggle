package com.shroggle.util.reflection;

import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class ClassesByClassLoaderTest {

    @Test(expected = IllegalArgumentException.class)
    public void getByNull() {
        final ClassesMock classesMock = new ClassesMock();
        new ClassesClassLoader(null, classesMock, classesMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getByNullNo() {
        final ClassesMock classesMock = new ClassesMock();
        new ClassesClassLoader("fff", classesMock, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getByNullYes() {
        final ClassesMock classesMock = new ClassesMock();
        new ClassesClassLoader("fff", null, classesMock);
    }

    @Test
    public void getNo() {
        final ClassesMock yesClassesMock = new ClassesMock();
        final ClassesMock noClassesMock = new ClassesMock();
        final List<Class> foundClasses = new ArrayList<Class>();
        noClassesMock.setResult(foundClasses);
        final ClassesFilter filter = new ClassesFilterMock();
        final Classes classes = new ClassesClassLoader(
                "gggg", yesClassesMock, noClassesMock);

        Assert.assertEquals(foundClasses, classes.get(filter));
        Assert.assertEquals(filter, noClassesMock.getFilter());
        Assert.assertTrue(noClassesMock.isCalled());
        Assert.assertFalse(yesClassesMock.isCalled());
    }

}
