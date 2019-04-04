package org.sysu.bpmmanagementservice.vo;

public class AccountVo {
    private String username; //也就是ActIdUserEntity里面的userId
    private String role;

    public AccountVo() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

