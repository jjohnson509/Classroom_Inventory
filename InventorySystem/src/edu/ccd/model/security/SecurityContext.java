package edu.ccd.model.security;

public class SecurityContext {
    String UserName;
    String AuthToken;

    public SecurityContext() {
        UserName = "Unknown";
        AuthToken = "";
    }

    public boolean setSecurityContext(String name, String token) {
        UserName = name;
        AuthToken = token;

        return UserName == name && AuthToken == token;
    }
}
