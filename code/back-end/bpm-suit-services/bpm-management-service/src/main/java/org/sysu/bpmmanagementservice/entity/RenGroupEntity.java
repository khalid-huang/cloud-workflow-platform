package org.sysu.bpmmanagementservice.entity;

import javax.persistence.*;

@Entity
@Table(name = "ren_group")
public class RenGroupEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "note")
    private String note;

    @Column(name = "belongToId")
    private String belongToId;

    @Column(name = "groupType")
    private int groupType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBelongToId() {
        return belongToId;
    }

    public void setBelongToId(String belongToId) {
        this.belongToId = belongToId;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }
}
