package com.tanvir.endpoints;

/**
 * Centralised API endpoint constants — update here if paths change.
 */
public final class UserEndpoints {

    private UserEndpoints() {}

    public static final String USERS          = "/api/users";
    public static final String USER_BY_ID     = "/api/users/{id}";
    public static final String LOGIN          = "/api/login";
    public static final String REGISTER       = "/api/register";
    public static final String LOGOUT         = "/api/logout";

    // Query parameter keys
    public static final String PARAM_PAGE     = "page";
    public static final String PARAM_PER_PAGE = "per_page";
    public static final String PARAM_DELAY    = "delay";
}
