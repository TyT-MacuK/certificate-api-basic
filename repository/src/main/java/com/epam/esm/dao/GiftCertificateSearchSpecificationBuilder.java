package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class GiftCertificateSearchSpecificationBuilder {
    private static final String NAME_PARAM = "name";
    private static final String DESCRIPTION_PARAM = "description";
    private static final String TAGS_PARAM = "tags";
    private static final String CREATE_DATE_PARAM = "createDate";
    private static final String PART_OF_REQUEST = "%s%";
    private static final String DESCENDING_PARAM = "DESC";

    private Specification<GiftCertificate> specification;

    public GiftCertificateSearchSpecificationBuilder() {
        specification = Specification.where(null);
    }

    public GiftCertificateSearchSpecificationBuilder certificateName(String certificateName) {
        if (certificateName != null && !certificateName.isBlank()) {
            specification = specification.and(byCertificateName(certificateName));
        }
        return this;
    }

    public GiftCertificateSearchSpecificationBuilder certificateDescription(String certificateDescription) {
        if (certificateDescription != null && !certificateDescription.isBlank()) {
            specification = specification.and(byCertificateDescription(certificateDescription));
        }
        return this;
    }

    public GiftCertificateSearchSpecificationBuilder tagNames(List<String> tagNames) {
        if (tagNames != null && !tagNames.isEmpty()) {
            specification = specification.and(byTagNames(tagNames));
        }
        return this;
    }

    public GiftCertificateSearchSpecificationBuilder orderByName(String orderByName) {
        if (orderByName != null && !orderByName.isBlank()) {
            specification = specification.and(orderBy(orderByName, NAME_PARAM));
        }
        return this;
    }

    public GiftCertificateSearchSpecificationBuilder orderByCreateDate(String orderByCreateDate) {
        if (orderByCreateDate != null && !orderByCreateDate.isBlank()) {
            specification = specification.and(orderBy(orderByCreateDate, CREATE_DATE_PARAM));
        }
        return this;
    }

    public Specification<GiftCertificate> build() {
        return specification;
    }

    private Predicate createPredicate(String partRequestWithName, String requestParam,
                                      CriteriaBuilder criteriaBuilder, Root<GiftCertificate> certificateRoot) {
        String fullRequest = String.format(partRequestWithName, PART_OF_REQUEST);
        return criteriaBuilder.like(certificateRoot.get(requestParam), "%" + fullRequest + "%");
    }

    private Specification<GiftCertificate> byCertificateName(String certificateName) {
        return (criteriaBuilder, criteriaQuery, certificateRoot) ->
                createPredicate(certificateName, NAME_PARAM, certificateRoot, criteriaBuilder);
    }

    private Specification<GiftCertificate> byCertificateDescription(String certificateDescription) {
        return (criteriaBuilder, criteriaQuery, certificateRoot) ->
                createPredicate(certificateDescription, DESCRIPTION_PARAM, certificateRoot, criteriaBuilder);
    }

    private Specification<GiftCertificate> byTagNames(List<String> tagNames) {
        return (certificateRoot, criteriaQuery, criteriaBuilder) -> {
            Join<GiftCertificate, Tag> join = certificateRoot.join(TAGS_PARAM, JoinType.LEFT);
            Predicate predicate = join.get(NAME_PARAM).in(tagNames);

            criteriaQuery = criteriaQuery.distinct(true).where(predicate);
            return Specification.<GiftCertificate>where(null).toPredicate(certificateRoot, criteriaQuery, criteriaBuilder);
        };
    }

    private Specification<GiftCertificate> orderBy(String orderType, String orderParam) {
        return (certificateRoot, criteriaQuery, criteriaBuilder) -> {
            Order requestOrder;
            if (orderType.equalsIgnoreCase(DESCENDING_PARAM)) {
                requestOrder = criteriaBuilder.desc(certificateRoot.get(orderParam));
            } else {
                requestOrder = criteriaBuilder.asc(certificateRoot.get(orderParam));
            }
            criteriaQuery.orderBy(requestOrder);
            return Specification.<GiftCertificate>where(null).toPredicate(certificateRoot, criteriaQuery, criteriaBuilder);
        };
    }
}
