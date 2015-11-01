package com.alorma.androidreactiveaccounts;

/**
 * Created by bernat.borras on 1/11/15.
 */
public class RequestPermissionException extends Exception {
    private String permission;

    public RequestPermissionException(String permission) {

        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String getMessage() {
        return permission;
    }
}
