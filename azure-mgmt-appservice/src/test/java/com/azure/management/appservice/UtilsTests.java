package com.azure.management.appservice;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

public class UtilsTests {

    @Test
    public void testAttributeCollection() throws Exception {
        Collection<RuntimeStack> runtimeStacks = RuntimeStack.getAll();
        int count = runtimeStacks.size();
        Assert.assertTrue(count > 30);      // a rough count

        RuntimeStack newRuntimeStack = new RuntimeStack("stack", "version");    // new, but not count as pre-defined

        runtimeStacks = RuntimeStack.getAll();
        Assert.assertEquals(count, runtimeStacks.size());
    }
}
