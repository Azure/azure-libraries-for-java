/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.dns.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.dns.DnsRecordSet;
import com.microsoft.azure.management.dns.DnsZone;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ExternalChildResourcesNonCachedImpl;

/**
 * Represents an record set collection associated with a DNS zone.
 */
@LangDefinition
class DnsRecordSetsImpl extends
        ExternalChildResourcesNonCachedImpl<DnsRecordSetImpl,
                                        DnsRecordSet,
                                        RecordSetInner,
                                        DnsZoneImpl,
                                        DnsZone> {
    /**
     * The default record set ttl in seconds.
     */
    private final long defaultTtlInSeconds = 3600;

    /**
     * Creates new DnsRecordSetsImpl.
     *
     * @param parent the parent DNS zone of the record set
     */
    DnsRecordSetsImpl(DnsZoneImpl parent) {
        super(parent, parent.taskGroup(), "RecordSet");
    }

    DnsRecordSetImpl defineARecordSet(String name) {
        return setDefaults(prepareInlineDefine(ARecordSetImpl.newRecordSet(name, this.parent())));
    }

    DnsRecordSetImpl defineAaaaRecordSet(String name) {
        return setDefaults(prepareInlineDefine(AaaaRecordSetImpl.newRecordSet(name, this.parent())));
    }

    void withCNameRecordSet(String name, String alias) {
        CNameRecordSetImpl recordSet = CNameRecordSetImpl.newRecordSet(name, this.parent());
        recordSet.inner().cnameRecord().withCname(alias);
        setDefaults(prepareInlineDefine(recordSet.withTimeToLive(defaultTtlInSeconds)));
    }

    DnsRecordSetImpl defineCaaRecordSet(String name) {
        return setDefaults(prepareInlineDefine(CaaRecordSetImpl.newRecordSet(name, this.parent())));
    }

    DnsRecordSetImpl defineCNameRecordSet(String name) {
        return setDefaults(prepareInlineDefine(CNameRecordSetImpl.newRecordSet(name, this.parent())));
    }

    DnsRecordSetImpl defineMXRecordSet(String name) {
        return setDefaults(prepareInlineDefine(MXRecordSetImpl.newRecordSet(name, this.parent())));
    }

    DnsRecordSetImpl defineNSRecordSet(String name) {
        return setDefaults(prepareInlineDefine(NSRecordSetImpl.newRecordSet(name, this.parent())));
    }

    DnsRecordSetImpl definePtrRecordSet(String name) {
        return setDefaults(prepareInlineDefine(PtrRecordSetImpl.newRecordSet(name, this.parent())));
    }

    DnsRecordSetImpl defineSrvRecordSet(String name) {
        return setDefaults(prepareInlineDefine(SrvRecordSetImpl.newRecordSet(name, this.parent())));
    }

    DnsRecordSetImpl defineTxtRecordSet(String name) {
        return setDefaults(prepareInlineDefine(TxtRecordSetImpl.newRecordSet(name, this.parent())));
    }

    DnsRecordSetImpl updateARecordSet(String name) {
        return prepareInlineUpdate(ARecordSetImpl.newRecordSet(name, this.parent()));
    }

    DnsRecordSetImpl updateAaaaRecordSet(String name) {
        return prepareInlineUpdate(AaaaRecordSetImpl.newRecordSet(name, this.parent()));
    }

    DnsRecordSetImpl updateMXRecordSet(String name) {
        return prepareInlineUpdate(MXRecordSetImpl.newRecordSet(name, this.parent()));
    }

    DnsRecordSetImpl updateCaaRecordSet(String name) {
        return prepareInlineUpdate(CaaRecordSetImpl.newRecordSet(name, this.parent()));
    }

    DnsRecordSetImpl updateCNameRecordSet(String name) {
        return prepareInlineUpdate(CNameRecordSetImpl.newRecordSet(name, this.parent()));
    }

    DnsRecordSetImpl updateNSRecordSet(String name) {
        return prepareInlineUpdate(NSRecordSetImpl.newRecordSet(name, this.parent()));
    }

    DnsRecordSetImpl updatePtrRecordSet(String name) {
        return prepareInlineUpdate(PtrRecordSetImpl.newRecordSet(name, this.parent()));
    }

    DnsRecordSetImpl updateSrvRecordSet(String name) {
        return prepareInlineUpdate(SrvRecordSetImpl.newRecordSet(name, this.parent()));
    }

    DnsRecordSetImpl updateTxtRecordSet(String name) {
        return prepareInlineUpdate(TxtRecordSetImpl.newRecordSet(name, this.parent()));
    }

    DnsRecordSetImpl updateSoaRecordSet() {
        return prepareInlineUpdate(SoaRecordSetImpl.newRecordSet(this.parent()));
    }

    void withoutARecordSet(String name, String eTagValue) {
        prepareInlineRemove(ARecordSetImpl.newRecordSet(name, this.parent()).withETagOnDelete(eTagValue));
    }

    void withoutAaaaRecordSet(String name, String eTagValue) {
        prepareInlineRemove(AaaaRecordSetImpl.newRecordSet(name, this.parent()).withETagOnDelete(eTagValue));
    }

    void withoutCaaRecordSet(String name, String eTagValue) {
        prepareInlineRemove(CaaRecordSetImpl.newRecordSet(name, this.parent()).withETagOnDelete(eTagValue));
    }

    void withoutCNameRecordSet(String name, String eTagValue) {
        prepareInlineRemove(CNameRecordSetImpl.newRecordSet(name, this.parent()).withETagOnDelete(eTagValue));
    }

    void withoutMXRecordSet(String name, String eTagValue) {
        prepareInlineRemove(MXRecordSetImpl.newRecordSet(name, this.parent()).withETagOnDelete(eTagValue));
    }

    void withoutNSRecordSet(String name, String eTagValue) {
        prepareInlineRemove(NSRecordSetImpl.newRecordSet(name, this.parent()).withETagOnDelete(eTagValue));
    }

    void withoutPtrRecordSet(String name, String eTagValue) {
        prepareInlineRemove(PtrRecordSetImpl.newRecordSet(name, this.parent()).withETagOnDelete(eTagValue));
    }

    void withoutSrvRecordSet(String name, String eTagValue) {
        prepareInlineRemove(SrvRecordSetImpl.newRecordSet(name, this.parent()).withETagOnDelete(eTagValue));
    }

    void withoutTxtRecordSet(String name, String eTagValue) {
        prepareInlineRemove(TxtRecordSetImpl.newRecordSet(name, this.parent()).withETagOnDelete(eTagValue));
    }

    private DnsRecordSetImpl setDefaults(DnsRecordSetImpl recordSet) {
        return recordSet.withTimeToLive(defaultTtlInSeconds);
    }
}
