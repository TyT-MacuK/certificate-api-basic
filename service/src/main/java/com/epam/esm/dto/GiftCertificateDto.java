package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GiftCertificateDto extends AbstractDto {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "UTC")
    private LocalDateTime createDay;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "UTC")
    private LocalDateTime lastUpdateDay;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDay() {
        return createDay;
    }

    public void setCreateDay(LocalDateTime createDay) {
        this.createDay = createDay;
    }

    public LocalDateTime getLastUpdateDay() {
        return lastUpdateDay;
    }

    public void setLastUpdateDay(LocalDateTime lastUpdateDay) {
        this.lastUpdateDay = lastUpdateDay;
    }

    @Override
    public int hashCode() {
        int primal = 31;
        int result = 1;
        result = primal * result + Long.hashCode(id);
        result = primal * result + (name != null ? name.hashCode() : 0);
        result = primal * result + (description != null ? description.hashCode() : 0);
        result = primal * result + (price != null ? price.hashCode() : 0);
        result = primal * result + duration;
        result = primal * result + (createDay != null ? createDay.hashCode() : 0);
        result = primal * result + (lastUpdateDay != null ? lastUpdateDay.hashCode() : 0);
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
        GiftCertificateDto dto = (GiftCertificateDto) object;
        if (id != dto.id) {
            return false;
        }
        if (name == null) {
            if (dto.name != null) {
                return false;
            }
        } else if (!name.equals(dto.name)) {
            return false;
        }
        if (description == null) {
            if (dto.description != null) {
                return false;
            }
        } else if (!description.equals(dto.description)) {
            return false;
        }
        if (price == null) {
            if (dto.price != null) {
                return false;
            }
        } else if (!price.equals(dto.price)) {
            return false;
        }
        if (createDay == null) {
            if (dto.createDay != null) {
                return false;
            }
        } else if (!createDay.equals(dto.createDay)) {
            return false;
        }
        if (lastUpdateDay == null) {
            if (dto.lastUpdateDay != null) {
                return false;
            }
        } else if (!lastUpdateDay.equals(dto.lastUpdateDay)) {
            return false;
        }
        return duration == dto.duration;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[Gift certificate DTO: id - ").append(id);
        builder.append(", name - ").append(name).append("\n");
        builder.append("Description - ").append(description).append("\n");
        builder.append("Price - ").append(price).append("\n");
        builder.append("Duration - ").append(duration).append("\n");
        builder.append("Create day - ").append(createDay).append("\n");
        builder.append("Last update day - ").append(lastUpdateDay);
        builder.append("]");
        return builder.toString();
    }

    public static class Builder {
        private GiftCertificateDto dto;

        public Builder() {
            dto = new GiftCertificateDto();
        }

        public Builder setId(long id) {
            dto.setId(id);
            return this;
        }

        public Builder setName(String name) {
            dto.setName(name);
            return this;
        }

        public Builder setDescription(String description) {
            dto.setDescription(description);
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            dto.setPrice(price);
            return this;
        }

        public Builder setDuration(int duration) {
            dto.setDuration(duration);
            return this;
        }

        public Builder setCreateDate(LocalDateTime createDate) {
            dto.setCreateDay(createDate);
            return this;
        }

        public Builder setLastUpdateDate(LocalDateTime lastUpdateDate) {
            dto.setLastUpdateDay(lastUpdateDate);
            return this;
        }

        public GiftCertificateDto build() {
            return dto;
        }
    }
}
