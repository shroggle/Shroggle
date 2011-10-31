package com.shroggle.util.resource;

import com.shroggle.entity.Resource;

import java.io.InputStream;

/**
 * @author Artem Stasuk
 */
public class ResourceProducerMock implements ResourceProducer {

    @Override
    public InputStream execute(Resource resource) {
        called = true;
        this.resource = resource;
        this.originalResource = originalResource;
        return result;
    }

    public Resource getOriginalResource() {
        return originalResource;
    }

    public Resource getResource() {
        return resource;
    }

    public boolean isCalled() {
        return called;
    }

    public void setResult(InputStream result) {
        this.result = result;
    }

    private InputStream result;
    private boolean called;
    private Resource resource;
    private Resource originalResource;

}
