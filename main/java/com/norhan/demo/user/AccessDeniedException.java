package com.norhan.demo.user;

public class AccessDeniedException extends com.norhan.demo.common.AccessDeniedException {
    public AccessDeniedException() {
        super("Access denied");
    }
}
