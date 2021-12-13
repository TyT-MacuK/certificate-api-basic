package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.TypeOfValidationError;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.epam.esm.exception.TypeOfValidationError.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GiftCertificateValidatorTest {
    private static GiftCertificateValidator validator;

    @BeforeAll
    static void initialize() {
        validator = new GiftCertificateValidator();
    }

    @ParameterizedTest
    @MethodSource("provideGiftCertificateParams")
    void isGiftCertificateValidTest(GiftCertificateDto certificate, List<TypeOfValidationError> expected) {
        List<TypeOfValidationError> actual = validator.isGiftCertificateValid(certificate);
        assertEquals(expected, actual);
    }

    static List<Arguments> provideGiftCertificateParams() {
        List<Arguments> testCases = new ArrayList<>();
        testCases.add(Arguments.of(new GiftCertificateDto.Builder()
                .setName("test")
                .setDescription("test test")
                .setPrice(new BigDecimal(1))
                .setDuration(5).build(), Collections.emptyList()));
        testCases.add(Arguments.of(new GiftCertificateDto.Builder()
                .setName(null)
                .setDescription("test test")
                .setPrice(new BigDecimal(1))
                .setDuration(5).build(), Collections.singletonList(INVALID_NAME)));
        testCases.add(Arguments.of(new GiftCertificateDto.Builder()
                .setName("test")
                .setDescription(null)
                .setPrice(new BigDecimal(1))
                .setDuration(5).build(), Collections.singletonList(INVALID_DESCRIPTION)));
        testCases.add(Arguments.of(new GiftCertificateDto.Builder()
                .setName("test")
                .setDescription("test test")
                .setPrice(new BigDecimal(-1))
                .setDuration(5).build(), Collections.singletonList(INVALID_PRICE)));
        testCases.add(Arguments.of(new GiftCertificateDto.Builder()
                .setName("test")
                .setDescription("test test")
                .setPrice(new BigDecimal(1))
                .setDuration(-5).build(), Collections.singletonList(INVALID_DURATION)));
        return testCases;
    }

    @ParameterizedTest
    @MethodSource("provideSortOrderParams")
    void isSortOrderValidTest(String typeSort, boolean expected) {
        boolean actual = validator.isSortOrderValid(typeSort);
        assertEquals(expected, actual);
    }

    static List<Arguments> provideSortOrderParams() {
        List<Arguments> testCases = new ArrayList<>();
        testCases.add(Arguments.of("asc", true));
        testCases.add(Arguments.of("Desc", true));
        testCases.add(Arguments.of("ASC", true));
        testCases.add(Arguments.of("ASC", true));
        testCases.add(Arguments.of("test", false));
        testCases.add(Arguments.of("", false));
        testCases.add(Arguments.of(null, false));
        return testCases;
    }

    @AfterAll
    static void tierDown() {
        validator = null;
    }
}
