package ru.javawebinar.topjava;

import org.junit.jupiter.api.Assertions;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.topjava.util.Util;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.ErrorType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ErrorInfoUtil {
    public static final Locale locale = new Locale("ru");

    public static ResultMatcher errorInfo(String url, ErrorType errorType, MessageSource messageSource) {
        return errorInfo(url, errorType, messageSource, Collections.emptyList());
    }

    public static ResultMatcher errorInfo(String url, ErrorType errorType, MessageSource messageSource, String errorCode) {
        return errorInfo(url, errorType, messageSource, List.of(errorCode));
    }

    public static ResultMatcher errorInfo(String url, ErrorType errorType, MessageSource messageSource, List<String> errorCodes) {
        List<String> details = new ArrayList<>();
        for (String errorCode : errorCodes) {
            details.add(Util.localizeMessage(errorCode, messageSource, locale));
        }
        return result -> {
            ErrorInfo error = TestUtil.readFromJsonMvcResult(result, ErrorInfo.class);
            Assertions.assertEquals("http://localhost" + url, error.getUrl());
            Assertions.assertEquals(errorType, error.getType());
            List<String> actualDetails = error.getDetails();
            if (details.isEmpty() == false) {
                Assertions.assertEquals(details.size(), actualDetails.size());
                for (int i = 0; i < actualDetails.size(); i++) {
                    Assertions.assertTrue(actualDetails.get(i).endsWith(details.get(i)));
                }
            }
        };
    }
}