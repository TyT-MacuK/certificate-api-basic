package com.epam.esm.dto;

public class TagDto extends AbstractDto {
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
        TagDto dto = (TagDto) object;
        if (name == null) {
            if (dto.name != null) {
                return false;
            }
        } else if (!name.equals(dto.name)) {
            return false;
        }
        return id == dto.id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[Tag DTO: id - ").append(id);
        builder.append(", name - ").append(name);
        builder.append("]");
        return builder.toString();
    }
}
