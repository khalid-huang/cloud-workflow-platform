package org.sysu.bpmmanagementservice.entity;

import javax.persistence.*;

@Entity
@Table(name = "act_id_membership")
@IdClass(MembershipMultiKeysClass.class)
public class MembershipEntity {

    @Id
    @Column(name = "USER_ID_")
    private String userId;

    @Id
    @Column(name = "GROUP_ID_")
    private String groupId;

    @Column(name = "BIND_NUM_")
    private int bindNum;

//    public

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getBindNum() {
        return bindNum;
    }

    public void setBindNum(int bindNum) {
        this.bindNum = bindNum;
    }
}
