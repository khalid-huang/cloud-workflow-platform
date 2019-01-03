package org.sysu.multitenantmanagementservice.Entity;

import javax.persistence.*;

@Entity
@Table(name = "process_engine")
public class ProcessEngineEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_id")
    private Long resourceId;

    @Column(name = "response_time_level")
    private Long responseTimeLevel;

    @Column(name = "request_throughout_level")
    private long requestThroughoutLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getResponseTimeLevel() {
        return responseTimeLevel;
    }

    public void setResponseTimeLevel(Long responseTimeLevel) {
        this.responseTimeLevel = responseTimeLevel;
    }

    public long getRequestThroughoutLevel() {
        return requestThroughoutLevel;
    }

    public void setRequestThroughoutLevel(long requestThroughoutLevel) {
        this.requestThroughoutLevel = requestThroughoutLevel;
    }
}
