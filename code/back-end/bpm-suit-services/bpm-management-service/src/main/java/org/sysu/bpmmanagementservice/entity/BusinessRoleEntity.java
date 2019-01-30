package org.sysu.bpmmanagementservice.entity;

import javax.persistence.*;

@Entity
@Table(name = "act_id_group")
public class BusinessRoleEntity {

    /** 设计上，让id == name，表示业务角色名称*/
    @Id
    @Column(name = "ID_")
    private String id;

    @Column(name = "REV_")
    private int revision;

    @Column(name = "NAME_")
    private String name;

    @Column(name = "TYPE_")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
