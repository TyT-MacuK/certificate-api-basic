package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GiftCertificate extends AbstractEntity {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private LocalDateTime createDay;
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
        GiftCertificate certificate = (GiftCertificate) object;
        if (id != certificate.id) {
            return false;
        }
        if (name == null) {
            if (certificate.name != null) {
                return false;
            }
        } else if (!name.equals(certificate.name)) {
            return false;
        }
        if (description == null) {
            if (certificate.description != null) {
                return false;
            }
        } else if (!description.equals(certificate.description)) {
            return false;
        }
        if (price == null) {
            if (certificate.price != null) {
                return false;
            }
        } else if (!price.equals(certificate.price)) {
            return false;
        }
        if (createDay == null) {
            if (certificate.createDay != null) {
                return false;
            }
        } else if (!createDay.equals(certificate.createDay)) {
            return false;
        }
        if (lastUpdateDay == null) {
            if (certificate.lastUpdateDay != null) {
                return false;
            }
        } else if (!lastUpdateDay.equals(certificate.lastUpdateDay)) {
            return false;
        }
        return duration == certificate.duration;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[Gift certificate: id - ").append(id);
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
        private final GiftCertificate certificate;

        public Builder() {
            certificate = new GiftCertificate();
        }

        public Builder setId(long id) {
            certificate.setId(id);
            return this;
        }

        public Builder setName(String name) {
            certificate.setName(name);
            return this;
        }

        public Builder setDescription(String description) {
            certificate.setDescription(description);
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            certificate.setPrice(price);
            return this;
        }

        public Builder setDuration(int duration) {
            certificate.setDuration(duration);
            return this;
        }

        public Builder setCreateDate(LocalDateTime createDate) {
            certificate.setCreateDay(createDate);
            return this;
        }

        public Builder setLastUpdateDate(LocalDateTime lastUpdateDate) {
            certificate.setLastUpdateDay(lastUpdateDate);
            return this;
        }

        public GiftCertificate build() {
            return certificate;
        }
    }
}