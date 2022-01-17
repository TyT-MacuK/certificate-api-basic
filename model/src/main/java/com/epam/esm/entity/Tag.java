package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag")
public class Tag extends AbstractEntity {
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<GiftCertificate> giftCertificates;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity)) return false;
        if (o == null) return false;
        if (!getClass().equals(o.getClass())) return false;

        Tag tag = (Tag) o;

        if (this.getId() != tag.getId()) return false;
        return name != null ? name.equals(tag.name) : tag.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (this.getId() ^ (this.getId() >>> 32));
        result = 31 * result + Long.hashCode(this.getId());
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + this.getId() +
                ", name=" + name +
                '}';
    }
}
