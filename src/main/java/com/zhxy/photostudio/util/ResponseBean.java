package com.zhxy.photostudio.util;

import lombok.Data;

@Data
public class ResponseBean<T> {

    private T entity;

    private boolean status;

    private String message;

    public ResponseBean(T entity, boolean status, String message) {
        this.entity = entity;
        this.status = status;
        this.message = message;
    }

    public ResponseBean(T entity, boolean status) {
        this.entity = entity;
        this.status = status;
    }

    public ResponseBean(boolean status) {
        this.status = status;
    }
}
