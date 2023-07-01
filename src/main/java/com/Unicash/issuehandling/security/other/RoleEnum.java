package com.Unicash.issuehandling.security.other;


public enum RoleEnum {
    ADMIN("ADMIN"),
    SUPPORT("SUPPORT"),
    DEVELOPER("DEVELOPER");

    private String role;

    RoleEnum(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}
