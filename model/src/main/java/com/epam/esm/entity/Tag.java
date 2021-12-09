package com.epam.esm.entity;

public class Tag extends AbstractEntity {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int primal = 31;
        int result = 1;
        result = primal * result + Long.hashCode(id);
        result = primal * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        Tag tag = (Tag) object;
        if (name == null) {
            if (tag.name != null) {
                return false;
            }
        } else if (!name.equals(tag.name)) {
            return false;
        }
        return id == tag.id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[Tag: id - ").append(id);
        builder.append(", name - ").append(name);
        builder.append("]");
        return builder.toString();
    }
}
