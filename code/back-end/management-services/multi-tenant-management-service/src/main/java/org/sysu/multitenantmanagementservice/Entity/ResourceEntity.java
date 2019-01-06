package org.sysu.multitenantmanagementservice.Entity;

import javax.persistence.*;

@Entity
@Table(name = "resource")
public class ResourceEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "file_storage")
    private String fileStorage;

    @Column(name = "human_level")
    private String humanLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getFileStorage() {
        return fileStorage;
    }

    public void setFileStorage(String fileStorage) {
        this.fileStorage = fileStorage;
    }

    public String getHumanLevel() {
        return humanLevel;
    }

    public void setHumanLevel(String humanLevel) {
        this.humanLevel = humanLevel;
    }
}
