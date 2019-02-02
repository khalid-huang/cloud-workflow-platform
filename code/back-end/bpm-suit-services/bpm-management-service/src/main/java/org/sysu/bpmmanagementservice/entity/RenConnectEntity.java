package org.sysu.bpmmanagementservice.entity;

import javax.persistence.*;

@Entity
@Table(name = "ren_connect")
public class RenConnectEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "belongToOrganizabledId")
    private String belongToOrganizabledId;

    @Column(name = "type")
    private int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBelongToOrganizabledId() {
        return belongToOrganizabledId;
    }

    public void setBelongToOrganizabledId(String belongToOrganizabledId) {
        this.belongToOrganizabledId = belongToOrganizabledId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
