/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.containerregistry.implementation;
//
//import com.microsoft.azure.management.apigeneration.LangDefinition;
//import com.microsoft.azure.management.containerregistry.Build;
//
//import java.util.ArrayList;
//
/**
 * Implementation for container group's volume definition stages interface.
 */
public class BuildArgumentImpl {

}
//@LangDefinition
//public class BuildArgumentImpl
//    implements
//        Build.QueuedQuickBuildDefinitionStages.QueuedQuickBuildArgumentDefinitionStages.BuildArgumentDefinition<Build.QueuedQuickBuildDefinitionStages.WithCreate> {
//
//    private BuildArgumentInner buildArgumentInner;
//    private BuildImpl parent;
//
//    BuildArgumentImpl(BuildImpl parent, String buildArgumentName) {
//        this.parent = parent;
//        this.buildArgumentInner = new BuildArgumentInner().withName(buildArgumentName);
//    }
//
//    @Override
//    public BuildImpl attach() {
//        if (parent.quickBuildRequest().buildArguments() == null) {
//            parent.quickBuildRequest().withBuildArguments(new ArrayList<BuildArgumentInner>());
//        }
//        parent.quickBuildRequest().buildArguments().add(buildArgumentInner);
//        return parent;
//    }
//
//    @Override
//    public BuildArgumentImpl withValue(String value) {
//        this.buildArgumentInner.withValue(value);
//        return this;
//    }
//
//    @Override
//    public BuildArgumentImpl withType(String type) {
//        this.buildArgumentInner.withType(type);
//        return this;
//    }
//
//    @Override
//    public BuildArgumentImpl withSecrecyEnabled() {
//        this.buildArgumentInner.withIsSecret(true);
//        return this;
//    }
//
//    @Override
//    public BuildArgumentImpl withSecrecyDisabled() {
//        this.buildArgumentInner.withIsSecret(false);
//        return this;
//    }
//}
