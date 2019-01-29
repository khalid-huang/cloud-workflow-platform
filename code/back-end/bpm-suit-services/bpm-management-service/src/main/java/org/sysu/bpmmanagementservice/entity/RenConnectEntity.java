package org.sysu.bpmmanagementservice.entity;

import javax.persistence.*;

@Entity
@Table(name = "ren_connect")
public class RenConnectEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "belongToOrganizableId")
    private String belongToOrganizabledId;

    @Column(name = "type")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBelongToOrganizabledId() {
        return belongToOrganizabledId;
    }

    public void setBelongToOrganizabledId(String belongToOrganizabledId) {
        this.belongToOrganizabledId = belongToOrganizabledId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
