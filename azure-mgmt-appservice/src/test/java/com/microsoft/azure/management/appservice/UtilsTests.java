package com.microsoft.azure.management.appservice;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

public class UtilsTests {

    @Test
    public void testAttributeEnumCollection() throws Exception {
        Collection<RuntimeStack> runtimeStacks = RuntimeStack.values();
        int count = runtimeStacks.size();
        Assert.assertTrue(count > 30);      // a rough count

        RuntimeStack newRuntimeStack = new RuntimeStack("stack", "version");    // new, but not count as pre-defined

        runtimeStacks = RuntimeStack.values();
        Assert.assertEquals(count, runtimeStacks.size());
    }
}
