package com.epam.esm.hateoas;

import com.epam.esm.controllers.GiftCertificateController;
import com.epam.esm.controllers.TagController;
import com.epam.esm.controllers.UserController;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * The type Hateoas builder.
 */
public class HateoasBuilder {

    private HateoasBuilder() {}
    /**
     * Add link to gift certificate.
     *
     * @param certificate the certificate
     * @throws EntityNotFoundException the entity not found exception
     */
    public static void addLinkToGiftCertificate(GiftCertificateDto certificate) throws EntityNotFoundException {
        certificate.add(linkTo(methodOn(GiftCertificateController.class).findById(certificate.getId(),
                LocaleContextHolder.getLocale().getLanguage())).withSelfRel());
        addLinksToTags(certificate.getTags());
    }

    /**
     * Add links to gift certificates.
     *
     * @param certificateList the certificate list
     * @throws EntityNotFoundException the entity not found exception
     */
    public static void addLinksToGiftCertificates(List<GiftCertificateDto> certificateList) throws EntityNotFoundException {
        if (certificateList != null && !certificateList.isEmpty()) {
            for (GiftCertificateDto certificate : certificateList) {
                certificate.add(linkTo(methodOn(GiftCertificateController.class).findById(certificate.getId(),
                        LocaleContextHolder.getLocale().getLanguage())).withSelfRel());
                addLinksToTags(certificate.getTags());
            }
        }
    }

    /**
     * Add link to tag.
     *
     * @param tag the tag
     * @throws EntityNotFoundException the entity not found exception
     */
    public static void addLinkToTag(TagDto tag) throws EntityNotFoundException {
        tag.add(linkTo(methodOn(TagController.class).findTagById(tag.getId(),
                LocaleContextHolder.getLocale().getLanguage())).withSelfRel());
    }

    /**
     * Add links to tags.
     *
     * @param tags the tags
     * @throws EntityNotFoundException the entity not found exception
     */
    public static void addLinksToTags(List<TagDto> tags) throws EntityNotFoundException {
        if (tags != null && !tags.isEmpty()) {
            for (TagDto tag : tags) {
                tag.add(linkTo(methodOn(TagController.class).findTagById(tag.getId(),
                        LocaleContextHolder.getLocale().getLanguage())).withSelfRel());
            }
        }
    }

    /**
     * Add link to user.
     *
     * @param user the user
     * @throws EntityNotFoundException the entity not found exception
     */
    public static void addLinkToUser(UserDto user) throws EntityNotFoundException {
        user.add(linkTo(methodOn(UserController.class).findById(user.getId(),
                LocaleContextHolder.getLocale().getLanguage())).withSelfRel());
    }

    /**
     * Add links to users.
     *
     * @param userList the user list
     * @throws EntityNotFoundException the entity not found exception
     */
    public static void addLinksToUsers(List<UserDto> userList) throws EntityNotFoundException {
        if (userList != null && !userList.isEmpty()) {
            for(UserDto user : userList) {
                user.add(linkTo(methodOn(UserController.class).findById(user.getId(),
                                LocaleContextHolder.getLocale().getLanguage())).withSelfRel());
            }
        }
    }

    /**
     * Add link to order.
     *
     * @param order the order
     * @throws EntityNotFoundException the entity not found exception
     */
    public static void addLinkToOrder(OrderDto order) throws EntityNotFoundException {
        order.add(linkTo(methodOn(UserController.class).findById(order.getId(),
                LocaleContextHolder.getLocale().getLanguage())).withSelfRel());
    }

    /**
     * Add links to orders.
     *
     * @param orderList the order list
     * @throws EntityNotFoundException the entity not found exception
     */
    public static void addLinksToOrders(List<OrderDto> orderList) throws EntityNotFoundException {
        if (orderList != null && !orderList.isEmpty()) {
            for(OrderDto order : orderList) {
                order.add(linkTo(methodOn(UserController.class).findById(order.getId(),
                        LocaleContextHolder.getLocale().getLanguage())).withSelfRel());

                GiftCertificateDto certificate = order.getGiftCertificate();
                addLinkToGiftCertificate(certificate);

                UserDto user = order.getUser();
                addLinkToUser(user);
            }
        }
    }
}
