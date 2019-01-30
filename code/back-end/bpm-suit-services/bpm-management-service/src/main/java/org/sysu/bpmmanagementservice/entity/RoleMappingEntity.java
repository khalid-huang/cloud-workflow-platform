package org.sysu.bpmmanagementservice.entity;

import javax.persistence.*;

@Entity
@Table(name = "role_mapping")
public class RoleMappingEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "mapped_id")
    private String mappedId;

    @Column(name = "mapped_type")
    private int mappedType;

    @Column(name = "brole_name")
    private String broleName;

    @Column(name = "data_version")
    private String dataVersion;

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
}
