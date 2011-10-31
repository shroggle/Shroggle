package com.shroggle.util.reflection;

import java.util.List;

/**
 * @author Artem Stasuk
 */
public class ClassesMock implements Classes {

    @Override
    public List<Class> get(final ClassesFilter filter) {
        this.called = true;
        this.filter = filter;
        return result;
    }

    public ClassesFilter getFilter() {
        return filter;
    }

    public void setResult(final List<Class> result) {
        this.result = result;
    }

    public boolean isCalled() {
        return called;
    }

    private ClassesFilter filter;
    private List<Class> result;
    private boolean called;

}
