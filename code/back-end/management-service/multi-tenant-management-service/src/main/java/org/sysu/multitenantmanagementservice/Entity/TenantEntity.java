package org.sysu.multitenantmanagementservice.Entity;

import javax.persistence.*;

@Entity
@Table(name = "tenant")
public class TenantEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Integer status;

    @Column(name = "password")
    private String password;

    @Column(name = "dburl")
    private String dburl;

    @Column(name = "dbschema")
    private String dbscheam;

    @Column(name = "dbusername")
    private String dbusername;

    @Column(name = "dbpassword")
    private String dbpassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDburl() {
        return dburl;
    }

    public void setDburl(String dburl) {
        this.dburl = dburl;
    }

    public String getDbscheam() {
        return dbscheam;
    }

    public void setDbscheam(String dbscheam) {
        this.dbscheam = dbscheam;
    }

    public String getDbusername() {
        return dbusername;
    }

    public void setDbusername(String dbusername) {
        this.dbusername = dbusername;
    }

    public String getDbpassword() {
        return dbpassword;
    }

    public void setDbpassword(String dbpassword) {
        this.dbpassword = dbpassword;
    }
}
