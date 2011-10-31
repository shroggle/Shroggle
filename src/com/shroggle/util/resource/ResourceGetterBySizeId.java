package com.shroggle.util.resource;

import com.shroggle.entity.Resource;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemException;
import com.shroggle.util.resource.provider.ResourceProvider;

import java.io.InputStream;

/**
 * @author Artem Stasuk
 */
public class ResourceGetterBySizeId implements ResourceGetter {

    public ResourceGetterBySizeId(final ResourceProvider provider) {
        this.provider = provider;
    }

    @Override
    public ResourceResponse execute(final ResourceRequest request) {
        final Resource resource = provider.get(request);

        InputStream data = null;
        if (resource != null) {
            try {
                data = ServiceLocator.getFileSystem().getResourceStream(resource);
            } catch (final FileSystemException exception) {
                data = resourceProducer.execute(resource);
            }
        }

        if (resource != null) {
            return new ResourceResponse(resource, data);
        }

        return new ResourceResponse();
    }

    private final ResourceProvider provider;
    private final ResourceProducer resourceProducer = ServiceLocator.getResourceProducer();

}
