package com.microsoft.azure.management.appservice.implementation;

import com.microsoft.azure.management.appservice.AppServiceStacks;
import com.microsoft.azure.management.appservice.JavaVersion;
import com.microsoft.azure.management.appservice.NetFrameworkVersion;
import com.microsoft.azure.management.appservice.NodeVersion;
import com.microsoft.azure.management.appservice.PhpVersion;
import com.microsoft.azure.management.appservice.PythonVersion;
import com.microsoft.azure.management.appservice.RuntimeStack;
import com.microsoft.azure.management.appservice.RuntimeVersion;
import com.microsoft.azure.management.appservice.StackMajorVersion;
import com.microsoft.azure.management.appservice.WebContainer;
import com.microsoft.azure.management.appservice.WindowsAppServiceStacks;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of AppServiceStacks.
 */
class AppServiceStacksImpl implements AppServiceStacks {
    private static final String WINDOWS_OS_NAME = "Windows";

    private static final String LINUX_OS_NAME = "Linux";

    private AppServiceManager manager;

    /**
     * @param appServiceManager The manager
     */
    public AppServiceStacksImpl(AppServiceManager appServiceManager) {
        this.manager = appServiceManager;
    }

    /**
     * @return Returns the latest Windows runtime stacks
     */
    @Override
    public WindowsAppServiceStacks getLatestWindowsStacks() {
        Hashtable<String, RuntimeVersion> variousRuntimes = new Hashtable<>();
        variousRuntimes.put(JavaVersion.COMPONENT_NAME.toLowerCase(), JavaVersion.OFF);
        variousRuntimes.put(NodeVersion.COMPONENT_NAME.toLowerCase(), NodeVersion.OFF);
        variousRuntimes.put(WebContainer.COMPONENT_NAME.toLowerCase(), WebContainer.OFF);
        variousRuntimes.put(PythonVersion.COMPONENT_NAME.toLowerCase(), PythonVersion.OFF);
        variousRuntimes.put(PhpVersion.COMPONENT_NAME.toLowerCase(), PhpVersion.OFF);
        variousRuntimes.put(NetFrameworkVersion.COMPONENT_NAME.toLowerCase(), NetFrameworkVersion.OFF);

        Iterator<ApplicationStackInner> stackIter = this.manager.inner().providers().getAvailableStacks(WINDOWS_OS_NAME).iterator();
        while (stackIter.hasNext()) {
            ApplicationStackInner stack = stackIter.next();

            RuntimeVersion runtime = variousRuntimes.get(stack.name().toLowerCase());
            if (runtime != null) {
                runtime.parseApplicationStackInner(stack);
            } else {
                //throw new FileNotFoundException("Runtime " + stack.name() + " not supported");
            }
        }

        WindowsAppServiceStacksImpl runtimes = new WindowsAppServiceStacksImpl();

        runtimes.withJavaVersions(JavaVersion.values());
        runtimes.withNetFrameworkVersions(NetFrameworkVersion.values());
        runtimes.withPhpVersions(PhpVersion.values());
        runtimes.withPythonVersions(PythonVersion.values());
        runtimes.withWebContainers(WebContainer.values());
        runtimes.withNodeVersions(NodeVersion.values());

        return runtimes;
    }

    @Override
    public Set<RuntimeStack> listLatestLinuxStacks() {
        Set<RuntimeStack> linuxStacks = null;
        Iterator<ApplicationStackInner> stackIter = this.manager.inner().providers().getAvailableStacks(LINUX_OS_NAME).iterator();

        if (stackIter != null && stackIter.hasNext()) {
            linuxStacks = new HashSet<>();
        }

        while(stackIter.hasNext()) {
            ApplicationStackInner stack = stackIter.next();

            for (StackMajorVersion majorVersion : stack.properties().majorVersions()) {
                linuxStacks.add(RuntimeStack.fromStackNameAndVersionString(majorVersion.runtimeVersion()));
            }
        }

        return linuxStacks;
    }
}
