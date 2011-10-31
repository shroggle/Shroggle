package com.shroggle.util.resource;

import com.shroggle.entity.Resource;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemException;
import com.shroggle.util.resource.provider.ResourceProvider;

import java.io.InputStream;

/**
 * @author Artem Stasuk
 */
public class ResourceGetterDirect implements ResourceGetter {

    public ResourceGetterDirect(final ResourceProvider provider) {
        this.provider = provider;
    }

    @Override
    public ResourceResponse execute(final ResourceRequest request) {
        final Resource resource = provider.get(request);

        if (resource != null) {
            try {
                final InputStream data = ServiceLocator.getFileSystem().getResourceStream(resource);
                return new ResourceResponse(resource, data);
            } catch (final FileSystemException exception) {
                // none
            }
        }

        return new ResourceResponse();
    }

    private final ResourceProvider provider;

}