/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.dns.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.dns.CaaRecordSet;
import com.microsoft.azure.management.dns.CaaRecordSets;
import com.microsoft.azure.management.dns.RecordType;
import rx.Observable;

/**
 * Implementation of CaaRecordSets.
 */
@LangDefinition
class CaaRecordSetsImpl
        extends DnsRecordSetsBaseImpl<CaaRecordSet, CaaRecordSetImpl>
        implements CaaRecordSets {

    CaaRecordSetsImpl(DnsZoneImpl dnsZone) {
        super(dnsZone, RecordType.CAA);
    }

    @Override
    public CaaRecordSetImpl getByName(String name) {
        RecordSetInner inner = this.parent().manager().inner().recordSets().get(
                this.dnsZone.resourceGroupName(),
                this.dnsZone.name(),
                name,
                this.recordType);
        if (inner == null) {
            return null;
        }
        return new CaaRecordSetImpl(inner.name(), this.dnsZone, inner);
    }

    @Override
    protected PagedList<CaaRecordSet> listIntern(String recordSetNameSuffix, Integer pageSize) {
        return super.wrapList(this.parent().manager().inner().recordSets().listByType(
                this.dnsZone.resourceGroupName(),
                this.dnsZone.name(),
                this.recordType,
                pageSize,
                recordSetNameSuffix));
    }

    @Override
    protected Observable<CaaRecordSet> listInternAsync(String recordSetNameSuffix, Integer pageSize) {
        return wrapPageAsync(this.parent().manager().inner().recordSets().listByTypeAsync(
                this.dnsZone.resourceGroupName(),
                this.dnsZone.name(),
                this.recordType));
    }

    @Override
    protected CaaRecordSetImpl wrapModel(RecordSetInner inner) {
        if (inner == null) {
            return null;
        }
        return new CaaRecordSetImpl(inner.name(), this.dnsZone, inner);
    }
}