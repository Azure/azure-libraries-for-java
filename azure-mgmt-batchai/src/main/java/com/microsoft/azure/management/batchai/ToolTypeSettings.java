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

            interface WithAttachAndPythonInterpreter<ParentT> extends WithAttach<ParentT>,
                    ToolTypeSettings.DefinitionStages.WithPythonInterpreter<WithAttach<ParentT>> {
            }

            /**
             * The first stage of the Microsoft Cognitive Toolkit settings definition.
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface Blank<ParentT> extends WithLanguageType<ParentT> {
            }

            /**
             * Specifies the language type and script/config file path to use for Microsoft Cognitive Toolkit job.
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithLanguageType<ParentT> {
                /**
                 * @param configFilePath path of the config file
                 * @return the next stage of the definition
                 */
                WithAttach<ParentT> withBrainScript(String configFilePath);

                /**
                 * @param pythonScriptFilePath the path and file name of the python script to execute the job
                 * @return the next stage of the definition
                 */
                WithAttachAndPythonInterpreter<ParentT> withPythonScriptFile(String pythonScriptFilePath);
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
         * Definition of TensorFlow job settings.
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

            /**
             * The first stage of the TensorFlow settings definition.
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface Blank<ParentT> extends WithPython<ParentT> {
            }

            /**
             * Specifies python script file path.
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithPython<ParentT> {
                /**
                 * @param pythonScriptFilePath the path and file name of the python script to execute the job
                 * @return the next stage of the definition
                 */
                WithMasterCommandLineArgs<ParentT> withPythonScriptFile(String pythonScriptFilePath);
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

            /**
             * The first stage of the Caffe settings definition.
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface Blank<ParentT> extends WithFileType<ParentT> {
            }

            /**
             * Specifies the path and file name of the python script to execute the job or the path of the config file.
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithFileType<ParentT> {
                /**
                 * @param configFilePath the path of the config file
                 * @return the next stage of the definition
                 */
                WithAttach<ParentT> withConfigFile(String configFilePath);

                /**
                 * @param pythonScriptFilePath
                 * @return the next stage of the definition
                 */
                WithAttachAndPythonInterpreter<ParentT> withPythonScriptFile(String pythonScriptFilePath);
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

            /**
             * The first stage of the Caffe2 settings definition.
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface Blank<ParentT> extends WithPython<ParentT> {
            }

            /**
             * Specifies python script file path to execute the job.
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithPython<ParentT> {
                WithAttachAndPythonInterpreter<ParentT> withPythonScriptFile(String pythonScriptFilePath);
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

            /**
             * The first stage of the Chainer settings definition.
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface Blank<ParentT> extends WithPython<ParentT> {
            }

            /**
             * Specifies python script file path to execute the job.
             * @param <ParentT> the stage of the parent definition to return to after attaching this definition
             */
            interface WithPython<ParentT> {
                WithAttachAndPythonInterpreter<ParentT> withPythonScriptFile(String pythonScriptFilePath);
            }
        }
    }

    /**
     * Common definition stages.
     */
    interface DefinitionStages {
        /**
         * Specifies number of processes parameter that is passed to MPI runtime.
         * The default value for this property is equal to nodeCount property.
         * @param <ReturnT> next definition stage type
         */
        interface WithProcessCount<ReturnT> {
            /**
             * @param processCount Number of processes parameter that is passed to MPI runtime
             * @return the next stage of the definition
             */
            ReturnT withProcessCount(int processCount);
        }

        /**
         * Specifies command line arguments for the job.
         * @param <ReturnT> next definition stage type
         */
        interface WithCommandLineArgs<ReturnT> {
            ReturnT withCommandLineArgs(String commandLineArgs);
        }

        /**
         * Specifies the path to python interpreter.
         * @param <ReturnT> next definition stage type
         */
        interface WithPythonInterpreter<ReturnT> {
            /**
             * @param path the path to python interpreter
             * @return the next stage of the definition
             */
            ReturnT withPythonInterpreterPath(String path);
        }
    }
}
