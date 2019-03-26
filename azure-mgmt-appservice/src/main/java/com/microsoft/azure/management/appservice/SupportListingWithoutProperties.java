package com.microsoft.azure.management.appservice;

import com.microsoft.azure.PagedList;

public interface SupportListingWithoutProperties<T> {

    /**
     * Lists all the resources of the specified type in the currently selected subscription without
     * fetching extra properties that could require extra permissions
     *
     * @return list of resources
     */
    PagedList<T> listWithoutProperties();
}
