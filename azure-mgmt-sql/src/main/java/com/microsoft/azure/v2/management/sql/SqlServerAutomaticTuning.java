/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.sql;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import com.microsoft.azure.management.sql.implementation.ServerAutomaticTuningInner;

import java.util.Map;

/**
 * An immutable client-side representation of an Azure SQL Server automatic tuning object.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_8_0)
public interface SqlServerAutomaticTuning extends
    HasInner<ServerAutomaticTuningInner>,
    Refreshable<SqlServerAutomaticTuning>,
    Updatable<SqlServerAutomaticTuning.Update> {

    /**
     * @return the server automatic tuning desired state
     */
    AutomaticTuningServerMode desiredState();

    /**
     * @return the server automatic tuning actual state
     */
    AutomaticTuningServerMode actualState();

    /**
     * @return the server automatic tuning individual options
     */
    Map<String, AutomaticTuningServerOptions> tuningOptions();



    /**************************************************************
     * Fluent interfaces to update a SqlServerAutomaticTuning
     **************************************************************/

    /**
     * The template for a SqlServerAutomaticTuning update operation, containing all the settings that can be modified.
     */
    @Beta(Beta.SinceVersion.V1_8_0)
    interface Update extends
        SqlServerAutomaticTuning.UpdateStages.WithAutomaticTuningMode,
        SqlServerAutomaticTuning.UpdateStages.WithAutomaticTuningOptions,
        Appliable<SqlServerAutomaticTuning> {
    }

    /**
     * Grouping of all the SqlServerAutomaticTuning update stages.
     */
    interface UpdateStages {
        /**
         * The update stage setting the SQL server automatic tuning desired state.
         */
        interface WithAutomaticTuningMode {
            /**
             * Sets the SQL server automatic tuning desired state.
             *
             * @param desiredState the server automatic tuning desired state
             * @return Next stage of the update.
             */
            Update withAutomaticTuningMode(AutomaticTuningServerMode desiredState);
        }

        /**
         * The update stage setting the server automatic tuning options.
         */
        interface WithAutomaticTuningOptions {
            /**
             * Sets the various SQL server automatic tuning options desired state.
             *
             * @param tuningOptionName tuning option name (
             *
             * @return Next stage of the update.
             */
            Update withAutomaticTuningOption(String tuningOptionName, AutomaticTuningOptionModeDesired desiredState);

            /**
             * Sets the various SQL server automatic tuning options desired state.
             *
             * @return Next stage of the update.
             */
            Update withAutomaticTuningOptions(Map<String, AutomaticTuningOptionModeDesired> tuningOptions);
        }
    }
}
