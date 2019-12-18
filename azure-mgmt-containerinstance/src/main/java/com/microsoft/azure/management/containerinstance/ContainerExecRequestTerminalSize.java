/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.containerinstance;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The size of the terminal.
 */
public class ContainerExecRequestTerminalSize {
    /**
     * The row size of the terminal.
     */
    @JsonProperty(value = "rows")
    private Integer rows;

    /**
     * The column size of the terminal.
     */
    @JsonProperty(value = "cols")
    private Integer cols;

    /**
     * Get the row size of the terminal.
     *
     * @return the rows value
     */
    public Integer rows() {
        return this.rows;
    }

    /**
     * Set the row size of the terminal.
     *
     * @param rows the rows value to set
     * @return the ContainerExecRequestTerminalSize object itself.
     */
    public ContainerExecRequestTerminalSize withRows(Integer rows) {
        this.rows = rows;
        return this;
    }

    /**
     * Get the column size of the terminal.
     *
     * @return the cols value
     */
    public Integer cols() {
        return this.cols;
    }

    /**
     * Set the column size of the terminal.
     *
     * @param cols the cols value to set
     * @return the ContainerExecRequestTerminalSize object itself.
     */
    public ContainerExecRequestTerminalSize withCols(Integer cols) {
        this.cols = cols;
        return this;
    }

}
