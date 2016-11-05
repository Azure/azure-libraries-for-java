package com.microsoft.azure.management.dns.implementation;

import com.microsoft.azure.management.dns.PtrRecord;
import com.microsoft.azure.management.dns.PtrRecordSet;
import com.microsoft.azure.management.dns.RecordType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link PtrRecordSet}.
 */
class PtrRecordSetImpl
        extends DnsRecordSetImpl
        implements PtrRecordSet {
    PtrRecordSetImpl(final DnsZoneImpl parentDnsZone, final RecordSetInner innerModel, final RecordSetsInner client) {
        super(parentDnsZone, innerModel, client);
    }

    static PtrRecordSetImpl newRecordSet(final String name, final DnsZoneImpl parentDnsZone, final RecordSetsInner client) {
        return new PtrRecordSetImpl(parentDnsZone,
                new RecordSetInner()
                        .withName(name)
                        .withType(RecordType.PTR.toString())
                        .withPtrRecords(new ArrayList<PtrRecord>()),
                client);
    }

    @Override
    public List<String> targetDomainNames() {
        List<String> targetDomainNames = new ArrayList<>();
        if (this.inner().aaaaRecords() != null) {
            for (PtrRecord ptrRecord : this.inner().ptrRecords()) {
                targetDomainNames.add(ptrRecord.ptrdname());
            }
        }
        return Collections.unmodifiableList(targetDomainNames);
    }

    @Override
    protected RecordSetInner prepareForUpdate(RecordSetInner resource) {
        if (this.inner().ptrRecords() != null && this.inner().ptrRecords().size() > 0) {
            if (resource.ptrRecords() == null) {
                resource.withPtrRecords(new ArrayList<PtrRecord>());
            }

            for (PtrRecord recordToAdd : this.inner().ptrRecords()) {
                resource.ptrRecords().add(recordToAdd);
            }
            this.inner().ptrRecords().clear();
        }

        if (this.recordSetRemoveInfo.ptrRecords().size() > 0) {
            if (resource.ptrRecords() != null) {
                for (PtrRecord recordToRemove : this.recordSetRemoveInfo.ptrRecords()) {
                    for (PtrRecord record : resource.ptrRecords()) {
                        if (record.ptrdname().equalsIgnoreCase(recordToRemove.ptrdname())) {
                            resource.ptrRecords().remove(record);
                            break;
                        }
                    }
                }
            }
            this.recordSetRemoveInfo.ptrRecords().clear();
        }
        return resource;
    }
}

