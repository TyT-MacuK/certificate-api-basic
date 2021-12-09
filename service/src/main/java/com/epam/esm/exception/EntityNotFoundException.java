package com.epam.esm.exception;

public class EntityNotFoundException extends Exception {
    private final long entityId;

    public EntityNotFoundException(long id) {
        this.entityId = id;
    }

    public long getEntityId() {
        return entityId;
    }
}
