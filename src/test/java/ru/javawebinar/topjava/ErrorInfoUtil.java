package ru.javawebinar.topjava;

import org.junit.jupiter.api.Assertions;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.topjava.util.Util;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.ErrorType;

import java.util.Arrays;
import java.util.Locale;

public class ErrorInfoUtil {
    public static final Locale locale = new Locale("ru");

    public static ResultMatcher errorInfo(String url, ErrorType errorType) {
        return result -> {
            ErrorInfo error = TestUtil.readFromJsonMvcResult(result, ErrorInfo.class);
            checkUrlAndErrorType(url, error, errorType);
        };
    }

    public static ResultMatcher errorInfo(String url, ErrorType errorType, MessageSource messageSource, String errorCode) {
        String detail = Util.localizeMessage(errorCode, messageSource, locale);
        return result -> {
            ErrorInfo error = TestUtil.readFromJsonMvcResult(result, ErrorInfo.class);
            checkUrlAndErrorType(url, error, errorType);
            String[] actualDetails = error.getDetails();
            Assertions.assertTrue(
                    Arrays.stream(actualDetails)
                            .anyMatch(actualDetail -> actualDetail.endsWith(detail)));
        };
    }

    private static void checkUrlAndErrorType(String url, ErrorInfo error, ErrorType errorType) {
        Assertions.assertEquals("http://localhost" + url, error.getUrl());
        Assertions.assertEquals(errorType, error.getType());
    }
}