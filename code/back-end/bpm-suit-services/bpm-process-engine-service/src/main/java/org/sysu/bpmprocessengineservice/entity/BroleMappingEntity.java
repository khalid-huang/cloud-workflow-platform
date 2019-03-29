package org.sysu.bpmprocessengineservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "brole_mapping")
public class BroleMappingEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "mapped_id")
    private String mappedId;

    @Column(name = "mapped_type")
    private int mappedType;

    @Column(name = "brole_name")
    private String broleName;

    @Column(name = "data_version")
    private String dataVersion;

    @Column(name = "proc_def_id")
    private String procDefId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMappedId() {
        return mappedId;
    }

    public void setMappedId(String mappedId) {
        this.mappedId = mappedId;
    }

    public int getMappedType() {
        return mappedType;
    }

    public void setMappedType(int mappedType) {
        this.mappedType = mappedType;
    }

    public String getBroleName() {
        return broleName;
    }

    public void setBroleName(String broleName) {
        this.broleName = broleName;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }
}

