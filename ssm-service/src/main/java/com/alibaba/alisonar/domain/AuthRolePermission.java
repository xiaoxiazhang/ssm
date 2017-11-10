package com.alibaba.alisonar.domain;

import java.util.Date;

public class AuthRolePermission {
    private Long id;

    private Long authRoleId;

    private Long authPermissionId;

    private Date gmtCreate;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthRoleId() {
        return authRoleId;
    }

    public void setAuthRoleId(Long authRoleId) {
        this.authRoleId = authRoleId;
    }

    public Long getAuthPermissionId() {
        return authPermissionId;
    }

    public void setAuthPermissionId(Long authPermissionId) {
        this.authPermissionId = authPermissionId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}