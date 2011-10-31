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
package com.shroggle.entity;

/**
 * @author Artem Stasuk
 */
public class ResourceCustom implements Resource {

    public static Resource copyWithSize(final Resource resource, ResourceSize resourceSize) {
        return new ResourceCustom(resource.getSiteId(), resource.getResourceId(), resource.getExtension(),
                resource.getResourceType(), resource.getName(), resourceSize);
    }

    public static Resource copyWithoutSize(final Resource resource) {
        return new ResourceCustom(resource.getSiteId(), resource.getResourceId(), resource.getExtension(),
                resource.getResourceType(), resource.getName(), null);
    }

    public ResourceCustom(
            final int siteId, final int resourceId, final String extension,
            final ResourceType type, final String name, final ResourceSize resourceSize) {
        this.siteId = siteId;
        this.resourceId = resourceId;
        this.extension = extension;
        this.type = type;
        this.name = name;
        this.resourceSize = resourceSize;
    }

    @Override
    public int getSiteId() {
        return siteId;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public ResourceSize getResourceSize() {
        return resourceSize;
    }

    @Override
    public int getResourceId() {
        return resourceId;
    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ResourceType getResourceType() {
        return type;
    }

    @Override
    public int hashCode() {
        return resourceId << 2 + type.hashCode() << 2 + (extension == null ? 0 : extension.hashCode());
    }

    @Override
    public boolean equals(final Object object) {
        if (object.getClass() != ResourceCustom.class) {
            return false;
        }
        final ResourceCustom resourceCustom = (ResourceCustom) object;
        final boolean extensionEquals = (resourceCustom.extension == null && extension == null) 
                || (extension != null && extension.equals(resourceCustom.extension));
        return extensionEquals && resourceCustom.resourceId == resourceId && resourceCustom.type == type;
    }

    private final int siteId;
    private final int resourceId;
    private final String extension;
    private final String name;
    private final ResourceType type;
    private final ResourceSize resourceSize;

}
