package org.sysu.multitenantmanagementservice.Entity;

import javax.persistence.*;

@Entity
@Table(name = "ren_tenant_service_level")
public class RenTenantServiceLevelEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "request_arrive_rate")
    private Integer requestArriveRate;

    @Column(name = "response_time_level")
    private Integer responseTimeLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getRequestArriveRate() {
        return requestArriveRate;
    }

    public void setRequestArriveRate(Integer requestArriveRate) {
        this.requestArriveRate = requestArriveRate;
    }

    public Integer getResponseTimeLevel() {
        return responseTimeLevel;
    }

    public void setResponseTimeLevel(Integer responseTimeLevel) {
        this.responseTimeLevel = responseTimeLevel;
    }
}
