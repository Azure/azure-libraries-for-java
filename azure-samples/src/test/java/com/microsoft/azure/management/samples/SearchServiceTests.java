/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.samples;

import com.microsoft.azure.management.search.samples.ManageSearchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SearchServiceTests extends SamplesTestBase {
  @Test
  public void testManageSearchService() {
    Assertions.assertTrue(ManageSearchService.runSample(azure));
  }
}
