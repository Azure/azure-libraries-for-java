/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasParent;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;

/**
 * Client-side representation for Microsoft Cognitive Toolkit settings.
 */
@Fluent
@Beta
public interface CognitiveToolkit extends Indexable,
        HasParent<BatchAIJob>,
        HasInner<CNTKsettings> {

    /**
     * Definition of azure file share reference.
     *
     * @param <ParentT> the stage of the parent definition to return to after attaching this definition
     */
    interface Definition<ParentT> extends
            DefinitionStages.Blank<ParentT>,
            DefinitionStages.WithAttachAndPythonInterpreter<ParentT> {
    }

    /**
     * Definition stages for azure file share reference.
     */
    interface DefinitionStages {

        interface WithAttach<ParentT> extends
                Attachable.InDefinition<ParentT> {
            DefinitionStages.WithAttach<ParentT> withCommandLineArgs(String commandLineArgs);

            DefinitionStages.WithAttach<ParentT> withProcessCount(int processCount);
        }

        interface WithAttachAndPythonInterpreter<ParentT> extends WithAttach<ParentT> {
            DefinitionStages.WithAttach<ParentT> withPythonInterpreterPath(String path);
        }

        interface Blank<ParentT> extends WithLanguageType<ParentT> {
        }

        interface WithLanguageType<ParentT> {
            DefinitionStages.WithAttach<ParentT> withBrainScript(String configFilePath);

            DefinitionStages.WithAttachAndPythonInterpreter<ParentT> withPython(String pythonScriptFilePath);
        }
    }
}
