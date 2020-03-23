/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.dns.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.dns.CaaRecord;
import com.microsoft.azure.management.dns.CaaRecordSet;
import com.microsoft.azure.management.dns.RecordType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of CaaRecordSet.
 */
@LangDefinition
class CaaRecordSetImpl
        extends DnsRecordSetImpl
        implements CaaRecordSet {
    CaaRecordSetImpl(final String name, final DnsZoneImpl parent, final RecordSetInner innerModel) {
        super(name, RecordType.CAA.toString(), parent, innerModel);
    }

    static CaaRecordSetImpl newRecordSet(final String name, final DnsZoneImpl parent) {
        return new CaaRecordSetImpl(name, parent,
                new RecordSetInner()
                        .withCaaRecords(new ArrayList<CaaRecord>()));
    }

    @Override
    public List<CaaRecord> records() {
        if (this.inner().caaRecords() != null) {
            return Collections.unmodifiableList(this.inner().caaRecords());
        }
        return Collections.unmodifiableList(new ArrayList<CaaRecord>());
    }

    @Override
    protected RecordSetInner prepareForUpdate(RecordSetInner resource) {
        if (this.inner().caaRecords() != null && this.inner().caaRecords().size() > 0) {
            if (resource.caaRecords() == null) {
                resource.withCaaRecords(new ArrayList<CaaRecord>());
            }

            resource.caaRecords().addAll(this.inner().caaRecords());
            this.inner().caaRecords().clear();
        }

        if (this.recordSetRemoveInfo.caaRecords().size() > 0) {
            if (resource.caaRecords() != null) {
                for (CaaRecord recordToRemove : this.recordSetRemoveInfo.caaRecords()) {
                    for (CaaRecord record : resource.caaRecords()) {
                        if (record.value().equalsIgnoreCase(recordToRemove.value())
                                && (record.flags().intValue() == recordToRemove.flags().intValue())
                                && (record.tag().equalsIgnoreCase(recordToRemove.tag()))) {
                            resource.caaRecords().remove(record);
                            break;
                        }
                    }
                }
            }
            this.recordSetRemoveInfo.caaRecords().clear();
        }
        return resource;
    }
}
