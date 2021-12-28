package com.epam.esm.validator;

import static com.epam.esm.exception.TypeOfValidationError.INVALID_NAME;

import com.epam.esm.exception.TypeOfValidationError;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagValidatorTest {
    private static TagValidator validator;

    @BeforeAll
    static void initialize() {
        validator = new TagValidator();
    }

//    @ParameterizedTest
//    @MethodSource("provideTagParams")
//    void validateNameTest(String name, List<TypeOfValidationError> expected) {
//        List<TypeOfValidationError> actual = validator.validateName(name);
//        assertEquals(expected, actual);
//    }
//
//    static List<Arguments> provideTagParams() {
//        List<Arguments> testCases = new ArrayList<>();
//        testCases.add(Arguments.of("test", Collections.emptyList()));
//        testCases.add(Arguments.of("testtesttesttesttesttesttesttest", Collections.singletonList(INVALID_NAME)));
//        testCases.add(Arguments.of("test name", Collections.singletonList(INVALID_NAME)));
//        testCases.add(Arguments.of("test1", Collections.singletonList(INVALID_NAME)));
//        testCases.add(Arguments.of("!test", Collections.singletonList(INVALID_NAME)));
//        testCases.add(Arguments.of("", Collections.singletonList(INVALID_NAME)));
//        testCases.add(Arguments.of(null, Collections.singletonList(INVALID_NAME)));
//        return testCases;
//    }

    @AfterAll
    static void tierDown() {
        validator = null;
    }
}
