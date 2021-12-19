package com.epam.esm.exception;

import java.util.List;

public class AttachException extends Exception {
    List<Class<?>> notFoundEntityList;

    public AttachException(List<Class<?>> notFoundEntityList) {
        this.notFoundEntityList = notFoundEntityList;
    }

    public List<Class<?>> getNotFoundEntityList() {
        return notFoundEntityList;
    }
}