package org.sysu.multitenantmanagementservice.vo;

public class TenantVo {
    private String id;

    private String orgName;

    private String status;

    private String principleUsername;

    private String databaseUrl;

    private String schemaName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrincipleUsername() {
        return principleUsername;
    }

    public void setPrincipleUsername(String principleUsername) {
        this.principleUsername = principleUsername;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }
}
