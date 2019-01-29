package org.sysu.bpmmanagementservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ren_cconfig")
public class RenCconfigEntity {
    @Id
    @Column(name = "rkey")
    private String rkey;

    @Column(name = "rvalue")
    private String rvale;

    public String getRkey() {
        return rkey;
    }

    public void setRkey(String rkey) {
        this.rkey = rkey;
    }

    public String getRvale() {
        return rvale;
    }

    public void setRvale(String rvale) {
        this.rvale = rvale;
    }
}
