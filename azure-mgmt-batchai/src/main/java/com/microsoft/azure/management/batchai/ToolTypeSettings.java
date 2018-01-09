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
 * Contains configuration definitions for different tool types.
 */
public interface ToolTypeSettings {
    /**
     * Client-side representation for Microsoft Cognitive Toolkit settings.
     */
    @Fluent
    @Beta(Beta.SinceVersion.V1_6_0)
    interface CognitiveToolkit extends Indexable,
            HasParent<BatchAIJob>,
            HasInner<CNTKsettings> {

        /**
         * Definition of azure cognitive toolkit settings.
         *
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface Definition<ParentT> extends
                DefinitionStages.Blank<ParentT>,
                CognitiveToolkit.DefinitionStages.WithAttachAndPythonInterpreter<ParentT> {
        }

        /**
         * Definition stages for azure cognitive toolkit settings.
         */
        interface DefinitionStages {

            interface WithAttach<ParentT> extends
                    Attachable.InDefinition<ParentT>,
                    ToolTypeSettings.DefinitionStages.WithProcessCount<WithAttach<ParentT>>,
                    ToolTypeSettings.DefinitionStages.WithCommandLineArgs<WithAttach<ParentT>> {
            }

            interface WithAttachAndPythonInterpreter<ParentT> extends WithAttach<ParentT> {
                WithAttach<ParentT> withPythonInterpreterPath(String path);
            }

            interface Blank<ParentT> extends WithLanguageType<ParentT> {
            }

            interface WithLanguageType<ParentT> {
                WithAttach<ParentT> withBrainScript(String configFilePath);

                WithAttachAndPythonInterpreter<ParentT> withPython(String pythonScriptFilePath);
            }
        }
    }

    /**
     * Specifies the settings for TensorFlow job.
     */
    @Fluent
    @Beta(Beta.SinceVersion.V1_6_0)
    interface TensorFlow extends Indexable,
            HasParent<BatchAIJob>,
            HasInner<TensorFlowSettings> {

        /**
         * Definition of Tensorflow job settings.
         *
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface Definition<ParentT> extends
                DefinitionStages.Blank<ParentT>,
                DefinitionStages.WithMasterCommandLineArgs<ParentT>,
                DefinitionStages.WithAttach<ParentT> {
        }

        /**
         * Definition stages for TensorFlow job settings.
         */
        interface DefinitionStages {

            interface WithAttach<ParentT> extends
                    Attachable.InDefinition<ParentT>,
                    ToolTypeSettings.DefinitionStages.WithPythonInterpreter<WithAttach<ParentT>> {
                WithAttach<ParentT> withWorkerCommandLineArgs(String commandLineArgs);

                WithAttach<ParentT> withParameterServerCommandLineArgs(String commandLineArgs);

                WithAttach<ParentT> withWorkerCount(int workerCount);

                WithAttach<ParentT> withParameterServerCount(int parameterServerCount);
            }

            interface Blank<ParentT> extends WithPython<ParentT> {
            }

            interface WithPython<ParentT> {
                WithMasterCommandLineArgs<ParentT> withPython(String pythonScriptFilePath);
            }

            interface WithMasterCommandLineArgs<ParentT> {
                WithAttach<ParentT> withMasterCommandLineArgs(String commandLineArgs);
            }
        }
    }

    /**
     * Specifies the settings for Caffe job.
     */
    @Fluent
    @Beta(Beta.SinceVersion.V1_6_0)
    interface Caffe extends Indexable,
            HasParent<BatchAIJob>,
            HasInner<CaffeSettings> {

        /**
         * Definition of Caffe settings.
         *
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface Definition<ParentT> extends
                DefinitionStages.Blank<ParentT>,
                Caffe.DefinitionStages.WithAttachAndPythonInterpreter<ParentT> {
        }

        /**
         * Definition stages for Caffe settings.
         */
        interface DefinitionStages {

            interface WithAttach<ParentT> extends
                    Attachable.InDefinition<ParentT>,
                    ToolTypeSettings.DefinitionStages.WithCommandLineArgs<WithAttach<ParentT>>,
                    ToolTypeSettings.DefinitionStages.WithProcessCount<WithAttach<ParentT>> {
            }

            interface WithAttachAndPythonInterpreter<ParentT> extends WithAttach<ParentT>,
                    ToolTypeSettings.DefinitionStages.WithPythonInterpreter<WithAttach<ParentT>> {
            }

            interface Blank<ParentT> extends WithFileType<ParentT> {
            }

            interface WithFileType<ParentT> {
                WithAttach<ParentT> withConfigFile(String configFilePath);

                WithAttachAndPythonInterpreter<ParentT> withPython(String pythonScriptFilePath);
            }
        }
    }

    /**
     * Specifies the settings for Caffe2 job.
     */
    @Fluent
    @Beta(Beta.SinceVersion.V1_6_0)
    interface Caffe2 extends Indexable,
            HasParent<BatchAIJob>,
            HasInner<Caffe2Settings> {

        /**
         * Definition of Caffe2 settings.
         *
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface Definition<ParentT> extends
                DefinitionStages.Blank<ParentT>,
                Caffe2.DefinitionStages.WithAttachAndPythonInterpreter<ParentT> {
        }

        /**
         * Definition stages for Caffe2 settings.
         */
        interface DefinitionStages {

            interface WithAttach<ParentT> extends
                    Attachable.InDefinition<ParentT>,
                    ToolTypeSettings.DefinitionStages.WithCommandLineArgs<WithAttach<ParentT>> {
            }

            interface WithAttachAndPythonInterpreter<ParentT> extends WithAttach<ParentT>,
                    ToolTypeSettings.DefinitionStages.WithPythonInterpreter<WithAttach<ParentT>> {
            }

            interface Blank<ParentT> extends WithPython<ParentT> {
            }

            interface WithPython<ParentT> {
                WithAttachAndPythonInterpreter<ParentT> withPython(String pythonScriptFilePath);
            }
        }
    }

    /**
     * Specifies the settings for Chainer job.
     */
    @Fluent
    @Beta(Beta.SinceVersion.V1_6_0)
    interface Chainer extends Indexable,
            HasParent<BatchAIJob>,
            HasInner<ChainerSettings> {

        /**
         * Definition of Chainer settings.
         *
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface Definition<ParentT> extends
                DefinitionStages.Blank<ParentT>,
                Chainer.DefinitionStages.WithAttachAndPythonInterpreter<ParentT> {
        }

        /**
         * Definition stages for Chainer job settings.
         */
        interface DefinitionStages {

            interface WithAttach<ParentT> extends
                    Attachable.InDefinition<ParentT>,
                    ToolTypeSettings.DefinitionStages.WithCommandLineArgs<WithAttach<ParentT>>,
                    ToolTypeSettings.DefinitionStages.WithProcessCount<WithAttach<ParentT>> {
            }

            interface WithAttachAndPythonInterpreter<ParentT> extends WithAttach<ParentT>,
                    ToolTypeSettings.DefinitionStages.WithPythonInterpreter<WithAttach<ParentT>> {
            }

            interface Blank<ParentT> extends WithPython<ParentT> {
            }

            interface WithPython<ParentT> {
                WithAttachAndPythonInterpreter<ParentT> withPython(String pythonScriptFilePath);
            }
        }
    }

    /**
     * Common definition stages.
     */
    interface DefinitionStages {
        interface WithProcessCount<ReturnT> {
            ReturnT withProcessCount(int processCount);
        }

        interface WithCommandLineArgs<ReturnT> {
            ReturnT withCommandLineArgs(String commandLineArgs);
        }

        interface WithPythonInterpreter<ReturnT> {
            ReturnT withPythonInterpreterPath(String path);
        }
    }
}
