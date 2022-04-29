package com.example.security;

public class SecurityConstants {

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";

    public static final  String AUTH_ADMIN = "hasRole('" + ADMIN + "')";
    public static final  String AUTH_USER = "hasRole('" + USER + "')";
    public static final  String AUTH_ALL = "hasAnyRole('" + ADMIN + "', '" + USER + "')";
}
